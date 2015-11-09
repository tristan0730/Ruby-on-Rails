import java.util.LinkedList;

/**
 * Created by yiyangtan on 9/19/15.
 */
public class TrinaryTree{

    private class TreeNode {
        private int value;
        private TreeNode left;
        private TreeNode mid;
        private TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    private TreeNode root;

    public TrinaryTree() {
        root = null;
    }

    public void insert(int value) {
        root = insert(root,value);
    }

    private TreeNode insert(TreeNode root, int value) {
        if(root == null){
            return new TreeNode(value);
        }
        if(root.value>value){
            root.left = insert(root.left,value);
        }
        else if(root.value<value){
            root.right = insert(root.right,value);
        }
        else{
            if(root.mid==null){
                root.mid=new TreeNode(value);
            }
            else{
                root.mid = insert(root.mid, value);
            }
        }
        return root;
    }

    public void delete(int value) {
        root = delete(root, value);
    }

    private TreeNode delete(TreeNode root, int value) {
        if(root==null) return null;
        if(root.value<value){
            root.right = delete(root.right,value);
        }
        else if(root.value>value){
            root.left = delete(root.left,value);
        }
        else{
            if(root.mid!=null){
                root.mid = delete(root.mid,value);
            }
            else{
                if(root.right==null&&root.left==null){
                    return null;
                }
                else if(root.left == null){
                    return root.right;
                }
                else if(root.right == null){
                    return root.left;
                }
                else{
                    TreeNode temp = root;
                    root = findSuc(root.right);
                    root.right = deleteSuc(temp.right);
                    root.left = temp.left;
                }
            }
        }
        return root;
    }

    private TreeNode findSuc(TreeNode root) {
        while(root.left!=null) root = root.left;
        return root;
    }

    //when delete min, first find if there is duplicate key
    //if found, delete duplicate, otherwise, delete that node
    private TreeNode deleteSuc(TreeNode root) {
        if(root.left==null){
            return root.right;
        }
        else{
            root.left = deleteSuc(root.left);
        }
        return root;
    }


    //for test use

    /*
     * specification of serialize:
     *
     * The function serialize the trinary tree by using level order traversal, # indicate null,
     * all nodes with less then 3 children will be followed by # for each null children, except
     * for nodes in deepest level
     */
    public String serialize() {
        return serialize(root);
    }

    private String serialize(TreeNode root) {
        String str = "";
        LinkedList<TreeNode> parent = new LinkedList<TreeNode>();
        parent.add(root);
        boolean end = false;
        while (!end) {
            end = true;
            int size = parent.size();
            for (int i = 0; i < size; i++) {
                TreeNode temp = parent.removeFirst();
                str += temp == null? "#": (Integer) temp.value;
                str += ",";
                if (temp == null)
                    continue;
                if (!isLeaf(temp))
                    end = false;
                parent.add(temp.left);
                parent.add(temp.mid);
                parent.add(temp.right);
            }
        }
        return str.substring(0, str.length() - 1);
    }

    private boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null && node.mid == null;
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }


	/*
	 *
	 * Test Cases
	 *
	 */

    //test insert without duplicate
    private static void test1() {
        TrinaryTree t = new TrinaryTree();
        t.insert(10);
        t.insert(5);
        t.insert(15);
        t.insert(3);
        t.insert(7);
        t.insert(8);
        t.insert(9);
        t.insert(13);
        t.insert(14);
        t.insert(18);
        t.insert(16);
        //expected result after insertion
        String res = "10,5,#,15,3,#,7,13,#,18,#,#,#,#,#,8,#,#,14,16,#,#,#,#,9,#,#,#,#,#,#";
        if (res.equals(t.serialize()))
            System.out.println("Test Case 1 passed!");
        else
            System.out.println("Test Case 1 failed...");
    }

