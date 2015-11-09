/**
 * Created by yiyangtan on 9/23/15.
 */
public class parseLong {
    static long parseLong(String str) throws IllegalArgumentException{
        if(str==null || str.length()==0) {
            throw new IllegalArgumentException();
        }
        int len = str.length();
        if(len!=str.trim().length()){
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder();
        boolean nonNeg = true;
        if(str.charAt(0)=='+'){
            nonNeg = true;
            str = str.substring(1);
        }
        else if(str.charAt(0)=='-'){
            nonNeg = false;
            str = str.substring(1);
        }
        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            if(!isNum(c)){
                throw new IllegalArgumentException();
            }
            sb.append(c);
            int over = overflow(nonNeg,sb.toString());
            if(over!=0){
                throw new IllegalArgumentException();
            }
        }
        String nums = sb.toString();
        long result = 0;
        for(int i=0;i<nums.length();i++){
            result = result*10+(nums.charAt(i)-'0');
        }
        if(!nonNeg) result =result*-1;
        return result;
    }

    static boolean isNum(char c){
        if(c>='0'&&c<='9') return true;
        return false;
    }

    static int overflow(boolean nonNeg, String s){
        String max = "9223372036854775807";
        String min = "9223372036854775808";
        if(s.length()<max.length()) return 0;
        if(nonNeg){
            if(s.length()>max.length()) return 1;
            for(int i=0;i<s.length();i++){
                if(s.charAt(i)<max.charAt(i)) return 0;
                else if(s.charAt(i)>max.charAt(i)) return 1;
            }
        }
        else{
            if(s.length()>min.length()) return -1;
            for(int i=0;i<s.length();i++){
                if(s.charAt(i)<min.charAt(i)) return 0;
                else if(s.charAt(i)>min.charAt(i)) return -1;
            }
        }
        return 0;
    }
    public static void main(String[]args){
        parseLong stl = new parseLong();
        long result;
        String []test = new String[5];
        String []des = new String[12];
        test[0] = "123";
        test[1] = "-123";
        test[2] = "9223372036854775807";
        test[3] = "+9223372036854775807";
        test[4] = "-9223372036854775808";


        for(int i=0;i<test.length;i++){
            //System.out.println(des[i]);
            System.out.println("Input: "+"\""+test[i]+"\"");
            System.out.println("Output: "+stl.parseLong(test[i]));
            System.out.println("*************************************************************************");
        }
    }
}
