package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guru implements Serializable {
    private String id;
    private String head_img;
    private String dharma;
    private String name;
    private String sex;
    private Date create_date;
    private String status;
    private String other;
}
