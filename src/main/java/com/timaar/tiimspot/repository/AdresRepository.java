package com.timaar.tiimspot.repository;

import com.timaar.tiimspot.domain.Adres;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Adres entity.
 */
public interface AdresRepository extends JpaRepository<Adres,Long> {

}
