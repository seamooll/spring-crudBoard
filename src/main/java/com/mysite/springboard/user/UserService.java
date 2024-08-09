package com.mysite.springboard.user;

import com.mysite.springboard.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String passward){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(passward)); //bean으로 등록하여 Password Encoder 객체를 주입받아 사용
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else {
            throw new DataNotFoundException("siteuser not found");
        }
    }


}
// PasswordEncoder는 BCryptPasswordEncoder의 인터페이스.
// 암호화 방식 변경 시 수정이 쉽도록  BCryptPasswordEncoder 객체를 직접 new로 생성하는 방식보다는 PasswordEncoder 객체를 빈으로 등록해서 사용하는 것이 좋다