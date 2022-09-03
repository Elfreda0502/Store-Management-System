package com.game.framework.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.game.common.core.domain.entity.SysUser;
import com.game.common.core.domain.model.LoginUser;
import com.game.common.enums.UserStatus;
import com.game.common.exception.ServiceException;
import com.game.common.utils.StringUtils;
import com.game.system.service.ISysUserService;

/**
 * User verification processing
 *
 * @author Yu Yue
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user))
        {
            log.info("Login User: {} does not exist.", username);
            throw new ServiceException("Login User:" + username + " does not exist");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("Login User: {} has been removed.", username);
            throw new ServiceException("Sorry, your account:" + username + " has been deleted");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("Login User: {} disabled.", username);
            throw new ServiceException("Sorry, your account: " + username + " terminated");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
