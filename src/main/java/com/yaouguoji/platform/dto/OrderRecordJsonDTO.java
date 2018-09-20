package com.yaouguoji.platform.dto;

import lombok.Data;

@Data
public class OrderRecordJsonDTO extends OrderRecordDTO {

    /**
     * 商户类型
     */
    private String shopType;

    /**
     * 商户分店名
     */
    private String branchName;

    /**
     * 商户名称
     */
    private String shopName;

}
