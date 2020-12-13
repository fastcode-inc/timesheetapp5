package com.fastcode.timesheetapp5.application.core.authorization.tokenverification;

import com.fastcode.timesheetapp5.domain.core.authorization.tokenverification.TokenverificationEntity;

public interface ITokenVerificationAppService {
    TokenverificationEntity findByTokenAndType(String token, String type);

    TokenverificationEntity generateToken(String type, Long usersId);

    TokenverificationEntity findByUsersIdAndType(Long usersId, String type);

    void deleteToken(TokenverificationEntity entity);
}
