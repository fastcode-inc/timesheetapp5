package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.project.IProjectAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.task.ITaskAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.timesheetdetails.ITimesheetdetailsAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.usertask.IUsertaskAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.TaskController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task/extended")
public class TaskControllerExtended extends TaskController {

    public TaskControllerExtended(
        ITaskAppServiceExtended taskAppServiceExtended,
        IProjectAppServiceExtended projectAppServiceExtended,
        ITimesheetdetailsAppServiceExtended timesheetdetailsAppServiceExtended,
        IUsertaskAppServiceExtended usertaskAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            taskAppServiceExtended,
            projectAppServiceExtended,
            timesheetdetailsAppServiceExtended,
            usertaskAppServiceExtended,
            helper,
            env
        );
    }
    //Add your custom code here

}
