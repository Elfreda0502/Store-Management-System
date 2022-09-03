package com.game.leaving.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.game.common.utils.SecurityUtils;
import com.game.common.utils.poi.ExcelUtil;
import com.game.leaving.domain.SysLeaving;
import com.game.leaving.param.SysLeavingVo;
import com.game.leaving.service.ISysLeavingService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.game.common.core.page.TableDataInfo;

/**
 * 留言Controller
 * 
 * @author Yu Yue
 * @date 2022-05-24
 */
@RestController
@RequestMapping("/store/leaving")
public class SysLeavingController extends BaseController
{
    @Autowired
    private ISysLeavingService sysLeavingService;

    /**
     * 查询留言列表
     */
//    @PreAuthorize("@ss.hasPermi('web:leaving:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysLeaving sysLeaving)
    {
        startPage();
        List<SysLeaving> list = sysLeavingService.selectSysLeavingList(sysLeaving);
        List<SysLeavingVo> voList = new ArrayList<>();
        for (SysLeaving leaving : list) {
            SysLeavingVo sysLeavingVo = new SysLeavingVo();
            BeanUtils.copyProperties(leaving,sysLeavingVo);
            String username = SecurityUtils.getUsername();
            sysLeavingVo.setNickname(username);
            voList.add(sysLeavingVo);
        }
        return getDataTable(voList);
    }

    /**
     * 导出留言列表
     */
    @PreAuthorize("@ss.hasPermi('web:leaving:export')")
    @Log(title = "留言", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysLeaving sysLeaving)
    {
        List<SysLeaving> list = sysLeavingService.selectSysLeavingList(sysLeaving);
        ExcelUtil<SysLeaving> util = new ExcelUtil<SysLeaving>(SysLeaving.class);
        util.exportExcel(response, list, "留言Data");
    }

    /**
     * 获取留言详细信息
     */
    @PreAuthorize("@ss.hasPermi('web:leaving:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysLeavingService.selectSysLeavingById(id));
    }

    /**
     * 新增留言
     */
//    @PreAuthorize("@ss.hasPermi('web:leaving:add')")
    @Log(title = "留言", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysLeaving sysLeaving)
    {
        Long userId = SecurityUtils.getUserId();
        sysLeaving.setUid(userId);
        sysLeaving.setLeavingTime(new Date());
        return toAjax(sysLeavingService.insertSysLeaving(sysLeaving));
    }

    /**
     * 修改留言
     */
    @PreAuthorize("@ss.hasPermi('web:leaving:edit')")
    @Log(title = "留言", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysLeaving sysLeaving)
    {
        sysLeaving.setReplyTime(new Date());
        return toAjax(sysLeavingService.updateSysLeaving(sysLeaving));
    }

    /**
     * 删除留言
     */
    @PreAuthorize("@ss.hasPermi('web:leaving:remove')")
    @Log(title = "留言", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysLeavingService.deleteSysLeavingByIds(ids));
    }
}
