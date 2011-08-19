package org.crusty.g2103.enemies.spawners;

import org.crusty.engine.Screen;
import org.crusty.g2103.Cell;
import org.crusty.g2103.Game;
import org.crusty.g2103.enemies.CircleEnemy;
import org.crusty.g2103.levels.LevelMaze;

public class CircleWaveSpawner extends WaveSpawner {

	public CircleWaveSpawner(Screen s, Cell start, int enemyNum, double speed, int hp) {
		super(s, start, enemyNum, speed, hp, 250, 20, 100, 22);
		timeBetweenEnemy = 500;
	}

	@Override
	public void logic(long dt) {
		LevelMaze scr = null;
		if (s instanceof LevelMaze)
			scr = (LevelMaze) s;
		else
			return;
		
		if (Game.currentTimeMillis() - lastSpawnTime > timeBetweenEnemy) {
			setText((enemyNum - currentEnemyNum) + " spawning.");
			if (currentEnemyNum < enemyNum) {
				scr.addEntity(new CircleEnemy(s, scr.getMaze(), start, speed, hp));
				lastSpawnTime = Game.currentTimeMillis();
				currentEnemyNum++;
			} else {
				scr.removeSpawner(this);
			}
		}
	}

}
