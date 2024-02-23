package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "topic", schema = "public")
@Data
@NoArgsConstructor
public class Topic {
    @Id
    @SequenceGenerator(name = "topicSeq", sequenceName = "userSequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topicSeq")
    private Long topicId;

    @NotBlank
    @Column(nullable = false, length = 70)
    private String author;

    @NotBlank
    @Column(nullable = false, length = 70)
    private String title;


    @ManyToOne
    @JoinColumn(name = "userId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    public Topic(Long topicId, String author, String title) {
        this.topicId = topicId;
        this.author = author;
        this.title = title;
    }
}
