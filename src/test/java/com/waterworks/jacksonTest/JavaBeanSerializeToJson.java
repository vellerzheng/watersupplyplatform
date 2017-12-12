package com.waterworks.jacksonTest;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

public class JavaBeanSerializeToJson {

    @Test
    public void JavaBeanSerializeToJsonTest() throws Exception {
        // 使用ObjectMapper来转化对象为Json
        ObjectMapper mapper = new ObjectMapper();
        // 添加功能，让时间格式更具有可读性
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(dateFormat);

        Country country = new Country("China");
        country.setBirthDate(dateFormat.parse("1949-10-01"));
        country.setLakes(new String[] { "Qinghai Lake", "Poyang Lake",
                "Dongting Lake", "Taihu Lake" });

        List<String> nation = new ArrayList<String>();
        nation.add("Han");
        nation.add("Meng");
        nation.add("Hui");
        nation.add("WeiWuEr");
        nation.add("Zang");
        country.setNation(nation);

        Province province = new Province();
        province.name = "Shanxi";
        province.population = 37751200;
        Province province2 = new Province();
        province2.name = "ZheJiang";
        province2.population = 55080000;
        List<Province> provinces = new ArrayList<Province>();
        provinces.add(province);
        provinces.add(province2);
        country.setProvinces(provinces);

        country.addTraffic("Train(KM)", 112000);
        country.addTraffic("HighWay(KM)", 4240000);
        // 为了使JSON视觉上的可读性，增加一行如下代码，注意，在生产中不需要这样，因为这样会增大Json的内容
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // 配置mapper忽略空属性
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        // 默认情况，Jackson使用Java属性字段名称作为 Json的属性名称,也可以使用Jackson annotations(注解)改变Json属性名称
        mapper.writeValue(new File("country.json"), country);
        String jsonList = mapper.writeValueAsString(country);
        System.out.println(jsonList);
    }


}
