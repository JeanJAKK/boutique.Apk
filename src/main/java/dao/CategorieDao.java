/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mycompany.boutique.Categorie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jakk
 */
public class CategorieDao {

    private final Connection connexion;

    public CategorieDao() {
        this.connexion = Connexion.getConnexion();
    }

    public void ajouter(Categorie categorie) {
        String sql = "INSERT INTO CATEGORIE(libelle, description)"
                + " VALUES (?, ?)";

        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setString(1, categorie.getLibelle());
            etat.setString(2, categorie.getDescription());
            etat.executeUpdate();

            etat.close();
            connexion.close();
            System.out.println("Catégorie ajoutée!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Categorie trouver(Integer id) throws SQLException {
        String sql = "SELECT * FROM CATEGORIE WHERE ID = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setInt(1, id);
            ResultSet resultat = etat.executeQuery();
            if (resultat.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(resultat.getInt(1));
                categorie.setLibelle(resultat.getString(2));
                categorie.setDescritpion(resultat.getString(3));

                return categorie;
            }
        }catch (SQLException ex){
                    throw ex;
        }
        return null;
    } 
    
    

    public Categorie modifier(Categorie categorie) {
        String sql = "UPDATE CATEGORIE SET libelle = ? "
                + ", description = ? "
                + "WHERE id = ?";

        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setString(1, categorie.getLibelle());
            etat.setString(2, categorie.getDescription());
            etat.setInt(3, categorie.getId());
            etat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        try{
            return this.trouver(categorie.getId());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void supprimer(Categorie categorie){
        String sql = "DELETE FROM CATEGORIE WHERE id = ?";
        try{
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setInt(1, categorie.getId());
            etat.executeUpdate();
            
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public void supprimer(Integer id){
        String sql = "DELETE FROM CATEGORIE WHERE id = ?";
        try{
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setInt(1, id);
            etat.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public int compter(){
        String sql = "SELECT COUNT(*) FROM CATEGORIE";
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
    
//    public void lister(){
//        String sql = "SELECT * FROM CATEGORIE";
//        try{
//            PreparedStatement etat = connexion.prepareStatement(sql);
//            ResultSet resultat = etat.executeQuery();
//        }
//    }
}
