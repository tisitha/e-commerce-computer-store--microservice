package com.tisitha.product_service.service;

import java.util.List;
import java.util.UUID;

public interface ProductService<U,V> {

    V add(U product);

    List<V> isNew();

    List<V> isTop();

    List<V> isDeal();

    List<V> search(String text);

    V getProduct(UUID id);

    V updateProduct(UUID id,U product);

    void deleteProduct(UUID id);
}