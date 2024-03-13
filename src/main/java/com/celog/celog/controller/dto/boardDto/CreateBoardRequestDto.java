package com.celog.celog.controller.dto.boardDto;

<<<<<<< Updated upstream
=======
import com.celog.celog.domain.Board;
import jakarta.validation.constraints.Email;
>>>>>>> Stashed changes
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class CreateBoardRequestDto {
<<<<<<< Updated upstream
    @NotBlank(message = "제목을 입력해주세요")
    String title;

    @NotBlank(message = "내용을 입력해주세요")
    String content;
=======
    @Email(message = "제목을 입력해주세요")
    String title;

    @Email(message = "내용을 입력해주세요")
    String content;

>>>>>>> Stashed changes
}
