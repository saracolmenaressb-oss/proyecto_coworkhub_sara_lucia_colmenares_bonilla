package com.campuslands.proyectocoworkhub.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member_ship_plan")
public class MembershipPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 60)
    private String name;
    @Column(name = "included_hours_per_month", nullable = false)
    private  int includedHourPerMonth;
    @Column(name = "overage_discount", nullable = false)
    private int overageDiscount;
    @Column(name = "max_active_bookings", nullable = false)
    private int maxActiveBooking;

    @OneToMany(mappedBy =  "membershipPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    protected MembershipPlan() {
    }

    public MembershipPlan(String name, int includedHourPerMonth, int overageDiscount, int maxActiveBooking) {
        this.name = name;
        this.includedHourPerMonth = includedHourPerMonth;
        this.overageDiscount = overageDiscount;
        this.maxActiveBooking = maxActiveBooking;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIncludedHourPerMonth() {
        return includedHourPerMonth;
    }

    public void setIncludedHourPerMonth(int includedHourPerMonth) {
        this.includedHourPerMonth = includedHourPerMonth;
    }

    public int getOverageDiscount() {
        return overageDiscount;
    }
    public void setOverageDiscount(int overageDiscount) {
        this.overageDiscount = overageDiscount;
    }
    public int getMaxActiveBooking() {
        return maxActiveBooking;
    }
    public void setMaxActiveBooking(int maxActiveBookings) {
        this.maxActiveBooking = maxActiveBookings;
    }

    public List<Member> getMembers() {
        return members;
    }
}
