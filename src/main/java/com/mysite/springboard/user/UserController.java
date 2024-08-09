package com.mysite.springboard.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup_form";
        }
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            //오류 코드는 임의로 passwordInCorrect로 정의
            return "signup_form";
        }
        try{
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());

        }catch(DataIntegrityViolationException e){
            e.printStackTrace(); // 예외 처리. 발생한 예외의 스택 프레이스(에외가 발생한 위치, 호출 스택의 상태)를 콘솔에 출력
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");// reject(): 특정 오류 코드를 추가하여 뷰에서 이 오류를 처리할 수 있도록 함.
            return "signup_form";
        }catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage()); //bindingResult.reject(오류 코드, 오류 메시지)는 UserCreateForm의 검증에 의한 오류 외에 일반적인 오류를 발생시킬 때 사용
            return "signup_form";
        }

        return "redirect:/";

    }

    @GetMapping("/login") // 실제 로그인 진행하는 @PostMapping 방식의 메서드는 스프링 시큐러티가 대신 처리. 직접 코드 작성할 필요 없음.
    public String login(){
        return "login_form";
    }
}
