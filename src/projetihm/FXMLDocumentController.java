package projetihm;

import java.sql.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Controller de notre application de gestion de laboratoire
 *
 * @author Rémi BIOU, Chloé FOUCHER
 * @version 12/04/2019
 */
public class FXMLDocumentController implements Initializable {

    //Variables FXML
    @FXML
    private Rectangle instructionsRectangle;
    @FXML
    private ImageView homeIcon, expIcon, upletIcon, eyeIcon, addIcon, plaqueIcon, instruction3ImageView, instruction4ImageView;
    @FXML
    private AnchorPane topPanel, welcomePanel, navPanel, loginPanel, menuPanel, insertPanel, viewPanel, upletPanel;
    @FXML
    private ScrollPane viewdetailsPanel, plaqueScrollPane;
    @FXML
    private Label resInfoLabel, warningSearch, datasuiviLabel, fonctionLabel, welcomeLabel, loginErrorLabel, frequenceLabel, a3Label, frequenceviewLabel, a3viewLabel, datalibelleLabel, dataequipeLabel, datastatutLabel, dataa1Label, dataa2Label, datadatetransmissionLabel, datafrequenceLabel, datatypeLabel, datadatedemandeLabel, datadatedebutLabel, datanbslotsLabel, datadureeLabel, dataa3Label, slotsRestantsLabel, warningLabel, warningUpletLabel, plaqueErrorLabel, instruction1Label, instruction2Label, anySelectedLabel, errorInsertPlaqueLabel;
    @FXML
    private TableView viewTable;
    @FXML
    private TextField searchstatutTextField, searchEquipeTextField, mailTextField, passwordTextField, frequenceTextField, a3TextField, libelleTextField, a1TextField, a2TextField, nbSlotTextField, dureeExpTextField, qteCelluleTextField, qteAgentTextField, numPlaqueTextField;
    @FXML
    private DatePicker dateDemandeDate, searchdateTextField;
    @FXML
    private RadioButton ouiRadio, nonRadio, radioButton96, radioButton384;
    @FXML
    private GridPane rectPlaque;
    @FXML
    private ComboBox equipeComboBox, typeExpComboBox, typeCelluleComboBox, choixExperienceComboBox, choixReplicatComboBox, choixPlaqueComboBox, expSelectedComboBox, nomAgentComboBox;
    @FXML
    private TableView viewTableView, expUpletTableView, resUpletTableView;
    @FXML
    private TableColumn idColumn1, libelleColumn1, typeColumn1, datedemandeColumn1, equipeColumn1, launcherColumn1, statutColumn1, detailsColumn1;
    @FXML
    private VBox newPlaquePanel;
    //Variables d'environnement user
    private static String nameUser, functionUser;
    //Variables non-FXML
    private Integer existingUser, slots_to_check, nb_checked_slots, c, l, slots_by_uplet;
    private Connection con;
    private ArrayList<ArrayList> new_slots;
    private boolean controlPressed = false;
    private ArrayList<Integer> uplets_to_attribute;
    private int idExperience; //Id de l'expérience selectionnée pour avoir plus de détails
    private String dateRecup, statut_exp, plaqueUsed;
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

    /**
     * Listener permettant la connexion d'un utilisateur lorsqu'il clique sur le
     * bouton connexion
     *
     * @param event
     */
    @FXML
    private void connectUser(ActionEvent event) {
        connexionUser();
    }

    /**
     * Listener permettant la connexion d'un utilisateur lorsqu'il clique sur le
     * clavier (entrer) via le bouton connexion
     *
     * @param e
     */
    @FXML
    private void connectUserBis(KeyEvent e) {
        if (e.getCode() == ENTER) {
            connexionUser();
        }
    }

