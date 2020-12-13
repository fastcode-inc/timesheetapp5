package com.fastcode.timesheetapp5.domain.core.authorization.users;

import com.fastcode.timesheetapp5.domain.core.abstractentity.AbstractEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.userspreference.UserspreferenceEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UsersEntity extends AbstractEntity {

    @Basic
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Basic
    @Column(name = "isactive", nullable = false)
    private Boolean isactive;

    @Basic
    @Column(name = "emailaddress", nullable = false)
    private String emailaddress;

    @Basic
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "isemailconfirmed", nullable = false)
    private Boolean isemailconfirmed;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "username", nullable = false)
    private String username;

    @OneToOne(mappedBy = "users", cascade = CascadeType.MERGE)
    private UserspreferenceEntity userspreference;
}
