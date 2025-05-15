// src/main/java/com/kp/monitorenterprising/model/MonitorMaterial.java
package com.kp.monitorenterprising.model;

public class MonitorMaterial {
    private int monitorId;
    private int materialId;
    private int quantity; // единиц материала на один монитор

    public MonitorMaterial(int monitorId, int materialId, int quantity) {
        this.monitorId = monitorId;
        this.materialId = materialId;
        this.quantity = quantity;
    }

    public int getMonitorId() {
        return monitorId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "MonitorMaterial{" +
                "monitorId=" + monitorId +
                ", materialId=" + materialId +
                ", quantity=" + quantity +
                '}';
    }
}
