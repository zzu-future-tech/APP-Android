package com.futuretech.closet.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * 处理Attribute属性值的字符串
 */
public class AttributeUtils {

    public static String[] attribute = new String[]{"全选","工作", "休闲", "运动", "其他"};

    /**
     * List{"工作","休闲"}->"工作|休闲"
     * @param list List{"工作","休闲"}
     * @return "工作|休闲"
     */
    public static String Stringlist2String(List list){
        return  null;
    }

    /**
     * List{"1","2"}->"工作|休闲"
     * @param list List{"1","2"}
     * @return "工作|休闲"
     */
    public static String Idlist2String(List list){
        return  null;
    }

    /**
     * "工作|休闲"->List{"工作","休闲"}
     * @param str "工作|休闲"
     * @return List{"工作","休闲"}
     */
    public static List string2StringList(String str){
        return splitString(str);
    }

    /**
     * "工作|休闲"->List{"1","2"}
     * @param str "工作|休闲"
     * @return List{"1","2"}
     */
    public static List string2IdList(String str){
        return stringList2IdList(splitString(str));
    }

    private static List<String> splitString(String str){
        String[] ss = str.split("\\|");
        List<String> stringList = Arrays.asList(ss);
        //Log.d(TAG, "splitString: "+stringList);
        return stringList;
    }

    private static List stringList2IdList(List<String> list){
        List idList = new ArrayList();
        for(int i=0;i<list.size();i++){
            for(int j=0;j<attribute.length;j++){
                if(list.get(i).equals(attribute[j])){
                    idList.add(j);
                }
            }
        }
        //Log.d(TAG, "stringList2IdList: "+idList);
        return idList;
    }
}
