package com.game.store.order.service.impl;

import java.util.List;

import cn.hutool.core.util.RandomUtil;
import com.game.common.core.domain.AjaxResult;
import com.game.common.core.domain.entity.SysUser;
import com.game.common.utils.DateUtils;
import com.game.store.order.domain.StoreOrder;
import com.game.store.order.mapper.StoreOrderMapper;
import com.game.store.order.service.IStoreOrderService;
import com.game.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


/**
 * Order Service business layer processing
 *
 * @author Yu Yue
 * @date 2022-05-22
 */
@Service
public class StoreOrderServiceImpl implements IStoreOrderService
{

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private ISysUserService sysUserService;


    @Autowired
    private StoreOrderMapper storeOrderMapper;

    /**
     * checking order
     *
     * @param id order primary key
     * @return order
     */
    @Override
    public StoreOrder selectStoreOrderById(String id)
    {
        return storeOrderMapper.selectStoreOrderById(id);
    }

    /**
     * Query order list
     *
     * @param storeOrder order
     * @return order
     */
    @Override
    public List<StoreOrder> selectStoreOrderList(StoreOrder storeOrder)
    {
        return storeOrderMapper.selectStoreOrderList(storeOrder);
    }

    /**
     * New order
     *
     * @param storeOrder order
     * @return result
     */
    @Override
    public int insertStoreOrder(StoreOrder storeOrder)
    {
        storeOrder.setCreateTime(DateUtils.getNowDate());
        return storeOrderMapper.insertStoreOrder(storeOrder);
    }

    /**
     * Change Order
     *
     * @param storeOrder order
     * @return result
     */
    @Override
    public int updateStoreOrder(StoreOrder storeOrder)
    {
        String uid = storeOrder.getUid();



        // get User email
// SysUser sysUser = sysUserService.getById(uid);
        SysUser sysUser = sysUserService.selectUserById(Long.valueOf(uid));
        String email = sysUser.getEmail();
        // send email
        String verifyCode = storeOrder.getVerifyCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
// true means to build a mail object that can have attachments
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("Congratulations, the item you ordered is ready!");
            helper.setFrom("smartstore2022@163.com");
            helper.setTo(email);
            helper.setText("Hello, the products you ordered from our smart store have been stocked, please go to the store and show the verification code to the merchant, pick up the goods with the verification code, the VERIFICATION CODE is " + verifyCode + " (this verification code is the only proof of picking up the goods, please keep it safe). Please pick up your order at the store within 7 days of receiving the SMS. Store address: 301 Upper Thomson Rd, 50450 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur, Malaysia");
            storeOrder.setUpdateTime(DateUtils.getNowDate());
            javaMailSender.send(mimeMessage);
            int i = storeOrderMapper.updateStoreOrder(storeOrder);
            return i;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }

    }

    /**
     * Bulk delete orders
     *
     * @param ids the primary key of the order that needs to be deleted
     * @return result
     */
    @Override
    public int deleteStoreOrderByIds(String[] ids)
    {
        return storeOrderMapper.deleteStoreOrderByIds(ids);
    }

    /**
     * Delete order information
     *
     * @param id order primary key
     * @return result
     */
    @Override
    public int deleteStoreOrderById(String id)
    {
        return storeOrderMapper.deleteStoreOrderById(id);
    }
}