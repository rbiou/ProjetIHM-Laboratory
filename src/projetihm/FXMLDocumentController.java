<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ComboBox?>
<?import javafx.scene.control.DatePicker?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.RadioButton?>
<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.control.TableColumn?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.shape.Line?>
<?import javafx.scene.shape.Rectangle?>
<?import javafx.scene.text.Font?>

<AnchorPane id="AnchorPane" prefHeight="500.0" prefWidth="700.0" xmlns="http://javafx.com/javafx/8.0.191" xmlns:fx="http://javafx.com/fxml/1" fx:controller="projetihm.FXMLDocumentController">
    <children>
        <AnchorPane fx:id="navPanel" prefHeight="500.0" prefWidth="700.0" visible="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
            <children>
                <AnchorPane prefHeight="500.0" prefWidth="110.0" style="-fx-background-color: #88d9e6;" AnchorPane.bottomAnchor="0.0" AnchorPane.topAnchor="0.0">
                    <children>
                        <ImageView fx:id="homeIcon" fitHeight="60.0" fitWidth="56.0" layoutX="27.0" layoutY="36.0" onMouseClicked="#setHomePanel" pickOnBounds="true" preserveRatio="true">
                            <image>
                                <Image url="@home_white.png" />
                            </image>
                        </ImageView>
                        <ImageView fx:id="expIcon" fitHeight="60.0" fitWidth="56.0" layoutX="27.0" layoutY="102.0" onMouseClicked="#setExperiencePanel" pickOnBounds="true" preserveRatio="true">
                            <image>
                                <Image url="@flask_black.png" />
                            </image>
                        </ImageView>
                    </children>
                </AnchorPane>
                <AnchorPane fx:id="topPanel" prefHeight="104.0" prefWidth="590.0" style="-fx-background-color: #bdbdbd;" visible="false" AnchorPane.leftAnchor="110.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                    <children>
                        <ImageView fitHeight="67.0" fitWidth="66.0" layoutX="23.0" layoutY="19.0" pickOnBounds="true" preserveRatio="true">
                            <image>
                                <Image url="@flask_black.png" />
                            </image>
                        </ImageView>
                        <Label layoutX="95.0" layoutY="37.0" prefHeight="31.0" prefWidth="192.0" text="Experience">
                            <font>
                                <Font name="Bebas" size="30.0" />
                            </font>
                        </Label>
                        <ImageView fx:id="upletIcon" fitHeight="40.0" fitWidth="66.0" layoutX="529.3333333333334" layoutY="54.0" onMouseClicked="#setUpletPanel" pickOnBounds="true" preserveRatio="true" AnchorPane.rightAnchor="22.0">
                            <image>
                                <Image url="@circle_black.png" />
                            </image>
                        </ImageView>
                        <ImageView fx:id="eyeIcon" fitHeight="40.0" fitWidth="49.0" layoutX="449.33333333333337" layoutY="54.0" onMouseClicked="#setViewPanel" pickOnBounds="true" preserveRatio="true" AnchorPane.rightAnchor="102.0">
                            <image>
                                <Image url="@eye_white.png" />
                            </image>
                        </ImageView>
                        <ImageView fx:id="addIcon" fitHeight="40.0" fitWidth="66.0" layoutX="489.33333333333337" layoutY="54.0" onMouseClicked="#setInsertPanel" pickOnBounds="true" preserveRatio="true" AnchorPane.rightAnchor="62.0">
                            <image>
                                <Image url="@add_black.png" />
                            </image>
                        </ImageView>
                  <ImageView fx:id="plaqueIcon" fitHeight="40.0" fitWidth="49.0" layoutX="578.0" layoutY="54.0" onMouseClicked="#setPlaquePanel" pickOnBounds="true" preserveRatio="true" AnchorPane.rightAnchor="142.0">
                     <image>
                        <Image url="@square_black.png" />
                     </image>
                  </ImageView>
                    </children>
                </AnchorPane>
                <AnchorPane fx:id="welcomePanel" layoutX="110.0" prefHeight="104.0" prefWidth="590.0" AnchorPane.leftAnchor="110.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                    <children>
                        <Label fx:id="welcomeLabel" layoutX="305.0" layoutY="29.0" prefHeight="27.0" prefWidth="200.0" text="Bienvenue XXXX" AnchorPane.rightAnchor="116.0">
                            <font>
                                <Font size="18.0" />
                            </font>
                        </Label>
                  <Label fx:id="fonctionLabel" layoutX="355.0" layoutY="52.0" prefHeight="27.0" prefWidth="200.0" text="Fonction : XXXX" AnchorPane.rightAnchor="116.0">
                     <font>
                        <Font size="18.0" />
                     </font>
                  </Label>
                        <ImageView fx:id="powerIcon" fitHeight="47.0" fitWidth="50.0" layoutX="521.0" layoutY="29.0" onMouseClicked="#logOff" pickOnBounds="true" preserveRatio="true" AnchorPane.rightAnchor="20.0">
                            <image>
                                <Image url="@power_black.png" />
                            </image>
                        </ImageView>
                    </children>
                </AnchorPane>
                <AnchorPane fx:id="menuPanel" layoutX="110.0" layoutY="104.0" prefHeight="398.0" prefWidth="590.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="110.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="104.0">
                    <children>
                        <Rectangle fx:id="expMenuButton" arcHeight="5.0" arcWidth="5.0" fill="#bdbdbd" height="60.0" layoutX="80.0" layoutY="70.0" onMouseClicked="#setExperiencePanel" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" width="190.0" />
                        <ImageView fx:id="expMenuIcon" fitHeight="47.0" fitWidth="47.0" layoutX="80.0" layoutY="77.0" onMouseClicked="#setExperiencePanel" pickOnBounds="true" preserveRatio="true">
                            <image>
                                <Image url="@flask_black.png" />
                            </image>
                        </ImageView>
                        <Label fx:id="expMenuLabel" layoutX="140.0" layoutY="90.0" onMouseClicked="#setExperiencePanel" prefHeight="20.0" prefWidth="110.0" text="EXPERIENCE">
                            <font>
                                <Font name="Bebas" size="20.0" />
                            </font>
                        </Label>
                    </children>
                </AnchorPane>
                <AnchorPane fx:id="upletPanel" layoutX="110.0" layoutY="102.0" prefHeight="401.0" prefWidth="621.0" visible="false">
                    <children>
                        <Label fx:id="titreLabel" layoutX="9.0" layoutY="15.0" text="Ajout d'un réplicat">
                            <font>
                                <Font name="System Bold" size="18.0" />
                            </font>
                        </Label>
                        <Label fx:id="expSelectedLabel" layoutX="208.0" layoutY="29.0" text="Selectionner une expérience* :">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Line fx:id="lineTitre" endX="72.99996948242188" layoutX="100.0" layoutY="46.0" startX="-100.0" />
                        <Rectangle fx:id="rectangleSolution" arcHeight="5.0" arcWidth="5.0" fill="#dad9d9" height="173.0" layoutX="23.0" layoutY="88.0" stroke="TRANSPARENT" strokeType="INSIDE" width="575.0" />
                        <Label fx:id="plaqueLabel1" layoutX="246.0" layoutY="92.0" prefHeight="21.0" prefWidth="167.0" text="Solution du Réplicat" textFill="#479aa7">
                            <font>
                                <Font name="System Bold" size="15.0" />
                            </font>
                        </Label>
                        <TextField fx:id="qteAgentTextField" layoutX="288.0" layoutY="153.0" promptText="Saisir le numéro de la plaque" />
                        <TextField fx:id="qteCelluleTextField" layoutX="289.0" layoutY="224.0" promptText="Saisir le numéro de la plaque" />
                        <Label fx:id="qteAgentLabel" layoutX="155.0" layoutY="154.0" text="Quantité d'agent* :">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="qteCelluleLabel" layoutX="140.0" layoutY="227.0" text="Quantité de cellules* :">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="typeCelluleLabel" layoutX="162.0" layoutY="190.0" text="Type de cellules* :">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="nomAgentLabel" layoutX="162.0" layoutY="122.0" text="Nom de l'agent* :">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Button fx:id="ajoutUpletButton" layoutX="289.0" layoutY="283.0" mnemonicParsing="false" onAction="#connectUser" style="-fx-background-color: #88d9e6;" text="Ajouter" />
                  <ComboBox fx:id="nomAgentComboBox" layoutX="288.0" layoutY="120.0" prefWidth="150.0" promptText="Sélectionner le nom de l'agent" />
                  <ComboBox fx:id="typeCelluleComboBox" layoutX="288.0" layoutY="188.0" prefWidth="150.0" promptText="Sélectionner le type de cellules" />
                  <ComboBox fx:id="expSelectedComboBox" layoutX="407.0" layoutY="28.0" prefWidth="150.0" promptText="Sélectionner une expérience" />
                    </children>
                </AnchorPane>

                <AnchorPane fx:id="viewPanel" layoutX="110.0" layoutY="104.0" prefHeight="398.0" prefWidth="590.0" visible="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="110.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="104.0">
                    <children>
                        <TextField layoutX="33.0" layoutY="35.0" promptText="Rechercher par statut..." />
                        <TextField layoutX="209.0" layoutY="35.0" promptText="Rechercher par équipe..." />
                        <DatePicker layoutX="380.0" layoutY="35.0" promptText="Rechercher par date..." />
                        <Button fx:id="searchButton" layoutX="246.0" layoutY="75.0" mnemonicParsing="false" onAction="#searchExperience" style="-fx-background-color: #88d9e6;" text="Rechercher" />
                        <TableView fx:id="viewTableView" layoutX="33.0" layoutY="127.0" prefHeight="257.0" prefWidth="522.0" AnchorPane.bottomAnchor="10.0" AnchorPane.leftAnchor="30.0" AnchorPane.rightAnchor="30.0" AnchorPane.topAnchor="130.0">
                            <columnResizePolicy>
                                <TableView fx:constant="CONSTRAINED_RESIZE_POLICY" />
                            </columnResizePolicy>
                        </TableView>
                    </children>
                </AnchorPane>
                <AnchorPane fx:id="insertPanel" layoutX="110.0" layoutY="104.0" prefHeight="401.0" prefWidth="621.0" visible="false">
                    <children>
                        <Rectangle arcHeight="5.0" arcWidth="5.0" fill="#dad9d9" height="246.0" layoutX="8.0" layoutY="73.0" stroke="TRANSPARENT" strokeType="INSIDE" width="609.0" />
                        <Label fx:id="libelleLabel" layoutX="85.0" layoutY="92.0" text="Libellé* : ">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="dateDemandeLabel" layoutX="14.0" layoutY="159.0" text="Date de demande* : ">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="typeExpLabel" layoutX="13.0" layoutY="197.0" text="Type d'expérience* : ">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="a1Label" layoutX="109.0" layoutY="233.0" text="a1* : ">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="a2Label" layoutX="109.0" layoutY="265.0" text="a2* : ">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="nbmSlotPanel" layoutX="297.0" layoutY="92.0" text="Nombre de puits/réplicat* :  ">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="dureeExpLabel" layoutX="318.0" layoutY="129.0" text="Durée de l'expérience* : ">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="suiviTempsLabel" layoutX="314.0" layoutY="164.0" text="Expérience suivie dans le temps ?*">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="frequenceLabel" layoutX="384.0" layoutY="236.0" text="Fréquence* :" visible="false">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <Label fx:id="a3Label" layoutX="433.0" layoutY="268.0" text="a3* :" visible="false">
                            <font>
                                <Font size="14.0" />
                            </font>
                        </Label>
                        <RadioButton fx:id="ouiRadio" layoutX="541.0" layoutY="165.0" mnemonicParsing="false" onAction="#expSuiviTempsYes" text="Oui" />
                        <RadioButton fx:id="nonRadio" layoutX="541.0" layoutY="195.0" mnemonicParsing="false" onAction="#expSuiviTempsNo" text="Non" />
                        <TextField fx:id="libelleTextField" layoutX="146.0" layoutY="90.0" promptText="Saisir le libellé">
                            <font>
                                <Font size="11.0" />
                            </font>
                        </TextField>
                        <TextField fx:id="a1TextField" layoutX="147.0" layoutY="231.0" promptText="Saisir la valeur de a1">
                            <font>
                                <Font size="11.0" />
                            </font>
                        </TextField>
                        <TextField fx:id="a2TextField" layoutX="147.0" layoutY="263.0" promptText="Saisir la valeur de a2">
                            <font>
                                <Font name="System Italic" size="11.0" />
                            </font>
                        </TextField>
                        <TextField fx:id="a3TextField" layoutX="475.0" layoutY="266.0" promptText="Saisir la valeur de a3" visible="false">
                            <font>
                                <Font name="System Italic" size="11.0" />
                            </font>
                        </TextField>
                        <DatePicker fx:id="dateDemandeDate" layoutX="147.0" layoutY="156.0" prefHeight="25.0" prefWidth="137.0" />
                        <TextField fx:id="nbSlotTextField" layoutX="475.0" layoutY="89.0" promptText="Saisir le nombre de slots par uplet">
                            <font>
                                <Font name="System Italic" size="11.0" />
                            </font>
                        </TextField>
                        <TextField fx:id="dureeExpTextField" layoutX="476.0" layoutY="127.0" promptText="Saisir la durée de l'expérience">
                            <font>
                                <Font name="System Italic" size="11.0" />
                            </font>
                        </TextField>
                        <TextField fx:id="frequenceTextField" layoutX="475.0" layoutY="234.0" promptText="Saisir la fréquence de l'expérience" visible="false">
                            <font>
                                <Font name="System Italic" size="11.0" />
                            </font>
                        </TextField>
                        <Button fx:id="AjoutExpButton" layoutX="287.0" layoutY="334.0" mnemonicParsing="false" onAction="#ajoutExperience" style="-fx-background-color: #88d9e6;" text="Ajouter" />
                  <Label fx:id="nomEquipeLabel" layoutX="18.0" layoutY="123.0" text="Nom de l'équipe* : ">
                     <font>
                        <Font size="14.0" />
                     </font>
                  </Label>
                  <ComboBox fx:id="equipeComboBox" layoutX="147.0" layoutY="121.0" prefHeight="25.0" prefWidth="137.0" promptText="Selectionner l'équipe" />
                  <ComboBox fx:id="typeExpComboBox" layoutX="147.0" layoutY="191.0" prefHeight="25.0" prefWidth="137.0" promptText="Selectionner le type de l'expérience" />
                  <Line endX="107.00003051757812" layoutX="101.0" layoutY="51.0" startX="-100.0" />
                  <Label layoutX="15.0" layoutY="19.0" text="Ajouter une expérience">
                     <font>
                        <Font name="System Bold" size="18.0" />
                     </font>
                  </Label>
                    </children>
                </AnchorPane>
                <ScrollPane fx:id="viewdetailsPanel" layoutX="110.0" layoutY="104.0" prefHeight="396.0" prefWidth="621.0" visible="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="110.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="104.0">
                    <content>
                        <AnchorPane prefHeight="900.0" prefWidth="621.0">
                            <children>
                                <Label layoutX="125.0" layoutY="40.0" text="Libellé :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="421.0" layoutY="40.0" text="Type :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="26.0" layoutY="70.0" text="Equipe commanditaire :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="340.0" layoutY="70.0" text="Date de demande :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="127.0" layoutY="100.0" text="Statut :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="362.0" layoutY="100.0" text="Date de début :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="149.0" layoutY="130.0" text="a1 :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="149.0" layoutY="160.0" text="a2 :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="a3viewLabel" layoutX="436.0" layoutY="220.0" text="a3 :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="311.0" layoutY="130.0" text="Nombre de slots/uplet :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="317.0" layoutY="160.0" text="Durée de l'expérience :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="332.0" layoutY="190.0" text="Suivi dans le temps :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="frequenceviewLabel" layoutX="100.0" layoutY="220.0" text="Fréquence :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="35.0" layoutY="190.0" text="Date de transmission :">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datalibelleLabel" layoutX="189.0" layoutY="40.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="dataequipeLabel" layoutX="189.0" layoutY="70.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datastatutLabel" layoutX="189.0" layoutY="100.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="dataa1Label" layoutX="189.0" layoutY="130.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="dataa2Label" layoutX="189.0" layoutY="160.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datadatetransmissionLabel" layoutX="189.0" layoutY="190.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datafrequenceLabel" layoutX="189.0" layoutY="220.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datatypeLabel" layoutX="475.0" layoutY="40.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datadatedemandeLabel" layoutX="475.0" layoutY="70.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datadatedebutLabel" layoutX="475.0" layoutY="100.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datanbslotsLabel" layoutX="475.0" layoutY="130.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datadureeLabel" layoutX="475.0" layoutY="160.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="datasuiviLabel" layoutX="475.0" layoutY="190.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label fx:id="dataa3Label" layoutX="475.0" layoutY="220.0" text="XXX">
                                    <font>
                                        <Font size="14.0" />
                                    </font>
                                </Label>
                                <Label layoutX="25.0" layoutY="271.0" text="Réplicat(s) de l'expérience">
                                    <font>
                                        <Font name="System Bold" size="18.0" />
                                    </font>
                                </Label>
                                <Line endX="149.0" layoutX="100.0" layoutY="301.0" startX="-100.0" />
                                <TableView layoutX="60.0" layoutY="319.0" prefHeight="205.0" prefWidth="506.0" AnchorPane.leftAnchor="60.0" AnchorPane.rightAnchor="60.0">
                                    <columns>
                                        <TableColumn prefWidth="75.0" text="Identifiant du réplicat" />
                                        <TableColumn prefWidth="75.0" text="Type de cellules" />
                                        <TableColumn prefWidth="75.0" text="Type d'agent" />
                                        <TableColumn prefWidth="75.0" text="Quantité d'agent" />
                                        <TableColumn prefWidth="75.0" text="Quantité de cellules" />
                                        <TableColumn prefWidth="75.0" text="Renouvelé" />
                                        <TableColumn prefWidth="75.0" text="Urgent" />
                                        <TableColumn prefWidth="75.0" text="Date d'échéance" />
                                        <TableColumn prefWidth="75.0" text="Statut" />
                                    </columns>
                                    <columnResizePolicy>
                                        <TableView fx:constant="CONSTRAINED_RESIZE_POLICY" />
                                    </columnResizePolicy>
                                </TableView>
                                <Label layoutX="27.0" layoutY="547.0" text="Résultats de l'expérience">
                                    <font>
                                        <Font name="System Bold" size="18.0" />
                                    </font>
                                </Label>
                                <Line endX="136.0" layoutX="102.0" layoutY="577.0" startX="-100.0" />
                                <TableView layoutX="58.0" layoutY="597.0" prefHeight="205.0" prefWidth="506.0">
                                    <columns>
                                        <TableColumn prefWidth="75.0" text="Identifiant du réplicat" />
                                        <TableColumn prefWidth="75.0" text="Rouge : Moyenne" />
                                        <TableColumn prefWidth="75.0" text="Rouge : Ecart-type" />
                                        <TableColumn prefWidth="75.0" text="Vert : moyenne" />
                                        <TableColumn prefWidth="75.0" text="Vert : écart-type" />
                                        <TableColumn prefWidth="75.0" text="Bleu : moyenne" />
                                        <TableColumn prefWidth="75.0" text="Bleu : écart-type" />
                                        <TableColumn prefWidth="75.0" text="Transparence : moyenne" />
                                        <TableColumn prefWidth="75.0" text="Transparence : écart-type" />
                                    </columns>
                                    <columnResizePolicy>
                                        <TableView fx:constant="CONSTRAINED_RESIZE_POLICY" />
                                    </columnResizePolicy>
                                </TableView>
                                <Button fx:id="resultsButton" layoutX="213.0" layoutY="827.0" mnemonicParsing="false" onAction="#sendResults" style="-fx-background-color: #88d9e6;" text="Envoyer les résultats au chercheur" />
                            </children>
                        </AnchorPane>
                    </content>
                </ScrollPane>
            <ScrollPane fx:id="plaqueScrollPane" layoutX="110.0" layoutY="104.0" prefHeight="401.0" prefWidth="621.0" visible="false">
               <content>
                  <AnchorPane fx:id="plaquePanel" prefHeight="659.0" prefWidth="621.0">
                     <children>
                        <Rectangle fx:id="rectangle11" arcHeight="5.0" arcWidth="5.0" fill="#dad9d9" height="117.0" layoutX="16.0" layoutY="62.0" stroke="TRANSPARENT" strokeType="INSIDE" width="575.0" />
                        <Rectangle fx:id="rectangle1" arcHeight="5.0" arcWidth="5.0" fill="#dad9d9" height="66.0" layoutX="16.0" layoutY="194.0" stroke="TRANSPARENT" strokeType="INSIDE" width="575.0" />
                        <Rectangle fx:id="rectangle2" arcHeight="5.0" arcWidth="5.0" fill="#dad9d9" height="92.0" layoutX="25.0" layoutY="242.0" stroke="TRANSPARENT" strokeType="INSIDE" visible="false" width="575.0" />
                        <Label fx:id="plaqueTitreLabel" layoutX="270.0" layoutY="195.0" text="Plaque" textFill="#479aa7">
                           <font>
                              <Font name="System Bold" size="15.0" />
                           </font>
                        </Label>
                        <Label fx:id="plaqueSelectedLabel" layoutX="150.0" layoutY="226.0" text="Plaque utilisée* :">
                           <font>
                              <Font size="14.0" />
                           </font>
                        </Label>
                        <Label fx:id="numPaqueLabel" layoutX="89.0" layoutY="262.0" text="N° plaque* :" visible="false">
                           <font>
                              <Font size="14.0" />
                           </font>
                        </Label>
                        <Label fx:id="nbPuitLabel" layoutX="345.0" layoutY="262.0" text="Nombre de puits* :" visible="false">
                           <font>
                              <Font size="14.0" />
                           </font>
                        </Label>
                        <TextField fx:id="numPlaqueTextField" layoutX="170.0" layoutY="259.0" promptText="Saisir le numéro de la plaque" visible="false" />
                        <RadioButton fx:id="radioButton96" layoutX="477.0" layoutY="263.0" mnemonicParsing="false" text="96" visible="false" />
                        <RadioButton fx:id="radioButton384" layoutX="530.0" layoutY="263.0" mnemonicParsing="false" text="384" visible="false" />
                        <Line endX="208.33334350585938" layoutX="101.0" layoutY="48.0" startX="-100.0" />
                        <Label fx:id="titreReplicatLabel" layoutX="20.0" layoutY="14.0" text="Choix de la plaque pour le réplicat">
                           <font>
                              <Font name="System Bold" size="18.0" />
                           </font>
                        </Label>
                        <ComboBox fx:id="choixPlaqueComboBox" layoutX="270.0" layoutY="224.0" prefHeight="25.0" prefWidth="137.0" promptText="Sélectionner une plaque" />
                        <ComboBox fx:id="choixReplicatComboBox" layoutX="303.0" layoutY="140.0" prefHeight="25.0" prefWidth="137.0" promptText="Sélectionner un réplicat" />
                        <Label fx:id="choixReplicatLabel" layoutX="176.0" layoutY="143.0" text="Choisir un réplicat : ">
                           <font>
                              <Font size="14.0" />
                           </font>
                        </Label>
                        <Button fx:id="AjoutPlaqueButton" layoutX="473.0" layoutY="295.0" mnemonicParsing="false" onAction="#connectUser" style="-fx-background-color: #88d9e6;" text="Ajouter la plaque" visible="false" />
                        <ComboBox fx:id="choixExperienceComboBox" layoutX="304.0" layoutY="104.0" prefHeight="25.0" prefWidth="137.0" promptText="Sélectionner un réplicat" />
                        <Label fx:id="choixExperienceLabel" layoutX="150.0" layoutY="106.0" text="Choisir une expérience : ">
                           <font>
                              <Font size="14.0" />
                           </font>
                        </Label>
                        <Label fx:id="titreSelectionLabel" layoutX="173.0" layoutY="71.0" text="Selection de l'expérience et de l'uplet" textFill="#479aa7">
                           <font>
                              <Font name="System Bold" size="15.0" />
                           </font>
                        </Label>
                        <GridPane fx:id="rectPlaque" layoutX="18.0" layoutY="347.0" prefHeight="282.0" prefWidth="575.0">
                           <columnConstraints>
                              <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" />
                           </columnConstraints>
                           <rowConstraints>
                              <RowConstraints minHeight="10.0" vgrow="SOMETIMES" />
                           </rowConstraints>
                        </GridPane>
                     </children>
                  </AnchorPane>
               </content>
            </ScrollPane>
            </children>
        </AnchorPane>
        <AnchorPane fx:id="loginPanel" layoutX="1.0" prefHeight="500.0" prefWidth="700.0" style="-fx-alignment: CENTER;" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
            <children>
                <Label layoutX="237.0" layoutY="130.0" prefHeight="51.0" prefWidth="226.0" scaleZ="0.0" text="CONNEXION" textAlignment="CENTER" AnchorPane.topAnchor="130.0">
                    <font>
                        <Font name="Bebas" size="50.0" />
                    </font>
                </Label>
                <TextField fx:id="mailTextField" layoutX="237.0" layoutY="208.0" prefHeight="25.0" prefWidth="226.0" promptText="Saisir votre email..." AnchorPane.topAnchor="210.0" />
                <Button fx:id="connexionButton" layoutX="427.0" layoutY="277.0" mnemonicParsing="false" onAction="#connectUser" style="-fx-background-color: #88d9e6;" text="Connexion" AnchorPane.topAnchor="300.0" />
                <Label fx:id="loginErrorLabel" layoutX="237.0" layoutY="272.0" text="error" textFill="RED" visible="false" AnchorPane.topAnchor="270.0" />
                <PasswordField fx:id="passwordTextField" layoutX="237.0" layoutY="240.0" prefHeight="25.0" prefWidth="226.0" promptText="Saisir votre mot de passe..." />
            </children>
        </AnchorPane>
    </children>
</AnchorPane>
