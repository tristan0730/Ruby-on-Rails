import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yiyangtan on 10/14/15.
 */
public class WordBreak {
    public static List<String> wordBreak(String s, Set<String> wordDict) {
        boolean [] dp = new boolean[s.length()+1];
        for(int i=0;i<dp.length;i++){
            dp[i]=true;
        }
        List<String> words = new ArrayList<String>();
        List<String> result = new ArrayList<String>();
        wordBreak(s,wordDict,dp,0,words,result);
        return result;
    }
    public static boolean wordBreak(String s, Set<String> wordDict, boolean[]dp,int index, List<String> words, List<String> result){
        if(index==s.length()){
            String r = "";
            for(int i=0;i< words.size();i++){
                r += words.get(i);
                r +=" ";
            }
            r = r.trim();
            result.add(r);
            //System.out.println();
            return true;
        }
        if(!dp[index]) return dp[index];
        for(int i=index+1;i<=s.length();i++){
            if(wordDict.contains(s.substring(index,i))){
                words.add(s.substring(index,i));
                //System.out.print(s.substring(index,i)+" "+ "dp[i] "+ dp[i]+" ");
                dp[index] = dp[index] | wordBreak(s,wordDict,dp,i,words,result);
                words.remove(words.size()-1);
            }
        }
        return dp[index];
    }
    public static void main(String [] args){
        String s = "catsanddog";
        Set<String> wordDict = new HashSet<>();
        wordDict.add("cat");
        wordDict.add("cats");
        wordDict.add("and");
        wordDict.add("sand");
        wordDict.add("dog");
        System.out.print(wordBreak(s,wordDict));
    }
}