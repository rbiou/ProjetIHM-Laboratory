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
    private ImageView homeIcon,expIcon,upletIcon,eyeIcon,addIcon;
    @FXML
    private AnchorPane topPanel,welcomePanel, navPanel, loginPanel, menuPanel;
    @FXML
    private Label welcomeLabel, loginErrorLabel;
    @FXML
    private TextField mailTextField, passwordTextField;
    //Variable d'environnement user : email A REMPLACER AVEC LE PRENOM
    private static String mailUser;
    
    @FXML
    private void connectUser(ActionEvent event) {
        if (mailTextField.getText().isEmpty() == false && passwordTextField.getText().isEmpty() == false){
            mailUser = mailTextField.getText();
            welcomeLabel.setText("Bienvenue "+mailUser);
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
        homeIcon.setImage(new Image(getClass().getResource("home_black.png").toExternalForm()));
        expIcon.setImage(new Image(getClass().getResource("flask_white.png").toExternalForm()));
    }
    
    @FXML
    private void setHomePanel(MouseEvent event) {
        topPanel.setVisible(false);
        welcomePanel.setVisible(true);
        menuPanel.setVisible(true);
        homeIcon.setImage(new Image(getClass().getResource("home_white.png").toExternalForm()));
        expIcon.setImage(new Image(getClass().getResource("flask_black.png").toExternalForm()));
    }
           
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
