package com.timaar.tiimspot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.timaar.tiimspot.domain.enumeration.Geslacht;

/**
 * A Persoon.
 */
@Entity
@Table(name = "persoon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "persoon")
public class Persoon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "voornaam", nullable = false)
    private String voornaam;

    @NotNull
    @Column(name = "naam", nullable = false)
    private String naam;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "geslacht", nullable = false)
    private Geslacht geslacht;

    @NotNull
    @Column(name = "geboorte_datum", nullable = false)
    private ZonedDateTime geboorteDatum;

    @Column(name = "telefoonnummer")
    private String telefoonnummer;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)    private Adres adres;

    @OneToMany(mappedBy = "persoon",cascade = CascadeType.ALL)    
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "persoon_ouder",
               joinColumns = @JoinColumn(name="persoons_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="ouders_id", referencedColumnName="ID"))
    private Set<Ouder> ouders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Geslacht getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(Geslacht geslacht) {
        this.geslacht = geslacht;
    }

    public ZonedDateTime getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(ZonedDateTime geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres Adres) {
        this.adres = Adres;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Ouder> getOuders() {
        return ouders;
    }

    public void setOuders(Set<Ouder> ouders) {
        this.ouders = ouders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Persoon persoon = (Persoon) o;
        return Objects.equals(id, persoon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Persoon{" +
            "id=" + id +
            ", voornaam='" + voornaam + "'" +
            ", naam='" + naam + "'" +
            ", geslacht='" + geslacht + "'" +
            ", geboorteDatum='" + geboorteDatum + "'" +
            ", telefoonnummer='" + telefoonnummer + "'" +
            '}';
    }
}
