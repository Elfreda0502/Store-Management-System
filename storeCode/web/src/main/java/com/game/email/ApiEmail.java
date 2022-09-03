package com.game.email;

import cn.hutool.core.util.RandomUtil;
import com.game.common.core.domain.AjaxResult;
import com.game.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: storeCode
 * @description: ddd
 * @author: BigSmart
 * @create: 2022-07-12 17:16
 **/
@RequestMapping("/email")
@RestController()
public class ApiEmail {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    public static Map<String,String> registerMap = new HashMap<>();

    @GetMapping("/sendCode")
    public AjaxResult sendCode(@RequestParam String email){

        String code = RandomUtil.randomNumbers(4);
        registerMap.put(email,code);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // true表示构建一个可以带附件的邮件对象
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("Store");
            helper.setFrom("1058275023@qq.com");
            helper.setTo(email);
            helper.setText("Hello, the products you ordered from our smart store have been stocked, please go to the store and show the verification code to the merchant, pick up the goods with the verification code, the VERIFICATION CODE is " + code +" (this verification code is the only proof of picking up the goods, please keep it safe). Please pick up your order at the store within 7 days of receiving the SMS. Store address: 301 Upper Thomson Rd, 50450 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur, Malaysia");
        } catch (Exception e) {
            System.out.println(e);
            return AjaxResult.error("Send Fail");
        }
        javaMailSender.send(mimeMessage);
        return AjaxResult.success("Send Success");
    }
}
