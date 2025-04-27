package com.kp.monitorenterprising.model;

import java.math.BigDecimal;

public class Monitor {
    private int id;
    private String modelName;
    private double screenSize;
    private String resolution;
    private int refreshRate;
    private BigDecimal price;

    public Monitor(int id, String modelName, double screenSize, String resolution, int refreshRate, BigDecimal price) {
        this.id = id;
        this.modelName = modelName;
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getModelName() {
        return modelName;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public String getResolution() {
        return resolution;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
