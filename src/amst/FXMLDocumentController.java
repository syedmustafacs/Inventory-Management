package amst;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 *
 */

public class FXMLDocumentController implements Initializable {
  boolean show;
    private DbConnection dc;
      @FXML
    private Label label;
        @FXML
    private TextField txtFieldTotalPrice;
          @FXML
    private TextField txtFieldShortDescription;
            @FXML
    private TextField txtFieldEmail;
 
    private String pass;
      @FXML
    private PasswordField txtFieldPassword;
     
      GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
int width = gd.getDisplayMode().getWidth();
int height = gd.getDisplayMode().getHeight();
      
    private void handleButtonAction(ActionEvent event) throws FileNotFoundException, DocumentException, BadElementException, IOException {
        
        
      
    }
    @FXML
    private void handleLogin(ActionEvent event) throws SQLException, IOException {
    
         Connection conn=dc.Connect();
       
       
        try {
          String email;  String password; 
          boolean check=false;
          
//            boolean rs= conn.createStatement().execute("SELECT* FROM person WHERE ID=190");
         //  ResultSet rs= conn.createStatement().executeQuery("SELECT * FROM login");
         ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM login");
       while(rs.next()){
       
         email=  (String) rs.getObject("email"); password=(String) rs.getObject("passwd");     
 
     //  if(email.equalsIgnoreCase(""+txtFieldEmail.getText()) && password.equals (""+txtFieldPassword.getAccessibleText())){
        if(email.equalsIgnoreCase(""+txtFieldEmail.getText()) || password.equals (""+txtFieldPassword.getText())){
        check=true;
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("FXML/FXMLDashboard.fxml"));
        
        
        Parent root1=(Parent) fxmlLoader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root1));
        show=true;
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.show();
         System.out.println("Correct Email ID and Password");
        
       
     }
               }
       if(check==false){
           System.out.println("Wrong");
       }
          //  System.out.println(""+ rs.getString("email"));
            //System.out.println(""+check);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
       
    }
    
    @FXML
    
    private void handleProduct(ActionEvent event) throws IOException 
    {
       
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("FXML/FXMLProduct.fxml"));
     
        Parent root1=(Parent) fxmlLoader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    
    }
     @FXML
    
    private void generateInvoice(ActionEvent event) throws IOException 
    {
     
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("FXML/FXMLGeneratInvoice.fxml"));
     
        Parent root2=(Parent) fxmlLoader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root2));
        stage.show();
    
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     dc=new DbConnection();
    }    
    
}
