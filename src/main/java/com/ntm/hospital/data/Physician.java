package com.ntm.hospital.data;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Physician {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "physician_id")
    private Integer id;

    @Column(name = "physician_name", nullable = false)
    private String name;
    private String position;

    private Integer ssn;
}
