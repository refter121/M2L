package m2l.m2l;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class seePersonneController implements Initializable {
    SQL_M2L bdd = new SQL_M2L();
    public static Boolean Select = true;
    public String NomPersonneSelect = "";
    public String PrenomPersonneSelect = "";
    public String MailPersonneSelect = "";

    personne target;

    @FXML
    public AnchorPane ap;
    @FXML
    public Button btn_retour;
    @FXML
    public Button btn_maj;
    @FXML
    public Button btn_supr;
    @FXML
    public Label lab_title;

    /* tableau */
    @FXML
    public TableView<personne> tab_personne;
    @FXML public TableColumn<personne, Integer> tc_personne_id;
    @FXML public TableColumn<personne,String> tc_personne_nom;
    @FXML public TableColumn<personne,String> tc_personne_pre;
    @FXML public TableColumn<personne,String> tc_personne_mail;

    public ObservableList<personne> obs = FXCollections.observableArrayList();


    public seePersonneController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*--------------------------------------------------------------------------------------------------------------
        * affichage dynamique du tableau
        --------------------------------------------------------------------------------------------------------------*/
        try {
            obs.setAll(bdd.listPersonne());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(obs);


        tc_personne_id.setCellValueFactory(new PropertyValueFactory<>("IDPERSONNE"));
        tc_personne_nom.setCellValueFactory(new PropertyValueFactory<>("NOMPERSONNE"));
        tc_personne_pre.setCellValueFactory(new PropertyValueFactory<>("PRENOMPERSONNE"));
        tc_personne_mail.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));

        tab_personne.setItems(obs);

        btn_supr.setDisable(true);
        btn_maj.setDisable(true);
        tab_personne.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn_maj.setDisable(false);
                btn_supr.setDisable(false);
                Boolean Select = true;
                int idselect = newSelection.getIDPERSONNE();
                String NomPersonneSelect = newSelection.getNOMPERSONNE();
                String PrenomPersonneSelect = newSelection.getPRENOMPERSONNE();
                String MailPersonneSelect = newSelection.getEMAIL();
                System.out.println("Personne sélectionnée: "+idselect+" "+NomPersonneSelect+" "+PrenomPersonneSelect+" "+MailPersonneSelect );

                target = newSelection; /*creation d'un objet a partie de la selection*/
            }
        });
        /*--------------------------------------------------------------------------------------------------------------
        * supression de la donnée
        * TODO: message de confirmation / d'avertissement
        --------------------------------------------------------------------------------------------------------------*/
        btn_supr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                warningmsg wm = new warningmsg(target.getNOMPERSONNE()+" "+target.getPRENOMPERSONNE());
                if(wm.isValue()){
                    try {
                        System.out.println("supression de " + target.getNOMPERSONNE());
                        bdd.DeletePersonne(target.getIDPERSONNE());
                        obs.remove(target);



                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        /*--------------------------------------------------------------------------------------------------------------
        * mise a jour des données
        --------------------------------------------------------------------------------------------------------------*/
        HelloController helloController = new HelloController();
        helloController.allez_a_page(btn_maj, "personne_maj.fxml", "application m2l");
        btn_maj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("personne_maj.fxml"));
                try{
                    loader.setController(new MajPersonneController(target));/* creation du controleur !! Le controleur n'est pas def dans FXML!! */

                    Parent root = loader.load();

                    Stage stage = (Stage) ap.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("application m2l");
                    stage.show();

                }catch (IOException | SQLException | ClassNotFoundException e){
                    throw new RuntimeException(e);
                }
            }
        });

        /*--------------------------------------------------------------------------------------------------------------
        * retour au menu principal
        --------------------------------------------------------------------------------------------------------------*/
        helloController.allez_a_page(btn_retour, "menu.fxml", "application m2l");
    }
}
