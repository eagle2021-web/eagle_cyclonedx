package com.eagle.mysql.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class GavlDTO {
    @JSONField(ordinal = 1)
    private String groupId;
    @JSONField(ordinal = 3)
    private String artifactId;
    @JSONField(ordinal = 2)
    private String version;
    @JSONField(ordinal = 5)
    private Integer businessTag;
    @JSONField(ordinal = 6)
    private String type;
    @JSONField(ordinal = 4)
    private String language;

    // type 业务 语言 名称 版本 组织
    public static boolean checkKeyValue(GavlDTO dto) {
        String tag = dto.businessTag == null ? "" : String.valueOf(dto.businessTag);
        int bits = 0;
        List<String> collect = Stream.of(dto.getGroupId(), dto.getVersion(), dto.getArtifactId(), dto.getLanguage(), tag, dto.getType())
                .collect(Collectors.toList());
        for (String s : collect) {
            int bit = StringUtils.isNotBlank(s) ? 1 : 0;
            bits = (bits << 1) + bit;
        }
        String binaryString = String.format("%6s", Integer.toBinaryString(bits)).replace(' ', '0');
        System.out.printf("0b%s%n", binaryString);
        return Integer.bitCount(bits + 1) == 1;
    }

    public static void main(String[] args) {
        GavlDTO gavlDTO = new GavlDTO();

        GavlDTO.checkKeyValue(gavlDTO);
        System.out.println();
    }
}
