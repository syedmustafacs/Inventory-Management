/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amst;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author HP
 */
public class DbConnection {

    public java.sql.Connection Connect() throws SQLException {
        try {
            //Your database url string,e nsure it is correct
           
            
            
           
            String url = "jdbc:mysql://localhost:3306/tehreem?zeroDateTimeBehavior=convertToNull";
            String user = "root";
            String password = "";

            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection conn = DriverManager.getConnection(url, user, password);
            return conn; 

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
