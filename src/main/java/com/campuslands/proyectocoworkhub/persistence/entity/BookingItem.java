package com.campuslands.proyectocoworkhub.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_item")
public class BookingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "applied_unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal appliedUnitPrice;

    // 0..* BookingItem -> 1 Booking ("contains")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // 0..* BookingItem -> 1 AdditionalService ("charged in")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "additional_service_id", nullable = false)
    private AdditionalService additionalService;

    protected BookingItem() {
    }

    public BookingItem(int quantity, BigDecimal appliedUnitPrice, AdditionalService additionalService) {
        this.quantity = quantity;
        this.appliedUnitPrice = appliedUnitPrice;
        this.additionalService = additionalService;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAppliedUnitPrice() {
        return appliedUnitPrice;
    }

    public void setAppliedUnitPrice(BigDecimal appliedUnitPrice) {
        this.appliedUnitPrice = appliedUnitPrice;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public AdditionalService getAdditionalService() {
        return additionalService;
    }

    public void setAdditionalService(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }
}
