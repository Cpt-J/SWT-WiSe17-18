package org.verleihsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.verleihsystem.dao.*;
import org.verleihsystem.model.*;

import javax.servlet.http.HttpSession;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

@SessionAttributes("art")
@Controller
public class MainController extends WebMvcConfigurerAdapter {

    private static final String F23 = "Anfangsdatum muss vor Rueckgabedatum liegen";
    private static final String F24 = "Zeitraum befindet sich vor aktuellem Datum";
    private static final String F25 = "Uebergabe-Datum muss angegeben werden";
    private static final String F26 = "Uebergabe-Uhrzeit muss angegeben werden";
    private static final String F27 = "Uebergabeort muss aus der Liste gewaehlt werden";

    @Autowired
    private BenutzerDAO benutzerDAO;
    @Autowired
    private ArtikelDAO artikelDAO;
    @Autowired
    private AnfrageDAO anfrageDAO;

    @Autowired
    private TerminDAO terminDAO;

    @Autowired
    private PfandDAO pfandDAO;

    @Autowired
    private OrtDAO ortDAO;

    @Autowired
    private VerleihgebuehrDAO artikelgebuehrDAO;

    @Autowired
    private ArtikelkategorieDAO artikelkategorieDAO;


    @GetMapping("/insertTestData")
    public String insertTestData() {
        benutzerDAO.deleteAll();
        artikelkategorieDAO.deleteAll();
        artikelDAO.deleteAll();
        pfandDAO.deleteAll();
        ortDAO.deleteAll();
        Benutzer ausleiher = new Benutzer();
        ausleiher.setBenutzername("ausleiher");
        ausleiher.setEmailAdresse("g");
        ausleiher.setPasswort("pass");
        ausleiher.setLetzteAktivitätDatum(new Date(5));
        ausleiher.setLetzteAktivitätZeit(new Time(5));

        Benutzer verleiher = new Benutzer();
        verleiher.setBenutzername("verleiher");
        verleiher.setEmailAdresse("g");
        verleiher.setPasswort("pass");
        verleiher.setLetzteAktivitätDatum(new Date(5));
        verleiher.setLetzteAktivitätZeit(new Time(5));
        benutzerDAO.save(ausleiher);
        benutzerDAO.save(verleiher);
        Artikelkategorie artkat = new Artikelkategorie();
        artkat.setBezeichnung("Werkzeug");
        artikelkategorieDAO.save(artkat);
        Artikel artikel = new Artikel();
        artikel.setName("Bohrmaschine");
        artikel.setBeschreibung("Bohrmaschine mit Torx, Schlitz, Imbus Aufsätzen");
        artikel.setTechnischeDaten("1234,567");
        artikel.setKategorie(artkat);
        artikel.setVerleiher(verleiher);
        artikel.setZustand(Artikelzustand.wenigGebraucht);
        artikelDAO.save(artikel);
        Pfand pfand = new Pfand();
        pfand.setArtikel(artikel);
        pfand.setPfandArt(PfandArt.pfandGegenstand);
        pfand.setPfandBetrag(20);
        pfand.setPfandGegenstand("Personalausweis");
        pfandDAO.save(pfand);
        Ort see = new Ort();
        see.setStrasse("Musterstraße");
        see.setStadt("Lübeck");
        see.setPlz("23562");
        see.setNummer("123");
        see.setBeschreibung("Am Ende der Straße links am See");
        see.setArtikel(artikel);
        Ort wiese = new Ort();
        wiese.setStrasse("Burgstraße");
        wiese.setStadt("Lübeck");
        wiese.setPlz("23562");
        wiese.setNummer("12");
        wiese.setBeschreibung("Am Ende der Straße rechts auf der Wiese");
        wiese.setArtikel(artikel);
        Ort supermarkt = new Ort();
        supermarkt.setStrasse("Burgstraße");
        supermarkt.setStadt("Lübeck");
        supermarkt.setPlz("23562");
        supermarkt.setNummer("1");
        supermarkt.setBeschreibung("Am Ende der Straße auf dem Parkplatz vom Supermarkt");
        supermarkt.setArtikel(artikel);
        ortDAO.save(see);
        ortDAO.save(wiese);
        ortDAO.save(supermarkt);

        //
        //Artikel artikel = artikelDAO.findOne(id);
        Anfrage anfrage = new Anfrage();
        anfrage.setArtikel(artikel);
        anfrage.setAusleiher(ausleiher);
        anfrage.setVerleiher(verleiher);
        anfrage.setKommentar("Ausleiher:comments not Implemented here\n");
        Termin ausgabeTermin = new Termin();
        Termin rückgabeTermin = new Termin();
        ausgabeTermin.setOrt(see);
        rückgabeTermin.setOrt(wiese);
        ausgabeTermin.setDatum(new Date(5));
        rückgabeTermin.setDatum(new Date(10));
        ausgabeTermin.setUhrzeit(new Time(5));
        rückgabeTermin.setUhrzeit(new Time(5));
        terminDAO.save(ausgabeTermin);
        terminDAO.save(rückgabeTermin);
        anfrage.setAusgabeTermin(ausgabeTermin);
        anfrage.setRueckgabeTermin(rückgabeTermin);
        anfrage.setStatus(Status.offen);
        anfrageDAO.save(anfrage);

        return "redirect:/dashboard";
    }

