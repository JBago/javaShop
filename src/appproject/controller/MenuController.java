/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.controller;

import static appproject.controller.LoginController.logiran;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author RB computer
 */
public class MenuController implements Initializable {
   
    @FXML
    AnchorPane window;
    @FXML
    Rectangle shop;
    @FXML
    Rectangle naslovnica;
    @FXML
    Rectangle kategorija;
    @FXML
    Rectangle kosarica;
    @FXML
    BorderPane border_pane;
    @FXML
    ScrollPane center;

    
    @FXML
    public void otvori_shop(MouseEvent event) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Shopp.fxml"));
        center.setContent(dashboard);
        shop.setFill(Color.valueOf("#bc6025"));
        kategorija.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
        naslovnica.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
        kosarica.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
    }
    
     @FXML
    public void otvori_naslovnica(MouseEvent event) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Naslovnica.fxml"));
        center.setContent(dashboard);
        naslovnica.setFill(Color.valueOf("#bc6025"));
        kategorija.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
        shop.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
        kosarica.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
    }
    
    @FXML
    public void otvori_kategorija(MouseEvent event) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Kategorije.fxml"));
        center.setContent(dashboard);
        kategorija.setFill(Color.valueOf("#bc6025"));
        shop.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
        naslovnica.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
        kosarica.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
    }
    
    @FXML
    public void otvori_kosarica(MouseEvent event) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Kosarica.fxml"));
        center.setContent(dashboard);
        kosarica.setFill(Color.valueOf("#bc6025"));
        kategorija.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
        shop.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
        naslovnica.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
    }
    
    @FXML
    public void odjava(MouseEvent event) throws IOException {
        
                    logiran=-1;                
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Login.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Prijavite se na sustav!");
                    stage.setScene(new Scene(root, 450, 450));
                    stage.setResizable(false);
                    stage.show();
                    window.getScene().getWindow().hide();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            center.setHbarPolicy(ScrollBarPolicy.NEVER);
            border_pane.setMinSize(1280.0, 720.0);
            border_pane.setMaxSize(1280.0, 720.0);
            Parent dashboard = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Naslovnica.fxml"));
            center.setContent(dashboard);
            naslovnica.setFill(Color.valueOf("#bc6025"));
            shop.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
            kategorija.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
            kosarica.setFill(LinearGradient.valueOf("from 50% 100% to 50% 0%,  #cc6612  ,#ee8822 93% , #b85416 100%"));
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
}

