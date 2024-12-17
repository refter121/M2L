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
    public Button btn_retirer;
    @FXML
    public TextField txt_nomequipe;
    @FXML
    public TextField txt_idjoueur;


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

    /*@FXML
    public TableView<personne> tab_joueur;
    @FXML public TableColumn<personne, Integer> tc_idpersonne;
    @FXML public TableColumn<personne,String> tc_personnenom;
    @FXML public TableColumn<personne,String> tc_personneprenom;
    @FXML public TableColumn<personne,String> tc_email;*/

    public ObservableList<personne> obsPR = FXCollections.observableArrayList();

    public ObservableList<personne> obsPL = FXCollections.observableArrayList();

    public MajEquipeController(equipe target) throws SQLException, ClassNotFoundException {
        this.target = target;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init nom de l'equipe
        txt_nomequipe.setText(target.getNOM());

        //init tab membre (R)

        obsPR.addAll(target.getList()); /* affichage du tableau + controleur*/
        idpersonne_R.setCellValueFactory(new PropertyValueFactory<>("IDPERSONNE"));
        nompersonne_R.setCellValueFactory(new PropertyValueFactory<>("NOMPERSONNE"));
        prenompersonne_R.setCellValueFactory(new PropertyValueFactory<>("PRENOMPERSONNE"));
        tab_personne_R.setItems(obsPR);

        //init tab tab non membre (L)
        try {
            obsPL.setAll(bdd.listPersonne());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (personne p : obsPR) {
            obsPL.removeIf(q -> p.getIDPERSONNE() == q.getIDPERSONNE());
        }


        System.out.println(obsPL);


        idpersonne_L.setCellValueFactory(new PropertyValueFactory<>("IDPERSONNE"));
        nompersonne_L.setCellValueFactory(new PropertyValueFactory<>("NOMPERSONNE"));
        prenompersonne_L.setCellValueFactory(new PropertyValueFactory<>("PRENOMPERSONNE"));
        tab_personne_L.setItems(obsPL);

        //Selecteur

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
        * retirer d'un membre a l'equipe
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

        /*--------------------------------------------------------------------------------------------------------------
        * ajout d'un membre a l'equipe
        --------------------------------------------------------------------------------------------------------------*/
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
        * modification d'une Equipe dans la BDD
        --------------------------------------------------------------------------------------------------------------*/
        btn_maj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nom = txt_nomequipe.getText();

                for(personne p: obsPR){
                    listeid.add(p.getIDPERSONNE());
                }

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
