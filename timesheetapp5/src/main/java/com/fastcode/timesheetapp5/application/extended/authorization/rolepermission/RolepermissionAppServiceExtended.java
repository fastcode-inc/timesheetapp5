package com.fastcode.timesheetapp5.application.extended.authorization.rolepermission;

import com.fastcode.timesheetapp5.application.core.authorization.rolepermission.RolepermissionAppService;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.authorization.rolepermission.IRolepermissionRepositoryExtended;
import com.fastcode.timesheetapp5.domain.extended.authorization.usersrole.IUsersroleRepositoryExtended;
import com.fastcode.timesheetapp5.security.JWTAppService;
import org.springframework.stereotype.Service;

@Service("rolepermissionAppServiceExtended")
public class RolepermissionAppServiceExtended
    extends RolepermissionAppService
    implements IRolepermissionAppServiceExtended {

    public RolepermissionAppServiceExtended(
        JWTAppService jwtAppService,
        IUsersroleRepositoryExtended usersroleRepositoryExtended,
        IRolepermissionRepositoryExtended rolepermissionRepositoryExtended,
        IPermissionRepositoryExtended permissionRepositoryExtended,
        IRoleRepositoryExtended roleRepositoryExtended,
        IRolepermissionMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            jwtAppService,
            usersroleRepositoryExtended,
            rolepermissionRepositoryExtended,
            permissionRepositoryExtended,
            roleRepositoryExtended,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
