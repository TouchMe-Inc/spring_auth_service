package by.touchme.authservice.service;

import by.touchme.authservice.dto.BaseLoginDto;
import by.touchme.authservice.dto.RefreshDto;
import by.touchme.authservice.dto.JwtDto;

public interface AuthService {

    JwtDto login(BaseLoginDto baseLoginDto);
    JwtDto refresh(RefreshDto requestRefreshDto);
}
