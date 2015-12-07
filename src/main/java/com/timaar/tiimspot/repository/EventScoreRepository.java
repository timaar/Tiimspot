package com.timaar.tiimspot.repository;

import com.timaar.tiimspot.domain.EventScore;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EventScore entity.
 */
public interface EventScoreRepository extends JpaRepository<EventScore,Long> {

}
