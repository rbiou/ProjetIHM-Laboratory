package projetihm;

import java.sql.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;

/**
 * Controller de notre application de gestion de laboratoire
 *
 * @author Rémi BIOU, Chloé FOUCHER
 * @version 28/03/2019
 */
public class FXMLDocumentController implements Initializable {

    //Variables FXML
    @FXML
    private ImageView homeIcon, expIcon, upletIcon, eyeIcon, addIcon, plaqueIcon;
    @FXML
    private AnchorPane topPanel, welcomePanel, navPanel, loginPanel, menuPanel, insertPanel, viewPanel, upletPanel;
    @FXML
    private ScrollPane viewdetailsPanel, plaqueScrollPane;
    @FXML
    private Label datasuiviLabel, fonctionLabel, welcomeLabel, loginErrorLabel, frequenceLabel, a3Label, frequenceviewLabel, a3viewLabel, datalibelleLabel, dataequipeLabel, datastatutLabel, dataa1Label, dataa2Label, datadatetransmissionLabel, datafrequenceLabel, datatypeLabel, datadatedemandeLabel, datadatedebutLabel, datanbslotsLabel, datadureeLabel, dataa3Label, slotsRestantsLabel, warningLabel, warningUpletLabel;
    @FXML
    private TableView viewTable;
    @FXML
    private TextField mailTextField, passwordTextField, frequenceTextField, a3TextField, libelleTextField, a1TextField, a2TextField, nbSlotTextField, dureeExpTextField, qteCelluleTextField, qteAgentTextField;
    @FXML
    private DatePicker dateDemandeDate;
    @FXML
    private RadioButton ouiRadio, nonRadio;
    @FXML
    private GridPane rectPlaque;
    @FXML
    private ComboBox equipeComboBox, typeExpComboBox, typeCelluleComboBox, choixExperienceComboBox, choixReplicatComboBox, choixPlaqueComboBox, expSelectedComboBox, nomAgentComboBox;
    @FXML
    private TableView viewTableView;
    @FXML
    private TableColumn idColumn1, libelleColumn1, typeColumn1, datedemandeColumn1, equipeColumn1, launcherColumn1, statutColumn1, detailsColumn1;
    //Variables d'environnement user
    private static String nameUser, functionUser;
    //Variables non-FXML
    private Integer existingUser, slots_to_check, nb_checked_slots, c, l;
    private Connection con;
    private ObservableList<Experience> listExp = FXCollections.observableArrayList();
    private ArrayList<ArrayList> new_slots;

