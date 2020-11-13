package com.mycompany.battleship;
public class Player {
    // These are the lengths of all of the ships.
    private static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5}; // default {2, 3, 3, 4, 5}
    private final Ship[] fleet;
    private final Grid playerGrid;
    private Grid opponentGrid;
    private static final int MAX_HITS = maxHits();
    private int numShips = 0, totalHits = 0;
    
	/**
	 *
	 */
	public Player() {
        fleet = new Ship[SHIP_LENGTHS.length];
        
        playerGrid = new Grid();
        opponentGrid = new Grid();
    }
    
    public int[] getShipLengths() {
        return SHIP_LENGTHS;
    }
    
    public Grid getGrid() {
        return playerGrid;
    }
    
    public boolean hasQuit() {
        return totalHits == MAX_HITS;
    }
    
    public void setOpponentGrid(Grid opponentGrid) {
        this.opponentGrid = opponentGrid;
    }
    
    public boolean chooseShipLocation(Ship s, int row, int col, int direction) {
        s.setLocation(row, col);
        s.setDirection(direction);
        
        if (numShips < fleet.length) {
            if (!playerGrid.addShip(s))
            return false;
            
            fleet[numShips] = s;
            numShips++;
        }
        return true;
    }
    
    private static int maxHits() {
        int hits = 0;
        for (int i : SHIP_LENGTHS) {
            hits += i;
        }
        return hits;
    }
    
    // Print your ships on the grid
    public void printMyShips() {
        playerGrid.printShips();
    }
    
    // Print opponent guesses
    public void printOpponentGuesses() {
        playerGrid.printStatus();
    }
    
    // Print your guesses
    public void printMyGuesses() {
        opponentGrid.printStatus();
    }
    
    // Record a guess from the opponent
    public boolean recordOpponentGuess(int row, int col) {
        if (playerGrid.alreadyGuessed(row, col))
        return false;
        
        if (playerGrid.hasShip(row, col) == true) {
            playerGrid.markHit(row, col);
            ++totalHits;
        } else {
            playerGrid.markMiss(row, col);
        }
        return true;
    }
}
