package bankapp;

public class GlobalState {
	private static GlobalState instance;

	// the global variable
	private int USER_MODE;

	private GlobalState() {
		USER_MODE = 0; // starting value. 0 for main welcome menu. 1 for bank customer. 2 for bank admin.
	}

	// getter for singleton instance
	public static GlobalState getInstance() {
		if (instance == null) {
			instance = new GlobalState();
		}
		return instance;
	}

	// getter for USER_MODE
	public int getUserMode() { //to use: int mode = GlobalState.getInstance().getUserMode();
		return USER_MODE;
	}

	// setter for USER_MODE
	public void setUserMode(int mode) { //to use: GlobalState.getInstance().setUserMode(1);
		USER_MODE = mode;
	}
}