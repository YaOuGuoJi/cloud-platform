package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuwen
 * @date 2018/9/29
 */
@Data
public class OrderRecordRequest implements Serializable {

    private static final long serialVersionUID = -908472319393867891L;

    /**
     * 排序方式
     * @see com.yaouguoji.platform.constant.OrderRankType
     */
    private Integer type;

    /**
     * 返回数据限制
     */
    private Integer limit;

    /**
     * 分页页码
     */
    private Integer pageNum;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 对象的id: userId, shopId, orderId, areaId
     */
    private Integer id;

}