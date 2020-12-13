package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.authorization.users.IUsersAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.UserspermissionController;
import com.fastcode.timesheetapp5.security.JWTAppService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userspermission/extended")
public class UserspermissionControllerExtended extends UserspermissionController {

    public UserspermissionControllerExtended(
        IUserspermissionAppServiceExtended userspermissionAppServiceExtended,
        IPermissionAppServiceExtended permissionAppServiceExtended,
        IUsersAppServiceExtended usersAppServiceExtended,
        JWTAppService jwtAppService,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            userspermissionAppServiceExtended,
            permissionAppServiceExtended,
            usersAppServiceExtended,
            jwtAppService,
            helper,
            env
        );
    }
    //Add your custom code here

}
