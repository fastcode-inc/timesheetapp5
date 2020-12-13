package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.customer.ICustomerAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.project.IProjectAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.task.ITaskAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.ProjectController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project/extended")
public class ProjectControllerExtended extends ProjectController {

    public ProjectControllerExtended(
        IProjectAppServiceExtended projectAppServiceExtended,
        ICustomerAppServiceExtended customerAppServiceExtended,
        ITaskAppServiceExtended taskAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(projectAppServiceExtended, customerAppServiceExtended, taskAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
