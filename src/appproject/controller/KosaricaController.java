/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.controller;

import appproject.model.Artikal;
import appproject.model.Kosarica;
import appproject.model.Transakcija;
import appproject.model.TransakcijaArtikal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author RB computer
 */
public class KosaricaController implements  Initializable {
 
    
    @FXML
    ScrollPane pane;
    @FXML
    Label cijena;
    @FXML
    HBox hbox;
    @FXML
    Label info;
    
    double km=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            start();
            
        } catch (Exception ex) {
            Logger.getLogger(KosaricaController.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    } 
    
    public void start() throws Exception {
        
        km=0;
        cijena.setText("0.0");
       
        
       
        
        int i;
        double j;
        ObservableList<Kosarica> dataa = Kosarica.listaArtikalaUKosarici(LoginController.logiran);
        j=Math.ceil((double)dataa.size()/6);
        i=(int)j;
        if(i<1) i=1;
        Pagination pagination = new Pagination(i, 0);
        
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex, dataa);
            }
        });
        
        for (i=0;i<dataa.size();i++)
        {
            Kosarica artikall = dataa.get(i);
            Artikal artikal=Artikal.OdaberiArtikal(artikall.getId_artikla());
            km=km+(Math.round((dataa.get(i).getKolicina()*artikal.getTrenutnaCijena())*100.0)/100.0);
        }
        
        cijena.setText(Double.toString(km)+" KM");
        pagination.setStyle("-fx-background-color:#f0f0f0");
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
 
        pane.setContent(pagination);
        pane.getStylesheets().add("appproject/view/shopp.css");
        
      
    }
    
    public VBox createPage(int pageIndex, ObservableList<Kosarica> dataa) {
        VBox box = new VBox();
        box.setStyle("-fx-padding:10px; -fx-spacing:10px");
        int page = pageIndex * 6;
        int racunica=0;
        if (page+6>dataa.size()) racunica=page+6-(page+6-dataa.size());
        else racunica=6;
        
        for (int i = page; i <= racunica-1; i++) {
            HBox element = new HBox(5);
            element.setMinHeight(100);
            element.spacingProperty().setValue(30);
            element.setStyle("-fx-background-color: rgba(220,220,220,0.9); -fx-padding: 20px; -fx-background-radius: 10px");
            element.setAlignment(Pos.CENTER_LEFT);
            Kosarica artikall = dataa.get(i);
            Artikal artikal=Artikal.OdaberiArtikal(artikall.getId_artikla());
            ImageView image = new ImageView();
            
            VBox Vime= new VBox();
            Vime.setMinWidth(150);
            Label text = new Label();
            text.setText(artikal.getIme());
            Vime.setAlignment(Pos.CENTER_LEFT);
            Vime.getChildren().addAll(text);
            
            VBox Vcijena= new VBox();
            Vcijena.setMinWidth(50);
            Text cijenaa= new Text();
            Text popust= new Text();
            if(artikal.getPopust()!=0){
                cijenaa.setText(artikal.getCijena().toString()+" KM");
                cijenaa.setStrikethrough(true);
                popust.setText(artikal.getPopust().toString()+"%");
            }
            Vcijena.setAlignment(Pos.CENTER_LEFT);
            Vcijena.setSpacing(5);
            Vcijena.getChildren().addAll(cijenaa, popust);
            
            VBox Vtcijena = new VBox();
            Vtcijena.setMinWidth(45);
            Text Tcijena= new Text();
            Tcijena.setText(artikal.getTrenutnaCijena().toString()+" KM");
            Vtcijena.setAlignment(Pos.CENTER_LEFT);
            Vtcijena.getChildren().addAll(Tcijena);
            
            
            VBox Vkolicina= new VBox();
            Label Lkolicina= new Label("Količina:");
            Text kolicina = new Text();
            kolicina.setText(artikall.getKolicina().toString());
            Vkolicina.getChildren().addAll(Lkolicina, kolicina);
            Vkolicina.setSpacing(10);
            
            VBox Vdel = new VBox();
            ImageView del = new ImageView();
            del.setOpacity(0.5);
            del.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                del.setOpacity(0.8);
            }
            });
            del.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                del.setOpacity(0.5);
            }
            });
            del.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
            artikall.brisi();
                try {
                    
                    start();
                } catch (Exception ex) {
                    Logger.getLogger(KosaricaController.class.getName()).log(Level.SEVERE, null, ex);
                }
               }
            });
            Image delete= new Image("appproject/view/delete.jpg",30,30,false,true);
            del.setImage(delete);            
            Image convertToJavaFXImage = Artikal.convertToJavaFXImage(artikal.getId(),60,60);
            image.setImage(convertToJavaFXImage);  
            Vdel.getChildren().addAll(del);
            Vdel.setAlignment(Pos.CENTER_LEFT);
            element.getChildren().addAll(image, Vime, Vcijena, Vtcijena , Vkolicina, Vdel);
            box.getChildren().add(element);
        }
        //cijena.setText(Double.toString(km));
        return box;
    }
    
     public void kupi (ActionEvent e) {
        try {
            
            Date date = new Date(System.currentTimeMillis());
            Transakcija transakcija = new Transakcija(LoginController.logiran, km ,date);
            transakcija.dodajTransakciju();
            ObservableList<Kosarica> dataa = Kosarica.listaArtikalaUKosarici(LoginController.logiran);
            if(dataa.size()==0) throw new Exception();
            for (int i=0;i<dataa.size();i++)
           {
            Kosarica artikall = dataa.get(i);
            Artikal artikal=Artikal.OdaberiArtikal(artikall.getId_artikla());
            TransakcijaArtikal transakcijaArtikal = new TransakcijaArtikal(transakcija.getId(),artikal.getId(),artikal.getTrenutnaCijena(),artikall.getKolicina(),artikal.getTrenutnaCijena()*artikall.getKolicina());
            transakcijaArtikal.dodajArtikalTransakcije();
           }
            Kosarica.isprazni(LoginController.logiran);
            info.setText("Uspješno ste kupili proizvode.");
            info.setVisible(true);
            info.setStyle("-fx-background-color: rgba(180,255,180,0.7)");
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            info.setVisible(false);
             }));
            fiveSecondsWonder.play();
            start();
        } catch (Exception ex) {
    info.setText("Košarica je prazna!");
    info.setVisible(true);
    info.setStyle("-fx-background-color: rgba(255,180,180,0.7)");
    info.toFront();
    Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            info.setVisible(false);
        }));
    fiveSecondsWonder.play();
     }
    }
     
}
