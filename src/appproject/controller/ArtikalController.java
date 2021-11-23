/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.controller;

import appproject.model.Artikal;
import static appproject.model.Artikal.convertToJavaFXImage;
import appproject.model.Kategorija;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author acka
 */
public class ArtikalController implements Initializable {
    
    @FXML
    AnchorPane prozor;
    @FXML
    TableView artikalTbl;
    @FXML
    TableColumn idTblCol;
    @FXML
    TableColumn nazivTblCol;
    @FXML
    TableColumn cijenaTblCol;
    @FXML
    TableColumn popustTblCol;
    @FXML
    TableColumn trCijenaTblCol;
    @FXML
    TableColumn kategorijaTblCol;
    @FXML
    ChoiceBox<Kategorija> kategorija;
    @FXML
    TextField naziv;
    @FXML
    TextField cijena;
    @FXML
    TextField popust;
    @FXML
    TextField trCijena;
    @FXML
    Button dodajBtn;
    @FXML 
    Button urediBtn;
    @FXML 
    Button brisiBtn;
    @FXML
    ImageView slika;
  
    Artikal odabraniArtikal;

    final ObservableList<Kategorija> kat=FXCollections.observableArrayList(
            Kategorija.uzmiSveKategorije()
     );
    
    
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
    public void odaberi_artikal (Event e) {
    this.odabraniArtikal = (Artikal) this.artikalTbl.getSelectionModel().getSelectedItem();
    this.naziv.setText(this.odabraniArtikal.getIme());
    this.cijena.setText(Double.toString(this.odabraniArtikal.getCijena()));
    this.popust.setText(Integer.toString(this.odabraniArtikal.getPopust()));
    this.kategorija.setValue(Kategorija.dajSaId(this.odabraniArtikal.getKategorija()));
    if(this.odabraniArtikal.getSlika()!=null){
    Image sslika=convertToJavaFXImage(this.odabraniArtikal.getId(),200,200);
    this.slika.setImage(sslika);
    }else this.slika.setImage(null);
    }


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kategorija.setItems(kat);
        ObservableList<Artikal> data = Artikal.listaArtikala();
        //idTblCol.setCellValueFactory(new PropertyValueFactory<Artikal, Integer>("Id"));
        nazivTblCol.setCellValueFactory(new PropertyValueFactory<Artikal, String>("ime"));
        cijenaTblCol.setCellValueFactory(new PropertyValueFactory<Artikal, Double>("cijena"));
        trCijenaTblCol.setCellValueFactory(new PropertyValueFactory<Artikal, Double>("trenutnaCijena"));
        popustTblCol.setCellValueFactory(new PropertyValueFactory<Artikal, Integer>("popust"));
        //kategorijaTblCol.setCellValueFactory(new PropertyValueFactory<Artikal, Integer>("id_kategorije"));
        kategorijaTblCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Artikal,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Artikal, String> param) {
      
                    return new SimpleStringProperty(param.getValue().daj_kategoriju());      
            }
            
        });
        artikalTbl.setItems(data);
        cijena.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
            String oldValue, String newValue) {
        if(Objects.equals(popust.getText(),"") || Objects.equals(newValue,"")){
        
         trCijena.setText("");}
        else{
        double popustt=(100-Double.parseDouble(popust.getText()))/100;
        double k=Double.parseDouble(newValue)*popustt;
        k=(double) Math.round(k * 100) / 100;
        trCijena.setText(Double.toString(k));}
            }
        });
        popust.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
            String oldValue, String newValue) {
            if(Objects.equals(cijena.getText(),"") || Objects.equals(newValue,"")){
                 trCijena.setText("");}
            else{
        double popustt=(100-Double.parseDouble(newValue))/100;
        double k=Double.parseDouble(cijena.getText())*popustt;
        k=(double) Math.round(k * 100) / 100;
        trCijena.setText(Double.toString(k));
            }
            
            }
        });
}
    
    public void odaberi_sliku (ActionEvent e) {
         
     
        
            FileChooser fileChooser = new FileChooser();
             
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
              
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
                       
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                slika.setImage(image);
            } catch (IOException ex) {
                Logger.getLogger(ArtikalController.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    
    }
    
    
    @FXML
    public void dodaj_artikal (ActionEvent e) {
    String ime = this.naziv.getText();
    Double ccijena = Double.parseDouble(this.cijena.getText());
    Integer ppopust = Integer.parseInt(this.popust.getText());
    Double ttrCijena = Double.parseDouble(this.trCijena.getText());
    Integer kkategorija = kategorija.getValue().getId();
    if(slika.getImage()==null) {
    Artikal novi = new Artikal (0,ime, ccijena, ppopust, ttrCijena,kkategorija);
    novi.spasi();}
    else
    {
        try {
            BufferedImage bImage = SwingFXUtils.fromFXImage(slika.getImage(), null);
            if (bImage.getColorModel().hasAlpha()) {
            bImage = dropAlphaChannel(bImage);
            }
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", s);
            byte[] res  = s.toByteArray();
            s.close();
            Blob sslika;
            try {
                sslika = new SerialBlob(res);
                Artikal novi = new Artikal (0,ime, ccijena, ppopust, ttrCijena,sslika,kkategorija);
                novi.spasi();
            } catch (SQLException ex) {
                Logger.getLogger(ArtikalController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        } catch (IOException ex) {
            Logger.getLogger(ArtikalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    ObservableList<Artikal> data = Artikal.listaArtikala();
    this.artikalTbl.setItems(data);
    }
    
    @FXML
    public void uredi_artikal(Event e) {
    this.odabraniArtikal.setIme(this.naziv.getText());
    this.odabraniArtikal.setCijena(Double.parseDouble(this.cijena.getText()));
    this.odabraniArtikal.setPopust(Integer.parseInt(this.popust.getText()));
    this.odabraniArtikal.setTrenutnaCijena(Double.parseDouble(this.trCijena.getText()));
    this.odabraniArtikal.setKategorija(this.kategorija.getValue().getId());   
    if(slika.getImage()!=null){
    try {
    BufferedImage bImage = SwingFXUtils.fromFXImage(slika.getImage(), null);
    if (bImage.getColorModel().hasAlpha()) {
    bImage = dropAlphaChannel(bImage);
    }
    ByteArrayOutputStream s = new ByteArrayOutputStream();
    ImageIO.write(bImage, "jpg", s);    
    byte[] res  = s.toByteArray();
    Blob sslika;
        try {
            sslika = new SerialBlob(res);
             this.odabraniArtikal.setSlika(sslika);
        } catch (SQLException ex) {
            Logger.getLogger(ArtikalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (IOException ex) {
            Logger.getLogger(ArtikalController.class.getName()).log(Level.SEVERE, null, ex);
    } 
    }
    this.odabraniArtikal.uredi();
    ObservableList<Artikal> data = Artikal.listaArtikala();
    this.artikalTbl.setItems(data);
    }
    
    @FXML
    public void brisi_artikal(Event e) {
    if (this.odabraniArtikal != null) {
    this.odabraniArtikal.brisi();
    ObservableList<Artikal> data = Artikal.listaArtikala();
    this.artikalTbl.setItems(data);
    }
    }
    
    
    public BufferedImage dropAlphaChannel(BufferedImage src) {
     BufferedImage convertedImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
     convertedImg.getGraphics().drawImage(src, 0, 0, null);

     return convertedImg;
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
