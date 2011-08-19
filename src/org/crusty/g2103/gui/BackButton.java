package org.crusty.g2103.gui;

import java.awt.event.MouseEvent;

import org.crusty.engine.Screen;
import org.crusty.engine.GUI.Button;
import org.crusty.engine.GUI.MouseOverPane;
import org.crusty.g2103.Game;
import org.crusty.g2103.Sound;
import org.crusty.g2103.levels.LevelHighScores;

public class BackButton extends Button {

	public BackButton(Screen s, int x, int y, int width, int height, MouseOverPane mouseOverPane) {
		super(s, x, y, width, height, mouseOverPane);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (mouseOver) {
			if (screen instanceof LevelHighScores) {
				screen.setCurrentScreen(((Game) screen.getEngine()).screenMenu);
				System.out.println("Going to mainMenu");
				Sound.t3.play();
			}
		}
	}

}
