
package org.wso2.carbon.password.recovery.rest.api.resources;

import org.wso2.carbon.password.recovery.rest.api.Constants;
import org.wso2.carbon.password.recovery.rest.api.UserCoreRestException;
import org.wso2.carbon.password.recovery.rest.api.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.captcha.mgt.beans.CaptchaInfoBean;
import org.wso2.carbon.identity.mgt.IdentityMgtServiceException;
import org.wso2.carbon.identity.mgt.beans.VerificationBean;
import org.wso2.carbon.password.recovery.rest.api.bean.RecoveryNotification;
import org.wso2.carbon.password.recovery.rest.api.bean.ResetPassword;
import org.wso2.carbon.password.recovery.rest.api.bean.VerifyConfirmation;
import org.wso2.carbon.password.recovery.rest.api.bean.VerifyUser;
import org.wso2.carbon.password.recovery.rest.api.core.UserManager;
import org.wso2.carbon.user.core.UserCoreConstants;
import org.wso2.carbon.user.core.util.UserCoreUtil;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/recover")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
public class UserRecoveryResource extends AbstractResource {
    private static final Log LOG = LogFactory.getLog(UserRecoveryResource.class);
    UserManager userManager;

    public UserRecoveryResource() throws UserCoreRestException {
        userManager = Utils.getUserManager();
    }


    @GET
    @Path("captcha")
    public Response getCaptcha(@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization)
            throws IdentityMgtServiceException {

        CaptchaInfoBean captchaInfoBean;
        try {
            captchaInfoBean = userManager.getCaptcha();
        } catch (Exception e) {
            LOG.error(e);
            LOG.error(e);
            if (e instanceof IdentityMgtServiceException) {
                return handleResponse(ResponseStatus.FAILED, e.getMessage());
            }
            return handleResponse(ResponseStatus.FAILED, "Error while generating captcha");
        }
        return Response.ok(captchaInfoBean).build();
    }

    @PUT
    @Path("verify-user")
    public Response verifyUser(@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
                               VerifyUser verifyUser) {
        VerificationBean verificationBean;
        String tenantDomain = null;
        try {
            if (verifyUser.getUserDomain() == null) {
                verifyUser.setUserDomain(UserCoreConstants.PRIMARY_DEFAULT_DOMAIN_NAME);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("verifyUser : User domain set to default for user :" + verifyUser.getUsername());
                }
            }

            if (verifyUser.getTenantId() == 0) {
                verifyUser.setTenantId(MultitenantConstants.SUPER_TENANT_ID);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("verifyUser : Tenant domain set to super tenant for user :" + verifyUser.getUsername());
                }
            }

            tenantDomain = Utils.getTenantDomain(verifyUser.getTenantId());
            String fullUserName = getFullUserName(verifyUser.getUsername(), verifyUser.getUserDomain(), tenantDomain);

