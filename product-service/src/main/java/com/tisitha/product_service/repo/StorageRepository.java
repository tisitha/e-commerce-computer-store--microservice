package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StorageRepository extends JpaRepository<Storage, UUID> {

    List<Storage> findAllByLatest(boolean b);

    List<Storage> findAllByTop(boolean b);

    List<Storage> findAllByDealNot(int i);

    List<Storage> findByNameContainingIgnoreCase(String text);

    Page<Storage> findByBrandInAndStorageTypeInAndCapacityGBInAndInterfaceTypeInAndUsageTypeIn(
            List<String> brand,
            List<String> storageType,
            List<String> capacityGB,
            List<String> interfaceType,
            List<String> usageType,
            Pageable pageable
    );

    @Query("SELECT DISTINCT s.brand FROM Storage s")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT s.storageType FROM Storage s")
    List<String> findDistinctStorageType();

    @Query("SELECT DISTINCT s.capacityGB FROM Storage s")
    List<String> findDistinctCapacityGB();

    @Query("SELECT DISTINCT s.interfaceType FROM Storage s")
    List<String> findDistinctInterfaceType();

    @Query("SELECT DISTINCT s.usageType FROM Storage s")
    List<String> findDistinctUsageType();
}
