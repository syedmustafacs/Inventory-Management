/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amst;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Syed
 */
public class PartsDetail {
    
    
    
    private final StringProperty id;
    
    private final StringProperty name;
    
    private final StringProperty quantity;
    private final StringProperty Threshold;
    
    public PartsDetail(String id,String name,String quantity,String Threshold){
    
    this.id=new SimpleStringProperty(id); this.name=new SimpleStringProperty(name); this.quantity=new SimpleStringProperty(quantity);
    this.Threshold = new SimpleStringProperty(Threshold);
    }
    
   
   
    // getters
     public String getid(){
    return id.get();
    }
      public String getname(){
    return name.get();
    }
       public String getquantity(){
    return quantity.get();
    }
        public String getthreshold(){
    return Threshold.get();
    }
       
        //Setters
    public void setid(String value) {
        id.set(value);
    }
     
    public void setname(String value) {
        name.set(value);
    }
    
     
    public void setquantity(String value) {
        quantity.set(value);
    }
     
    public void setthreshold(String value) {
        Threshold.set(value);
    }
    
    
    //Property values
    public StringProperty idProperty() {
        return id;
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty quantityProperty() {
        return quantity;
    }
     public StringProperty thresholdProperty() {
        return Threshold;
    }
}
