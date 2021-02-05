package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "QUESTION")
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "message", nullable = false)
    private String message;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Reply> replies;

}
