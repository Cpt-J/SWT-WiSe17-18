package org.verleihsystem.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "benutzer")
public class Benutzer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
    @Column(unique = true)
	private String benutzername;
	@NotNull
	private String emailAdresse;
	@NotNull
	private String passwort;
	@NotNull
	private Date letzteAktivitätDatum;
	@NotNull
	private Time letzteAktivitätZeit;
	@NotNull
	private boolean angemeldet;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "verleiher", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Anfrage> empfangeneAnfragen=new ArrayList<>();//abweichend von Klassendiagramm attribut-name geändert - wegen Javanamenskonvention

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "ausleiher", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Anfrage> gesendeteAnfragen=new ArrayList<>();//abweichend von Klassendiagramm attribut-name geändert - wegen Javanamenskonvention

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "verleiher", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Artikel> artikel=new ArrayList<>();
	
	public void anmelden() {

	}

	public void abmelden() {

	}

	public boolean artikelEintragen(Artikel artikel) {
		return false;
	}

	public boolean anfrageStellen(Artikel artikel) {
		return false;
	}

	public boolean buchen(Anfrage anfrage) {
		return false;
	}

	public boolean anfrageBearbeiten(Anfrage anfrage, Status status, String kommentar) {
		return false;
	}

	public boolean stornieren(Anfrage anfrage) {
		return false;
	}

	public boolean loeschen(String passwort) {
		return false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getEmailAdresse() {
		return emailAdresse;
	}

	public void setEmailAdresse(String emailAdresse) {
		this.emailAdresse = emailAdresse;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Date getLetzteAktivitätDatum() {
		return letzteAktivitätDatum;
	}

	public void setLetzteAktivitätDatum(Date letzteAktivitätDatum) {
		this.letzteAktivitätDatum = letzteAktivitätDatum;
	}

	public Time getLetzteAktivitätZeit() {
		return letzteAktivitätZeit;
	}

	public void setLetzteAktivitätZeit(Time letzteAktivitätZeit) {
		this.letzteAktivitätZeit = letzteAktivitätZeit;
	}

	public boolean isAngemeldet() {
		return angemeldet;
	}

	public void setAngemeldet(boolean angemeldet) {
		this.angemeldet = angemeldet;
	}

	public List<Anfrage> getEmpfangeneAnfragen() {
		return empfangeneAnfragen;
	}

	public void setEmpfangeneAnfragen(List<Anfrage> empfangeneAnfragen) {
		this.empfangeneAnfragen = empfangeneAnfragen;
	}

	public List<Anfrage> getGesendeteAnfragen() {
		return gesendeteAnfragen;
	}

	public void setGesendeteAnfragen(List<Anfrage> gesendeteAnfragen) {
		this.gesendeteAnfragen = gesendeteAnfragen;
	}

	public List<Artikel> getArtikel() {
		return artikel;
	}

	public void setArtikel(List<Artikel> artikel) {
		this.artikel = artikel;
	}



}
