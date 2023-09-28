package com.eagle.mysql.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum GenderEnum {
    MALE("男"),
    FEMALE("女");
    @EnumValue
    private String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }
}
