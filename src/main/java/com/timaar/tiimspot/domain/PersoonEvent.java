package com.timaar.tiimspot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.timaar.tiimspot.domain.enumeration.AanwezigheidsStatus;

/**
 * A PersoonEvent.
 */
@Entity
@Table(name = "persoon_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "persoonevent")
public class PersoonEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "aanwezigheids_status", nullable = false)
    private AanwezigheidsStatus aanwezigheidsStatus;

    @OneToOne    private EventScore eventScore;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "persoon_id")
    private Persoon persoon;

    @OneToMany(mappedBy = "persoonEvent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AanwezigheidsStatus getAanwezigheidsStatus() {
        return aanwezigheidsStatus;
    }

    public void setAanwezigheidsStatus(AanwezigheidsStatus aanwezigheidsStatus) {
        this.aanwezigheidsStatus = aanwezigheidsStatus;
    }

    public EventScore getEventScore() {
        return eventScore;
    }

    public void setEventScore(EventScore EventScore) {
        this.eventScore = EventScore;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event Event) {
        this.event = Event;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon Persoon) {
        this.persoon = Persoon;
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
        PersoonEvent persoonEvent = (PersoonEvent) o;
        return Objects.equals(id, persoonEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersoonEvent{" +
            "id=" + id +
            ", aanwezigheidsStatus='" + aanwezigheidsStatus + "'" +
            '}';
    }
}
