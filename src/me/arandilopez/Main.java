package me.arandilopez;

public class Main {

    public static void main(String[] args) {
	    AVLTree<Integer> numbers = new AVLTree<>();

	    numbers.insert(5);
	    numbers.insert(4);
	    numbers.insert(1);
	    numbers.insert(8);
	    numbers.insert(10);
	    numbers.insert(3);
	    numbers.insert(9);

		System.out.println("Numbers has 5: " + numbers.contains(5));

	    numbers.inorder();
	    numbers.printBalance();

	    numbers.delete(8);
		numbers.inorder();
		numbers.printBalance();

		numbers.delete(5);
		numbers.inorder();
	    numbers.printBalance();
    }
}
