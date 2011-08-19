package org.crusty.g2103.towers;

import java.awt.Color;
import java.awt.Graphics2D;

import org.crusty.engine.Screen;
import org.crusty.g2103.Game;
import org.crusty.g2103.Maze;
import org.crusty.g2103.enemies.Enemy;
import org.crusty.g2103.gui.TowerButton;
import org.crusty.g2103.levels.LevelMaze;
import org.crusty.math.Vec2;

public class FireTower extends Tower {

	private long timeBetweenShots = 2000;
	private long lengthOfFire = 500;
	private long lastTimeShot = 0;
	
	Enemy targettedEnemy = null;
	
	private boolean fireOn = false;
	
	public FireTower(Screen screen, Maze maze, int costOfThisTower, TowerButton tb) {
		super(screen, maze, costOfThisTower, tb);
		
		rangeRadius = 30;
		
		stage = 0;
//		cost = 50;
		damage = 20;
	}

	public void draw(Graphics2D g) {
		super.draw(g);
		
		g.setColor(Color.WHITE);
		g.drawLine(	(int) pos.x - 4, (int) pos.y - 4, 
					(int) pos.x + 4, (int) pos.y + 4);
		g.drawLine(	(int) pos.x + 4, (int) pos.y - 4, 
					(int) pos.x - 4, (int) pos.y + 4);
		
		if (fireOn) {
			g.setColor(Color.YELLOW);
			for (int i = 0; i < 5; i++) {
				drawOvalAroundCenter(g, pos, 5 + i*5);
			}
		}
	}
	
	public void drawOvalAroundCenter(Graphics2D g, Vec2 pos, int radius) {
		g.drawOval((int) pos.x - radius, (int) pos.y - radius, radius*2, radius*2);
	}
	
	public void logic(long dt) {
		// Not calling super.logic()
		// Get closest enemy
		if (screen instanceof LevelMaze) {
			if (!mouseHold) {
				if (fireOn && Game.currentTimeMillis() - lastTimeShot > lengthOfFire) {
					fireOn = false;
				}
				
				if (Game.currentTimeMillis() - lastTimeShot > timeBetweenShots) {
					targettedEnemy = ((LevelMaze) screen).getClosestEnemy(pos, rangeRadius);
					
					if (targettedEnemy != null) {
						lastTimeShot = Game.currentTimeMillis();
						fireOn = true;
						int count = ((LevelMaze) screen).damageEnemies(this, pos, rangeRadius, damage);
						this.enemiesKilled += count;
						timesShot++;
//						SoundManager.playSound("fireTowerShoot.wav");
					}
				}
			}
		}
	}

}
