package games;

public class Games {
	// First Game
	public static String PaperScissorStone() {
		String[] outcome = {"paper", "scissor", "stone"};
		int max = 2, min = 0, rani = (int)(Math.random()*(max-min+1)+min);
		return outcome[rani];
	}
	// Second Game
	public static int OddOrEven() {
		int max = 100, min = 1, rani = (int)(Math.random()*(max-min+1)+min);
		return rani;
	}
	// Third Game
}
