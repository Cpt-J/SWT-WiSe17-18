package org.verleihsystem.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "anfrage")
public class Anfrage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private String kommentar ="";
	@ManyToOne
	@JoinColumn(name="artikel_id")
	private Artikel artikel;
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="rueckgabe_termin_id")
	private Termin rueckgabeTermin;
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="ausgabe_termin_id")
	private Termin ausgabeTermin;
	@Enumerated(EnumType.ORDINAL)//achtung! neuanordnung oder erweiterung der enum macht alte daten unbrauchbar - falls das in betracht kommt, sieh zB https://dzone.com/articles/mapping-enums-done-right 
	private Status status;
	@ManyToOne
	@JoinColumn(name="verleiher_id")
	private Benutzer verleiher;
	@ManyToOne
	@JoinColumn(name="ausleiher_id")
	private Benutzer ausleiher;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKommentar() {
		return kommentar;
	}
	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	public Termin getRueckgabeTermin() {
		return rueckgabeTermin;
	}
	public void setRueckgabeTermin(Termin rueckgabeTermin) {
		this.rueckgabeTermin = rueckgabeTermin;
	}
	public Termin getAusgabeTermin() {
		return ausgabeTermin;
	}
	public void setAusgabeTermin(Termin ausgabeTermin) {
		this.ausgabeTermin = ausgabeTermin;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Benutzer getVerleiher() {
		return verleiher;
	}
	public void setVerleiher(Benutzer verleiher) {
		this.verleiher = verleiher;
	}
	public Benutzer getAusleiher() {
		return ausleiher;
	}
	public void setAusleiher(Benutzer ausleiher) {
		this.ausleiher = ausleiher;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anfrage other = (Anfrage) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
