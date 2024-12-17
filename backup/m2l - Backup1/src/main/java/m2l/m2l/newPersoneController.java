package m2l.m2l;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class newPersoneController implements Initializable {
    SQL_M2L bdd = new SQL_M2L();
    int id;
    String nom;
    String prenom;
    String adresse;
    Boolean error=false;
    public newPersoneController(int id, String nom, String prenom, String adresse) throws SQLException, ClassNotFoundException {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    @FXML
    AnchorPane ap;
    @FXML
    public Button btn_retour;
    @FXML
    public Button btn_ajouter;

    /*champ de saisie */
    @FXML
    public TextField txt_nom;
    @FXML
    public TextField txt_prenom;
    @FXML
    public TextField txt_adresse;

    public newPersoneController() throws SQLException, ClassNotFoundException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*--------------------------------------------------------------------------------------------------------------
        * ajout d'une Personne a la BDD
        --------------------------------------------------------------------------------------------------------------*/
        btn_ajouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /* recuperation des champ */
                String nom = txt_nom.getText();
                String prenom = txt_prenom.getText();
                String adresse = txt_adresse.getText();

                if(nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input error");
                    alert.setContentText("Veuillez remplir tous les champs");
                    alert.showAndWait();
                    return;
                }
                else{
                    try {
                        bdd.addPersonne(nom,prenom,adresse);
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setContentText("Erreur lors de la transmission des données à la base. Vérifier que la BDD est active et si les informations entrer sont en norme puis réessayez.");
                        alert.showAndWait();
                        error = true;
                        throw new RuntimeException(e);
                    }
                }

                if(error!=true){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("confirmation");
                    alert.setContentText("Confirmation de la commande");
                    alert.showAndWait();
                    return;
                } else {error=false;}

                txt_nom.clear();
                txt_prenom.clear();
                txt_adresse.clear();

            }
        });

        /*--------------------------------------------------------------------------------------------------------------
        * retour au menu principal
        --------------------------------------------------------------------------------------------------------------*/
        btn_retour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("menu.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) ap.getScene().getWindow();
                    stage.setScene(new Scene(root, 1200, 900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("application m2l");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

