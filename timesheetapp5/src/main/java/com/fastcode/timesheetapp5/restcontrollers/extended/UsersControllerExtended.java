package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.authorization.users.IUsersAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.timesheet.ITimesheetAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.usertask.IUsertaskAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.UsersController;
import com.fastcode.timesheetapp5.security.JWTAppService;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/extended")
public class UsersControllerExtended extends UsersController {

    public UsersControllerExtended(
        IUsersAppServiceExtended usersAppServiceExtended,
        ITimesheetAppServiceExtended timesheetAppServiceExtended,
        IUserspermissionAppServiceExtended userspermissionAppServiceExtended,
        IUsersroleAppServiceExtended usersroleAppServiceExtended,
        IUsertaskAppServiceExtended usertaskAppServiceExtended,
        PasswordEncoder pEncoder,
        JWTAppService jwtAppService,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            usersAppServiceExtended,
            timesheetAppServiceExtended,
            userspermissionAppServiceExtended,
            usersroleAppServiceExtended,
            usertaskAppServiceExtended,
            pEncoder,
            jwtAppService,
            helper,
            env
        );
    }
    //Add your custom code here

}
