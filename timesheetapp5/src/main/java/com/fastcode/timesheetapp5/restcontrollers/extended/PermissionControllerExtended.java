package com.fastcode.timesheetapp5.restcontrollers.extended;

import com.fastcode.timesheetapp5.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.fastcode.timesheetapp5.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.restcontrollers.core.PermissionController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission/extended")
public class PermissionControllerExtended extends PermissionController {

    public PermissionControllerExtended(
        IPermissionAppServiceExtended permissionAppServiceExtended,
        IRolepermissionAppServiceExtended rolepermissionAppServiceExtended,
        IUserspermissionAppServiceExtended userspermissionAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            permissionAppServiceExtended,
            rolepermissionAppServiceExtended,
            userspermissionAppServiceExtended,
            helper,
            env
        );
    }
    //Add your custom code here

}
