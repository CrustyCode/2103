
package org.crusty.g2103;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.crusty.engine.CrustyEngine;
import org.crusty.engine.Screen;
import org.crusty.g2103.levels.LevelHighScores;
import org.crusty.g2103.levels.LevelMenu;
import org.crusty.g2103.levels.LevelNameInput;

public class Game extends CrustyEngine {
	private static final long serialVersionUID = 7703861327701386347L;
	// Screens
	public Screen screenMenu;
	public Screen screenMaze;
	public Screen screenHighScores;
	public Screen screenNameInput;
	
	public Game(int width, int height, String title) {
		super(width, height, title);
		
		// Add Sprites
//		SpriteManager.addSprite("test.png");
//		Entity e = new Entity(SpriteManager.getSprite("test.png"));
		
		
		
		screenMenu = new LevelMenu(this);
		currentScreen = screenMenu;
		//screenMaze = new LevelMaze(this);
		screenHighScores = new LevelHighScores(this);
		screenNameInput = new LevelNameInput(this);
	}

	@Override
	public void draw(Graphics2D g) {
		
		// Clear background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		super.draw(g);
	}
	
	public static void main(String[] args) {
		Game g = new Game(800, 600, "2103 by Elliot Walmsley");
		Sound.touch();
		g.gameLoop();
	}

	@Override
	public void loadImages() {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (currentScreen != null)
			currentScreen.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentScreen != null)
			currentScreen.mouseMoved(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
//		System.out.println("Game keyEvent: " + e.getKeyCode());
		if (currentScreen != null)
			currentScreen.keyTyped(e);
	}

//	@Override
//	public void keyPressed(KeyEvent e) {
//		
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		
//	}
	
}
