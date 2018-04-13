package org.verleihsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pfand")
public class Pfand {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private double pfandBetrag;
	@NotNull
	private String pfandGegenstand;
	@Enumerated(EnumType.ORDINAL)
	private PfandArt pfandArt;//achtung! neuanordnung oder erweiterung der enum macht alte daten unbrauchbar - falls das in betracht kommt, sieh zB https://dzone.com/articles/mapping-enums-done-right 
	@OneToOne
	@JoinColumn(name="artikel_id")
	private Artikel artikel;//abweichend von Klassendiagramm hinzugefügt, da unidirektional komplizierter wäre
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getPfandBetrag() {
		return pfandBetrag;
	}
	public void setPfandBetrag(double pfandBetrag) {
		this.pfandBetrag = pfandBetrag;
	}
	public String getPfandGegenstand() {
		return pfandGegenstand;
	}
	public void setPfandGegenstand(String pfandGegenstand) {
		this.pfandGegenstand = pfandGegenstand;
	}
	public PfandArt getPfandArt() {
		return pfandArt;
	}
	public void setPfandArt(PfandArt pfandArt) {
		this.pfandArt = pfandArt;
	}
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}



}
