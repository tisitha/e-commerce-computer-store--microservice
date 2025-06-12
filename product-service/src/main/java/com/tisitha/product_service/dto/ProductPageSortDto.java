package com.tisitha.product_service.dto;

import java.util.List;

public record ProductPageSortDto<T>(List<T> product ,
                                 long totalElement,
                                 int pageCount,
                                 boolean isLast) {
}
