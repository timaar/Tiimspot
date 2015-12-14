package com.timaar.tiimspot.repository;

import com.timaar.tiimspot.domain.Persoon;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Persoon entity.
 */
public interface PersoonRepository extends JpaRepository<Persoon,Long> {

    @Query("select distinct persoon from Persoon persoon left join fetch persoon.ouders")
    List<Persoon> findAllWithEagerRelationships();

    @Query("select persoon from Persoon persoon left join fetch persoon.ouders where persoon.id =:id")
    Persoon findOneWithEagerRelationships(@Param("id") Long id);

}
