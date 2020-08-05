package com.rest.qa.testUtil;

import com.rest.qa.base.BaseClass;
import org.json.JSONArray;
import org.json.JSONObject;

public class testUtil {
    public static JSONObject responseJson;
    public static String getValueByJPath( JSONObject responseJson,String jpath){
        Object obj = responseJson;
        for(String s : jpath.split("/"))
            if(!s.isEmpty())
                if(!(s.contains("[") || s.contains("]")))
                    obj = ((JSONObject) obj).get(s);
                else if(s.contains("[") || s.contains("]"))
                    obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
        return obj.toString();
    }
}
