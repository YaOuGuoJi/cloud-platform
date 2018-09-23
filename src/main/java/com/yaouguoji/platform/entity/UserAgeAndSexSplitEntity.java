package com.yaouguoji.platform.entity;

import lombok.Data;

@Data
public class UserAgeAndSexSplitEntity {
    private Integer sex;
    private Integer count0_13;
    private Integer count13_18;
    private Integer count18_25;
    private Integer count25_35;
    private Integer count35_50;
    private Integer count50_65;
    private Integer countOver65;
}
