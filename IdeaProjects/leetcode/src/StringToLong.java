/**
 * Created by yiyangtan on 9/19/15.
 */
public class StringToLong {
    public long toLong(String s){
        if(s==null) return 0;
        s = s.trim();
        if(s.length()==0) return 0;
        StringBuilder sb = new StringBuilder();
        boolean nonNeg = true;
        if(s.charAt(0)=='+'){
            nonNeg = true;
            s = s.substring(1);
        }
        else if(s.charAt(0)=='-'){
            nonNeg = false;
            s = s.substring(1);
        }
        while(s.charAt(0)=='0'){
            s = s.substring(1);
        }
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(!isNum(c)) break;
            sb.append(c);
            int over = overflow(nonNeg,sb.toString());
            if(over==1) return Long.MAX_VALUE;
            else if(over==-1){
                return Long.MIN_VALUE;
            }
        }
        String nums = sb.toString();
        long result = 0;
        for(int i=0;i<nums.length();i++){
            result = result*10 + (nums.charAt(i)-'0');
        }
        if(!nonNeg) result *= -1;
        return result;
    }
    public boolean isNum(char c){
        if(c>='0'&&c<='9') return true;
        return false;
    }
    public int overflow(boolean nonNeg, String s){
        String max = "9223372036854775807";
        String min = "9223372036854775808";
        if(s.length()<max.length()) return 0;
        if(nonNeg){
            for(int i=0;i<s.length();i++){
                if(s.charAt(i)<max.charAt(i)) return 0;
                else if(s.charAt(i)>max.charAt(i)) return 1;
            }
        }
        else{
            for(int i=0;i<s.length();i++){
                if(s.charAt(i)<min.charAt(i)) return 0;
                else if(s.charAt(i)>min.charAt(i)) return -1;
            }
        }
        return nonNeg?1:-1;
    }
    public static void main(String[]args){
        StringToLong stl = new StringToLong();
        long result;
        String []test = new String[12];
        String []des = new String[12];
        des[0] = "Test 1: Empty String";
        test[0] = "";
        des[1] = "Test 2: Space Only String";
        test[1] = "       ";
        des[2] = "Test 3: Special Character Only String";
        test[2] = "     @#$@#%^$%^$^$       ";
        des[3] = "Test 4: String with \'+\' sign";
        test[3] = "    +1234567   ";
        des[4] = "Test 5: String with 0's at the beginning";
        test[4] = "    000001234567   ";
        des[5] = "Test 6: String with '-' sign";
        test[5] = "   -1234567";
        des[6] = "Test 7: String with valid numbers followed by special characters";
        test[6] = "123456#$#$@890";
        des[7] = "Test 8: Upperbound Overflow String";
        test[7] = "9223372036854775808";
        des[8] = "Test 9: String that equals to Long.MAX_VALUE";
        test[8] = "9223372036854775807";
        des[9] = "Test 10: Lowerbound Overflow String";
        test[9] = "-9223372036854775809";
        des[10] = "Test 11: String that equals to Long.MIN_VALUE";
        test[10]= "-9223372036854775808";
        des[11] = "Test 12: Null String";
        for(int i=0;i<test.length;i++){
            System.out.println(des[i]);
            System.out.println("Input: "+"\""+test[i]+"\"");
            System.out.println("Output: "+stl.toLong(test[i]));
            System.out.println("*************************************************************************");
        }
    }
}