    /**
     * Méthode qui permet de connecter l'utilisateur à notre application en
     * contrôlant ses identifiants qui renvoie ensuite au tableau de bord de
     * l'application
     *
     * @param event
     */
    @FXML
    private void connectUser(ActionEvent event) {
        //Contrôle si les champs de saisie pour se connecter ne sont pas vides
        if (mailTextField.getText().isEmpty() == false && passwordTextField.getText().isEmpty() == false) {
            //Connexion en utilisant la BDD
            try {
                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery(
//                        "SELECT PERSONNE.PRENOM_PERSONNE, FONCTION.LIBELLE_FONCTION "
//                        + "FROM PERSONNE "
//                        + "JOIN FONCTION ON PERSONNE.ID_FONCTION = FONCTION.ID_FONCTION "
//                        + "WHERE EMAIL_PERSONNE ='" + mailTextField.getText() + "' AND PASSWORD = ENCRYPTER('" + passwordTextField.getText() + "')");
                existingUser = 1;
                nameUser = "Remi";
                functionUser = "laborantin";
//                while (rs.next()) {
//                    existingUser = 1;
//                    nameUser = rs.getString(1);
//                    functionUser = rs.getString(2);
//                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            if (existingUser > 0) {
                //Affichage du profil de l'utilisateur sur le tableau de bord
                welcomeLabel.setText("Bienvenue " + nameUser);
                fonctionLabel.setText("Fonction : " + functionUser);
                //Réinitialisation des champs de saisies pour se connecter
                mailTextField.setText("");
                passwordTextField.setText("");
                //Gestion des pages
                welcomePanel.setVisible(true);
                welcomeLabel.setVisible(true);
                fonctionLabel.setVisible(true);
                menuPanel.setVisible(true);
                loginPanel.setVisible(false);
                navPanel.setVisible(true);
                loginErrorLabel.setVisible(false);
            } else {
                //Affichage d'un message d'erreur
                loginErrorLabel.setText("Mauvais identifiants.");
                loginErrorLabel.setVisible(true);
            }
        } else {
            //Affichage d'un message d'erreur
            loginErrorLabel.setText("Tous les champs doivent être remplis.");
            loginErrorLabel.setVisible(true);
        }
    }

    /**
     * Méthode permettant la déconnexion de l'utilisateur connecté sur
     * l'interface où la méthode est appelée
     *
     * @param event
     */
    @FXML
    private void logOff(MouseEvent event) {
        //Gestion des pages
        loginPanel.setVisible(true);
        navPanel.setVisible(false);
        welcomePanel.setVisible(false);
        menuPanel.setVisible(false);
    }

    /**
     * Méthode permettant l'affichage du panel central de gestion des
     * expériences (où l'on retrouve la possibilité d'ajouter une expérience,
     * une plaque, ...) ainsi que la gestion de l'UX des menus (surlignage du
     * module où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setExperiencePanel(MouseEvent event) {
        //Gestion des pages & de l'UX dans le cas d'un utilisateur étant chercheur
        if ("chercheur".equals(functionUser)) {
            upletIcon.setVisible(true);
            addIcon.setVisible(true);
            eyeIcon.setVisible(false);
            plaqueIcon.setVisible(false);
            insertPanel.setVisible(true);
            upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
            addIcon.setImage(new Image(getClass().getResource("add_white.png").toExternalForm()));
            //Gestion des pages & de l'UX dans le cas d'un utilisateur étant laborantin
        } else if ("laborantin".equals(functionUser)) {
            upletIcon.setVisible(false);
            addIcon.setVisible(false);
            eyeIcon.setVisible(true);
            plaqueIcon.setVisible(true);
            viewPanel.setVisible(true);
            eyeIcon.setImage(new Image(getClass().getResource("eye_white.png").toExternalForm()));
            plaqueIcon.setImage(new Image(getClass().getResource("square_black.png").toExternalForm()));
        }
        //Remise à 0 du label warning
        warningLabel.setText("");
        //remise à 0 des comboBox
        equipeComboBox.getItems().clear();
        typeExpComboBox.getItems().clear();
        //Récupération des équipes enregistrés et ajout à la ComboBox du choix de l'équipe dans le formulaire
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOM_EQUIPE FROM EQUIPE");
            while (rs.next()) {
                equipeComboBox.getItems().add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //Ajout des propositions de choix à la ComboBox de choix du type d'expérience
        typeExpComboBox.getItems().addAll("colorimétrique", "opacimétrique");
        //Gestion des pages et de l'UX
        topPanel.setVisible(true);
        welcomePanel.setVisible(false);
        menuPanel.setVisible(false);
        plaqueScrollPane.setVisible(false);
        upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
        homeIcon.setImage(new Image(getClass().getResource("home_black.png").toExternalForm()));
        expIcon.setImage(new Image(getClass().getResource("flask_white.png").toExternalForm()));
    }

    /**
     * Méthode permettant l'affichage du panel central permettant d'avoir une
     * vue d'ensemble des fonctionnalités disponibles à l'utilisateur, ainsi que
     * son profil avec la possibilité de se déconnecter ainsi que la gestion de
     * l'UX des menus (surlignage du module où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setHomePanel(MouseEvent event) {
        //Gestion des pages et de l'UX
        topPanel.setVisible(false);
        welcomePanel.setVisible(true);
        menuPanel.setVisible(true);
        viewPanel.setVisible(false);
        upletPanel.setVisible(false);
        insertPanel.setVisible(false);
        plaqueScrollPane.setVisible(false);
        homeIcon.setImage(new Image(getClass().getResource("home_white.png").toExternalForm()));
        expIcon.setImage(new Image(getClass().getResource("flask_black.png").toExternalForm()));
    }

    /**
     * Méthode permettant l'affichage du panel central permettant la gestion des
     * plaques et le positionnement des puits ainsi que la gestion de l'UX des
     * menus (surlignage du module où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setPlaquePanel(MouseEvent event) {
        //Selection de l'expérience et de l'uplet/réplica à traiter
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT ID_EXPERIENCE, LIBELLE_EXP, NOM_EQUIPE "
                    + "FROM EXPERIENCE "
                    + "JOIN EQUIPE ON EXPERIENCE.EMAIL_EQUIPE = EQUIPE.EMAIL_EQUIPE");
            while (rs.next()) {
                choixExperienceComboBox.getItems().add(rs.getString(1) + " - " + rs.getString(2) + " - Equipe " + rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        //Selection de la plaque à traiter
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT NUM_PLAQUE, TYPE_PLAQUE "
                    + "FROM PLAQUE "
                    + "WHERE REFUS = 0");
            while (rs.next()) {
                choixPlaqueComboBox.getItems().add("Plaque n° " + rs.getString(1) + " - " + rs.getString(2) + " slots");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        //Gestion des pages et de l'UX
        eyeIcon.setImage(new Image(getClass().getResource("eye_black.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
        plaqueIcon.setImage(new Image(getClass().getResource("square_white.png").toExternalForm()));
        topPanel.setVisible(true);
        viewPanel.setVisible(false);
        insertPanel.setVisible(false);
        upletPanel.setVisible(false);
        plaqueScrollPane.setVisible(true);
    }

    /**
     * Méthode permettant l'affichage graphique de la plaque permettant de
     * positionner les expériences sollicités par les chercheurs
     *
     * @param event
     */
    @FXML
    private void setSlotsPositionChecker(ActionEvent event) {
        //Initialisation du nombre de slots cochés
        nb_checked_slots = 0;
        if (choixPlaqueComboBox.getValue() != null && choixExperienceComboBox.getValue() != null) {
            //Liste des slots/réplicas occupés
            ArrayList<ArrayList> taken_slots = new ArrayList<>();
            //Liste des slots/réplicas cochés par l'utilisateur
            new_slots = new ArrayList<ArrayList>();
            //Recherche des propriétés de la plaque : slots disponibles, slots occupés
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT X,Y "
                        + "FROM SLOT "
                        + "WHERE NUM_PLAQUE = " + (choixPlaqueComboBox.getValue() + "").split(" ")[2]);
                while (rs.next()) {
                    taken_slots.add(new ArrayList<>(Arrays.asList(rs.getInt(1), rs.getInt(2))));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT NB_SLOT "
                        + "FROM EXPERIENCE "
                        + "WHERE ID_EXPERIENCE = " + (choixExperienceComboBox.getValue() + "").split(" ")[0]);
                while (rs.next()) {
                    slots_to_check = rs.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT COUNT(*) "
                        + "FROM N_UPLET "
                        + "WHERE ID_EXPERIENCE = " + (choixExperienceComboBox.getValue() + "").split(" ")[0]);
                while (rs.next()) {
                    slots_to_check = slots_to_check * rs.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            //Re-initialisation des contraintes de dimensionnement de l'affichage graphique de la plaque
            rectPlaque.getRowConstraints()
                    .clear();
            rectPlaque.getColumnConstraints()
                    .clear();
            //Initiatilasion des variables de boucles, du nombre de colonnes et de lignes à 0
            c = 0;
            l = 0;
            Integer i, j;
            //Si la plaque de culture à 96 puits, alors l'affichage doit être sous forme de 12 colonnes et 8 lignes 

            if ("96".equals(
                    (choixPlaqueComboBox.getValue() + "").split(" ")[4])) {
                c = 12;
                l = 8;
                //Si la plaque de culture à 384 puits, alors l'affichage doit être sous forme de 24 colonnes et 16 lignes 
            } else if ("384".equals(
                    (choixPlaqueComboBox.getValue() + "").split(" ")[4])) {
                c = 24;
                l = 16;
            }
            //Ajout, ligne par ligne, colonne par colonne, de boutons radios représentant les puits au GridPane représentant la plaque
            i = 0;
            while (i < l) {
                j = 0;
                while (j < c) {
                    if (taken_slots.contains(Arrays.asList(i, j))) {
                        RadioButton disableButton = new RadioButton();
                        disableButton.setDisable(true);
                        disableButton.setSelected(true);
                        rectPlaque.add(disableButton, j, i);
                    } else {
                        RadioButton availableButton = new RadioButton();
                        availableButton.setStyle("-fx-mark-highlight-color: #88d9e6 ; -fx-mark-color: #88d9e6;");
                        availableButton.setOnMouseEntered((MouseEvent event1) -> {
                            //Nombre de slots/réplicas cochés
                            if (availableButton.isSelected() == false && availableButton.isDisabled() == false) {
                                availableButton.setSelected(true);
                                nb_checked_slots++;
                                new_slots.add(new ArrayList<>(Arrays.asList(GridPane.getColumnIndex(availableButton), GridPane.getRowIndex(availableButton))));
                            } else if (availableButton.isDisabled() == false) {
                                availableButton.setSelected(false);
                                nb_checked_slots--;
                                new_slots.remove(new ArrayList<>(Arrays.asList(GridPane.getColumnIndex(availableButton), GridPane.getRowIndex(availableButton))));
                            }
                            System.out.println(new_slots);
                            if (slots_to_check - nb_checked_slots > 0) {
                                slotsRestantsLabel.setVisible(true);
                                slotsRestantsLabel.setText((slots_to_check - nb_checked_slots) + " puits restants à déterminer");
                                ObservableList<Node> childrens = rectPlaque.getChildren();
                                childrens.stream().map((node) -> (RadioButton) node).filter((cb) -> (cb.isSelected() == false && cb.isDisabled())).forEachOrdered((cb) -> {
                                    cb.setDisable(false);
                                });
                            } else if (slots_to_check - nb_checked_slots == 0) {
                                slotsRestantsLabel.setVisible(false);
                                ObservableList<Node> childrens = rectPlaque.getChildren();
                                childrens.stream().map((node) -> (RadioButton) node).filter((cb) -> (cb.isSelected() == false)).forEachOrdered((cb) -> {
                                    cb.setDisable(true);
                                });
                            }
                        });
                        rectPlaque.add(availableButton, j, i);
                    }
                    j++;
                }
                i++;
            }
            //Redimensionnement de l'affichage des colonnes de manière égale selon le nombre de colonnes de puits
            for (i = 1;
                    i < c;
                    i++) {
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(100.0 / c);
                rectPlaque.getColumnConstraints().add(colConst);
            }
            //Redimensionnement de l'affichage des lignes de manière égale selon le nombre de lignes de puits
            for (i = 1;
                    i < l;
                    i++) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(100.0 / l);
                rectPlaque.getRowConstraints().add(rowConst);
            }
        }
    }