            verificationBean = userManager.verifyUser(fullUserName, verifyUser.getCaptcha(), tenantDomain);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Verify user: IsVerified =" + verificationBean.isVerified() + " User:" + fullUserName);
            }
        } catch (Exception e) {
            LOG.error(e);
            if (e instanceof IdentityMgtServiceException) {
                return handleResponse(ResponseStatus.FAILED, e.getMessage());
            }
            return handleResponse(ResponseStatus.FAILED, "Error while verify user");
        }
        return Response.ok(verificationBean).build();
    }


    @PUT
        @Path("send-notification")
    public Response sendRecoveryNotification(@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
                                             RecoveryNotification recoveryNotification) {

        VerificationBean verificationBean;
        String tenantDomain = null;
        try {

            if (recoveryNotification.getUserDomain() == null) {
                recoveryNotification.setUserDomain(UserCoreConstants.PRIMARY_DEFAULT_DOMAIN_NAME);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("sendRecoveryNotification : User domain set to default for user :" + recoveryNotification.getUsername());
                }
            }

            if (recoveryNotification.getTenantId() == 0) {
                recoveryNotification.setTenantId(MultitenantConstants.SUPER_TENANT_ID);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("sendRecoveryNotification : Tenant domain set to super tenant for user :" + recoveryNotification.getUsername());
                }
            }

            tenantDomain = Utils.getTenantDomain(recoveryNotification.getTenantId());
            String fullUserName = getFullUserName(recoveryNotification.getUsername(), recoveryNotification.getUserDomain(), tenantDomain);


            verificationBean = userManager.sendRecoveryNotification(fullUserName, recoveryNotification.getKey(),
                                                                    recoveryNotification.getNotificationType(), tenantDomain);
        } catch (Exception e) {
            LOG.error(e);
            if (e instanceof IdentityMgtServiceException) {
                return handleResponse(ResponseStatus.FAILED, e.getMessage());
            }
            return handleResponse(ResponseStatus.FAILED, "Error while sending notification");
        }
        return Response.ok(verificationBean).build();
    }

    @PUT
    @Path("verify-code")
    public Response verifyConfirmationCode(@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
                                           VerifyConfirmation verifyConfirmation) {
        VerificationBean verificationBean;
        String tenantDomain = null;
        try {
            if (verifyConfirmation.getUserDomain() == null) {
                verifyConfirmation.setUserDomain(UserCoreConstants.PRIMARY_DEFAULT_DOMAIN_NAME);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("verifyConfirmationCode : User domain set to default for user :" + verifyConfirmation.getUsername());
                }
            }

            if (verifyConfirmation.getTenantId() == 0) {
                verifyConfirmation.setTenantId(MultitenantConstants.SUPER_TENANT_ID);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("verifyConfirmationCode : Tenant domain set to super tenant for user :" + verifyConfirmation.getUsername());
                }
            }

            tenantDomain = Utils.getTenantDomain(verifyConfirmation.getTenantId());
            String fullUserName = getFullUserName(verifyConfirmation.getUsername(), verifyConfirmation.getUserDomain(), tenantDomain);


            verificationBean = userManager.verifyConfirmationCode(fullUserName, verifyConfirmation.getCode(),
                                                                  verifyConfirmation.getCaptcha(), tenantDomain);
        } catch (Exception e) {
            LOG.error(e);
            if (e instanceof IdentityMgtServiceException) {
                return handleResponse(ResponseStatus.FAILED, e.getMessage());
            }
            return handleResponse(ResponseStatus.FAILED, "Error while verifying confirmation code");
        }
        return Response.ok(verificationBean).build();
    }


    @PUT
    @Path("update-password")
    public Response updatePassword(@HeaderParam(Constants.AUTHORIZATION_HEADER) String authorization,
                                   ResetPassword resetPassword) {
        VerificationBean verificationBean;
        String tenantDomain = null;
        try {
            if (resetPassword.getUserDomain() == null) {
                resetPassword.setUserDomain(UserCoreConstants.PRIMARY_DEFAULT_DOMAIN_NAME);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("verifyConfirmationCode : User domain set to default for user :" + resetPassword.getUsername());
                }
            }

            if (resetPassword.getTenantId() == 0) {
                resetPassword.setTenantId(MultitenantConstants.SUPER_TENANT_ID);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("verifyConfirmationCode : Tenant domain set to super tenant for user :" + resetPassword.getUsername());
                }
            }
            tenantDomain = Utils.getTenantDomain(resetPassword.getTenantId());
            String fullUserName = getFullUserName(resetPassword.getUsername(), resetPassword.getUserDomain(), tenantDomain);


            verificationBean = userManager.updatePassword(fullUserName, resetPassword.getConfirmationCode(),
                                                          resetPassword.getNewPassword(), tenantDomain);
        } catch (Exception e) {
            LOG.error(e);
            if (e instanceof IdentityMgtServiceException) {
                return handleResponse(ResponseStatus.FAILED, e.getMessage());
            }
            return handleResponse(ResponseStatus.FAILED, "Error while updating password: ");
        }
        return Response.ok(verificationBean).build();
    }

    private String getFullUserName(String username, String UserDomain, String tenantDomain) {
        String userNameWithUserDomain = UserCoreUtil.addDomainToName(username, UserDomain);
        String fullUserName = UserCoreUtil.addTenantDomainToEntry(userNameWithUserDomain, tenantDomain);
        return fullUserName;
    }
}
