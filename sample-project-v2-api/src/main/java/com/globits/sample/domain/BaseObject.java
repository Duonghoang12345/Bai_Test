package com.globits.sample.domain;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedSuperclass
    public class BaseObject {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO )
        @Column(name = "id")
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


    }