    /**
     * Méthode permettant l'insertion slot par slot des slots que l'utilisateur
     * vient de cocher sur le panel rectPlaque Les slots cochés sont récupérés à
     * partir de la liste new_slots.
     *
     * @param event
     */
    @FXML
    private void insertSlots(ActionEvent event) {
        for (ArrayList new_slot : new_slots) {
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "INSERT INTO SLOT (ID_UPLET, NUM_PLAQUE, X, Y)"
                        + "VALUES (" + (choixReplicatComboBox.getValue() + "").split("°")[1] + "," + (choixPlaqueComboBox.getValue() + "").split(" ")[2] + "," + new_slot.get(1) + "," + new_slot.get(0) + ")");
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        //Remise à Z du nombre de slots cochés
        nb_checked_slots = 0;
        //Remise à Z de la liste des slots à ajouter
        new_slots.clear();
        rectPlaque.getChildren().clear();
    }

    /**
     * Méthode permettant l'affichage du panel central permettant la
     * visualisation de toutes les expériences stockées ainsi que la gestion de
     * l'UX des menus (surlignage du module où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setViewPanel(MouseEvent event) {
        TableExp();
        //Gestion des pages et de l'UX
        eyeIcon.setImage(new Image(getClass().getResource("eye_white.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
        plaqueIcon.setImage(new Image(getClass().getResource("square_black.png").toExternalForm()));
        viewPanel.setVisible(true);
        insertPanel.setVisible(false);
        upletPanel.setVisible(false);
        plaqueScrollPane.setVisible(false);
        viewdetailsPanel.setVisible(false);
    }

    /**
     * Méthode permettant l'affichage du panel central permettant l'ajout d'une
     * expérience ainsi que la gestion de l'UX des menus (surlignage du module
     * où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setInsertPanel(MouseEvent event) {
        //Suppression des propositions de choix dans les ComboBox du choix du type d'exp. et d'équipe pour pouvoir les MAJ
        equipeComboBox.getItems().clear();
        typeExpComboBox.getItems().clear();
        //Récupération des équipes enregistrés et ajout à la ComboBox du choix de l'équipe dans le formulaire
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOM_EQUIPE FROM EQUIPE");
            while (rs.next()) {
                equipeComboBox.getItems().add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //Ajout des propositions de choix à la ComboBox de choix du type d'expérience
        typeExpComboBox.getItems().addAll("colorimétrique", "opacimétrique");
        //Gestion des pages et de l'UX
        eyeIcon.setImage(new Image(getClass().getResource("eye_black.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_white.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
        plaqueIcon.setImage(new Image(getClass().getResource("square_black.png").toExternalForm()));
        viewPanel.setVisible(false);
        insertPanel.setVisible(true);
        upletPanel.setVisible(false);
        plaqueScrollPane.setVisible(false);
    }

    /**
     * Méthode permettant l'affichage du panel central permettant l'ajout d'un
     * uplet/réplica de la part du chercheur ainsi que la gestion de l'UX des
     * menus (surlignage du module où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setUpletPanel(MouseEvent event
    ) {
        //Ajout des propositions de choix à la ComboBox de choix du type de cellules
        typeCelluleComboBox.getItems().clear();
        typeCelluleComboBox.getItems().addAll("cancéreuse", "non-cancéreuse");
        //Gestion des pages et de l'UX
        eyeIcon.setImage(new Image(getClass().getResource("eye_black.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_white.png").toExternalForm()));
        plaqueIcon.setImage(new Image(getClass().getResource("square_black.png").toExternalForm()));
        plaqueScrollPane.setVisible(false);
        viewPanel.setVisible(false);
        insertPanel.setVisible(false);
        upletPanel.setVisible(true);
        //Remise à 0 des comboBox
        expSelectedComboBox.getItems().clear();
        nomAgentComboBox.getItems().clear();
        warningUpletLabel.setText("");
        // Affichage de la liste des expériences
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT ID_EXPERIENCE, LIBELLE_EXP, NOM_EQUIPE "
                    + "FROM EXPERIENCE "
                    + "JOIN EQUIPE ON EXPERIENCE.EMAIL_EQUIPE = EQUIPE.EMAIL_EQUIPE");
            while (rs.next()) {
                expSelectedComboBox.getItems().add(rs.getString(1) + " - " + rs.getString(2) + " - Equipe " + rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Affichage deu produit
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT NOM_PRODUIT "
                    + "FROM PRODUIT");
            while (rs.next()) {
                String produit = rs.getString(1);
                nomAgentComboBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Méthode activant l'affichage du formulaire permettant la saisie des
     * paramètres d'une expérience suivi dans le temps, lors de l'ajout d'une
     * expérience
     *
     * @param event
     */
    @FXML
    private void expSuiviTempsYes(ActionEvent event
    ) {
        //Gestion des pages et de l'UX
        frequenceLabel.setVisible(true);
        a3Label.setVisible(true);
        frequenceTextField.setVisible(true);
        a3TextField.setVisible(true);
        nonRadio.setSelected(false);
    }

