package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="comment")
@Setter
@Getter
@EqualsAndHashCode(exclude = "url")
@NoArgsConstructor
public class Comment {
    @Id
    @SequenceGenerator(name="comment_seq", sequenceName = "user_sequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    private Long comment_id;

    @Column(nullable = false, length = 70)
    private String author;

    @Column(nullable = false, length = 400)
    private String text;

    @Column(nullable = false, length = 70)
    private String url;


    @ManyToOne
    @JoinColumn(name="topic_id")
    private Topic myTopic;
}
