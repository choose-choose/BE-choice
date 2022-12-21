package com.example.moduhouse.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Image")
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "Board_ID", nullable = false)
    private Board board;

    public Image(String image, Board board){
        this.board = board;
        this.image = image;
    }
}