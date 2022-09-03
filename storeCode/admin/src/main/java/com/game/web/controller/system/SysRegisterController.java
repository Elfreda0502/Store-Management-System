package com.game.web.controller.system;

import cn.hutool.core.util.RandomUtil;
import com.game.email.ApiEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import com.game.common.core.controller.BaseController;
import com.game.common.core.domain.AjaxResult;
import com.game.common.core.domain.model.RegisterBody;
import com.game.common.utils.StringUtils;
import com.game.framework.web.service.SysRegisterService;
import com.game.system.service.ISysConfigService;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Registration verification
 *
 * @author Yu Yue
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;


    @Autowired
    JavaMailSender javaMailSender;


    public static Map<String,String> registerMap = new HashMap<>();

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        String email = user.getEmail();
        String rawCode = registerMap.get(email);
        if (rawCode == null){
            return error("This email no send code");
        }
        if (!rawCode.equals(user.getCode())){
            return error("This code no correct");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }



    @GetMapping("/sendCode")
    public AjaxResult sendCode(@RequestParam String email){


        String code = RandomUtil.randomNumbers(4);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // true表示构建一个可以带附件的邮件对象
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("Register as a new member of the Smart Store");
            helper.setFrom("smartstore2022@163.com");
            helper.setTo(email);
            helper.setText("Dear member, hello, welcome to use our Smart Store Management System, your verification code for this registration is " + code +", valid for 10 minutes, please submit the verification code in time in the page to complete the verification.");
        } catch (Exception e) {
            System.out.println(e);
            return AjaxResult.error("Send Fail");
        }

        registerMap.put(email,code);
        javaMailSender.send(mimeMessage);
        return AjaxResult.success("Send Success");
    }



}
