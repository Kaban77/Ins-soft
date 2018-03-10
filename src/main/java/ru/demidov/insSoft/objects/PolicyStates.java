package ru.demidov.insSoft.objects;

public enum PolicyStates {

	PROJECT(0), REGISTERED(5), CANCELED(7), TERMINATION(10);

	private int state;

	PolicyStates(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

}
