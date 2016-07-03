package org.wso2.carbon.password.recovery.rest.api.core.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
//import org.wso2.carbon.hierarchical.tenant.mgt.FeatureStoreManager;
//import org.wso2.carbon.hierarchical.tenant.mgt.RoleManager;
//import UserManager;
//import org.wso2.carbon.hierarchical.tenant.mgt.UserStoreManager;
import org.wso2.carbon.password.recovery.rest.api.core.UserManager;
import org.wso2.carbon.user.core.service.RealmService;


/**
 * @scr.component name="password.recovery.rest.api.core.component" immediate="true"
 * @scr.reference name="user.realmservice.default"
 * interface="org.wso2.carbon.user.core.service.RealmService"
 * cardinality="1..1" policy="dynamic" bind="setRealmService"
 * unbind="unsetRealmService"
 */
public class PasswordRecoveryServiceComponent {
    private static final Log LOGGER
            = LogFactory.getLog(PasswordRecoveryServiceComponent.class);
    private static RealmService realmService;

    public static RealmService getRealmService() {
        return realmService;
    }

    protected void setRealmService(RealmService realmService) {
        this.realmService = realmService;
    }

    protected void activate(ComponentContext context) {
        BundleContext bundleContext = context.getBundleContext();
        bundleContext.registerService(UserManager.class.getName(), new UserManager(), null);
        LOGGER.info("Password Recovery ServiceComponent bundle is activated");
    }

    protected void deactivate(ComponentContext context) {
        LOGGER.info("Password Recovery ServiceComponent bundle is deactivated");
    }

    protected void unsetRealmService(RealmService realmService) {
        setRealmService(null);
    }
}