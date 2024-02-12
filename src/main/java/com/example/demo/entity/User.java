package com.example.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "userSequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @NotEmpty
    private Long userId;

    @NotEmpty
    @Column(nullable = false, length = 70)
    private String user_login;

    @NotEmpty
    @Column(nullable = false, length = 70)
    private String user_password;


    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private List<Topic> topics=new ArrayList<>();
}
