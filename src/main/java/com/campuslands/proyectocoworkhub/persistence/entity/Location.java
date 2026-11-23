package com.campuslands.proyectocoworkhub.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String city;
    @Column(nullable = false, length = 50)
    private String address;
    @NotNull(message = "La hora de apertura es obligatoria")
    @Column(name = "opening_time", nullable = false)
    private LocalTime openingTime;
    @NotNull(message = "La hora de cierre es obligatoria")
    @Column(name = "closing_time", nullable = false)
    private LocalTime closingTime;
    @OneToMany(mappedBy = "location")
    private List<Room> rooms = new ArrayList<>();

    protected Location() {
    }

    public Location(String name, String city, String address, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.setLocation(this);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setLocation(null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
