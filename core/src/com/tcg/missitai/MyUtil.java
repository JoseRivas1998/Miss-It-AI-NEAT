package com.tcg.missitai;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 2/28/19
 */
public class MyUtil {

    public static int maxIndex(float[] data) {
        float max = 0;
        int maxIndex = 0;
        for (int i = 0; i < data.length; i++) {
            if(max < data[i]) {
                max = data[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

}
