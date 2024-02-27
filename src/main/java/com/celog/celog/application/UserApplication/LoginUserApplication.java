package com.celog.celog.application.UserApplication;

import com.celog.celog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserApplication {
    private final UserRepository userRepository;
}
