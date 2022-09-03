package com.game.leaving.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.game.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @program: RuoYi-Vue-master
 * @description: ddd
 * @author: Yu Yue
 * @create: 2022-05-24 11:38
 **/
@Data
public class SysLeavingVo {

    private Long id;


    @Excel(name = "Userid")
    private Long uid;

    private String nickname;


    @Excel(name = "留言信息")
    private String leavingMessage;


    @Excel(name = "回复信息")
    private String replyMessage;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "留言时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date leavingTime;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "回复时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;
}
