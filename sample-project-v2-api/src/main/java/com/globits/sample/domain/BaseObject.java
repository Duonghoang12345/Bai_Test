package com.globits.sample.domain;

import javax.persistence.*;

@MappedSuperclass
public class BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


