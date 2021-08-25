package com.example.studentskaSluzba;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import static com.example.studentskaSluzba.DB.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class SluzbaControler {
    static DB baza = null;
    static Korisnik k;
    static Predmet p;
    static Prijava pr;


    public void connect() throws SQLException {
        baza = new DB();
        k = baza.korisnik();
        p = baza.predmet();
    }


    @RequestMapping("/admin")
    public String prikaziTabele(Model model) throws SQLException {
        if (baza == null) connect();

        model.addAttribute("listKorisnika", listaKorisnika);
        model.addAttribute("listPredmeta", listaPredmeta);
        return "adminpage";
    }

    //zavrseno
    @GetMapping("/admin/obrisiKorisnika")
    @ResponseBody
    public String obrisiKorisnika(@RequestParam int id) throws SQLException {


        baza.brisanjeKorisnici(id);
        NadjiProfesora(id);
        KorisnikIzListe(id);
        return "";
    }

    ////*****
    @GetMapping("/admin/editKorisnika")
    @ResponseBody
    public String izmeniKorisnika(@RequestParam int id, String imeKorisnika, String prezime, String uloga) throws SQLException {
        baza.izmenaKorisnika(id, imeKorisnika, prezime, uloga);
        String provera = "Profesor";
        for (Korisnik k : listaKorisnika) {

            if (id == k.getID()) {
                if (k.uloga.equals(provera) && (!uloga.equals(provera))) {

                    NadjiProfesora(k.getID());
                }
                if (imeKorisnika != "") {
                    k.setIme(imeKorisnika);
                }
                if (prezime != "") {
                    k.setPrezime(prezime);
                }

                k.setUloga(uloga);

            }
        }
        return "";
    }

    //Zavrseno
    @GetMapping("/admin/obrisiPredmet")
    @ResponseBody
    public String obrisiPredmet(@RequestParam int id) throws SQLException {
        baza.brisanjePredmeta(id);
        PredmetIzListe(id);
        return "";
    }

    /////********
    @GetMapping("/admin/editPredmet")
    @ResponseBody
    public String izmeniPredmet(@RequestParam int id, String nazivPredmeta, String imeNastavnika) throws SQLException {

        Integer profesor;

        for (Predmet p : listaPredmeta) {
            if (id == p.getID()) {

                for (Korisnik k : listaKorisnika) {
                    if (k != null) {
                        String profesorIme = k.getIme() + " " + k.getPrezime();
                        if (profesorIme.equals(imeNastavnika)) {
                            profesor = k.getID();
                            baza.izmenaPredmeta(id, nazivPredmeta, profesor);
                            break;
                        }


                    }
                }

                if (nazivPredmeta != "") {
                    p.setImePredmeta(nazivPredmeta);
                }
                if (imeNastavnika != "") {
                    p.setImeProfesora(imeNastavnika);
                }

            }
        }
        return "";
    }

    @GetMapping("/admin/dodajKorisnika")
    @ResponseBody
    public String dodajKorisnika(@RequestParam String imeKorisnika, String prezime, String uloga) throws SQLException {
        baza.dodajKorisnika(imeKorisnika, prezime, uloga);
        int id = 0;

        id = baza.identifikatorK++;
        Korisnik korisnik = new Korisnik(id + 1, imeKorisnika, prezime, uloga);
        listaKorisnika.add(korisnik);
        return "";
    }

    ////*******
    @GetMapping("/admin/dodajPredmet")
    @ResponseBody
    public String dodajPredmet(@RequestParam String nazivPredmeta, String imeNastavnika, String prezimeNastavnika) throws SQLException {
        int profesor = 0;
        String nastavnik = imeNastavnika + " " + prezimeNastavnika;
        for (Korisnik k : listaKorisnika) {

            if (k != null && k.getUloga().equals("Profesor") && k.getIme().equals(imeNastavnika) && k.getPrezime().equals(prezimeNastavnika)) {
                profesor = k.getID();
            }
        }


        baza.dodavanjePredmeta(nazivPredmeta, profesor);
        int id = 0;

        id = baza.identifikatorP++;
        Predmet predmet = new Predmet(id + 1, nazivPredmeta, nastavnik);
        listaPredmeta.add(predmet);

        return "";
    }

    public void KorisnikIzListe(int id) {
        for (Korisnik k : listaKorisnika
        ) {
            if (k != null && k.getID() == id) {

                listaKorisnika.remove(k);
                break;

            }
        }
    }

    public void PredmetIzListe(int id) {
        for (Predmet p : listaPredmeta
        ) {
            if (p != null && p.getID() == id) {

                listaPredmeta.remove(p);
                break;
            }
        }
    }

    private void NadjiProfesora(int id) throws SQLException {
        String imeProfesora = null;
        Integer indikator = null;

        for (Korisnik k : listaKorisnika) {
            if (k != null && k.ID == id) {
                imeProfesora = k.getIme() + " " + k.getPrezime();
                indikator = k.getID();
                break;
            }
        }
        SvePredmete(imeProfesora, indikator);

    }

    private void SvePredmete(String imeProfesora, int id) throws SQLException {
        for (Iterator<Predmet> iterator = listaPredmeta.iterator(); iterator.hasNext(); ) {
            Predmet value = iterator.next();
            if (value.imeProfesora.equals(imeProfesora)) {
                baza.brisanjeKorisnici(id);
                iterator.remove();
            }
        }
    }


    /***********************************************************************/

    /*  DODATO U DRUGOM DELU */
    @RequestMapping("/student{id}")
    public String prikaziTabeleStudent(@PathVariable("id") int id, Model model) throws SQLException {
        // System.out.println(id);
        String korisnik = "";
        if (baza == null) connect();
        listaPrijava.clear();
        listaPredmetaSlusanje.clear();

        baza.prikaziPrijave(id);
        baza.predmetiSlusanje(id);

        for (Korisnik k : listaKorisnika) {
            if (k != null && k.getID() == id && k.getUloga().equals("Student")) {
                korisnik = k.getIme() + " " + k.getPrezime();
            }
        }

        model.addAttribute("trenutniKorisnik", korisnik);
        model.addAttribute("listPredmeta", listaPredmeta);
        model.addAttribute("listPrijava", listaPrijava);
        model.addAttribute("listSlusanje", listaPredmetaSlusanje);

        return "studentpage";
    }

    @GetMapping("/student{id}/obrisiPredmetStudent")
    @ResponseBody
    public String obrisiPredmetStudent(@PathVariable("id") int id, @RequestParam int idPredmeta) throws SQLException {
        baza.obrisiPredmetSlusanje(id, idPredmeta);


        for (Predmet p : listaPredmetaSlusanje) {
            if (p != null && p.getID() == idPredmeta) {
                listaPredmetaSlusanje.remove(p);
                break;
            }


            //System.out.println(idPredmeta);
        }
        return "";
    }

    @GetMapping("/student{id}/izaberiPredmetStudent")
    @ResponseBody
    public String izaberiPredmetStudent(@PathVariable("id") int id, @RequestParam String nazivPredmeta, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int idPredmeta = 0;
        int nastavnik = 0;
        String profesor = null;
        for (Predmet p : listaPredmeta) {
            if (p != null && p.getImePredmeta().equals(nazivPredmeta)) {
                idPredmeta = p.getID();
                profesor = p.getImeProfesora();
                break;
            }
        }

        for (Korisnik k : listaKorisnika) {
            String ime = k.getIme() + " " + k.getPrezime();
            if (k != null && ime.equals(profesor)) {
                nastavnik = k.getID();
            }
        }

        if (baza.dodajPredmetSlusanje(id, idPredmeta, nazivPredmeta, nastavnik)) {
            Predmet pr = new Predmet(idPredmeta, nazivPredmeta, profesor);
            listaPredmetaSlusanje.add(pr);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return "";
    }

    @GetMapping("/student{id}/prijaviIspitStudent")
    @ResponseBody
    public String prijaviIspitStudent(@PathVariable("id") int id, @RequestParam int idPredmeta, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String nazivIspita = null;
        String nastavnik = null;
        int idPrijave = 0;
        for (Predmet p : listaPredmeta) {
            if (p != null && p.getID() == idPredmeta) {
                nazivIspita = p.getImePredmeta();
                nastavnik = p.getImeProfesora();
                break;
            }
        }

        for (Prijava p : listaPrijava) {
            if (idPrijave < p.getID()) {
                idPrijave = p.getID();
            }
        }

        if (baza.prijaviIspit(id, idPredmeta, nastavnik, nazivIspita)) {
            Prijava pr = new Prijava(idPrijave++, id, nastavnik, "Neocenjen", nazivIspita, idPredmeta);
            listaPrijava.add(pr);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return "";
    }

    @GetMapping("/student{id}/prikaziPrijave")
    @ResponseBody
    public String prikaziPrijave(@PathVariable("id") int id) throws SQLException {
        baza.prikaziPrijave(id);

        return "";
    }


    /***********************************************************************/

    /*  DODATO U TRECEM DELU */
    @RequestMapping("/profesor{id}")
    public String prikaziTabeleProfesor(@PathVariable("id") int id, Model model) throws SQLException {
        if (baza == null) connect();
        String ime = null;
        String korisnik = "";

        for (Korisnik k : listaKorisnika) {
            if (k.getID() == id)
                ime = k.getIme() + " " + k.getPrezime();
        }
        //listaProfesorPredmet.clear();
        for (Predmet p : listaPredmeta) {
            if (ime.equals(p.getImeProfesora()))
                listaProfesorPredmet.add(p);
        }

        //listaProfesorOcenjivanje.clear();
        for (Prijava p : listaPrijava) {
            if (ime.equals(p.getProfesor()))
                listaProfesorOcenjivanje.add(p);
        }

        for (Korisnik k : listaKorisnika) {
            if (k != null && k.getID() == id && k.getUloga().equals("Profesor")) {
                korisnik = k.getIme() + " " + k.getPrezime();
            }
        }

        listaPrijava.clear();
        baza.prikaziPrijaveProfesor(id);
        for (Prijava p : listaPrijava) {
            for (Korisnik k : listaKorisnika) {
                if (k != null && p.idStudenta == k.ID) {
                    p.profesor = k.getIme() + " " + k.getPrezime();
                }
            }
        }

        for (Prijava p:
             listaPrijava) {
            if (p == null) System.out.println("null");
            System.out.println(p.nazivPredmeta + p.profesor + p.ocena);
        }

        model.addAttribute("listOcenjivanja", listaPrijava);
        model.addAttribute("trenutniKorisnik", korisnik);
        model.addAttribute("listPredmeta", listaPredmeta);


        return "profesorpage";
    }

    @GetMapping("/profesor{id}/prikaziPrijaveProfesor")
    @ResponseBody
    public String prikaziPrijaveProfesor(@PathVariable("id") int id, Model model) throws SQLException {
        String korisnik = "";
        if (baza == null) connect();
        listaPrijava.clear();
        baza.prikaziPrijaveProfesor(1);

        return " ";
    }


    @GetMapping("/profesor{id}/izmeniOcenuProfesor")
    @ResponseBody
    public String izmeniOcenuProfesor(@PathVariable("id") int idProfesora, @RequestParam int idStudenta, int idPredmeta, int ocena) throws SQLException {
        System.out.println(idProfesora);
        System.out.println(idStudenta);
        System.out.println(idPredmeta);
        System.out.println(ocena);

        baza.upisiOcenu(idPredmeta, idStudenta, ocena);

        String nazivPredmeta = null;
        for (Predmet predmet : listaPredmeta) {
            if (predmet.getID() == idPredmeta) {
                nazivPredmeta = predmet.getImePredmeta();
                break;
            }
        }
        for (Prijava p : listaPrijava) {
            if (p.idStudenta == idStudenta && p.nazivPredmeta == nazivPredmeta) {
                p.ocena = ocena + "";
            }
        }
        return "";
    }
}