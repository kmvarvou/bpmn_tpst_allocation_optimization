package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Node implements Comparable<Node>,Cloneable{
    private int _id;
    private List<Node> _children;
    private Node parent;
    String Name;
    double cost;
    double time;
    double selectivity;
    double[] path;
    boolean start;
    boolean end;
    Resource resource;
    HashSet<String> eligible_resources;
    HashMap<Integer,Node> neighbors;
    List<Integer> cooperation;
    HashMap<Integer,Node> activities;
    int[] transition_costs;
    
    HashMap<String,Double> profile;
    public enum Type{
        Root,
        Leaf,
        Blank,
        Sequence,
        XOR,
        AND,
        XOR_AND;
    }
    
    public Type type;
    
    @Override
	 public Object clone() throws CloneNotSupportedException{
	 return super.clone();
	 }
         
     public Node copy() throws CloneNotSupportedException
     {
         List<Node> copy= new ArrayList<Node>();
       for(Node n : _children)
       {
           //copy.add((Node) n.copy());
       }
       Node n2 = new Node(this.id());
       n2.setChildren(copy);
       if(eligible_resources!=null && !eligible_resources.isEmpty()){
       HashSet<String> resources_copy = new HashSet<String>();
       if(eligible_resources!=null)
       {
       for(String r : eligible_resources)
       {
           resources_copy.add(r);
       }
       n2.eligible_resources = resources_copy;
       }
       }
       if(this.cooperation!=null)
       {
       List<Integer> cooperation_copy = new ArrayList<>();
       for(Integer n : cooperation)
       {
           cooperation_copy.add(n);
       }
        n2.cooperation = cooperation_copy;
       }
       HashMap<Integer,Node> neighbors_copy = new HashMap<Integer,Node>();
       if(neighbors!=null)
       {
       Iterator neighbors_it = neighbors.entrySet().iterator();
       while(neighbors_it.hasNext())
       {
           Map.Entry<Integer,Node> neighbors_entry = (Map.Entry<Integer,Node>) neighbors_it.next();
           Node clone = neighbors_entry.getValue().copy();
           neighbors_copy.put(neighbors_entry.getKey(), clone);
           copy.add(clone);
       }
       n2.neighbors = neighbors_copy;
       }
       n2.cost = this.cost;
       n2.Name = this.Name;
       n2.type = this.type;
       n2.time = this.time;
       n2.path = this.path;
       n2.start = this.start;
       n2.end = this.end;
       n2.transition_costs = this.transition_costs;
       if(this.resource!= null)
       {
         
           n2.resource= this.resource.Copy();
       }
       if(this.profile!=null)
       {
       n2.profile = (HashMap<String,Double>) this.profile.clone();
       }
       n2.parent = (Node) this.parent.clone();
       
       return n2;
     }    

         
    
    public Node(int id) {
        _id = id;
        _children = new ArrayList<>();
        neighbors = new HashMap<>();
        Name = toAlphabetic(id);
        start = false;
        end = false;
    }
    
    public void setCost(double cost)
    {
        this.cost = cost;
    }
    
    public void setTime(double time)
    {
        this.time = time;
    }
    
    public Node(int id, String Name, Type type)
    {
        _id = id;
        _children = new ArrayList<>();
        neighbors = new HashMap<>();
        this.type = type;
    }
    
    
     public void setResource(Resource resource)
    {
        this.resource = resource;
    }
    
     
     public HashSet<String> getEligibleResources() {
        HashSet<String> allocation = new HashSet<>();
        if(this.type== Type.Leaf)
        {
            allocation.addAll(this.eligible_resources);
        }
        else
        {
            for(int i=0;i<this._children.size();i++)
            {
                allocation.addAll(this.getChildren().get(i).getEligibleResources());
            }
            
        }
        return allocation;
    }
     
    public void setTransitionCosts(int[] transition_costs)
    {
        this.transition_costs = transition_costs;
    }
    
    public void setTransitions(List<Integer> transitions)
    {
        this.cooperation = transitions;
    }
    
    public List<Integer> getTransitions()
    {
        return this.cooperation;
    }
   
    public void setChildren(List<Node> children)
    {
        this._children = children;
    } 
     
    public Resource getResource()
    {
        return this.resource;
    }
    
    public void setResources(HashSet<String> Resources)
    {
        this.eligible_resources = Resources;
    }
    
    public void setProfile(HashMap<String, Double> profile)
    {
        this.profile = profile;
    }
    
    public void setParent(Node parent)
    {
        this.parent = parent;
    }
    
    public void setNeighbors(HashMap<Integer,Node> neighbors)
    {
        this.neighbors = neighbors;
    }
    
    public HashMap<Integer,Node> getNeighbors()
    {
        return this.neighbors;
    }
    
    public Node getParent()
    {
        return this.parent;
    }
    
    public void setType(Type type)
    {
        this.type = type;
    }
    
    public void insertMetadata(double[] prob)
    {
        this.path=prob;
    }
    
    public Type getType()
    {
        return this.type;
    }

    public void addChild(Node node) {
        _children.add(node);
    }

    public int id() {
        return _id;
    }

    public List<Node> getChildren() {
        return _children;
    }
    
    public List<Node> getChildrenChildren() {
        List<Node> list = new ArrayList<>();
        if(this.type==Type.Leaf)
        {
            list.add(this);
            return list;
        }
        else
        {
            list.add(this);
            for(Node n : this._children)
            {
                list.addAll(n.getChildrenChildren());
            }
            return list;
        }
    }
    
    public HashSet<Resource> getAllocation() {
        HashSet<Resource> allocation = new HashSet<>();
        if(this.type== Type.Leaf)
        {
            allocation.add(resource);
        }
        else
        {
            for(int i=0;i<this._children.size();i++)
            {
                allocation.addAll(this.getChildren().get(i).getAllocation());
            }
            
        }
        return allocation;
    }
    
    public List<Node> getChildrenLeaves() {
        List<Node> leaves = new ArrayList<>();
        if(this.type== Type.Leaf)
        {
            leaves.add(this);
        }
        else
        {
            for(int i=0;i<this._children.size();i++)
            {
                leaves.addAll(this._children.get(i).getChildrenLeaves());
            }
            
        }
        return leaves;
    }


    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        if(this.type == Type.Leaf)
        {
        buffer.append(prefix);
        buffer.append(this.Name);
        buffer.append(",");
        buffer.append(type);
        //buffer.append(this.eligible_resources);
        //buffer.append(this.resource.Name);
        //buffer.append(","+cost);
        //buffer.append(this.getResource().Name);
        //buffer.append(this.calculateCost());
        buffer.append('\n');
        }
        else{
            buffer.append(prefix);
        buffer.append(this.Name);
        buffer.append(",");
        buffer.append(type);
        buffer.append(","+neighbors.size());
       // buffer.append(","+this.calculateCost());
        
        buffer.append('\n');
        
        }
        
       
        
        for(int i=0;i<this.neighbors.size();i++)
        {
            if(i<neighbors.size()-1)
            {
                neighbors.get(i).print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            }
            else
            {
                
                neighbors.get(i).print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
        
        
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
    

    
    public double performanceCoefficient()
    {
        Resource allocated = this.resource;
        HashMap<String,Double> resource_profile = allocated.profile;
        HashMap<String,Double> node_profile = this.profile;
        Iterator profile_iterator = resource_profile.entrySet().iterator();
        Double sum =0.0;
        while(profile_iterator.hasNext())
        {
            Map.Entry<String,Double> profile_pair = (Map.Entry<String,Double>) profile_iterator.next();
            Double profile1 = profile_pair.getValue();
           
            Double profile2 = node_profile.get(profile_pair.getKey());
            sum += Math.abs(profile2-profile1);
        }
        return (1 + sum/resource_profile.size());
       
    }
    
    public void print()
    {
        
        if(this.type==Type.Leaf)
        {
           
            return;
        }
        else
        {
            Iterator it = this.neighbors.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        temp_node.print();
            }
            return;
        }
    }
    
    public double getCooperation()
    {
        double sum = 0;
        for(int i=0;i<this.cooperation.size();i++)
        {
            Node one = this;
            Node two = this.activities.get(this.cooperation.get(i));
            
            sum += pairCooperation(one,two);
        }
        return (double) (1 + (sum/this.cooperation.size()));
    }
    
    public double pairCooperation(Node one, Node two)
    {
        HashMap<String,Double> profile_one = one.resource.profile;
        
        HashMap<String,Double> profile_two = two.resource.profile;
        Iterator it = profile_one.entrySet().iterator();
        
        double diff =0;
        while(it.hasNext())
        {
            Map.Entry<String,Double> profile_value =  (Map.Entry<String,Double>) it.next();
            diff += Math.abs(profile_one.get(profile_value.getKey()) - profile_two.get(profile_value.getKey()));
        }
        return (double) diff/profile_one.size();
    }
    
    public double calculateTime(HashMap<String,Integer> ric_costs)
    {
       
        if(this.type==Type.Leaf)
        {
         
            
            if(this.resource.Type.equals("Blank"))
            {
                return this.cost;
            }
            else{
            
              
            
            return (this.time * performanceCoefficient());
          
            }
        }
        else
        {
            
            Iterator it = this.neighbors.entrySet().iterator();
            if(this.type==Type.Sequence)
            {
                double sum=0;
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                       
                        
                        sum += temp_node.calculateTime(ric_costs);
                        
            }
            
            for(int i=0;i<this.neighbors.size()-1;i++)
            {
                Node first = this.neighbors.get(i);
                Node second = this.neighbors.get(i+1);
                List<Node> first_children = first.getChildrenChildren();
                List<Node> second_children = second.getChildrenChildren();
                for(int j=0;j<first_children.size();j++)
                {
                    for(int k=0;k<second_children.size();k++)
                    {
                        if(first.type!=type.Leaf || second.type!= type.Leaf)
                        {
                          
                        }else
                        {
                           String resource_pair = "";
                           resource_pair = resource_pair.concat(first.resource.Name);
                           resource_pair = resource_pair.concat("-");
                           resource_pair = resource_pair.concat(second.resource.Name);
                           sum += ric_costs.get(resource_pair);
                        }
                    }
                }
                
            }
            
            return sum;
            }
            else if(this.type==type.XOR_AND)
            {
                double min=Double.MAX_VALUE;
                while(it.hasNext())
                {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        if(temp_node.calculateTime(ric_costs)<min)
                        {
                            min=temp_node.calculateTime(ric_costs);
                        }
                }
                
                return min;
                
            }
            else if(this.type==type.AND)
            {
                double max=0;
                while(it.hasNext())
                {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                       
                        if(temp_node.calculateTime(ric_costs)>max)
                        {
                            max=temp_node.calculateTime(ric_costs);
                        }
                }
                
                return max;
                
            }
            else if(this.type==type.XOR)
            {
                double sum=0;
                
               while(it.hasNext())
               {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        sum += this.path[temp.getKey()]*temp_node.calculateTime(ric_costs);
                        
                        
                        
                }
               
               return sum;
            }
            else{
                return 0;
            }
        }
        
    }
    
    
    public double calculateCost()
    {
       
        if(this.type==Type.Leaf)
        {
        
            
            
            
              
            
            return (this.cost   * performanceCoefficient());
         
            
        }
        else
        {
            
            Iterator it = this.neighbors.entrySet().iterator();
            if(this.type==Type.Sequence)
            {
                double sum=0;
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        sum += temp_node.calculateCost();
                        
            }
            
            
            
            return sum;
            }
            else if(this.type==type.XOR_AND)
            {
                double min=Double.MAX_VALUE;
                while(it.hasNext())
                {
                
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                        if(temp_node.calculateCost()<min)
                        {
                            min=temp_node.calculateCost();
                        }
                }
                
                return min;
                
            }
            else if(this.type==type.AND)
            {
                double sum=0;
                while(it.hasNext())
                {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                       
                        sum +=temp_node.calculateCost();
                }
                
                return sum;
                
            }
            else if(this.type==type.XOR)
            {
                double sum=0;
                
               while(it.hasNext())
               {
                Map.Entry<Integer,Node> temp = (Map.Entry<Integer,Node>) it.next();
                        Node temp_node = temp.getValue();
                       
                        sum += this.path[temp.getKey()]*temp_node.calculateCost();
                        
                        
                        
                }
               
               return sum;
            }
            else{
                return 0;
            }
        }
        
    }
    
    
    
    public void replaceNeighbor(Node old, Node replacement)
    {
        Iterator it = this.neighbors.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
            int key = entry.getKey();
            Node candidate = entry.getValue();
            if(old.Name.equals(candidate.Name))
            {
                this.neighbors.put(key, replacement);
            }
        }
                
    }
    
    public HashMap<Integer,Node> reorderNeighbor(int pos_a, Node b, int pos_b)
    {
        HashMap<Integer,Node> new_neighbors = new HashMap();
        
        
        for(int i=0;i<this.neighbors.size();i++)
        {
            if(i<pos_a)
            {
                Node temp_node = this.neighbors.get(i);
                new_neighbors.put(i, temp_node);
            }
            else if(i>pos_a && i <pos_b)
            {
                Node temp_node = this.neighbors.get(i);
                new_neighbors.put(i+1,temp_node);
            }
            else if(i==pos_a)
            {
                Node temp_node = this.neighbors.get(i);
                new_neighbors.put(i, b);
                new_neighbors.put(i+1,temp_node);
                
            }
            else if(i== pos_b)
            {
                
                continue;
            }
            else if(i>pos_a && i >= pos_b)
            {
                Node temp_node = this.neighbors.get(i);
                new_neighbors.put(i, temp_node);
            }
        }
        
        return new_neighbors;
        
        
    }
    
    public void swap(String first, String second)
        {
            HashMap<Integer,Node> new_neighbors = new HashMap<>();
            HashMap<Integer,Node> old_neighbors = this.neighbors;
            Iterator it = old_neighbors.entrySet().iterator();
            int first_index=0;
            int second_index=0;
            int i=0;
            while(it.hasNext())
            {
                Map.Entry<Integer,Node> entry = (Map.Entry<Integer,Node>) it.next();
                Node next_check =  entry.getValue();
                if(next_check.Name.equals(first))
                {
                    first_index =i;
                }
                if(next_check.Name.equals(second))
                {
                    second_index =i;
                }
                i++;
            }
            
            Iterator it2 = old_neighbors.entrySet().iterator();
            i=0;
            for( i=0;i<old_neighbors.size();i++)
            {
                if(i<second_index)
                {
                    new_neighbors.put(i, old_neighbors.get(i));
                }
                else if(i>second_index && i<first_index)
                {
                    new_neighbors.put(i+1, old_neighbors.get(i));
                }
                
                if(i==second_index)
                {
                    new_neighbors.put(i, old_neighbors.get(first_index));
                    new_neighbors.put(i+1, old_neighbors.get(i));
                }
                
                if(i>second_index && i>first_index)
                {
                    new_neighbors.put(i, old_neighbors.get(i));
                }
            }
            
            this.neighbors = new_neighbors;
        }

    @Override
    public int compareTo(Node t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void insertNeighbor(Node n)
    {
        if(this.neighbors!=null)
        {HashMap<Integer,Node> temp = this.neighbors;
        int id = temp.size();
        this.neighbors.put(id, n);
        }
        else
        {
            
            HashMap<Integer,Node> temp =new HashMap();
            temp.put(0, n);
            this.neighbors = temp;
        }
    }
    
    
    public void initializeProbability()
    {
        /*Integer[] random = new Integer[neighbors.size()];
        int min=1;
        int max = neighbors.size();
        Random r = new Random();
        int sum=0;
        for(int i=0;i<neighbors.size();i++)
        {
            
            random[i] =  (int) ((Math.random() * (max - min)) + min);
            sum+= random[i];
        }
        double prob[] = new double[neighbors.size()];
        for(int i=0;i<neighbors.size();i++)
        {
            
            
            prob[i] = (double) random[i]/sum;
        }
        this.path = prob;
        */
        double prob[] = new double[neighbors.size()];
        for(int i=0;i<neighbors.size();i++)
        {
            
            prob[i] = (double) 1.0/neighbors.size();
            
        }
        this.path = prob;
    }

}
