package com.rc.commons.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
* <p>公共方法类</p>
* <p>产生随机字符(包含数字和大小写的英文字母)</p>
* <p>Copyright: Copyright (c) 2005</p>
* @author Weiwenqi
* @version 1.0
* 该类提供系统通用的功能性调用方法。
*/

public class RandomValue
{

    public RandomValue()
    {
    }
    /**
    * 获得随机字符。
    * @return String 随机产生的字符
    * */

    public static String getRandomValue()
    {
        try
        {
            ArrayList list = new ArrayList();
            char c = 0;
            for (int i = 48; i < 123; i++)
            {
                if (!((i >= 58 && i < 65) || (i >= 91 && i < 97)))
                {
                    c = (char) i;
                    list.add(String.valueOf(c));
                }
            }
            Collections.shuffle(list);
            Random random = new Random();
            return (String) list.get(random.nextInt(62));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "E";
        }
    }

    public static void main(String[] args)
    {
        System.out.println(getRandomValue());
    }

}
