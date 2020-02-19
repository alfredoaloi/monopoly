package logic;

import java.util.Random;

public final class Dice {

	private static final int MAX_LAUNCH = 12;

	public static final int launch() {
		return new Random().nextInt(MAX_LAUNCH) + 1;
	}

}
