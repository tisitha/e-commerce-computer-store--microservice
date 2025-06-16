package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.PowerSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PowerSupplyRepository extends JpaRepository<PowerSupply, UUID> {

    List<PowerSupply> findAllByLatest(boolean b);

    List<PowerSupply> findAllByTop(boolean b);

    List<PowerSupply> findAllByDealNot(int i);

    List<PowerSupply> findByNameContainingIgnoreCase(String text);

    List<PowerSupply> findByBrandInAndWattageOutputInAndCertificationRatingInAndFormFactorInAndModularityTypeIn(
            List<String> brand,
            List<String> wattageOutput,
            List<String> certificationRating,
            List<String> formFactor,
            List<String> modularityType
    );

    @Query("SELECT DISTINCT p.brand FROM PowerSupply p")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT p.wattageOutput FROM PowerSupply p")
    List<String> findDistinctWattageOutput();

    @Query("SELECT DISTINCT p.certificationRating FROM PowerSupply p")
    List<String> findDistinctCertificationRating();

    @Query("SELECT DISTINCT p.formFactor FROM PowerSupply p")
    List<String> findDistinctFormFactor();

    @Query("SELECT DISTINCT p.modularityType FROM PowerSupply p")
    List<String> findDistinctModularityType();
}
