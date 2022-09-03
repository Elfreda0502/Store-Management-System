package com.game.web.controller.system;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.game.common.constant.Constants;
import com.game.common.core.domain.AjaxResult;
import com.game.common.core.domain.entity.SysMenu;
import com.game.common.core.domain.entity.SysUser;
import com.game.common.core.domain.model.LoginBody;
import com.game.common.utils.SecurityUtils;
import com.game.framework.web.service.SysLoginService;
import com.game.framework.web.service.SysPermissionService;
import com.game.system.service.ISysMenuService;

/**
 * Login Authentication
 *
 * @author Yu Yue
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * Login method
     *
     * @param loginBody login information
     * @return result
     */
    @CrossOrigin
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();

        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * Get User information
     *
     * @return User information
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();

        Set<String> roles = permissionService.getRolePermission(user);

        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * Get routing information
     *
     * @return routing information
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