    /**
     * Méthode désactivant l'affichage du formulaire permettant la saisie des
     * paramètres d'une expérience suivi dans le temps, lors de l'ajout d'une
     * expérience
     *
     * @param event
     */
    @FXML
    private void expSuiviTempsNo(ActionEvent event
    ) {
        //Gestion des pages et de l'UX
        frequenceLabel.setVisible(false);
        a3Label.setVisible(false);
        frequenceTextField.setVisible(false);
        a3TextField.setVisible(false);
        ouiRadio.setSelected(false);
    }

    /**
     * Méthode permettant l'ajout d'une expérience à la base de données
     *
     * @param event
     */
    @FXML
    private void ajoutExperience(ActionEvent event) {
        String libelle = libelleTextField.getText();
        LocalDate localdate = dateDemandeDate.getValue();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateString = localdate.format(dateTimeFormatter);
        String frequence = frequenceTextField.getText();
        String a3 = a3TextField.getText();
        String a1 = a1TextField.getText();
        String a2 = a2TextField.getText();
        String nb_slot = nbSlotTextField.getText();
        String duree = dureeExpTextField.getText();
        String nomEquipe = (String) equipeComboBox.getValue();
        String typeExp = (String) typeExpComboBox.getValue();
        String email = "";
        String statut = "à faire";
        warningLabel.setVisible(false);

        if (libelle.isEmpty() == false && dateString.isEmpty() == false && a1.isEmpty() == false && a2.isEmpty() == false && nb_slot.isEmpty() == false && duree.isEmpty() == false && nomEquipe.isEmpty() == false && typeExp.isEmpty() == false) {

            //Récupération de l'email de l'équipe selectionnée
            try {
                Statement stmt = con.createStatement();
                ResultSet rs1 = stmt.executeQuery("SELECT EMAIL_EQUIPE FROM EQUIPE WHERE NOM_EQUIPE ='" + nomEquipe + "'");
                rs1.next();
                email = rs1.getString(1);
                System.out.println(email);
            } catch (SQLException e) {
                System.out.println(e);
            }

            //requête d'insertion d'une expérience
            try {
                if (!"".equals(a3) && !"".equals(frequence)) {
                    Statement stmt1 = con.createStatement();
                    ResultSet rs1 = stmt1.executeQuery("INSERT INTO EXPERIENCE (LIBELLE_EXP, EMAIL_EQUIPE, DATE_DEMANDE, TYPE_EXP, A1, A2, NB_SLOT, DUREE_EXP, A3, STATUT_EXP, FREQUENCE) values ('" + libelle + "', '" + email + "', '" + dateString + "', '" + typeExp + "', " + a1 + ", " + a2 + ", " + nb_slot + ", " + duree + ", " + a3 + ", '" + statut + "', '" + frequence + "')");
                    warningLabel.setText("");
                    warningLabel.setText("Expérience ajoutée.");
                    warningLabel.setVisible(true);
                } else {
                    Statement stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("INSERT INTO EXPERIENCE (LIBELLE_EXP, EMAIL_EQUIPE, DATE_DEMANDE, TYPE_EXP, A1, A2, NB_SLOT, DUREE_EXP, STATUT_EXP) "
                            + "values ('" + libelle + "', '" + email + "', '" + dateString + "', '" + typeExp + "', " + a1 + ", " + a2 + ", " + nb_slot + ", " + duree + ", '" + statut + "')");
                    warningLabel.setText("");
                    warningLabel.setText("Expérience ajoutée.");
                    warningLabel.setVisible(true);
                }

            } catch (SQLException e) {
                System.out.println("Exception SQL : ");
                while (e != null) {
                    String message = e.getMessage();
                    int errorCode = e.getErrorCode();
                    if (errorCode == 984) {
                        warningLabel.setText("");
                        warningLabel.setText("Une valeur saisie est incorrecte.");
                        warningLabel.setVisible(true);
                    } else if (errorCode == 2290) {
                        warningLabel.setText("");
                        warningLabel.setText("La valeur de a1 doit être supérieure à celle de a2.");
                        warningLabel.setVisible(true);
                    }
                    e = e.getNextException();
                }
            }
        } else {
            warningLabel.setText("");
            warningLabel.setText("Tous les champs ne sont pas complets.");
            warningLabel.setVisible(true);
        }
    }

