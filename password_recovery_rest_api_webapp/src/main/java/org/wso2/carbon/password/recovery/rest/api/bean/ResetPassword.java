package org.wso2.carbon.password.recovery.rest.api.bean;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "username",
        "userDomain",
        "confirmationCode",
        "newPassword",
        "tenantId"
})
@XmlRootElement(name = "resetPassword")
public class ResetPassword {
    @XmlElement(required = true)
    private String username;

    @XmlElement(required = true)
    private String confirmationCode;

    @XmlElement(required = true)
    private String newPassword;

    @XmlElement(required = false)
    private int tenantId;

    @XmlElement(required = false)
    private String userDomain;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }
}