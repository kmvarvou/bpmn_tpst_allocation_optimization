/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author kostis
 */
public class Resource {
    
    String Name;
    String Type;
    HashMap<String,Double> profile;
    Node node;
    Boolean availability;
    Integer capacity;
    Integer current_capacity;
    HashSet<Node> allocated_nodes;
    
    @Override
	 public Object clone() throws CloneNotSupportedException{
	 return super.clone();
	 }
    
         
    public Resource Copy() throws CloneNotSupportedException
    {
        Resource copy = new Resource(this.Name,this.Type);
        copy.setCapacity(this.capacity);
        HashSet<Node> nodes = new HashSet();
        for(Node n : allocated_nodes)
        {
          //nodes.add(n.copy());   
        }
        copy.allocated_nodes = nodes;
        copy.profile = this.profile;
        return copy;
    }      
    public Resource(String Name, String Type)
    {
        this.Name = Name;
        this.Type = Type;
        this.allocated_nodes = new HashSet();        
       
        
    }
    
    public void changeAvailability(boolean newAvail)
    {
        this.availability = newAvail;
    }
    
    public void Allocate(Node node)
    {
        this.allocated_nodes.add(node);
        this.current_capacity-=1;
        
    }
    
    public void setProfile(HashMap<String, Double> profile)
    {
        this.profile = profile;
    }
    
    public void setCapacity(Integer capacity)
    {
        this.capacity = capacity;
        this.current_capacity = capacity;
        
    }
    public Node getAllocatedNode()
    {
        return this.node;
    }
    
    public void resetCapacity()
    {
        this.allocated_nodes = new HashSet<Node>();
        this.current_capacity = this.capacity;
    }
    
}
