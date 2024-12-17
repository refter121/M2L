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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MajEquipeController implements Initializable {
    /*  */
    SQL_M2L bdd = new SQL_M2L();
    ArrayList<personne> listeJoueur = new ArrayList<>();
    public ArrayList<Integer> listeid = new ArrayList<>();

    equipe target;
    personne subtarget;

    /* FXML */
    @FXML
    public AnchorPane ap;
    @FXML
    public Button btn_retour;
    @FXML
    public Button btn_maj;
    @FXML
    public Button btn_addjoueur;
    @FXML
    public Button btn_remove;
    @FXML
    public TextField txt_nomequipe;
    @FXML
    public TextField txt_idjoueur;


    /* tableau */
    @FXML
    public TableView<personne> tab_joueur;
    @FXML public TableColumn<personne, Integer> tc_idpersonne;
    @FXML public TableColumn<personne,String> tc_personnenom;
    @FXML public TableColumn<personne,String> tc_personneprenom;
    @FXML public TableColumn<personne,String> tc_email;

    public ObservableList<personne> obs = FXCollections.observableArrayList();

    public MajEquipeController(equipe target) throws SQLException, ClassNotFoundException {
        this.target = target;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*--------------------------------------------------------------------------------------------------------------
        * ajout d'un membre a l'equipe
        --------------------------------------------------------------------------------------------------------------*/
        System.out.println(target.getIDEQUIPE());

        txt_nomequipe.setText(target.getNOM());

        for (int i = 0; i < target.getList().size(); i++) {
            listeid.add(target.getList().get(i).getIDPERSONNE());
        }

        obs.addAll(target.getList()); /* affichage du tableau + controleur*/
        tc_idpersonne.setCellValueFactory(new PropertyValueFactory<>("IDPERSONNE"));
        tc_personnenom.setCellValueFactory(new PropertyValueFactory<>("NOMPERSONNE"));
        tc_personneprenom.setCellValueFactory(new PropertyValueFactory<>("PRENOMPERSONNE"));
        tc_email.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
        tab_joueur.setItems(obs);

        btn_remove.setDisable(true);
        tab_joueur.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn_remove.setDisable(false);
                subtarget = newSelection;

            }
        });
        /*--------------------------------------------------------------------------------------------------------------
        * supression d'un joueur de la liste
        --------------------------------------------------------------------------------------------------------------*/
        btn_remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                obs.remove(subtarget);
                System.out.println(listeid);
                listeid.clear();
                for (int i = 0; i < obs.size(); i++) {
                    listeid.add(obs.get(i).getIDPERSONNE());
                }
                System.out.println(listeid);

            }
        });

        /*--------------------------------------------------------------------------------------------------------------
        * ajout de joueur par selection d'un ID
        --------------------------------------------------------------------------------------------------------------*/
        btn_addjoueur.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String id = txt_idjoueur.getText();
                    int idint = Integer.parseInt(txt_idjoueur.getText());

                    if(id.isEmpty() || !bdd.perrsoneid(idint) || listeid.contains(idint)){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Input error");
                            alert.setContentText("Veuillez rensegner une ID de joueur ou assurez vous que soit valide");
                            alert.showAndWait();

                    }else{
                        listeid.add(idint);
                        System.out.println(idint);
                        System.out.println(listeid);
                        obs.addAll(bdd.ceateObjetPersonne(idint));

                        tc_idpersonne.setCellValueFactory(new PropertyValueFactory<>("IDPERSONNE"));
                        tc_personnenom.setCellValueFactory(new PropertyValueFactory<>("NOMPERSONNE"));
                        tc_personneprenom.setCellValueFactory(new PropertyValueFactory<>("PRENOMPERSONNE"));
                        tc_email.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));

                        tab_joueur.setItems(obs);

                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        /*--------------------------------------------------------------------------------------------------------------
        * modification d'une Equipe a la BDD
        --------------------------------------------------------------------------------------------------------------*/
        btn_maj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nom = txt_nomequipe.getText();

                System.out.println(nom);
                System.out.println(listeid);

                if(nom.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input error");
                    alert.setContentText("saisissez un nom et au moin un joueuer");
                    alert.showAndWait();

                }else{
                    try {
                        bdd.majEquipe(target.getIDEQUIPE(),nom,listeid);
                        FXMLLoader loader = new FXMLLoader(seeEquipeController.class.getResource("equipe_see.fxml"));
                        try {
                            Parent root = loader.load();
                            Stage stage = (Stage) ap.getScene().getWindow();
                            stage.setScene(new Scene(root, 1200, 900));
                            stage.setMaxHeight(900);
                            stage.setMaxWidth(1200);
                            stage.setTitle("see personne");
                            stage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        /*--------------------------------------------------------------------------------------------------------------
        * retour au menu precedent
        --------------------------------------------------------------------------------------------------------------*/
        btn_retour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(seeEquipeController.class.getResource("equipe_see.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) ap.getScene().getWindow();
                    stage.setScene(new Scene(root, 1200, 900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("see personne");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
