package projetihm;


import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gphy
 */
public class Experience {

    private final int id_exp;
    private final String email_equipe;
    private final String libelle;
    private final int nb_slot;
    private final String date_demande;
    private final String date_deb;
    private final String statut;
    private final String date_transmission; 
    private final String type_exp; 
    private final int a1; 
    private final int a2; 
    private final int a3; 
    private final int frequence; 
    private final int duree; 
    private final int termine; 

    public Experience(int newID, String newEmail, String nexLibelle, int newNB, String newDateDem, String newDateDeb, String newStatut, String newDateTrans, String newType, int newA1, int newA2, int newA3, int newFreq, int newDuree, int newTerm) {
        id_exp = newID; 
        email_equipe = newEmail;
        libelle = nexLibelle;
        nb_slot = newNB;
        date_demande = newDateDem;
        date_deb = newDateDeb;
        statut = newStatut;
        date_transmission = newDateTrans;
        type_exp = newType;
        a1 = newA1;
        a2 = newA2;
        a3 = newA3;
        frequence = newFreq; 
        duree = newDuree; 
        termine = newTerm; 
    }
    
     public int getId_exp() {
        return id_exp;
    }

    public String getEmail_equipe() {
        return email_equipe;
    }

    public String getLibelle() {
        return libelle;
    }

    public int getNb_slot() {
        return nb_slot;
    }

    public String getDate_demande() {
        return date_demande;
    }

    public String getDate_deb() {
        return date_deb;
    }

    public String getStatut() {
        return statut;
    }

    public String getDate_transmission() {
        return date_transmission;
    }

    public String getType_exp() {
        return type_exp;
    }

    public int getA1() {
        return a1;
    }

    public int getA2() {
        return a2;
    }

    public int getA3() {
        return a3;
    }

    public int getFrequence() {
        return frequence;
    }

    public int getDuree() {
        return duree;
    }

    public int getTermine() {
        return termine;
    }
}
