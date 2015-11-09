import java.util.*;

/**
 * Created by yiyangtan on 9/23/15.
 */
public class Ticket {
    public static ArrayList<ArrayList<String>> findOrgAndDes(String[][] tickets){
        ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
        ArrayList<String> org = new ArrayList<>();
        ArrayList<String> dst = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        for(int i=0;i<tickets.length;i++){
            String o = tickets[i][0];
            String d = tickets[i][1];
            if(map.containsKey(d)){
                map.put(d,map.get(d)-1);
            }
            else{
                map.put(d,-1);
            }
            if(map.containsKey(o)){
                map.put(o,map.get(o)+1);
            }
            else {
                map.put(o, 1);
            }
        }
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            if(pair.getValue().equals(1)){
                org.add((String)pair.getKey());
            }
            if(pair.getValue().equals(-1)){
                dst.add((String)pair.getKey());
            }
        }
        arr.add(org);
        arr.add(dst);
        return arr;
    }
    public static ArrayList<String> lostTckt(ArrayList<ArrayList<String>> orgAndDes, String[][] tickets){
        ArrayList<ArrayList<String>> arr = findOrgAndDes(tickets);
        System.out.println(arr);
        ArrayList<String> result = new ArrayList<>();
        if(orgAndDes.get(0).get(0).toString().equals(arr.get(0).get(0).toString())){
            result.add(arr.get(0).get(1).toString());
        }
        else{
            result.add(arr.get(0).get(0).toString());
        }

        if(orgAndDes.get(1).get(0).toString().equals(arr.get(1).get(0).toString())){
            result.add(0,arr.get(1).get(1).toString());
        }
        else{
            result.add(0,arr.get(1).get(0).toString());
        }
        System.out.println(result);
        return result;
    }
    public static void main(String [] agrs){
        String[][] tickets = { {"SFO", "LAX"}, {"LAX", "JFK"}, {"JFK", "LHR"}, {"LHR", "CDG"}, {"CDG", "DXB"}, {"DXB", "HKG"}, {"HKG", "HAM"}};
        System.out.println("The tickets list is "+Arrays.deepToString(tickets));
        System.out.println("Origin and destination are:"+findOrgAndDes(tickets));
        String[][] lostTickets = { {"SFO", "LAX"}, {"JFK", "LHR"}, {"LHR", "CDG"}, {"CDG", "DXB"}, {"DXB", "HKG"}, {"HKG", "HAM"}};
        System.out.println("The lost ticket is "+lostTckt(findOrgAndDes(tickets),lostTickets));
    }
}
