package com.example.demo.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;


@Entity
@Table(name = "comment", schema = "public")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @SequenceGenerator(name = "commentSeq", sequenceName = "userSequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentSeq")
    private Long commentId;

    @NotEmpty
    @Column(nullable = false, length = 70)
    private String author;

    @NotEmpty
    @Column(nullable = false, length = 400)
    private String text;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long topicId;


    @ManyToOne
    @JoinColumn(name = "topicId")
    @EqualsAndHashCode.Exclude
    private  Topic topic;

    public Comment(Long commentId, String author, String text, Long topicId) {
        this.commentId=commentId;
        this.author=author;
        this.text=text;
        this.topicId=topicId;
    }
}
