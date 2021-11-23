package appproject.model;









import static appproject.model.Kategorija.uzmiSveKategorije;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;


/**
 *
 * @author Daniel
 */
public class Artikal {


    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty ime = new SimpleStringProperty();
    SimpleDoubleProperty cijena = new SimpleDoubleProperty();
    SimpleIntegerProperty popust = new SimpleIntegerProperty();
    SimpleDoubleProperty trenutnaCijena = new SimpleDoubleProperty();
    SimpleIntegerProperty kategorija = new SimpleIntegerProperty();
    Blob slika;

    public Artikal (Integer id, String ime, Double cijena, Integer popust, Double trenutnaCijena, Integer kategorija) {
        this.id = new SimpleIntegerProperty (id);
        this.kategorija = new SimpleIntegerProperty(kategorija);
        this.ime = new SimpleStringProperty(ime);
        this.cijena = new SimpleDoubleProperty(cijena);
        this.popust = new SimpleIntegerProperty(popust);
        this.trenutnaCijena = new SimpleDoubleProperty(trenutnaCijena);
        this.slika=null;
        

    }
    
    public Artikal (Integer id, String ime, Double cijena, Integer popust, Double trenutnaCijena, Blob slika , Integer kategorija) {
        this.id = new SimpleIntegerProperty (id);
        this.kategorija = new SimpleIntegerProperty(kategorija);
        this.ime = new SimpleStringProperty(ime);
        this.cijena = new SimpleDoubleProperty(cijena);
        this.popust = new SimpleIntegerProperty(popust);
        this.trenutnaCijena = new SimpleDoubleProperty(trenutnaCijena);
        this.slika=slika;
        
    }
    
    public Integer getId () {
        return id.get();
    }
    
    public String getIme () {
        return ime.get();
    }
    
 
    public Double getCijena () {
        return cijena.get();
    }

    public Integer getPopust () {
        return popust.get();
    }
    
    public int getKategorija() {
        return kategorija.get();
    }

    public Double getTrenutnaCijena () {
        return trenutnaCijena.get();
    }

    public Blob getSlika () {
        return slika;
    }
    
    public void setIme(String ime) {
        this.ime.set(ime);
    }
    
    public void setCijena(Double cijena) {
        this.cijena.set(cijena);
    }
    
    public void setPopust(Integer popust) {
        this.popust.set(popust);
    }
    
    public void setTrenutnaCijena(Double trenutnaCijena) {
        this.trenutnaCijena.set(trenutnaCijena);
    }
    
    public void setKategorija(Integer kat) {
        this.kategorija.set(kat);
    }
    
    public void setSlika(Blob slika) {
        this.slika=slika;
    }
    
     public String daj_kategoriju () {
        List <Kategorija> kategorija = uzmiSveKategorije();
        for (Kategorija k : kategorija) {
            if (k.getId() == this.getKategorija()) return k.getNaziv();
        }
        return null;
    }
    
    public static ObservableList<Artikal> listaArtikala () {
        ObservableList<Artikal> lista = FXCollections.observableArrayList();
        
        ResultSet rs = Baza.DB.select("SELECT * FROM artikl");
        
        try {
            while (rs.next()) {
                lista.add(new Artikal(rs.getInt("id"), rs.getString("ime"), rs.getDouble("cijena"), rs.getInt("popust"),rs.getDouble("trenutnaCijena"),rs.getBlob("slika"),rs.getInt("id_kategorije")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
    
     public static ObservableList<Artikal> listaArtikalaPoKategoriji (int kategorija) {
        try {
            ObservableList<Artikal> lista = FXCollections.observableArrayList();
            Connection con= new Konekcija().konekcija;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM artikl WHERE id_kategorije = ?");
            ps.setInt(1, kategorija);
	    ResultSet rs = ps.executeQuery();
            
            try {
                while (rs.next()) {
                    lista.add(new Artikal(rs.getInt("id"), rs.getString("ime"), rs.getDouble("cijena"), rs.getInt("popust"),rs.getDouble("trenutnaCijena"),rs.getInt("id_kategorije")));
                }
            } catch (SQLException ex) {
                System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(Artikal.class.getName()).log(Level.SEVERE, null, ex);
        } return null;
     }
     
    public static Artikal OdaberiArtikal (int id) {
        try {
         
            Connection con= new Konekcija().konekcija;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM artikl WHERE id = ?");
            ps.setInt(1, id);
	    ResultSet rs = ps.executeQuery();
            
            try {
                while (rs.next()) {
                   Artikal artikal= new Artikal(rs.getInt("id"), rs.getString("ime"),  rs.getDouble("cijena"), rs.getInt("popust"),rs.getDouble("trenutnaCijena"),rs.getInt("id_kategorije"));
                   return artikal;
                }
            } catch (SQLException ex) {
                System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Artikal.class.getName()).log(Level.SEVERE, null, ex);
        } return null;
     }
    public static Image convertToJavaFXImage(int id, int width, int height) { 

        try {
            Connection con= new Konekcija().konekcija;
            //Connection con=(Connection)DriverManager.getConnection("jdbc:mysql://localhost/puj?user=root&password=");
            PreparedStatement ps = con.prepareStatement(
			            "SELECT slika FROM artikl WHERE id = ?");
			     
                            {
			        ps.setInt(1, id);
			        ResultSet results = ps.executeQuery();
			        Image img = null ;
			        if (results.next()) {
			            Blob foto = results.getBlob("slika");
			            InputStream is = foto.getBinaryStream();
			            img = new Image(is,width,height,false,true) ; // false = no background loading 
			            is.close();
			        }
			        results.close();
			        return img ;
			    }
        }catch (Throwable e) {
				Image img=null;
                                img = new Image("appproject/view/nema_slike.jpeg",60,60,false,true);
                                return img;
				}
				
    }
    
    public void spasi () {
    try {
    PreparedStatement upit = Baza.DB.exec("INSERT INTO artikl VALUES(null,?,?,?,?,?,?)");
    upit.setString(1, this.getIme());
    upit.setDouble(2, this.getCijena());
    upit.setDouble(3, this.getPopust());
    upit.setDouble(4, this.getTrenutnaCijena());
    upit.setBlob(5, this.getSlika());
    upit.setInt(6, this.getKategorija());
    
    
    upit.executeUpdate();
    } catch (SQLException ex) {
    System.out.println("Greška prilikom spasavanja artikla u bazu:" + ex.getMessage());
    }
    }
    
    public void uredi () {
    try { PreparedStatement upit = Baza.DB.exec("UPDATE artikl SET ime=?, cijena=?, popust=?, trenutnaCijena=?, slika=?, id_kategorije=? WHERE id=?");
    upit.setString(1, this.getIme());
    upit.setDouble(2, this.getCijena());
    upit.setInt(3, this.getPopust());
    upit.setDouble(4, this.getTrenutnaCijena());
    upit.setBlob(5,this.getSlika());
    upit.setInt(6, this.getKategorija());
    upit.setInt(7, this.getId());
    upit.executeUpdate();
    }  
    catch (SQLException ex) { System.out.println("Greška prilikom spasavanja artikla u bazu: " + ex.getMessage());
    }
    }
    
    public void brisi () {
    try { PreparedStatement upit = Baza.DB.exec("DELETE FROM artikl WHERE id=?");
    upit.setInt(1, this.getId());
    upit.executeUpdate();
    } catch (SQLException ex) { System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
    }
    }
}
  
    
