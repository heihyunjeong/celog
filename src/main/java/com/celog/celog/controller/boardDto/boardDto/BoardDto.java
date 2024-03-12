package com.celog.celog.controller.boardDto.boardDto;

import com.celog.celog.domain.Board;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Builder
public class BoardDto {

    Long id;

    Long content;

    String writer;

    String title;

    int view_cnt;

    Date reg_date;


}
