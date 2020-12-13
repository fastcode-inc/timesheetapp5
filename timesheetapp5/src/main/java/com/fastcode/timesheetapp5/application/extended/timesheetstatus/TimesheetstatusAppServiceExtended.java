package com.fastcode.timesheetapp5.application.extended.timesheetstatus;

import com.fastcode.timesheetapp5.application.core.timesheetstatus.TimesheetstatusAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.extended.status.IStatusRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.timesheet.ITimesheetRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.timesheetstatus.ITimesheetstatusRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("timesheetstatusAppServiceExtended")
public class TimesheetstatusAppServiceExtended
    extends TimesheetstatusAppService
    implements ITimesheetstatusAppServiceExtended {

    public TimesheetstatusAppServiceExtended(
        ITimesheetstatusRepositoryExtended timesheetstatusRepositoryExtended,
        IStatusRepositoryExtended statusRepositoryExtended,
        ITimesheetRepositoryExtended timesheetRepositoryExtended,
        ITimesheetstatusMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            timesheetstatusRepositoryExtended,
            statusRepositoryExtended,
            timesheetRepositoryExtended,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
