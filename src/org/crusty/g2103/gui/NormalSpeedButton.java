package org.crusty.g2103.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.crusty.engine.Screen;
import org.crusty.engine.GUI.Button;
import org.crusty.engine.GUI.MouseOverPane;
import org.crusty.g2103.Game;

public class NormalSpeedButton extends Button {

	public NormalSpeedButton(Screen s, int x, int y, int width, int height, MouseOverPane mouseOverPane) {
		super(s, x, y, width, height, mouseOverPane);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (mouseOver) {
			Game.setTimeMultiplier(1);
		}
	}

	public void draw(Graphics2D g) {
		super.draw(g);
		int ox = 18, oy = 11;
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.setColor(Color.WHITE);
		int[] xPoints = { -5, 0, -5 };
		int[] yPoints = { -5, 0, 5 };
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] += ox + this.getRect().getX();
			yPoints[i] += oy + this.getRect().getY();
		}
		g.drawPolyline(xPoints, yPoints, xPoints.length);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
}
