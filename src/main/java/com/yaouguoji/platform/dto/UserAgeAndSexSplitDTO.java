package com.yaouguoji.platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAgeAndSexSplitDTO implements Serializable {
    private static final long serialVersionUID = 4275770536195910071L;
    private Integer sex;
    private Integer count0_13;
    private Integer count13_18;
    private Integer count18_25;
    private Integer count25_35;
    private Integer count35_50;
    private Integer count50_65;
    private Integer countOver65;
}
