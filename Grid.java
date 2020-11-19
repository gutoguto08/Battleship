package com.mycompany.battleship;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Grid {
	
    private final Location[][] grid;
	private final DrawStatus drawStatus;
	private final DrawShips drawShips;
    
    // Constants for number of rows and columns.
    public static final int NUM_ROWS = 10, NUM_COLS = 10,
			LOCATION_SPACING = 10,
			LOCATION_WIDTH = BattleshipTester.WINDOW_SIZE/NUM_ROWS-LOCATION_SPACING;
	public int offsetY = 0;
    
    // Create a new Grid. Initialize each Location in the grid
    // to be a new Location object.
    public Grid() {
        grid = new Location[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; ++i)
        for (int j = 0; j < NUM_COLS; ++j)
        grid[i][j] = new Location();
		
		drawStatus = new DrawStatus();
		drawShips = new DrawShips();
    }
	
	public DrawStatus getDrawStatus() {
		drawStatus.setBounds(BattleshipTester.WINDOW_BUFFER, offsetY, BattleshipTester.WINDOW_SIZE, BattleshipTester.WINDOW_SIZE);
		return drawStatus;
	}
	
	public DrawShips getDrawShips() {
		drawShips.setBounds(BattleshipTester.WINDOW_BUFFER, offsetY, BattleshipTester.WINDOW_SIZE, BattleshipTester.WINDOW_SIZE);
		return drawShips;
	}
	
    // Mark a hit in this location by calling the markHit method
    // on the Location object.  
    public void markHit(int row, int col) {
        grid[row][col].markHit();
    }
    
    // Mark a miss on this location.    
    public void markMiss(int row, int col) {
        grid[row][col].markMiss();
    }
    
    // Set the status of this location object.
    public void setStatus(int row, int col, int status) {
        grid[row][col].setStatus(status);
    }
	
	public void setGridOffsetY(int offset) {
		offsetY = offset;
	}
    
    // Get the status of this location in the grid  
    public int getStatus(int row, int col) {
        return grid[row][col].getStatus();
    }
    
    // Return whether or not this Location has already been guessed.
    public boolean alreadyGuessed(int row, int col) {
        return !grid[row][col].isUnguessed();
    }
    
    // Set whether or not there is a ship at this location to the val   
    public void setShip(int row, int col, boolean val) {
        grid[row][col].setShip(val);
    }
    
    // Return whether or not there is a ship here   
    public boolean hasShip(int row, int col) {
        return grid[row][col].hasShip();
    }
    
    // Get the Location object at this row and column position
    public Location get(int row, int col) {
        return grid[row][col];
    }
    
    // Return the number of rows in the Grid
    public int numRows() {
        return grid.length;
    }
    
    // Return the number of columns in the grid
    public int numCols() {
        return grid[0].length;
    }
	
    public boolean addShip(Ship s) {
        if (s.getDirection() == Ship.HORIZONTAL) {    // ship is horizontal
            for (int j = 0; j < s.getLength(); ++j)
                if (grid[s.getRow()][s.getCol() + j - ( Grid.NUM_COLS < s.getLength() + s.getCol() ? (s.getCol() + s.getLength() - Grid.NUM_COLS) : 0)].hasShip())
                    return false;
            for (int i = 0; i < s.getLength(); ++i) {
                grid[s.getRow()][s.getCol() + i - ( Grid.NUM_COLS < s.getLength() + s.getCol() ? (s.getCol() + s.getLength() - Grid.NUM_COLS) : 0)].setShip(true);
            }
        }
        else {                          // ship is vertical
            for (int j = 0; j < s.getLength(); ++j)
                if (grid[s.getRow() + j - ( Grid.NUM_ROWS < s.getLength() + s.getRow() ? (s.getRow() + s.getLength() - Grid.NUM_ROWS) : 0)][s.getCol()].hasShip())
                    return false;
            for (int i = 0; i < s.getLength(); ++i) {
                grid[s.getRow() + i - ( Grid.NUM_ROWS < s.getLength() + s.getRow() ? (s.getRow() + s.getLength() - Grid.NUM_ROWS) : 0)][s.getCol()].setShip(true);
            }
        }
        return true;
    }
    
	public class DrawStatus extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (int i = 0; i < NUM_ROWS; ++i) {
				for (int j = 0; j < NUM_COLS; ++j) {
		
					if (grid[i][j].hasShip()) {
						g.setColor(Color.GRAY);
					} else if (grid[i][j].checkMiss()) {
						g.setColor(Color.PINK);
					} else {
						g.setColor(Color.BLUE);
					}
					int x = j*BattleshipTester.WINDOW_SIZE/NUM_COLS+BattleshipTester.WINDOW_BUFFER;
					int y = i*BattleshipTester.WINDOW_SIZE/NUM_ROWS+BattleshipTester.WINDOW_BUFFER+offsetY;
					
					g.fillRect(x, y, LOCATION_WIDTH, LOCATION_WIDTH);
				}
			}
		}
	}
	
	
	public class DrawShips extends JPanel {
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (int i = 0; i < NUM_ROWS; ++i) {
				for (int j = 0; j < NUM_COLS; ++j) {
					if (grid[i][j].hasShip()) {
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.BLUE);
					}
					int x = j*BattleshipTester.WINDOW_SIZE/NUM_COLS+BattleshipTester.WINDOW_BUFFER;
					int y = i*BattleshipTester.WINDOW_SIZE/NUM_ROWS+BattleshipTester.WINDOW_BUFFER+offsetY;
					g.fillRect(x, y, LOCATION_WIDTH, LOCATION_WIDTH);
				}
			}
		}
	}
	
    public void printStatus() {
		BattleshipTester.frame.repaint();
		
		// Top Header
        System.out.print(" ");
        for (int i = 1; i <= NUM_COLS; ++i) {
            System.out.print(" " + i);
        }
        System.out.println();
        
        // Grid
        for (int i = 0; i < NUM_ROWS; ++i) {
            System.out.print((char) (i + 65) + " ");
            for (int j = 0; j < NUM_COLS; ++j) {
                if (grid[i][j].checkHit()) {
                    System.out.print("X ");
                } else if (grid[i][j].checkMiss()) {
                    System.out.print("O ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
    
    public void printShips() {
		BattleshipTester.frame.repaint();
		
		// Top Header
        System.out.print(" ");
        for (int i = 1; i <= NUM_COLS; ++i) {
            System.out.print(" " + i);
        }
        System.out.println();
        
        // Grid
        for (int i = 0; i < NUM_ROWS; ++i) {
            System.out.print((char) (i + 65) + " ");
            for (int j = 0; j < NUM_COLS; ++j) {
                if (grid[i][j].hasShip()) {
                    System.out.print("X ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
	}
}
