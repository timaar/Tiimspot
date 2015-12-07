package com.timaar.tiimspot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Adres.
 */
@Entity
@Table(name = "adres")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adres")
public class Adres implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "straat", nullable = false)
    private String straat;

    @NotNull
    @Column(name = "huisnummer", nullable = false)
    private String huisnummer;

    @Column(name = "busnummer")
    private String busnummer;

    @NotNull
    @Column(name = "postcode", nullable = false)
    private String postcode;

    @NotNull
    @Column(name = "gemeente", nullable = false)
    private String gemeente;

    @NotNull
    @Column(name = "land_iso3", nullable = false)
    private String landISO3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getBusnummer() {
        return busnummer;
    }

    public void setBusnummer(String busnummer) {
        this.busnummer = busnummer;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getLandISO3() {
        return landISO3;
    }

    public void setLandISO3(String landISO3) {
        this.landISO3 = landISO3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Adres adres = (Adres) o;
        return Objects.equals(id, adres.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Adres{" +
            "id=" + id +
            ", straat='" + straat + "'" +
            ", huisnummer='" + huisnummer + "'" +
            ", busnummer='" + busnummer + "'" +
            ", postcode='" + postcode + "'" +
            ", gemeente='" + gemeente + "'" +
            ", landISO3='" + landISO3 + "'" +
            '}';
    }
}
