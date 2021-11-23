/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.model;

import static appproject.model.KontaktModel.listaKontakata;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author acka
 */
public class Transakcija {
    
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleIntegerProperty id_korisnika = new SimpleIntegerProperty();
    SimpleDoubleProperty ukupna_cijena = new SimpleDoubleProperty();
    Date datum;
    
    public Transakcija(Integer id, Integer id_korisnika, Double ukupna_cijena, Date datum) {
        this.id = new SimpleIntegerProperty(id);
        this.id_korisnika = new SimpleIntegerProperty(id_korisnika);
        this.ukupna_cijena = new SimpleDoubleProperty(ukupna_cijena);
        this.datum = datum;
    }
    
    public Transakcija (Integer id_korisnika, Double ukupna_cijena, Date datum) {
        this.id_korisnika = new SimpleIntegerProperty(id_korisnika);
        this.ukupna_cijena = new SimpleDoubleProperty(ukupna_cijena);
        this.datum = datum;
    }
    
     public Integer getId () {
        return id.get();
    }
     
     public Integer getId_korisnika () {
        return id_korisnika.get();
    }
    public Double getUkupna_cijena() {
        return ukupna_cijena.get();
    }
   
    public Date getDatum() {
        return datum;
    }
    
    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }
    
    public String daj_imeKorisnika () {
        List <KontaktModel> kontakt = listaKontakata();
        for (KontaktModel k : kontakt) {
            if (k.getId() == this.getId_korisnika()) return k.getKorisnickoIme();
        }
        return null;
    } 
    
  public void dodajTransakciju()
  {
      try { PreparedStatement upit = Baza.DB.exec("INSERT INTO transakcija VALUES (?,?,?,?)");
    upit.setString(1, null);
    upit.setInt(2, this.getId_korisnika());
    upit.setDouble(3, this.getUkupna_cijena());
    upit.setDate(4, this.getDatum());
    upit.executeUpdate();
    ResultSet rs=Baza.DB.select("SELECT * FROM transakcija");
    rs.last();
    this.setId(rs.getInt("id"));
    } catch (SQLException ex) { System.out.println("Greška prilikom izvršavanja transakcije: " + ex.getMessage());
    }
  }
    
  public static ObservableList<Transakcija> listaTransakcija () {
        ObservableList<Transakcija> lista = FXCollections.observableArrayList();
        
        ResultSet rs = Baza.DB.select("SELECT * FROM transakcija");
        
        try {
            while (rs.next()) {
                lista.add(new Transakcija(rs.getInt("id"),rs.getInt("id_korisnika"), rs.getDouble("ukupna_cijena"), rs.getDate("datum")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
  
  
    public void brisi () {
    try { PreparedStatement upit = Baza.DB.exec("DELETE FROM transakcija WHERE id=?");
    upit.setInt(1, this.getId());
    upit.executeUpdate();
    } catch (SQLException ex) { System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
    }
    }
}
