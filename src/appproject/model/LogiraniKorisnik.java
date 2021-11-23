/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author RB computer
 */
public class LogiraniKorisnik{
    
    
    //SimpleIntegerProperty id = new SimpleIntegerProperty();
    public int id;
    SimpleStringProperty lozinka = new SimpleStringProperty();
    SimpleStringProperty ime = new SimpleStringProperty();
    
    private static String adminIme="admin";
    private static String adminLozinka="admin";
    
    /*public LogiraniKorisnik (Integer id, String ime) {
        this.id = new SimpleIntegerProperty (id);
        this.ime = new SimpleStringProperty(ime);
    }
*/
    public LogiraniKorisnik (String lozinka, String ime) {
        this.lozinka = new SimpleStringProperty (lozinka);
        this.ime = new SimpleStringProperty(ime);
    }
    
    public LogiraniKorisnik () {
      
    }
    
    public Integer getId () {
        return this.id;
    }

    
    public void setId(Integer id) {
        this.id =  id;
    }
    

    
    public static boolean logiraj (String kime, String lozinka) {
        Baza db = new Baza();
        PreparedStatement ps = db.exec("SELECT * FROM korisnik WHERE korisnicko_ime =? AND "
                + "lozinka=?");
        try {
            ps.setString(1, kime);
            ps.setString(2, lozinka);
            ResultSet rs = ps.executeQuery();            
            if (rs.next()) {                
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška: "+ex.getMessage());
            return false;
        }
    }

    public static int dajID_logirani(String lozinka,String ime) {
        Baza db = new Baza();
        int idd;
        PreparedStatement ps = db.exec("SELECT * FROM korisnik WHERE korisnicko_ime =? AND "
                + "lozinka=?");
        try {
            ps.setString(1, ime);
            ps.setString(2, lozinka);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               idd=rs.getInt("id"); 
               return idd;
            } 
        } catch (SQLException ex) {
            System.out.println("Nastala je greška: "+ex.getMessage());
        }
        return -1;
    }
    
    public static boolean provjera(String ime, String lozinka){
        if (Objects.equals(ime,adminIme) && Objects.equals(lozinka,adminLozinka))
        return true;
        else return false;
    }
    
}