package structure;

/**
 * @author lei
 */
public class RBTree<K, V> {

    public static final boolean RED = true;
    public static final boolean BLACK = false;


    RBNode<K, V> root;
    
    //      \ /                   \ /
    //       p                     l  
    //      / \                   / \
    //     l   r     ==>         ll  p
    //    / \                       / \
    //   ll  lr                    lr  r
    private void rotateRight(RBNode<K, V> p) {
        if (p.left == null) return;
        RBNode<K, V> l = p.left;
        // 时刻记住链是双向的
        p.left = l.right;
        if (l.right != null) {
            l.right.parent = p;
        }
        l.parent = p.parent;
        if (p.parent == null) {
            this.root = l;
        } else if (p.parent.left == p) {
            p.parent.left = l;
        } else {
            p.parent.right = l;
        }
        p.parent = l;
        l.right = p;
    }

    /**
     * p左旋
     *         p						r
     *       /  \					   / \
     *     l    r   ==> 		      p   rr
     *         / \					 / \
     *        rl  rr				l  rl
     * 
     * 三部分
     * p
     * p.right
     * p.parent
     * @param p 红黑树节点
     */            
    private void rotateLeft(RBNode<K, V> p) {
        if (p.right == null) return;
        RBNode<K, V> r = p.right;
        p.right = r.left;
        if (r.left != null) {
            r.left.parent = p;
        }
        r.parent = p.parent;
        if (p.parent == null) {
            root = r;
        } else if (p.parent.left == p) {
            p.parent.left = r;
        } else {
            p.parent.right = r;
        }
        p.parent = r;
        r.left = p;
    }


    public static class RBNode<K, V> {
        K key;
        V value;
        RBNode<K, V> left;
        RBNode<K, V> right;
        RBNode<K, V> parent;
        boolean color;

        public RBNode(K key, V value, RBNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.color = BLACK;
            this.left = this.right = null;
        }
    }
}