    /**
     * Méthode permettant l'ajout d'un uplet
     *
     * @param event
     */
    @FXML
    private void ajoutUplet(ActionEvent event) {
        if (expSelectedComboBox.getValue() != null && typeCelluleComboBox.getValue() != null && qteAgentTextField.getText().isEmpty() == false && qteCelluleTextField.getText().isEmpty() == false) {
            //Ajout de l'uplet
            try {
                Statement stmt1 = con.createStatement();
                ResultSet rs1 = stmt1.executeQuery("INSERT INTO N_UPLET (ID_EXPERIENCE, TYPE_CELLULE, Q_AGENT, Q_CELLULE) values (" + (expSelectedComboBox.getValue() + "").split(" ")[0] + ",  '" + typeCelluleComboBox.getValue() + "', '" + qteAgentTextField.getText() + "', '" + qteCelluleTextField.getText() + "' )");
                warningUpletLabel.setText("");
                warningUpletLabel.setText("Uplet ajouté.");
                warningUpletLabel.setVisible(true);
            } catch (SQLException e) {
                while (e != null) {
                    String message = e.getMessage();
                    int errorCode = e.getErrorCode();
                    e = e.getNextException();
                    if (errorCode == 1722) {
                        warningUpletLabel.setText("");
                        warningUpletLabel.setText("Valeur non valide.");
                        warningUpletLabel.setVisible(true);
                    }
                }
            }
        } else {
            warningUpletLabel.setText("");
            warningUpletLabel.setText("Veuillez remplir tous les champs.");
            warningUpletLabel.setVisible(true);
        }
    }

