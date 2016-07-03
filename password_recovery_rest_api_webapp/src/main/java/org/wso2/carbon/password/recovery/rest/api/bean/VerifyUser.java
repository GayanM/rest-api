
package org.wso2.carbon.password.recovery.rest.api.bean;

import org.wso2.carbon.captcha.mgt.beans.CaptchaInfoBean;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "username",
        "userDomain",
        "tenantId",
        "captcha"
})
@XmlRootElement(name = "verifyUser")
public class VerifyUser {
    @XmlElement(required = true)
    private String username;

    @XmlElement(required = false)
    private int tenantId;

    @XmlElement(required = false)
    private String userDomain;

    @XmlElement(required = false)
    private CaptchaInfoBean captcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public CaptchaInfoBean getCaptcha() {
        return captcha;
    }

    public void setCaptcha(CaptchaInfoBean captcha) {
        this.captcha = captcha;
    }

    public String getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }
}