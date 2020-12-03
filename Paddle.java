package com.mycompany.testingground;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Paddle extends JPanel {
	
	private static final int WIDTH = Screen.WIDTH/20, HEIGHT = Screen.HEIGHT/50;
	private static final int MAX_VELOCITY = 5;
	
	@Override
	public void paintComponent(Graphics g) {
		
	}
	
	public Paddle () {
		this.setSize(WIDTH, HEIGHT);
	}
}
