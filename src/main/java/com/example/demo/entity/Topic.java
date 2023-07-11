package com.example.demo.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="topic")

public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String createdBy;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 50,insertable = false,updatable = false)
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Topic() {

    }
    @OneToOne()
    @JoinColumn(name="url")
    private User mUser;

    @OneToMany(mappedBy = "mTopic")
    private Set<Comment> comments;

}
