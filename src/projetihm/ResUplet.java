package projetihm;

/**
 * Cette classe permet de définir les attributs d'un résultat d'uplet pour
 * ensuite ajouter les résultats depuis une observableListe vers une TableView
 *
 * @version 12/04/19
 * @author Biou Rémi, Foucher Chloé
 */
public class ResUplet {

    private int idUplet;
    private double ecartTypeTR;
    private double moyenneTR;
    private double ecartTypeR;
    private double moyenneR;    
    private double ecartTypeV;
    private double moyenneV;
    private double ecartTypeB;
    private double moyenneB;
    private String statutUplet;

    /**
     * Constructeur de la classe ResUplet
     *
     * @param newID
     * @param newEcartTypeTR
     * @param newEcartTypeR
     * @param newEcartTypeV
     * @param newEcartTypeB
     * @param newMoyTR
     * @param newMoyR
     * @param newMoyV
     * @param newMoyB
     * @param newStatutUplet
     */
    public ResUplet(int newID, double newEcartTypeTR, double newEcartTypeR, double newEcartTypeV, double newEcartTypeB, double newMoyTR, double newMoyR, double newMoyV, double newMoyB, String newStatutUplet) {
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

    /**
     * getter de l'écart-type (transparence) du résultat de l'uplet
     *
     * @return ecartTypeTR
     */
    public double getEcartTypeTR() {
        return ecartTypeTR;
    }

    /**
     * getter de la moyenne (transparence) du résultat de l'uplet
     *
     * @return moyenneTR
     */
    public double getMoyenneTR() {
        return moyenneTR;
    }

    /**
     * getter de l'écart-type (rouge) du résultat de l'uplet
     *
     * @return ecartTypeR
     */
    public double getEcartTypeR() {
        return ecartTypeR;
    }

    /**
     * getter de la moyenne (rouge) du résultat de l'uplet
     *
     * @return moyenneR
     */
    public double getMoyenneR() {
        return moyenneR;
    }

    /**
     * getter de l'écart-type (vert) du résultat de l'uplet
     *
     * @return ecartTypeV
     */
    public double getEcartTypeV() {
        return ecartTypeV;
    }

    /**
     * getter de la moyenne (vert) du résultat de l'uplet
     *
     * @return moyenneV
     */
    public double getMoyenneV() {
        return moyenneV;
    }

    /**
     * getter de l'écart-type (bleu) du résultat de l'uplet
     *
     * @return ecartTypeB
     */
    public double getEcartTypeB() {
        return ecartTypeB;
    }

    /**
     * getter de la moyenne (bleu) du résultat de l'uplet
     *
     * @return moyenneB
     */
    public double getMoyenneB() {
        return moyenneB;
    }

    /**
     * getter du statut du résultat de l'uplet
     *
     * @return statutUplet
     */
    public String getStatutUplet() {
        return statutUplet;
    }

    /**
     * getter de l'identifiant du résultat de l'uplet
     *
     * @return idUplet
     */
    public int getIdUplet() {
        return idUplet;
    }

}
