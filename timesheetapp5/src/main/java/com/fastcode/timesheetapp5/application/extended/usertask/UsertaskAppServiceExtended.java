package com.fastcode.timesheetapp5.application.extended.usertask;

import com.fastcode.timesheetapp5.application.core.usertask.UsertaskAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.task.ITaskRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.usertask.IUsertaskRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("usertaskAppServiceExtended")
public class UsertaskAppServiceExtended extends UsertaskAppService implements IUsertaskAppServiceExtended {

    public UsertaskAppServiceExtended(
        IUsertaskRepositoryExtended usertaskRepositoryExtended,
        ITaskRepositoryExtended taskRepositoryExtended,
        IUsersRepositoryExtended usersRepositoryExtended,
        IUsertaskMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(usertaskRepositoryExtended, taskRepositoryExtended, usersRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
