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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MajPersonneController implements Initializable {
    SQL_M2L bdd = new SQL_M2L();
    private personne target;


    @FXML
    public AnchorPane ap;
    @FXML
    public Button btn_retour;
    @FXML
    public Button btn_maj;

    /*champ de saisie */
    @FXML
    public TextField txt_nom;
    @FXML
    public TextField txt_prenom;
    @FXML
    public TextField txt_adresse;

    public MajPersonneController(personne target) throws SQLException, ClassNotFoundException {
        this.target = target;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt_nom.setText(target.getNOMPERSONNE());
        txt_prenom.setText(target.getPRENOMPERSONNE());
        txt_adresse.setText(target.getEMAIL());




        /*--------------------------------------------------------------------------------------------------------------
        * mise a jour d'une Personne de la BDD
        --------------------------------------------------------------------------------------------------------------*/
        btn_maj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               if (seePersonneController.Select) {
                   /* recuperation des champ */
                   System.out.println("true");
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
                           bdd.majPersonne(target.getIDPERSONNE(),nom,prenom,adresse);
                       } catch (SQLException e) {
                           throw new RuntimeException(e);
                       }
                   }
               }
               else {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Input error");
                   alert.setContentText("Veuillez sélectionner un champs à modifier");
                   alert.showAndWait();
                   return;
               }
                FXMLLoader loader = new FXMLLoader(seePersonneController.class.getResource("personne_see.fxml"));
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

        /*--------------------------------------------------------------------------------------------------------------
        * retour au menu principal
        --------------------------------------------------------------------------------------------------------------*/
        btn_retour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(seePersonneController.class.getResource("personne_see.fxml"));
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

    public personne getTarget() {
        return target;
    }

    public void setTarget(personne target) {
        this.target = target;
    }
}

