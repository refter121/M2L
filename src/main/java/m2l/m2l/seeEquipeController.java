package m2l.m2l;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class seeEquipeController implements Initializable {
    SQL_M2L bdd = new SQL_M2L();
    equipe target;

    @FXML
    public AnchorPane ap;
    @FXML
    public Button btn_retour;
    @FXML
    public  Button btn_maj;
    @FXML
    public  Button btn_supr;


    /*tableau*/
    @FXML
    public TableView<equipe> tv_equipe_see;
    @FXML public TableColumn<equipe, Integer>tc_equipe_see_id ;
    @FXML public TableColumn<equipe,String> tc_equipe_see_nom;

    public ObservableList<equipe> obs = FXCollections.observableArrayList();

    public seeEquipeController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            obs.setAll(bdd.listEquipe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(obs);

        tc_equipe_see_id.setCellValueFactory(new PropertyValueFactory<>("IDEQUIPE"));
        tc_equipe_see_nom.setCellValueFactory(new PropertyValueFactory<>("NOM"));
        tv_equipe_see.setItems(obs);

        btn_maj.setDisable(true);
        btn_supr.setDisable(true);
        tv_equipe_see.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn_maj.setDisable(false);
                btn_supr.setDisable(false);
                target = newSelection;

                int idselect = newSelection.getIDEQUIPE();
                String NomEquipeSelect = newSelection.getNOM();
                System.out.println("Equipe sélectionnée: "+idselect+" "+NomEquipeSelect);

            }

        });

        /*--------------------------------------------------------------------------------------------------------------
        * Mise a jour des données
        --------------------------------------------------------------------------------------------------------------*/
        HelloController helloController = new HelloController();
        helloController.allez_a_page(btn_maj, "equipe_update.fxml", "Maj équipe");

        btn_maj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("equipe_update.fxml"));
                try {
                    loader.setController(new MajEquipeController(target)); /* creation du controleur !! Le controleur n'est pas def dans FXML!! */

                    Parent root = loader.load();

                    Stage stage = (Stage) ap.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("Maj équipe");
                    stage.show();

                } catch (SQLException | ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


/*--------------------------------------------------------------------------------------------------------------
        * supression de la donnée
        * TODO: message de confirmation / d'avertissement
        --------------------------------------------------------------------------------------------------------------*/
        btn_supr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                warningmsg wm = new warningmsg(target.getNOM());
                if(wm.isValue()){
                    try {
                        System.out.println("supression de " + target.getNOM());
                        bdd.DeleteEquipe(target.getIDEQUIPE());
                        obs.remove(target);



                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        btn_retour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("menu.fxml"));
                try{
                    Parent root = loader.load();
                    Stage stage = (Stage) ap.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("application m2l");
                    stage.show();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
