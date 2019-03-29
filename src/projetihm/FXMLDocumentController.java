package projetihm;

import java.sql.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

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
    private Label fonctionLabel, welcomeLabel, loginErrorLabel, frequenceLabel, a3Label, frequenceviewLabel, a3viewLabel, datalibelleLabel, dataequipeLabel, datastatutLabel, dataa1Label, dataa2Label, datadatetransmissionLabel, datafrequenceLabel, datatypeLabel, datadatedemandeLabel, datadatedebutLabel, datanbslotsLabel, datadureeLabel, dataa3Label, slotsRestantsLabel, warningLabel;
    @FXML
    private TableView viewTable;
    @FXML
    private TextField mailTextField, passwordTextField, frequenceTextField, a3TextField, libelleTextField, a1TextField, a2TextField, nbSlotTextField, dureeExpTextField;
    @FXML
    private DatePicker dateDemandeDate;
    @FXML
    private RadioButton ouiRadio, nonRadio;
    @FXML
    private GridPane rectPlaque;
    @FXML
    private ComboBox equipeComboBox, typeExpComboBox, typeCelluleComboBox, choixExperienceComboBox, choixReplicatComboBox, choixPlaqueComboBox, expSelectedComboBox;
    @FXML
    private TableView viewTableView;
    @FXML
    private TableColumn idColumn1, libelleColumn1, typeColumn1, datedemandeColumn1, equipeColumn1, launcherColumn1, statutColumn1, detailsColumn1;
    //Variables d'environnement user
    private static String nameUser, functionUser;
    //Variables non-FXML
    private Integer existingUser, slots_to_check, nb_checked_slots;
    private Connection con;
    private ObservableList<ObservableList> viewData;

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
                functionUser = "chercheur";
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
     * Méthode permettant de charger les uplets/réplicas associer à l'expérience
     * lorsque l'utilisateur choisis une expérience, durant l'ajout de réplicas
     * à une plaque
     *
     * @param event
     */
    @FXML
    private void loadUpletExperience(ActionEvent event) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT ID_UPLET, URGENT, DATE_ECHEANCE "
                    + "FROM N_UPLET "
                    + "WHERE ID_EXPERIENCE = " + (choixExperienceComboBox.getValue() + "").split(" ")[0] + " AND ETAT = 'accepté'");
            choixReplicatComboBox.getItems().clear();
            while (rs.next()) {
                Integer urgent = rs.getInt(2);
                if (urgent == 0) {
                    choixReplicatComboBox.getItems().add("Uplet n°" + rs.getString(1));
                } else {
                    choixReplicatComboBox.getItems().add(0, "Uplet n°" + rs.getString(1) + " - URGENT, à faire avant le " + rs.getString(3).substring(0, 10));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Méthode permettant l'affichage graphique de la plaque permettant de
     * positionner les expériences sollicités par les chercheurs
     *
     * @param event
     */
    @FXML
    private void setSlotsPositionChecker(ActionEvent event) {
        //Recherche des propriétés de la plaque : slots disponibles, slots occupés
        //Liste des slots/réplicas occupés
        ArrayList<ArrayList> taken_slots = new ArrayList<>();
        //Liste des slots/réplicas cochés par l'utilisateur
        ArrayList<ArrayList> new_slots = new ArrayList<>();
        //
        nb_checked_slots = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT X,Y, NB_SLOT "
                    + "FROM SLOT "
                    + "JOIN N_UPLET ON SLOT.ID_UPLET = N_UPLET.ID_UPLET JOIN EXPERIENCE ON N_UPLET.ID_EXPERIENCE = EXPERIENCE.ID_EXPERIENCE "
                    + "WHERE NUM_PLAQUE = " + (choixPlaqueComboBox.getValue() + "").split(" ")[2]);
            choixReplicatComboBox.getItems().clear();
            while (rs.next()) {
                slots_to_check = rs.getInt(3);
                taken_slots.add(new ArrayList<Integer>(Arrays.asList(rs.getInt(1), rs.getInt(2))));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        //Re-initialisation des contraintes de dimensionnement de l'affichage graphique de la plaque
        rectPlaque.getRowConstraints().clear();
        rectPlaque.getColumnConstraints().clear();
        //Initiatilasion des variables de boucles, du nombre de colonnes et de lignes à 0
        Integer c = 0, l = 0, i, j;
        //Si la plaque de culture à 96 puits, alors l'affichage doit être sous forme de 12 colonnes et 8 lignes 
        if ("96".equals((choixPlaqueComboBox.getValue() + "").split(" ")[4])) {
            c = 12;
            l = 8;
            //Si la plaque de culture à 384 puits, alors l'affichage doit être sous forme de 24 colonnes et 16 lignes 
        } else if ("384".equals((choixPlaqueComboBox.getValue() + "").split(" ")[4])) {
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
                    availableButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            //Nombre de slots/réplicas cochés
                            new_slots.add(new ArrayList<Integer>(Arrays.asList(GridPane.getColumnIndex(availableButton), GridPane.getRowIndex(availableButton))));
                            if (availableButton.isSelected()) {
                                nb_checked_slots++;
                            } else {
                                nb_checked_slots--;
                            }
                            if (slots_to_check - nb_checked_slots > 0) {
                                slotsRestantsLabel.setVisible(true);
                                slotsRestantsLabel.setText((slots_to_check - nb_checked_slots) + " puits restants à déterminer");
                            } else {
                                slotsRestantsLabel.setVisible(false);
                            }
                        }
                    });
                    rectPlaque.add(availableButton, j, i);
                }
                j++;
            }
            i++;
        }
        //Redimensionnement de l'affichage des colonnes de manière égale selon le nombre de colonnes de puits
        for (i = 1; i < c; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / c);
            rectPlaque.getColumnConstraints().add(colConst);
        }
        //Redimensionnement de l'affichage des lignes de manière égale selon le nombre de lignes de puits
        for (i = 1; i < l; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / l);
            rectPlaque.getRowConstraints().add(rowConst);
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
        //Récupération des données
