package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.authorization.role.IRoleAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.authorization.users.IUsersAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.UsersroleController;
import com.fastcode.timesheetapp5.security.JWTAppService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usersrole/extended")
public class UsersroleControllerExtended extends UsersroleController {

    public UsersroleControllerExtended(
        IUsersroleAppServiceExtended usersroleAppServiceExtended,
        IRoleAppServiceExtended roleAppServiceExtended,
        IUsersAppServiceExtended usersAppServiceExtended,
        JWTAppService jwtAppService,
        LoggingHelper helper,
        Environment env
    ) {
        super(usersroleAppServiceExtended, roleAppServiceExtended, usersAppServiceExtended, jwtAppService, helper, env);
    }
    //Add your custom code here

}
