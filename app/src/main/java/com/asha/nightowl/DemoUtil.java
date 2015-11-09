package com.asha.nightowl;

/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class DemoUtil {
    private static final int[] FakeImage = {R.drawable.demo01,R.drawable.demo02,R.drawable.demo03,R.drawable.demo04};
    public static int fetchFakeImage(int positon){

        return FakeImage[positon % FakeImage.length];
    }
}
