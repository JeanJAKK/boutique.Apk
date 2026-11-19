/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mycompany.boutique.Employe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jakk
 */
public class EmployeDao {
    private final Connection connexion;

    public EmployeDao() {
        this.connexion = Connexion.getConnexion();
    }

    public void ajouter(Employe employe) {
        String sql = "INSERT INTO EMPLOYE(nom, prenom)"
                + " VALUES (?, ?)";

        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setString(1, employe.getNom());
            etat.setString(2, employe.getPrenom());
            etat.executeUpdate();

            etat.close();
            connexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Employe trouver(long id) throws SQLException {
        String sql = "SELECT * FROM EMPLOYE WHERE ID = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setLong(1, id);
            ResultSet resultat = etat.executeQuery();
            if (resultat.next()) {
                Employe employe = new Employe();
                employe.setId(resultat.getLong(1));
                employe.setNom(resultat.getString(2));
                employe.setPrenom(resultat.getString(3));

                return employe;
            }
        }catch (SQLException ex){
                    throw ex;
        }
        return null;
    } 
    
    

    public Employe modifier(Employe employe) {
        String sql = "UPDATE EMPLOYE SET nom = ? "
                + ", prenom = ? "
                + "WHERE id = ?";

        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setString(1, employe.getNom());
            etat.setString(2, employe.getPrenom());
            etat.setLong(3, employe.getId());
            etat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        try{
            return this.trouver(employe.getId());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void supprimer(Employe employe){
        String sql = "DELETE FROM EMPLOYE WHERE id = ?";
        try{
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setLong(1, employe.getId());
            etat.executeUpdate();
            
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public void supprimer(long id){
        String sql = "DELETE FROM EMPLOYE WHERE id = ?";
        try{
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setLong(1, id);
            etat.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public int compter(){
        String sql = "SELECT COUNT(*) FROM EMPLOYE";
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
