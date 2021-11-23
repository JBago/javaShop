/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.controller;


import appproject.model.Artikal;
import appproject.model.Kosarica;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
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
public class KategorijeController implements Initializable {
    
    @FXML
    AnchorPane kat1;
    @FXML
    AnchorPane kat2;
    @FXML
    AnchorPane kat3;
    @FXML
    AnchorPane kat4;
    @FXML
    AnchorPane kat5;
    @FXML
    AnchorPane kat6;
    @FXML
    AnchorPane win;
    @FXML
    AnchorPane page1;
    @FXML
    Label info;
    @FXML 
    ImageView vratiSe;
    
    
    @FXML
    public void k1(MouseEvent event) throws IOException {
        try {
            page1.setVisible(false);
            start(1);
        } catch (Exception ex) {
            Logger.getLogger(KategorijeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    
     @FXML
    public void k2(MouseEvent event) throws IOException {
        try {
            page1.setVisible(false);
            start(2);
        } catch (Exception ex) {
            Logger.getLogger(KategorijeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    
    @FXML
    public void k3(MouseEvent event) throws IOException {
        try {
            page1.setVisible(false);
            start(3);
        } catch (Exception ex) {
            Logger.getLogger(KategorijeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    
    @FXML
    public void k4(MouseEvent event) throws IOException {
        try {
            page1.setVisible(false);
            start(4);
        } catch (Exception ex) {
            Logger.getLogger(KategorijeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    
    @FXML
    public void k5(MouseEvent event) throws IOException {
        try {
            page1.setVisible(false);
            start(5);
        } catch (Exception ex) {
            Logger.getLogger(KategorijeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    
    @FXML
    public void k0(MouseEvent event) throws IOException {
        try {
            page1.setVisible(false);
            start(0);
        } catch (Exception ex) {
            Logger.getLogger(KategorijeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
  
     @FXML
    public void povratak(MouseEvent event) throws IOException {
        try {
            win.getChildren().clear();
            Parent dashboard = FXMLLoader.load(getClass().getClassLoader().getResource("appproject/view/Kategorije.fxml"));
            win.getChildren().addAll(dashboard);
        } catch (Exception ex) {
            Logger.getLogger(KategorijeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
        
    }

    public void start(int kategorija) throws Exception {
              
        int i;
        double j;
        j=Math.ceil((double)Artikal.listaArtikalaPoKategoriji(kategorija).size()/itemsPerPage());
        i=(int)j;
        if(i<1) i=1;
        Pagination pagination = new Pagination(i, 0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex, kategorija);
            }
        });
       
       
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        pagination.setPadding(new Insets(0,0,10,0));
        win.getChildren().addAll(pagination);
        win.getStylesheets().add("appproject/view/shopp.css");
        vratiSe.toFront(); 
    }
    
       public int itemsPerPage() {
        return 5;
    }
 
    public VBox createPage(int pageIndex, int kategorija) {
        VBox box = new VBox(5);
        box.setStyle("-fx-padding:10px;");
        int page = pageIndex * itemsPerPage();
        ObservableList<Artikal> data = Artikal.listaArtikalaPoKategoriji(kategorija);
        int racunica=0;
        if (page+itemsPerPage()>data.size()) racunica=page+itemsPerPage()-(page+itemsPerPage()-data.size());
        else racunica=page+itemsPerPage();
        for (int i = page; i <= racunica-1; i++) {
            HBox element = new HBox(5);
            element.setMinHeight(110);
            element.spacingProperty().setValue(45);
            element.setStyle("-fx-background-color: rgba(220,220,220,0.9); -fx-padding: 20px; -fx-background-radius: 10px");
            element.setAlignment(Pos.CENTER_LEFT);
            Artikal artikal = data.get(i);
            ImageView image = new ImageView();
            
            VBox Vime= new VBox();
            Vime.setMinWidth(450);
            Label text = new Label();
            text.setText(artikal.getIme());
            Vime.setAlignment(Pos.CENTER_LEFT);
            Vime.getChildren().addAll(text);
            
            VBox Vcijena= new VBox();
            Vcijena.setMinWidth(50);
            Text cijena= new Text();
            Text popust= new Text();
            if(artikal.getPopust()!=0){
                cijena.setText(artikal.getCijena().toString()+" KM");
                cijena.setStrikethrough(true);
                popust.setText(Integer.toString(artikal.getPopust())+"%");
            }
            Vcijena.setAlignment(Pos.CENTER_LEFT);
            Vcijena.setSpacing(5);
            Vcijena.getChildren().addAll(cijena, popust);
            
            Text Tcijena= new Text();
            Tcijena.setText(artikal.getTrenutnaCijena().toString()+" KM");
            
            VBox Vkolicina= new VBox();
            Label Lkolicina= new Label("Količina:");
            TextField kolicina = new TextField();
            kolicina.setPrefWidth(30);
            kolicina.setAlignment(Pos.CENTER);
            Vkolicina.getChildren().addAll(Lkolicina, kolicina);
            Vkolicina.setSpacing(10);
            Hyperlink link = new Hyperlink("Ubaci u košaricu");
            link.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                ubaci(artikal,kolicina);
                kolicina.setText("");
            }
           });
           link.setVisited(true);
            Image convertToJavaFXImage = Artikal.convertToJavaFXImage(artikal.getId(),60,60);
            image.setImage(convertToJavaFXImage);          
            element.getChildren().addAll(image, Vime, Vcijena, Tcijena , Vkolicina , link);
            box.getChildren().add(element);
        }
        return box;
    }
    
    @FXML    
    public void ubaci(Artikal artikal, TextField kolicina){
    try{
   
    int kor=LoginController.logiran;
    int kol=Integer.parseInt(kolicina.getText());   
    int id = artikal.getId();
    if(kol<=0) throw new Exception();
    Kosarica novi = new Kosarica (id,kor,kol);
    novi.spasi(); 
    info.setText("Uspješno dodan artikal");
    info.setVisible(true);
    info.setStyle("-fx-background-color: rgba(180,255,180,0.7)");
    info.toFront();
    Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            info.setVisible(false);
        }));
    fiveSecondsWonder.play();
    }catch (Exception ex) {
    info.setText("Došlo je do greške!");
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
