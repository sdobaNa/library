package com.example.library.model.library;

import com.example.library.model.GeneralEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "books", schema = "public", catalog = "library")
public class Book extends GeneralEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_uid", nullable = false)
    private final Category category;

    public Book() {
        this.name = "";
        this.author = "";
        this.issueDate = new Date();
        this.category = new Category();
    }

    public Book(UUID uid, String name, String author, Date issueDate, String categoryUid) {
        super(uid);
        this.name = name;
        this.author = author;
        this.issueDate = issueDate;
        this.category = new Category(UUID.fromString(categoryUid), "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return issueDate;
    }

    public void setDate(Date date) {
        this.issueDate = date;
    }

    public UUID getCategoryUid() {
        return this.category.getUid();
    }

    public void setCategoryUid(String categoryUid) {
        this.category.setUid(categoryUid);
    }

    @Override
    public String toString() {
        return "Book{" +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", date=" + issueDate +
                ", category_uid='" + category + '\'' +
                '}';
    }
}
