// src/main/java/com/kp/monitorenterprising/model/Material.java
package com.kp.monitorenterprising.model;

import java.math.BigDecimal;

public class Material {
    private int materialId;
    private String name;
    private String unit;
    private BigDecimal pricePerUnit;
    private int stock;

    public Material(int materialId, String name, String unit, BigDecimal pricePerUnit, int stock) {
        this.materialId = materialId;
        this.name = name;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
        this.stock = stock;
    }

    public int getMaterialId() {
        return materialId;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
