package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="topic", schema = "public")
@Setter
@Getter
@EqualsAndHashCode(exclude = "url")
@NoArgsConstructor
public class Topic {
    @Id
    @SequenceGenerator(name="topicSeq", sequenceName = "userSequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topicSeq")
    private Long topicId;

    @Column(nullable = false, length = 70)
    private String author;

    @Column(nullable = false, length = 70)
    private String title;




    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

//    @OneToMany(mappedBy = "topic")
//    private List<Comment> comments;

}