    @GetMapping("/artikel/{artikelId}")
    public String artikelDetailansicht(@PathVariable("artikelId") long id, HttpSession session, Model model) {
        Artikel artikel = this.artikelDAO.findOne(id);
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if(user != null)
            model.addAttribute("login", user.isAngemeldet());
        if(artikel.getVerleiher().getBenutzername().equals(user.getBenutzername())){
            model.addAttribute("login", false);
        }
        model.addAttribute("artikel", artikel);

        return "artikel";
    }


    @GetMapping("/ausleihanfrage/{artikelId}")
    public String anfrageErstellen(@PathVariable("artikelId") long id, Model model, HttpSession session, @ModelAttribute Anfrage anfrage) {
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if(user == null || !user.isAngemeldet()){
            return "redirect:/login";
        }
        Artikel artikel = artikelDAO.findOne(id);
        if (artikel == null) return "redirect:/dashboard";
        model.addAttribute("artikel", artikel);
        return ("ausleihanfrage");
    }

    @PostMapping("/ausleihanfrage/{artikelId}")
    public String anfrageErstellen(@PathVariable("artikelId") long id, HttpSession session, @ModelAttribute Anfrage anfrage, Model model) {
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if(user == null || !user.isAngemeldet()){
            return "redirect/login";
        }

        String t;
        String error1 = "";
        String error2 = "";
        String error3 = "";
        String error4 = "";

        //ausgebedatum eingegeben?
        if ((t = anfrage.getAusgabeTermin().getDatumString()) == null || t.length() != 10) {
            error1 = F25;
            model.addAttribute("date1", true);
        } else {
            anfrage.getAusgabeTermin().setDatum(Date.valueOf(t));
        }

        //rückgabedatum eingegeben?
        if ((t = anfrage.getRueckgabeTermin().getDatumString()) == null || t.length() != 10) {
            error1 = F25;
            model.addAttribute("date2", true);
        } else {
            anfrage.getRueckgabeTermin().setDatum(Date.valueOf(t));
        }

        //ausgabeUhrzeit eingegeben?
        if ((t = anfrage.getAusgabeTermin().getUhrzeitString()) == null || t.length() != 5) {
            error2 = F26;
            model.addAttribute("time1", true);

        } else {
            anfrage.getAusgabeTermin().setUhrzeit(Time.valueOf(t + ":00"));
        }

        //rückgabeuhrzeit eingegeben?
        if ((t = anfrage.getRueckgabeTermin().getUhrzeitString()) == null || t.length() != 5) {
            error2 = F26;
            model.addAttribute("time2", true);
        } else {
            anfrage.getRueckgabeTermin().setUhrzeit(Time.valueOf(t + ":00"));
        }

        //uebergabeort gewählt?
        if (anfrage.getAusgabeTermin().getOrt() == null) {
            error3 += F27;
            model.addAttribute("ort1", true);
        }

        //rückgabeort gewählt?
        if (anfrage.getRueckgabeTermin().getOrt() == null) {
            error4 += F27;
            model.addAttribute("ort2", true);
        }

        //Beide Datumeingaben getätigt
        if (error1.length() == 0) {
            //Ausleihdatum vor rückgabedatum?
            if (anfrage.getAusgabeTermin().getDatum().after(anfrage.getRueckgabeTermin().getDatum()) || anfrage.getAusgabeTermin().getDatumString().equals(anfrage.getRueckgabeTermin().getDatumString())) {
                model.addAttribute("date1", true);
                model.addAttribute("date2", true);
                error1 += F23;
            }
            //ausleihdatum in der vergangenheit?
            if (anfrage.getAusgabeTermin().getDatum().before(new Date(System.currentTimeMillis()))) {
                model.addAttribute("date1", true);
                if (error1.length() != 0) error1 += "<br/>";
                error1 += F24;
            }
        }

        Artikel artikel = artikelDAO.findOne(id);

        if (error1.length() != 0 || error2.length() != 0 || error3.length() != 0 || error4.length() != 0) {
            model.addAttribute("error1", error1);
            model.addAttribute("error2", error2);
            model.addAttribute("error3", error3);
            model.addAttribute("error4", error4);
            model.addAttribute("artikel", artikel);
            return "ausleihanfrage";
        }


        anfrage.setArtikel(artikel);
        anfrage.setStatus(Status.offen);
        anfrage.setAusleiher(user);
        anfrage.setVerleiher(artikel.getVerleiher());
        user.getGesendeteAnfragen().add(anfrage);
        anfrageDAO.save(anfrage);
        System.out.println(anfrage.getAusgabeTermin().getUhrzeitString() + " " + anfrage.getAusgabeTermin().getDatumString() + "    "
                + anfrage.getRueckgabeTermin().getUhrzeitString() + "  " + anfrage.getRueckgabeTermin().getDatumString());

        return ("redirect:/dashboard");
    }

