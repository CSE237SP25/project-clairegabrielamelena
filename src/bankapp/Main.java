package bankapp;

public class Main {
	
	public static Bank mainBank;

	public static void main(String[] args) {
		
		mainBank = new Bank();
		
		mainBank.run();
		
	}
}
