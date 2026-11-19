package com.campuslands.proyectocoworkhub.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del equipamiento es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar los 80 caracteres")
    @Column(nullable = false, unique = true, length = 80)
    private String name;

    // Lado inverso del @ManyToMany: apunta al atributo 'equipments' en Room
    @ManyToMany(mappedBy = "equipments")
    private Set<Room> rooms = new HashSet<>();

    protected Equipment() {
    }


    public Equipment(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

}
