/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entité.ProduitAchete;
import java.sql.Connection;
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
public class ProduitAcheteDao {
    private final Connection connexion;
    
    public ProduitAcheteDao(){
        this.connexion = Connexion.getConnexion();
    }
    
    public void ajouter(ProduitAchete produitAchete){
        String sql = "INSERT INTO PRODUIT_ACHETE (remise, achat_id, produit_id)" +
                "VALUE (?, ?, ?)";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setDouble(1, produitAchete.getRemise());
            etat.setInt(2, (int) produitAchete.getAchat().getId());
            etat.setInt(3, (int) produitAchete.getProduit().getId());
            etat.executeUpdate();
            etat.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ProduitAchete trouver(long achat_id, long produit_id ) throws ObjetNonTrouveException, SQLException{
        String sql = "SELECT * FROM PRODUIT_ACHETE WHERE achat_id = ? AND produit_id = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setLong(1, achat_id);
            etat.setLong(2, produit_id);
            ResultSet resultat = etat.executeQuery();
            if (resultat.next()){
                ProduitAchete produitAchete = new ProduitAchete();

                produitAchete.setRemise(resultat.getInt(1));
                produitAchete.setAchatId(resultat.getInt(2));
                produitAchete.setProduitId(resultat.getInt(3));

                return produitAchete;
            } else{
                throw new ObjetNonTrouveException("ProduitAchete non trouvé");
            }
        } catch (SQLException | ObjetNonTrouveException e) {
            throw e;
        }
    }
    
    public ProduitAchete modifier(ProduitAchete produitAchete){
        String sql = "UPDATE PRODUIT_ACHETE SET remise = ? WHERE achat_id = AND" +
                "produit_id = ? ";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setDouble(1, produitAchete.getRemise());
            etat.setInt(2, (int) produitAchete.getAchat().getId());
            etat.setInt(3, (int) produitAchete.getProduit().getId());
            etat.executeUpdate();
            etat.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        try {
            return this.trouver(produitAchete.getAchat().getId(), produitAchete.getProduit().getId());
        } catch (SQLException | ObjetNonTrouveException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public void supprimer(ProduitAchete produitAchete) throws ObjetNonTrouveException{
         String sql = "DELETE FROM PRODUIT_ACHETE WHERE achat_id = ? AND produit_id = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setInt(1, (int) produitAchete.getAchat().getId());
            etat.setInt(2, (int) produitAchete.getProduit().getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public int compter(){
        String sql = "SELECT COUNT(*) FROM PRODUIT_ACHETE";
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
    
    public List<ProduitAchete> lister(){
        String sql = "SELECT * FROM PRODUIT_ACHETE";
        List<ProduitAchete> liste = new ArrayList<>() ;
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            ResultSet resultat = etat.executeQuery();
            
            if (resultat.next()) {
               liste.add((ProduitAchete) resultat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return liste;
    }
}
