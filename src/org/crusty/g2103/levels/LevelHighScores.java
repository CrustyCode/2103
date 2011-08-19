package org.crusty.g2103.levels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.digest.DigestUtils;
import org.crusty.engine.CrustyEngine;
import org.crusty.engine.FontStore;
import org.crusty.engine.Screen;
import org.crusty.g2103.gui.BackButton;
import org.crusty.mandelbrot.Mandelbrot;

public class LevelHighScores extends Screen {

	String[][] scores = null;
	
	Image background;
	
	public LevelHighScores(CrustyEngine engine) {
		super(engine);
		
		BackButton backButton = new BackButton(this, 50, 500, 46, 21, null);
		backButton.setText("Back");	
		addEntity(backButton);
		
		background = Mandelbrot.renderRandom(CrustyEngine.width, CrustyEngine.height);
	}
	
	public void submitHighScore(String name, String score, String country) {
		
		String toHash = name + score + country + "RnwhYcbjaT4PsA4acQuh"; //Secret.key;
		String generatedHash = DigestUtils.md5Hex(toHash);
		
		try {
			String url = "http://www.crustycode.com/towerdefence/highscores.php?mode=submit&";
			country = java.net.URLEncoder.encode(country.toString(), "ISO-8859-1");
			String args = "name=" + name + "&score=" + score + "&country=" + country + "&hash=" + generatedHash;
			url += args;
			System.out.println("URL: " + url);
			
			URL u = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * return String[][] Format:
	 * 	{
	 * 		{ name, score, country }
	 * 		{ name, score, country }
	 * 		{ name, score, country }
	 * 	}
	 * */
	public String[][] getHighScores() {
		String[][] scores = new String[10][3];
		try {
			String url = "http://www.crustycode.com/towerdefence/highscores.php?mode=get";
			
//			System.out.println("URL: " + url);
			
			URL u = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
			String inputLine;
			
			int row = 0;
			int col = 0;
			while ((inputLine = in.readLine()) != null) {
//				System.out.println(inputLine);
				scores[row][col] = inputLine;
				col++;
				if (col > 2) {
					row++;
					col = 0;
				}
				if (row > 9) {
					break;
				}
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scores;
	}
	
	public void refreshHighScores() {
		scores = getHighScores();
	}
	
	public void draw(Graphics2D g) {
		
		// Background
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g.drawImage(background, 0, 0, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		g.setFont(FontStore.woahFont);
		g.setColor(Color.WHITE);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g.drawString("2103", -50, 350);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g.setFont(FontStore.mainFont);
		g.drawString("High Scores", 260, 80);
		g.setColor(Color.YELLOW);
		
		// Load high scores
		
		if (scores == null) {
			scores = getHighScores();
		}
		g.setFont(FontStore.smallFont);
		int xc = -50;
		for (int row = 0; row < 10; row++) {
			if (scores[row][0] == null)
				break;
			g.setFont(FontStore.smallFont);
			g.drawString(scores[row][0], xc + 220, 150 + row*35);
			g.drawString(scores[row][2], xc + 560, 150 + row*35);
			g.setFont(FontStore.mainFont);
			g.drawString(scores[row][1], xc + 390, 150 + row*35);
		}
		for (int row = 0; row < 10; row++) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - row*0.1f));
			g.drawString((row + 1) + ".", xc + 155, 150 + row*35);
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		for (int row = 0; row < 10; row++) {
			g.drawLine(xc + 190, 155 + row*35, xc + 720, 155 + row*35);
		}
		g.drawLine(xc + 360, 130, xc + 360, 480);
		g.drawLine(xc + 530, 130, xc + 530, 480);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		super.draw(g);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
