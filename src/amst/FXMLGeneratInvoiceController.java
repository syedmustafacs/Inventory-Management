/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amst;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Syed
 */
public class FXMLGeneratInvoiceController implements Initializable {

    /**
     * Initializes the controller class.
     */
      @FXML
     TableView table; TableRow s;
      @FXML
     Pane pane;
        @FXML
     Button btn=new Button();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     @FXML
   private void btnAdd(ActionEvent e) {
   
   
    pane.getChildren().add(btn);
    pane.getChildren().add(btn);
     
        
        
   } 
}
