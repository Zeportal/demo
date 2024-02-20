package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;


@Entity
@Table(name = "topic", schema = "public")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Topic {
    @Id
    @SequenceGenerator(name = "topicSeq", sequenceName = "userSequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topicSeq")
    private Long topicId;

    @NotEmpty
    @Column(nullable = false, length = 70)
    private String author;

    @NotEmpty
    @Column(nullable = false, length = 70)
    private String title;


    @ManyToOne
    @JoinColumn(name = "userId")
    @EqualsAndHashCode.Exclude
    private User user;


}
