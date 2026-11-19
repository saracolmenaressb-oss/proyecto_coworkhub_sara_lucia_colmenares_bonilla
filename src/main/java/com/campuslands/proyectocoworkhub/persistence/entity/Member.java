package com.campuslands.proyectocoworkhub.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private String documentNumber;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membershipplan_id", nullable = false)
    private MembershipPlan membershipPlan;
    public Member(String documentNumber, String fullName, String email, MemberStatus status) {
        this.documentNumber = documentNumber;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
    }
    protected Member() {
    }
    public Long getId() {
        return id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public MemberStatus getStatus() {
        return status;
    }
    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateFullName(String fullName) {
        this.fullName = fullName;
    }
}