//        viewData = FXCollections.observableArrayList();
//        try {
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(
//                    "SELECT ID_EXPERIENCE, LIBELLE_EXP, TYPE_EXP, DATE_DEMANDE, NOM_EQUIPE, STATUT_EXP "
//                    + "FROM EXPERIENCE "
//                    + "JOIN EQUIPE ON EXPERIENCE.EMAIL_EQUIPE = EQUIPE.EMAIL_EQUIPE");
//            //setCellValueFactory du tableau affiché
//            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//                //We are using non property style for making dynamic table
//                final int j = i;
//                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
//                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
//                    publiremi c ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
//                        return new SimpleStringProperty(param.getValue().get(j).toString());
//                    }
//                });
//                viewTableView.getColumns().addAll(col);
//                System.out.println("Column [" + i + "] ");
//            }
//            //Insertion des données dans le tableau
//            while (rs.next()) {
//                //Iterate Row
//                ObservableList<String> row = FXCollections.observableArrayList();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    //Iterate Column
//                    row.add(rs.getString(i));
//                }
//                System.out.println("Row [1] added " + row);
//                viewData.add(row);
//            }
//            viewTable.setItems(viewData);
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
        //Gestion des pages et de l'UX
        eyeIcon.setImage(new Image(getClass().getResource("eye_white.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
        plaqueIcon.setImage(new Image(getClass().getResource("square_black.png").toExternalForm()));
        viewPanel.setVisible(true);
        insertPanel.setVisible(false);
        upletPanel.setVisible(false);
        plaqueScrollPane.setVisible(false);
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
        typeCelluleComboBox.getItems().addAll("cancéreuses", "non-cancéreuses");
        //Gestion des pages et de l'UX
        eyeIcon.setImage(new Image(getClass().getResource("eye_black.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_white.png").toExternalForm()));
        plaqueIcon.setImage(new Image(getClass().getResource("square_black.png").toExternalForm()));
        plaqueScrollPane.setVisible(false);
        viewPanel.setVisible(false);
        insertPanel.setVisible(false);
        upletPanel.setVisible(true);
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
//                    if (errorCode == 984) {
//                        warningLabel.setText("Une valeur saisie est incorrecte.");
//                    } else if (errorCode == 2290) {
//                        warningLabel.setText("La valeur de a1 doit être supérieure à celle de a2.");
//                    }
                    e = e.getNextException();
                    warningLabel.setText("");
                    warningLabel.setText(message + errorCode);
                    warningLabel.setVisible(true);
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
         //
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

