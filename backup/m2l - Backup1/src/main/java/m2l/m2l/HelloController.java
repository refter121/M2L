package m2l.m2l;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    //declare les elements sur les quel on veut interagir @FXML au dessu de chaque elements

    @FXML
    public MenuBar menu_menubar;
    @FXML
    public Menu menu_competition;

    /* vers les competitions*/
    @FXML
    public MenuItem itm_menu_addcompetition;
    @FXML
    public MenuItem itm_menu_seecompetition;

    /* vers les equipes */
    @FXML
    public MenuItem itm_menu_addequipe;
    @FXML
    public MenuItem itm_menu_seeequipe;

    /* vers les personne */
    @FXML
    public MenuItem itm_menu_addpersonne;
    @FXML
    public MenuItem itm_menu_seepersonne;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //element.setOnAction(new EventHandler<>()
        /* -------------------------------------------------------------------------------------------------------------
        * vers l'affichage des personnes
        --------------------------------------------------------------------------------------------------------------*/
        itm_menu_seepersonne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(seePersonneController.class.getResource("personne_see.fxml"));
                try{
                    Parent root = loader.load();
                    Stage stage = (Stage) menu_menubar.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("see personne");
                    stage.show();

                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });

        /* -------------------------------------------------------------------------------------------------------------
        * vers l'ajout des personnes
        --------------------------------------------------------------------------------------------------------------*/
        itm_menu_addpersonne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(newPersoneController.class.getResource("personne_new.fxml"));
                try{
                    Parent root = loader.load();
                    Stage stage = (Stage) menu_menubar.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("new personne");
                    stage.show();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });


        /* -------------------------------------------------------------------------------------------------------------
        * vers l'affichage des equipes
        --------------------------------------------------------------------------------------------------------------*/
        itm_menu_seeequipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(seeEquipeController.class.getResource("equipe_see.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) menu_menubar.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("See Equipe");
                    stage.show();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }

            }
        });

        /* -------------------------------------------------------------------------------------------------------------
        * vers l'ajout des equipes
        --------------------------------------------------------------------------------------------------------------*/
        itm_menu_addequipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(newEquipeController.class.getResource("equipe_new.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) menu_menubar.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("New Equipe");
                    stage.show();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }

            }
        });

        /* -------------------------------------------------------------------------------------------------------------
        * vers l'affichage des competitions
        --------------------------------------------------------------------------------------------------------------*/
        itm_menu_seecompetition.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(seeCompController.class.getResource("competition_see.fxml"));
                try{
                    Parent root = loader.load();
                    Stage stage = (Stage) menu_menubar.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("see competition");
                    stage.show();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });

        /* -------------------------------------------------------------------------------------------------------------
        * vers l'ajout des competitions
        --------------------------------------------------------------------------------------------------------------*/
        itm_menu_addcompetition.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(AddnewComp.class.getResource("competition_new.fxml")); //classController.class.getResource("*.fxml")
                try{
                    Parent root = loader.load();
                    Stage stage = (Stage) menu_menubar.getScene().getWindow();
                    stage.setScene(new Scene(root,1200,900));
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.setTitle("add new competition"); //titre de la page
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}