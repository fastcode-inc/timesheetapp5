package com.fastcode.timesheetapp5.domain.core.authorization.tokenverification;

public interface ITokenVerificationManager {
    TokenverificationEntity save(TokenverificationEntity entity);

    void delete(TokenverificationEntity entity);

    TokenverificationEntity findByTokenAndType(String token, String tokenType);

    TokenverificationEntity findByUsersIdAndType(Long id, String tokenType);
}
