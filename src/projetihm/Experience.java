package projetihm;

/**
 * Cette classe permet de définir les attributs d'une expérience pour ensuite
 * ajouter les expériences depuis une observableListe vers une TableView
 *
 * @version 12/04/19
 * @author Biou Rémi, Foucher Chloé
 */

public class Experience {

    //Déclaration des attributs
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

    /**
     * Constructeur de la classe Experience
     *
     * @param newID
     * @param newEmail
     * @param nexLibelle
     * @param newNB
     * @param newDateDem
     * @param newDateDeb
     * @param newStatut
     * @param newDateTrans
     * @param newType
     * @param newA1
     * @param newA2
     * @param newA3
     * @param newFreq
     * @param newDuree
     * @param newTerm
     */
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

    /**
     * getter de l'id de l'expérience
     *
     * @return id_exp
     */
    public int getId_exp() {
        return id_exp;
    }

    /**
     * getter de l'équipe de l'expérience
     *
     * @return email_equipe
     */
    public String getEmail_equipe() {
        return email_equipe;
    }

    /**
     * getter du libelle de l'expérience
     *
     * @return libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * getter du nombre de slots de l'expérience
     *
     * @return nb_slot
     */
    public int getNb_slot() {
        return nb_slot;
    }

    /**
     * getter de la date de demande de l'expérience
     *
     * @return date_demande
     */
    public String getDate_demande() {
        return date_demande;
    }

    /**
     * getter de la date de début de l'expérience
     *
     * @return date_deb
     */
    public String getDate_deb() {
        return date_deb;
    }

    /**
     * getter du statut de l'expérience
     *
     * @return statut
     */
    public String getStatut() {
        return statut;
    }

    /**
     * getter de la date de transmission de l'expérience
     *
     * @return date_transmission
     */
    public String getDate_transmission() {
        return date_transmission;
    }

    /**
     * getter du type d'expérience
     *
     * @return type_exp
     */
    public String getType_exp() {
        return type_exp;
    }

    /**
     * getter de la valeur de a1 de l'expérience
     *
     * @return a1
     */
    public int getA1() {
        return a1;
    }

    /**
     * getter de la valeur de a2 de l'expérience
     *
     * @return a2
     */
    public int getA2() {
        return a2;
    }

    /**
     * getter de la valeur de a3 de l'expérience
     *
     * @return a3
     */
    public int getA3() {
        return a3;
    }

    /**
     * getter de l'id de l'expérience
     *
     * @return frequence
     */
    public int getFrequence() {
        return frequence;
    }

    /**
     * getter de la durée de l'expérience
     *
     * @return duree
     */
    public int getDuree() {
        return duree;
    }

    /**
     * getter pour identifier si l'expérience est terminée ou non
     *
     * @return termine
     */
    public int getTermine() {
        return termine;
    }
}
