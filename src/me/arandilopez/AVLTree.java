package me.arandilopez;

/**
 * Created by arandilopez on 12/8/16.
 */
public class AVLTree<T extends Comparable> {
    Node<T> root = null;
    private int count = 0;

    boolean insert(T data) {
        if (empty()) {
            this.root = new Node<>(data);
            this.count++;
            return true;
        } else {
            return recursiveInsert(this.root, data);
        }
    }

    private boolean recursiveInsert(Node<T> subroot, T data) {
        if ( data.compareTo( subroot.data ) == 0 ) {
            return false; // this element already exists in BST
            // so do not add it
        }
        if ( data.compareTo( subroot.data ) < 0 ) {
            if (subroot.left == null) {
                subroot.setLeft(new Node<>(data, subroot));
                count++;
                rebalance(subroot);
                return true;
            } else {
                return recursiveInsert(subroot.left, data);
            }
        } else {
            if (subroot.right == null) {
                subroot.setRight(new Node<>(data, subroot));
                count++;
                rebalance(subroot);
                return true;
            } else {
                return recursiveInsert(subroot.right, data);
            }
        }
    }

    public boolean delete(T data) {
        if (empty()) {
            return false;
        } else {
            if ( data.compareTo(this.root.data) == 0 ) {
                Node<T> dummieRoot = new Node<>(null);
                dummieRoot.setLeft(this.root);
                boolean result = recursiveDelete(root, dummieRoot, data);
                this.root = dummieRoot.getLeft();
                return result;
            } else {
                return recursiveDelete(root, null, data);
            }
        }
    }

    private boolean recursiveDelete(Node<T> subroot, Node<T> parent, T data) {
        if ( data.compareTo(subroot.data) < 0 ) {
            if (subroot.left != null) {
                return recursiveDelete(subroot.left, subroot, data);
            } else {
                return false;
            }
        } else if ( data.compareTo(subroot.data) > 0 ) {
            if (subroot.right != null) {
                return recursiveDelete(subroot.right, subroot, data);
            } else {
                return false;
            }
        } else {
            if (subroot.left != null && subroot.right != null) {
                subroot.data = findMin(subroot.right);
                return recursiveDelete(subroot.right, subroot, subroot.data);
            } else if (parent.left == subroot) {
                if (subroot.left != null) {
                    parent.setLeft(subroot.left);
                } else {
                    parent.setLeft(subroot.right);
                }
            } else if (parent.right == subroot) {
                if (subroot.left != null) {
                    parent.setRight(subroot.left);
                } else {
                    parent.setRight(subroot.right);
                }
            }
            this.count--;
            rebalance(parent);
            return true;
        }
    }

    public boolean contains(T data) {
        return recursiveContains(this.root, data);
    }

    private boolean recursiveContains(Node<T> subroot, T data) {
        if (subroot != null) {
            if ( data.compareTo(subroot.data) == 0 ) {
                return true;
            } else {
                if ( data.compareTo(subroot.data) < 0 ) {
                    return recursiveContains(subroot.left, data);
                } else {
                    return recursiveContains(subroot.right, data);
                }
            }
        } else {
            return false;
        }
    }

    private void rebalance(Node<T> subroot) {
        setBalance(subroot, subroot.left, subroot.right);

        if (subroot.balance == -2) {
            if (subroot.left.balance <= 0) {

                subroot = rotateLeftLeft(subroot);

            } else if (subroot.left.balance >= 1) {

                subroot = rotateLeftRight(subroot);

            }
        } else if (subroot.balance == 2) {
            if (subroot.right.balance >= 0) {

                subroot = rotateRightRight(subroot);

            } else if (subroot.right.balance <= -1) {

                subroot = rotateRightLeft(subroot);

            }
        }

        if (subroot.parent != null) {
            rebalance(subroot.parent);
        } else {
            this.root = subroot;
        }
    }

    private Node<T> rotateRightRight(Node<T> subroot) {
        Node<T> aux = subroot.right;

        aux.setParent(subroot.parent);
        subroot.setRight(aux.left);

        if (subroot.right != null) {
            subroot.right.setParent(subroot);
        }

        aux.setLeft( subroot );
        subroot.setParent(aux);

        if (aux.parent != null) {
            if (aux.parent.right == subroot) {
                aux.parent.setRight(aux);
            } else {
                aux.parent.setLeft(aux);
            }
        }

        setBalance(subroot, aux);
        return aux;
    }

    private Node<T> rotateLeftLeft(Node<T> subroot) {

        Node<T> aux = subroot.left;
        aux.setParent(subroot.parent);

        subroot.setLeft( aux.right);

        if (subroot.left != null) {
            subroot.left.setParent( subroot );
        }

        aux.setRight( subroot );
        subroot.setParent( aux );

        if (aux.parent != null) {
            if (aux.parent.right == subroot) {
                aux.parent.setRight(aux);
            } else {
                aux.parent.setLeft( aux );
            }
        }

        setBalance(subroot, aux);
        return aux;
    }

    private Node<T> rotateLeftRight(Node<T> subroot) {
        subroot.setLeft(rotateRightRight(subroot.left));
        return rotateLeftLeft(subroot);
    }

    private Node<T> rotateRightLeft(Node<T> subroot) {
        subroot.setRight(rotateLeftLeft(subroot.right));
        return rotateRightRight(subroot);
    }

    private void setBalance(Node<T>... nodes) {
        for (Node<T> node : nodes) {
            if (node != null)
                node.setBalance( height(node.right) - height(node.left) );
        }
    }

    private Integer height(Node<T> subroot) {
        if (subroot == null) {
            return -1;
        } else {
            return 1 + Math.max(height(subroot.left), height(subroot.right));
        }
    }

    public void printBalance() {
        recursivePrintBalance(this.root);
        System.out.println();
    }

    private void recursivePrintBalance(Node<T> subroot) {
        if (subroot != null) {
            recursivePrintBalance(subroot.left);
            System.out.print(subroot.balance + ", ");
            recursivePrintBalance(subroot.right);
        }
    }

    void inorder() {
        recursiveInorder(this.root);
        System.out.println();
    }

    private void recursiveInorder(Node<T> subroot) {
        if (subroot != null) {
            recursiveInorder(subroot.left);
            System.out.print(subroot + ", ");
            recursiveInorder(subroot.right);
        }
    }

    public T max() {
        return findMax(this.root);
    }

    public T min() {
        return findMin(this.root);
    }

    public boolean empty() {
        return (this.root == null);
    }

    private T findMax(Node<T> subroot) {
        if (subroot != null) {
            while (subroot.right != null) {
                subroot = subroot.right;
            }
        }
        return subroot.data;
    }

    private T findMin(Node<T> subroot) {
        if (subroot != null) {
            while (subroot.left != null) {
                subroot = subroot.left;
            }
        }
        return subroot.data;
    }
}
