/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.controller;

import appproject.model.Transakcija;
import appproject.model.TransakcijaArtikal;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author acka
 */
public class TransakcijeController implements Initializable {
    
    @FXML
    AnchorPane prozor;
    @FXML
    TableView TransakcijaTbl; 
    @FXML
    TableView ArtikalTbl;
    @FXML
    TableColumn idTblCol;
    @FXML
    TableColumn imeTblCol;
    @FXML
    TableColumn cijenaTblCol;
    @FXML
    TableColumn datumTblCol;  
    @FXML
    TableColumn artCijenaCol;
    @FXML
    TableColumn ukCijenaCol;
    @FXML
    TableColumn kolicinaTblCol;
    @FXML
    TableColumn nazivCol;
    Transakcija odabrana_transakcija;
    
   @FXML
    public void artikliOtvori(MouseEvent event) throws IOException {
       Parent dashboard = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Artikal.fxml"));
        prozor.getChildren().clear();
        prozor.getChildren().addAll(dashboard);
    }
    
    @FXML
    public void transakcijeOtvori(MouseEvent event) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Transakcije.fxml"));
        prozor.getChildren().clear();
        prozor.getChildren().addAll(dashboard);
    }
    
    @FXML
    public void korisniciOtvori(MouseEvent event) throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Kontakti.fxml"));
        prozor.getChildren().clear();
        prozor.getChildren().addAll(dashboard);
    }

    
    @FXML
    public void odaberi_transakciju (Event e) {
        this.odabrana_transakcija=  (Transakcija) this.TransakcijaTbl.getSelectionModel().getSelectedItem();
        ObservableList<TransakcijaArtikal> data = TransakcijaArtikal.listaArtikalaPoTransakciji(this.odabrana_transakcija.getId());
        artCijenaCol.setCellValueFactory(new PropertyValueFactory<TransakcijaArtikal, Double>("Cijena"));
        kolicinaTblCol.setCellValueFactory(new PropertyValueFactory<TransakcijaArtikal, Integer>("Kolicina"));
        ukCijenaCol.setCellValueFactory(new PropertyValueFactory<TransakcijaArtikal, Double>("Ukupna_cijena"));
        nazivCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TransakcijaArtikal,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TransakcijaArtikal, String> param) {
      
                    return new SimpleStringProperty(param.getValue().daj_naziv());      
            }
            
        });
        ArtikalTbl.setItems(data);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Transakcija> data = Transakcija.listaTransakcija();
        idTblCol.setCellValueFactory(new PropertyValueFactory<Transakcija, Integer>("Id"));
        //imeTblCol.setCellValueFactory(new PropertyValueFactory<Transakcija, String>("id_korisnika"));
        imeTblCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transakcija,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transakcija, String> param) {
      
                    return new SimpleStringProperty(param.getValue().daj_imeKorisnika());      
            }
            
        });
        cijenaTblCol.setCellValueFactory(new PropertyValueFactory<Transakcija, Double>("Ukupna_cijena"));
        datumTblCol.setCellValueFactory(new PropertyValueFactory<Transakcija, Date>("Datum"));
        TransakcijaTbl.setItems(data);

    }
    
    @FXML
    public void brisi_transakciju(Event e) {
    if (this.odabrana_transakcija != null) {
    TransakcijaArtikal.brisi(this.odabrana_transakcija.getId());
    this.odabrana_transakcija.brisi();
    ObservableList<Transakcija> data = Transakcija.listaTransakcija();
    this.TransakcijaTbl.setItems(data);
    }
    }
    
      public void odjava(MouseEvent event){
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Prijavite se na sustav!");
            stage.setScene(new Scene(root, 450, 450));
            stage.setResizable(false);
            stage.show();
            prozor.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(ArtikalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
