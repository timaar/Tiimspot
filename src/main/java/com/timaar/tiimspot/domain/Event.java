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

import com.timaar.tiimspot.domain.enumeration.EventType;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "naam", nullable = false)
    private String naam;

    @NotNull
    @Column(name = "datum", nullable = false)
    private ZonedDateTime datum;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EventType type;

    @OneToOne    private Adres adres;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersoonEvent> persoonEvents = new HashSet<>();

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres Adres) {
        this.adres = Adres;
    }

    public Set<PersoonEvent> getPersoonEvents() {
        return persoonEvents;
    }

    public void setPersoonEvents(Set<PersoonEvent> PersoonEvents) {
        this.persoonEvents = PersoonEvents;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> Comments) {
        this.comments = Comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", naam='" + naam + "'" +
            ", datum='" + datum + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
