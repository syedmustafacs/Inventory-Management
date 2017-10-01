package amst;

/**
 *
 * @author Syed Mustafa
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ShoppingCart {
    
    
     private final StringProperty quantity;
    
    private final StringProperty name;
      private final StringProperty price;
        private final StringProperty total;
        private final StringProperty cartridge;
        private final StringProperty gst;
        
    
     public ShoppingCart(String name,String quantity,String price,String total,String gst,String cartridge){
    
    this.name=new SimpleStringProperty(name);
    this.quantity=new SimpleStringProperty(quantity);
    this.price=new SimpleStringProperty(price); 
    this.total=new SimpleStringProperty(total);
    this.cartridge= new SimpleStringProperty(cartridge);
    this.gst = new SimpleStringProperty(gst);
    
    }
     
     
     
     // getters
     public String getquantity(){
    return quantity.get();
    }
      public String getname(){
    return name.get();
    }
      public String getprice(){
    return price.get();
    }
      public String gettotal(){
    return total.get();
    }
        public String getcartridge(){
    return cartridge.get();
    }
          public String getgst(){
    return gst.get();
    }
     
       
        //Setters
    public void setquantity(String value) {
        quantity.set(value);
    }
     
    public void setname(String value) {
        name.set(value);
    }
     public void setprice(String value) {
        price.set(value);
    }
      public void settotal(String value) {
        total.set(value);
    }
      public void setcartridge(String value) {
        cartridge.set(value);
    }
       public void setgst(String value) {
        gst.set(value);
    }
     
    
    
    
    //Property values
    public StringProperty quantityProperty() {
        return quantity;
    }
    
    public StringProperty nameProperty() {
        return name;
    }
      public StringProperty priceProperty() {
        return price;
    }
        public StringProperty totalProperty() {
        return total;
    }
           public StringProperty cartridgeProperty() {
        return cartridge;
    }
            public StringProperty gstProperty() {
        return gst;
    }
}
