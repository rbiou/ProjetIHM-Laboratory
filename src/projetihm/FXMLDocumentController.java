package projetihm;

import java.awt.Color;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.CONTROL;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;

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
    private Label resInfoLabel, warningSearch, datasuiviLabel, fonctionLabel, welcomeLabel, loginErrorLabel, frequenceLabel, a3Label, frequenceviewLabel, a3viewLabel, datalibelleLabel, dataequipeLabel, datastatutLabel, dataa1Label, dataa2Label, datadatetransmissionLabel, datafrequenceLabel, datatypeLabel, datadatedemandeLabel, datadatedebutLabel, datanbslotsLabel, datadureeLabel, dataa3Label, slotsRestantsLabel, warningLabel, warningUpletLabel;
    @FXML
    private TableView viewTable;
    @FXML
    private TextField searchstatutTextField, searchEquipeTextField, mailTextField, passwordTextField, frequenceTextField, a3TextField, libelleTextField, a1TextField, a2TextField, nbSlotTextField, dureeExpTextField, qteCelluleTextField, qteAgentTextField;
    @FXML
    private DatePicker dateDemandeDate, searchdateTextField;
    @FXML
    private RadioButton ouiRadio, nonRadio;
    @FXML
    private GridPane rectPlaque;
    @FXML
    private ComboBox equipeComboBox, typeExpComboBox, typeCelluleComboBox, choixExperienceComboBox, choixReplicatComboBox, choixPlaqueComboBox, expSelectedComboBox, nomAgentComboBox;
    @FXML
    private TableView viewTableView, expUpletTableView, resUpletTableView;
    @FXML
    private TableColumn idColumn1, libelleColumn1, typeColumn1, datedemandeColumn1, equipeColumn1, launcherColumn1, statutColumn1, detailsColumn1;
    //Variables d'environnement user
    private static String nameUser, functionUser;
    //Variables non-FXML
    private Integer existingUser, slots_to_check, nb_checked_slots, c, l, slots_by_uplet;
    private Connection con;
    //Decalarations des listes utilisées pour la recherche d'expérience
    private ObservableList<Experience> listExp = FXCollections.observableArrayList();
    private ObservableList<Uplet> listUplet = FXCollections.observableArrayList();
    private ObservableList<ResUplet> listResUplet = FXCollections.observableArrayList();
    private ObservableList<Experience> listExpSearchDate = FXCollections.observableArrayList();
    private ObservableList<Experience> listExpSearchEquipe = FXCollections.observableArrayList();
    private ObservableList<Experience> listExpSearchStatut = FXCollections.observableArrayList();
    private ObservableList<Experience> listExpSearchStatutEquipe = FXCollections.observableArrayList();
    private ObservableList<Experience> listExpSearchStatutDate = FXCollections.observableArrayList();
    private ObservableList<Experience> listExpSearchEquipeDate = FXCollections.observableArrayList();
    private ObservableList<Experience> listExpSearchAll = FXCollections.observableArrayList();
    private ArrayList<ArrayList> new_slots;
    private boolean controlPressed = false;
    private ArrayList<Integer> uplets_to_attribute;
    private int idExperience; //Id de l'expérience selectionnée pour avoir plus de détails
    private String dateRecup;

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

        //chargement du tableau
        TableExp();
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
        viewdetailsPanel.setVisible(false);
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
        //Reset des combobox
        choixExperienceComboBox.getItems().clear();
        choixPlaqueComboBox.getItems().clear();
        //Par défaut, l'utilisateur n'ajoute pas une nouvelle plaque
        newPlaquePanel.setVisible(false);
        //Selection de l'expérience et de l'uplet/réplica à traiter
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT ID_EXPERIENCE, LIBELLE_EXP, NOM_EQUIPE "
                    + "FROM EXPERIENCE "
                    + "JOIN EQUIPE ON EXPERIENCE.EMAIL_EQUIPE = EQUIPE.EMAIL_EQUIPE "
                    + "WHERE STATUT_EXP = 'en cours'");
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
            choixPlaqueComboBox.getItems().add(0, "Nouvelle plaque");
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
     * Méthode permettant le bon affichage des boutons concernant le choix du
     * nombre de slots sur une plaque lors que la création d'une nouvelle plaque
     * : une plaque a soit 96, soit 384 slots donc les 2 boutons radios ne
     * peuvent être cochés. Cas du bouton radio 96 slots.
     *
     * @param event
     */
    @FXML
    private void setSlotsChoice96(ActionEvent event) {
        radioButton384.setSelected(false);
        radioButton96.setSelected(true);
    }

    /**
     * Méthode permettant le bon affichage des boutons concernant le choix du
     * nombre de slots sur une plaque lors que la création d'une nouvelle plaque
     * : une plaque a soit 96, soit 384 slots donc les 2 boutons radios ne
     * peuvent être cochés. Cas du bouton radio 384 slots.
     *
     * @param event
     */
    @FXML
    private void setSlotsChoice384(ActionEvent event) {
        radioButton384.setSelected(true);
        radioButton96.setSelected(false);
    }

    /**
     * Méthode permettant l'affichage graphique de la plaque permettant de
     * positionner les expériences sollicités par les chercheurs
     *
     * @param event
     */
    @FXML
    private void setSlotsPositionChecker(ActionEvent event) {
        //Par défaut, aucun message n'est affiché pour guider l'utilisateur dans sa sélection des puits
        slotsRestantsLabel.setVisible(false);
        //Vide la plaque précédente
        rectPlaque.getChildren().clear();
        //Initialisation du nombre de slots cochés
        nb_checked_slots = 0;
        if (choixPlaqueComboBox.getValue() != null && choixExperienceComboBox.getValue() != null) {
            //Selection ou création de la plaque
            plaqueUsed = getPlaqueUsed();
            if (!"Nouvelle plaque".equals(choixPlaqueComboBox.getValue() + "")) {
                //Si l'utilisateur a voulu ajouté une nouvelle plaque, on retire le formulaire de saisie d'une nouvelle plaque
                newPlaquePanel.setVisible(false);
                //Liste des slots/réplicas occupés
                ArrayList<ArrayList> taken_slots = new ArrayList<>();
                //Liste des slots/réplicas cochés par l'utilisateur
                new_slots = new ArrayList<>();
                //Liste des uplets à placer pour l'expérience sélectionnée
                uplets_to_attribute = new ArrayList<>();
                //Recherche des propriétés de la plaque : slots disponibles, slots occupés
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(
                            "SELECT X,Y "
                            + "FROM SLOT "
                            + "WHERE NUM_PLAQUE = " + plaqueUsed);
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
                        slots_by_uplet = rs.getInt(1);
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
                try {
                    Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery(
                            "SELECT ID_UPLET "
                            + "FROM N_UPLET "
                            + "WHERE ID_EXPERIENCE = " + (choixExperienceComboBox.getValue() + "").split(" ")[0] + " AND (SELECT COUNT(*) FROM SLOT WHERE ID_UPLET = N_UPLET.ID_UPLET) = 0");
                    while (rs.next()) {
                        uplets_to_attribute.add(rs.getInt(1));
                    }
                    int size = 0;
                    if (rs != null) {
                        rs.beforeFirst();
                        rs.last();
                        size = rs.getRow();
                    }
                    slots_to_check = slots_to_check * size;
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
                                if (controlPressed) {
                                    //Nombre de slots/réplicas cochés
                                    if (availableButton.isSelected() == false && availableButton.isDisabled() == false) {
                                        availableButton.setSelected(true);
                                        nb_checked_slots++;
                                        new_slots.add(new ArrayList<>(Arrays.asList(GridPane.getColumnIndex(availableButton), GridPane.getRowIndex(availableButton))));
                                    } else if (availableButton.isSelected() == true && availableButton.isDisabled() == false) {
                                        availableButton.setSelected(false);
                                        nb_checked_slots--;
                                        new_slots.remove(new ArrayList<>(Arrays.asList(GridPane.getColumnIndex(availableButton), GridPane.getRowIndex(availableButton))));
                                    }
                                    //MAJ des puits désactivés selon le nombre de puits pouvant être placé
                                    majPuitsPlaque();
                                }
                            });
                            availableButton.setOnAction((ActionEvent event2) -> {
                                //Nombre de slots/réplicas cochés
                                if (availableButton.isSelected() == true && availableButton.isDisabled() == false) {
                                    availableButton.setSelected(true);
                                    nb_checked_slots++;
                                    new_slots.add(new ArrayList<>(Arrays.asList(GridPane.getColumnIndex(availableButton), GridPane.getRowIndex(availableButton))));
                                } else if (availableButton.isSelected() == false && availableButton.isDisabled() == false) {
                                    availableButton.setSelected(false);
                                    nb_checked_slots--;
                                    new_slots.remove(new ArrayList<>(Arrays.asList(GridPane.getColumnIndex(availableButton), GridPane.getRowIndex(availableButton))));
                                }
                                //MAJ des puits désactivés selon le nombre de puits pouvant être placé
                                majPuitsPlaque();
                            });
                            rectPlaque.add(availableButton, j, i);
                        }
                        j++;
                    }
                    i++;
                }
                //MAJ des dimensions de la plaque à afficher
                majDimensionsPlaque();
                //MAJ des puits désactivés selon le nombre de puits pouvant être placé
                majPuitsPlaque();
            }
        }
    }

    /**
     * Méthode permettant la création d'une plaque ou la définition du choix de
     * l'utilisateur parmis celles exisentes.
     */
    private String getPlaqueUsed() {
        if (choixPlaqueComboBox.getValue() == "Nouvelle plaque") {
            newPlaquePanel.setVisible(true);
            plaqueErrorLabel.setVisible(false);
            numPlaqueTextField.clear();
            radioButton96.setSelected(false);
            radioButton384.setSelected(false);
            return (numPlaqueTextField.getText());
        } else {
            return (choixPlaqueComboBox.getValue() + "").split(" ")[2];
        }
    }

    /**
     * Méthode permettant l'ajout d'une nouvelle plaque.
     *
     * @param : event
     */
    @FXML
    private void addPlaque(ActionEvent event) {
        String slotsPlaque = "";
        if (radioButton96.isSelected() == true) {
            slotsPlaque = "96";
        } else if (radioButton384.isSelected() == true) {
            slotsPlaque = "384";
        }
        if (!"".equals(slotsPlaque) && !numPlaqueTextField.getText().isEmpty()) {
            Integer plaqueExistante = 1;
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT COUNT(*) FROM PLAQUE"
                        + " WHERE NUM_PLAQUE = " + numPlaqueTextField.getText());
                while (rs.next()) {
                    plaqueExistante = rs.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            if (plaqueExistante == 0) {
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(
                            "INSERT INTO PLAQUE (NUM_PLAQUE, TYPE_PLAQUE)"
                            + " VALUES (" + numPlaqueTextField.getText() + "," + slotsPlaque + ")");
                } catch (SQLException e) {
                    System.out.println(e);
                }
                choixPlaqueComboBox.setValue("Plaque n° " + numPlaqueTextField.getText() + " - " + slotsPlaque + " slots");
                newPlaquePanel.setVisible(false);
            } else {
                plaqueErrorLabel.setText("Plaque déjà existante.");
                plaqueErrorLabel.setVisible(true);
            }
        } else {
            plaqueErrorLabel.setText("Champ(s) non rempli(s).");
            plaqueErrorLabel.setVisible(true);
        }
    }

    /**
     * Méthode permettant la désactivation/activation des slots/puits de la
     * plaque lorsque le nombre limite de slots/puits pouvant être placés est
     * dépassé.
     */
    private void majPuitsPlaque() {
        if (slots_to_check - nb_checked_slots > 0) {
            ObservableList<Node> childrens = rectPlaque.getChildren();
            childrens.stream().map((node) -> (RadioButton) node).filter((cb) -> (cb.isSelected() == false && cb.isDisabled())).forEachOrdered((cb) -> {
                cb.setDisable(false);
            });
        } else if (slots_to_check - nb_checked_slots == 0) {
            ObservableList<Node> childrens = rectPlaque.getChildren();
            childrens.stream().map((node) -> (RadioButton) node).filter((cb) -> (cb.isSelected() == false)).forEachOrdered((cb) -> {
                cb.setDisable(true);
            });
        }
        setMessagePlaque();
    }

    /**
     * Méthode permettant la MAJ des dimensions de la plaque où l'on sélectionne
     * les emplacements des puits, pour un affichage optimal selon configuration
     * 96/384 puits
     */
    private void majDimensionsPlaque() {
        rectPlaque.getRowConstraints().clear();
        rectPlaque.getColumnConstraints().clear();
        Integer i;
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

    /**
     * Méthode gérant le message affiché lors de la sélection des slots/puits
     * sur la plaque.
     */
    private void setMessagePlaque() {
        slotsRestantsLabel.setVisible(true);
        if (choixPlaqueComboBox.getValue() == "Nouvelle plaque") {
            slotsRestantsLabel.setVisible(false);
        } else if (slots_to_check == 0) {
            slotsRestantsLabel.setText("Aucun slot à placer pour cette expérience.");
            slotsRestantsLabel.setTextFill(Color.RED);
        } else if (slots_to_check - nb_checked_slots > 0 && nb_checked_slots % slots_by_uplet != 0) {
            slotsRestantsLabel.setText(slots_by_uplet - (nb_checked_slots % slots_by_uplet) + " puits restants pour compléter le réplica.");
            slotsRestantsLabel.setTextFill(Color.RED);
        } else if (slots_to_check - nb_checked_slots > 0 && nb_checked_slots % slots_by_uplet == 0) {
            slotsRestantsLabel.setText((nb_checked_slots / slots_by_uplet) + " réplica(s) complété(s). " + (slots_to_check - nb_checked_slots) + " puits pouvant encore être déterminer.");
            slotsRestantsLabel.setTextFill(Color.BLACK);
        } else if (slots_to_check - nb_checked_slots == 0) {
            slotsRestantsLabel.setText((nb_checked_slots / slots_by_uplet) + " réplica(s) complété(s).");
            slotsRestantsLabel.setTextFill(Color.GREEN);
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
        if (new_slots.size() % slots_by_uplet == 0) {
            //Insertion des slots un par un
            uplets_to_attribute.forEach((uplet) -> {
                for (int i = 0; i <= slots_by_uplet - 1; i++) {
                    if (new_slots.size() > 0) {
                        try {
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(
                                    "INSERT INTO SLOT (ID_UPLET, NUM_PLAQUE, X, Y)"
                                    + " VALUES (" + uplet + "," + plaqueUsed + "," + new_slots.get(0).get(1) + "," + new_slots.get(0).get(0) + ")");
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        new_slots.remove(new_slots.get(0));
                    }
                }
            });
            //Remise à Z du label d'erreur
            slotsRestantsLabel.setText("");
            //Remise à Z du nombre de slots cochés
            nb_checked_slots = 0;
            //Remise à Z de la liste des slots à ajouter
            new_slots.clear();
            //Remise à Z de la plaque
            rectPlaque.getChildren().clear();
        } else {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), slotsRestantsLabel);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.setCycleCount(6);
            fadeTransition.play();
        }
    }

    /**
     * Méthode détectant la pression de la touche Ctrl d'un utilisateur (lors de
     * la sélection des emplacements de slots)
     *
     * @param e
     */
    @FXML
    public void keyPressed(KeyEvent e) {
        KeyCode key = e.getCode();
        if (key == CONTROL) {
            controlPressed = true;
        }
    }

    /**
     * Méthode détectant le relachement de la touche Ctrl d'un utilisateur (lors
     * de la sélection des emplacements de slots)
     *
     * @param e
     */
    @FXML
    private void keyRealeased(KeyEvent e) {
        KeyCode key = e.getCode();
        if (key == CONTROL) {
            controlPressed = false;
        }
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
        //Remise à 0 des textField de la recherche
        searchEquipeTextField.clear();
        searchstatutTextField.clear();
        searchdateTextField.setValue(null);
        searchEquipeTextField.setStyle("-fx-border-width:0px");
        searchstatutTextField.setStyle("-fx-border-width:0px");
        searchdateTextField.setStyle("-fx-border-width:0px");
        warningSearch.setText("");
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
                    if (errorCode == 1722) {
                        warningUpletLabel.setText("");
                        warningUpletLabel.setText("Valeur non valide.");
                        warningUpletLabel.setVisible(true);
                    }
                    e = e.getNextException();
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
    private void searchExperience(ActionEvent event) {
        //Remise à 0 des listes qui stokent les expériences selon la recherche faite par l'utilisateur
        listExpSearchStatut.clear();
        listExpSearchEquipe.clear();
        listExpSearchDate.clear();
        listExpSearchStatutEquipe.clear();
        listExpSearchStatutDate.clear();
        listExpSearchEquipeDate.clear();
        listExpSearchAll.clear();
        warningSearch.setText("");
        //Remise à 0 des border des textFields de la recherche
        searchEquipeTextField.setStyle("-fx-border-width:0px");
        searchstatutTextField.setStyle("-fx-border-width:0px");
        searchdateTextField.setStyle("-fx-border-width:0px");

        //cas où uniquement le statut est renseigné
        for (int i = 0; i < listExp.size(); i++) {
            if (listExp.get(i).getStatut().equals(searchstatutTextField.getText())) {
                listExpSearchStatut.add(listExp.get(i));
            }
        }
        //cas où uniquement l'équipe est renseignée
        for (int i = 0; i < listExp.size(); i++) {
            if (listExp.get(i).getEmail_equipe().equals(searchEquipeTextField.getText())) {
                listExpSearchEquipe.add(listExp.get(i));
            }
        }
        //cas où uniquement la date est renseignée    
        LocalDate localdate = searchdateTextField.getValue();
        if (localdate != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
            String searchDate = localdate.format(dateTimeFormatter);
            //cas où uniquement la date est renseignée
            for (int i = 0; i < listExp.size(); i++) {
                if (listExp.get(i).getDate_demande().equals(searchDate)) {
                    listExpSearchDate.add(listExp.get(i));
                }
            }
            //La date et l'équipe sont renseignés
            for (int i = 0; i < listExp.size(); i++) {
                if (listExp.get(i).getEmail_equipe().equals(searchEquipeTextField.getText()) && listExp.get(i).getDate_demande().equals(searchDate)) {
                    listExpSearchEquipeDate.add(listExp.get(i));
                }
            }
            //La date et le statut sont renseignés
            for (int i = 0; i < listExp.size(); i++) {
                if (listExp.get(i).getStatut().equals(searchstatutTextField.getText()) && listExp.get(i).getDate_demande().equals(searchDate)) {
                    listExpSearchStatutDate.add(listExp.get(i));
                }
            }
            //La date, le statut et l'équipe sont renseignés
            for (int i = 0; i < listExp.size(); i++) {
                if (listExp.get(i).getStatut().equals(searchstatutTextField.getText()) && listExp.get(i).getDate_demande().equals(searchDate) && listExp.get(i).getEmail_equipe().equals(searchEquipeTextField.getText())) {
                    listExpSearchAll.add(listExp.get(i));
                }
            }
        }

        for (int i = 0; i < listExp.size(); i++) {
            if (listExp.get(i).getEmail_equipe().equals(searchEquipeTextField.getText()) && listExp.get(i).getStatut().equals(searchstatutTextField.getText())) {
                listExpSearchStatutEquipe.add(listExp.get(i));
            }
        }

        //Affichage du tableau conditionné à la recherche faite par le laborantin
        if (searchEquipeTextField.getText().isEmpty() == true && localdate == null && searchstatutTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchStatut);
        } else if (searchstatutTextField.getText().isEmpty() == true && localdate == null && searchEquipeTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchEquipe);
        } else if (searchEquipeTextField.getText().isEmpty() == true && searchstatutTextField.getText().isEmpty() == true && localdate != null) {
            viewTableView.setItems(listExpSearchDate);
        } else if (searchEquipeTextField.getText().isEmpty() == true && localdate != null && searchstatutTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchStatutDate);
        } else if (searchEquipeTextField.getText().isEmpty() == false && localdate == null && searchstatutTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchStatutEquipe);
        } else if (searchEquipeTextField.getText().isEmpty() == false && localdate != null && searchstatutTextField.getText().isEmpty() == true) {
            viewTableView.setItems(listExpSearchEquipeDate);
        } else if (searchEquipeTextField.getText().isEmpty() == false && localdate != null && searchstatutTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchAll);
        } else {
            //Si els 3 champs sont vides -> erreur
            searchEquipeTextField.setStyle("-fx-border-color: RED");
            searchstatutTextField.setStyle("-fx-border-color: RED");
            searchdateTextField.setStyle("-fx-border-color: RED");
            warningSearch.setText("");
            warningSearch.setText("Veuillez remplir au moins un champs.");
            warningSearch.setVisible(true);
        }
        //Affichage d'un message dans le cas où aucun résultat n'a été trouvé après la recherche faite par le laborantin
        if (listExpSearchEquipe.isEmpty() == true && listExpSearchStatut.isEmpty() == true && listExpSearchDate.isEmpty() == true && listExpSearchStatutEquipe.isEmpty() == true && listExpSearchStatutDate.isEmpty() == true && listExpSearchEquipeDate.isEmpty() == true && listExpSearchAll.isEmpty() == true) {
            warningSearch.setText("");
            warningSearch.setText("Aucun résultat ne correspond à votre recherche.");
            warningSearch.setVisible(true);
        }

        viewTableView.refresh();
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
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DATE_TRANSMISSION FROM EXPERIENCE WHERE ID_EXPERIENCE = " + idExperience + " ");
            while (rs.next()) {
                dateRecup = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        if (dateRecup == null) {
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("UPDATE EXPERIENCE SET DATE_TRANSMISSION = SYSDATE WHERE ID_EXPERIENCE = " + idExperience + " ");
                    resInfoLabel.setText("");
                    resInfoLabel.setText("Les résultats ont été transmis au chercheur.");
                    resInfoLabel.setVisible(true);
            } catch (SQLException e) {
                System.out.println(e);
                while (e != null) {
                    System.out.println(e);
                    String message = e.getMessage();
                    int errorCode = e.getErrorCode();
                    System.out.println(errorCode);
                    if (errorCode == 2290) {
                        resInfoLabel.setText("");
                        resInfoLabel.setText("Impossible d'envoyer les résultats. L'expérience n'est pas terminée.");
                        resInfoLabel.setVisible(true);
                    }
                    e = e.getNextException();
               }
            }
        } else {
            resInfoLabel.setText("");
            resInfoLabel.setText("Ces résultats ont déjà été transmis au chercheur.");
            resInfoLabel.setVisible(true);
        }

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
                        resInfoLabel.setText("");
                        idExperience = getTableView().getItems().get(getIndex()).getId_exp();
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
                        dataa3Label.setText(String.valueOf(getTableView().getItems().get(getIndex()).getA3()));
                        if (getTableView().getItems().get(getIndex()).getA3() == 0) {
                            datasuiviLabel.setText("NON");
                        } else {
                            datasuiviLabel.setText("OUI");
                        }
                        resultatsExp(idExperience);
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
     * Récupération des uplets de l'expérience et de leur résultats
     *
     * @param idExp
     */
    public void resultatsExp(int idExp) {
        //Remise à 0 des tableView
        expUpletTableView.getColumns().clear();
        resUpletTableView.getColumns().clear();
        //Remise à 0 des listes de donnéees
        listUplet.clear();
        listResUplet.clear();
        //Récupération des uplets de l'expérience
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_UPLET, TYPE_CELLULE, Q_AGENT, Q_CELLULE, RENOUV, URGENT, DATE_ECHEANCE, ETAT FROM N_UPLET JOIN EXPERIENCE USING (ID_EXPERIENCE) WHERE ID_EXPERIENCE = " + idExp + " ");
            while (rs.next()) {
                Uplet uplet = new Uplet(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8));
                listUplet.add(uplet);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        //Déclaration des différentes pour afficher les caractéristiques des uplets d'une expérience
        TableColumn<Uplet, String> colonneidUplet = new TableColumn<>("Identifiant uplet");
        colonneidUplet.setCellValueFactory(new PropertyValueFactory("id_uplet"));
        colonneidUplet.setStyle("-fx-alignment: CENTER;");
        ///////////////////////////////////////////////////////////////////////////
        TableColumn<Uplet, String> colonneTypeCell = new TableColumn<>("Type de cellules");
        colonneTypeCell.setCellValueFactory(new PropertyValueFactory("type_cell"));
        colonneTypeCell.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<Uplet, String> colonneAgent = new TableColumn<>("Quantité d'agent");
        colonneAgent.setCellValueFactory(new PropertyValueFactory("q_agent"));
        colonneAgent.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////////
        TableColumn<Uplet, String> colonneCellule = new TableColumn<>("Quantité de cellules");
        colonneCellule.setCellValueFactory(new PropertyValueFactory("q_cellule"));
        colonneCellule.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<Uplet, String> colonneRenouv = new TableColumn<>("Renouvelé");
        colonneRenouv.setCellValueFactory(new PropertyValueFactory("renouv"));
        colonneRenouv.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<Uplet, String> colonneDateEche = new TableColumn<>("Date échéance");
        colonneDateEche.setCellValueFactory(new PropertyValueFactory("date_echeance"));
        colonneDateEche.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<Uplet, String> colonneEtat = new TableColumn<>("Etat de l'uplet");
        colonneEtat.setCellValueFactory(new PropertyValueFactory("etat"));
        colonneEtat.setStyle("-fx-alignment: CENTER;");
        ///////////////////////////////////////////////////////////////////////////
        expUpletTableView.setItems(listUplet);
        expUpletTableView.getColumns().addAll(colonneidUplet, colonneTypeCell, colonneAgent, colonneCellule, colonneRenouv, colonneDateEche, colonneEtat);

        //Récupération des uplets de l'expérience
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_UPLET, ECART_TYPE_TR, ECART_TYPE_R, ECART_TYPE_V, ECART_TYPE_B, MOYENNE_TR, MOYENNE_R, MOYENNE_V, MOYENNE_B, STATUT_UPLET FROM RESULTATS_UPLET JOIN N_UPLET USING (ID_UPLET) JOIN EXPERIENCE USING (ID_EXPERIENCE) WHERE ID_EXPERIENCE = " + idExp + " ");
            while (rs.next()) {
                ResUplet resUplet = new ResUplet(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
                listResUplet.add(resUplet);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        //Déclaration des différentes pour afficher les résultats des uplets d'une expérience
        TableColumn<ResUplet, String> colonneidUpletRes = new TableColumn<>("Identifiant uplet");
        colonneidUpletRes.setCellValueFactory(new PropertyValueFactory("idUplet"));
        colonneidUpletRes.setStyle("-fx-alignment: CENTER;");
        ///////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneMoyenneTR = new TableColumn<>("Transparence : Moyenne");
        colonneMoyenneTR.setCellValueFactory(new PropertyValueFactory("moyenneTR"));
        colonneMoyenneTR.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneEcartTypeTR = new TableColumn<>("Transparence : Ecart-type");
        colonneEcartTypeTR.setCellValueFactory(new PropertyValueFactory("ecartTypeTR"));
        colonneEcartTypeTR.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneMoyenneR = new TableColumn<>("Rouge : Moyenne");
        colonneMoyenneR.setCellValueFactory(new PropertyValueFactory("moyenneR"));
        colonneMoyenneR.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneEcartTypeR = new TableColumn<>("Rouge : Ecart-type");
        colonneEcartTypeR.setCellValueFactory(new PropertyValueFactory("ecartTypeR"));
        colonneEcartTypeR.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneMoyenneV = new TableColumn<>("Vert : Moyenne");
        colonneMoyenneV.setCellValueFactory(new PropertyValueFactory("moyenneR"));
        colonneMoyenneV.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneEcartTypeV = new TableColumn<>("Vert : Ecart-type");
        colonneEcartTypeV.setCellValueFactory(new PropertyValueFactory("ecartTypeR"));
        colonneEcartTypeV.setStyle("-fx-alignment: CENTER;");
        ///////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneMoyenneB = new TableColumn<>("Bleu : Moyenne");
        colonneMoyenneB.setCellValueFactory(new PropertyValueFactory("moyenneR"));
        colonneMoyenneB.setStyle("-fx-alignment: CENTER;");
        ////////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneEcartTypeB = new TableColumn<>("Bleu : Ecart-type");
        colonneEcartTypeB.setCellValueFactory(new PropertyValueFactory("ecartTypeR"));
        colonneEcartTypeB.setStyle("-fx-alignment: CENTER;");
        ///////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneStatutResUplet = new TableColumn<>("Statut du résultat");
        colonneStatutResUplet.setCellValueFactory(new PropertyValueFactory("statutUplet"));
        colonneStatutResUplet.setStyle("-fx-alignment: CENTER;");
        //////////////////////////////////////////////////////////////////////////
        resUpletTableView.setItems(listResUplet);
        resUpletTableView.getColumns().addAll(colonneidUpletRes, colonneMoyenneTR, colonneEcartTypeTR, colonneMoyenneR, colonneEcartTypeR, colonneMoyenneV, colonneEcartTypeV, colonneMoyenneB, colonneEcartTypeB, colonneStatutResUplet);
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