    /**
     * Méthode permettant la recherche d'expérience parmis celle en base de
     * données selon 3 critères non-obligatoires : - l'équipe commanditaire - le
     * statut de l'expérience - la date de début de l'expérience
     *
     * @param event
     */
    @FXML
    private void searchExperience(ActionEvent event
    ) {
        //
    }

    /**
     * Méthode permettant l'envoi des résultats de l'expérience sélectionnée à
     * l'équipe de recherche l'ayant commandité
     *
     * @param event
     */
    @FXML
    private void sendResults(ActionEvent event
    ) {
        //
    }

    public void reloadTableView() {
        listExp.clear();
        //remise à 0 de la table view
        viewTableView.getColumns().clear();
        //récupération de toutes les expériences
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_EXPERIENCE, NOM_EQUIPE, LIBELLE_EXP, NB_SLOT, DATE_DEMANDE, DATE_DEB_EXP, STATUT_EXP, DATE_TRANSMISSION, TYPE_EXP, A1, A2, A3, FREQUENCE, DUREE_EXP, TERMINE FROM EXPERIENCE JOIN EQUIPE USING(EMAIL_EQUIPE)");
            while (rs.next()) {
                Experience experience = new Experience(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getInt(15));
                listExp.add(experience);
            }
        } catch (SQLException ex) {
            //
        }
        System.out.println(listExp);