    //test delete without duplicate
    private static void test2() {
        TrinaryTree t = new TrinaryTree();
        t.insert(17);
        t.insert(10);
        t.insert(22);
        t.insert(5);
        t.insert(13);
        t.insert(19);
        t.insert(27);
        t.insert(4);
        t.insert(11);
        t.insert(14);
        t.insert(18);
        t.insert(20);
        t.insert(29);
        t.insert(3);
        t.insert(28);
        //expected result after insertion
        String res = "17,10,#,22,5,#,13,19,#,27,4,#,#,11,#,14,18,#,20,#,#,29,"
                + "3,#,#,#,#,#,#,#,#,#,#,#,#,#,#,28,#,#";
        if (!res.equals(t.serialize())) {
            System.out.println("test2: "+t.serialize());
            System.out.println("Test Case 2 failed...");
            return;
        }

        t.delete(10);
        //expected result after delete 10
        res = "17,11,#,22,5,#,13,19,#,27,4,#,#,#,#,14,18,#,20,#,#,29,3,#,#,#,#,#,#,#,#,#,#,#,28,#,#";
        if (!res.equals(t.serialize())) {
            System.out.println("test2: "+t.serialize());
            System.out.println("Test Case 2 failed...");
            return;
        }

        t.delete(28);
        //expected result after delete 28
        res = "17,11,#,22,5,#,13,19,#,27,4,#,#,#,#,14,18,#,20,#,#,29,3,#,#,#,#,#,#,#,#,#,#,#,#,#,#";
        if (!res.equals(t.serialize())) {
            System.out.println("Test Case 2 failed...");
            return;
        }

        t.delete(27);
        //expected result after delete 27
        res = "17,11,#,22,5,#,13,19,#,29,4,#,#,#,#,14,18,#,20,#,#,#,3,#,#,#,#,#,#,#,#,#,#,#";
        if (!res.equals(t.serialize())) {
            System.out.println("Test Case 2 failed...");
            return;
        }

        t.delete(5);
        t.delete(19);
        t.delete(17);
        //expected result after multiple deletions
        res = "18,11,#,22,4,#,13,20,#,29,3,#,#,#,#,14,#,#,#,#,#,#";
        if (!res.equals(t.serialize()))
            System.out.println("Test Case 2 failed...");
        else
            System.out.println("Test Case 2 passed!");

    }

    //test insert with duplicates
    public static void test3() {
        TrinaryTree t = new TrinaryTree();
        t.insert(27);
        t.insert(15);
        t.insert(33);
        t.insert(7);
        t.insert(22);
        t.insert(30);
        t.insert(39);
        t.insert(27);
        t.insert(27);
        t.insert(15);
        t.insert(7);
        t.insert(9);
        t.insert(22);
        t.insert(31);
        t.insert(35);
        t.insert(39);
        t.insert(8);
        t.insert(9);
        t.insert(22);
        t.insert(30);
        t.insert(30);
        //expected result after insertion
        String res = "27,15,27,33,7,15,22,#,27,#,30,#,39,#,7,9,#,#,#,#,22,#,#,#,#,#,30,31,35,39,"
                + "#,#,#,#,8,9,#,#,22,#,#,30,#,#,#,#,#,#,#,#,#,#";
        if (!res.equals(t.serialize()))
            System.out.println("Test Case 3 failed...");
        else
            System.out.println("Test Case 3 passed!");
    }

    //test delete with duplicates
    public static void test4() {
        TrinaryTree t = new TrinaryTree();
        t.insert(27);
        t.insert(15);
        t.insert(33);
        t.insert(7);
        t.insert(22);
        t.insert(30);
        t.insert(39);
        t.insert(27);
        t.insert(27);
        t.insert(15);
        t.insert(7);
        t.insert(9);
        t.insert(22);
        t.insert(31);
        t.insert(35);
        t.insert(39);
        t.insert(8);
        t.insert(9);
        t.insert(22);
        t.insert(30);
        t.insert(30);
        //expected result after deletion
        String res = "27,15,27,33,7,15,22,#,27,#,30,#,39,#,7,9,#,#,#,#,22,#,#,#,#,#,30,31,35,39,"
                + "#,#,#,#,8,9,#,#,22,#,#,30,#,#,#,#,#,#,#,#,#,#";
        if (!res.equals(t.serialize())){
            System.out.println("Test Case 4 failed...");
            return;
        }

        //delete 27
        t.delete(27);
        //expected result after delete 27
        res = "27,15,27,33,7,15,22,#,#,#,30,#,39,#,7,9,#,#,#,#,22,#,#,30,31,35,39,"
                + "#,#,#,#,8,9,#,#,22,#,#,30,#,#,#,#,#,#,#,#,#,#";
        if (!res.equals(t.serialize())){
            System.out.println("27: "+t.serialize());
            System.out.println("Test Case 4 failed...");
            return;
        }

        //delete 7
        t.delete(7);
        //expected result after delete 7
        res = "27,15,27,33,7,15,22,#,#,#,30,#,39,#,#,9,#,#,#,#,22,#,#,30,31,35,39,"
                + "#,8,9,#,#,22,#,#,30,#,#,#,#,#,#,#,#,#,#";
        if (!res.equals(t.serialize())){
            System.out.println("7: "+t.serialize());
            System.out.println("Test Case 4 failed...");
            return;
        }

        //multiple deletions
        t.delete(22);
        t.delete(27);
        t.delete(27);
        //expected result after delete 7
        res = "30,15,30,33,7,15,22,#,30,#,31,#,39,#,#,9,#,#,#,#,22,#,#,#,#,#,#,#,35,39,#,8,9,#,#,#,#,#,#,#,#,#,#";
        if (!res.equals(t.serialize())) {
            System.out.println("22 27 27: "+t.serialize());
            System.out.println("Test Case 4 failed...");
        }
        else
            System.out.println("Test Case 4 passed!");
    }
}