package org.crusty.g2103.towers;

import java.awt.Color;
import java.awt.Graphics2D;

import org.crusty.engine.Screen;
import org.crusty.g2103.Game;
import org.crusty.g2103.Maze;
import org.crusty.g2103.Slug;
import org.crusty.g2103.enemies.Enemy;
import org.crusty.g2103.gui.TowerButton;
import org.crusty.g2103.levels.LevelMaze;

public class CannonTower extends Tower {

	private long timeBetweenShots = 1000;
	private long lengthOfFire = 200;
	private long lastTimeShot = 0;
	
	Enemy targettedEnemy = null;
	
	private boolean justFired = false;
	
	public CannonTower(Screen screen, Maze maze, int costOfThisTower, TowerButton tb) {
		super(screen, maze, costOfThisTower, tb);
		
		rangeRadius = 100;
		
		stage = 0;
//		cost = 50;
		damage = 10;
	}

	public void draw(Graphics2D g) {
		super.draw(g);
		
		g.setColor(Color.WHITE);
//		g.drawRect((int) pos.x - 4, (int) pos.y - 4, 8, 8);
		int[] xPoints = {  0, 4, -4 };
		int[] yPoints = { -4, 4,  4 };
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] += pos.x;
			yPoints[i] += pos.y;
		}
		g.drawPolygon(xPoints, yPoints, xPoints.length);
		
		if (justFired) {
			g.setColor(Color.YELLOW);
			g.drawOval((int) pos.x - 2, (int) pos.y - 2, 4, 4);
		}
	}
	
	public void logic(long dt) {
		// Not calling super.logic()
		// Get closest enemy
		if (screen instanceof LevelMaze) {
			if (!mouseHold) {
				if (justFired && Game.currentTimeMillis() - lastTimeShot > lengthOfFire) {
					justFired = false;
				}
				
				if (Game.currentTimeMillis() - lastTimeShot > timeBetweenShots) {
					targettedEnemy = ((LevelMaze) screen).getClosestEnemy(pos, rangeRadius);
					
					if (targettedEnemy != null) {
						lastTimeShot = Game.currentTimeMillis();
						justFired = true;
						//((LevelMaze) screen).createSlug(this, pos, targettedEnemy, damage, 0.1f);
						Slug s = new Slug(this, screen, pos, targettedEnemy, damage, 0.1f);
						screen.addEntity(s);
						timesShot++;
//						SoundManager.playSound("cannonTowerShoot.wav");
					}
				}
			}
		}
	}

}

