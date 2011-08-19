package org.crusty.g2103.enemies.spawners;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.crusty.engine.Screen;
import org.crusty.engine.GUI.MessageBox;
import org.crusty.g2103.Cell;
import org.crusty.g2103.enemies.Enemy;

public abstract class WaveSpawner extends MessageBox {

	int enemyNum;
	int currentEnemyNum = 0;
	
	long timeBetweenEnemy = 500;
	long lastSpawnTime = 0;
	
	Enemy e;
	Screen s;
	
	double speed;
	int hp;
	
	Cell start;
	
	public WaveSpawner(Screen s, Cell start, int enemyNum, double speed, int hp, 
			int x, int y, int width, int height) {
		super(s, x, y, width, height);
		this.start = start;
		this.s = s;
		this.enemyNum = enemyNum;
		this.speed = speed;
		this.hp = hp;
	}

	public abstract void logic(long dt);
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
}
