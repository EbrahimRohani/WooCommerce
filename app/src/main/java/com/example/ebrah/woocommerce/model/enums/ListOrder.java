package com.example.ebrah.woocommerce.model.enums;

public enum ListOrder{
    ORDER_BY_DATE("date"), ORDER_BY_POPULARITY("popularity"), ORDER_BY_RATING("rating");

    private String orderBy;
    ListOrder(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy(){
        return orderBy;
    }
}
