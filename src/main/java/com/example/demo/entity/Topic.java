package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name="topic", schema = "public")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Topic {
    @Id
    @SequenceGenerator(name="topicSeq", sequenceName = "userSequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topicSeq")
    @NotEmpty
    private Long topicId;

    @NotEmpty
    @Column(nullable = false, length = 70)
    private String author;

    @NotEmpty
    @Column(nullable = false, length = 70)
    private String title;




    @ManyToOne
    @JoinColumn(name="userId")
    @EqualsAndHashCode.Exclude
    private User user;

//    @OneToMany(mappedBy = "topic")
//    private List<Comment> comments;

}
