/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RB computer
 */
public class Kategorija {
    
    
    
    private int id;
    private String naziv;
    
    public Kategorija () {
        
    }
    public Kategorija (int id, String naziv) {
        this.id=id;
        this.naziv=naziv;
    }
    
    public int getId() {
        return this.id;
    }
    public String getNaziv() {
        return this.naziv;
    }
    public void setID(int id) {
        this.id=id;
    }
    public void setNaziv(String naziv){
        this.naziv=naziv;
    }
    
    public static Kategorija dajSaId (int id) {
        List <Kategorija> kategorija = uzmiSveKategorije();
        for (Kategorija m : kategorija) {
            if (m.getId() == id) return m;
        }
        return null;
    }
    
    public static List<Kategorija> uzmiSveKategorije() {
        List<Kategorija> kategorija= new ArrayList<>();
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM kategorija");
        try {
            while (rs.next()) {
                kategorija.add(new Kategorija(rs.getInt("id"), rs.getString("naziv")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return kategorija;
    }
    @Override
    public String toString() {
        return getNaziv();
    }




    public void brisi() {
       try {
            Baza DB = new Baza();
            PreparedStatement delete = DB.exec("DELETE FROM mjesto WHERE id=?");
            delete.setInt(1, this.id);
            delete.executeUpdate();
        } catch (SQLException ex) {
            
        }
    }
}
    

