package com.tisitha.product_service.service;

import java.util.List;

public interface ProductItemService {

    List<Object> getNew(Integer size);

    List<Object> getTop(Integer size);

    List<Object> getDeals(Integer size);

    List<Object> search(String text,Integer size);
}
