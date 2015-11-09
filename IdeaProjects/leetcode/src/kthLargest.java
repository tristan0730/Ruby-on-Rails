import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by yiyangtan on 9/20/15.
 */
public class kthLargest {
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(k, Collections.reverseOrder());
        for(int i=0;i<nums.length;i++){
            pq.offer(nums[i]);
        }
        int result=0;
        for(Integer r:pq){
            result = r;
            System.out.print(result+" ");
        }
        return result;
    }
    public static void main(String[] args){
        //int[] nums ={3,2,1,5,6,4};
        int[] nums ={-1,2,0};
        kthLargest.findKthLargest(nums,2);

    }
}
