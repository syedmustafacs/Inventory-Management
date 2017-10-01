package amst;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.swing.DefaultComboBoxModel;

/**
 * FXML Controller class
 *
 * @author Syed
 */
public class FXMLProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    DatabaseQueries qs;
    public DbConnection dc;
    public TableView<PartsDetail> table2;
    @FXML
    public TableView<PartsDetail> table1;
    public TableView table;
    public TableColumn id;  
    public TableColumn name;
    public TableColumn quantity;
    public ObservableList data1;
    public ObservableList<Clients> clients;
    Pane pane;
    Button btn;
    public TextField nameOfProduct;
    public TextField idParts;
    public TextField quantitiesParts;
    @FXML 
    public TextField updateInventoryId;
     @FXML 
    public TextField updateInventoryQuantity;
    public TextField updateInventoryThreshold;
    public TextField addPartQuantity;
    public TextField addPartName;
    public TextField addPartMetaData;
    public TextField addPartThreshold;
    public ComboBox  clientComboBox;
    public TableView productTable;
    public TableColumn productId;
    public TableColumn productName;
    public TextField invoiceId;
    public TextField invoiceQuantity;
    public TextField invoicePrice;
    @FXML
    public TableColumn id1;  
    @FXML
    public TableColumn name1;
    @FXML
    public TableColumn quantity1;
    @FXML
    public TableColumn threshold1; 
   //
    public TableColumn id2;  
    public TableColumn name2;
    public TableColumn quantity2;
   
    public CheckBox checkForGst;
    public Label alertField;
    
    ObservableList shoppingCart=FXCollections.observableArrayList();
    ShoppingCart sc;
    int subTotal=0;
    int clientCounter=0;
    
    // Table Start for Shopping Cart
       
     public TableView cart;
     public TableColumn cartName;
     public TableColumn cartQuantity;
     public TableColumn cartPrice;
     public TableColumn cartTotal;
     public TableColumn cartGst;
     public TableColumn cartCartridge;
     public TextField txtFieldSubTotal;
     @FXML
     public TextField invoiceGst;
     @FXML 
     public TextField invoiceCartridge;
    
    
    
    
    // END
   
    //ArrayList for decrementing Inventory
    ArrayList<String> inventoryDecrementArrayList = new ArrayList();
    // END
    ArrayList<String> inventoryIncrementArrayList = new ArrayList();
    
    int cc(String query) throws SQLException
    {  
        
        ArrayList<Integer> al = new ArrayList();
        Connection conn=dc.Connect(); 
        ResultSet tmp; int val=0;
        tmp =conn.createStatement().executeQuery(query);
        while(tmp.next())
        {
     //   al.add(Integer.parseInt(tmp.getString("quantity")));
            val = Integer.parseInt(tmp.getString("quantity"));
        }
        return val;
    }
    
    boolean checkInventory(String name,int quantity) throws SQLException
    {
        DatabaseQueries ci = new DatabaseQueries();
       String q = quantity+""; int val=0;int th=0;int calc=0;
       String query= ci.checkProduct(name, q);
          ResultSet rs; ResultSet rs1;
          Connection conn=dc.Connect();
          rs=conn.createStatement().executeQuery(query);
          while(rs.next())
          {
             int partId = Integer.parseInt( rs.getString("pid"));
             int partQuantity = Integer.parseInt(rs.getString("quantity"));
             int totalQ= quantity*partQuantity;
             query= ci.checkInventory(""+partId);
             rs1=conn.createStatement().executeQuery(query);
             //if(Integer.parseInt(rs1.getString("quantity"))>=totalQ)
            while(rs1.next())
            {
                 val = Integer.parseInt(rs1.getString("quantity"));
                 th = Integer.parseInt(rs1.getString("Threshold"));
                 calc = val-th;
                 
               /// System.out.println("Value is "+val);
               /// System.out.println("Threshold is" + th);
            }
             if(calc<totalQ)
             {
                 System.out.println("Quantity Reached to threshold Can not Generate product");
                 return false;
             }
          }
        return true;
    }
    void calculator()
    {
        System.out.println("Correctly Calculated");
    }
      int counter =2;
      
    public void actionCartAdd(ActionEvent e) throws SQLException
    {    
       try{     
          int quantity,price,cartridge=0; int gst=1;
          DatabaseQueries dq= new DatabaseQueries();
          
          quantity =Integer.parseInt(invoiceQuantity.getText());
          price = Integer.parseInt(invoicePrice.getText());  
          cartridge = Integer.parseInt(invoiceCartridge.getText());
          gst = Integer.parseInt(invoiceGst.getText());
          
           // Result Set for deducting inventory
           ResultSet inventoryMinus;
    
    //end
    Connection conn = dc.Connect();
       
       
 /*   showProducts(null);
            showProducts1(null);
            showProducts2(null);
            showProductTable(null); */
          boolean allowed = checkInventory(invoiceId.getText(),quantity);
    
      if(allowed)
      {
  
          System.out.println("Allowed");
          int total = price * quantity;
          
           int saleTax= (total*gst)/100;
           int  Total = cartridge + total + saleTax;
              
          
          subTotal =subTotal+Total;
          System.out.println("Total is "+Total);
          shoppingCart.add(new ShoppingCart(invoiceId.getText(),invoiceQuantity.getText(),invoicePrice.getText(),""+Total,""+saleTax,""+cartridge));
           txtFieldSubTotal.setText(""+subTotal);
        
        // Inventory Minus
      String ans=  dq.createInvoice(invoiceId.getText(), invoiceQuantity.getText());
      inventoryMinus=conn.createStatement().executeQuery(ans);
      int inventory =0;
        //   System.out.println(" "+ans[i]);
      ArrayList<String> al = null;
         while(inventoryMinus.next())
         
         {  // tmp =conn.createStatement().executeQuery("SELECT * from `part` where id = 216 " );
            inventory= cc("SELECT quantity from `part` where id = "+inventoryMinus.getString("pid")+" ");
     
          inventory = inventory - (Integer.parseInt(inventoryMinus.getString("quantity"))* quantity );
          if(inventory<0)
          {
               System.out.println("Sorry Your inventory is not sufficient");
          }
           else
          {  
           
              inventoryDecrementArrayList.add("UPDATE `part` SET `quantity`='"+inventory+"' WHERE id ="+inventoryMinus.getString("pid")+"");
           // 
         //  conn.createStatement().execute("UPDATE `part` SET `quantity`='"+inventory+"' WHERE id ="+inventoryMinus.getString("pid")+"");
           } 
         }
         
         
         //Decrementing Inventory
        int size = inventoryDecrementArrayList.size();
        for(int i=0;i<size;i++)
        {
          conn.createStatement().execute(inventoryDecrementArrayList.get(i));
          
        }
        //Decrementing Inventory End
        
        showProducts1(e); 
       
         //...........
           clientComboBox.setVisible(false);
      }
      else{
          //alertField.setText("Insufficient Inventory try less value");
          //InvoiceFN.setText("Cannot Update");
          //InvoiceSN.setText("");
          System.out.println("Inventory Empty");
          clientComboBox.setVisible(true);
      }
      
     
      
        // Removal of fields
          
          invoiceId.setText("");
          invoiceQuantity.setText("");
          invoicePrice.setText("");
          invoiceCartridge.setText("");
          invoiceGst.setText("");
          
          // removal of field end
        
          
        //Cart Tablle  
        cartName.setCellValueFactory(new PropertyValueFactory<>("name"));
      
        cartQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
         
        cartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        cartGst.setCellValueFactory(new PropertyValueFactory<>("gst"));
        cartCartridge.setCellValueFactory(new PropertyValueFactory<>("cartridge"));
      
        cart.setItems(shoppingCart);
        //Cart Table End
        
      
       }
       catch(NumberFormatException nfe)
       {
           System.out.println("Check your Input might not be integer");
       }
        
    }
    
      
    //CartReset
    
    public void actionCartReset(ActionEvent es) throws SQLException{
        try{
        int inventory =0;
        DatabaseQueries dq= new DatabaseQueries();
        Connection conn = dc.Connect();
        ResultSet inventoryReturn;
        ShoppingCart cartDetail  = (ShoppingCart) 
        cart.getSelectionModel().getSelectedItem();
        String ans=  dq.checkProduct(cartDetail.getname(), cartDetail.getquantity());
        inventoryReturn=conn.createStatement().executeQuery(ans);   
        int Quantity= Integer.parseInt(cartDetail.getquantity());
        while(inventoryReturn.next())
        {
            inventory= cc("SELECT quantity from `part` where id = "+inventoryReturn.getString("pid")+" ");
            inventory = inventory + (Integer.parseInt(inventoryReturn.getString("quantity"))*Quantity);
           
            inventoryIncrementArrayList.add("UPDATE `part` SET `quantity`='"+inventory+"' WHERE id ="+inventoryReturn.getString("pid")+"");
         }
        
         
         
         //Inventory Increment
        int size = inventoryIncrementArrayList.size();
        for(int i=0;i<size;i++)
        {
          conn.createStatement().execute(inventoryIncrementArrayList.get(i));
        }
        //Inventory Increment End
        
        
        showProducts1(es); //Auto Refresh
        
        
       shoppingCart.removeAll(cartDetail); //removing rows from cart
       
       
        if(shoppingCart.isEmpty())
        {
            clientComboBox.setVisible(true);
        }
        
       int subtotal = Integer.parseInt(cartDetail.gettotal());
       subTotal = subTotal - subtotal;
       txtFieldSubTotal.setText(""+subTotal);
        }
        
        catch(Exception E)
        {
        Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, E);
        }
 }
    
    
    //CartReset End
    
    
      public void actionGenerateInvoice(ActionEvent e) throws DocumentException, BadElementException, IOException, SQLException {
          // PdfGenerate pdf=new PdfGenerate(invoiceId.getText(),invoiceQuantity.getText(), (String) clientComboBox.getValue());
          // pdf.createPdf();
          //  checkForGst.setSelected(false);
          //
        try
        {
         Connection conn = dc.Connect();
       
        if(shoppingCart.isEmpty()== true){
            System.out.print("Cart is Empty cant Generate");
        }
        else{
            
         
        String client = (String) clientComboBox.getValue();
        PdfGenerate pdf = new PdfGenerate(shoppingCart,client);
        pdf.createPdf();
       
        shoppingCart.clear();
        subTotal=0;
        txtFieldSubTotal.setText("");
        clientComboBox.setVisible(true);
        }
        }
        catch(Exception es)
        {
            System.out.println("Not allowed ");
        }
        
      }
         
         public void actionAddPart(ActionEvent e) throws SQLException{
         Connection conn=dc.Connect();
//            showProducts(null);
            showProducts1(null);
//            showProducts2(null);
            showProductTable(null);
         String  Quantity=addPartQuantity.getText(); String  Name=addPartName.getText();
          String MetaData= addPartMetaData.getText();
          String Threshold = addPartThreshold.getText();
           
         try
         {
     conn.createStatement().execute("INSERT INTO `part` (`id`, `name`, `quantity`, `detail`,`Threshold`) VALUES (NULL, '"+Name+"', '"+Quantity+"','"+MetaData+"','"+Threshold+"')");
        // PartSN.setText("Part Added");
        // PartFN.setText("");
    
         }
         catch(Exception exceptionPart)
         {
           // PartFN.setText("Cannot Add Part");
            //PartSN.setText("");
             System.out.println("Exception coming");
         }
        //   System.out.println("KKG");
        
      
         }
   
   public void createProduct(ActionEvent e) throws SQLException
   {
  
            Connection conn=dc.Connect();
            showProducts(null);
            showProducts1(null);
            showProducts2(null);
            showProductTable(null);
            String name = nameOfProduct.getText();  String id=idParts.getText(); String quantity=quantitiesParts.getText();
            String query[]= qs.createProduct(name,id,quantity);
   
   try
   {
       conn.createStatement().execute(query[0]);  //for creating product means creating a new table
       conn.createStatement().execute(query[1]); // for adding into product table
      
   }
   catch(Exception p1)
              {
                  
              }
      int size=query.length;
      for(int i=2;i<=size-1;i++)
      { 
          try
          {
      conn.createStatement().execute(query[i]);
   //   ProductFN.setText("Product Created");
          //        ProductSN.setText("");
          }
          catch(Exception p2)
              {
       //           ProductFN.setText("Failed:Remember use '_' for space");
     //             ProductSN.setText("");
                  
              }
      }
      
        
   }
   public void action(ActionEvent e) throws IOException{
   
   
   FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("FXML/FXMLProducts.fxml"));
     
        Parent root1=(Parent) fxmlLoader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        
   } 
   
    public void showProducts(ActionEvent e) throws SQLException{
        try {
            Connection conn=dc.Connect();
            
             data1=FXCollections.observableArrayList();
       
           ResultSet rs= conn.createStatement().executeQuery("SELECT* FROM part");
         while(rs.next()){
           
           data1.add(new PartsDetail(rs.getString("id"),rs.getString("name"),rs.getString("quantity"),rs.getString("Threshold")));
    
           }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
      
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
         
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
      
      
        table.setItems(data1); 
       
        
   }
    public void showClients(ActionEvent e) throws SQLException{
    Connection conn=dc.Connect();
  ArrayList alClients=new ArrayList();
  
    
    clients=FXCollections.observableArrayList();
   DatabaseQueries clientQ=new DatabaseQueries();
  String Query=  clientQ.getClients();
        System.out.println(" query is "+Query);
     ResultSet rs= conn.createStatement().executeQuery(Query);
    // DefaultComboBoxModel model = new DefaultComboBoxModel();
        
     while(rs.next()){
     clients.add(new Clients(rs.getString("name")));
  //       model.addElement(rs.getString("name"));
         System.out.println("Client name is "+rs.getString("name"));
//    clientComboBox.setValue(rs.getString("name"));
    
           alClients.add(rs.getString("name"));
    
     }
     ObservableList oList = FXCollections.observableArrayList(alClients);
     
    
    clientComboBox.setItems(oList);
    
    
    }
    public void showProductTable(ActionEvent e) throws SQLException{
    
    
    try {
            Connection conn=dc.Connect();
            
             data1=FXCollections.observableArrayList();
       String query=qs.getProducts();
           ResultSet rs= conn.createStatement().executeQuery(query);
         while(rs.next()){
           
           data1.add(new ProductsDetail(rs.getString("id"),rs.getString("name")));
    
           }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
      
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
         
        
      
      
        productTable.setItems(data1); 
        
       
            
    
    }
    
   public void MouseClick1(MouseEvent e) throws SQLException
   {
 
        PartsDetail parts = table1.getSelectionModel().getSelectedItem();
        updateInventoryId.setText(parts.getid());
        updateInventoryQuantity.setText(parts.getquantity());
        updateInventoryThreshold.setText(parts.getthreshold());
        
   }
   public void BoxCheck(ActionEvent e){
       System.out.println("BOX CHECK reached");
   if(!checkForGst.isSelected()){
   
   checkForGst.setSelected(true);
   }
   }
     public void actionOnClickForProduct(MouseEvent e) throws SQLException
   {
        ProductsDetail product = (ProductsDetail) productTable.getSelectionModel().getSelectedItem();
     //   System.out.println("Product Name is "+product.getname());
        invoiceId.setText(product.getname());
     
       
        
        
        
   }
   
    @FXML
   public void actionOnSave(ActionEvent e) throws SQLException{
     //  DatabaseQueries qs= new DatabaseQueries();
      
        try
       {     
            
       String Query = qs.updateInventory(updateInventoryQuantity.getText(), updateInventoryId.getText(),updateInventoryThreshold.getText());
      // String Query = "Update part SET `quantity` = "+ updateInventoryQuantity.getText() +" WHERE `id`=" +updateInventoryQuantity.getText();
       System.out.println(Query);
       Connection conn=dc.Connect();
       //conn.createStatement().executeQuery(Query);
      
       conn.createStatement().execute(Query);
          // InventorySN.setText("Updated Inventory");
        //   InventoryFN.setText("");
       showProducts1(e);
       
       updateInventoryId.setText("");
       updateInventoryQuantity.setText("");
       updateInventoryThreshold.setText("");
       }
       catch(Exception exceptionInv)
       {
          // InventoryFN.setText("Failed to update inventory");
          // InventorySN.setText("");
       }
      
  
   }
   @FXML
    public void showProducts1(ActionEvent e) throws SQLException{
         
        try {
            Connection conn=dc.Connect();
            
             data1=FXCollections.observableArrayList();
           String Query=  qs.getParts();
       
           ResultSet rs= conn.createStatement().executeQuery(Query);
         while(rs.next()){
           
           data1.add(new PartsDetail(rs.getString("id"),rs.getString("name"),rs.getString("quantity"),rs.getString("threshold")));
          
           }
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        id1.setCellValueFactory(new PropertyValueFactory<>("id"));
      
        name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        quantity1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        threshold1.setCellValueFactory(new PropertyValueFactory<>("threshold"));
       // cb.setCellValueFactory(new PropertyValueFactory<>());
      
        table1.setItems(data1); 
        
   }
    public void showProducts2(ActionEvent e) throws SQLException{
      
       
        try {
            Connection conn=dc.Connect();
            
             data1=FXCollections.observableArrayList();
           String Query=  qs.getParts();
       
           ResultSet rs= conn.createStatement().executeQuery(Query);
         while(rs.next()){
           
           data1.add(new PartsDetail(rs.getString("id"),rs.getString("name"),rs.getString("quantity"),rs.getString("threshold")));
    
           }
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        id2.setCellValueFactory(new PropertyValueFactory<>("id"));
      
        name2.setCellValueFactory(new PropertyValueFactory<>("name"));
         
        quantity2.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
       // cb.setCellValueFactory(new PropertyValueFactory<>());
      
        table2.setItems(data1); 
        
   }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            dc=new DbConnection();
            qs= new DatabaseQueries();
          //  showProducts(null);
            showProducts1(null);
           // showProducts2(null);
            showProductTable(null);
       //   actionOnIncrement(null);
            showClients(null);
            
            
            /*   try {
            showClients(null);
            showProductTable(null);
            } catch (SQLException ex) {
            Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
        } catch (SQLException ex) {
            Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
