package com.campuslands.proyectocoworkhub.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "room",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_room_name_location", columnNames = {"name", "location_id"})
        }
)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 50)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType type;
    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser mayor que cero")
    @Column(nullable = false)
    private int capacity;
    @NotNull(message = "La tarifa por hora es obligatoria")
    @PositiveOrZero(message = "La tarifa por hora no puede ser negativa")
    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;
    private boolean active;

    //varias salas pertenecen a una sola sede
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // Una sala puede tener varios equipamientos (tabla intermedia room_equipment)
    @ManyToMany
    @JoinTable(
            name = "room_equipment",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private Set<Equipment> equipments = new HashSet<>();

    // una sala puede tener varias reservas:
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings = new ArrayList<>();

    public Room(String name, RoomType type, int capacity, BigDecimal hourlyRate, boolean active) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.hourlyRate = hourlyRate;
        this.active = active;
    }

    protected Room() {
    }
    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
        equipment.getRooms().add(this);
    }

    public void removeEquipment(Equipment equipment) {
        this.equipments.remove(equipment);
        equipment.getRooms().remove(this);
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RoomType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public boolean isActive() {
        return active;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public Location getLocation() {
        return location;
    }
}
