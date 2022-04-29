package com.feng.socket.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName color
 */
@Data
public class Color implements Serializable {
    /**
     * 颜色id
     */
    private Integer cId;

    /**
     *  颜色名称
     */
    private String color;

    private static final long serialVersionUID = 1L;

    public Color(Integer cId, String color) {
        this.cId = cId;
        this.color = color;
    }
}