package com.goorm.friendchise.domain.headquarter.commercialarea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommercialAreaRepository extends JpaRepository<CommercialArea, Long> {

    @Query("SELECT ca FROM CommercialArea ca " +
            "WHERE ST_Contains(ca.geom, ST_SetSRID(ST_MakePoint(:x, :y), 4326)) = true " +
            "OR ST_Touches(ca.geom, ST_SetSRID(ST_MakePoint(:x, :y), 4326)) = true")
    List<CommercialArea> findByPoint(double x, double y);
}
