package projetihm;

import java.sql.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private Label fonctionLabel, welcomeLabel, loginErrorLabel, frequenceLabel, a3Label, frequenceviewLabel, a3viewLabel, datalibelleLabel, dataequipeLabel, datastatutLabel, dataa1Label, dataa2Label, datadatetransmissionLabel, datafrequenceLabel, datatypeLabel, datadatedemandeLabel, datadatedebutLabel, datanbslotsLabel, datadureeLabel, dataa3Label;
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
    private ComboBox equipeComboBox, typeExpComboBox, typeCelluleComboBox;
    @FXML
    private TableView viewTableView;
    @FXML
    private TableColumn idColumn1, libelleColumn1, typeColumn1, datedemandeColumn1, equipeColumn1, launcherColumn1, statutColumn1, detailsColumn1;
    //Variables d'environnement user
    private static String nameUser, functionUser;
    //Variables non-FXML
    private Integer existingUser;
    private Connection con;
    private ObservableList<ObservableList> viewData;
    
    /**
     * Méthode qui permet de connecter l'utilisateur à notre application en contrôlant ses identifiants
     * qui renvoie ensuite au tableau de bord de l'application
     * @param event
     */
    @FXML
    private void connectUser(ActionEvent event) {
        //Contrôle si les champs de saisie pour se connecter ne sont pas vides
        if (mailTextField.getText().isEmpty() == false && passwordTextField.getText().isEmpty() == false) {
            //Connexion en utilisant la BDD
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT PERSONNE.PRENOM_PERSONNE, FONCTION.LIBELLE_FONCTION "
                        + "FROM PERSONNE "
                        + "JOIN FONCTION ON PERSONNE.ID_FONCTION = FONCTION.ID_FONCTION "
                        + "WHERE EMAIL_PERSONNE ='" + mailTextField.getText() + "' AND PASSWORD = '" + passwordTextField.getText() + "'");
                existingUser = 0;
                while (rs.next()) {
                    existingUser = 1;
                    nameUser = rs.getString(1);
                    functionUser = rs.getString(2);
                }
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
     * Méthode permettant la déconnexion de l'utilisateur connecté sur l'interface où la méthode est appelée
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
     * Méthode permettant l'affichage du panel central de gestion des expériences (où l'on retrouve la possibilité
     * d'ajouter une expérience, une plaque, ...)
     * ainsi que la gestion de l'UX des menus (surlignage du module où navigue l'utilisateur)
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
     * Méthode permettant l'affichage du panel central permettant d'avoir une vue d'ensemble des fonctionnalités
     * disponibles à l'utilisateur, ainsi que son profil avec la possibilité de se déconnecter
     * ainsi que la gestion de l'UX des menus (surlignage du module où navigue l'utilisateur)
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
     * Méthode permettant l'affichage du panel central permettant la gestion des plaques et le positionnement des puits
     * ainsi que la gestion de l'UX des menus (surlignage du module où navigue l'utilisateur)
     * @param event
     */
    @FXML
    private void setPlaquePanel(MouseEvent event) {
        //Affichage graphique de la plaque permettant de positionner les expériences sollicités par les chercheurs
        //Ici, 96 par défaut en attendant la connexion avec la BDD
        setSlotsPositionChecker(96);
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
     * Méthode permettant l'affichage et la personnalisation de la représentation graphique de la plaque sélectionnée
     * lors du positionnement des données associés à chaque puit/slot de la part du laborantin
     * @param nb_slots : paramètre décrivant le nom de slot/puit de la plaque sélectionnée et 
     *                   determinant alors l'affichage graphique de la plaque à afficher
     */
    private void setSlotsPositionChecker(Integer nb_slots) {
        //Re-initialisation des contraintes de dimensionnement de l'affichage graphique de la plaque
        rectPlaque.getRowConstraints().clear();
        rectPlaque.getColumnConstraints().clear();
        //Initiatilasion des variables de boucles, du nombre de colonnes et de lignes à 0
        Integer c = 0, l = 0, i, j;
        //Si la plaque de culture à 96 puits, alors l'affichage doit être sous forme de 12 colonnes et 8 lignes 
        if (nb_slots == 96) {
            c = 12;
            l = 8;
        //Si la plaque de culture à 384 puits, alors l'affichage doit être sous forme de 24 colonnes et 16 lignes 
        } else if (nb_slots == 384) {
            c = 24;
            l = 16;
        }
        //Ajout, ligne par ligne, colonne par colonne, de boutons radios représentant les puits au GridPane représentant la plaque
        i = 0;
        while (i < l) {
            j = 0;
            while (j < c) {
                rectPlaque.add(new RadioButton(), j, i);
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
     * Méthode permettant l'affichage du panel central permettant la visualisation de toutes les expériences stockées
     * ainsi que la gestion de l'UX des menus (surlignage du module où navigue l'utilisateur)
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
     * Méthode permettant l'affichage du panel central permettant l'ajout d'une expérience
     * ainsi que la gestion de l'UX des menus (surlignage du module où navigue l'utilisateur)
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
                System.out.println(rs.getString(1));
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
     * Méthode permettant l'affichage du panel central permettant l'ajout d'un uplet/réplica de la
     * part du chercheur ainsi que la gestion de l'UX des menus (surlignage du module où navigue l'utilisateur)
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
    }
   
    /**
     * Méthode activant l'affichage du formulaire permettant la saisie des paramètres d'une
     * expérience suivi dans le temps, lors de l'ajout d'une expérience
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
     * Méthode désactivant l'affichage du formulaire permettant la saisie des paramètres d'une
     * expérience suivi dans le temps, lors de l'ajout d'une expérience
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
     * @param event
     */
    @FXML
    private void ajoutExperience(ActionEvent event) {
        String libelle = libelleTextField.getText();
        LocalDate date = dateDemandeDate.getValue();
        System.err.println("Selected date: " + date);
        String frequence = frequenceTextField.getText();
        String a3 = a3TextField.getText();
        String a1 = a1TextField.getText();
        int a1OK = Integer.parseInt(a1);
        String a2 = a2TextField.getText();
        int a2OK = Integer.parseInt(a2);
        String nb_slot = nbSlotTextField.getText();
        int nb_slotOK = Integer.parseInt(nb_slot);
        String duree = dureeExpTextField.getText();
        int dureeOK = Integer.parseInt(duree);
        String nomEquipe = (String) equipeComboBox.getValue();
        System.out.println(nomEquipe);
        String typeExp = (String) typeExpComboBox.getValue();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT EMAIL_EQUIPE FROM EQUIPE WHERE NOM_EQUIPE ='" + nomEquipe + "'");
            rs1.next();
            String email = rs1.getString(1);
            System.out.println(email);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            if (a3 != null && frequence != null) {
                int a3OK = Integer.parseInt(a3);
                int frequenceOK = Integer.parseInt(frequence);
                System.out.println("insertion1");
                Statement stmt1 = con.createStatement();
                ResultSet rs1 = stmt1.executeQuery("INSERT INTO EXPERIENCE (LIBELLE_EXP, EMAIL_EQUIPE, DATE_DEMANDE, TYPE_EXP, A1, A2, NB_SLOT, DUREE_EXP, A3, STATUT_EXP, FREQUENCE) values (libelle, email, date, typeExp, a1OK, a2OK, nb_slotOK, dureeOK, a3OK, 'à faire', frequenceOK)");
            } else {
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("INSERT INTO EXPERIENCE (LIBELLE_EXP, EMAIL_EQUIPE, DATE_DEMANDE, TYPE_EXP, A1, A2, NB_SLOT, DUREE_EXP, STATUT_EXP) values (libelle, email, date, typeExp, a1OK, a2OK, nb_slotOK, dureeOK, 'à faire')");
                System.out.println("insertion2");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Méthode permettant la recherche d'expérience parmis celle en base de données selon 3 critères non-obligatoires :
     * - l'équipe commanditaire
     * - le statut de l'expérience
     * - la date de début de l'expérience
     * @param event
     */
    @FXML
    private void searchExperience(ActionEvent event) {
        //
    }
    
    /**
     * Méthode permettant l'envoi des résultats de l'expérience sélectionnée à l'équipe de
     * recherche l'ayant commandité
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
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connectServer();
    }
}
