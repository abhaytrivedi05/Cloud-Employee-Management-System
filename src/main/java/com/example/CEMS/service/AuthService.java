package com.example.CEMS.service;

import com.example.CEMS.dto.RegisterRequestDto;

public interface AuthService {

    String register(RegisterRequestDto dto);
}
