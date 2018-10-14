package com.yaouguoji.platform.util;

import com.yaouguoji.platform.entity.UserSexAndAgeEntity;

import java.math.BigDecimal;
import java.util.Map;

public class ShowUserInfoToShopUtil {
    public static String nameSplit(int name[], int i) {
        String ages;
        if (i == 0) {
            ages = name[0] +"_"+ name[i + 1];
        } else if (i == name.length - 1) {
            ages = "over" + name[i];
        } else {
            ages = (name[i] +1)+ "_" + name[i + 1];
        }
        return ages;
    }

    public static void setSexAndAge(int i, Map<String, Map<String, Integer>> ageAndSex, UserSexAndAgeEntity userSexAndAgeEntity, String ages) {
        if (userSexAndAgeEntity.getSex() == 1) {
            Map<String, Integer> male = ageAndSex.get("male");
            male.put(ages, ageAndSex.get("male").get(ages) + userSexAndAgeEntity.getNumberOfPeople());
            ageAndSex.put("male", male);
        } else if (userSexAndAgeEntity.getSex() == 2) {
            Map<String, Integer> female = ageAndSex.get("female");
            female.put(ages, ageAndSex.get("female").get(ages) + userSexAndAgeEntity.getNumberOfPeople());
            ageAndSex.put("female", female);
            System.out.println("22");
        }
    }
    public static void setFrequency(int arr[], BigDecimal average,double ratio){
        if(average==null||average.compareTo(new BigDecimal(4))==-1){
           arr[0]=0;
           arr[1]=3;
           arr[2]=6;
        }
        else {
            arr[0]=0;
            arr[1]=average.divide(new BigDecimal((1-ratio)/1),2).ROUND_HALF_UP;
            arr[2]=average.divide(new BigDecimal((1+ratio)/1),2).ROUND_HALF_UP;
        }
    }
}
