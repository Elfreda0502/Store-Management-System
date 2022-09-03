package com.game.framework.web.service;

import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.game.common.core.domain.entity.SysRole;
import com.game.common.core.domain.model.LoginUser;
import com.game.common.utils.SecurityUtils;
import com.game.common.utils.StringUtils;

/**
 * Custom permission implementation, ss is taken from the initials of SpringSecurity
 *
 * @author Yu Yue
 */
@Service("ss")
public class PermissionService
{

    private static final String ALL_PERMISSION = "*:*:*";


    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    /**
     * Verify whether the User has a certain permission
     *
     * @param permission permission string
     * @return whether User has a certain permission
     */
    public boolean hasPermi(String permission)
    {
        if (StringUtils.isEmpty(permission))
        {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        return hasPermissions(loginUser.getPermissions(), permission);
    }

    /**
     * Verify whether User does not have a certain permission, contrary to hasPermi logic
     *
     * @param permission permission string
     * @return User does not have a permission
     */
    public boolean lacksPermi(String permission)
    {
        return hasPermi(permission) != true;
    }

    /**
     * Verify that User has any of the following permissions
     *
     * @param permissions a list of permissions separated by PERMISSION_NAMES_DELIMETER
     * @return User has any of the following permissions
     */
    public boolean hasAnyPermi(String permissions)
    {
        if (StringUtils.isEmpty(permissions))
        {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        Set<String> authorities = loginUser.getPermissions();
        for (String permission : permissions.split(PERMISSION_DELIMETER))
        {
            if (permission != null && hasPermissions(authorities, permission))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if User has a role
     *
     * @param role role string
     * @return User has a role
     */
    public boolean hasRole(String role)
    {
        if (StringUtils.isEmpty(role))
        {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles()))
        {
            return false;
        }
        for (SysRole sysRole : loginUser.getUser().getRoles())
        {
            String roleKey = sysRole.getRoleKey();
            if (SUPER_ADMIN.equals(roleKey) || roleKey.equals(StringUtils.trim(role)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify that User does not have a role, which is the opposite of isRole logic.
     *
     * @param role role name
     * @return User does not have a role
     */
    public boolean lacksRole(String role)
    {
        return hasRole(role) != true;
    }

    /**
     * Verify if User has any of the following roles
     *
     * @param roles list of roles separated by ROLE_NAMES_DELIMETER
     * Does @return User have any of the following roles
     */
    public boolean hasAnyRoles(String roles)
    {
        if (StringUtils.isEmpty(roles))
        {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles()))
        {
            return false;
        }
        for (String role : roles.split(ROLE_DELIMETER))
        {
            if (hasRole(role))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if permission is included
     *
     * @param permissions list of permissions
     * @param permission permission string
     * @return whether User has a certain permission
     */
    private boolean hasPermissions(Set<String> permissions, String permission)
    {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }
}
