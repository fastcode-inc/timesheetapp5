package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.status.IStatusAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.timesheet.ITimesheetAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.timesheetstatus.ITimesheetstatusAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.TimesheetstatusController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timesheetstatus/extended")
public class TimesheetstatusControllerExtended extends TimesheetstatusController {

    public TimesheetstatusControllerExtended(
        ITimesheetstatusAppServiceExtended timesheetstatusAppServiceExtended,
        IStatusAppServiceExtended statusAppServiceExtended,
        ITimesheetAppServiceExtended timesheetAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(timesheetstatusAppServiceExtended, statusAppServiceExtended, timesheetAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
