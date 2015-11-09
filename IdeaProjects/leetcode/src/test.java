/**
 * Created by yiyangtan on 10/19/15.
 */
import java.util.*;
public class test {
    public static void main(String [] args) {
        int [] nums = {1,3,3,4,5,6,7,8};
        System.out.print(md(nums));
    }
    public static List<Integer> md(int[]nums){
        List<Integer> arr = new ArrayList<>();
        int d1=0;
        int d2=0;
        int count = 1;
        for(int i:nums){
            d1 += i*i-count*count;
            d2 += i-count;
            count++;
        }
        //(y+x)=d1/d2;
        //y-x = d2;
        //y = (d1/d2+d2)/2;
        //x = y-d2;
        int y = (d1/d2+d2)/2;
        int x = y-d2;
        arr.add(x);
        arr.add(y);
        return arr;
    }
}
