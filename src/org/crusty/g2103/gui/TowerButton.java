package org.crusty.g2103.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.crusty.engine.Screen;
import org.crusty.engine.GUI.Button;
import org.crusty.engine.GUI.MouseOverPane;
import org.crusty.g2103.levels.LevelMaze;

public class TowerButton extends Button {

	String towerType; // Type of tower
	
//	String 	laserTowerCost = "70", 
//			fireTowerCost = "60", 
//			slugTowerCost = "50", 
//			cannonTowerCost = "40";
	
//	String cost;
	int cost;
	
	/** Screen s, int x, int y, int width, int height, Tower t */
	public TowerButton(Screen s, int x, int y, int width, int height, String towerType, int cost, MouseOverPane mouseOverPane) {
		super(s, x, y, width, height, mouseOverPane);
		this.towerType = towerType;
		this.cost = cost;
//		setText(towerType);
	}
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (getRect().contains(e.getX(), e.getY())) {
			// Pick up a tower with mouse
			if (this.screen instanceof LevelMaze) {
				((LevelMaze) screen).pickUpTower(this, e);
			}
		}
	}
	
	public int getCost() {
		return cost;
	}
	
	public String getTowerType() {
		return towerType;
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		int ox = 30, oy = 30;
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		if (towerType.equals("LaserTower")) {
			g.setColor(Color.WHITE);
			g.drawOval((int) getRect().x - 15 + ox, (int) getRect().y - 15 + oy, 30, 30);
			g.drawOval((int) getRect().x - 18 + ox, (int) getRect().y - 18 + oy, 36, 36);
		} else if (towerType.equals("FireTower")) {
			g.setColor(Color.WHITE);
			g.drawLine(	(int) getRect().x - 15 + ox, (int) getRect().y - 15 + oy, 
						(int) getRect().x + 15 + ox, (int) getRect().y + 15 + oy);
			g.drawLine(	(int) getRect().x + 15 + ox, (int) getRect().y - 15 + oy, 
						(int) getRect().x - 15 + ox, (int) getRect().y + 15 + oy);
		} else if (towerType.equals("SlugTower")) {
			g.setColor(Color.WHITE);
			g.drawRect((int) getRect().x - 15 + ox, (int) getRect().y - 15 + oy, 30, 30);
		} else if (towerType.equals("CannonTower")) {
			g.setColor(Color.WHITE);
			int[] xPoints = {   0, 15, -15 };
			int[] yPoints = { -15, 15,  15 };
			for (int i = 0; i < xPoints.length; i++) {
				xPoints[i] += getRect().x + ox;
				yPoints[i] += getRect().y + oy;
			}
			g.drawPolygon(xPoints, yPoints, xPoints.length);
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g.drawString("" + cost, getRect().x + 40, getRect().y + 15);
	}

	public void incCost() {
		cost++;
	}

	public void decCost() {
		cost--;
	}
	
}
