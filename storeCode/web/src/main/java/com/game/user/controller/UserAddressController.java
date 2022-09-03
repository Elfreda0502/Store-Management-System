package com.game.user.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.game.common.core.domain.entity.SysUser;
import com.game.common.utils.SecurityUtils;
import com.game.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.game.common.annotation.Log;
import com.game.common.core.controller.BaseController;
import com.game.common.core.domain.AjaxResult;
import com.game.common.enums.BusinessType;
import com.game.user.domain.UserAddress;
import com.game.store.service.IUserAddressService;
import com.game.common.utils.poi.ExcelUtil;
import com.game.common.core.page.TableDataInfo;

/**
 * UseraddressController
 *
 * @author Yu Yue
 * @date 2022-05-13
 */
@RestController
@RequestMapping("/user/address")
public class UserAddressController extends BaseController
{
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询Useraddress列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserAddress userAddress)
    {
        startPage();
        String username = SecurityUtils.getUsername();
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        Long userId = sysUser.getUserId();
        userAddress.setUserId(userId);
        List<UserAddress> list = userAddressService.selectUserAddressList(userAddress);
        return getDataTable(list);
    }



    /**
     * 导出Useraddress列表
     */
    @Log(title = "Useraddress", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserAddress userAddress)
    {
        List<UserAddress> list = userAddressService.selectUserAddressList(userAddress);
        ExcelUtil<UserAddress> util = new ExcelUtil<UserAddress>(UserAddress.class);
        util.exportExcel(response, list, "UseraddressData");
    }

    /**
     * 获取Useraddress详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userAddressService.selectUserAddressById(id));
    }

    /**
     * 新增Useraddress
     */
    @Log(title = "Useraddress", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserAddress userAddress)
    {

        String username = SecurityUtils.getUsername();
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        Long userId = sysUser.getUserId();
        userAddress.setUserId(userId);
        return toAjax(userAddressService.insertUserAddress(userAddress));
    }

    /**
     * 修改Useraddress
     */
    @Log(title = "Useraddress", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserAddress userAddress)
    {
        return toAjax(userAddressService.updateUserAddress(userAddress));
    }

    /**
     * 删除Useraddress
     */
    @Log(title = "Useraddress", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userAddressService.deleteUserAddressByIds(ids));
    }

    /**
     * 删除Useraddress
     */
    @Log(title = "Useraddress", businessType = BusinessType.DELETE)
    @GetMapping("/del/{ids}")
    public AjaxResult remove2(@PathVariable Long[] ids)
    {
        return toAjax(userAddressService.deleteUserAddressByIds(ids));
    }
}
