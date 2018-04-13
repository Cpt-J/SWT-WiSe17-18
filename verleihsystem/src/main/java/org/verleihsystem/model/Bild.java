package org.verleihsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


//abweichend von Klassendiagramm hinzugefügt, um attribute atomar zu halten.
@Entity
@Table(name = "bilder")
public class Bild {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Lob//https://blogs.oracle.com/adf/jpa-insert-and-retrieve-clob-and-blob-types
	private Byte[] bild;
	@ManyToOne
	@JoinColumn(name="artikel_id")
	private Artikel artikel;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Byte[] getBild() {
		return bild;
	}
	public void setBild(Byte[] bild) {
		this.bild = bild;
	}
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	
}
