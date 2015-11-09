/**
 * Created by yiyangtan on 9/23/15.
 */
public class zillowTrinaryTree {
    /*
// The following definitions of Tree and Node are provided.
// insert and delete will be methods of class Tree.
*/
public class Tree {
    private class Node {
        private int val;
        private Node left = null;
        private Node right = null;
        private Node mid = null;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node root;


    /*
     * Please complete this method.
     * Inserts val into the tree. There is no need to rebalance the tree.
     */
    public void insert(int val) {
        root = insert(root, val);
    }
    public Node insert(Node root, int val){
        if(root==null){
            return new Node(val);
        }
        if(root.val>val){
            root.left = insert(root.left,val);
        }
        else if(root.val<val){
            root.right = insert(root.right,val);
        }
        else{
            if(root.mid == null){
                root.mid = new Node(val);
            }
            else{
                root.mid = insert(root.mid,val);
            }
        }
        return root;
    }

    /*
     * Please complete this method.
     * Deletes only one instance of val from the tree.
     * If val does not exist in the tree, do nothing.
     * There is no need to rebalance the tree.
     */
    public void delete(int val) {
        root = delete(root,val);
    }
    public Node delete(Node root, int val){
        if(root==null) return null;
        if(root.val<val){
            root.right = delete(root.right, val);
        }
        else if(root.val>val){
            root.left = delete(root.left, val);
        }
        else{
            if(root.mid!=null){
                root.mid = delete(root.mid,val);
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
                    Node temp = root;
                    root = findSuc(root.right);
                    root.right = deleteSuc(temp.right);
                    root.left = temp.left;
                }
            }
        }
        return root;
    }

    public Node findSuc(Node root){
        while(root.left!=null) root = root.left;
        return root;
    }
    public Node deleteSuc(Node root){
        if(root.left==null){
            return root.right;
        }
        else{
            root.left = deleteSuc(root.left);
        }
        return root;
    }

}}
