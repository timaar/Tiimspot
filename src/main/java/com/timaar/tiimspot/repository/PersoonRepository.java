package com.timaar.tiimspot.repository;

import com.timaar.tiimspot.domain.Persoon;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Persoon entity.
 */
public interface PersoonRepository extends JpaRepository<Persoon,Long> {

}
