/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mycompany.boutique.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jakk
 */
public class ClientDao {
    
    private final Connection connexion;

    public ClientDao() {
        this.connexion = Connexion.getConnexion();
    }

    public void ajouter(Client client) {
        String sql = "INSERT INTO CLIENT(nom, prenom)"
                + " VALUES (?, ?)";

        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setString(1, client.getNom());
            etat.setString(2, client.getPrenom());
            etat.executeUpdate();

            etat.close();
            connexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Client trouver(long id) throws SQLException {
        String sql = "SELECT * FROM CLIENT WHERE ID = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setLong(1, id);
            ResultSet resultat = etat.executeQuery();
            if (resultat.next()) {
                Client client = new Client();
                client.setId(resultat.getLong(1));
                client.setNom(resultat.getString(2));
                client.setPrenom(resultat.getString(3));

                return client;
            }
        }catch (SQLException ex){
                    throw ex;
        }
        return null;
    } 
    
    

    public Client modifier(Client client) {
        String sql = "UPDATE CLIENT SET nom = ? "
                + ", prenom = ? "
                + "WHERE id = ?";

        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setString(1, client.getNom());
            etat.setString(2, client.getPrenom());
            etat.setLong(3, client.getId());
            etat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        try{
            return this.trouver(client.getId());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void supprimer(Client client){
        String sql = "DELETE FROM CLIENT WHERE id = ?";
        try{
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setLong(1, client.getId());
            etat.executeUpdate();
            
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public void supprimer(long id){
        String sql = "DELETE FROM CLIENT WHERE id = ?";
        try{
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setLong(1, id);
            etat.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public int compter(){
        String sql = "SELECT COUNT(*) FROM CLIENT";
        try{
            PreparedStatement etat = connexion.prepareStatement(sql);
            ResultSet resultat = etat.executeQuery();
            if(resultat.next()){
                return resultat.getInt(1);
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }
}
