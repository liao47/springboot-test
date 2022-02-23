package com.liao47.util;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.liao47.dto.GachaItem;
import com.liao47.dto.GachaType;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author liaoshiqing
 * @date 2022/2/23 16:32
 */
public class GenshinGachaUtils {

    private static final List<GachaType> GACHA_TYPES = Arrays.asList(
            new GachaType("15", "301", "角色活动祈愿"),
            new GachaType("16", "302", "武器活动祈愿"),
            new GachaType("4", "200", "常驻祈愿"),
            new GachaType("14", "100", "新手祈愿"));

    /**
     * excel转json
     * @param excelPath
     * @param jsonExportDir
     * @param uid
     */
    public static void excelToJson(String excelPath, String jsonExportDir, String uid) {
        JSONObject json = new JSONObject();
        json.put("gachaType", GACHA_TYPES);
        json.put("uid", uid);
        JSONObject gachaLog = new JSONObject();
        json.put("gachaLog", gachaLog);

        File file = new File(excelPath);
        ImportParams importParams = new ImportParams();
        for (int i = 0; i < GACHA_TYPES.size(); i++) {
            importParams.setStartSheetIndex(i);
            List<GachaItem> list = ExcelImportUtil.importExcel(file, GachaItem.class, importParams);
            if (CollectionUtils.isNotEmpty(list)) {
                list.forEach(t -> {
                    t.setUid(uid);
                    t.setItemId("");
                    t.setCount("1");
                    t.setLang("zh-cn");
                });
                gachaLog.put(GACHA_TYPES.get(i).getKey(), list);
            }
        }

        String fileName = jsonExportDir + File.separator + String.format("gachaData-%s.json", uid);
        try {
            File exportDir = new File(jsonExportDir);
            if (!exportDir.exists() && !exportDir.mkdirs()) {
                System.out.println("mkdirs jsonExportDir fail");
            }
            File exportFile = new File(fileName);
            if (!exportFile.exists() && !exportFile.createNewFile()) {
                System.out.println("create new file fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(JSONObject.toJSONString(json, true).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
