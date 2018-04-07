package com.zc.usefixedheaderscrollview.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2018/4/7.
 */

public class InitDataUtil {

    public static List<Integer> getData(){
        List<Integer> mData = new ArrayList<>();
        for (int i = 0;i<20;i++){
            mData.add(i);
        }
        return mData;
    }

}
