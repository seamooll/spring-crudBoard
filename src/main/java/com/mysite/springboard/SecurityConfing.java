package com.mysite.springboard;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //스프링의 환경 설정 파일임 의미. 하나 이상의 @Bean 메서드 포함해야. 스프링 IoC(Inversion of Control) 컨테이너가 해당 클래스의 Bean정의하는 소스로 사용
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐러티의 제어를 받도록 만드는 애너테이션 -스프링 시큐리티 활성화
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfing {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // 모든 요청 URL에 이 클래스가 필터로 적용(필터 체인). URL별로 특별한 설정할 수 있게 됨
        http
                .authorizeRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()) //new AntPathRequestMatcher("/**"): /** 패턴(모든 :URL패턴)에 매칭되는 모든 요청 의미
                .csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))) // /h2-console/로 시작하는 모든 URL은 CSRF 검증X
                .headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))) //SAME ORIGIN: 프레임에 포함된 웹 페이지가 동일한 사이트에서 재공할 때에만 사용이 허락됨. 같은 출처
                .formLogin((formLogin) -> formLogin //스프링 시큐리티의 로그인 설정 담당
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)) // 로그아웃 시 생성된 사용자 세션 삭제.
        ;
        return http.build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    } // 스프링 시큐러티에서 인증 관리하는 AuthenticationManager(사용자 인증 처리하는 핵심 컴포넌트, 인터페이스) 구성하고 반환


}
