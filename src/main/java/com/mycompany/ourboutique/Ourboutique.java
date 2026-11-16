/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ourboutique;

import dao.ProduitDao;

/**
 *
 * @author jakk
 */
public class Ourboutique {
//    private final Produit p;
//    private final Categorie c;
    private static ProduitDao pd;
    private final String liste;
    
    public Ourboutique(){
//        c = new Categorie();
//        p = new Produit("libelle", 0, Date.valueOf("1234-12-12"), c);
        pd = new ProduitDao();
        liste = pd.lister().toString();
        System.out.println(liste);
    }
    
    
    public static void main(String[] args) {
        Ourboutique ob = new Ourboutique();
    }
}
