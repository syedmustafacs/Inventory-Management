

package amst;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Syed
 */
public class ProductsDetail {
    
    
    
    private final StringProperty id;
    
    private final StringProperty name;
    

    
    public ProductsDetail(String id,String name){
    
    this.id=new SimpleStringProperty(id); this.name=new SimpleStringProperty(name);
    
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
