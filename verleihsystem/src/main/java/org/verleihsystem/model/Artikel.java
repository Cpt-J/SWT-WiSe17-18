package org.verleihsystem.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "artikel")
public class Artikel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;
	@NotNull
	private String beschreibung;
	@NotNull
	private String technischeDaten;
	@OneToMany(cascade= CascadeType.ALL,mappedBy="artikel",orphanRemoval=true)
	private List<Ort> uebergabeorte=new ArrayList<>();
	@OneToOne(mappedBy = "artikel", cascade=CascadeType.ALL,orphanRemoval=true)
	private Pfand pfand;
	@OneToOne(mappedBy="artikel", cascade=CascadeType.ALL,orphanRemoval=true)
	private Verleihgebuehr gebuehr;
	@ManyToOne
	@JoinColumn(name="kategorie_id")
	private Artikelkategorie kategorie;
	@Enumerated(EnumType.ORDINAL)
	private Artikelzustand zustand;//achtung! neuanordnung oder erweiterung der enum macht alte daten unbrauchbar - falls das in betracht kommt, sieh zB https://dzone.com/articles/mapping-enums-done-right 
	@OneToMany(cascade= CascadeType.ALL,mappedBy="artikel",orphanRemoval=true)
	private List<Bild> bilder=new ArrayList<>();
	@OneToMany(mappedBy="artikel",cascade= CascadeType.ALL,orphanRemoval=true)//falls Verleiher Artikel löschen können soll, muss "gelöscht"-flag eingefügt werden, endgültige löschung erst nachdem anfragen genügend in vergangeheit liegen (fristen)
	private List<Anfrage> anfragen;//abweichend von Klassendiagramm hinzugefügt (für Berechnung der Verfügbarkeit hilfreich)
	@ManyToOne
	@JoinColumn(name="verleiher_id")
	private Benutzer verleiher;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public String getTechnischeDaten() {
		return technischeDaten;
	}
	public void setTechnischeDaten(String technischeDaten) {
		this.technischeDaten = technischeDaten;
	}
	public List<Ort> getUebergabeorte() {
		return uebergabeorte;
	}
	public void setUebergabeorte(List<Ort> uebergabeorte) {
		this.uebergabeorte = uebergabeorte;
	}
	public Pfand getPfand() {
		return pfand;
	}
	public void setPfand(Pfand pfand) {
		this.pfand = pfand;
	}
	public Verleihgebuehr getGebuehr() {
		return gebuehr;
	}
	public void setGebuehr(Verleihgebuehr gebuehr) {
		this.gebuehr = gebuehr;
	}
	public Artikelkategorie getKategorie() {
		return kategorie;
	}
	public void setKategorie(Artikelkategorie kategorie) {
		this.kategorie = kategorie;
	}
	public Artikelzustand getZustand() {
		return zustand;
	}
	public void setZustand(Artikelzustand zustand) {
		this.zustand = zustand;
	}
	public List<Bild> getBilder() {
		return bilder;
	}
	public void setBilder(List<Bild> bilder) {
		this.bilder = bilder;
	}
	public List<Anfrage> getAnfragen() {
		return anfragen;
	}
	public void setAnfragen(List<Anfrage> anfragen) {
		this.anfragen = anfragen;
	}
	public Benutzer getVerleiher() {
		return verleiher;
	}
	public void setVerleiher(Benutzer verleiher) {
		this.verleiher = verleiher;
	}
	


}
