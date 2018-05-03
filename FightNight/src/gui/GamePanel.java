package gui;

import gameplay.GameState;
import networking.frontend.NetworkMessenger;
import processing.core.PApplet;

public class GamePanel extends PApplet implements NetworkMessenger{

	GameState currentState = null;
	
	
	public void draw() {
		clear();

		if(currentState != null)
			currentState.draw(this);
		//Starting Setup
		stroke(0, 0, 0);

		mousePressed();

		//Calls to draw

	}

	public void mousePressed() {
		
	}

	public void mouseMoved() {
		
	}

	public void keyPressed(){
		if(key == CODED) {
			
		}
		if(key == 'a') {
			
		}
			
	}

	@Override
	public void sendMessage(String messageType, Object... message) {
		// TODO Auto-generated method stub
		
	}
	
}
