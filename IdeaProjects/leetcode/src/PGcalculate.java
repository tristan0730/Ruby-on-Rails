/**
 * Created by yiyangtan on 10/15/15.
 */
import java.util.*;
public class PGcalculate {
    public static int calculate(String s){
        ArrayList<String> rest = new ArrayList<>();
        for(int i = 0;i<s.length();i++){
            rest.add(s.substring(i,i+1));
        }
        int count = 10;
        return helper(rest,count);
    }
    public static int helper(ArrayList<String> rest,int count){
        List<String> result = new ArrayList<>();
        for(int i = 0;i<count;i++){
            System.out.print("1");
        }
        System.out.println(rest);
        // calculate value in the bracket
        for(int i=0;i<rest.size();i+=0){
            String c = rest.get(i);
            if(c.equals("(")){
                rest.remove(0);
                System.out.println(result+"      "+rest);
                for(int j = 0;j<2*count;j++){
                    System.out.print(">");
                }
                System.out.println();
                result.add(Integer.toString(helper(rest,2*count)));
                System.out.println(result+"      "+rest);
                for(int j = 0;j<2*count;j++){
                    System.out.print("<");
                }
                System.out.println();
            }
            else if(c.equals(")")){
                rest.remove(0);
                break;
            }
            else{
                result.add(rest.get(0));
                rest.remove(0);
            }
        }
        for(int i = 0;i<count;i++){
            System.out.print("2");
        }
        System.out.println(result);
        //calculate value with */ operations
        List<String> answer = new ArrayList<>();
        for(int i=0;i<result.size();i+=0){
            String c = result.get(i);
            if(c.equals("*")){
                result.remove(0);
                int a = Integer.parseInt(answer.get(answer.size() - 1));
                answer.remove(answer.size()-1);
                int b = Integer.parseInt(result.get(0));
                result.remove(0);
                String tmp = Integer.toString(a*b);
                answer.add(tmp);
            }
            else if(c.equals("/")){
                result.remove(0);
                int a = Integer.parseInt(answer.get(answer.size() - 1));
                answer.remove(answer.size()-1);
                int b = Integer.parseInt(result.get(0));
                result.remove(0);
                String tmp = Integer.toString(a/b);
                answer.add(tmp);
            }
            else{
                answer.add(result.get(0));
                result.remove(0);
            }
        }

        result = answer;
        answer = new ArrayList<>();
        for(int i = 0;i<count;i++){
            System.out.print("3");
        }
        System.out.println(result);
        //calculate value with +- operations
        for(int i=0;i<result.size();i+=0){
            String c = result.get(i);
            if(c.equals("+")){
                result.remove(0);
                int a = Integer.parseInt(answer.get(answer.size() - 1));
                answer.remove(answer.size()-1);
                int b = Integer.parseInt(result.get(0));
                result.remove(0);
                String tmp = Integer.toString(a+b);
                answer.add(tmp);
            }
            else if(c.equals("-")){
                result.remove(0);
                int a = Integer.parseInt(answer.get(answer.size() - 1));
                answer.remove(answer.size()-1);
                int b = Integer.parseInt(result.get(0));
                result.remove(0);
                String tmp = Integer.toString(a-b);
                answer.add(tmp);
            }
            else{
                answer.add(result.get(0));
                result.remove(0);
            }
        }
        for(int i = 0;i<count;i++){
            System.out.print("4");
        }
        System.out.println(answer);
        return Integer.parseInt(answer.get(0));
    }
    public static void main(String [] args){
        System.out.print(calculate("3+6*(9-2)+2*(1+4*(8*5-3))"));
    }
}
