package com.liao47.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liaoshiqing
 * @date 2022/2/23 16:24
 */
@Data
public class GachaItem implements Serializable {
    private String uid;

    @JSONField(name = "gacha_type")
    private String gachaType;

    @JSONField(name = "item_id")
    private String itemId;

    private String count = "1";

    @Excel(name = "时间")
    private String time;

    @Excel(name = "名称")
    private String name;

    private String lang = "zh-cn";

    @JSONField(name = "item_type")
    @Excel(name = "类别")
    private String itemType;

    @JSONField(name = "rank_type")
    @Excel(name = "星级")
    private String rankType;

    private String id;
}
