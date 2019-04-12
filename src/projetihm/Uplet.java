package projetihm;

/**
 * Cette classe permet de définir les attributs d'un upelt pour ensuite ajouter
 * les uplets depuis une observableListe vers une TableView
 *
 * @version 12/04/19
 * @author Biou Rémi, Foucher Chloé
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

    /**
     * Constructeur de la classe Uplet
     *
     * @param newUplet
     * @param newTypeCell
     * @param newAgent
     * @param newCellule
     * @param newRenouv
     * @param newUrgent
     * @param newDateEcheance
     * @param newEtat
     */
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
    /**
     * Getter de l'identifiant de l'uplet
     * @return id_uplet
     */
    public int getId_uplet() {
        return id_uplet;
    }
    /**
     * Getter du type de cellule de l'uplet
     * @return type_cell
     */
    public String getType_cell() {
        return type_cell;
    }
    /**
     * Getter de la quantité d'agent de l'uplet
     * @return q_agent
     */
    public String getQ_agent() {
        return q_agent;
    }
    /**
     * Getter de la quantité de cellules de l'uplet
     * @return q_cellule
     */
    public String getQ_cellule() {
        return q_cellule;
    }
    /**
     * Getter pour idenfier si l'uplet est renouvelé ou non
     * @return renouv
     */
    public String getRenouv() {
        return renouv;
    }
    /**
     * Getter pour idenfier si l'uplet est urgent ou non
     * @return urgent
     */
    public int getUrgent() {
        return urgent;
    }
    /**
     * Getter de la date d'échéance de l'uplet
     * @return date_echeance
     */
    public String getDate_echeance() {
        return date_echeance;
    }
    /**
     * Getter de l'état de l'uplet
     * @return etat
     */
    public String getEtat() {
        return etat;
    }
}
