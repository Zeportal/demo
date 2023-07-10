package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="topic")
@Setter
@Getter
@EqualsAndHashCode(exclude = "url")
@NoArgsConstructor
public class Topic {
    @Id
    @SequenceGenerator(name="topic_seq", sequenceName = "user_sequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_seq")
    private Long topic_id;

    @Column(nullable = false, length = 70)
    private String author;

    @Column(nullable = false, length = 70)
    private String title;

    @Column(nullable = false, length = 70)
    private String url;


    @ManyToOne()
    @JoinColumn(name="user_id")
    private User mUser;

    @OneToMany(mappedBy = "myTopic")
    private Set<Comment> comments;

}
