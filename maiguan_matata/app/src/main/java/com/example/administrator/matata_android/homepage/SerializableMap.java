package com.example.administrator.matata_android.homepage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SerializableMap implements Serializable {

    private Map<String,Object> hmap;

    public Map<String,Object> getMap(){
        return hmap;
    }
    public void setMap(Map<String,Object> map){
        this.hmap=map;
    }
    public Object get(String key){
        return hmap.get(key);
    }
}
