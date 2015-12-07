package com.timaar.tiimspot.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.timaar.tiimspot.domain.enumeration.Score;

/**
 * A EventScore.
 */
@Entity
@Table(name = "event_score")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eventscore")
public class EventScore implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "discipline")
    private Score discipline;

    @Enumerated(EnumType.STRING)
    @Column(name = "techniek")
    private Score techniek;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Score getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Score discipline) {
        this.discipline = discipline;
    }

    public Score getTechniek() {
        return techniek;
    }

    public void setTechniek(Score techniek) {
        this.techniek = techniek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventScore eventScore = (EventScore) o;
        return Objects.equals(id, eventScore.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EventScore{" +
            "id=" + id +
            ", discipline='" + discipline + "'" +
            ", techniek='" + techniek + "'" +
            '}';
    }
}
