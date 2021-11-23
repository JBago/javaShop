/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.model;

import static appproject.model.Artikal.listaArtikala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author acka
 */
public class TransakcijaArtikal {
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleIntegerProperty id_transakcija = new SimpleIntegerProperty();
    SimpleIntegerProperty id_artikla = new SimpleIntegerProperty();
    SimpleDoubleProperty cijena = new SimpleDoubleProperty();
    SimpleIntegerProperty kolicina = new SimpleIntegerProperty();
    SimpleDoubleProperty ukupna_cijena = new SimpleDoubleProperty();
    
    public TransakcijaArtikal (Integer id_transakcija, Integer id_artikla, Double cijena,Integer kolicina ,Double ukupna_cijena) {
        this.id_transakcija = new SimpleIntegerProperty(id_transakcija);
        this.id_artikla= new SimpleIntegerProperty(id_artikla);
        this.cijena = new SimpleDoubleProperty(cijena);
        this.kolicina = new SimpleIntegerProperty(kolicina);
        this.ukupna_cijena = new SimpleDoubleProperty(ukupna_cijena);
        
    }
    
      public Integer getId () {
        return id.get();
    }
     
      
        public Integer getId_transakcija () {
        return id_transakcija.get();
    }
     
          public Integer getId_artikla () {
        return id_artikla.get();
    }
    
        
        public Integer getKolicina () {
        return kolicina.get();
    }
     
       public double getCijena () {
        return cijena.get();
    }
       
         public double getUkupna_cijena () {
        return ukupna_cijena.get();
    }
      
     public String daj_naziv () {
        List <Artikal> artikal = listaArtikala();
        for (Artikal a : artikal) {
            if (a.getId() == this.getId_artikla()) return a.getIme();
        }
        return null;
    }     
         
  public void dodajArtikalTransakcije()
  {
        try { PreparedStatement upit = Baza.DB.exec("INSERT INTO transakcija_artikal VALUES (null,?,?,?,?,?)");
    upit.setInt(1, this.getId_transakcija());
    upit.setInt(2, this.getId_artikla());
    upit.setDouble(3, this.getCijena());
    upit.setInt(4, this.getKolicina());
    upit.setDouble(5, this.getUkupna_cijena());
    upit.executeUpdate();
    } catch (SQLException ex) { System.out.println("Greška prilikom izvršavanja transakcije: " + ex.getMessage());
    }
  }
  
  public static ObservableList<TransakcijaArtikal> listaArtikalTransakcija () {
        ObservableList<TransakcijaArtikal> lista = FXCollections.observableArrayList();
        
        ResultSet rs = Baza.DB.select("SELECT * FROM transakcija_artikal");
        
        try {
            while (rs.next()) {
                lista.add(new TransakcijaArtikal(rs.getInt("id_transakcija"), rs.getInt("id_artikla"), rs.getDouble("cijena"), rs.getInt("kolicina"), rs.getDouble("ukupna_cijena")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
  
        public static ObservableList<TransakcijaArtikal> listaArtikalaPoTransakciji (int id) {
        try {
            ObservableList<TransakcijaArtikal> lista = FXCollections.observableArrayList();
            Connection con= new Konekcija().konekcija;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM transakcija_artikal WHERE id_transakcija = ?");
            ps.setInt(1, id);
	    ResultSet rs = ps.executeQuery();
            
            try {
                while (rs.next()) {
                    lista.add(new TransakcijaArtikal(rs.getInt("id_transakcija"), rs.getInt("id_artikla"), rs.getDouble("cijena"), rs.getInt("kolicina"), rs.getDouble("ukupna_cijena")));
                }
            } catch (SQLException ex) {
                System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(Artikal.class.getName()).log(Level.SEVERE, null, ex);
        } return null;
     }
        
    public static void brisi (int id_transakcije) {
    try { PreparedStatement upit = Baza.DB.exec("DELETE FROM transakcija_artikal WHERE id_transakcija=?");
    upit.setInt(1, id_transakcije);
    upit.executeUpdate();
    } catch (SQLException ex) { System.out.println("Greška prilikom spasavanja korisnika u bazu: " + ex.getMessage());
    }
    }
}
