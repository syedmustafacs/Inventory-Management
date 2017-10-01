package amst;


import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Syed
 */
public class Clients {
    private  StringProperty id;
    
    private final StringProperty name;
    
     public Clients(String id,String name){
    
    this.id=new SimpleStringProperty(id); this.name=new SimpleStringProperty(name);
    
    }
   public Clients(String name){
    
    this.name=new SimpleStringProperty(name);
    
    }
     
     
      // getters
     public String getid(){
    return id.get();
    }
      public String getname(){
    return name.get();
    }
      
        //Setters
    public void setid(String value) {
        id.set(value);
    }
     
    public void setname(String value) {
        name.set(value);
    }
    
    
     
    //Property values
    public StringProperty idProperty() {
        return id;
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
}
