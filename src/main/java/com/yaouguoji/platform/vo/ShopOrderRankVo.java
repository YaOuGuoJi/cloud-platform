package com.yaouguoji.platform.vo;

import com.yaouguoji.platform.dto.ShopInfoDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShopOrderRankVo implements Serializable {

    private static final long serialVersionUID = 1514838332075044027L;

    /**
     * 商户对象
     */
    private ShopInfoDTO shopInfoDTO;

    /**
     * 数据
     */
    private Object data;
}
