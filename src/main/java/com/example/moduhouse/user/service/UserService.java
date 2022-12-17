package com.example.moduhouse.user.service;

import com.example.moduhouse.global.jwt.JwtUtil;
import com.example.moduhouse.user.dto.LoginRequestDto;
import com.example.moduhouse.user.dto.SignupRequestDto;
import com.example.moduhouse.user.entity.User;
import com.example.moduhouse.user.entity.UserRoleEnum;
import com.example.moduhouse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "HangHae99NoHangHae130Yes";

    // 회원가입
    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 중복 닉네임
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
//            throw new CustomException(ErrorCode.DUPLICATED_USER);
            return;
        }

        UserRoleEnum role = UserRoleEnum.USER;

        // 권한 토큰키 검증
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
                return;
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
    }

    // 로그인
    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalIdentifierException("1")
//                new CustomException(ErrorCode.NOT_FOUND_USER)

        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
//            throw new CustomException(ErrorCode.WRONG_PASSWORD);
            return;
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        //add header로 헤더에 값 넣어주기 (키, 토큰)
    }
}
