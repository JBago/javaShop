package appproject.controller;


import appproject.model.KontaktModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class KontaktiController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    AnchorPane prozor;
    @FXML
    TableView kontaktiTbl;
    @FXML
    TableColumn idTblCol;
    @FXML
    TableColumn korisnicko_imeTblCol;
    @FXML
    TableColumn lozinkaTblCol;
    @FXML
    TableColumn imeTblCol;
    @FXML
    TableColumn prezimeTblCol;
    @FXML
    TableColumn emailTblCol;
    @FXML
    Button dodajBtn;
    @FXML 
    Button urediBtn;
    @FXML 
    Button brisiBtn; 
    @FXML
    TextField kime;
    @FXML
    TextField lozinka;
    @FXML
    TextField ime;
    @FXML
    TextField prezime;
    @FXML
    TextField email;

    KontaktModel odabrani_korisnik;
    
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<KontaktModel> data = KontaktModel.listaKontakata();
        idTblCol.setCellValueFactory(new PropertyValueFactory<KontaktModel, Integer>("Id"));
        korisnicko_imeTblCol.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("KorisnickoIme"));
        lozinkaTblCol.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("Lozinka"));
        imeTblCol.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("Ime"));
        prezimeTblCol.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("Prezime"));
        emailTblCol.setCellValueFactory(new PropertyValueFactory<KontaktModel, String>("Email"));
        kontaktiTbl.setItems(data);

    }
    
    @FXML
    public void odaberi_korisnika (Event e) {
    this.odabrani_korisnik = (KontaktModel) this.kontaktiTbl.getSelectionModel().getSelectedItem();
    this.kime.setText(this.odabrani_korisnik.getKorisnickoIme());
    this.lozinka.setText(this.odabrani_korisnik.getLozinka());
    this.ime.setText(this.odabrani_korisnik.getIme());
    this.prezime.setText(this.odabrani_korisnik.getPrezime());
    this.email.setText(this.odabrani_korisnik.getEmail());

    }
    
    @FXML
    public void urediKorisnika(Event e) {
    this.odabrani_korisnik.setKorisnickoIme(this.kime.getText());
    this.odabrani_korisnik.setIme(this.ime.getText());
    this.odabrani_korisnik.setLozinka(this.lozinka.getText());
    this.odabrani_korisnik.setPrezime(this.prezime.getText());
    this.odabrani_korisnik.setEmail(this.email.getText());
    this.odabrani_korisnik.uredi();
    ObservableList<KontaktModel> data = KontaktModel.listaKontakata();
    this.kontaktiTbl.setItems(data);
    }
    
     @FXML
    public void dodajKorisnika(Event e) {
    String kime = this.kime.getText();
    String lozinka = this.lozinka.getText();
    String ime = this.ime.getText();
    String prezime = this.prezime.getText();
    String email = this.email.getText();
    KontaktModel novi = new KontaktModel (0,kime, lozinka, ime, prezime,email);
    novi.spasi();
    ObservableList<KontaktModel> data = KontaktModel.listaKontakata();
    this.kontaktiTbl.setItems(data);  
    }
    
     @FXML
    public void izbrisiKorisnika(Event e) {
    if (this.odabrani_korisnik != null) {
    this.odabrani_korisnik.brisi();
    ObservableList<KontaktModel> data = KontaktModel.listaKontakata();
    this.kontaktiTbl.setItems(data);
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
