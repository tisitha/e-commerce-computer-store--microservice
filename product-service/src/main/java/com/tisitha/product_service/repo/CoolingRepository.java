package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Casing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoolingRepository extends JpaRepository<Casing, UUID> {

    List<Casing> findAllByIsNew(boolean b);

    List<Casing> findAllByIsTop(boolean b);

    List<Casing> findAllByDealNot(int i);

    List<Casing> findByNameContainingIgnoreCase(String text);
}
