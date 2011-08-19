package org.crusty.g2103.levels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Locale;

import org.crusty.engine.CrustyEngine;
import org.crusty.engine.FontStore;
import org.crusty.engine.Screen;
import org.crusty.g2103.Game;
import org.crusty.mandelbrot.Mandelbrot;

public class LevelNameInput extends Screen {

	Image background;
	String name = "";
	String country = Locale.getDefault().getDisplayCountry();
	int score;
	
	public LevelNameInput(CrustyEngine engine) {
		super(engine);
		background = Mandelbrot.renderRandom(800, 600);
		reset();
	}
	
	public void reset() {
		 score = -1;
	}
	
	public void logic(long dt) {
		if (score == -1) {
			LevelMaze levelMaze = ((LevelMaze) ((Game) getEngine()).screenMaze);
			score = levelMaze.getScore();
		}
	}
	
	public void keyTyped(KeyEvent e) {
//		System.out.println("Name: " + name);
//		System.out.println("e.getKeyChar(): " + e.getKeyChar());
		if (e.getKeyChar() <= 'Z' && e.getKeyChar() >= 'A' ||
				e.getKeyChar() <= 'z' && e.getKeyChar() >= 'a' ||
				e.getKeyChar() <= '9' && e.getKeyChar() >= '0') {
			if (name.length() < 12)
				name += (char) e.getKeyChar();
		} else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			if (name.length() > 0) {
				name = name.substring(0, name.length() - 1);
			}
		} else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			if (name.length() >= 3) {
//				LevelMaze levelMaze = ((LevelMaze) ((Game) getEngine()).screenMaze);
//				int score = levelMaze.getScore();
				LevelHighScores highScoreScreen = (LevelHighScores) ((Game) getEngine()).screenHighScores;
				setCurrentScreen(highScoreScreen);
				highScoreScreen.submitHighScore(name, "" + score, country);
				highScoreScreen.refreshHighScores();
			}
		} 
	}
	
	public void draw(Graphics2D g) {
		
		// Background
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.drawImage(background, 0, 0, null);
		
		g.setFont(FontStore.woahFont);
		g.setColor(Color.WHITE);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g.drawString("2103", -50, 350);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		g.setFont(FontStore.massiveFont);
		g.drawString("" + score, 200, 260);
		g.setFont(FontStore.mainFont);
		g.drawString(country, 260, 340);
		g.drawString("Enter name: " + name + "_", 260, 380);
		
		super.draw(g);
		
//		g.drawString("The year is 2103 AD.", 260, 190);
//		g.drawString("", 260, 190);
//		g.drawString("", 260, 210);
//		g.drawString("", 260, 230);
//		g.drawString("", 260, 250);
		
	}

}
