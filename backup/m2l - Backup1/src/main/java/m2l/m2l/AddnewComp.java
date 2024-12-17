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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddnewComp implements Initializable {
    SQL_M2L bdd = new SQL_M2L();

    private ArrayList<personne> listCanPer = new ArrayList<>();
    private ArrayList<equipe> listCanEqu = new ArrayList<>();
    private equipe targetE;
    private personne targetP;
    private boolean error = false;


    @FXML
    public AnchorPane ap;
    @FXML
    public Button btn_retour;
    @FXML
    public Button btn_ajouter;
    @FXML
    public Button btn_retirer;
    @FXML
    public Button btn_creer;

    @FXML TextField txt_nom;
    @FXML TextField txt_date;

    @FXML
    public CheckBox cb_equipe;


    /*tab equipe L*/
    @FXML
    public TableView<equipe> tab_equipe_L;
    @FXML public TableColumn<equipe, Integer> idequipe_L;
    @FXML public TableColumn<equipe, String> nomequipe_L;

    public ObservableList<equipe> obsEL = FXCollections.observableArrayList();

    /*tab equipe R*/
    @FXML
    public TableView<equipe> tab_equipe_R;
    @FXML public  TableColumn<equipe, Integer> idequipe_R;
    @FXML public TableColumn<equipe, String> nomequipe_R;

    public ObservableList<equipe> obsER = FXCollections.observableArrayList();

    /*tab Personne L*/
    @FXML
    public  TableView<personne> tab_personne_L;
    @FXML public TableColumn<personne, Integer> idpersonne_L;
    @FXML public TableColumn<personne, String> nompersonne_L;
    @FXML public  TableColumn<personne, String> prenompersonne_L;

    public ObservableList<personne> obsPL = FXCollections.observableArrayList();

    /*tab Personne L*/
    @FXML
    public  TableView<personne> tab_personne_R;
    @FXML public TableColumn<personne, Integer> idpersonne_R;
    @FXML public TableColumn<personne, String> nompersonne_R;
    @FXML public  TableColumn<personne, String> prenompersonne_R;

    public ObservableList<personne> obsPR = FXCollections.observableArrayList();

    public AddnewComp() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* tableau droite */
        idequipe_R.setCellValueFactory(new PropertyValueFactory<>("IDEQUIPE"));
        nomequipe_R.setCellValueFactory(new PropertyValueFactory<>("NOM"));
        tab_equipe_R.setItems(obsER);

        idpersonne_R.setCellValueFactory(new PropertyValueFactory<>("IDPERSONNE"));
        nompersonne_R.setCellValueFactory(new PropertyValueFactory<>("NOMPERSONNE"));
        prenompersonne_R.setCellValueFactory(new PropertyValueFactory<>("PRENOMPERSONNE"));
        tab_personne_R.setItems(obsPR);

        /* init tableau gauche */
        try {
            obsEL.setAll(bdd.listEquipe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(obsEL);

        idequipe_L.setCellValueFactory(new PropertyValueFactory<>("IDEQUIPE"));
        nomequipe_L.setCellValueFactory(new PropertyValueFactory<>("NOM"));
        tab_equipe_L.setItems(obsEL);

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

        /* activation du mode equie ou individuel */
        tab_equipe_R.setDisable(true);
        tab_equipe_L.setDisable(true);

        cb_equipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cb_equipe.isSelected()) {
                    System.out.println("en equipe");

                    tab_equipe_R.setDisable(false);
                    tab_equipe_L.setDisable(false);

                    tab_personne_R.setDisable(true);
                    tab_personne_L.setDisable(true);

                    obsPR.clear();
                    try {
                        obsPL.setAll(bdd.listPersonne());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    System.out.println("participation individuel");

                    tab_equipe_R.setDisable(true);
                    tab_equipe_L.setDisable(true);

                    tab_personne_R.setDisable(false);
                    tab_personne_L.setDisable(false);

                    obsER.clear();

                    try {
                        obsEL.setAll(bdd.listEquipe());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

        /* selection et ajout et retier */
        tab_equipe_L.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int idselect = newSelection.getIDEQUIPE();
                String NomEquipeSelect = newSelection.getNOM();
                targetE = newSelection;
                System.out.println("Equipe sélectionnée: "+idselect+" "+NomEquipeSelect);
            }});

        tab_personne_L.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int idselect = newSelection.getIDPERSONNE();
                String NomPersonneSelect = newSelection.getNOMPERSONNE();
                String PrenompersonneSelect = newSelection.getPRENOMPERSONNE();
                targetP = newSelection;
                System.out.println("Personne: "+idselect+" "+NomPersonneSelect +" "+PrenompersonneSelect);
            }});

        tab_equipe_R.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int idselect = newSelection.getIDEQUIPE();
                String NomEquipeSelect = newSelection.getNOM();
                targetE = newSelection;
                System.out.println("Equipe sélectionnée: " + idselect + " " + NomEquipeSelect);
            }
        });;

        tab_personne_R.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int idselect = newSelection.getIDPERSONNE();
                String NomPersonneSelect = newSelection.getNOMPERSONNE();
                String PrenompersonneSelect = newSelection.getPRENOMPERSONNE();
                targetP = newSelection;
                System.out.println("Personne: "+idselect+" "+NomPersonneSelect +" "+PrenompersonneSelect);}
        });



        btn_ajouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cb_equipe.isSelected() && targetE != null) {
                    obsER.add(targetE);
                    obsEL.remove(targetE);

                } else if (!cb_equipe.isSelected() && targetP != null) {
                    obsPR.add(targetP);
                    obsPL.remove(targetP);
                    
                }
            }
        });

        btn_retirer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cb_equipe.isSelected() && targetE != null) {
                    obsEL.add(targetE);
                    obsER.remove(targetE);

                } else if (!cb_equipe.isSelected() && targetP != null) {
                    obsPL.add(targetP);
                    obsPR.remove(targetP);

                }
            }
        });

        btn_creer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    int id = bdd.compID();
                    listCanEqu.addAll(obsER);
                    listCanPer.addAll(obsPR);

                    if (cb_equipe.isSelected()) {/* comp equipe*/
                        bdd.addCompetitionE(id,txt_nom.getText(),txt_date.getText(),listCanEqu);

                    } else { /* comp Personne */
                        bdd.addCompetitionP(id,txt_nom.getText(),txt_date.getText(),listCanPer);
                    }

                } catch (SQLException e) {
                    error=true;
                    throw new RuntimeException(e);
                }
                if(error!=true){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("confirmation");
                    alert.setContentText("Confirmation de la commande");
                    alert.showAndWait();
                    return;
                } else {error=false;}
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
