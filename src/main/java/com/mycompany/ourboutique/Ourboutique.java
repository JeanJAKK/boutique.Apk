/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ourboutique;

import dao.ProduitDao;
import entité.Categorie;
import entité.Produit;
import java.sql.Date;

/**
 *
 * @author jakk
 */
public class Ourboutique {
    private final Produit p;
    private final Categorie c;
    private static ProduitDao pd;
    
    public Ourboutique(){
        c = new Categorie();
        p = new Produit("libelle", 0, Date.valueOf("1234-12-12"), c);
        pd = new ProduitDao();
        pd.ajouter(p);
    }
    
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
