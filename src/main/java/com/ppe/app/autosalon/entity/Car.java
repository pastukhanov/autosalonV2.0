package com.ppe.app.autosalon.entity;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Car implements Entity {
    private long id;
    private CarType type;

    private static long carsCount = 0l;
    private String model;
    private String brand;


    public Car(CarType type, String model, String brand) {
        this.id = ++carsCount;
        this.type = type;
        this.model = model;
        this.brand = brand;
    }

    public Car(long id, CarType type, String model, String brand) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.brand = brand;
    }

    public Car() {
        this.id = ++carsCount;
        this.type = CarType.TRUCK;
        this.model = "Model";
        this.brand = "Brand";
    }

    public Car(Map<String, Object> attributes) {
        if (attributes == null) {
            this.id = -1;
            this.type = CarType.UNKNOWN;
            this.model = "Unknown";
            this.brand = "Unknown";
        } else {
        Object idValue = attributes.get("id");
        if (idValue instanceof Integer) {
            this.id = ((Integer) idValue).longValue();
        } else if (idValue instanceof Long) {
            this.id = (Long) idValue;
        }
        this.type = CarType.valueOf((String) attributes.get("type"));
        this.model = (String) attributes.get("model");
        this.brand = (String) attributes.get("brand");
        }
    }


    public Long getId() {
        return id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    @Override
    public String toString() {
        return "Car{id='" + id + "', type='" + type + "', model='" +
                model + "', brand='" + brand + "'}";
    }

    public Map<String, Object> toMap(Boolean flag) {
        Map<String, Object> map = new HashMap<>();
        if (flag) map.put("id", id);
        map.put("type", type.toString());
        map.put("model", model);
        map.put("brand", brand);
        return map;
    }

    public Map<String, Object> toMap() {
        return toMap(true);
    }

    public CarType getType() {
        return this.type;
    }

    public String getModel() {
        return this.model;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Car)) return false;
        final Car other = (Car) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (!Objects.equals(this$type, other$type)) return false;
        final Object this$model = this.getModel();
        final Object other$model = other.getModel();
        if (!Objects.equals(this$model, other$model)) return false;
        final Object this$brand = this.getBrand();
        final Object other$brand = other.getBrand();
        if (!Objects.equals(this$brand, other$brand)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Car;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $model = this.getModel();
        result = result * PRIME + ($model == null ? 43 : $model.hashCode());
        final Object $brand = this.getBrand();
        result = result * PRIME + ($brand == null ? 43 : $brand.hashCode());
        return result;
    }

    public static void setCarsCount(long carsCount) {
        Car.carsCount = carsCount;
    }

    public static void decrementCarsCount() {
        carsCount--;
    }

}
