package com.mycompany.testingground;

import javax.swing.JFrame;


public class Screen {
	
	private final JFrame frame;
	private static Ball ball;
	private static Paddle paddle1, paddle2;
	public static final int WIDTH = 400, HEIGHT = 400;
	
	public Screen () {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(null);
		
		
		frame.add(ball);
		frame.add(paddle1);
		frame.add(paddle2);
	}
	
	
}
