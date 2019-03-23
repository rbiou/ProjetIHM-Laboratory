/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm;

import java.sql.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 *
 * @author Remi
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView homeIcon, expIcon, upletIcon, eyeIcon, addIcon;
    @FXML
    private AnchorPane topPanel, welcomePanel, navPanel, loginPanel, menuPanel, insertPanel, viewPanel;
    @FXML
    private ScrollPane viewdetailsPanel, upletPanel;
    @FXML
    private Label fonctionLabel, welcomeLabel, loginErrorLabel, frequenceLabel, a3Label, frequenceviewLabel, a3viewLabel, datalibelleLabel, dataequipeLabel, datastatutLabel, dataa1Label, dataa2Label, datadatetransmissionLabel, datafrequenceLabel, datatypeLabel, datadatedemandeLabel, datadatedebutLabel, datanbslotsLabel, datadureeLabel, dataa3Label;
    @FXML
    private TableView viewTable;
        private TextField mailTextField, passwordTextField, frequenceTextField, a3TextField, libelleTextField, a1TextField, a2TextField, nbSlotTextField, dureeExpTextField;
    @FXML
    private DatePicker  dateDemandeDate; 
    @FXML
    private RadioButton ouiRadio, nonRadio;
    @FXML
    private ComboBox equipeComboBox, typeExpComboBox;
    @FXML
    private TableView viewTableView;
    @FXML
    private TableColumn idColumn1, libelleColumn1, typeColumn1, datedemandeColumn1, equipeColumn1, launcherColumn1, statutColumn1, detailsColumn1;
    //Variable d'environnement user
    private static String nameUser, functionUser;
    //Variables non-FXML
    private Integer existingUser;
    private Connection con;
    private ObservableList<ObservableList> viewData;

    @FXML
    private void connectUser(ActionEvent event) {
        if (mailTextField.getText().isEmpty() == false && passwordTextField.getText().isEmpty() == false) {
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
                welcomePanel.setVisible(true);
                welcomeLabel.setText("Bienvenue " + nameUser);
                welcomeLabel.setVisible(true);
                fonctionLabel.setText("Fonction : " + functionUser);
                fonctionLabel.setVisible(true);
                menuPanel.setVisible(true);
                loginPanel.setVisible(false);
                navPanel.setVisible(true);
                loginErrorLabel.setVisible(false);
                mailTextField.setText("");
                passwordTextField.setText("");
            } else {
                loginErrorLabel.setText("Mauvais identifiants.");
                loginErrorLabel.setVisible(true);
            }
        } else {
            loginErrorLabel.setText("Tous les champs doivent être remplis.");
            loginErrorLabel.setVisible(true);
        }
    }

    @FXML
    private void logOff(MouseEvent event) {
        loginPanel.setVisible(true);
        navPanel.setVisible(false);
        welcomePanel.setVisible(false);
        menuPanel.setVisible(false);
    }

    @FXML
    private void setExperiencePanel(MouseEvent event) {
        topPanel.setVisible(true);
        welcomePanel.setVisible(false);
        menuPanel.setVisible(false);
        viewPanel.setVisible(true);
        eyeIcon.setImage(new Image(getClass().getResource("eye_white.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
        homeIcon.setImage(new Image(getClass().getResource("home_black.png").toExternalForm()));
        expIcon.setImage(new Image(getClass().getResource("flask_white.png").toExternalForm()));
    }

    @FXML
    private void setHomePanel(MouseEvent event) {
        topPanel.setVisible(false);
        welcomePanel.setVisible(true);
        menuPanel.setVisible(true);
        viewPanel.setVisible(false);
        upletPanel.setVisible(false);
        insertPanel.setVisible(false);
        homeIcon.setImage(new Image(getClass().getResource("home_white.png").toExternalForm()));
        expIcon.setImage(new Image(getClass().getResource("flask_black.png").toExternalForm()));
    }

    @FXML
    private void setViewPanel(MouseEvent event) {
        //Récupération des données
        viewData = FXCollections.observableArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT ID_EXPERIENCE, LIBELLE_EXP, TYPE_EXP, DATE_DEMANDE, NOM_EQUIPE, STATUT_EXP "
                    + "FROM EXPERIENCE "
                    + "JOIN EQUIPE ON EXPERIENCE.EMAIL_EQUIPE = EQUIPE.EMAIL_EQUIPE");
            //setCellValueFactory du tableau affiché
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                viewTableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
            //Insertion des données dans le tableau
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                viewData.add(row);
            }
            viewTable.setItems(viewData);
        } catch (SQLException e) {
            System.out.println(e);
        }
        //Gestion des pages
        eyeIcon.setImage(new Image(getClass().getResource("eye_white.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
        viewPanel.setVisible(true);
        insertPanel.setVisible(false);
        upletPanel.setVisible(false);
    }

    @FXML
    private void setInsertPanel(MouseEvent event) {
        eyeIcon.setImage(new Image(getClass().getResource("eye_black.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_white.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_black.png").toExternalForm()));
        viewPanel.setVisible(false);
        insertPanel.setVisible(true);
        upletPanel.setVisible(false);
        equipeComboBox.getItems().clear();
        typeExpComboBox.getItems().clear();
        ///////////////////////////////////////////////////////////////////////////////
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
        //////////////////////////////////////////////////////////////////////////////
        typeExpComboBox.getItems().addAll("colorimétrique", "opacimétrique");
    }

    @FXML
    private void setUpletPanel(MouseEvent event
    ) {
        eyeIcon.setImage(new Image(getClass().getResource("eye_black.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_white.png").toExternalForm()));
        viewPanel.setVisible(false);
        insertPanel.setVisible(false);
        upletPanel.setVisible(true);
    }

    @FXML
    private void expSuiviTempsYes(ActionEvent event
    ) {
        frequenceLabel.setVisible(true);
        a3Label.setVisible(true);
        frequenceTextField.setVisible(true);
        a3TextField.setVisible(true);
        nonRadio.setSelected(false);
    }

    @FXML
    private void expSuiviTempsNo(ActionEvent event
    ) {
        frequenceLabel.setVisible(false);
        a3Label.setVisible(false);
        frequenceTextField.setVisible(false);
        a3TextField.setVisible(false);
        ouiRadio.setSelected(false);
    }

 @FXML
    private void ajoutExperience(ActionEvent event) {   
            String libelle = libelleTextField.getText(); 
            LocalDate date = dateDemandeDate.getValue();
            System.err.println("Selected date: " + date);
            String frequence = frequenceTextField.getText();
            String a3 = a3TextField.getText(); 
            String a1 = a1TextField.getText();
            int a1OK= Integer.parseInt(a1);
            String a2 = a2TextField.getText();
            int a2OK= Integer.parseInt(a2);
            String nb_slot = nbSlotTextField.getText();
            int nb_slotOK= Integer.parseInt(nb_slot);
            String duree =  dureeExpTextField.getText();
            int dureeOK= Integer.parseInt(duree);
            String nomEquipe = (String) equipeComboBox.getValue(); 
            System.out.println(nomEquipe);
            String typeExp = (String) typeExpComboBox.getValue(); 
        
        try {
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT EMAIL_EQUIPE FROM EQUIPE WHERE NOM_EQUIPE ='"+nomEquipe+"'");
            rs1.next(); 
            String email = rs1.getString(1);
            System.out.println(email);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try {
            if (a3 != null && frequence != null) {
                int a3OK= Integer.parseInt(a3);
                int frequenceOK= Integer.parseInt(frequence);
                System.out.println("insertion1");
                Statement stmt1 = con.createStatement();
                ResultSet rs1 = stmt1.executeQuery("INSERT INTO EXPERIENCE (LIBELLE_EXP, EMAIL_EQUIPE, DATE_DEMANDE, TYPE_EXP, A1, A2, NB_SLOT, DUREE_EXP, A3, STATUT_EXP, FREQUENCE) values (libelle, email, date, typeExp, a1OK, a2OK, nb_slotOK, dureeOK, a3OK, 'à faire', frequenceOK)"); 
            }
            else {
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("INSERT INTO EXPERIENCE (LIBELLE_EXP, EMAIL_EQUIPE, DATE_DEMANDE, TYPE_EXP, A1, A2, NB_SLOT, DUREE_EXP, STATUT_EXP) values (libelle, email, date, typeExp, a1OK, a2OK, nb_slotOK, dureeOK, 'à faire')"); 
                System.out.println("insertion2");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void searchExperience(ActionEvent event
    ) {
        //
    }

    @FXML
    private void sendResults(ActionEvent event
    ) {
        //
    }

    private void connectServer() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.254.3:1521:PFPBS", "GROUPE_73", "GROUPE_73");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Connection etablished.");
        connectServer();
    }
}
