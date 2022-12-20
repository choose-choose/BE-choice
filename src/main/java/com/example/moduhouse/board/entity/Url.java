package com.example.moduhouse.board.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Url")
@NoArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;


    @ManyToOne
    @JoinColumn(name = "Board_ID", nullable = false)
    private Board board;

    public Url(String url, Board board){
        this.board = board;
        this.url = url;
    }
}



