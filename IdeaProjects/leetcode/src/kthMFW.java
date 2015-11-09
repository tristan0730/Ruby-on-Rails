/**
 * Created by yiyangtan on 10/16/15.
 */
import java.util.*;
class wfq implements Comparable<wfq> {
//class wfq {
    String w;
    int fq;
    wfq(String w,int fq){
        this.w = w;
        this.fq = fq;
    }
    public int compareTo(wfq other){
        return this.fq==other.fq?0:this.fq>other.fq?1:-1;
    }
}
public class kthMFW {
    public static List<String> kth(String s, int k){
        String[] words = s.toLowerCase().trim().split(" ");
        HashMap<String, Integer> map = new HashMap<>();
        /*
        Comparator<wfq> cp = new Comparator<wfq>() {
            public int compare(wfq o1, wfq o2) {
                if (o1.fq == o2.fq) {
                    return 0;
                } else if (o1.fq > o2.fq) {
                    return 1;
                } else return -1;
            }
        };
        PriorityQueue<wfq> pq = new PriorityQueue<>(k,cp);
        */
        PriorityQueue<wfq> pq = new PriorityQueue<>(k);
        /*
        PriorityQueue<wfq> pq = new PriorityQueue<wfq>(k, new Comparator<wfq>(){
                public int compare(wfq w1,wfq w2){
                    if(w1.fq==w2.fq){
                        return 0;
                    }
                    else if(w1.fq>w2.fq){
                        return 1;
                    }
                    else return -1;
                }
        });
        */
        for(String w: words){
            int fq = 1;
            if(map.containsKey(w)){
                fq += map.get(w);
            }
            map.put(w,fq);
        }
        for(Map.Entry<String,Integer> entry: map.entrySet()){
            if(pq.size()<k){
                pq.offer(new wfq(entry.getKey(), entry.getValue()));
            }
            else if(entry.getValue()>pq.peek().fq){
                pq.poll();
                pq.offer(new wfq(entry.getKey(),entry.getValue()));
            }

            //pq.add(new wfq(entry.getKey(), entry.getValue()));
        }
        List<String> l = new ArrayList<String>();
        while(pq.size()!=0){
            System.out.println(pq.peek().w+pq.peek().fq);
            l.add(pq.poll().w);
        }
        return l;
    }
    public static void main(String [] args){
        String s = "aa bb cc bb bb cc dd dd ee ff ee dd aa ee";
        System.out.print(kth(s,3));
    }
}
