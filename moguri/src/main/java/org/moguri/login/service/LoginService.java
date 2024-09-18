package com.example.project.login.service;

import com.example.project.login.domain.User;
import com.example.project.login.param.LoginRequest;
import com.example.project.login.param.ChangePasswordRequest;
import com.example.project.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return user;
        }
        throw new RuntimeException("Invalid credentials");
    }

    @Transactional
    public void logout() {
        // 로그아웃 처리 로직 (예: 세션 무효화)
        // 보통 로그아웃은 상태를 관리하는 부분이므로 여기서는 별도의 처리 없이
        // 세션 무효화 관련 로직을 구현해야 합니다.
    }

    @Transactional(readOnly = true)
    public User getUserInfo(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userRepository.update(user);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
