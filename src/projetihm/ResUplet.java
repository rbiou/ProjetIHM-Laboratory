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
    private int ecartTypeTR;
    private int moyenneTR;
    private int ecartTypeR;
    private int moyenneR;
    private int ecartTypeV;
    private int moyenneV;
    private int ecartTypeB;
    private int moyenneB;
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

    /**
     * getter de l'écart-type (transparence) du résultat de l'uplet
     *
     * @return ecartTypeTR
     */
    public int getEcartTypeTR() {
        return ecartTypeTR;
    }

    /**
     * getter de la moyenne (transparence) du résultat de l'uplet
     *
     * @return moyenneTR
     */
    public int getMoyenneTR() {
        return moyenneTR;
    }

    /**
     * getter de l'écart-type (rouge) du résultat de l'uplet
     *
     * @return ecartTypeR
     */
    public int getEcartTypeR() {
        return ecartTypeR;
    }

    /**
     * getter de la moyenne (rouge) du résultat de l'uplet
     *
     * @return moyenneR
     */
    public int getMoyenneR() {
        return moyenneR;
    }

    /**
     * getter de l'écart-type (vert) du résultat de l'uplet
     *
     * @return ecartTypeV
     */
    public int getEcartTypeV() {
        return ecartTypeV;
    }

    /**
     * getter de la moyenne (vert) du résultat de l'uplet
     *
     * @return moyenneV
     */
    public int getMoyenneV() {
        return moyenneV;
    }

    /**
     * getter de l'écart-type (bleu) du résultat de l'uplet
     *
     * @return ecartTypeB
     */
    public int getEcartTypeB() {
        return ecartTypeB;
    }

    /**
     * getter de la moyenne (bleu) du résultat de l'uplet
     *
     * @return moyenneB
     */
    public int getMoyenneB() {
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
