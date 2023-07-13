package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user")
@Setter
@Getter
@EqualsAndHashCode()
@NoArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name="userSeq", sequenceName = "userSequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private Long userId;

    @Column(nullable = false, length = 70)
    private String userLogin;

    @Column(nullable = false, length = 70)
    private String userPassword;


//    @OneToMany(mappedBy = "user")
//    private Set<Topic> topics;
}
