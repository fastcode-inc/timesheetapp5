package com.fastcode.timesheetapp5.application.extended.timesheetdetails;

import com.fastcode.timesheetapp5.application.core.timesheetdetails.TimesheetdetailsAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.extended.task.ITaskRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.timesheet.ITimesheetRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.timesheetdetails.ITimesheetdetailsRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("timesheetdetailsAppServiceExtended")
public class TimesheetdetailsAppServiceExtended
    extends TimesheetdetailsAppService
    implements ITimesheetdetailsAppServiceExtended {

    public TimesheetdetailsAppServiceExtended(
        ITimesheetdetailsRepositoryExtended timesheetdetailsRepositoryExtended,
        ITaskRepositoryExtended taskRepositoryExtended,
        ITimesheetRepositoryExtended timesheetRepositoryExtended,
        ITimesheetdetailsMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            timesheetdetailsRepositoryExtended,
            taskRepositoryExtended,
            timesheetRepositoryExtended,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
