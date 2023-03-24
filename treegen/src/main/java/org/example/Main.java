package org.example;

import org.jgrapht.Graph;
import org.jgrapht.generate.PruferTreeGenerator;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.util.SupplierUtil;

import java.util.*;
import java.util.function.Supplier;
import org.example.Node.Type;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Main {

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        int count =0;
        int count2 =0;
        int count3 =0;
        int count4=0;
        double max =0;
        double max2 =0;
        double max3 =0;
        double max4=0;
        double sum=0;
        double sum_cost =0;
        double sum2=0;
        double sum2_cost=0;
        double sum3=0;
        double sum3_cost=0;
        double sum4=0;
        
        
        double sum_compare =0;
        double sum2_compare =0;
        double sum3_compare =0;
    
        int max_platos =0;
        int max_platos_seed =0;
        int min_height =0;
        int n=0;
        while(n==0)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose number of nodes. (approximately, varies for each different run, with different seed)");
        
        try{
        n = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter positive integer value.");
        }
        }
        
        int s=0;
        while(s==0)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose number of resources");
        
        try{
        s = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter positive integer value.");
        }
        }
        
        
        int t=0;
        while(t==0)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose capacity of each resource");
       
        try{
        t = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter positive integer value.");
        }
        }
        
        double count_size =0;
        String filename = "output" + ".txt";
        PrintWriter pw = new PrintWriter(new FileWriter(filename));
        for(int whatever=0;whatever<1000;whatever++)
        {
        
        
        //int seed =5335; // case 2
        int seed = 12; // case 1
        //int seed =150; // case 3
        //int seed =226;
        Supplier<TreeNode> vSupplier = new Supplier<>() {
            private int id = 0;

            @Override
            public TreeNode get() {
                return new TreeNode(id++);
            }
        };

        // Create the graph object
        Graph<TreeNode, DefaultEdge> graph =
                new SimpleGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);

        // Create the CompleteGraphGenerator object
        PruferTreeGenerator<TreeNode, DefaultEdge> generator =
                new PruferTreeGenerator<>(n, seed);

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        generator.generateGraph(graph);

        List<Node> nodes = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            nodes.add(new Node(i));
        }

        Iterator<TreeNode> iter = new DepthFirstIterator<>(graph);
        boolean[] visited = new boolean[n];
        while (iter.hasNext()) {
            TreeNode root = iter.next();
            Node node = nodes.get(root.id());
            visited[root.id()] = true;
            for (DefaultEdge e : graph.edgesOf(root)) {
                if (graph.getEdgeSource(e) != root && !visited[graph.getEdgeSource(e).id()]) {
                    node.addChild(nodes.get(graph.getEdgeSource(e).id()));
                }
                if (graph.getEdgeTarget(e) != root && !visited[graph.getEdgeTarget(e).id()]) {
                    node.addChild(nodes.get(graph.getEdgeTarget(e).id()));
                }
            }
        }
        
        int extra = n;
        for (int i = 0; i < n; i++) {
            if (nodes.get(i).getChildren().size() == 1) {
                Node new_node = new Node(extra++);
                nodes.get(i).addChild(new_node);
                nodes.add(new_node);
            }
        }
        
        Node test = nodes.get(0).getChildren().get(0);
        
        
       
        

        
        Node root = nodes.get(0);
        Type type = Type.Sequence;
        root.setType(type);
      
        root.setParent(root);
        for (int i = 0; i < n; i++) {
            if (nodes.get(i).getChildren().size() != 0) {
                List<Node> children = nodes.get(i).getChildren();
                for(int j=0;j<children.size();j++)
                {
                    children.get(j).setParent(nodes.get(i));
                }
            }
        }
        
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getChildren().size() == 0 & nodes.get(i).id()!=0) {
                nodes.get(i).setType(Type.Leaf);
            }
           
            if (nodes.get(i).getChildren().size() != 0 & nodes.get(i).getParent().id()==0 & nodes.get(i).id()!=0) {
                nodes.get(i).setType(Type.XOR);
            }
            
           
            
            if (nodes.get(i).getChildren().size() != 0 & nodes.get(i).getParent().id()!=0 & nodes.get(i).id()!=0) {
                
                int maximum = 2;
                int min= 0;
                Random rn = new Random();
                int gateway_type = (int) ((Math.random() * (maximum - min)) + min);
              
                if(gateway_type==1)
                {
                 
                nodes.get(i).setType(Type.Sequence);
                }
                else
                {
                nodes.get(i).setType(Type.XOR);  
                }
            }
            
        }
        
        
        for(int i = 0; i < nodes.size(); i++) {
           if (nodes.get(i).getChildren().size() != 0 & nodes.get(i).getParent().type==type.XOR_AND & nodes.get(i).id()!=0) {
                nodes.get(i).setType(Type.Sequence);
            }
           
           if(nodes.get(i).type==Type.Sequence && nodes.get(i).getParent().type==Type.Sequence && i!=0)
           {
               
           }
           
           
            
        }
        
        for(int i = 0; i < nodes.size(); i++){
           
           
           if(nodes.get(i).type==Type.XOR && nodes.get(i).getChildren().size()==1)
           {
                Node new_node = new Node(nodes.size());
                
                
           }
           
           
            
        }
        
         
        
      
       
       for(int i = 0; i < nodes.size(); i++) {
            int maximum = 10;
             int min = 2;
             Random random = new Random();
             int r = random.nextInt(maximum - min + 1) + min;
             nodes.get(i).setCost(r);
             
             r = random.nextInt(maximum - min + 1) + min;
             nodes.get(i).setTime(r);
           if (nodes.get(i).getChildren().size() != 0)
           {
             
             HashMap<Integer,Node> children = new HashMap<>();
             for(Integer j=0;j< nodes.get(i).getChildren().size();j++)
             {
               children.put(j, nodes.get(i).getChildren().get(j));
               
             }
             nodes.get(i).neighbors = children;
           }
           
       
           
           
          
           
        
        }
        
       for(int i = 0; i < nodes.size(); i++) {
           
           
           if(nodes.get(i).type==Type.XOR || nodes.get(i).type==Type.XOR_AND )
           {
               nodes.get(i).initializeProbability();
                
           }
        }
       
        String[] profile_names = new String[6]; 
        profile_names[0] = "Realistic";
        profile_names[1] = "Investigative";
        profile_names[2] = "Artistic";
        profile_names[3] = "Social";
        profile_names[4] = "Enterprising";
        profile_names[5] = "Conventional";
        
        Tree tree = new Tree(nodes.get(0));
        HashMap<Integer,Resource> resource_names = new HashMap();
        HashMap<String,Resource> resource_names2 = new HashMap();
        HashMap<String,Resource> resources = new HashMap<>();
       
        for(int i=0;i<s;i++)
        {
            Resource resource_template = new Resource(toAlphabetic(i),toAlphabetic(i));
             createProfile(profile_names,resource_template);
             resource_template.setCapacity(t);
             resource_names.put(i, resource_template);
             resource_names2.put(resource_template.Name,resource_template);
        }
        
        HashMap<String,Integer> ric_costs = initializeRIC(resource_names);
        tree.resources = resource_names2;
        tree.setActivities(nodes);
       
        ArrayList<String[]> temp = new ArrayList<>();
        
        System.out.println(nodes.size() + " megethos");
     for(int i = 0; i < nodes.size(); i++) {
            
           if (nodes.get(i).getChildren().size() != 0)
           {
             
             HashMap<Integer,Node> children = new HashMap<>();
             for(Integer j=0;j< nodes.get(i).getChildren().size();j++)
             {
               children.put(j, nodes.get(i).getChildren().get(j));
               
             }
             nodes.get(i).neighbors = children;
           }
           
           
        
        }
         
         if(nodes.get(0).getNeighbors().get(0).type!=type.Leaf)
        {
            Node start = new Node(nodes.size());
            start.setType(Type.Leaf);
            start.setParent(nodes.get(0));
            for(int f = nodes.get(0).getChildren().size(); f>0;f--)
            {
                Node neighbors_node = nodes.get(0).getChildren().get(f-1);
                nodes.get(0).neighbors.remove(f-1);
                nodes.get(0).neighbors.put(f, neighbors_node);
            }
           nodes.get(0).neighbors.put(0,start);
           start.start = true;
           nodes.add(start);
           nodes.get(0).getChildren().add(start);
        }
         else
         {
             nodes.get(0).getNeighbors().get(0).start=true;
         }
        
         
         
        if(nodes.get(0).getNeighbors().get(nodes.get(0).getNeighbors().size()-1).type!=type.Leaf)
        {
            Node end = new Node(nodes.size());
            end.setType(Type.Leaf);
            end.setParent(nodes.get(0));
            nodes.get(0).neighbors.put(nodes.get(0).neighbors.size(), end);
            nodes.get(0).getChildren().add(end);
            end.end = true;
            nodes.add(end);
            
        }
        else
        {
            nodes.get(0).getNeighbors().get(nodes.get(0).getNeighbors().size()-1).end=true;
        }
       
      HashSet<String> constraints_precedence = initializeConstraints(nodes);
      
      if(constraintCheck(nodes.get(0).getChildren().get(0),nodes.get(0).getChildren().get(nodes.get(0).getChildren().size()-1),constraints_precedence))
      {
          
      }
      HashSet<String> eligible = constraintPruning(nodes,constraints_precedence);
       createMapping(resource_names,nodes);
        initializeTransitionPairs(nodes);
        
       
       
        if(tree.root.neighbors.size()>3)
        {
           
        }
        
        if(nodes.get(0).getChildren().size()>max_platos)
        {
            
            max_platos = nodes.get(0).getChildren().size();
            max_platos_seed =seed;
            min_height = getHeight(nodes.get(0));
           
            
        }
        count_size += nodes.size();
        initializeCommunication(nodes);
        Tree clone = tree.Copy();
        Tree clone2 = tree.Copy();
        Tree clone3 = tree.Copy();
        clone.activities = clone.getAllNodes();
        clone2.activities = clone2.getAllNodes();
        clone3.activities = clone3.getAllNodes();
        HashMap<Integer,Node> tree_nodes = new HashMap<>();
        HashMap<Integer,Node> clone1_nodes = new HashMap<>();
        HashMap<Integer,Node> clone2_nodes = new HashMap<>();
        HashMap<Integer,Node> clone3_nodes = new HashMap<>();
        for(int p=0;p<nodes.size();p++)
        {
            tree_nodes.put(nodes.get(p).id(), nodes.get(p));
        }
        
        for(int p=0;p<nodes.size();p++)
        {
            nodes.get(p).activities = tree_nodes;
        }
        
        for(int p=0;p<clone.activities.size();p++)
        {
            clone1_nodes.put(clone.activities.get(p).id(), clone.activities.get(p));
        }
        
        for(int p=0;p<clone.activities.size();p++)
        {
            clone.activities.get(p).activities = clone1_nodes;
        }
        
        for(int p=0;p<clone2.activities.size();p++)
        {
            clone2_nodes.put(clone2.activities.get(p).id(), clone2.activities.get(p));
        }
        
        for(int p=0;p<clone2.activities.size();p++)
        {
            clone2.activities.get(p).activities = clone2_nodes;
        }
        
        for(int p=0;p<clone3.activities.size();p++)
        {
            clone3_nodes.put(clone3.activities.get(p).id(), clone3.activities.get(p));
        }
        
        for(int p=0;p<clone3.activities.size();p++)
        {
            clone3.activities.get(p).activities = clone3_nodes;
        }
        
       
        
       
        reAllocate(tree,nodes.get(0),profile_names,resources,resource_names2);
        
        
        
        
        for(String pair : eligible)
        {
            String[] nodes_pair = pair.split("-");
            
        }
        
      
        
       
      advancedAllocate(clone.activities,clone,clone.root,profile_names,resources,resource_names2,eligible);
        
        
        Double cost1 =  tree.costFunction();
        Double time1  = tree.timeFunction(ric_costs);
        
       
        
         advancedAllocatePromote(clone2.activities,clone2,clone2.root,profile_names,resources,resource_names2,eligible,1);
         advancedAllocatePromote(clone3.activities,clone3,clone3.root,profile_names,resources,resource_names2,eligible,0.5);
        
       
        
       
     
      
       
        Double cost1_clone =  clone.costFunction();
        Double time1_clone = clone.timeFunction(ric_costs);
        
        Double cost1_clone2 = clone2.costFunction();
        Double time1_clone2 = clone2.timeFunction(ric_costs);
        
        
        Double cost1_clone3 = clone3.costFunction();
        Double time1_clone3 = clone3.timeFunction(ric_costs);
       
         
        
       identifySequences(nodes);
       
      
       
       
       identifySequences(clone.getAllNodes());
       identifySequences(clone2.getAllNodes());
       identifySequences(clone3.getAllNodes());
       
       Double cost2 = tree.costFunction();
       Double time2 = tree.timeFunction(ric_costs);
      
      Double cost2_clone = clone.costFunction();
       Double time2_clone = clone.timeFunction(ric_costs);
       
       Double cost2_clone2 = clone2.costFunction();
       Double time2_clone2 = clone2.timeFunction(ric_costs);
       
       Double cost2_clone3 = clone3.costFunction();
       Double time2_clone3 = clone3.timeFunction(ric_costs);
       
       
           
           
          
       
        
        
        
       
        
        
       
       
       
        if(time2<time1)
        {
            
            if(nodes.get(0).getChildren().size()>3)
            {
            
            }
           
          
            double diff = (time1-time2)/time1;
            double diff_cost = (cost2-cost1)/cost1;
            if(diff>max)
            {
                max=  diff;
            }
            
           
            if(time2<time1)
            {
                count++;
            }
            sum += diff;
           
       }
      
        
        if(time2_clone<time1)
        {
            
           
           double diff2 = (time1-time2_clone)/time1;
           double diff_cost2 = (cost2_clone-cost1_clone)/cost1_clone;
            if(diff2>max2)
            {
                max2=  diff2;
                
                
               
            }
            
            
             sum2+= diff2;
            count2++;
       }
      
       
       if(time2_clone2<time1 )
        {
           
           
           double diff3 = (time1-time2_clone2)/time1;
            if(diff3>max3)
            {
                max3=  diff3;
            }
            
            
             sum3+= diff3;
            count3++;
       }
       
       
       if(time2_clone3<time1)
        {
           
           
           double diff4 = (time1-time2_clone3)/time1;
            if(diff4>max4)
            {
                max4=  diff4;
            }
            
            
             sum4+= diff4;
            count4++;
       }
       
       if(time2_clone<time1_clone && time2>=time1)
       {
           
       }
       sum_cost += cost1;
       sum2_cost += cost1_clone;
       sum3_cost += cost1_clone2;
       
       sum_compare += time1;
       sum2_compare += time1_clone;
       sum3_compare += time1_clone2;
       
      
       
        
      pw.println(seed + "," + cost1 +"," + time1 + "," + cost2 + "," + time2 + "," +  cost1_clone + "," + time1_clone + "," +  cost2_clone + "," + time2_clone + "," + "," + cost1_clone2 + "," + time1_clone2 + "," + cost2_clone2 + "," + time2_clone2 + "," + cost1_clone3 + "," + time1_clone3 + "," + cost2_clone3 + "," + time2_clone3);
      

    }
        
       System.out.println(count + " , " + count2+ " , " + count3 + " , " +  count4);
      
       System.out.println(sum/count+ " , " + sum2/count2 + " , " + sum3/count3 + " , " + sum4/count4);
       
       System.out.println("max: " + max + " , " + max2  + " , " + max3 + ", " + max4);
      
       System.out.println(count_size/1000);
       System.out.println(max_platos + " , " + max_platos_seed + " height" + min_height);
       pw.close();
    }
   
    
   public static void swapResource(Node node1, Node node2)
    {
        Resource resource1 = node1.getResource();
        Resource resource2 = node2.getResource();
        node1.setResource(resource2);
        node2.setResource(resource1);
        resource2.Allocate(node1);
        resource1.Allocate(node2);
    }
    
    
    public static void firstAllocation(Node sequence3,HashMap<Resource,Boolean> resource_availability,HashMap<Resource,HashMap<Node,Double>> preference_collection, HashMap<Integer,Resource> resource_collection)
    {
      HashMap<Integer,Node> neighbors = sequence3.getNeighbors();
      Iterator it = neighbors.entrySet().iterator();
      while(it.hasNext())
      {
          Map.Entry<Integer,Node> pair =(Map.Entry<Integer,Node>) it.next();
          Node pair_node = pair.getValue();
          
          if(pair_node.type==Type.Leaf && pair_node.Name.equals("Blank path")!=true)
          {
          
          boolean allocation = false;
          int min = 0;
          int max = resource_collection.size()-1;
          
          
          while(allocation==false)
          {
              int r = new  Random().nextInt(max - min + 1) + min;
              
              Resource candidate = resource_collection.get(r);
             
              if(resource_availability.get(candidate)==true  && preference_collection.get(candidate).containsKey(pair_node))
              {
                  allocation = true;
                  resource_availability.put(candidate,false);
                  candidate.changeAvailability(false);
                  candidate.Allocate(pair_node);
                  pair_node.setResource(candidate);
              }
              
          }
          
          }
          else if(pair_node.type==Type.Leaf)
          {
              
             continue;         
          }
          else
          {
            firstAllocation(pair_node,resource_availability,preference_collection,resource_collection);  
          }
          
          
      }
        
        
    }
    
    
    public static LinkedHashSet<Node> reAllocate(Tree tree, Node sequence3, String[] profiles, HashMap<String,Resource> resources, HashMap<String,Resource> resource_names)
    {
        
      HashMap<Integer,Node> neighbors = sequence3.getNeighbors();
      LinkedHashSet<Node> leaves = new LinkedHashSet();
      
      Iterator it = neighbors.entrySet().iterator();
      int i=0;
      while(it.hasNext())
      {
          Map.Entry<Integer,Node> pair =(Map.Entry<Integer,Node>) it.next();
          Node pair_node = pair.getValue();
          if(pair_node.type==Type.Leaf && pair_node.Name.equals("Blank path")!=true)
          {
              leaves.add(pair_node);
              i++;
          }
          if(pair_node.type!=Type.Leaf)
          {
              HashMap<Integer,Node> neighbors2 = pair_node.getNeighbors();
              Iterator it2 = neighbors2.entrySet().iterator();
              getLeaves(pair_node.neighbors,leaves);
          }
      }
      
      int counter =0;
      
      for (Node n : leaves)
      {
          HashMap<String,Double> node_profiles = new HashMap();
          for(int j=0;j< profiles.length;j++)
          {
              Random rd = new Random();
              node_profiles.put(profiles[j], rd.nextDouble());
          }
          n.setProfile(node_profiles);
      }
      HashMap<Node,HashMap<Resource,Double>> scores = new HashMap();
      for (Node n : leaves)
      {
          
          HashMap<Resource,Double> score = new HashMap();
          
          Iterator it_resource = resource_names.entrySet().iterator();
          int j=0;
          while(it_resource.hasNext())
          {
              
             Map.Entry<Integer,Resource> resource_pair = (Map.Entry<Integer,Resource>) it_resource.next();
             if(n.eligible_resources.contains(resource_pair.getValue().Name))
             {
             HashMap<String,Double> profile1 = n.profile;
             HashMap<String,Double> profile2 = resource_pair.getValue().profile;
             Double sum =0.0;
             Iterator profile_it = profile1.entrySet().iterator();
             while(profile_it.hasNext())
             {
                 Map.Entry<String,Double> profile_pair1 = (Map.Entry<String,Double>) profile_it.next();
                 Double score1 = profile_pair1.getValue();
                 Double score2 = profile2.get(profile_pair1.getKey());
                 sum += Math.abs(score1-score2);
             }
             score.put(resource_pair.getValue(), sum/resource_names.size());
            
          
             
          scores.put(n, score);
             }
             else
             {
                 continue;
             }
          }
          
          
      }
      
      for (Node n : leaves)
      {
          HashMap<Resource,Double> score = scores.get(n);
          Resource index = getMinValue(score);
         
       
         if( resources.containsKey(n.Name))
                  {
                     
                  }
         if(index==null)
         {
            System.out.println("allocation aborted");
            
             LinkedHashSet<Node> leaves_new = rerank2(leaves,n);
             reAllocateNested(leaves_new,profiles,resources,resource_names);
             
             return null;
         }
         if(index.current_capacity>0)
         {
           
           resources.put(n.Name, index);
          
           n.setResource(index);
           index.Allocate(n);
          
         }
         else
         {
            
         }
           
      }
      
    
      rerank(leaves);
      return leaves;
        
    }
    
    
    public static LinkedHashSet<Node> advancedAllocate(List<Node> nodes,Tree tree, Node sequence3, String[] profiles, HashMap<String,Resource> resources, HashMap<String,Resource> resource_names, HashSet<String> eligible)
    {
        
      HashMap<Integer,Node> neighbors = sequence3.getNeighbors();
      LinkedHashSet<Node> leaves = new LinkedHashSet();
      
      Iterator it = neighbors.entrySet().iterator();
      int i=0;
      while(it.hasNext())
      {
          Map.Entry<Integer,Node> pair =(Map.Entry<Integer,Node>) it.next();
          Node pair_node = pair.getValue();
          if(pair_node.type==Type.Leaf && pair_node.Name.equals("Blank path")!=true)
          {
              leaves.add(pair_node);
              i++;
          }
          if(pair_node.type!=Type.Leaf)
          {
              HashMap<Integer,Node> neighbors2 = pair_node.getNeighbors();
              Iterator it2 = neighbors2.entrySet().iterator();
              getLeaves(pair_node.neighbors,leaves);
          }
      }
      
      int counter =0;
      
      for (Node n : leaves)
      {
          HashMap<String,Double> node_profiles = new HashMap();
          for(int j=0;j< profiles.length;j++)
          {
              Random rd = new Random();
              node_profiles.put(profiles[j], rd.nextDouble());
          }
          n.setProfile(node_profiles);
      }
      
      HashSet<Node> allocated = new HashSet<>();
      
      for(String pair : eligible)
      {
          String[] pair_split = pair.split("-");
         
          Node first = nodes.get(Integer.parseInt(pair_split[0]));
          Node second = nodes.get(Integer.parseInt(pair_split[1]));
          if(parallelAllocation(first,second,resource_names,tree)==true)
          {
              allocated.add(second);
              allocated.add(first);
              
              
              leaves.remove(first);
              leaves.remove(second);
          }
      }
      
      
      
      HashMap<Node,HashMap<Resource,Double>> scores = new HashMap();
      for (Node n : leaves)
      {
          
          HashMap<Resource,Double> score = new HashMap();
          
          Iterator it_resource = resource_names.entrySet().iterator();
          int j=0;
          while(it_resource.hasNext())
          {
              
             Map.Entry<Integer,Resource> resource_pair = (Map.Entry<Integer,Resource>) it_resource.next();
             if(n.eligible_resources.contains(resource_pair.getValue().Name))
             {
             HashMap<String,Double> profile1 = n.profile;
             HashMap<String,Double> profile2 = resource_pair.getValue().profile;
             Double sum =0.0;
             Iterator profile_it = profile1.entrySet().iterator();
             while(profile_it.hasNext())
             {
                 Map.Entry<String,Double> profile_pair1 = (Map.Entry<String,Double>) profile_it.next();
                 Double score1 = profile_pair1.getValue();
                 Double score2 = profile2.get(profile_pair1.getKey());
                 sum += Math.abs(score1-score2);
             }
             score.put(resource_pair.getValue(), sum/resource_names.size());
                      
             
          scores.put(n, score);
             }
             else
             {
                 continue;
             }
          }
          
          
      }
      
      for (Node n : leaves)
      {
          HashMap<Resource,Double> score = scores.get(n);
          Resource index = getMinValue(score);
         
         if( resources.containsKey(n.Name))
                  {
                    
                  }
         if(index==null)
         {
            
            
             LinkedHashSet<Node> leaves_new = rerank2(leaves,n);
             reAllocateNested(leaves_new,profiles,resources,resource_names);
             
             return null;
         }
         if(index.current_capacity>0)
         {
          
           resources.put(n.Name, index);
          
           n.setResource(index);
           
           index.Allocate(n);
         }
         else
         {
            
         }
           
      }
      
   
     
     for(Node n : allocated)
     {
         
     }
      rerank(leaves);
      return leaves;
      
      
    }
    
    
    
    public static LinkedHashSet<Node> advancedAllocatePromote(List<Node> nodes,Tree tree, Node sequence3, String[] profiles, HashMap<String,Resource> resources, HashMap<String,Resource> resource_names, HashSet<String> eligible, double pfactor)
    {
        
      HashMap<Integer,Node> neighbors = sequence3.getNeighbors();
      LinkedHashSet<Node> leaves = new LinkedHashSet();
      
      Iterator it = neighbors.entrySet().iterator();
      int i=0;
      while(it.hasNext())
      {
          Map.Entry<Integer,Node> pair =(Map.Entry<Integer,Node>) it.next();
          Node pair_node = pair.getValue();
          if(pair_node.type==Type.Leaf && pair_node.Name.equals("Blank path")!=true)
          {
              leaves.add(pair_node);
              i++;
          }
          if(pair_node.type!=Type.Leaf)
          {
              HashMap<Integer,Node> neighbors2 = pair_node.getNeighbors();
              Iterator it2 = neighbors2.entrySet().iterator();
              getLeaves(pair_node.neighbors,leaves);
          }
      }
      
      int counter =0;
      
      for (Node n : leaves)
      {
          HashMap<String,Double> node_profiles = new HashMap();
          for(int j=0;j< profiles.length;j++)
          {
              Random rd = new Random();
              node_profiles.put(profiles[j], rd.nextDouble());
          }
          n.setProfile(node_profiles);
      }
      
      HashSet<Node> allocated = new HashSet<>();
      HashMap<Node,HashMap<Resource,Double>> scores = new HashMap();
      for (Node n : leaves)
      {
          
          HashMap<Resource,Double> score = new HashMap();
          
          Iterator it_resource = resource_names.entrySet().iterator();
          int j=0;
          while(it_resource.hasNext())
          {
              
             Map.Entry<Integer,Resource> resource_pair = (Map.Entry<Integer,Resource>) it_resource.next();
             if(n.eligible_resources.contains(resource_pair.getValue().Name))
             {
             HashMap<String,Double> profile1 = n.profile;
             HashMap<String,Double> profile2 = resource_pair.getValue().profile;
             Double sum =0.0;
             Iterator profile_it = profile1.entrySet().iterator();
             while(profile_it.hasNext())
             {
                 Map.Entry<String,Double> profile_pair1 = (Map.Entry<String,Double>) profile_it.next();
                 Double score1 = profile_pair1.getValue();
                 Double score2 = profile2.get(profile_pair1.getKey());
                 sum += Math.abs(score1-score2);
             }
             score.put(resource_pair.getValue(), sum/resource_names.size());
             
          
             
          scores.put(n, score);
             }
             else
             {
                 continue;
             }
          }
          
          
      }
      
      for(String pair : eligible)
      {
          String[] pair_split = pair.split("-");
          Node first = nodes.get(Integer.parseInt(pair_split[0]));
          Node second = nodes.get(Integer.parseInt(pair_split[1]));
          HashSet<String> first_resources = first.getEligibleResources();
          HashSet<String> second_resources = second.getEligibleResources();
          
          
          for(Node first_child : first.getChildrenLeaves())
          {
              HashMap<Resource,Double> score = scores.get(first_child);
              Iterator it_score = score.entrySet().iterator();
              while(it_score.hasNext())
              {
                  Map.Entry<Resource,Double>  score_pair = (Map.Entry<Resource,Double>) it_score.next();
                  if(second_resources.contains(score_pair.getKey().Name))
                  {
                      Double s = score_pair.getValue();
                      
                      s -= pfactor;
                      score.replace(score_pair.getKey(), s);
                      
                  }
              }
              
          }
          
          
          for(Node second_child : second.getChildrenLeaves())
          {
              HashMap<Resource,Double> score = scores.get(second_child);
              Iterator it_score = score.entrySet().iterator();
              while(it_score.hasNext())
              {
                  Map.Entry<Resource,Double>  score_pair = (Map.Entry<Resource,Double>) it_score.next();
                  if(second_resources.contains(score_pair.getKey().Name))
                  {
                      Double s = score_pair.getValue();
                      s -= 0.5;
                      score.replace(score_pair.getKey(), s);
                      
                  }
              }
              
          }
          
      }
      
      
      
      
      
      for (Node n : leaves)
      {
          HashMap<Resource,Double> score = scores.get(n);
          Resource index = getMinValue(score);
        
         if( resources.containsKey(n.Name))
                  {
                    
                  }
         if(index==null)
         {
            
            
             LinkedHashSet<Node> leaves_new = rerank2(leaves,n);
             reAllocateNested(leaves_new,profiles,resources,resource_names);
             
             return null;
         }
         if(index.current_capacity>0)
         {
           
           resources.put(n.Name, index);
          
           n.setResource(index);
           
           index.Allocate(n);
         }
         else
         {
            
         }
           
      }
      
     
     
     for(Node n : allocated)
     {
        
     }
      rerank(leaves);
      return leaves;
      
      
    }
    
    public static void initializeTransitionPairs(List<Node> nodes)
    {
        List<Node> leaves = new ArrayList<>();
        for(int i=0;i<nodes.size();i++)
        {
            if(nodes.get(i).type==Type.Leaf)
            {
                leaves.add(nodes.get(i));
            }
        }
        
        for(int i=0;i<leaves.size();i++)
        {
            List<Node> leaves_temp = new ArrayList<>(leaves);
            int min = 1;
            int max = leaves_temp.size();
            Random rand = new Random();
            int randomNum = rand.nextInt((max - min) + 1) + min;
            List<Node> leaves_subset = leaves_temp.subList(0, randomNum);
            List<Integer> leaves_transition = new ArrayList();
            for(Node leaf : leaves_subset)
            {
                leaves_transition.add(leaf.id());
            }
            leaves.get(i).setTransitions(leaves_transition);
        }
    }
   
    
    
    public static boolean parallelAllocation(Node first, Node two, HashMap<String,Resource> resource_names, Tree tree)
    {
        List<Node>  eligible = first.getChildrenLeaves();
        eligible.addAll(two.getChildrenLeaves());
        
        Collections.sort(eligible,new Comparator<Node>() {
    @Override
    public int compare(Node n1, Node n2) {
        return Double.compare(n1.eligible_resources.size(), n2.eligible_resources.size());
    }
    
    
});
        HashSet<Resource> first_allocation = new HashSet<>();
         HashSet<Resource> second_allocation = new HashSet<>();
         int count = 0;
         
         ArrayList<ArrayList<String>> resource_sets = new ArrayList<>();
         ArrayList<ArrayList<String>> resource_sets_second = new ArrayList<>();
         for(Node n : first.getChildrenLeaves())
         {
             n.getEligibleResources();
             ArrayList<String> temp = new ArrayList(n.getEligibleResources());
             ArrayList<String> temp2 = new ArrayList<>();
             for(String r : temp)
             {
                 temp2.add(r);
             }
             resource_sets.add(temp2);
         }
         
         for(Node n : two.getChildrenLeaves())
         {
            n.getEligibleResources();
             ArrayList<String> temp = new ArrayList(n.getEligibleResources());
             ArrayList<String> temp2 = new ArrayList<>();
             for(String r : temp)
             {
                 temp2.add(r);
             }
             resource_sets_second.add(temp2);
         }
         
        
        
         ArrayList<ArrayList<String>> result = generateCombinations(resource_sets, 0);
         ArrayList<ArrayList<String>> result2 = generateCombinations(resource_sets_second, 0);
         ArrayList<String> allocation_first = new ArrayList<>();
         ArrayList<String> allocation_second = new ArrayList<>();
         for(ArrayList<String> first_temp : result)
         {
             int original_size = first_temp.size();
             for(ArrayList<String> second_temp : result2)
         {
             ArrayList<String> first_temp_copy = new ArrayList<>();
             
             for(String s : first_temp)
             {
                 first_temp_copy.add(s);
             }
              first_temp_copy.retainAll(second_temp);
              
              int new_size = first_temp_copy.size();
              if(new_size==0)
              {
                allocation_first = first_temp;
                allocation_second = second_temp;
                count+=1;
                break;
              }
              
             
             
         }
         }
         int i =0;
         if(count==0)
         {
             return false;
         }
         for(Node n : first.getChildrenLeaves())
         {
             String resource_name = allocation_first.get(i);
             Resource r = tree.resources.get(resource_name);
             n.setResource(r);
             r.Allocate(n);
             i++;
         }
         i=0;
         for(Node n : two.getChildrenLeaves())
         {
             String resource_name = allocation_second.get(i);
             Resource r = tree.resources.get(resource_name);
             n.setResource(r);
             r.Allocate(n);
             i++;
         }
         
         if(count==1)
         {
            
             return true;
         }
         
         
         return false;
        
    }
    
    
    public static ArrayList<ArrayList<String>> generateCombinations(ArrayList<ArrayList<String>> sets, int index) {
        if (index == sets.size()) {
                        ArrayList<ArrayList<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        } else {
            ArrayList<ArrayList<String>> result = new ArrayList<>();
            ArrayList<ArrayList<String>> suffixes = generateCombinations(sets, index + 1);
            for (String element : sets.get(index)) {
                for (ArrayList<String> suffix : suffixes) {
                    ArrayList<String> combination = new ArrayList<>();
                    combination.add(element);
                    combination.addAll(suffix);
                    result.add(combination);
                    
                }
            }
            return result;
        }
    }

    
    
    public static HashSet<String> constraintPruning(List<Node> nodes, HashSet<String> constraints)
    {
        List<Node> sequences = new ArrayList<Node>();
        HashSet<String> eligible = new HashSet<>();
        for(int i=0;i<nodes.size();i++)
        {
            if(nodes.get(i).type==Type.Sequence && i!=0)
            {
                sequences.add(nodes.get(i));
            }
        }
        
        for(int i=0;i<sequences.size();i++)
        {
          List<Node> children =  nodes.get(i).getChildren();
          for(int j=0;j<children.size()-1;j++)
          {
             Node first = children.get(j);
             for(int k=j+1;k<children.size();k++)
             {
                 Node second = children.get(k);
                 if(constraintCheck(first,second,constraints)==true)
                 {
                    String constraint = "";
                    constraint = constraint.concat(Integer.toString(first.id()));
                    constraint = constraint.concat("-");
                    constraint = constraint.concat(Integer.toString(second.id()));
                    eligible.add(constraint);
                 }
                 
             }
          }
          
        }
        return eligible;
    }
    
    
    public static boolean constraintCheck(Node first, Node second, HashSet<String> constraints)
    {
        List<Node> first_leaves = first.getChildrenLeaves();
        List<Node> second_leaves = second.getChildrenLeaves();
        
        for(int l=0;l<first_leaves.size();l++)
        {
           
            for(int m=0;m<second_leaves.size();m++)
            {
                String constraint = "";
                constraint = constraint.concat(Integer.toString(first_leaves.get(l).id()));
                constraint = constraint.concat("-");
                constraint = constraint.concat(Integer.toString(second_leaves.get(m).id()));
                if(constraints.contains(constraint))
                  {
                    if(first_leaves.get(l).start==true && second_leaves.get(m).end==true)
                    {
                      
                    }
                    
                    if(first_leaves.get(l).end==true && second_leaves.get(m).start==true)
                    {
                      
                    }
                   return false;
                  }
                  else
                  {
                            
                  }
             }
        }
        
        return true;
    }
    
    public static boolean resourceCheck(Node first, Node second) 
    {
        List<Node> first_leaves = first.getChildrenLeaves();
        List<Node> second_leaves = second.getChildrenLeaves();
        
        for(int i=0;i<first_leaves.size();i++)
        {
            Node first_leaf = first_leaves.get(i);
            for(int j=0;j<second_leaves.size();j++)
            {
                Node second_leaf = second_leaves.get(j);
                HashSet<Resource> first_resource = (HashSet<Resource>) first_leaf.eligible_resources.clone();
                HashSet<Resource> second_resource = (HashSet<Resource>) second_leaf.eligible_resources.clone();
                first_resource.retainAll(second_resource);
                if(first_resource.size()!= first_leaf.eligible_resources.size() && first_resource.size()!= second_leaf.eligible_resources.size())
                {
                    
                }
                else
                {
                    return false;
                }
            }
        }
       
        
        
         return true;
         
         
        
         
             
        
        
        
    }
    
    public static LinkedHashSet<Node> reAllocateNested(LinkedHashSet<Node> leaves, String[] profiles, HashMap<String,Resource> resources, HashMap<String,Resource> resource_names)
    {
        
      Iterator it_names = resource_names.entrySet().iterator();
      while(it_names.hasNext())
      {
          Map.Entry<Integer,Resource> pair_clean = (Map.Entry<Integer,Resource>)it_names.next();
          pair_clean.getValue().resetCapacity();
      }
      
      for (Node n : leaves)
      {
          HashMap<String,Double> node_profiles = new HashMap();
          for(int j=0;j< profiles.length;j++)
          {
              Random rd = new Random();
              node_profiles.put(profiles[j], rd.nextDouble());
          }
          n.setProfile(node_profiles);
      }
      HashMap<Node,HashMap<Resource,Double>> scores = new HashMap();
      for (Node n : leaves)
      {
          
          HashMap<Resource,Double> score = new HashMap();
          
          Iterator it_resource = resource_names.entrySet().iterator();
          int j=0;
          while(it_resource.hasNext())
          {
              
             Map.Entry<Integer,Resource> resource_pair = (Map.Entry<Integer,Resource>) it_resource.next();
             if(n.eligible_resources.contains(resource_pair.getValue().Name))
             {
             HashMap<String,Double> profile1 = n.profile;
             HashMap<String,Double> profile2 = resource_pair.getValue().profile;
             Double sum =0.0;
             Iterator profile_it = profile1.entrySet().iterator();
             while(profile_it.hasNext())
             {
                 Map.Entry<String,Double> profile_pair1 = (Map.Entry<String,Double>) profile_it.next();
                 Double score1 = profile_pair1.getValue();
                 Double score2 = profile2.get(profile_pair1.getKey());
                 sum += Math.abs(score1-score2);
             }
             score.put(resource_pair.getValue(), sum/resource_names.size());
             
          
             
          scores.put(n, score);
             }
             else
             {
                 continue;
             }
          }
          
          
      }
      
      for (Node n : leaves)
      {
          HashMap<Resource,Double> score = scores.get(n);
          Resource index = getMinValue(score);
        
         if( resources.containsKey(n.Name))
                  {
                     
                  }
         if(index==null)
         {
            System.out.println("allocation aborted second");
             LinkedHashSet<Node> leaves_new = rerank2(leaves,n);
            reAllocateNested(leaves_new,profiles,resources,resource_names);
             
             return null;
         }
         if(index.current_capacity>0)
         {
          
           resources.put(n.Name, index);
          
           n.setResource(index);
           index.Allocate(n);
         }
         else
         {
           
         }
           
      }
      
     
      rerank(leaves);
      return leaves;
        
    }
    
     public static String toAlphabetic(int i) {
    if( i<0 ) {
        return "-"+toAlphabetic(-i-1);
    }

    int quot = i/26;
    int rem = i%26;
    char letter = (char)((int)'A' + rem);
    if( quot == 0 ) {
        return ""+letter;
    } else {
        return toAlphabetic(quot-1) + letter;
    }
    
    
    
}
    
    
    
    
    
    public static void getLeaves(HashMap<Integer,Node> neighbors, LinkedHashSet<Node> leaves)
    {
        Iterator it = neighbors.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<Integer,Node> pair =(Map.Entry<Integer,Node>) it.next();
            Node pair_node = pair.getValue();
            if(pair_node.type==Type.Leaf && pair_node.Name.equals("Blank path")!=true)
            {
              
              leaves.add(pair_node);
              
             }
            if(pair_node.type!=Type.Leaf)
           {
               getLeaves(pair_node.neighbors,leaves);
           }
        }
    }
    
    
    
    
    
    public static Resource getMinValue(HashMap<Resource,Double> score)// returns the index of max value of a double array
    {
       Boolean f = true;
       Resource minValue = score.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
       while(f && score.size()>0)
       {
       minValue = score.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
       if(minValue.current_capacity>0)
       {
           f = false;
       }
       else
       {
           score.remove(minValue);
       }
       }
       if(score.size()==0 & minValue.current_capacity==0)
       {
          
           return null;
       }
       
       
       return minValue;
	}
    
    public static void createProfile(String[] profile_names, Resource resource)
    {
        HashMap<String,Double> profiles = new HashMap();
        for(int i=0;i<profile_names.length;i++)
        {
            Random rd = new Random();
            profiles.put(profile_names[i], rd.nextDouble());
        }
        
       resource.setProfile(profiles);
    }
    
    public static void rerank(LinkedHashSet<Node> leaves)
    {
        ArrayList<Node> leaves_array = new ArrayList<>(leaves);
        
        Collections.sort(leaves_array,new Comparator<Node>() {
    @Override
    public int compare(Node n1, Node n2) {
        return Double.compare(n1.cost, n2.cost);
    }
});
        
        
     
    }
    
    
     public static LinkedHashSet rerank2(LinkedHashSet<Node> leaves, Node first)
    {
        leaves.remove(first);
        LinkedHashSet<Node> leaves_local = ( LinkedHashSet<Node> ) leaves.clone();
        ArrayList<Node> leaves_local_array = new ArrayList<>(leaves_local);
        
        Collections.sort(leaves_local_array,new Comparator<Node>() {
    @Override
    public int compare(Node n1, Node n2) {
        return Double.compare(n1.cost, n2.cost);
    }
});
        
        
      LinkedHashSet<Node> leaves_final = new LinkedHashSet();
      leaves_final.add(first);
      leaves_final.addAll(leaves_local_array);
      return leaves_final;
    }
     
    
     
     
    public static void createMapping(HashMap<Integer,Resource> resource_names, List<Node> nodes)
    {
      for(int i=0;i<nodes.size();i++)
      {
          
          if(nodes.get(i).type==Type.Leaf)
          {
          long seed = System.nanoTime();
          ArrayList<Resource> resources = new ArrayList<>(resource_names.values());
          
          Collections.shuffle(resources, new Random(seed));
          int min = 1;
          int max = resource_names.size();
          Random rand = new Random();
          int randomNum = rand.nextInt((max - min) + 1) + min;
          List<Resource> resources_subset = resources.subList(0, randomNum);
          HashSet<Resource> eligible_resources = new HashSet(resources_subset);
          HashSet<String> eligible_resources_names = new HashSet();
          for(Resource r : eligible_resources)
          {
              eligible_resources_names.add(r.Name);
          }
          nodes.get(i).setResources(eligible_resources_names);
          }
          
          
      }
    }
    
    public static void initializeCapacity()
    {
        
    }
    
    
    public static void identifySequences(List<Node> nodes) throws CloneNotSupportedException
    {
        List<Node> sequences = new ArrayList<Node>();
        for(int i=0;i<nodes.size();i++)
        {
            if(nodes.get(i).type==Type.Sequence )//&& i!=0
            {
                sequences.add(nodes.get(i));
            }
        }
       
       for(int i=0;i<sequences.size();i++)
       {
         HashSet<String> constraints = createConstraints(sequences.get(i));
         if(sequences.get(i).getChildren().size()>2)
         {
            
             List<Node> children = sequences.get(i).getChildren();
             for(Node s : children)
             {
                 
             }
         }
         int n = nodes.size();
         
         reseqeunce(sequences.get(i),nodes,constraints);
         if(nodes.size()>n)
         {
             
         }
        
       }
       
      
    }
    
    
    public static void reseqeunce(Node sequence, List<Node> tree, HashSet<String> constraints) throws CloneNotSupportedException
    {
        List<Node> children = sequence.getChildren();
        for(int i=0;i<children.size()-1;i++)
        {
            Node one = children.get(i);
            for(int j=i+1;j<children.size();j++)
            {
                Node two = children.get(j);
                
                
                    
                    
                    HashSet<Resource> resources_one = one.getAllocation();
                    HashSet<Resource> resources_two = two.getAllocation();
                    
                    for(Resource r : resources_one)
                    {
                        
                    }
                    for(Resource r : resources_two)
                    {
                        
                    }
                    resources_two.retainAll(resources_one);
                    if(resources_two.size()==0 && constraintCheck(one,two,constraints) && constraintCheck(two,one,constraints))
                    {
                       
                    
                    if(sequence.getChildren().size()==2)
                    {
                        sequence.setType(Type.AND);
                        
                       
                    }
                    if(j-i==1 && sequence.getChildren().size()>2)
                    {
                       
                      
                       if(one.type==Type.AND || two.type==Type.AND)
                       {
                           
                       }
                        Node new_node = new Node(tree.size());
                       
                       System.out.println(one.Name + " , " + two.Name + " den vgazei noima"); 
                        new_node.setType(Type.AND);
                        new_node.setParent(sequence);
                        List<Node> new_node_children = new ArrayList<Node>();
                        new_node_children.add(one);
                        new_node_children.add(two);
                        new_node.setChildren(new_node_children);
                        HashMap<Integer,Node> neighbors_new_node = new HashMap<Integer,Node>();
                        neighbors_new_node.put(0, one);
                        neighbors_new_node.put(1, two);
                        new_node.setNeighbors(neighbors_new_node);
                        one.setParent(new_node);
                        two.setParent(new_node);
                        children.remove(one);
                        children.remove(two);
                        children.add(new_node);
                        HashMap<Integer,Node> neighbors = sequence.getNeighbors();
                        Iterator it_neighbors = neighbors.entrySet().iterator();
                        int one_index=0;
                        int two_index=0;
                        while(it_neighbors.hasNext())
                        {
                            Map.Entry<Integer,Node> neighbors_pair = (Map.Entry<Integer,Node>) it_neighbors.next();
                            if(neighbors_pair.getValue().Name.equals(one.Name))
                            {
                                one_index = neighbors_pair.getKey();
                            }
                            if(neighbors_pair.getValue().Name.equals(two.Name))
                            {
                                two_index = neighbors_pair.getKey();
                            }
                        }
                       
                        neighbors.remove(one_index);
                        neighbors.remove(two_index);
                        
                        neighbors.put(one_index, new_node);
                        tree.add(new_node);
                        HashMap<Integer,Node> neighbors_replace = (HashMap<Integer,Node>) neighbors.clone();
                        if(i==0)
                        {
                            for(int k=two_index;k<neighbors.size();k++)
                         {
                          Node neighbors_node = neighbors.get(k+1);
                             neighbors.put(k,neighbors_node);
                             neighbors.remove(k+1);
                         
                         }
                        }
                        else
                        {
                        for(int k=two_index;k<neighbors.size();k++)
                         {
                          Node neighbors_node = neighbors.get(k+1);
                             neighbors.put(k,neighbors_node);
                             neighbors.remove(k+1);
                         
                         }
                        }
                        
                        if(sequence.neighbors.size()!=sequence.getChildren().size())
                        {
                           
                        }
                        for(Node test : sequence.neighbors.values())
                        {
                            
                        }
                        for(Node test : sequence.getChildren())
                        {
                             
                        }
                        sequence.setNeighbors(neighbors);
                        
                    }
                    if(j-i>1)
                    {
                         
                        Node alternative = (Node) sequence.clone();
                        moveActivity(alternative,j,i+1);
                       
                         if(one.getParent().type==Type.AND)
                            {
                                //children.remove(two);
                                //one.getParent().neig//hbors.put(one.getParent().neighbors.size(),two);
                                
                            }
                        
                       
                        if(constraintValidity(alternative,constraints))
                        {
                           System.out.println("tony");
                            if(one.getParent().type==Type.AND)
                            {
                               children.remove(two);
                               one.getParent().neighbors.put(one.getParent().neighbors.size(),two);
                                
                            }
                            else
                            {   
                            
                            
                            sequence.setNeighbors(alternative.getNeighbors());
                            Node new_node = new Node(tree.size());
                            new_node.setType(Type.AND);
                            new_node.setParent(sequence);
                            List<Node> new_node_children = new ArrayList<Node>();
                            new_node_children.add(one);
                            new_node_children.add(two);
                            new_node.setChildren(new_node_children);
                            one.setParent(new_node);
                            two.setParent(new_node);
                            children.remove(one);
                            children.remove(two);
                            children.add(new_node);
                            tree.add(new_node);
                            HashMap<Integer,Node> neighbors = sequence.neighbors;
                            int one_index=0;
                            int two_index=0;
                            Iterator it_neighbors = neighbors.entrySet().iterator();
                            while(it_neighbors.hasNext())
                            {
                            Map.Entry<Integer,Node> neighbors_pair = (Map.Entry<Integer,Node>) it_neighbors.next();
                            if(neighbors_pair.getValue().Name.equals(one.Name))
                            {
                                one_index = neighbors_pair.getKey();
                            }
                            if(neighbors_pair.getValue().Name.equals(two.Name))
                            {
                                two_index = neighbors_pair.getKey();
                            }
                            }
                       
                        neighbors.remove(one_index);
                        neighbors.remove(two_index);
                           
                            neighbors.put(one_index,new_node);
                            HashMap<Integer,Node> neighbors_new_node  = new HashMap<>();
                            neighbors_new_node.put(0, one);
                            neighbors_new_node.put(1, two);
                            new_node.setNeighbors(neighbors_new_node);
                            HashMap<Integer,Node> neighbors_replace = (HashMap<Integer,Node>) neighbors.clone();
                            for(int k=two_index;k<neighbors.size();k++)
                         {
                          Node neighbors_node = neighbors.get(k+1);
                             neighbors.put(k,neighbors_node);
                             neighbors.remove(k+1);

                         }
                            neighbors.remove(neighbors.size() -1);
                            
                            sequence.setNeighbors(neighbors);
                        }
                            }
                        
                       
                        
                    }
                    sequence.setChildren(children);
                    }
                
            }
            
            
        }
    }
    
    
    public static HashSet<String> initializeConstraints(List<Node> nodes)
    {
        List<Node> sequences = new ArrayList<Node>();
        HashSet<String> constraints = new HashSet<>();
        for(int i=0;i<nodes.size();i++)
        {
            if(nodes.get(i).type==Type.Sequence)
            {
                sequences.add(nodes.get(i));
            }
        }
        for(int i=0;i<sequences.size();i++)
        {
            constraints.addAll(createConstraints(sequences.get(i)));
            
        }
        return constraints;
    }
    
    public static HashSet<String> createConstraints(Node sequence)
    {
        HashSet<String> precedence = new HashSet<>();
        for(int i=0;i<sequence.getChildren().size()-1;i++){
            
            Node first = sequence.getNeighbors().get(i);
            List<Node> first_children = first.getChildrenLeaves();
            for(int j = i+1;j<sequence.getChildren().size();j++)
            {
                Node second = sequence.getNeighbors().get(j);
                List<Node> second_children = second.getChildrenLeaves();
                for(int k=0;k<first_children.size();k++)
                {
                    for(int l=0;l<second_children.size();l++)
                    {
                        Random r = new Random();
                        if(first_children.get(k).start == true )
                        {
                          
                            String constraint = "";
                           constraint = constraint.concat(Integer.toString(first_children.get(k).id()));
                           constraint = constraint.concat("-");
                           constraint = constraint.concat(Integer.toString(second_children.get(l).id()));
                           precedence.add(constraint); 
                        }
                        else if(second_children.get(l).start == true)
                        {
                           String constraint = "";
                           constraint = constraint.concat(Integer.toString(second_children.get(l).id()));
                           constraint = constraint.concat("-");
                           constraint = constraint.concat(Integer.toString(first_children.get(k).id()));
                           precedence.add(constraint); 
                        }
                        else if(first_children.get(k).end==true)
                        {
                            String constraint = "";
                           constraint = constraint.concat(Integer.toString(second_children.get(l).id()));
                           constraint = constraint.concat("-");
                           constraint = constraint.concat(Integer.toString(first_children.get(k).id()));
                           precedence.add(constraint); 
                                    
                        }
                        else if(second_children.get(l).end==true)
                        {
                            String constraint = "";
                           constraint = constraint.concat(Integer.toString(first_children.get(k).id()));
                           constraint = constraint.concat("-");
                           constraint = constraint.concat(Integer.toString(second_children.get(l).id()));
                           precedence.add(constraint); 
                        }
                        
                        else
                        {
                        
                        Double prob_const = r.nextDouble();
                        if(prob_const> 0.4)
                        {
                           String constraint = "";
                           constraint = constraint.concat(Integer.toString(first_children.get(k).id()));
                           constraint = constraint.concat("-");
                           constraint = constraint.concat(Integer.toString(second_children.get(l).id()));
                           precedence.add(constraint);
                        }
                        }
                        
                       
                    }
                    
                }
            }
        }
       
       return precedence;
    }
    
    
    public static void moveActivity(Node sequence, Integer original, Integer test_position)
    {
        HashMap<Integer,Node> order = sequence.getNeighbors();
        Node change = order.get(original);
        HashMap<Integer,Node> new_order = new HashMap<>();
        Iterator it = order.entrySet().iterator();
        int i=0;
       
        while(it.hasNext())
        {
          Map.Entry<Integer,Node> node_entry = (Map.Entry<Integer,Node>) it.next();
          if(i<test_position )
          {
              new_order.put(node_entry.getKey(),node_entry.getValue());
          }
          if(i==test_position)
          {
              new_order.put(i, change);
          }
          if(i>test_position&& i<=original)
          {
              new_order.put(i,order.get(i-1));
              
          }
          if(i>original)
          {
              new_order.put(node_entry.getKey(),node_entry.getValue());
          }
          i++;
        }
        sequence.setNeighbors(new_order);
       
         
    }
    
    public static boolean constraintValidity(Node sequence, HashSet<String> precedence)
    {
        
        for(int i=0;i<sequence.getChildren().size()-1;i++){
            
            Node first = sequence.getChildren().get(i);
            List<Node> first_children = first.getChildrenLeaves();
            for(int j = i+1;j<sequence.getChildren().size();j++)
            {
                Node second = sequence.getChildren().get(j);
                List<Node> second_children = second.getChildrenLeaves();
                for(int k=0;k<first_children.size();k++)
                {
                    for(int l=0;l<second_children.size();l++)
                    {
                        Random r = new Random();
                        Double prob_const = r.nextDouble();
                        
                               String constraint = "";
                               constraint = constraint.concat(Integer.toString(first_children.get(k).id()));
                               constraint = constraint.concat("-");
                               constraint = constraint.concat(Integer.toString(second_children.get(l).id()));
                           if(precedence.contains(constraint))
                           {
                               
                              
                               return false;
                               
                           }
                        
                    }
                    
                }
            }
        }
       
       return true;
    }
    
    static int getHeight(Node node) {
        if (node == null) {
            return 0;
        } else {
            int maxHeight = 0;
            for (Node child : node.getChildren()) {
                int height = getHeight(child);
                if (height > maxHeight) {
                    maxHeight = height;
                }
            }
            return maxHeight + 1;
        }
    }
    
    
    static public void initializeCommunication(List<Node> nodes)
    {
        List<Node> sequences = new ArrayList<Node>();
        for(int i=0;i<nodes.size();i++)
        {
            if(nodes.get(i).type==Type.Sequence)
            {
                
                sequences.add(nodes.get(i));
            }
        }
        
        for(int j=0;j<sequences.size();j++)
        {
          Node sequence = sequences.get(j);
          int[] transition_costs = new int[sequence.neighbors.size()-1];
          for(int k=0;k<transition_costs.length;k++)
          {
              Random r = new Random();
              Double prob = r.nextDouble();
              if(prob>0.5)
              {
                  int min=1;
                  int maximum =10;
                  int cost = (int) ((Math.random() * (maximum - min)) + min);
                  transition_costs[k] = cost;
                  
              }
          }
          sequence.setTransitionCosts(transition_costs);
        }
    }
    
    
    public static HashMap<String,Integer> initializeRIC(HashMap<Integer,Resource> resource_names)
    {
        HashMap<String,Integer> ric_costs = new HashMap<>();
        for(int i=0;i<resource_names.size();i++)
        {
            String first_resource = resource_names.get(i).Name;
            for(int j=0;j<resource_names.size();j++)
            {
                String second_resource = resource_names.get(j).Name;
                String resource_pair = "";
                resource_pair = resource_pair.concat(first_resource);
                resource_pair = resource_pair.concat("-");
                resource_pair = resource_pair.concat(second_resource);
                if(first_resource.equals(second_resource))
                {
                    int ric = 0;
                    ric_costs.put(resource_pair, ric);
                }
                else
                {
                
                int ric;
                if(Math.random()< 0.5)
                {
                    ric =0;
                }else
                {
                    ric = 0;
                }
                ric_costs.put(resource_pair, ric);
                
                }
            }
        }
        
        return ric_costs;
    } 
    
    
    
    
 
     
    
    
    
    
    
    
  
}