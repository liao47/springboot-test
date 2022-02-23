package com.liao47;

import com.liao47.util.GenshinGachaUtils;
import org.junit.jupiter.api.Test;

/**
 * @author liaoshiqing
 * @date 2022/2/23 17:02
 */
class GenshinGachaUtilsTest {
    @Test
    void test() {
        GenshinGachaUtils.excelToJson("C:\\Users\\ligeit\\Documents\\My Knowledge\\Data\\liao647@foxmail.com\\My " +
                "Notes\\Notes\\bilibili\\gachaData-501957937.json " +
                "gachaExport-2022010900333_Attachments\\gachaExport-20220109003333.xlsx",
                "C:\\work\\export\\genshin",
                "2333");
    }
}
