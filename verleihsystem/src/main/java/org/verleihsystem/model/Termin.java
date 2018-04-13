package org.verleihsystem.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "termine")
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "ort_id")
    private Ort ort;//übergabeort

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date datum;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull
    private Time uhrzeit;

    @Transient
    private String datumString;

    @Transient
    private String uhrzeitString;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Ort getOrt() {
        return ort;
    }

    public void setOrt(Ort ort) {
        this.ort = ort;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Time getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(Time uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    public String getUhrzeitString() {
        return uhrzeitString;
    }

    public void setUhrzeitString(String uhrzeitString) {
        this.uhrzeitString = uhrzeitString;
    }

    public String getDatumString() {
        return datumString;
    }

    public void setDatumString(String datumString) {
        this.datumString = datumString;
    }
}
