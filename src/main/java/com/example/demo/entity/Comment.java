package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="comment")

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String createdBy;

    @Column(nullable = false, length = 50)
    private String text;

    @Column(nullable = false, length = 50,insertable = false,updatable = false)
    private String topicUrl;

    @Column(nullable = false, length = 50)
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopicUrl() {
        return topicUrl;
    }

    public void setTopicUrl(String topicUrl) {
        this.topicUrl = topicUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Comment() {

    }
    @ManyToOne
    @JoinColumn(name="topicUrl")
    private Topic mTopic;
}
