package com.yaouguoji.platform.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderRecordJsonDTO extends OrderRecordDTO implements Serializable {

    private static final long serialVersionUID = 3665115151076098408L;
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
