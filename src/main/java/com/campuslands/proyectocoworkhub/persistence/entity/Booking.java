package com.campuslands.proyectocoworkhub.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_time", updatable = false)
    private LocalDateTime startTime;
    @Column(name = "end_time", updatable = false)
    private LocalDateTime endTime;
    @Column(nullable = false)
    private int attendees;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status;
    @Column(name = "room_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal roomCost;
    @Column(name = "services_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal serviceCost;
    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;
    @Column(name = "cancellation_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal cancellationFee;
    @Column(name = "hours_returned", nullable = false)
    private boolean hoursReturned = false;

    //0..* Booking -> 1 Room ("booked in")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // 0..* Booking -> 1 Member ("makes")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 1 Booking -> 0..* BookingItem ("contains")
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingItem> items = new ArrayList<>();

    public Booking() {
    }

    public Booking(LocalDateTime startTime, LocalDateTime endTime, int attendees, BookingStatus status, BigDecimal roomCost, BigDecimal serviceCost, BigDecimal totalCost, BigDecimal cancellationFee, Room room, Member member) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendees = attendees;
        this.status = status;
        this.roomCost = roomCost;
        this.serviceCost = serviceCost;
        this.totalCost = totalCost;
        this.cancellationFee = cancellationFee;
        this.room = room;
        this.member = member;
    }

    // Método helper para añadir un ítem manteniendo ambos lados sincronizados
    public void addItem(BookingItem item) {
        this.items.add(item);
        item.setBooking(this);
    }

    public void removeItem(BookingItem item) {
        this.items.remove(item);
        item.setBooking(null);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public boolean isHoursReturned() {
        return hoursReturned;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public BigDecimal getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(BigDecimal roomCost) {
        this.roomCost = roomCost;
    }

    public BigDecimal getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(BigDecimal serviceCost) {
        this.serviceCost = serviceCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(BigDecimal cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<BookingItem> getItems() {
        return items;
    }

    public void setHoursReturned(boolean hoursReturned) {
        this.hoursReturned = hoursReturned;
    }

    public void setItems(List<BookingItem> items) {
        this.items = items;
    }
}
