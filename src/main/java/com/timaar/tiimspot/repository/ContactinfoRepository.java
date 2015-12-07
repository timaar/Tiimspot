package com.timaar.tiimspot.repository;

import com.timaar.tiimspot.domain.Contactinfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contactinfo entity.
 */
public interface ContactinfoRepository extends JpaRepository<Contactinfo,Long> {

}
