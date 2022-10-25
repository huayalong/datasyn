package com.ruoyi.common.utils;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class CacheMap {

    public static Map<String, JdbcTemplate> map = new HashMap<>();

    public static Map<String,JdbcTemplate> getMap(){
        return map;
    }

}
