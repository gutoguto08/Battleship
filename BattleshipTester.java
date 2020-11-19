package com.mycompany.battleship;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class BattleshipTester extends JPanel {
	
    public static Scanner in = new Scanner(System.in);
	private static final Player p1 = new Player(), p2 = new Player();
	
	public static final JFrame frame = new JFrame();
	public static final int WINDOW_SIZE = 400, WINDOW_BUFFER = 25;
	
	public BattleshipTester() {
		frame.setTitle("Battleship Game");
		frame.setSize(WINDOW_SIZE*3, WINDOW_SIZE*3);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		final BattleshipTester host = new BattleshipTester();
		
		p1.getGrid().setGridOffsetY(0);
		p2.getGrid().setGridOffsetY(200);
		
		frame.add(p1.getGrid().getDrawShips());
		frame.add(p1.getGrid().getDrawStatus());
		
		host.run();
	}
    
    public void run()
    {
        introduction();
		
		//placeShips(p1);
        computerPlaceShips(p1);
        computerPlaceShips(p2);
        
        p1.setOpponentGrid(p2.getGrid());
        p2.setOpponentGrid(p1.getGrid());
        
        while (!p1.hasQuit() || !p2.hasQuit()) {
            computerAskForGuess(p2);
            computerAskForGuess(p1);
        }
		
        
        System.out.println("Game Exited.");
        in.close();
    }
    
    public void introduction() {
        System.out.println("Welcome to Battleship");
        System.out.println("You will be competing against an AI");
        System.out.println("Ready?");
        
        String begin;
        do {
            System.out.println("Press Enter to Begin!");
            begin = in.nextLine();
        } while (!begin.equals(""));
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); // Clear screen
    }
    
    public void askForGuess(Player player) {
        String coordinate;
        boolean tempBool;
        do {
            System.out.println("Enter coordinates for guess (example: B3)");
            coordinate = in.nextLine().toUpperCase();
            tempBool = !player.recordOpponentGuess((int) coordinate.toUpperCase().charAt(0) - 65, (Integer.parseInt(coordinate.substring(1))) - 1);
        } while ( (coordinate.charAt(0)-65 >= Grid.NUM_ROWS || Integer.parseInt(coordinate.substring(1)) > Grid.NUM_COLS) && tempBool );
        player.printOpponentGuesses();
    }
    
	@SuppressWarnings("empty-statement")
    public void computerAskForGuess(Player player) {
        while (!player.recordOpponentGuess(Randomizer.nextInt(Grid.NUM_ROWS), Randomizer.nextInt(Grid.NUM_COLS)));
        player.printOpponentGuesses();
    }
    
    public void placeShips(Player player) {
        int dir;
        String coordinate;
        
        System.out.println("You have to place down your ships!");
        
        for (int i = 0; i < player.getShipLengths().length; ++i) {
            player.printMyShips();
            do {
                System.out.println("Enter coordinates for ship (example: B3) at length: " + player.getShipLengths()[i]);
                coordinate = in.nextLine().toUpperCase();
            } while ( coordinate.charAt(0)-65 >= Grid.NUM_ROWS || Integer.parseInt(coordinate.substring(1)) > Grid.NUM_COLS );
            System.out.println("Enter direction. Horizontal [0], Vertical [1]");
            dir = in.nextInt(); in.nextLine();
            if (!player.chooseShipLocation(new Ship(player.getShipLengths()[i]), (int) coordinate.toUpperCase().charAt(0) - 65, (Integer.parseInt(coordinate.substring(1))) - 1, dir))
                --i;
        }
        player.printMyShips();
        System.out.println("You have placed all of your ships.");
    }
    
    public void computerPlaceShips(Player player) {
        int row, col;
        ArrayList <Integer> usedRows = new ArrayList<>(), usedCols = new ArrayList<>();
        
        for (int i = 0; i < player.getShipLengths().length; ++i) {
            row = Randomizer.nextInt(Grid.NUM_ROWS); col = Randomizer.nextInt(Grid.NUM_COLS);
            for (int j = 0; j < usedRows.size(); ++j) {
                if (row != usedRows.get(i)) {
                    usedRows.add(row);
                }
            }
            for (int j = 0; j < usedCols.size(); ++j) {
                if (col != usedCols.get(i)) {
                    usedCols.add(row);
                }
            }
            
            if (!player.chooseShipLocation(new Ship(player.getShipLengths()[i]), row, col, Randomizer.nextInt(2)))
                --i;
            
        }
        player.printMyShips();
    }
    
}
