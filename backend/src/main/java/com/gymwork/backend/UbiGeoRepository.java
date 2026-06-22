package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UbiGeoRepository extends JpaRepository<UbiGeo, String> {
    List<UbiGeo> findByCodigoStartingWith(String prefix);
}
