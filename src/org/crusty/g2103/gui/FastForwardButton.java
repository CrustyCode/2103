package org.crusty.g2103.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.crusty.engine.Screen;
import org.crusty.engine.GUI.Button;
import org.crusty.engine.GUI.MouseOverPane;
import org.crusty.g2103.Game;
import org.crusty.g2103.Sound;

public class FastForwardButton extends Button {

	private final int multiplier = 4;
	
	public FastForwardButton(Screen s, int x, int y, int width, int height, MouseOverPane mouseOverPane) {
		super(s, x, y, width, height, mouseOverPane);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (mouseOver) {
			if (Game.getTimeMultiplier() == multiplier) {
				Game.setTimeMultiplier(1);
				Sound.t5.play();
			} else {
				Game.setTimeMultiplier(multiplier);
				Sound.t7.play();
			}
		}
	}

	public void draw(Graphics2D g) {
		super.draw(g);
		if (Game.getTimeMultiplier() == multiplier) {
			g.setColor(Color.YELLOW);
			g.drawRect(getRect().x - 3, getRect().y - 3, 
					getRect().width + 6, getRect().height + 6);
		}
		int ox = 20, oy = 11;
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.setColor(Color.WHITE);
		int[] xPoints = { -5, 0, -5 };
		int[] yPoints = { -5, 0, 5 };
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] += ox + this.getRect().getX();
			yPoints[i] += oy + this.getRect().getY();
		}
		g.drawPolyline(xPoints, yPoints, xPoints.length);
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] += 5;
		}
		g.drawPolyline(xPoints, yPoints, xPoints.length);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
}
