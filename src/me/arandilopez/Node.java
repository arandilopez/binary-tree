package me.arandilopez;

/**
 * Created by arandilopez on 12/8/16.
 */
public class Node<T extends Comparable> {
    T data;
    Node<T> parent = null;
    Node<T> right = null;
    Node<T> left = null;
    int balance = 0;

    public Node(T data) {
        this(data, null, null, null);
    }

    public Node(T data, Node<T> parent) {
        this(data, parent, null, null);
    }

    public Node(T data, Node<T> parent, Node<T> right, Node<T> left) {
        this.data = data;
        this.parent = parent;
        this.right = right;
        this.left = left;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}
