import java.util.*;

/**
 * Created by yiyangtan on 10/15/15.
 */
class Node{
    char val;
    Node left;
    Node right;
    Node(char val){
        this.val = val;
    }
}
public class PGternary {

    //a?b?c:d:e
    public static Node ternaryToBT(String s){
        if(s==null) return null;
        Queue<Character> l = new LinkedList<>();
        for(int i=0;i<s.length();i++){
            l.offer(s.charAt(i));
        }
        return helper(l);
    }
    public static Node helper(Queue<Character> l){
        Node root = new Node(l.poll());
        if(l.isEmpty()){
            return root;
        }
        if(l.peek()==':'){
            l.poll();
        }
        else if(l.peek()=='?'){
            l.poll();
            root.left = helper(l);
            root.right = helper(l);
        }
        return root;
    }
    public static void levelprint(Node root){
        List<List<Character>> result = new ArrayList<>();
        List<Node> lnode = new ArrayList<>();
        lnode.add(root);
        while(!lnode.isEmpty()) {
            int size = lnode.size();
            List<Character> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Node n = lnode.get(0);
                level.add(n.val);
                if (n.left != null) {
                    lnode.add(n.left);
                }
                if (n.right != null) {
                    lnode.add(n.right);
                }
                lnode.remove(0);
            }
            System.out.println(level);
        }
    }
    public static void main(String [] args){
        Node root = ternaryToBT("a?b?c:d:e");
        levelprint(root);
        root = ternaryToBT("a?b:c?d:e");
        levelprint(root);
        root = ternaryToBT("a?b:c");
        levelprint(root);
    }
}
