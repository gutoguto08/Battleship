package com.mycompany.testingground;


public class Game {
	
	private static Screen screen;
	
	public Game () {
		screen = new Screen();
	}
	
	enum Level {
		EASY,
		HARD,
		IMPOSSIBLE
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		
	}
	
	
}
