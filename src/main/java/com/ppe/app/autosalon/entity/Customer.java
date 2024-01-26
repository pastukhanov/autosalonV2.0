package com.ppe.app.autosalon.entity;


import java.util.HashMap;
import java.util.Map;

public class Customer implements Entity{

    private long id;
    private static long customersCount = 0l;
    private String name;
    private int age;
    private CustomerGender gender;


    public Customer(String name, int age, CustomerGender gender) {
        this.id = ++customersCount;
        this.name = name;
        this.age = age;
        this.gender = gender;

    }

    public Customer(long id, String name, int age, CustomerGender gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Customer() {
        this.id = ++customersCount;
        this.name = "";
        this.age = 0;
        this.gender = CustomerGender.MALE;
    }

    public Customer(Map<String, Object> attributes) {
        if (attributes == null) {
            this.id = -1;
            this.name = "Unknown";
            this.age = 100;
            this.gender = CustomerGender.MALE;
        } else {
            Object idValue = attributes.get("id");
            if (idValue instanceof Integer) {
                this.id = ((Integer) idValue).longValue();
            } else if (idValue instanceof Long) {
                this.id = (Long) idValue;
            }
            this.name = (String) attributes.get("name");
            this.age = (Integer) attributes.get("age");
            this.gender =   CustomerGender.valueOf((String) attributes.get("gender"));
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(CustomerGender gender) {
        this.gender = gender;
    }

    @Override
    public Map<String, Object> toMap() {
        return toMap(true);

    }

    @Override
    public Map<String, Object> toMap(Boolean flag) {
        Map<String, Object> map = new HashMap<>();
        if (flag) map.put("id", id);
        map.put("name", name);
        map.put("age", age);
        map.put("gender", gender.toString());
        return map;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Customer{name='" + name + "', age=" + age + ", gender=" + gender + "}";
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public CustomerGender getGender() {
        return this.gender;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Customer)) return false;
        final Customer other = (Customer) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.getAge() != other.getAge()) return false;
        final Object this$gender = this.getGender();
        final Object other$gender = other.getGender();
        if (this$gender == null ? other$gender != null : !this$gender.equals(other$gender)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Customer;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + this.getAge();
        final Object $gender = this.getGender();
        result = result * PRIME + ($gender == null ? 43 : $gender.hashCode());
        return result;
    }

    public static void setCustomersCount(long customersCount) {
        Customer.customersCount = customersCount;
    }
}
