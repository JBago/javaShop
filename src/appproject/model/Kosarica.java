/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author RB computer
 */

  public class Kosarica {
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleIntegerProperty id_artikla = new SimpleIntegerProperty();
    SimpleIntegerProperty id_korisnika = new SimpleIntegerProperty();
    SimpleIntegerProperty kolicina = new SimpleIntegerProperty();
    
    public Kosarica (Integer id, Integer id_artikla, Integer id_korisnika, Integer kolicina) {
        this.id = new SimpleIntegerProperty (id);
        this.id_artikla = new SimpleIntegerProperty(id_artikla);
        this.id_korisnika = new SimpleIntegerProperty(id_korisnika);
        this.kolicina = new SimpleIntegerProperty(kolicina);

    }
    
    public Kosarica (Integer id_artikla, Integer id_korisnika, Integer kolicina) {
        this.id_artikla = new SimpleIntegerProperty(id_artikla);
        this.id_korisnika = new SimpleIntegerProperty(id_korisnika);
        this.kolicina = new SimpleIntegerProperty(kolicina);

    }
    
    public Integer getId () {
        return id.get();
    }
    
    public Integer getId_artikla() {
        return id_artikla.get();
    }
    
    public Integer getId_korisnika() {
        return id_korisnika.get();
    }
    
    public Integer getKolicina() {
        return kolicina.get();
    }
     
   
    
   /* public void setIme(String ime) {
        this.ime = new SimpleStringProperty(ime);
    }

    public void setPrezime(String prezime) {
        this.prezime = new SimpleStringProperty(prezime);
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }
    */
    
    public static ObservableList<Kosarica> listaArtikalaUKosarici(int korisnik) {
        try {
            ObservableList<Kosarica> lista = FXCollections.observableArrayList();
            Connection con= new Konekcija().konekcija;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM kosarica WHERE id_korisnika = ?");
            ps.setInt(1, korisnik);
	    ResultSet rs = ps.executeQuery();
            
            try {
                while (rs.next()) {
                    lista.add(new Kosarica(rs.getInt("id"),rs.getInt("id_artikla"), rs.getInt("id_korisnika"), rs.getInt("kolicina")));
                }
            } catch (SQLException ex) {
                System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(Kosarica.class.getName()).log(Level.SEVERE, null, ex);
        } return null;
     }
    
    public void spasi () {
    try { PreparedStatement upit = Baza.DB.exec("INSERT INTO kosarica VALUES (null,?,?,?)");
    upit.setInt(1, this.getId_artikla());
    upit.setInt(2, this.getId_korisnika());
    upit.setInt(3, this.getKolicina());
    upit.executeUpdate();
    } catch (SQLException ex) { System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
    }
    }  
    
    public void brisi() {
        try {
            Baza DB = new Baza();
            PreparedStatement delete = DB.exec("DELETE FROM kosarica WHERE id=? AND id_korisnika=?");
            delete.setInt(1, this.getId());
            delete.setInt(2, this.getId_korisnika());
            delete.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Kosarica.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void isprazni(int id) {
        
            try {
            Baza DB = new Baza();
            PreparedStatement delete = DB.exec("DELETE FROM kosarica WHERE id_korisnika=?");
            delete.setInt(1, id);
            delete.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Kosarica.class.getName()).log(Level.SEVERE, null, ex);
        } 
     
    }
}
