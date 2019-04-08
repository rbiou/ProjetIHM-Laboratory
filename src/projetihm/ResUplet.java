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
public class ResUplet {
    
    private int idUplet; 
    private int ecartTypeTR;
    private int moyenneTR;
    private int ecartTypeR;
    private int moyenneR;
    private int ecartTypeV;
    private int moyenneV;
    private int ecartTypeB;
    private int moyenneB;
    private String statutUplet;

    public ResUplet(int newID, int newEcartTypeTR, int newEcartTypeR, int newEcartTypeV, int newEcartTypeB, int newMoyTR, int newMoyR, int newMoyV, int newMoyB, String newStatutUplet) {
        idUplet = newID; 
        ecartTypeTR = newEcartTypeTR;
        ecartTypeV = newEcartTypeV;
        ecartTypeR = newEcartTypeR;
        ecartTypeB = newEcartTypeB;
        moyenneR = newMoyR;
        moyenneTR = newMoyTR;
        moyenneV = newMoyV;
        moyenneB = newMoyB;
        statutUplet = newStatutUplet; 
    }

    public int getEcartTypeTR() {
        return ecartTypeTR;
    }

    public int getMoyenneTR() {
        return moyenneTR;
    }

    public int getEcartTypeR() {
        return ecartTypeR;
    }

    public int getMoyenneR() {
        return moyenneR;
    }

    public int getEcartTypeV() {
        return ecartTypeV;
    }

    public int getMoyenneV() {
        return moyenneV;
    }

    public int getEcartTypeB() {
        return ecartTypeB;
    }

    public int getMoyenneB() {
        return moyenneB;
    }

    public String getStatutUplet() {
        return statutUplet;
    }

    public int getIdUplet() {
        return idUplet;
    }

}
