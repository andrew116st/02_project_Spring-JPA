package ru.andrew116st.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table (name = "Books")

public class Books {

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Min(value = 1700, message = "Year should be greater than 1700")

    @Column(name = "year")
    private int year;

    @Transient
    private boolean delay;



    @Column (name= "created_at")
    @Temporal(TemporalType.TIMESTAMP)

    private Date createdAt;


    public Books(){

    }


    public Books(int id, String name, String author, int year, boolean delay, Date created_at) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.delay = delay;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public boolean getDelay() {return delay;}

    public void setDelay(boolean delay) {this.delay = delay;}

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


}