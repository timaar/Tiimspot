package com.timaar.tiimspot.repository;

import com.timaar.tiimspot.domain.Ouder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ouder entity.
 */
public interface OuderRepository extends JpaRepository<Ouder,Long> {

}
