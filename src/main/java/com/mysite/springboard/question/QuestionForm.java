package com.mysite.springboard.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message = "제목은 필수항목입니다.") // Null 또는 빈 문자열("") 허용X
    @Size(max=200) //길이가 200바이트 이하
    private String subject;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
