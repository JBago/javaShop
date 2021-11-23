/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appproject.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author RB computer
 */
public class Konekcija {
    private String host;
    private String korisnik;
    private String lozinka;
    private String baza;

    protected Connection konekcija;

    public Konekcija () {
        this.host = "localhost";
        this.korisnik = "root";
        this.lozinka = "";
        this.baza = "puj";
        this.spoji();
    }

    public Konekcija (String host, String korisnik, String lozinka, String baza) {
        this.host = host;
        this.korisnik = korisnik;
        this.lozinka = lozinka;
        this.baza = baza;
        this.spoji();
    }

    public void spoji () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.konekcija = DriverManager.getConnection("jdbc:mysql://"+this.host+"/"+this.baza+"?"
                    + "user="+this.korisnik+"&password="+this.lozinka);
        } catch (ClassNotFoundException e) {
            System.out.println ("Sustav nije uspio pronaći klasu za konekciju na MYSQL...");
        } catch (SQLException e) {
            System.out.println ("Sustav nije se mogao spojiti na bazu podataka...");
        }
    }

    public void odspoji () {
        try {
            this.konekcija.close();
        } catch (SQLException e) {
            System.out.println ("Sustav nije uspio zatvoriti konekciju...");
        }
    }

}