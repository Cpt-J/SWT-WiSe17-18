package org.verleihsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "verleihgebuehren")
public class Verleihgebuehr {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private double betrag;
	@Enumerated(EnumType.ORDINAL)
	private GebuehrArt gebuehrArt;//achtung! neuanordnung oder erweiterung der enum macht alte daten unbrauchbar - falls das in betracht kommt, sieh zB https://dzone.com/articles/mapping-enums-done-right 
	@OneToOne
	@JoinColumn(name="artikel_id")
	private Artikel artikel;//abweichend von Klassendiagramm hinzugefügt, da unidirektional komplizierter wäre
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getBetrag() {
		return betrag;
	}
	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}
	public GebuehrArt getGebuehrArt() {
		return gebuehrArt;
	}
	public void setGebuehrArt(GebuehrArt gebuehrArt) {
		this.gebuehrArt = gebuehrArt;
	}
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}


}
