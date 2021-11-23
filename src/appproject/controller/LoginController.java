/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import appproject.model.LogiraniKorisnik;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rbcomputer
 */
public class LoginController implements Initializable {

    @FXML
    Label statusLbl;
    
    @FXML
    TextField kimeTxt;
    
    @FXML
    PasswordField lozinkaTxt;
    
    public static int logiran;
    
    public void prijavise (ActionEvent e) {
        String kime = kimeTxt.getText();
        String lozinka = lozinkaTxt.getText();
        
        if (kime.equals("") || lozinka.equals("")) {
            statusLbl.setVisible(true);
            statusLbl.setText("Morate unijeti sve vrijednosti!");
        } else {
            if (LogiraniKorisnik.logiraj(kime, lozinka)) {
                try {
                    
                    logiran=LogiraniKorisnik.dajID_logirani(lozinka,kime);
                    statusLbl.setTextFill(Color.GREEN);
                    statusLbl.setText("Uspješno ste se prijavili");
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Menu.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Dobrodošli!");
                    stage.setScene(new Scene(root, 1270, 710));
                    stage.setResizable(false);
                    stage.show();
                    statusLbl.getScene().getWindow().hide();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if(LogiraniKorisnik.provjera(kime,lozinka)) {
                try {
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Artikal.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Dobrodošli!");
                    stage.setScene(new Scene(root, 1270, 710));
                    stage.setResizable(false);
                    stage.show();
                    statusLbl.getScene().getWindow().hide();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                statusLbl.setVisible(true);
                statusLbl.setText("Korisnicki podatci nisu ispravni!");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
