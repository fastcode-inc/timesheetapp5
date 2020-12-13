package com.fastcode.timesheetapp5.domain.core.authorization.tokenverification;

import java.io.Serializable;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenverificationId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long usersId;
    private String tokenType;

    public TokenverificationId(String tokenType, Long usersId) {
        this.usersId = usersId;
        this.tokenType = tokenType;
    }
}
