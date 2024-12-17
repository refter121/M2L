package m2l.m2l;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    personne target;

    public void allez_a_page(MenuItem menuItem, String action, String titre) {
        menuItem.setOnAction(event -> {
            try {
                chargerNouvellePage(action, titre, (Stage) menuItem.getParentPopup().getOwnerWindow());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void allez_a_page(Button button, String action, String titre) {
        button.setOnAction(event -> {
            try {
                chargerNouvellePage(action, titre, (Stage) button.getScene().getWindow());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void chargerNouvellePage(String action, String titre, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(action));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/m2l/m2l/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle(titre);
            stage.setMaxHeight(900);
            stage.setMaxWidth(1200);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* -------------------------------------------------------------------------------------------------------------
        * vers l'affichage des personnes
        --------------------------------------------------------------------------------------------------------------*/
        allez_a_page(itm_menu_seepersonne, "personne_see.fxml", "see personne");

        /* -------------------------------------------------------------------------------------------------------------
        * vers l'ajout des personnes
        --------------------------------------------------------------------------------------------------------------*/
        allez_a_page(itm_menu_addpersonne, "personne_new.fxml", "new personne");


        /* -------------------------------------------------------------------------------------------------------------
        * vers l'affichage des equipes
        --------------------------------------------------------------------------------------------------------------*/
        allez_a_page(itm_menu_seeequipe, "equipe_see.fxml", "See Equipe");


        /* -------------------------------------------------------------------------------------------------------------
        * vers l'ajout des equipes
        --------------------------------------------------------------------------------------------------------------*/
        allez_a_page(itm_menu_addequipe, "equipe_new.fxml", "New Equipe");


        /* -------------------------------------------------------------------------------------------------------------
        * vers l'affichage des competitions
        --------------------------------------------------------------------------------------------------------------*/
        allez_a_page(itm_menu_seecompetition, "competition_see.fxml", "see competition");


        /* -------------------------------------------------------------------------------------------------------------
        * vers l'ajout des competitions
        --------------------------------------------------------------------------------------------------------------*/
        allez_a_page(itm_menu_addcompetition, "competition_new.fxml", "add new competition");

    }
}