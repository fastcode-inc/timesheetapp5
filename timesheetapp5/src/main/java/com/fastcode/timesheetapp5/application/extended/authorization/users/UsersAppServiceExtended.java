package com.fastcode.timesheetapp5.application.extended.authorization.users;

import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion.*;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.*;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.*;
import com.fastcode.timesheetapp5.application.core.authorization.users.UsersAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.core.authorization.userspreference.IUserspreferenceRepository;
import com.fastcode.timesheetapp5.domain.extended.authorization.users.IUsersRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("usersAppServiceExtended")
public class UsersAppServiceExtended extends UsersAppService implements IUsersAppServiceExtended {

    public UsersAppServiceExtended(
        IDashboardversionRepository dashboardversionRepository,
        IReportversionRepository reportversionRepository,
        IDashboardversionreportRepository reportDashboardRepository,
        IUsersRepositoryExtended usersRepositoryExtended,
        IUserspreferenceRepository userspreferenceRepository,
        IUsersMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            dashboardversionRepository,
            reportversionRepository,
            reportDashboardRepository,
            usersRepositoryExtended,
            userspreferenceRepository,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
