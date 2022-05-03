package com.example.library.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;
@MappedSuperclass
public abstract class GeneralEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uid", nullable = false)
    private UUID uid;

    public GeneralEntity() {
        this.uid = null;
    }

    public GeneralEntity(UUID uid) {
        this.uid = uid;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = UUID.fromString(uid);
    }
}
