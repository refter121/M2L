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

public class seeCompController implements Initializable {
    SQL_M2L bdd = new SQL_M2L();


    @FXML
    public AnchorPane ap;
    @FXML
    public Button btn_retour,btn_supr,btn_maj;

    /* tableau */
    @FXML
    public TableView<competition> tv_competition_see;
    @FXML public TableColumn<competition, String> tc_competition_see_idcomp;
    @FXML public TableColumn<competition, String> tc_competition_see_nom;
    @FXML public TableColumn<competition, String> tc_competition_see_date;
    @FXML public TableColumn<competition, Boolean> tc_competition_see_enequipe;

    public ObservableList<competition> obs = FXCollections.observableArrayList();
    competition target;

    public seeCompController() throws SQLException, ClassNotFoundException {}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*--------------------------------------------------------------------------------------------------------------
        * initialisation du tableau
        --------------------------------------------------------------------------------------------------------------*/
        try {
            obs.setAll(bdd.listComp());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(obs);

        tc_competition_see_idcomp.setCellValueFactory(new PropertyValueFactory<>("IDCOMPETITION"));
        tc_competition_see_nom.setCellValueFactory(new PropertyValueFactory<>("INTITULECOMP"));
        tc_competition_see_date.setCellValueFactory(new PropertyValueFactory<>("DATECOMP"));
        tc_competition_see_enequipe.setCellValueFactory(new PropertyValueFactory<>("ENEQUIPE"));

        tv_competition_see.setItems(obs);

        //selecteur
        btn_maj.setDisable(true);
        btn_supr.setDisable(true);
        tv_competition_see.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn_maj.setDisable(false);
                btn_supr.setDisable(false);
                target = newSelection;

                int idselect = newSelection.getIDCOMPETITION();
                String NomEquipeSelect = newSelection.getINTITULECOMP();
                System.out.println("Equipe sélectionnée: "+idselect+" "+NomEquipeSelect);

            }

        });

        /*--------------------------------------------------------------------------------------------------------------
        * supression de la donnée
        * TODO: message de confirmation / d'avertissement
        --------------------------------------------------------------------------------------------------------------*/
        btn_supr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                warningmsg wm = new warningmsg(target.getINTITULECOMP());
                if(wm.isValue()){
                    try {
                        System.out.println("supression de " + target.getINTITULECOMP());
                        bdd.DeleteCompetition(target.getIDCOMPETITION());
                        obs.remove(target);



                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
        /*--------------------------------------------------------------------------------------------------------------
        * modification
        --------------------------------------------------------------------------------------------------------------*/
        btn_maj.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("competition_update.fxml"));
                try {
                    loader.setController(new MajCompController(target)); /* creation du controleur !! Le controleur n'est pas def dans FXML!! */

                    Parent root = loader.load();

                    Stage stage = (Stage) ap.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("Maj Comp");
                    stage.show();

                } catch (SQLException | ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        /*--------------------------------------------------------------------------------------------------------------
        * retour au menu principal
        --------------------------------------------------------------------------------------------------------------*/
        HelloController helloController = new HelloController();
        helloController.allez_a_page(btn_retour, "menu.fxml", "application m2l");

    }
}
