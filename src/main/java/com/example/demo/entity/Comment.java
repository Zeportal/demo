package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="comment", schema = "public")
@Setter
@Getter
@EqualsAndHashCode(exclude = "url")
@NoArgsConstructor
public class Comment {
    @Id
    @SequenceGenerator(name="commentSeq", sequenceName = "userSequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentSeq")
    private Long commentId;

    @Column(nullable = false, length = 70)
    private String author;

    @Column(nullable = false, length = 400)
    private String text;



    @ManyToOne
    @JoinColumn(name="topicId")
    private Topic topic;
}
