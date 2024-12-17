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

public class newEquipeController implements Initializable {
    /*  */
    SQL_M2L bdd = new SQL_M2L();
    ArrayList<personne> listeJoueur = new ArrayList<>();
    public ArrayList<Integer> listeid = new ArrayList<>();


    /* FXML */
    @FXML
    public AnchorPane ap;
    @FXML
    public Button btn_retour;
    @FXML
    public Button btn_addequipe;
    @FXML
    public Button btn_addjoueur;
    @FXML
    public TextField txt_nomequipe;
    @FXML
    public TextField txt_idjoueur;
    @FXML
    public Button btn_retirer;

    /* tableau */
    private personne targetPL = null;
    private personne targetPR = null;
    /*tab Personne L*/
    @FXML
    public  TableView<personne> tab_personne_L;
    @FXML public TableColumn<personne, Integer> idpersonne_L;
    @FXML public TableColumn<personne, String> nompersonne_L;
    @FXML public  TableColumn<personne, String> prenompersonne_L;
    /*tab Personne R*/
    @FXML
    public  TableView<personne> tab_personne_R;
    @FXML public TableColumn<personne, Integer> idpersonne_R;
    @FXML public TableColumn<personne, String> nompersonne_R;
    @FXML public  TableColumn<personne, String> prenompersonne_R;

    public ObservableList<personne> obsPR = FXCollections.observableArrayList();

    public ObservableList<personne> obsPL = FXCollections.observableArrayList();

    public ObservableList<personne> obs = FXCollections.observableArrayList();

    public newEquipeController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*--------------------------------------------------------------------------------------------------------------
        * ajout d'un membre a l'equipe
        --------------------------------------------------------------------------------------------------------------*/
        //init tab R
        idpersonne_R.setCellValueFactory(new PropertyValueFactory<>("IDPERSONNE"));
        nompersonne_R.setCellValueFactory(new PropertyValueFactory<>("NOMPERSONNE"));
        prenompersonne_R.setCellValueFactory(new PropertyValueFactory<>("PRENOMPERSONNE"));
        tab_personne_R.setItems(obsPR);

        //init tab L
        try {
            obsPL.setAll(bdd.listPersonne());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(obsPL);


        idpersonne_L.setCellValueFactory(new PropertyValueFactory<>("IDPERSONNE"));
        nompersonne_L.setCellValueFactory(new PropertyValueFactory<>("NOMPERSONNE"));
        prenompersonne_L.setCellValueFactory(new PropertyValueFactory<>("PRENOMPERSONNE"));
        tab_personne_L.setItems(obsPL);

        //selecteur

        tab_personne_L.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int idselect = newSelection.getIDPERSONNE();
                String NomPersonneSelect = newSelection.getNOMPERSONNE();
                String PrenompersonneSelect = newSelection.getPRENOMPERSONNE();
                targetPL = newSelection;
                System.out.println("Personne l: "+idselect+" "+NomPersonneSelect +" "+PrenompersonneSelect);
            } else {targetPL = null;}
        });

        tab_personne_R.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int idselect = newSelection.getIDPERSONNE();
                String NomPersonneSelect = newSelection.getNOMPERSONNE();
                String PrenompersonneSelect = newSelection.getPRENOMPERSONNE();
                targetPR = newSelection;
                System.out.println("Personne r: "+idselect+" "+NomPersonneSelect +" "+PrenompersonneSelect);
            } else {targetPR = null;}
        });

        /*--------------------------------------------------------------------------------------------------------------
        * ajouter et retirer une equipe de la liste
        --------------------------------------------------------------------------------------------------------------*/

        btn_retirer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (targetPR != null) {
                    obsPL.add(targetPR); // ajoute la pr dans le tableau l
                    obsPR.remove(targetPR); // enleve la pr dans le tableau r
                }
            }
        });


        btn_addjoueur.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (targetPL != null) {
                    obsPR.add(targetPL); // ajoute la pl dans le tableau r
                    obsPL.remove(targetPL); // enleve la pl dans le tableau l
                }
            }
        });

        /*--------------------------------------------------------------------------------------------------------------
        * ajout d'une Equipe a la BDD
        --------------------------------------------------------------------------------------------------------------*/
        btn_addequipe.setOnAction(new EventHandler<ActionEvent>() { //bouton nommé crée
            @Override
            public void handle(ActionEvent actionEvent) {
                String nom = txt_nomequipe.getText();

                System.out.println(nom);
                listeid.clear();
                System.out.println(listeid);


                obsPR.forEach(obs -> listeid.add(obs.getIDPERSONNE())); //ajoute à listeid le nom de toute les personnes (foreach)

                if (nom.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input error");
                    alert.setContentText("saisissez un nom et au moin un joueuer");
                    alert.showAndWait();
                } else {
                    try {
                        if (!(bdd.listEquipe().contains(nom))) {
                            try {bdd.addEquipe(nom, listeid);}
                            catch (SQLException e) {throw new RuntimeException(e);}
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        });

        /*--------------------------------------------------------------------------------------------------------------
        * retour au menu principal
        --------------------------------------------------------------------------------------------------------------*/
        HelloController helloController = new HelloController();
        helloController.allez_a_page(btn_retour, "menu.fxml", "application m2l");
    }
}
