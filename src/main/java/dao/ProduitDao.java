/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import entité.Produit;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ObjetNonTrouveException;

/**
 *
 * @author jakk
 */
public class ProduitDao {
    private final Connection connexion;
    
    public ProduitDao(){
        this.connexion = Connexion.getConnexion();
    }
    
    public void ajouter(Produit produit){
        String sql = "INSERT INTO PRODUIT (libelle, prix_unitaire, date_peremption)" +
                "VALUE (?, ?, ?, ?)";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setString(1, produit.getLibelle());
            etat.setDouble(2, produit.getPrixUnitaire());
            etat.setDate(3, (Date) produit.getDatePeremption());
            etat.executeUpdate();
            etat.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Produit trouver(int id) throws ObjetNonTrouveException, SQLException{
        String sql = "SELECT * FROM PRODUIT WHERE ID = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setInt(1, id);
            ResultSet resultat = etat.executeQuery();
            if (resultat.next()){
                Produit produit = new Produit();
                produit.setId(resultat.getInt(1));
                produit.setLibelle(resultat.getString(2));
                produit.setPrixUnitaire(resultat.getDouble(3));
                produit.setDatePeremption(resultat.getDate(4));
                
                return produit;
            } else{
                throw new ObjetNonTrouveException("Produit avec id " + String.valueOf(id) +
                        "non trouvé");
            }
        } catch (SQLException | ObjetNonTrouveException e) {
            throw e;
        }
    }
    
    public Produit modifier(Produit produit){
        String sql = "UPDATE PRODUIT SET libelle = ?, prix_unitaire = ?," +
                "date_peremption = ? WHERE id = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setString(1, produit.getLibelle());
            etat.setDouble(2, produit.getPrixUnitaire());
            etat.setDate(3, (Date) produit.getDatePeremption());
            etat.setInt(4, (int) produit.getId());
            etat.executeUpdate();
            etat.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        try {
            return this.trouver((int) produit.getId());
        } catch (SQLException | ObjetNonTrouveException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void supprimer(Produit produit) throws ObjetNonTrouveException{
         String sql = "DELETE FROM PRODUIT WHERE ID = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setInt(1, (int) produit.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public int compter(){
        String sql = "SELECT COUNT(*) FROM PRODUIT";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            ResultSet resultat = etat.executeQuery();
            
            if (resultat.next()) {
               return resultat.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    public List<Produit> lister(){
        String sql = "SELECT * FROM PRODUIT";
        List<Produit> liste = new ArrayList<>() ;
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            ResultSet resultat = etat.executeQuery();
            
            if (resultat.next()) {
               liste.add((Produit) resultat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return liste;
    }
}
