package com.fastcode.timesheetapp5.application.extended.authorization.permission;

import com.fastcode.timesheetapp5.application.core.authorization.permission.PermissionAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("permissionAppServiceExtended")
public class PermissionAppServiceExtended extends PermissionAppService implements IPermissionAppServiceExtended {

    public PermissionAppServiceExtended(
        IPermissionRepositoryExtended permissionRepositoryExtended,
        IPermissionMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(permissionRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
