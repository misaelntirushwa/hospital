package com.ntm.hospital.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "patient_id")
    private Integer id;

    @Column(name = "patient_name", nullable = false)
    private String name;
    private String address;
    private String phone;
    private Integer insuranceId;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    private Physician physician;
}
