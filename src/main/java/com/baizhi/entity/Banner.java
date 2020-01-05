package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner implements Serializable {
    @Excel(name = "id")
    private String id;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "图片",type = 2,width = 50,height = 20)
    private String img;
    @Excel(name = "日期",format = "yyyy-MM-dd")
    private Date create_date;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "其他")
    private String other;
}
