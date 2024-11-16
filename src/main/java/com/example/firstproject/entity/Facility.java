package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

@Entity
public class Facility {

    @Id
    @Column(name = "facility_code", length = 6, columnDefinition = "CHAR(6)")
    private String facility_code;
    private String facility_name;
    private LocalDateTime regdt;

    @PrePersist
    public void prePersist(){
        this.regdt = this.regdt == null ? LocalDateTime.now() : this.regdt;
    }
}
