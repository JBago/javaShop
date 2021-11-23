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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Daniel
 */
public class KontaktModel {
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty korisnickoIme = new SimpleStringProperty();
    SimpleStringProperty lozinka = new SimpleStringProperty();
    SimpleStringProperty ime = new SimpleStringProperty();
    SimpleStringProperty prezime = new SimpleStringProperty();
    SimpleStringProperty email = new SimpleStringProperty();
    
    
    public KontaktModel (Integer id, String korisnickoIme,String lozinka ,String ime, String prezime, String email) {
        this.id = new SimpleIntegerProperty (id);
        this.korisnickoIme = new SimpleStringProperty(korisnickoIme);
        this.lozinka = new SimpleStringProperty(lozinka);
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.email = new SimpleStringProperty(email);
    }
    
    
    
    
    public Integer getId () {
        return id.get();
    }
    
    public String getKorisnickoIme(){
        return korisnickoIme.get();
    }
    
    public String getLozinka() {
        return lozinka.get();
    }
    
    public String getIme () {
        return ime.get();
    }
    
    public String getPrezime () {
        return prezime.get();
    }
    
    public String getEmail () {
        return email.get();
    }
    
     public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = new SimpleStringProperty(korisnickoIme);
    }

     public void setLozinka(String lozinka) {
        this.lozinka = new SimpleStringProperty(lozinka);
    }
     
    public void setIme(String ime) {
        this.ime = new SimpleStringProperty(ime);
    }
    
    public void setPrezime(String prezime) {
        this.prezime = new SimpleStringProperty(prezime);
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }
    
    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }
    
    public static ObservableList<KontaktModel> listaKontakata () {
        ObservableList<KontaktModel> lista = FXCollections.observableArrayList();
        
        ResultSet rs = Baza.DB.select("SELECT * FROM korisnik");
        
        try {
            while (rs.next()) {
                lista.add(new KontaktModel(rs.getInt("id"), rs.getString("korisnicko_ime"), rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"), rs.getString("email")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
   
    public static KontaktModel OdaberiKontakt (int id) {
        try {
         
            Connection con= new Konekcija().konekcija;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM korisnik WHERE id = ?");
            ps.setInt(1, id);
	    ResultSet rs = ps.executeQuery();
            
            try {
                while (rs.next()) {
                   KontaktModel kontakt= new KontaktModel(rs.getInt("id"), rs.getString("korisnicko_ime"), rs.getString("lozinka"), rs.getString("ime"), rs.getString("prezime"), rs.getString("email"));
                   return kontakt;
                }
            } catch (SQLException ex) {
                System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(KontaktModel.class.getName()).log(Level.SEVERE, null, ex);
        } return null;
     }
    
    public void spasi () {
    try {
    PreparedStatement upit = Baza.DB.exec("INSERT INTO korisnik VALUES(null,?,?,?,?,?)");
    upit.setString(1, this.getKorisnickoIme());
    upit.setString(2, this.getLozinka());
    upit.setString(3, this.getIme());
    upit.setString(4, this.getPrezime());
    upit.setString(5, this.getEmail());   
    upit.executeUpdate();
    } catch (SQLException ex) {
    System.out.println("Greška prilikom spasavanja artikla u bazu:" + ex.getMessage());
    }
    }
    
    public void uredi () {
    try { PreparedStatement upit = Baza.DB.exec("UPDATE korisnik SET korisnicko_ime=?, lozinka=?, ime=?, prezime=?, email=? WHERE id=?");
    upit.setString(1, this.getKorisnickoIme());
    upit.setString(2, this.getLozinka());
    upit.setString(3, this.getIme());
    upit.setString(4, this.getPrezime());
    upit.setString(5, this.getEmail());
    upit.setInt(6, this.getId());
    upit.executeUpdate();
    }  
    catch (SQLException ex) { System.out.println("Greška prilikom spasavanja artikla u bazu: " + ex.getMessage());
    }
    }
    public void brisi () {
    try { PreparedStatement upit = Baza.DB.exec("DELETE FROM korisnik WHERE id=?");
    upit.setInt(1, this.getId());
    upit.executeUpdate();
    } catch (SQLException ex) { System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
    }
    }
}
