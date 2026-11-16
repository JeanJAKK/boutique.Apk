/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entité.Achat;
import entité.ProduitAchete;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.ObjetNonTrouveException;

/**
 *
 * @author jakk
 */
public class AchatDao {
    private final Connection connexion;
    
    public AchatDao(){
        this.connexion = Connexion.getConnexion();
    }
    
    public void ajouter(Achat achat){
        String sql = "INSERT INTO ACHAT (date_achat, remise, produit_achetes, employe, client" +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setDate(1, achat.getDateAchat());
            etat.setDouble(2, achat.getRemise());
            etat.setString(3, achat.getProduitAchete().toString());
            etat.setLong(4, achat.getClientId());
            etat.setLong(5, achat.getEmployeId());
        } catch (SQLException ex) {
            Logger.getLogger(AchatDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modifier(Achat achat){
        String sql = "UPDATE ACHAT SET date_achat = ?, remise = ?, produit_achete = ?"
                + "client_id = ?, employe_id = ? WHERE achaat_id = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setDate(1, achat.getDateAchat());
            etat.setDouble(2, achat.getRemise());
            etat.setString(3, achat.getProduitAchete().toString());
            etat.setLong(4, achat.getClientId());
            etat.setLong(5, achat.getEmployeId());
            etat.setLong(5, achat.getId());
        } catch (SQLException ex) {
            Logger.getLogger(AchatDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Achat trouver(long achat_id) throws ObjetNonTrouveException, SQLException{
        String sql = "SELECT * FROM ACHAT WHERE achat_id = ?";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setLong(1, achat_id);
            ResultSet resultat = etat.executeQuery();
            if (resultat.next()){
                Achat achat = new Achat();
                achat.setDateAchat(resultat.getDate(1));
                achat.setRemise(resultat.getInt(2));
                achat.setProduitAchete((List<ProduitAchete>) resultat.getObject(3));
                achat.setClientId(resultat.getInt(4));
                achat.setEmployeId(resultat.getInt(5));
                return achat;
            } else{
                throw new ObjetNonTrouveException("Achat non trouvé");
            }
        } catch (SQLException | ObjetNonTrouveException e) {
            throw e;
        }
    }
    
    public void supprimer(ProduitAchete produitAchete) throws ObjetNonTrouveException{
         String sql = "DELETE FROM ACHAT WHERE achat_id = ? ";
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            etat.setInt(1, (int) produitAchete.getAchat().getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public int compter(){
        String sql = "SELECT COUNT(*) FROM ACHAT";
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
    
    public List<Achat> lister(){
        String sql = "SELECT * FROM ACHAT";
        List<Achat> liste = new ArrayList<>() ;
        try {
            PreparedStatement etat = connexion.prepareStatement(sql);
            ResultSet resultat = etat.executeQuery();
            
            if (resultat.next()) {
               liste.add((Achat) resultat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return liste;
    }
}
