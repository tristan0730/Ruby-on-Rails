/**
 * Created by yiyangtan on 10/16/15.
 */
import java.util.*;
public class kthLE {
    public static int kthle(int[] arr,int k){
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i:arr){
            if(pq.size()<k){
                pq.offer(i);
            }
            else if(i>pq.peek()){
                pq.poll();
                pq.offer(i);
            }
        }
        return pq.peek();
    }
    public static void main(String [] args){
        int [] arr = new int[10];
        for(int i=0;i<arr.length;i++){
            arr[i]=i+1;
        }
        System.out.print(kthle(arr,4));
    }
}
