package com.game.common.core.domain.model;

import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.alibaba.fastjson.annotation.JSONField;
import com.game.common.core.domain.entity.SysUser;

/**
 * Login User identity permission
 *
 * @author Yu Yue
 */
public class LoginUser implements UserDetails
{
    private static final long serialVersionUID = 1L;

    /**
     *UserID
     */
    private Long userId;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * User unique identifier
     */
    private String token;

    /**
     * Log in time
     */
    private Long loginTime;

    /**
     * Expiration
     */
    private Long expireTime;

    /**
     * Login IP address
     */
    private String ipaddr;

    /**
     * Login location
     */
    private String loginLocation;

    /**
     * browser type
     */
    private String browser;

    /**
     * operating system
     */
    private String os;

    /**
     * list of permissions
     */
    private Set<String> permissions;

    /**
     * User information
     */
    private SysUser user;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public LoginUser()
    {
    }

    public LoginUser(SysUser user, Set<String> permissions)
    {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(Long userId, Long deptId, SysUser user, Set<String> permissions)
    {
        this.userId = userId;
        this.deptId = deptId;
        this.user = user;
        this.permissions = permissions;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getUserName();
    }

    /**
     * Whether the account has not expired, the expiration cannot be verified
     */
    @JSONField(serialize=false)
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * Specify whether the User is unlocked, the locked User cannot be authenticated
     *
     * @return
     */
    @JSONField(serialize=false)
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * Indicates whether the User's credentials (password) have expired, expired credentials prevent authentication
     *
     * @return
     */
    @JSONField(serialize=false)
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * Whether it is available, disabled User cannot be authenticated
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled()
    {
        return true;
    }

    public Long getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Long loginTime)
    {
        this.loginTime = loginTime;
    }

    public String getIpaddr()
    {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr)
    {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation()
    {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation)
    {
        this.loginLocation = loginLocation;
    }

    public String getBrowser()
    {
        return browser;
    }

    public void setBrowser(String browser)
    {
        this.browser = browser;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public Long getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Long expireTime)
    {
        this.expireTime = expireTime;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
    }

    public SysUser getUser()
    {
        return user;
    }

    public void setUser(SysUser user)
    {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }
}
