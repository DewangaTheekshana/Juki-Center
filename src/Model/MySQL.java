/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.mysql.cj.protocol.Resultset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class MySQL {
    
    private static Connection connection;
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/amc","root","@Duleekaduleeka123");
//            connection = DriverManager.getConnection("jdbc:mysql://sql.freedb.tech/freedb_amc_db","freedb_Akash","w*n3G#25g$cK9H%");
//            connection = DriverManager.getConnection("jdbc:mysql://sql100.infinityfree.com/if0_39610764_amc","if0_39610764","Dewanga123");
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public static ResultSet execute(String query) throws Exception{

            Statement statement = connection.createStatement();

            if (query.startsWith("SELECT")) {
                return statement.executeQuery(query);
            } else {
                statement.executeUpdate(query);
                return null;
            }

    }

}
