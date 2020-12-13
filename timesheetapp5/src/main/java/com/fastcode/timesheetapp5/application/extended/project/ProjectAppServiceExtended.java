package com.fastcode.timesheetapp5.application.extended.project;

import com.fastcode.timesheetapp5.application.core.project.ProjectAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.extended.customer.ICustomerRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.project.IProjectRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("projectAppServiceExtended")
public class ProjectAppServiceExtended extends ProjectAppService implements IProjectAppServiceExtended {

    public ProjectAppServiceExtended(
        IProjectRepositoryExtended projectRepositoryExtended,
        ICustomerRepositoryExtended customerRepositoryExtended,
        IProjectMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(projectRepositoryExtended, customerRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
