/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm;

/**
 *
 * @author gphy
 */
public class Uplet {
    private final int id_uplet;
    private final String type_cell; 
    private final String q_agent; 
    private final String q_cellule; 
    private final String renouv; 
    private final int urgent; 
    private final String date_echeance; 
    private final String etat; 

    public Uplet(int newUplet, String newTypeCell, String newAgent, String newCellule, String newRenouv, int newUrgent, String newDateEcheance, String newEtat) {
    id_uplet = newUplet; 
    type_cell = newTypeCell; 
    q_agent = newAgent; 
    q_cellule = newCellule; 
    renouv = newRenouv; 
    urgent = newUrgent; 
    date_echeance = newDateEcheance; 
    etat = newEtat;
    }

    public int getId_uplet() {
        return id_uplet;
    }

    public String getType_cell() {
        return type_cell;
    }

    public String getQ_agent() {
        return q_agent;
    }

    public String getQ_cellule() {
        return q_cellule;
    }

    public String getRenouv() {
        return renouv;
    }

    public int getUrgent() {
        return urgent;
    }

    public String getDate_echeance() {
        return date_echeance;
    }

    public String getEtat() {
        return etat;
    }
}
