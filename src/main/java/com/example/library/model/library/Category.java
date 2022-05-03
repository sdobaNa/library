package com.example.library.model.library;

import com.example.library.model.GeneralEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "categories", schema = "public", catalog = "library")
public class Category extends GeneralEntity {

    @Column(nullable = false, length = 100)
    private String name;

    public Category() {
        this.name = "";
    }

    public Category(UUID uid, String name) {
        super(uid);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
