package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.task.ITaskAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.timesheet.ITimesheetAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.timesheetdetails.ITimesheetdetailsAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.TimesheetdetailsController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timesheetdetails/extended")
public class TimesheetdetailsControllerExtended extends TimesheetdetailsController {

    public TimesheetdetailsControllerExtended(
        ITimesheetdetailsAppServiceExtended timesheetdetailsAppServiceExtended,
        ITaskAppServiceExtended taskAppServiceExtended,
        ITimesheetAppServiceExtended timesheetAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(timesheetdetailsAppServiceExtended, taskAppServiceExtended, timesheetAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
