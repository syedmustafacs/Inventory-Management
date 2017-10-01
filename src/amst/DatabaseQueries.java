/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amst;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

/**
 *
 * @author Syed
 */
public class DatabaseQueries {
    
     
      private DbConnection dc;
      String id; String qunatity;
      //String query="";
      String checkProduct(String name,String quantity)
      {
          String query="";
          query ="Select * from "+name+"";
          return query;
      }
      
       String checkInventory(String id)
      {
          String query="";
          query ="Select quantity,Threshold from part where id = "+id+"";
          return query;
      }
      
     
      String createInvoice(String name,String quantity)
      {
          
            String products  = name;
            String quantities [] = quantity.split(",");
           // int size= products.length;
          //  String query[] = new String[size];
            String query;
            
              query = "SELECT pid,quantity from "+ products+""; 
            
            
            return query;
      
      }
      
      String[] createProduct(String tName,String tParts,String tQuantities)
      {
          System.out.println("tparts is "+tParts);
          String pId[]= tParts.split(",");
          String pQn[] = tQuantities.split(",");
          String query[]=new String[pId.length+2];
          query[0]="CREATE TABLE "+tName+" ( id int not null auto_increment primary key, pid int not null,quantity int not null,FOREIGN KEY "+tName+"(pid)  REFERENCES part(id))";
          query[1] = "INSERT INTO `product` (`name`) VALUES ('"+tName+"')";
          int temp=2;
          for(int i=0;i<pId.length;i++)
          {
             query[temp]="INSERT INTO "+tName+" (`pid`,`quantity`) VALUES ("+pId[i]+","+pQn[i]+" ) ";
             temp++;
          }
     
      return query;
      }
      
      String getParts(){
      
          String query="SELECT * from part";
          return query;
      }
       String getProducts(){
       String query="SELECT * from product";
          return query;
       }
      String getClients(){
      String query = "SELECT * from client";
      return query;
      }
     String updateInventory(String quantity,String id,String threshold) throws SQLException{
     
       this.id=id; this.qunatity=quantity;
          
          String query="UPDATE `part` SET `quantity`='"+quantity+"',`Threshold`='"+threshold+"' WHERE id ="+id+"";  
            
          
       
       //  ResultSet    rs= conn.createStatement().executeQuery("UPDATE `part` SET `quantity`='220' WHERE id =201");
       
         //  rs=conn.createStatement().
            
     
           return query; 
      
      }
      
     
}
