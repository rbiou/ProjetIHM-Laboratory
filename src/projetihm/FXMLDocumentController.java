/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetihm;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private Label welcomeLabel, loginErrorLabel, frequenceLabel, a3Label, frequenceviewLabel, a3viewLabel, datalibelleLabel, dataequipeLabel, datastatutLabel, dataa1Label, dataa2Label, datadatetransmissionLabel, datafrequenceLabel, datatypeLabel, datadatedemandeLabel, datadatedebutLabel, datanbslotsLabel, datadureeLabel, dataa3Label;
    @FXML
    private TableView viewTable;
    @FXML
    private TextField mailTextField, passwordTextField, frequenceTextField, a3TextField;
    @FXML
    private RadioButton ouiRadio, nonRadio;
    //Variable d'environnement user : email A REMPLACER AVEC LE PRENOM
    private static String mailUser;

    @FXML
    private void connectUser(ActionEvent event) {
        if (mailTextField.getText().isEmpty() == false && passwordTextField.getText().isEmpty() == false) {
            mailUser = mailTextField.getText();
            welcomeLabel.setText("Bienvenue " + mailUser);
            welcomeLabel.setVisible(true);
            menuPanel.setVisible(true);
            loginPanel.setVisible(false);
            navPanel.setVisible(true);
            loginErrorLabel.setVisible(false);
            mailTextField.setText("");
            passwordTextField.setText("");
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
    }

    @FXML
    private void setUpletPanel(MouseEvent event) {
        eyeIcon.setImage(new Image(getClass().getResource("eye_black.png").toExternalForm()));
        addIcon.setImage(new Image(getClass().getResource("add_black.png").toExternalForm()));
        upletIcon.setImage(new Image(getClass().getResource("circle_white.png").toExternalForm()));
        viewPanel.setVisible(false);
        insertPanel.setVisible(false);
        upletPanel.setVisible(true);
    }

    @FXML
    private void expSuiviTempsYes(ActionEvent event) {
        frequenceLabel.setVisible(true);
        a3Label.setVisible(true);
        frequenceTextField.setVisible(true);
        a3TextField.setVisible(true);
        nonRadio.setSelected(false);
    }

    @FXML
    private void expSuiviTempsNo(ActionEvent event) {
        frequenceLabel.setVisible(false);
        a3Label.setVisible(false);
        frequenceTextField.setVisible(false);
        a3TextField.setVisible(false);
        ouiRadio.setSelected(false);
    }

    @FXML
    private void ajoutExperience(ActionEvent event) {
        //
    }
    
    @FXML
    private void searchExperience(ActionEvent event) {
        //
    }
    
    @FXML
    private void sendResults(ActionEvent event) {
        //
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}