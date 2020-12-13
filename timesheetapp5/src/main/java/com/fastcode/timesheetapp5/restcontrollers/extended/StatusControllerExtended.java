package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.status.IStatusAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.timesheetstatus.ITimesheetstatusAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.StatusController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status/extended")
public class StatusControllerExtended extends StatusController {

    public StatusControllerExtended(
        IStatusAppServiceExtended statusAppServiceExtended,
        ITimesheetstatusAppServiceExtended timesheetstatusAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(statusAppServiceExtended, timesheetstatusAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