        TableColumn<Experience, String> colonneLibelle = new TableColumn<>("Libelle");
        colonneLibelle.setCellValueFactory(new PropertyValueFactory("libelle"));
        colonneLibelle.setStyle("-fx-alignment: CENTER;");
        ///////////////////////////////////////////////////////////////////////////
        TableColumn<Experience, String> colonneDateDemande = new TableColumn<>("Date de demande");
        colonneDateDemande.setCellValueFactory(new PropertyValueFactory("date_demande"));
        colonneDateDemande.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<Experience, String> colonneEquipe = new TableColumn<>("Equipe commanditaire");
        colonneEquipe.setCellValueFactory(new PropertyValueFactory("email_equipe"));
        colonneEquipe.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////////
        TableColumn<Experience, String> colonneTypeExp = new TableColumn<>("Type d'expérience");
        colonneTypeExp.setCellValueFactory(new PropertyValueFactory("type_exp"));
        colonneTypeExp.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<Experience, String> colonneStatut = new TableColumn<>("Statut");
        colonneStatut.setCellValueFactory(new PropertyValueFactory("statut"));
        colonneStatut.setStyle("-fx-alignment: CENTER;");
        ///////////////////////////////////////////////////////////////////////////
        viewTableView.setItems(listExp);
        viewTableView.getColumns().addAll(colonneLibelle, colonneEquipe, colonneDateDemande, colonneTypeExp, colonneStatut);
    }

    public void TableExp() {
        reloadTableView();
        TableColumn<Experience, Void> colonneStart = new TableColumn<>("Gestion de l'expérience");

        Callback<TableColumn<Experience, Void>, TableCell<Experience, Void>> cellFactory;
        cellFactory = (final TableColumn<Experience, Void> param) -> {
            final TableCell<Experience, Void> cell = new TableCell<Experience, Void>() {
                private final Button btnPlay = new Button();

                {
                    btnPlay.setOnAction((ActionEvent event) -> {
                        int Idexperience = getTableView().getItems().get(getIndex()).getId_exp();
                        try {
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("UPDATE EXPERIENCE SET STATUT_EXP = 'en cours' WHERE ID_EXPERIENCE = " + Idexperience + " ");
                            TableExp();
                        } catch (SQLException ex) {
                            //
                        }
                    });
                }
                private final Button btnStop = new Button();

                {
                    btnStop.setOnAction((ActionEvent event) -> {
                        int Idexperience = getTableView().getItems().get(getIndex()).getId_exp();
                        try {
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("UPDATE EXPERIENCE SET TERMINE = 1 WHERE ID_EXPERIENCE = " + Idexperience + " ");
                        } catch (SQLException ex) {
                            //
                        }
                        TableExp();
                    });
                }
                private final Label termine = new Label("Terminée");

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Experience experience = getTableView().getItems().get(getIndex());
                        if ("en cours".equals(experience.getStatut())) {
                            btnStop.setGraphic(new ImageView(new Image(getClass().getResource("stop.png").toExternalForm(), 20, 20, true, true)));
                            setGraphic(btnStop);
                            setStyle("-fx-alignment : CENTER;");
                        } else if ("à faire".equals(experience.getStatut())) {
                            setStyle("-fx-alignment : CENTER;");
                            btnPlay.setGraphic(new ImageView(new Image(getClass().getResource("play.png").toExternalForm(), 20, 20, true, true)));
                            setGraphic(btnPlay);
                        } else if ("acceptée".equals(experience.getStatut()) || "refusée".equals(experience.getStatut())) {
                            setStyle("-fx-alignment : CENTER;");
                            setGraphic(termine);
                        }
                    }
                }
            };
            return cell;
        };
        colonneStart.setCellFactory(cellFactory);
        viewTableView.getColumns().add(colonneStart);

        TableColumn<Experience, Void> colonneDetails = new TableColumn<>("Détails");
        Callback<TableColumn<Experience, Void>, TableCell<Experience, Void>> cellFactoryDetails;
        cellFactoryDetails = (final TableColumn<Experience, Void> param) -> {
            final TableCell<Experience, Void> cellDetails = new TableCell<Experience, Void>() {
                private final Button btnDetails = new Button();
                {
                    btnDetails.setOnAction((ActionEvent event) -> {
                        int Idexperience = getTableView().getItems().get(getIndex()).getId_exp();
                        viewdetailsPanel.setVisible(true);
                        datalibelleLabel.setText(getTableView().getItems().get(getIndex()).getLibelle());
                        dataequipeLabel.setText(getTableView().getItems().get(getIndex()).getEmail_equipe());
                        datastatutLabel.setText(getTableView().getItems().get(getIndex()).getStatut());
                        dataa1Label.setText(String.valueOf(getTableView().getItems().get(getIndex()).getA1()));
                        dataa2Label.setText(String.valueOf(getTableView().getItems().get(getIndex()).getA2()));
                        datadatetransmissionLabel.setText(getTableView().getItems().get(getIndex()).getDate_transmission());
                        datafrequenceLabel.setText(String.valueOf(getTableView().getItems().get(getIndex()).getFrequence()));
                        datatypeLabel.setText(getTableView().getItems().get(getIndex()).getType_exp());
                        datadatedemandeLabel.setText(getTableView().getItems().get(getIndex()).getDate_demande());
                        datadatedebutLabel.setText(getTableView().getItems().get(getIndex()).getDate_deb());
                        datanbslotsLabel.setText(String.valueOf(getTableView().getItems().get(getIndex()).getNb_slot()));
                        datadureeLabel.setText(String.valueOf(getTableView().getItems().get(getIndex()).getDuree()));
                        if (getTableView().getItems().get(getIndex()).getA3() == 0) {
                            datasuiviLabel.setText("NON");
                        } else {
                            datasuiviLabel.setText("OUI");
                        }

                        dataa3Label.setText(String.valueOf(getTableView().getItems().get(getIndex()).getA3()));
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        btnDetails.setGraphic(new ImageView(new Image(getClass().getResource("details.png").toExternalForm(), 20, 20, true, true)));
                        setGraphic(btnDetails);
                        setStyle("-fx-alignment : CENTER;");
                    }
                }
            };
            return cellDetails;
        };
        colonneDetails.setCellFactory(cellFactoryDetails);
        viewTableView.getColumns().add(colonneDetails);

    }

    /**
     * Méthode de connexion à la base de données Oracle
     */
    private void connectServer() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //Création de la connexion avec nos identifiants
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.254.3:1521:PFPBS", "GROUPE_73", "GROUPE_73");
            //Messsage prévenant une connexion réussie
            System.out.println("Connection etablished.");
        } catch (Exception e) {
            System.out.println(e);
            //Messsage prévenant une connexion non réussie
            System.out.println("Connection not etablished.");
        }
    }

    /**
     * Méthode de déconnexion de la base de données Oracle
     */
//    @Override
//    public void stop() {
//        System.out.println("Connection closed.");
//        try {
//            con.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        Platform.exit();
//    }
    /**
     * Méthode d'initialisation des données de l'application()
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connectServer();
    }
}

