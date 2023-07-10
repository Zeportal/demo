package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="user")
@Setter
@Getter
@EqualsAndHashCode()
@NoArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name="user_seq", sequenceName = "user_sequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long user_id;

    @Column(nullable = false, length = 70)
    private String userLogin;

    @Column(nullable = false, length = 70)
    private String userPassword;


    @OneToMany(mappedBy = "mUser")
    private Set<Topic> topics;
}
