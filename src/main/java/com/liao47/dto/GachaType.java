package com.liao47.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liaoshiqing
 * @date 2022/2/23 16:54
 */
@Data
@AllArgsConstructor
public class GachaType implements Serializable {
    private String id;

    private String key;

    private String name;
}
