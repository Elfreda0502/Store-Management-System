package com.game.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.game.common.config.RuoYiConfig;
import com.game.common.utils.StringUtils;

/**
 * Home
 *
 * @author Yu Yue
 */
@RestController
public class SysIndexController
{
    
    @Autowired
    private RuoYiConfig ruoyiConfig;

    /**
     * visit home page, prompt
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("Welcome to {}backend management framework, current version: v{}, please visit through the front-end address", ruoyiConfig.getName(), ruoyiConfig.getVersion());
    }
}
