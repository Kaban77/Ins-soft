package ru.demidov.insSoft.policy;

public enum PolicyStates {

	PROJECT(0), REGISTERED(5), CANCELED(7), TERMINATION(10);

	private final int state;

	private PolicyStates(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

}
