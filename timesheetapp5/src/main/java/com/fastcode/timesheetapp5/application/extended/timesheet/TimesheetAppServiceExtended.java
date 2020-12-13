package com.fastcode.timesheetapp5.application.extended.timesheet;

import com.fastcode.timesheetapp5.application.core.timesheet.TimesheetAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.timesheet.ITimesheetRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("timesheetAppServiceExtended")
public class TimesheetAppServiceExtended extends TimesheetAppService implements ITimesheetAppServiceExtended {

    public TimesheetAppServiceExtended(
        ITimesheetRepositoryExtended timesheetRepositoryExtended,
        IUsersRepositoryExtended usersRepositoryExtended,
        ITimesheetMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(timesheetRepositoryExtended, usersRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
