package com.kp.monitorenterprising.model;

import java.math.BigDecimal;

public class Monitor {
    private int id;
    private String modelName;
    private double screenSize;
    private String resolution;
    private int refreshRate;
    private BigDecimal price;

    // новые поля
    private String status;
    private String description;

    /** Существующий конструктор остаётся без изменений */
    public Monitor(int id,
                   String modelName,
                   double screenSize,
                   String resolution,
                   int refreshRate,
                   BigDecimal price) {
        this.id = id;
        this.modelName = modelName;
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.price = price;
    }

    /**
     * Новый конструктор, включающий статус и описание (технологическую карту).
     * Можно использовать в новых DAO, не трогая старый код.
     */
    public Monitor(int id,
                   String modelName,
                   double screenSize,
                   String resolution,
                   int refreshRate,
                   BigDecimal price,
                   String status,
                   String description) {
        this(id, modelName, screenSize, resolution, refreshRate, price);
        this.status = status;
        this.description = description;
    }

    // старые геттеры
    public int getId() { return id; }
    public String getModelName() { return modelName; }
    public double getScreenSize() { return screenSize; }
    public String getResolution() { return resolution; }
    public int getRefreshRate() { return refreshRate; }
    public BigDecimal getPrice() { return price; }

    // новые геттеры
    public String getStatus() { return status; }
    public String getDescription() { return description; }

    // при желании можно добавить сеттеры, если понадобятся
    public void setStatus(String status) { this.status = status; }
    public void setDescription(String description) { this.description = description; }

    public String getMonitorId() {
        return Integer.toString(this.id);
    }

}
