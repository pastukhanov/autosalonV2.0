package com.ppe.app.autosalon.entity;


import java.util.HashMap;
import java.util.Map;

public class Sale implements Entity{

    private long id;
    private Car car;
    private Customer customer;



    private static long salesCount = 0l;

    public Sale(Car car, Customer customer) {
        this.id = ++salesCount;
        this.car = car;
        this.customer = customer;
    }

    public Sale (Long id, Car car, Customer customer) {
        this.id = id;
        this.car = car;
        this.customer = customer;
    }

    @Override
    public Map<String, Object> toMap() {
        return toMap(true);
    }

    @Override
    public Map<String, Object> toMap(Boolean flag) {
        Map<String, Object> map = new HashMap<>();
        if (flag) map.put("id", id);
        map.put("car_id", car.getId());
        map.put("car_type", car.getType().toString());
        map.put("car_model", car.getModel());
        map.put("car_brand", car.getBrand());
        map.put("customer_id", customer.getId());
        return map;
    }

    public Long getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Sale{id="+id+", car=" + car + ", customer=" + customer + "}";
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Sale)) return false;
        final Sale other = (Sale) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$car = this.getCar();
        final Object other$car = other.getCar();
        if (this$car == null ? other$car != null : !this$car.equals(other$car)) return false;
        final Object this$customer = this.getCustomer();
        final Object other$customer = other.getCustomer();
        if (this$customer == null ? other$customer != null : !this$customer.equals(other$customer)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Sale;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $car = this.getCar();
        result = result * PRIME + ($car == null ? 43 : $car.hashCode());
        final Object $customer = this.getCustomer();
        result = result * PRIME + ($customer == null ? 43 : $customer.hashCode());
        return result;
    }

    public static void setSalesCount(long salesCount) {
        Sale.salesCount = salesCount;
    }
}