package org.crusty.g2103.towers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import org.crusty.engine.Screen;
import org.crusty.g2103.Game;
import org.crusty.g2103.Maze;
import org.crusty.g2103.enemies.Enemy;
import org.crusty.g2103.gui.TowerButton;
import org.crusty.g2103.levels.LevelMaze;
import org.crusty.math.Vec2;

public class LaserTower extends Tower {

	private long timeBetweenShots = 500;
	private long lengthOfLaser = 100;
	private long lastTimeShot = 0;
	
	private boolean laserOn = false;
	
	Enemy targettedEnemy = null;
	private Vec2 lastShootingPos;
	
	public LaserTower(Screen screen, Maze maze, int costOfThisTower, TowerButton tb) {
		super(screen, maze, costOfThisTower, tb);
		
		rangeRadius = 70;
		
		stage = 0;
//		cost = 50;
		damage = 5;
		
		lastShootingPos = new Vec2(0, 0);
	}

	public void draw(Graphics2D g) {
		super.draw(g);
		
		g.setColor(Color.WHITE);
		g.drawOval((int) pos.x - 4, (int) pos.y - 4, 8, 8);
		g.drawOval((int) pos.x - 5, (int) pos.y - 5, 10, 10);
		
		if (laserOn) {
			g.setColor(Color.WHITE);
			Vec2 dirVec = new Vec2(lastShootingPos.x - pos.x, lastShootingPos.y - pos.y); // targettedEnemy.pos
			dirVec = dirVec.normalise();
			Vec2 vec = new Vec2(0, 0);
			
			int tailPartLen = 5;
			int fadeNum = 15;
			
			vec.x = dirVec.x * tailPartLen;
			vec.y = dirVec.y * tailPartLen;
			dirVec.x *= 150;
			dirVec.y *= 150;
			g.drawLine((int) pos.x, (int) pos.y, (int) (pos.x + dirVec.x), (int) (pos.y + dirVec.y));
			
			Vec2[] fade = new Vec2[fadeNum];
			for (int i = 0; i < fadeNum; i++) {
				fade[i] = new Vec2(pos.x + dirVec.x + vec.x*i, pos.y + dirVec.y + vec.y*i);
//				System.out.println(fade[i]);
			}
//			System.out.println("-----");
			for (int i = 0; i < fadeNum - 1; i++) {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - (1f/fadeNum)*i));
				g.drawLine((int) fade[i].x, (int) fade[i].y, (int) fade[i+1].x, (int) fade[i+1].y);
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			
		}
	}
	
	public void logic(long dt) {
		// Not calling super.logic()
		// Get closest enemy
		if (screen instanceof LevelMaze) {
			if (!mouseHold) {
				if (laserOn && Game.currentTimeMillis() - lastTimeShot > lengthOfLaser) {
					laserOn = false;
				}
				
				if (laserOn && targettedEnemy != null) {
					lastShootingPos.x = targettedEnemy.pos.x;
					lastShootingPos.y = targettedEnemy.pos.y;
				}
				
				if (Game.currentTimeMillis() - lastTimeShot > timeBetweenShots) {
					targettedEnemy = ((LevelMaze) screen).getClosestEnemy(pos, rangeRadius);
					
					if (targettedEnemy != null) {
						lastShootingPos.x = targettedEnemy.pos.x;
						lastShootingPos.y = targettedEnemy.pos.y;
							
						lastTimeShot = Game.currentTimeMillis();
						laserOn = true;
						
						Vec2 dirVec = new Vec2(targettedEnemy.pos.x - pos.x, targettedEnemy.pos.y - pos.y);
						dirVec = dirVec.normalise();
						dirVec.x *= 150;
						dirVec.y *= 150;
						int count = ((LevelMaze) screen).damageEnemies(this, pos.x, pos.y, 
								(pos.x + dirVec.x), (pos.y + dirVec.y),
								damage);
						this.enemiesKilled += count;
						timesShot++;
//						SoundManager.playSound("laserTowerShoot.wav");
					}
				}
			}
		}
	}
	
}
