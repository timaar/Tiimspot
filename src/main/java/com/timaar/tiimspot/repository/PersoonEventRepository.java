package com.timaar.tiimspot.repository;

import com.timaar.tiimspot.domain.PersoonEvent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersoonEvent entity.
 */
public interface PersoonEventRepository extends JpaRepository<PersoonEvent,Long> {

}
