package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.authorization.users.IUsersAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.task.ITaskAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.usertask.IUsertaskAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.UsertaskController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usertask/extended")
public class UsertaskControllerExtended extends UsertaskController {

    public UsertaskControllerExtended(
        IUsertaskAppServiceExtended usertaskAppServiceExtended,
        ITaskAppServiceExtended taskAppServiceExtended,
        IUsersAppServiceExtended usersAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(usertaskAppServiceExtended, taskAppServiceExtended, usersAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
