package com.xxx.xing.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xing
 * @Created by 2017-04-20 下午4:11.
 */
public class ArraysUtil {
    /**
     * 比较2个集合相同的元素和不同元素
     *
     * @param list1
     * @param list2
     * @return a 只有list1中有的元素 ，b 只有list2中有的元素
     */
    public static Map<String, List> getdiff(List list1, List list2) {
        Map<String, List> map = new HashMap<>();
        List justList1 = new ArrayList();
        List justList2 = new ArrayList();
        if (list1.size() == 0) {
            map.put("a", list1);
        }
        if (list2.size() == 0) {
            map.put("b", list2);
        }
        justList1.addAll(list1);
        justList1.addAll(list2);
        justList2.addAll(list1);
        justList2.addAll(list2);
        justList1.removeAll(list2); //lis1中有的
        justList2.removeAll(list1);
        map.put("a", justList1);
        map.put("b", justList2);
        return map;
    }


}
