package com.celog.celog.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private Long content;

    @Column()
    private String writer;

    @Column()
    private String title;

    @Column()
    private int view_cnt;

    @Column()
    private Date reg_date;
}
