/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;



/**
 *
 * @author RB computer
 */
public class NaslovnicaController implements Initializable{
    
    @FXML
    Pagination page;
    @FXML
    Label welcome;
    
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        page.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
        page.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        page.setStyle("-fx-arrows-visible: false; -fx-tooltip-visible: false; -fx-page-information-visible: false;");
        page.getStylesheets().add("appproject/view/nasl.css");
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            int pos = (page.getCurrentPageIndex()+1) % page.getPageCount();
            page.setCurrentPageIndex(pos);
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        }
    public VBox createPage(int pageIndex) {
        VBox box = new VBox();
            //box.setStyle("-fx-border-style:solid;-fx-border-color:black;");
            HBox element = new HBox();  
            ImageView image = new ImageView(); 
            Image img=null;
            //image.setEffect(new DropShadow(20, Color.BLACK));
            img = new Image("appproject/view/naslovnica"+pageIndex+".jpg",960,435,false,true);
            image.setImage(img);
 
            element.getChildren().addAll(image);
            box.getChildren().add(element);
        
        return box;
        }
    
   
    
}

