/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jakk
 */
public class Connexion {
    private static Connection connexion;
    
    static {
        String url = "jdbc:postgres://localhost:5432/ourboutique";
        String login = "ourboutiquejdbc";
        String password = "ourboutique123!";
        
        try {
            connexion = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static Connection getConnexion(){
        return connexion;
    }
}