    /**
     * Méthode qui permet de connecter l'utilisateur à notre application en
     * contrôlant ses identifiants. Une fois la connexion établie, l'utilisateur
     * est dirigé vers le tableau de bord de l'application
     */
    private void connexionUser() {
        existingUser = 0;
        //Contrôle si les champs de saisie pour se connecter ne sont pas vides
        if (mailTextField.getText().isEmpty() == false && passwordTextField.getText().isEmpty() == false) {
            //Connexion en utilisant la BDD
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT PERSONNE.PRENOM_PERSONNE, FONCTION.LIBELLE_FONCTION "
                        + "FROM PERSONNE "
                        + "JOIN FONCTION ON PERSONNE.ID_FONCTION = FONCTION.ID_FONCTION "
                        + "WHERE EMAIL_PERSONNE ='" + mailTextField.getText() + "' AND '" + passwordTextField.getText() + "' = decrypter(PERSONNE.PASSWORD)");
                while (rs.next()) {
                    existingUser = 1;
                    nameUser = rs.getString(1);
                    functionUser = rs.getString(2);
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.out.println(e);
                    String message = e.getMessage();
                    int errorCode = e.getErrorCode();
                    System.out.println(errorCode);
                }
            }
            if (existingUser != 0) {
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
     * Listener permettant la déconnexion de l'utilisateur connecté sur
     * l'interface où la méthode est appelée
     *
     * @param event
     */
    @FXML
    private void logOff(MouseEvent event
    ) {
        //Gestion des pages
        loginPanel.setVisible(true);
        navPanel.setVisible(false);
        welcomePanel.setVisible(false);
        menuPanel.setVisible(false);
    }

    /**
     * Listener permettant l'affichage du panel central de gestion des
     * expériences (où l'on retrouve la possibilité d'ajouter une expérience,
     * une plaque, ...) ainsi que la gestion de l'UX des menus (surlignage du
     * module où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setExperiencePanel(MouseEvent event
    ) {
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

        //Réinitialisation des textFields, ComboBox et suppression des bordures rouges
        clearChercheur();
        //Chargement des comboBox
        chargementComboBox();

        //Gestion des pages et de l'UX
        topPanel.setVisible(true);
        welcomePanel.setVisible(false);
        menuPanel.setVisible(false);
        plaqueScrollPane.setVisible(false);
        upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
        homeIcon.setImage(new Image(getClass().getResource("home_black.png").toExternalForm()));
        expIcon.setImage(new Image(getClass().getResource("flask_white.png").toExternalForm()));

        //Chargement du tableau pour afficher toutes les expériences au laborantin
        TableExp();
    }

    /**
     * Listener permettant l'affichage du panel central permettant d'avoir une
     * vue d'ensemble des fonctionnalités disponibles à l'utilisateur, ainsi que
     * son profil avec la possibilité de se déconnecter ainsi que la gestion de
     * l'UX des menus (surlignage du module où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setHomePanel(MouseEvent event
    ) {
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
     */
    @FXML
    private void PlaquePanel() {
        //Affichage des instructions
        setInstructionsOn();
        //Remise à Z des bordures rouges en cas d'erreur et du label d'erreur du formulaire lors de son précédent envoi
        resetErrorMessages();
        //Reset des combobox
        choixExperienceComboBox.getItems().clear();
        choixPlaqueComboBox.getItems().clear();
        //Par défaut, l'utilisateur n'ajoute pas une nouvelle plaque
        newPlaquePanel.setVisible(false);
        //Selection de l'expérience et de l'uplet/réplica à traiter
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT ID_EXPERIENCE, LIBELLE_EXP, NOM_EQUIPE, NB_SLOT, "
                    + "(SELECT COUNT(*) FROM N_UPLET WHERE N_UPLET.ID_EXPERIENCE = EXPERIENCE.ID_EXPERIENCE AND (SELECT COUNT(*) FROM SLOT WHERE SLOT.ID_UPLET = N_UPLET.ID_UPLET) != 0) AS TOT_UPLET_PLACE, "
                    + "(SELECT COUNT(*) FROM N_UPLET WHERE EXPERIENCE.ID_EXPERIENCE = ID_EXPERIENCE) AS TOT_UPLET "
                    + "FROM EXPERIENCE JOIN EQUIPE ON EXPERIENCE.EMAIL_EQUIPE = EQUIPE.EMAIL_EQUIPE "
                    + "WHERE STATUT_EXP = 'en cours' AND (((SELECT COUNT(*) FROM N_UPLET WHERE EXPERIENCE.ID_EXPERIENCE = N_UPLET.ID_EXPERIENCE)*NB_SLOT)-((SELECT COUNT(*) FROM N_UPLET WHERE N_UPLET.ID_EXPERIENCE = EXPERIENCE.ID_EXPERIENCE AND (SELECT COUNT(*) FROM SLOT WHERE SLOT.ID_UPLET = N_UPLET.ID_UPLET) != 0)*NB_SLOT)) > 0"
                    + "ORDER BY ((TOT_UPLET*NB_SLOT)-(TOT_UPLET_PLACE*NB_SLOT)) DESC");
            while (rs.next()) {
                choixExperienceComboBox.getItems().add(rs.getString(1) + " - " + rs.getString(2) + " - Equipe " + rs.getString(3) + " - " + ((rs.getInt(6) * rs.getInt(4)) - (rs.getInt(5) * rs.getInt(4))) + " puits à placer pour analyse");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        //Selection de la plaque à traiter
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT NUM_PLAQUE, TYPE_PLAQUE, (SELECT COUNT(*) FROM SLOT WHERE SLOT.NUM_PLAQUE=PLAQUE.NUM_PLAQUE) AS TOT "
                    + "FROM PLAQUE "
                    + "WHERE REFUS = 0 AND (SELECT COUNT(*) FROM SLOT WHERE SLOT.NUM_PLAQUE=PLAQUE.NUM_PLAQUE) < TYPE_PLAQUE "
                    + "ORDER BY TOT DESC");
            while (rs.next()) {
                choixPlaqueComboBox.getItems().add("Plaque n° " + rs.getString(1) + " - " + rs.getString(2) + " puits - " + (rs.getInt(2) - rs.getInt(3)) + " puit(s) disponible(s)");
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
     * Listener appelant la méthode PlaquePanel permettant l'affichage graphique
     * du formulaire permettant de positionner les expériences sollicitées par
     * les chercheurs
     *
     * @param event
     */
    @FXML
    private void setPlaquePanel(MouseEvent event) {
        PlaquePanel();
    }

    /**
     * Listener permettant le bon affichage des boutons concernant le choix du
     * nombre de slots sur une plaque lors que la création d'une nouvelle plaque
     * : une plaque a soit 96, soit 384 slots donc les 2 boutons radios ne
     * peuvent être cochés. Cas du bouton radio 96 slots. --> Au clique sur le
     * bouton
     *
     * @param event
     */
    @FXML
    private void setSlotsChoice96(ActionEvent event) {
        radioButton384.setSelected(false);
        radioButton96.setSelected(true);
    }

    /**
     * Listener permettant le bon affichage des boutons concernant le choix du
     * nombre de slots sur une plaque lors que la création d'une nouvelle plaque
     * : une plaque a soit 96, soit 384 slots donc les 2 boutons radios ne
     * peuvent être cochés. Cas du bouton radio 96 slots. --> En pressant la
     * touche entrée en ayant selectionner le bouton radio
     *
     * @param e
     */
    @FXML
    private void setSlotsChoice96Bis(KeyEvent e) {
        radioButton384.setSelected(false);
        radioButton96.setSelected(true);
    }

    /**
     * Listener permettant le bon affichage des boutons concernant le choix du
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
     * Listener permettant le bon affichage des boutons concernant le choix du
     * nombre de slots sur une plaque lors que la création d'une nouvelle plaque
     * : une plaque a soit 96, soit 384 slots donc les 2 boutons radios ne
     * peuvent être cochés. Cas du bouton radio 384 slots. --> En pressant la
     * touche entrée en ayant selectionner le bouton radio
     *
     * @param e
     */
    @FXML
    private void setSlotsChoice384Bis(KeyEvent e) {
        radioButton384.setSelected(false);
        radioButton96.setSelected(true);
    }

    /**
     * Listener permettant de masquer les instructions pour sélectionner les
     * réplicas sur la plaque lorsque l'utilisateur entre dans le panel pour
     * sélectionner les puits.
     *
     * @param event
     */
    @FXML
    private void setInstructionsOff(MouseEvent event) {
        anySelectedLabel.setEffect(null);
        instructionsRectangle.setVisible(false);
        instruction1Label.setVisible(false);
        instruction2Label.setVisible(false);
        instruction3ImageView.setVisible(false);
        instruction4ImageView.setVisible(false);
    }

    /**
     * Méthode permettant d'afficher les instructions pour sélectionner les
     * réplicas sur la plaque lorsque l'utilisateur arrive sur la partie pour
     * gérer les réplicas.
     */
    @FXML
    private void setInstructionsOn() {
        anySelectedLabel.setEffect(new GaussianBlur(5.5));
        instructionsRectangle.setVisible(true);
        instruction1Label.setVisible(true);
        instruction2Label.setVisible(true);
        instruction3ImageView.setVisible(true);
        instruction4ImageView.setVisible(true);
    }

    /**
     * Listener permettant d'afficher les instructions au clic du lien "Comment
     * ça marche ?" pour sélectionner les réplicas sur la plaque lorsque
     * l'utilisateur arrive sur la partie pour gérer les réplicas.
     */
    @FXML
    private void setInstructionsOnAgain(ActionEvent e) {
        anySelectedLabel.setEffect(new GaussianBlur(5.5));
        instructionsRectangle.setVisible(true);
        instruction1Label.setVisible(true);
        instruction2Label.setVisible(true);
        instruction3ImageView.setVisible(true);
        instruction4ImageView.setVisible(true);
    }

    /**
     * Listener appelant la méthode SlotsPositionChecker permettant l'affichage
     * graphique de la plaque permettant de positionner les expériences
     * sollicités par les chercheurs
     *
     * @param event
     */
    @FXML
    private void setSlotsPositionChecker(ActionEvent event) {
        SlotsPositionChecker();
    }

    /**
     * Méthode permettant l'affichage graphique de la plaque permettant de
     * positionner les expériences sollicités par les chercheurs
     *
     */
    private void SlotsPositionChecker() {
        //Nouvelle saisie du formulaire donc on efface la validation de l'ancien formulaire si il a déjà été envoyé
        if (errorInsertPlaqueLabel.getText().equals("Définition de réplica(s) réussie.")) {
            errorInsertPlaqueLabel.setTextFill(Color.RED);
            errorInsertPlaqueLabel.setVisible(false);
        }
        //Par défaut, aucun message n'est affiché pour guider l'utilisateur dans sa sélection des puits
        slotsRestantsLabel.setVisible(false);
        //Vide la plaque précédente et les instructions
        rectPlaque.getChildren().clear();
        anySelectedLabel.setVisible(true);
        //Initialisation du nombre de slots cochés
        nb_checked_slots = 0;
        if (choixPlaqueComboBox.getValue() != null) {
            //Selection ou création de la plaque
            plaqueUsed = getPlaqueUsed();
            if (!"Nouvelle plaque".equals(choixPlaqueComboBox.getValue() + "")) {
                //Si l'utilisateur a voulu ajouté ou a ajouté une nouvelle plaque, on retire le formulaire de saisie d'une nouvelle plaque 
                newPlaquePanel.setVisible(false);
                if (choixExperienceComboBox.getValue() != null) {
                    //On retire les instructions
                    anySelectedLabel.setVisible(false);
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
                                + "WHERE ID_EXPERIENCE = " + (choixExperienceComboBox.getValue() + "").split(" ")[0] + " AND (SELECT COUNT(*) FROM SLOT WHERE SLOT.ID_UPLET = N_UPLET.ID_UPLET) = 0");
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

    private void addPlaqueMethode() {
        //Remise à Z des bordures rouges en cas d'erreur et du label d'erreur du formulaire
        resetErrorMessagesNewPlaque();
        String slotsPlaque = "";
        if (radioButton96.isSelected() == true) {
            slotsPlaque = "96";
        } else if (radioButton384.isSelected() == true) {
            slotsPlaque = "384";
        }
        //Affichage des messages d'erreur et des emplacements des erreurs
        Boolean ok = true;
        if (numPlaqueTextField.getText().isEmpty()) {
            numPlaqueTextField.setStyle("-fx-border-color: RED");
            plaqueErrorLabel.setVisible(true);
            plaqueErrorLabel.setText(plaqueErrorLabel.getText() + "N° de plaque manquant. ");
        }
        if ("".equals(slotsPlaque)) {
            radioButton96.setStyle("-fx-border-color: RED");
            radioButton384.setStyle("-fx-border-color: RED");
            plaqueErrorLabel.setVisible(true);
            plaqueErrorLabel.setText(plaqueErrorLabel.getText() + "Type de plaque manquant. ");
        }
        if (!numPlaqueTextField.getText().matches("^[0-9]+$") && !numPlaqueTextField.getText().isEmpty()) {
            numPlaqueTextField.setStyle("-fx-border-color: RED");
            plaqueErrorLabel.setVisible(true);
            plaqueErrorLabel.setText(plaqueErrorLabel.getText() + "N° de plaque invalide : chiffres uniq. ");
        }
        //Insertion de la plaque
        if (!"".equals(slotsPlaque) && !numPlaqueTextField.getText().isEmpty() && numPlaqueTextField.getText().matches("^[0-9]+$")) {
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
                //Reset des erreurs précédentes après l'envoi du formulaire
                resetErrorMessagesNewPlaque();
            } else {
                plaqueErrorLabel.setText("Plaque déjà existante.");
                plaqueErrorLabel.setVisible(true);
            }
        }
    }

    /**
     * Listener permettant l'ajout d'une nouvelle plaque lorsque l'utilisateur
     * clique sur le bouton 'ajouter'
     *
     * @param : event
     */
    @FXML
    private void addPlaque(ActionEvent event) {
        addPlaqueMethode();
    }

    /**
     * Listener permettant l'ajout d'une nouvelle plaque lorsque l'utilisateur
     * presse la touche entrer en ayant selectionner le bouton 'ajouter'
     *
     * @param : e
     */
    @FXML
    private void addPlaqueBis(KeyEvent e) {
        if (e.getCode() == ENTER) {
            addPlaqueMethode();
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
        errorInsertPlaqueLabel.setTextFill(Color.RED);
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
     * Méthode reset-ant les messages d'erreur lors de la précédente tentative
     * d'envoi du formulaire pour l'ajout de réplicas sur une plaque
     */
    private void resetErrorMessages() {
        //Remise à Z du label d'erreur du formulaire à son précédent envoi
        errorInsertPlaqueLabel.setText("");
        errorInsertPlaqueLabel.setVisible(false);
        //Remise à Z des bordures rouges en cas d'erreur au précédent envoi du formulaire
        choixExperienceComboBox.setStyle("-fx-border-width: 0");
        choixPlaqueComboBox.setStyle("-fx-border-width: 0");
        rectPlaque.setStyle("-fx-border-color: black");
    }

    /**
     * Méthode reset-ant les messages d'erreur lors de la précédente tentative
     * d'envoi du formulaire pour l'ajout de nouvelle plaque
     */
    private void resetErrorMessagesNewPlaque() {
        //Remise à Z du label d'erreur du formulaire à son précédent envoi
        plaqueErrorLabel.setText("");
        plaqueErrorLabel.setVisible(false);
        //Remise à Z des bordures rouges en cas d'erreur au précédent envoi du formulaire
        numPlaqueTextField.setStyle("-fx-border-width: 0");
        radioButton96.setStyle("-fx-border-width: 0");
        radioButton384.setStyle("-fx-border-width: 0");
    }

    /**
     * Listener appelant la méthode permettant l'insertion de slots quand la
     * touche "Entrée" est préssée
     *
     * @param : e
     */
    @FXML
    private void onEnterPressedInsertSlots(KeyEvent e) {
        if (e.getCode() == ENTER) {
            insertSlots();
        }
    }

    /**
     * Listener appelant la méthode permettant l'insertion de slots quand le
     * bouton "Valider" est cliqué
     *
     * @param : e
     */
    @FXML
    private void onClickInsertSlots(ActionEvent e) {
        insertSlots();
    }

    /**
     * Méthode permettant l'insertion slot par slot des slots que l'utilisateur
     * vient de cocher sur le panel rectPlaque Les slots cochés sont récupérés à
     * partir de la liste new_slots.
     */
    @FXML
    private void insertSlots() {
        //Remise à Z des bordures rouges en cas d'erreur et du label d'erreur du formulaire
        resetErrorMessages();
        //Affichage des messages d'erreur et des emplacements des erreurs
        Boolean ok = true;
        if (choixPlaqueComboBox.getValue() == null) {
            choixPlaqueComboBox.setStyle("-fx-border-color: RED");
            errorInsertPlaqueLabel.setVisible(true);
            errorInsertPlaqueLabel.setText(errorInsertPlaqueLabel.getText() + "Plaque manquante. ");
        }
        if (choixExperienceComboBox.getValue() == null) {
            choixExperienceComboBox.setStyle("-fx-border-color: RED");
            errorInsertPlaqueLabel.setVisible(true);
            errorInsertPlaqueLabel.setText(errorInsertPlaqueLabel.getText() + "Expérience manquante. ");
        }
        if (nb_checked_slots == null) {
            rectPlaque.setStyle("-fx-border-color: RED");
            errorInsertPlaqueLabel.setVisible(true);
            errorInsertPlaqueLabel.setText(errorInsertPlaqueLabel.getText() + "Pas de puits sélectionnés. ");
        }
        if (nb_checked_slots != null) {
            if (nb_checked_slots == 0) {
                ok = false;
                rectPlaque.setStyle("-fx-border-color: RED");
                errorInsertPlaqueLabel.setVisible(true);
                errorInsertPlaqueLabel.setText(errorInsertPlaqueLabel.getText() + "Pas de puits sélectionnés. ");

            }
        }
        if (ok && nb_checked_slots != null && choixExperienceComboBox.getValue() != null && choixPlaqueComboBox.getValue() != null) {
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
                //Remise à Z du label de suivi de la sélection de la plaque
                slotsRestantsLabel.setText("");
                //Remise à Z du nombre de slots cochés
                nb_checked_slots = 0;
                //Remise à Z de la liste des slots à ajouter
                new_slots.clear();
                //MAJ du formulaire
                PlaquePanel();
                //MAJ de la plaque
                SlotsPositionChecker();
                //Remise à Z des bordures rouges en cas d'erreur et du label d'erreur du formulaire
                resetErrorMessages();
                //Message comme quoi insertion OK
                errorInsertPlaqueLabel.setText("Définition de réplica(s) réussie.");
                errorInsertPlaqueLabel.setTextFill(Color.GREEN);
                errorInsertPlaqueLabel.setVisible(true);
            } else {
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), slotsRestantsLabel);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.setCycleCount(6);
                fadeTransition.play();
            }
        }
    }

    /**
     * Listener détectant la pression de la touche Ctrl d'un utilisateur (lors
     * de la sélection des emplacements de slots)
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
     * Listener détectant le relachement de la touche Ctrl d'un utilisateur
     * (lors de la sélection des emplacements de slots)
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
     * Listener permettant l'affichage du panel central permettant la
     * visualisation de toutes les expériences stockées ainsi que la gestion de
     * l'UX des menus (surlignage du module où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setViewPanel(MouseEvent event) {
        reloadTableauExp();
    }

    /**
     * Méthode permettant l'affichage du tableau des expériences visibles par
     * les laborantins
     */
    private void reloadTableauExp() {
        //Appel de la méthode permettant la création de la table javaFX
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
     * Listener permettant l'affichage du panel central permettant l'ajout d'une
     * expérience ainsi que la gestion de l'UX des menus (surlignage du module
     * où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setInsertPanel(MouseEvent event) {
        //Réinitialisation des textFields, ComboBox et suppression des bordures rouge
        clearChercheur();
        //Chargement des comboBox
        chargementComboBox();
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
     * Listener permettant l'affichage du panel central permettant l'ajout d'un
     * uplet/réplica de la part du chercheur ainsi que la gestion de l'UX des
     * menus (surlignage du module où navigue l'utilisateur)
     *
     * @param event
     */
    @FXML
    private void setUpletPanel(MouseEvent event
    ) {
        //Réinitialisation des textFields, ComboBox et suppression des bordures rouge
        clearChercheur();
        //Chargement des comboBox
        chargementComboBox();
        //Gestion des pages et de l'UX
        eyeIcon.setImage(new Image(getClass().getResource("eye_black.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_white.png").toExternalForm()));
        plaqueIcon.setImage(new Image(getClass().getResource("square_black.png").toExternalForm()));
        plaqueScrollPane.setVisible(false);
        viewPanel.setVisible(false);
        insertPanel.setVisible(false);
        upletPanel.setVisible(true);
    }

    /**
     * Listener activant l'affichage du formulaire permettant la saisie des
     * paramètres d'une expérience suivie dans le temps, lors de l'ajout d'une
     * expérience --> AU CLIQUE
     *
     * @param event
     */
    @FXML
    private void expSuiviTempsYes(ActionEvent event
    ) {
        //Gestion des pages et de l'UX
        a3TextField.setStyle("-fx-border-width:0px");
        frequenceTextField.setStyle("-fx-border-width:0px");
        frequenceLabel.setVisible(true);
        a3Label.setVisible(true);
        frequenceTextField.setVisible(true);
        a3TextField.setVisible(true);
        nonRadio.setSelected(false);
    }

    /**
     * Listener activant l'affichage du formulaire permettant la saisie des
     * paramètres d'une expérience suivi dans le temps, lors de l'ajout d'une
     * expérience --> AVEC LE CLAVIER
     *
     * @param Keyevent
     */
    @FXML
    private void expSuiviTempsYesBis(KeyEvent e) {
        //Gestion des pages et de l'UX
        a3TextField.setStyle("-fx-border-width:0px");
        frequenceTextField.setStyle("-fx-border-width:0px");
        if (e.getCode() == ENTER) {
            frequenceLabel.setVisible(true);
            a3Label.setVisible(true);
            frequenceTextField.setVisible(true);
            a3TextField.setVisible(true);
            nonRadio.setSelected(false);
            ouiRadio.setSelected(true);
        }
    }

    /**
     * Listener désactivant l'affichage du formulaire permettant la saisie des
     * paramètres d'une expérience suivi dans le temps, lors de l'ajout d'une
     * expérience --> AVEC LE CLAVIER
     *
     * @param Keyevent
     */
    @FXML
    private void expSuiviTempsNoBis(KeyEvent e) {
        //Gestion des pages et de l'UX
        if (e.getCode() == ENTER) {
            frequenceLabel.setVisible(false);
            a3Label.setVisible(false);
            frequenceTextField.setVisible(false);
            a3TextField.setVisible(false);
            ouiRadio.setSelected(false);
            nonRadio.setSelected(true);
        }

    }

    /**
     * Listener désactivant l'affichage du formulaire permettant la saisie des
     * paramètres d'une expérience suivi dans le temps, lors de l'ajout d'une
     * expérience --> AU CLIQUE
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
     * Listener permettant l'ajout d'une expérience à la base de données en
     * faisant appelle à la méthode addExp() --> Quand l'utilisateur presse la
     * touche entrée
     *
     * @param event
     */
    @FXML
    private void ajoutExperienceBis(KeyEvent e
    ) {
        if (e.getCode() == ENTER) {
            addExp();
        }
    }

    /**
     * Listener permettant l'ajout d'une expérience à la base de données -->
     * Quand l'utilisateur clique sur le bouton
     *
     * @param event
     */
    @FXML
    private void ajoutExperience(ActionEvent event) {
        addExp();
    }

    /**
     * Listener sur le bouton d'ajout d'un uplet permettant d'ajouter un uplet
     * lorsque l'utilisateur clique sur le bouton
     *
     * @param event
     */
    @FXML
    private void ajoutUplet(ActionEvent event) {
        addUplet();
    }

    /**
     * Listener sur le bouton d'ajout d'un uplet permettant d'ajouter un uplet
     * lorsque l'utilisateur presse la touche entrée
     *
     * @param e
     */
    @FXML
    private void ajoutUpletBis(KeyEvent e) {
        if (e.getCode() == ENTER) {
            addUplet();
        }
    }

    /**
     * Listener permettant la recherche d'expérience parmis celles dans la base
     * de données selon 3 critères non-obligatoires : - l'équipe commanditaire -
     * le statut de l'expérience - la date de début de l'expérience --> AVEC LE
     * CLAVIER
     *
     * @param e
     */
    @FXML
    private void searchExperienceBis(KeyEvent e) {
        rechercherExp();
    }

    /**
     * Listener permettant la recherche d'expérience parmis celle en base de
     * données selon 3 critères non-obligatoires : - l'équipe commanditaire - le
     * statut de l'expérience - la date de début de l'expérience --> EN CLIQUANT
     * SUR LE BOUTON
     *
     * @param event
     */
    @FXML
    private void searchExperience(ActionEvent event) {
        rechercherExp();
    }

    /**
     * Listener permettant de réinitialisé le tableau et d'afficher toutes les
     * expériences --> EN CLIQUANT SUR LE BOUTON
     *
     * @param event
     */
    @FXML
    private void annuleRecherche(ActionEvent event) {
        reloadTableauExp();
    }

    /**
     * Listener permettant de réinitialisé le tableau et d'afficher toutes les
     * expériences --> EN PRESSANT LA TOUCHE ENTREE
     *
     * @param e
     */
    @FXML
    private void annuleRechercheBis(KeyEvent e) {
        if (e.getCode() == ENTER) {
            reloadTableauExp();
        }
    }

    /**
     * Listener permettant l'envoi des résultats de l'expérience sélectionnée à
     * l'équipe de recherche l'ayant commandité
     *
     * @param event
     */
    @FXML
    private void sendResults(ActionEvent event
    ) {
        //Récupération de la date de transmission et du statut pour l'expérience selectionnée
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DATE_TRANSMISSION, STATUT_EXP FROM EXPERIENCE WHERE ID_EXPERIENCE = " + idExperience + " ");
            while (rs.next()) {
                dateRecup = rs.getString(1);
                statut_exp = rs.getString(2);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        //Mise à jour de la date de transmission (avec la date du jour) pour l'expérience selectionné dans le cas où la date de transmission est nulle
        if (dateRecup == null) {
            if ("acceptée".equals(statut_exp) || "refusée".equals(statut_exp)) {
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
                        //Récupération du code d'erreur oracle dans le cas où le laborantin souhaite envoyer les résultats au chercheur pour une expérience non terminée
                        // cf : TRIGGER DE CONTROLE 
                        if (errorCode == 2290) {
                            resInfoLabel.setText("");
                            resInfoLabel.setText("Impossible d'envoyer les résultats. L'expérience n'est pas terminée.");
                            resInfoLabel.setVisible(true);
                        }
                        e = e.getNextException();
                    }
                }
            } else { //Si le statut est "àfaire" ou "en cours", il est impossible d'envoyer les résultats au chercheur
                resInfoLabel.setText("");
                resInfoLabel.setText("Impossible d'envoyer les résultats. L'expérience n'est pas terminée.");
                resInfoLabel.setVisible(true);
            }
        } else { // Si la date n'est pas nulle, les résultats ont donc déjà été transmis
            resInfoLabel.setText("");
            resInfoLabel.setText("Ces résultats ont déjà été transmis au chercheur.");
            resInfoLabel.setVisible(true);
        }

        //Mise à jour de l'affichage de la date de transmission 
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DATE_TRANSMISSION FROM EXPERIENCE WHERE ID_EXPERIENCE = " + idExperience + " ");
            while (rs.next()) {
                datadatetransmissionLabel.setText(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /*
    * Méthode de chargement du tableau contenant l'ensemble des expériences
     */
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
            System.out.println(ex);
        }
        //Création des colonnes de la tableView auxquelles sont ajoutées les données de la liste d'expérience (listExp)
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
        //Ajout des colonnes à la tableView
        viewTableView.getColumns().addAll(colonneLibelle, colonneEquipe, colonneDateDemande, colonneTypeExp, colonneStatut);
    }

    /*
    * Méthode permettant l'ajout des boutons à la tableView dans 2 colonnes distinctes
     */
    public void TableExp() {
        reloadTableView();
        //Création de la première colonne contenant soit les boutons : play ou stop
        TableColumn<Experience, Void> colonneStart = new TableColumn<>("Gestion de l'expérience");

        //Création de la cellule contenant le bouton (play ou stop), ou un label : TERMINEE
        Callback<TableColumn<Experience, Void>, TableCell<Experience, Void>> cellFactory;
        cellFactory = (final TableColumn<Experience, Void> param) -> {
            final TableCell<Experience, Void> cell = new TableCell<Experience, Void>() {
                //Création du premier bouton : PLAY et de son listener associé
                private final Button btnPlay = new Button();

                {
                    btnPlay.setOnAction((ActionEvent event) -> {
                        //Quand l'expérience est lancée, son statut passe de "à faire" à "en cours"
                        int Idexperience = getTableView().getItems().get(getIndex()).getId_exp();
                        try {
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("UPDATE EXPERIENCE SET STATUT_EXP = 'en cours' WHERE ID_EXPERIENCE = " + Idexperience + " ");
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                        try {
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("UPDATE EXPERIENCE SET DATE_DEB_EXP = SYSDATE WHERE ID_EXPERIENCE = " + Idexperience + " ");
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                        TableExp();
                    });
                }
                //Création du deuxième bouton : STOP et de son listener associé
                private final Button btnStop = new Button();

                {
                    //Quand l'expérience est terminée, son statut passe de "en cours" à "terminé"
                    btnStop.setOnAction((ActionEvent event) -> {
                        int Idexperience = getTableView().getItems().get(getIndex()).getId_exp();
                        try {
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("UPDATE EXPERIENCE SET TERMINE = 1 WHERE ID_EXPERIENCE = " + Idexperience + " ");
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                        TableExp();
                    });
                }
                //Création du label terminée
                private final Label termine = new Label("Terminée");

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        //Récupération de l'expérience concernée
                        Experience experience = getTableView().getItems().get(getIndex());
                        //Si son statut est en cours, le bouton stop est affichée pour pouvoir l'arrêter
                        if ("en cours".equals(experience.getStatut())) {
                            btnStop.setGraphic(new ImageView(new Image(getClass().getResource("stop.png").toExternalForm(), 20, 20, true, true)));
                            setGraphic(btnStop);
                            setStyle("-fx-alignment : CENTER;");
                            //Si son statut est à faire, on affiche le bouton PLAY pour pouvoir la lancer
                        } else if ("à faire".equals(experience.getStatut())) {
                            setStyle("-fx-alignment : CENTER;");
                            btnPlay.setGraphic(new ImageView(new Image(getClass().getResource("play.png").toExternalForm(), 20, 20, true, true)));
                            setGraphic(btnPlay);
                            //Si l'expérience est terminée c'est à faire que son statut est acceptée ou refusée alors on affiche le label TERMINEE
                        } else if ("acceptée".equals(experience.getStatut()) || "refusée".equals(experience.getStatut())) {
                            setStyle("-fx-alignment : CENTER;");
                            setGraphic(termine);
                        }
                    }
                }
            };
            return cell;
        };
        //Ajout de la cellule créer à la colonne
        colonneStart.setCellFactory(cellFactory);
        //Ajout de la colonne à la tableView
        viewTableView.getColumns().add(colonneStart);

        //Création de la deuxième colonne contenant un bouton "+" pour afficher davantage de détails, notamment les résultats de l'expérience
        TableColumn<Experience, Void> colonneDetails = new TableColumn<>("Détails");
        Callback<TableColumn<Experience, Void>, TableCell<Experience, Void>> cellFactoryDetails;
        cellFactoryDetails = (final TableColumn<Experience, Void> param) -> {
            final TableCell<Experience, Void> cellDetails = new TableCell<Experience, Void>() {
                //Création du bouton "+" et de son listener associé
                private final Button btnDetails = new Button();

                {
                    btnDetails.setOnAction((ActionEvent event) -> {
                        resInfoLabel.setText("");
                        //Récupération des valeurs de l'expérience sur laquelle le laborantin souhaite obtenir plus d'information
                        idExperience = getTableView().getItems().get(getIndex()).getId_exp();
                        viewdetailsPanel.setVisible(true);
                        datalibelleLabel.setText(getTableView().getItems().get(getIndex()).getLibelle());
                        dataequipeLabel.setText(getTableView().getItems().get(getIndex()).getEmail_equipe());
                        datastatutLabel.setText(getTableView().getItems().get(getIndex()).getStatut());
                        if ("acceptée".equals(datastatutLabel.getText())) {
                            datastatutLabel.setTextFill(Color.GREEN);
                        } else {
                            datastatutLabel.setTextFill(Color.RED);
                        }
                        dataa1Label.setText(String.valueOf(getTableView().getItems().get(getIndex()).getA1()) + " u.a");
                        dataa2Label.setText(String.valueOf(getTableView().getItems().get(getIndex()).getA2()) + " u.a");
                        if ((getTableView().getItems().get(getIndex()).getDate_transmission()) == null) {
                            datadatetransmissionLabel.setText("Résultats non transmis");
                        } else {
                            datadatetransmissionLabel.setText(getTableView().getItems().get(getIndex()).getDate_transmission());
                        }
                        datafrequenceLabel.setText(String.valueOf(getTableView().getItems().get(getIndex()).getFrequence()) + " u.a");
                        datatypeLabel.setText(getTableView().getItems().get(getIndex()).getType_exp());
                        datadatedemandeLabel.setText(getTableView().getItems().get(getIndex()).getDate_demande());
                        if (getTableView().getItems().get(getIndex()).getDate_deb() == null) {
                            datadatedebutLabel.setText("Expérience non démarrée");
                        } else {
                            datadatedebutLabel.setText(getTableView().getItems().get(getIndex()).getDate_deb());
                        }
                        datanbslotsLabel.setText(String.valueOf(getTableView().getItems().get(getIndex()).getNb_slot()) + " u.a");
                        datadureeLabel.setText(String.valueOf(getTableView().getItems().get(getIndex()).getDuree()) + " heure(s)");
                        dataa3Label.setText(String.valueOf(getTableView().getItems().get(getIndex()).getA3()) + " u.a");
                        if (getTableView().getItems().get(getIndex()).getA3() == 0 && getTableView().getItems().get(getIndex()).getFrequence() == 0) {
                            datasuiviLabel.setText("NON");
                            datafrequenceLabel.setText("Pas de valeur");
                            dataa3Label.setText("Pas de valeur");
                        } else {
                            datasuiviLabel.setText("OUI");
                        }
                        //Affichage des resultats de l'expérience dans 2 tableView
                        resultatsExp(idExperience);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        //Affichage du bouton et de l'image 
                        btnDetails.setGraphic(new ImageView(new Image(getClass().getResource("details.png").toExternalForm(), 20, 20, true, true)));
                        setGraphic(btnDetails);
                        setStyle("-fx-alignment : CENTER;");
                    }
                }
            };
            return cellDetails;
        };
        //Ajout de la cellulle à la colonne
        colonneDetails.setCellFactory(cellFactoryDetails);
        //Ajout de la colonne à la tableView
        viewTableView.getColumns().add(colonneDetails);

    }

    /**
     * Récupération des uplets de l'expérience et de leur résultats afin de
     * pouvoir les afficher dans des tableView pour le laborantin
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

                //Insertion différentes selon si le statut est renouvelé et si la date d'échéance est présente
                if (rs.getInt(5) == 0 && rs.getString(7) == null) {
                    Uplet uplet = new Uplet(rs.getInt(1), rs.getString(2), (rs.getString(3) + " µL"), (rs.getString(4) + " µL"), "Non", rs.getInt(6), "Aucune", rs.getString(8));
                    listUplet.add(uplet);
                } else if (rs.getInt(5) == 1 && rs.getString(7) == null) {
                    Uplet uplet = new Uplet(rs.getInt(1), rs.getString(2), (rs.getString(3) + " µL"), (rs.getString(4) + " µL"), "Oui", rs.getInt(6), "Aucune", rs.getString(8));
                    listUplet.add(uplet);
                } else if (rs.getInt(5) == 1 && rs.getString(7) != null) {
                    Uplet uplet = new Uplet(rs.getInt(1), rs.getString(2), (rs.getString(3) + " µL"), (rs.getString(4) + " µL"), "Oui", rs.getInt(6), rs.getString(7), rs.getString(8));
                    listUplet.add(uplet);
                } else {
                    Uplet uplet = new Uplet(rs.getInt(1), rs.getString(2), (rs.getString(3) + " µL"), (rs.getString(4) + " µL"), "Non", rs.getInt(6), rs.getString(7), rs.getString(8));
                    listUplet.add(uplet);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        //Déclaration des différentes colonnes pour afficher les caractéristiques des uplets d'une expérience
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
        //Ajout de la liste à la tableView
        expUpletTableView.setItems(listUplet);
        //Ajout des colonnes à la tableView
        expUpletTableView.getColumns().addAll(colonneidUplet, colonneTypeCell, colonneAgent, colonneCellule, colonneRenouv, colonneDateEche, colonneEtat);

        //Récupération des résultats uplets de l'expérience
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
        //Affichage des résultats opacimétriques ou colorimétriques selon le type de l'expérience
        if (datatypeLabel.getText().equals("opacimétrique")) {
            ///////////////////////////////////////////////////////////////////////////
            TableColumn<ResUplet, String> colonneMoyenneTR = new TableColumn<>("Transparence : Moyenne");
            colonneMoyenneTR.setCellValueFactory(new PropertyValueFactory("moyenneTR"));
            colonneMoyenneTR.setStyle("-fx-alignment: CENTER;");
            ////////////////////////////////////////////////////////////////////////////
            TableColumn<ResUplet, String> colonneEcartTypeTR = new TableColumn<>("Transparence : Ecart-type");
            colonneEcartTypeTR.setCellValueFactory(new PropertyValueFactory("ecartTypeTR"));
            colonneEcartTypeTR.setStyle("-fx-alignment: CENTER;");
            ///////////////////////////////////////////////////////////////////////////
            resUpletTableView.getColumns().addAll(colonneidUpletRes, colonneMoyenneTR, colonneEcartTypeTR);
        }
        if (datatypeLabel.getText().equals("colorimétrique")) {
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
            resUpletTableView.getColumns().addAll(colonneidUpletRes, colonneMoyenneR, colonneEcartTypeR, colonneMoyenneV, colonneEcartTypeV, colonneMoyenneB, colonneEcartTypeB);
        }
        ///////////////////////////////////////////////////////////////////////////
        TableColumn<ResUplet, String> colonneStatutResUplet = new TableColumn<>("Statut du résultat");
        colonneStatutResUplet.setCellValueFactory(new PropertyValueFactory("statutUplet"));
        colonneStatutResUplet.setStyle("-fx-alignment: CENTER;");
        //////////////////////////////////////////////////////////////////////////
        //Ajout de la liste des résultats à la tableView
        resUpletTableView.setItems(listResUplet);
        //Ajout des colonnes à la tableView
        resUpletTableView.getColumns().addAll(colonneStatutResUplet);
    }

    /**
     * Méthode permettant la recherche des expériences selon les données
     * renseignées par le laborantin
     */
    private void rechercherExp() {
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
            if (listExp.get(i).getStatut().toUpperCase().equals(searchstatutTextField.getText().toUpperCase())) {
                listExpSearchStatut.add(listExp.get(i));
            }
        }
        //cas où uniquement l'équipe est renseignée
        for (int i = 0; i < listExp.size(); i++) {
            if (listExp.get(i).getEmail_equipe().toUpperCase().equals(searchEquipeTextField.getText().toUpperCase())) {
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
                if (listExp.get(i).getEmail_equipe().toUpperCase().equals(searchEquipeTextField.getText().toUpperCase()) && listExp.get(i).getDate_demande().equals(searchDate)) {
                    listExpSearchEquipeDate.add(listExp.get(i));
                }
            }
            //La date et le statut sont renseignés
            for (int i = 0; i < listExp.size(); i++) {
                if (listExp.get(i).getStatut().toUpperCase().equals(searchstatutTextField.getText().toUpperCase()) && listExp.get(i).getDate_demande().equals(searchDate)) {
                    listExpSearchStatutDate.add(listExp.get(i));
                }
            }
            //La date, le statut et l'équipe sont renseignés
            for (int i = 0; i < listExp.size(); i++) {
                if (listExp.get(i).getStatut().toUpperCase().equals(searchstatutTextField.getText().toUpperCase()) && listExp.get(i).getDate_demande().equals(searchDate) && listExp.get(i).getEmail_equipe().toUpperCase().equals(searchEquipeTextField.getText().toUpperCase())) {
                    listExpSearchAll.add(listExp.get(i));
                }
            }
        }
        //L'équipe et le statut sont renseignés
        for (int i = 0; i < listExp.size(); i++) {
            if (listExp.get(i).getEmail_equipe().toUpperCase().equals(searchEquipeTextField.getText().toUpperCase()) && listExp.get(i).getStatut().toUpperCase().equals(searchstatutTextField.getText().toUpperCase())) {
                listExpSearchStatutEquipe.add(listExp.get(i));
            }
        }

        //Affichage du tableau conditionné à la recherche faite par le laborantin --> Affichage des messages d'erreur dans le cas où la liste correspondant aux données saisies est vide
        if (searchEquipeTextField.getText().isEmpty() == true && localdate == null && searchstatutTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchStatut);
            if (listExpSearchStatut.isEmpty() == true) {
                warningSearch.setText("");
                warningSearch.setText("Aucun résultat ne correspond à votre recherche.");
                warningSearch.setVisible(true);
            }
        } else if (searchstatutTextField.getText().isEmpty() == true && localdate == null && searchEquipeTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchEquipe);
            if (listExpSearchEquipe.isEmpty() == true) {
                warningSearch.setText("");
                warningSearch.setText("Aucun résultat ne correspond à votre recherche.");
                warningSearch.setVisible(true);
            }
        } else if (searchEquipeTextField.getText().isEmpty() == true && searchstatutTextField.getText().isEmpty() == true && localdate != null) {
            viewTableView.setItems(listExpSearchDate);
            if (listExpSearchDate.isEmpty() == true) {
                warningSearch.setText("");
                warningSearch.setText("Aucun résultat ne correspond à votre recherche.");
                warningSearch.setVisible(true);
            }
        } else if (searchEquipeTextField.getText().isEmpty() == true && localdate != null && searchstatutTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchStatutDate);
            if (listExpSearchStatutDate.isEmpty() == true) {
                warningSearch.setText("");
                warningSearch.setText("Aucun résultat ne correspond à votre recherche.");
                warningSearch.setVisible(true);
            }
        } else if (searchEquipeTextField.getText().isEmpty() == false && localdate == null && searchstatutTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchStatutEquipe);
            if (listExpSearchStatutEquipe.isEmpty() == true) {
                warningSearch.setText("");
                warningSearch.setText("Aucun résultat ne correspond à votre recherche.");
                warningSearch.setVisible(true);
            }
        } else if (searchEquipeTextField.getText().isEmpty() == false && localdate != null && searchstatutTextField.getText().isEmpty() == true) {
            viewTableView.setItems(listExpSearchEquipeDate);
            if (listExpSearchEquipeDate.isEmpty() == true) {
                warningSearch.setText("");
                warningSearch.setText("Aucun résultat ne correspond à votre recherche.");
                warningSearch.setVisible(true);
            }
        } else if (searchEquipeTextField.getText().isEmpty() == false && localdate != null && searchstatutTextField.getText().isEmpty() == false) {
            viewTableView.setItems(listExpSearchAll);
            if (listExpSearchAll.isEmpty() == true) {
                warningSearch.setText("");
                warningSearch.setText("Aucun résultat ne correspond à votre recherche.");
                warningSearch.setVisible(true);
            }
        } else {
            //Si les 3 champs sont vides -> erreur
            searchEquipeTextField.setStyle("-fx-border-color: RED");
            searchstatutTextField.setStyle("-fx-border-color: RED");
            searchdateTextField.setStyle("-fx-border-color: RED");
            warningSearch.setText("");
            warningSearch.setText("Veuillez remplir au moins un champs.");
            warningSearch.setVisible(true);
        }

        viewTableView.refresh();
    }

    /**
     * Méthode d'ajout d'un expérience. Cette méthode est appelée dans les
     * listeners d'ajout d'expérience
     */
    private void addExp() {
        //Remise à 0 des bordures rouges lorsqu'il y a une erreur
        typeExpComboBox.setStyle("-fx-border-width:0px");
        equipeComboBox.setStyle("-fx-border-width:0px");
        dureeExpTextField.setStyle("-fx-border-width:0px");
        nbSlotTextField.setStyle("-fx-border-width:0px");
        a2TextField.setStyle("-fx-border-width:0px");
        a1TextField.setStyle("-fx-border-width:0px");
        a3TextField.setStyle("-fx-border-width:0px");
        frequenceTextField.setStyle("-fx-border-width:0px");
        dateDemandeDate.setStyle("-fx-border-width:0px");
        libelleTextField.setStyle("-fx-border-width:0px");
        ouiRadio.setStyle("-fx-border-width:0px");
        nonRadio.setStyle("-fx-border-width:0px");

        //Si la date est différente de nulle, alors la saisie peut continuer
        if (dateDemandeDate.getValue() != null) {
            //Récupération des valeurs saisies par le chercheur
            LocalDate localdate = dateDemandeDate.getValue();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = localdate.format(dateTimeFormatter);
            String libelle = libelleTextField.getText();
            String frequence = frequenceTextField.getText();
            String a3 = a3TextField.getText().replace(",", "."); //remplacement de la virgule par un point pour permettent à l'utilisateur d'utilister une virgule ou un point (pas de contraintes)
            String a1 = a1TextField.getText().replace(",", ".");
            String a2 = a2TextField.getText().replace(",", ".");
            String nb_slot = nbSlotTextField.getText();
            String duree = dureeExpTextField.getText().replace(",", ".");
            String email = "";
            String statut = "à faire";
            //Déclaration pour le transtypage des String en numérique
            double intduree = -1;
            double inta1 = -1;
            double inta2 = -1;
            double inta3 = -1;
            int intfrequence = -1;
            int intnbslot = -1;

            //Transtypage de la duree de l'expérience si les valeurs saisies sont des chiffres et non des caractères            
            if (duree.matches("^[0-9]+(.[0-9]+)?$")) {
                intduree = Double.parseDouble(duree);
            }
            //Transtypage de a1 si les valeurs saisies sont des chiffres et non des caractères
            if (a1.matches("^[0-9]+(.[0-9]+)?$")) {
                inta1 = Double.parseDouble(a1);
            }

            //Transtypage de a2 si les valeurs saisies sont des chiffres et non des caractères
            if (a2.matches("^[0-9]+(.[0-9]+)?$")) {
                inta2 = Double.parseDouble(a2);
            }
            //Transtypage du nombre de slots si les valeurs saisies sont des chiffres et non des caractères
            if (nbSlotTextField.getText().matches("^[0-9]+$")) {
                intnbslot = Integer.parseInt(nbSlotTextField.getText());
            }
            //Transtypage de a3 si les valeurs saisies sont des chiffres et non des caractères
            if (a3.matches("^[0-9]+(.[0-9]+)?$")) {
                inta3 = Double.parseDouble(a3);
            }
            //Transtypage de la fréquence si les valeurs saisies sont des chiffres et non des caractères
            if (frequenceTextField.getText().matches("^[0-9]+$")) {
                intfrequence = Integer.parseInt(frequenceTextField.getText());
            }

            //Si tous les champs son remplies
            if (libelle.isEmpty() == false && dateString.isEmpty() == false && a1.isEmpty() == false && a2.isEmpty() == false && nb_slot.isEmpty() == false && duree.isEmpty() == false && equipeComboBox.getValue() != null && typeExpComboBox.getValue() != null && (ouiRadio.isSelected() == true || nonRadio.isSelected() == true)) {
                String nomEquipe = (String) equipeComboBox.getValue();
                String typeExp = (String) typeExpComboBox.getValue();
                //Récupération de l'email de l'équipe selectionnée
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs1 = stmt.executeQuery("SELECT EMAIL_EQUIPE FROM EQUIPE WHERE NOM_EQUIPE ='" + nomEquipe + "'");
                    rs1.next();
                    email = rs1.getString(1);
                } catch (SQLException e) {
                    System.out.println(e);
                }

                //requête d'insertion d'une expérience
                try {
                    if (inta3 != -1 && intfrequence != -1 && ouiRadio.isSelected() == true && intduree != -1 && inta1 != -1 && inta2 != -1 && intnbslot != -1) {
                        Statement stmt1 = con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery("INSERT INTO EXPERIENCE (LIBELLE_EXP, EMAIL_EQUIPE, DATE_DEMANDE, TYPE_EXP, A1, A2, NB_SLOT, DUREE_EXP, A3, STATUT_EXP, FREQUENCE) values ('" + libelle + "', '" + email + "', '" + dateString + "', '" + typeExp + "', " + inta1 + ", " + inta2 + ", " + intnbslot + ", " + intduree + ", " + inta3 + ", '" + statut + "', '" + intfrequence + "')");
                        //Réinitialisation des textFields, ComboBox et suppression des bordures rouge
                        clearChercheur();
                        //Chargement des comboBox
                        chargementComboBox();
                        warningLabel.setText("Expérience ajoutée.");
                        warningLabel.setVisible(true);

                    } else if (("".equals(a3) && "".equals(frequence)) && ouiRadio.isSelected() == true) {
                        //Expérience suivie dans le temps où la fréquence et a3 sont nulles -> encadrement en rouge
                        warningLabel.setText("");
                        warningLabel.setText("Veuillez remplir tous les champs.");
                        warningLabel.setVisible(true);
                        a3TextField.setStyle("-fx-border-color: RED");
                        frequenceTextField.setStyle("-fx-border-color: RED");
                    } else if ("".equals(a3) && ouiRadio.isSelected() == true) {
                        //Expérience suivie dans le temps où a3 est null -> encadrement en rouge
                        a3TextField.setStyle("-fx-border-color: RED");
                        frequenceTextField.setStyle("-fx-border-width:0px");
                    } else if ("".equals(frequence) && ouiRadio.isSelected() == true) {
                        //Expérience suivie dans le temps où la fréquence est null -> encadrement en rouge
                        frequenceTextField.setStyle("-fx-border-color: RED");
                        a3TextField.setStyle("-fx-border-width:0px");
                    } else if (ouiRadio.isSelected() == false && intduree != -1 && inta1 != -1 && inta2 != -1 && intnbslot != -1) {
                        //Si toutes les valeurs sont renseignées -> INSERTION
                        Statement stmt2 = con.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("INSERT INTO EXPERIENCE (LIBELLE_EXP, EMAIL_EQUIPE, DATE_DEMANDE, TYPE_EXP, A1, A2, NB_SLOT, DUREE_EXP, STATUT_EXP) "
                                + "values ('" + libelle + "', '" + email + "', '" + dateString + "', '" + typeExp + "', " + inta1 + ", " + inta2 + ", " + intnbslot + ", " + intduree + ", '" + statut + "')");
                        //Réinitialisation des textFields, ComboBox et suppression des bordures rouge
                        clearChercheur();
                        //Chargement des comboBox
                        chargementComboBox();
                        warningLabel.setText("Expérience ajoutée.");
                        warningLabel.setVisible(true);
                    } else {
                        //Encadrement de la duree si la valeur saisie n'est pas un nombre
                        if (!dureeExpTextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                            dureeExpTextField.setStyle("-fx-border-color: RED");
                            warningLabel.setText("");
                            warningLabel.setText("Valeur saisie incorrecte.");
                            warningLabel.setVisible(true);
                        }
                        //Encadrement de a1 si la valeur saisie n'est pas un nombre
                        if (!a1TextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                            a1TextField.setStyle("-fx-border-color: RED");
                            warningLabel.setText("");
                            warningLabel.setText("Valeur saisie incorrecte.");
                            warningLabel.setVisible(true);
                        }

                        //Encadrement de a2 si la valeur saisie n'est pas un nombre
                        if (!a2TextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                            a2TextField.setStyle("-fx-border-color: RED");
                            warningLabel.setText("");
                            warningLabel.setText("Valeur saisie incorrecte.");
                            warningLabel.setVisible(true);
                        }
                        //Encadrement du nombre de slots si la valeur saisie n'est pas un nombre
                        if (!nbSlotTextField.getText().matches("^[0-9]+$")) {
                            nbSlotTextField.setStyle("-fx-border-color: RED");
                            warningLabel.setText("");
                            warningLabel.setText("Valeur saisie incorrecte. Le nombre de puits doit être un entier.");
                            warningLabel.setVisible(true);
                        }

                        if (ouiRadio.isSelected() == true) {
                            //Encadrement de la fréquence si la valeur saisie n'est pas un nombre
                            if (!frequenceTextField.getText().matches("^[0-9]+$")) {
                                frequenceTextField.setStyle("-fx-border-color: RED");
                                warningLabel.setText("");
                                warningLabel.setText("Valeur saisie incorrecte. La fréquence doit être un entier.");
                                warningLabel.setVisible(true);
                            }

                            //Encadrement de a3 si la valeur saisie n'est pas un nombre
                            if (a3TextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                                if (inta3 < 0 || inta3 > 1) {
                                    a3TextField.setStyle("-fx-border-color: RED");
                                    warningLabel.setText("");
                                    warningLabel.setText("Attention, la valeur de a3 doit être comprise entre 0 et 1.");
                                    warningLabel.setVisible(true);
                                }
                            } else {
                                a3TextField.setStyle("-fx-border-color: RED");
                                warningLabel.setText("");
                                warningLabel.setText("Valeur saisie incorrecte.");
                                warningLabel.setVisible(true);
                            }
                        }
                    }

                } catch (SQLException e) {
                    System.out.println("Exception SQL : ");
                    while (e != null) {
                        //Encadrement de la durée si la valeur saisie n'est pas un nombre
                        if (!dureeExpTextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                            dureeExpTextField.setStyle("-fx-border-color: RED");
                            warningLabel.setText("");
                            warningLabel.setText("Valeur saisie incorrecte.");
                            warningLabel.setVisible(true);
                        }
                        //Encadrement de a1 si la valeur saisie n'est pas un nombre
                        if (!a1TextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                            a1TextField.setStyle("-fx-border-color: RED");
                            warningLabel.setText("");
                            warningLabel.setText("Valeur saisie incorrecte.");
                            warningLabel.setVisible(true);
                        }

                        //Encadrement de a2 si la valeur saisie n'est pas un nombre
                        if (!a2TextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                            a2TextField.setStyle("-fx-border-color: RED");
                            warningLabel.setText("");
                            warningLabel.setText("Valeur saisie incorrecte.");
                            warningLabel.setVisible(true);
                        }
                        //Encadrement du nombre de slots si la valeur saisie n'est pas un nombre
                        if (!nbSlotTextField.getText().matches("^[0-9]+$")) {
                            nbSlotTextField.setStyle("-fx-border-color: RED");
                            warningLabel.setText("");
                            warningLabel.setText("Valeur saisie incorrecte.");
                            warningLabel.setVisible(true);
                        }

                        if (ouiRadio.isSelected() == true) {
                            //Encadrement de la fréquence si la valeur saisie n'est pas un nombre
                            if (!frequenceTextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                                frequenceTextField.setStyle("-fx-border-color: RED");
                                warningLabel.setText("");
                                warningLabel.setText("Valeur saisie incorrecte.");
                                warningLabel.setVisible(true);
                            }

                            //Encadrement de a3 si la valeur saisie n'est pas un nombre
                            if (a3TextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                                if (inta3 < 0 || inta3 > 1) {
                                    a3TextField.setStyle("-fx-border-color: RED");
                                    warningLabel.setText("");
                                    warningLabel.setText("Attention, la valeur de a3 doit être comprise entre 0 et 1.");
                                    warningLabel.setVisible(true);
                                }
                            } else {
                                a3TextField.setStyle("-fx-border-color: RED");
                                warningLabel.setText("");
                                warningLabel.setText("Valeur saisie incorrecte.");
                                warningLabel.setVisible(true);
                            }
                        }
                        //Vérification que la valeur de a1 est bien supérieure à celle de a2
                        if (inta1 > inta2) {
                            a1TextField.setStyle("-fx-border-color: RED");
                            a2TextField.setStyle("-fx-border-color: RED");
                            warningLabel.setText("");
                            warningLabel.setText("Attention, la valeur de a2 doit être supérieure à celle de a1.");
                            warningLabel.setVisible(true);
                        }
                        e = e.getNextException();
                    }
                }
            } else { //Cas où certain champs sont vides -> Affichage des bordures en rouge
                warningLabel.setText("");
                warningLabel.setText("Veuillez remplir tous les champs.");
                warningLabel.setVisible(true);
                if (libelle.isEmpty() == true) {
                    libelleTextField.setStyle("-fx-border-color: RED");
                }
                if (a1.isEmpty() == true) {
                    a1TextField.setStyle("-fx-border-color: RED");
                }
                if (a2.isEmpty() == true) {
                    a2TextField.setStyle("-fx-border-color: RED");
                }
                if (nb_slot.isEmpty() == true) {
                    nbSlotTextField.setStyle("-fx-border-color: RED");
                }
                if (duree.isEmpty() == true) {
                    dureeExpTextField.setStyle("-fx-border-color: RED");
                }
                if (equipeComboBox.getValue() == null) {
                    equipeComboBox.setStyle("-fx-border-color: RED");
                }
                if (typeExpComboBox.getValue() == null) {
                    typeExpComboBox.setStyle("-fx-border-color: RED");
                }
                if (ouiRadio.isSelected() == false && nonRadio.isSelected() == false) {
                    ouiRadio.setStyle("-fx-border-color: RED");
                    nonRadio.setStyle("-fx-border-color: RED");
                }
            }
        } else {
            dateDemandeDate.setStyle("-fx-border-color: RED");
            warningLabel.setText("");
            warningLabel.setText("Veuillez remplir tous les champs.");
            warningLabel.setVisible(true);
        }

    }

    /**
     * Méthode d'insertion d'un uplet appelée dans les listeners
     */
    private void addUplet() {

        //Suppression des bordures rouges
        expSelectedComboBox.setStyle("-fx-border-width:0px");
        typeCelluleComboBox.setStyle("-fx-border-width:0px");
        qteAgentTextField.setStyle("-fx-border-width:0px");
        qteCelluleTextField.setStyle("-fx-border-width:0px");
        nomAgentComboBox.setStyle("-fx-border-width:0px");

        //Récupération des valeurs renseignées par l'utilisateur
        String qteAgent = qteAgentTextField.getText().replace(",", ".");
        String qteCell = qteCelluleTextField.getText().replace(",", ".");
        double intqteAgent = -1;
        double intqteCell = -1;

        //Transtypage des string en double uniquement si la valeur saisie contient des chiffres
        if (qteAgent.matches("^[0-9]+(.[0-9]+)?$")) {
            intqteAgent = Double.parseDouble(qteAgent);
        }
        if (qteCell.matches("^[0-9]+(.[0-9]+)?$")) {
            intqteCell = Double.parseDouble(qteCell);
        }

        //Si toutes les valeurs sont renseignés et correctes, l'uplet peut alors être ajouté
        if (nomAgentComboBox.getValue() != null && expSelectedComboBox.getValue() != null && typeCelluleComboBox.getValue() != null && qteAgentTextField.getText().isEmpty() == false && qteCelluleTextField.getText().isEmpty() == false) {
            //Ajout de l'uplet
            if (intqteAgent != -1 && intqteCell != -1) {
                try {
                    Statement stmt1 = con.createStatement();
                    ResultSet rs1 = stmt1.executeQuery("INSERT INTO N_UPLET (ID_EXPERIENCE, TYPE_CELLULE, Q_AGENT, Q_CELLULE) values (" + (expSelectedComboBox.getValue() + "").split(" ")[0] + ",  '" + typeCelluleComboBox.getValue() + "', " + intqteAgent + ", " + intqteCell + " )");
                    //Réinitialisation des textFields, ComboBox et suppression des bordures rouge
                    clearChercheur();
                    //Chargement des comboBox
                    chargementComboBox();
                    warningUpletLabel.setText("Réplicat ajouté.");
                    warningUpletLabel.setVisible(true);
                } catch (SQLException e) {
                    while (e != null) {
                        String message = e.getMessage();
                        int errorCode = e.getErrorCode();
                        System.out.println(message);
                        //Affichage des bordures rouges si la valeur saisie n'est pas correcte
                        if (!qteAgentTextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                            qteAgentTextField.setStyle("-fx-border-color: RED");
                            warningUpletLabel.setText("");
                            warningUpletLabel.setText("Valeur non valide.");
                            warningUpletLabel.setVisible(true);
                        }
                        if (!qteCelluleTextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                            qteCelluleTextField.setStyle("-fx-border-color: RED");
                            warningUpletLabel.setText("");
                            warningUpletLabel.setText("Valeur non valide.");
                            warningUpletLabel.setVisible(true);
                        }
                        e = e.getNextException();
                    }
                }
            } else {
                if (!qteAgentTextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                    qteAgentTextField.setStyle("-fx-border-color: RED");
                    warningUpletLabel.setText("");
                    warningUpletLabel.setText("Valeur non valide.");
                    warningUpletLabel.setVisible(true);
                }
                if (!qteCelluleTextField.getText().matches("^[0-9]+(.[0-9]+)?$")) {
                    qteCelluleTextField.setStyle("-fx-border-color: RED");
                    warningUpletLabel.setText("");
                    warningUpletLabel.setText("Valeur non valide.");
                    warningUpletLabel.setVisible(true);
                }
            }
        } else {
            //Afficahge des bordures rouge si des champs sont vides
            warningUpletLabel.setText("");
            warningUpletLabel.setText("Veuillez remplir tous les champs.");
            warningUpletLabel.setVisible(true);
            //Affichage en rouge des contours lorsque les champs sont vides
            if (expSelectedComboBox.getValue() == null) {
                expSelectedComboBox.setStyle("-fx-border-color: RED");
            }
            if (typeCelluleComboBox.getValue() == null) {
                typeCelluleComboBox.setStyle("-fx-border-color: RED");
            }
            if (qteAgentTextField.getText().isEmpty() == true) {
                qteAgentTextField.setStyle("-fx-border-color: RED");
            }
            if (qteCelluleTextField.getText().isEmpty() == true) {
                qteCelluleTextField.setStyle("-fx-border-color: RED");
            }
            if (nomAgentComboBox.getValue() == null) {
                nomAgentComboBox.setStyle("-fx-border-color: RED");
            }
        }
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
     * Méthode permettant le chargement des comboBox dans la partie chercheur
     */
    private void chargementComboBox() {
        //Récupération des équipes enregistrés et ajout à la ComboBox du choix de l'équipe dans le formulaire
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOM_EQUIPE FROM EQUIPE");
            while (rs.next()) {
                equipeComboBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        //Ajout des propositions de choix à la ComboBox de choix du type d'expérience
        typeExpComboBox.getItems().addAll("colorimétrique", "opacimétrique");
        //Ajout des propositions de choix à la ComboBox de choix du type de cellules
        typeCelluleComboBox.getItems().addAll("cancéreuse", "non-cancéreuse");

        // Affichage de la liste des expériences terminées
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT ID_EXPERIENCE, LIBELLE_EXP, NOM_EQUIPE FROM EXPERIENCE JOIN EQUIPE ON EXPERIENCE.EMAIL_EQUIPE = EQUIPE.EMAIL_EQUIPE WHERE TERMINE = 0 AND STATUT_EXP = 'à faire'");
            while (rs.next()) {
                expSelectedComboBox.getItems().add(rs.getString(1) + " - " + rs.getString(2) + " - Equipe " + rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Affichage du produit
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
     * Méthode permettant la réinitialisation des comboBox, textField et
     * messages d'erreur pour la partie chercheur
     */
    private void clearChercheur() {

        //Suppression des cadres rouge sur la partie de recherche
        searchEquipeTextField.setStyle("-fx-border-width:0px");
        searchstatutTextField.setStyle("-fx-border-width:0px");
        searchdateTextField.setStyle("-fx-border-width:0px");
        warningSearch.setText("");

        //Suppression des cadres rouge sur la partie d'insertion d'une expérience
        typeExpComboBox.setStyle("-fx-border-width:0px");
        equipeComboBox.setStyle("-fx-border-width:0px");
        dureeExpTextField.setStyle("-fx-border-width:0px");
        nbSlotTextField.setStyle("-fx-border-width:0px");
        a2TextField.setStyle("-fx-border-width:0px");
        a1TextField.setStyle("-fx-border-width:0px");
        a3TextField.setStyle("-fx-border-width:0px");
        frequenceTextField.setStyle("-fx-border-width:0px");
        dateDemandeDate.setStyle("-fx-border-width:0px");
        libelleTextField.setStyle("-fx-border-width:0px");
        ouiRadio.setStyle("-fx-border-width:0px");
        nonRadio.setStyle("-fx-border-width:0px");
        ouiRadio.setSelected(false);
        nonRadio.setSelected(false);
        //Réinitialisé le message d'erreur sur la partie d'insertion d'une expérience
        warningLabel.setText("");
        //Réinitialisation des textFields et des comboBox sur la partie d'insertion d'une expérience
        dateDemandeDate.setValue(null);
        libelleTextField.clear();
        frequenceTextField.clear();
        a3TextField.clear();
        a1TextField.clear();
        a2TextField.clear();
        nbSlotTextField.clear();
        dureeExpTextField.clear();
        equipeComboBox.getItems().clear();
        typeExpComboBox.getItems().clear();

        //Suppression des cadres rouge sur la partie d'insertion d'un uplet
        expSelectedComboBox.setStyle("-fx-border-width:0px");
        typeCelluleComboBox.setStyle("-fx-border-width:0px");
        qteAgentTextField.setStyle("-fx-border-width:0px");
        qteCelluleTextField.setStyle("-fx-border-width:0px");
        nomAgentComboBox.setStyle("-fx-border-width:0px");
        //Réinitialisé le message d'erreur sur la partie d'insertion d'un uplet
        warningUpletLabel.setText("");
        //Réinitialisation des textFields et des comboBox sur la partie d'insertion d'un uplet
        nomAgentComboBox.getItems().clear();
        expSelectedComboBox.getItems().clear();
        typeCelluleComboBox.getItems().clear();
        qteAgentTextField.clear();
        qteCelluleTextField.clear();
    }

    /**
     * Listener permettant le retour à la liste des expériences
     *
     * @param : event
     */
    @FXML
    private void backListExp(MouseEvent event) {
        reloadTableauExp();
        viewdetailsPanel.setVisible(false);
    }
    
    /**
     * Listener permettant d'afficher une aide pour l'utilisateur
     *
     * @param : event
     */
    @FXML
    private void setHelpOn(MouseEvent event) {
        bulbIcon.setImage(new Image(getClass().getResource("bulb_white.png").toExternalForm()));
        helpPane.setVisible(true);
        if ("laborantin".equals(functionUser)){
            helpText.setText("Vous êtes un(e) "+functionUser+". \n◆ Gérer vos expériences !\nVia l'onglet 👁, changer le statut des expériences,\naccéder aux résultats ou transmetter-les au(x)\nchercheur(s).\n◆ Planifier vos analyses !\nVia l'onglet □, définisser les plaques sur lesquelles\nvous effectuez vos analyses.");
        } else if ("chercheur".equals(functionUser)) {
            helpText.setText("Vous êtes un(e) "+functionUser+". \n◆ Commander simplement des expériences !\nVia l'onglet +, demander simplement la réalisation de vos\nexpériences à l'aide de notre assistant\nvous assurant une qualité optimale.\n◆ Préciser vos protocoles !\nVia l'onglet ○, préciser à nos services\nles protocoles précis que vous souhaitez\nmettre en place pour chaque réplica d'essai\nen les associant aux expériences.");
        }
    }
    
    /**
     * Listener permettant de retirer l'aide pour l'utilisateur
     *
     * @param : event
     */
    @FXML
    private void setHelpOff(MouseEvent event) {
        bulbIcon.setImage(new Image(getClass().getResource("bulb_black.png").toExternalForm()));
        helpPane.setVisible(false);
    }
    
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
