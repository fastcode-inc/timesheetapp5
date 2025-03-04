package com.fastcode.timesheetapp5.application.core.authorization.users;

import com.fastcode.timesheetapp5.application.core.authorization.users.dto.*;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.userspreference.UserspreferenceEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IUsersMapper {
    UsersEntity createUsersInputToUsersEntity(CreateUsersInput usersDto);

    @Mappings({ @Mapping(source = "entity.id", target = "id") })
    CreateUsersOutput usersEntityToCreateUsersOutput(UsersEntity entity, UserspreferenceEntity userPreference);

    @Mappings(
        {
            @Mapping(source = "usersProfile.firstname", target = "firstname"),
            @Mapping(source = "usersProfile.lastname", target = "lastname"),
            @Mapping(source = "usersProfile.username", target = "username"),
            @Mapping(source = "usersProfile.emailaddress", target = "emailaddress"),
        }
    )
    UpdateUsersInput findUsersWithAllFieldsByIdOutputAndUsersProfileToUpdateUsersInput(
        FindUsersWithAllFieldsByIdOutput users,
        UsersProfile usersProfile
    );

    UsersEntity findUsersWithAllFieldsByIdOutputToUsersEntity(FindUsersWithAllFieldsByIdOutput users);

    UsersProfile updateUsersOutputToUsersProfile(UpdateUsersOutput usersDto);

    UsersProfile findUsersByIdOutputToUsersProfile(FindUsersByIdOutput users);

    UsersEntity updateUsersInputToUsersEntity(UpdateUsersInput usersDto);

    UpdateUsersOutput usersEntityToUpdateUsersOutput(UsersEntity entity);

    @Mappings(
        { @Mapping(source = "entity.versiono", target = "versiono"), @Mapping(source = "entity.id", target = "id") }
    )
    FindUsersByIdOutput usersEntityToFindUsersByIdOutput(UsersEntity entity, UserspreferenceEntity userPreference);

    FindUsersWithAllFieldsByIdOutput usersEntityToFindUsersWithAllFieldsByIdOutput(UsersEntity entity);
    FindUsersByUsernameOutput usersEntityToFindUsersByUsernameOutput(UsersEntity entity);
}