    public List<Blob> artikelEintragen(String name, String technischeDaten, String beschreibung, List<Blob> bilder,
                                       Artikelkategorie kategorie, List<Ort> orte) {
        return null;
    }


    @RequestMapping("/")
    public String home(HttpSession session) {
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if (user == null || !user.isAngemeldet())
            return "redirect:/login";
        return "redirect:/dashboard";
    }

    @GetMapping("/anfrage/{anfrageId}")
    public String Anfragedetails(@PathVariable("anfrageId") long id, HttpSession session, Model model) {
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if (user == null || !user.isAngemeldet()) return "redirect:/login";
        Anfrage anfrage = anfrageDAO.findOne(id);
        if (user.getEmpfangeneAnfragen().contains(anfrage)) {
            model.addAttribute("verleiher", true);
        }
        if (user.getGesendeteAnfragen().contains(anfrage)) {
            model.addAttribute("ausleiher", true);
        } else if(!user.getEmpfangeneAnfragen().contains(anfrage)) {
            return "redirect:/dashboard";
        }
        model.addAttribute("anfrage", anfrage);
        return "buchen";
    }

    @GetMapping("/buchen/{anfrageId}")
    public String buchen(@PathVariable("anfrageId") long id, HttpSession session) {
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if (user == null || !user.isAngemeldet()) return "redirect:/login";
        Anfrage anfrage = anfrageDAO.findOne(id);
        if (!user.getGesendeteAnfragen().contains(anfrage)) return "redirect:/dashboard";
        anfrage.setStatus(Status.gebucht);
        anfrageDAO.save(anfrage);
        return "redirect:/dashboard";
    }

    @GetMapping("/bestaetigen/{anfrageId}")
    public String bestaetigen(@PathVariable("anfrageId") long id, HttpSession session) {
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if (user == null || !user.isAngemeldet()) return "redirect:/login";
        Anfrage anfrage = anfrageDAO.findOne(id);
        if (!user.getEmpfangeneAnfragen().contains(anfrage)) return "redirect:/dashboard";
        anfrage.setStatus(Status.bestaetigt);
        anfrageDAO.save(anfrage);
        return "redirect:/dashboard";
    }

    @PostMapping("/bestaetigen/{anfrageId}")
    public String bestaetigen(@PathVariable("anfrageId") long id, HttpSession session,@ModelAttribute Anfrage anfrage){
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if (user == null || !user.isAngemeldet())return "redirect:/login";
        Anfrage dbAnfrage = anfrageDAO.findOne(id);
        if(!user.getEmpfangeneAnfragen().contains(dbAnfrage))return "redirect:/dashboard";
        dbAnfrage.setStatus(Status.bestaetigt);
        dbAnfrage.setKommentar(anfrage.getKommentar());
        anfrageDAO.save(dbAnfrage);
        return "redirect:/dashboard";
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if (user == null || !user.isAngemeldet()) {
            return "redirect:login";
        }
        Benutzer dbUser= benutzerDAO.getBenutzerByBenutzername(user.getBenutzername());
        model.addAttribute("artikelliste", dbUser.getArtikel());
        model.addAttribute("gesendeteAnfragen", dbUser.getGesendeteAnfragen());
        model.addAttribute("empfangeneAnfragen", dbUser.getEmpfangeneAnfragen());
        return "dashboard";
    }


    @GetMapping("/login")
    public String showLogin(@ModelAttribute Benutzer benutzer, HttpSession session) {
        if (session.getAttribute("benuzter") != null && ((Benutzer) session.getAttribute("benutzer")).isAngemeldet()) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute Benutzer benutzer, Model model, HttpSession session) {
        Benutzer foundUser = benutzerDAO.getBenutzerByBenutzername(benutzer.getBenutzername());

        if (foundUser == null || !foundUser.getPasswort().equals(benutzer.getPasswort())) {
            model.addAttribute("failure", true);
            return "login";
        } else {
            foundUser.setAngemeldet(true);
            session.setAttribute("benutzer", foundUser);
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        Benutzer user = (Benutzer) session.getAttribute("benutzer");
        if (user != null) {
            session.removeAttribute("benutzer");
            user.setAngemeldet(false);
        }
        return "redirect:/login";
    }

    @RequestMapping("/addArtikel")
    public String addArtikel() {
        return "redirect:dashboard";
    }

    @RequestMapping("/suche")
    public String suche(Model model) {
        model.addAttribute("artikelliste", artikelDAO.findAll());
        return "suche";
    }

}
