package com.example.CEMS.service;

import com.example.CEMS.dto.LoginRequestDto;
import com.example.CEMS.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);
}
