package org.crusty.g2103.gui;

import java.awt.event.MouseEvent;

import org.crusty.engine.entity.RootEntity;
import org.crusty.engine.Screen;
import org.crusty.engine.GUI.Button;
import org.crusty.engine.GUI.MouseOverPane;
import org.crusty.g2103.Sound;
import org.crusty.g2103.enemies.Enemy;
import org.crusty.g2103.enemies.spawners.CircleWaveSpawner;
import org.crusty.g2103.enemies.spawners.CrossWaveSpawner;
import org.crusty.g2103.enemies.spawners.SquareWaveSpawner;
import org.crusty.g2103.enemies.spawners.WaveSpawner;
import org.crusty.g2103.levels.LevelMaze;

public class NextWaveButton extends Button {

	public NextWaveButton(Screen s, int x, int y, int width, int height, MouseOverPane mouseOverPane) {
		super(s, x, y, width, height, mouseOverPane);
	}

	@Override
	public void mousePressed(MouseEvent e1) {
		if (mouseOver) {
			if (screen instanceof LevelMaze) {
				boolean enemiesOnScreen = false;
				for (RootEntity e : screen.getEntities()) {
					if (e instanceof Enemy) {
						enemiesOnScreen = true;
						break;
					}
				}
				if (!enemiesOnScreen) {
					
					int waveNum = ((LevelMaze) screen).getWaveNum();
					if (waveNum % 7 == 0) {
						int newHp = (int) (100 + Math.floor(((LevelMaze) screen).getEnemyWaveNum())*3);
						newHp *= 1.5f;
						WaveSpawner ws = new CircleWaveSpawner(screen, ((LevelMaze) screen).getStart(), 
								((LevelMaze) screen).getEnemyWaveNum(), 1.5, newHp);
						screen.addEntity(ws);
					} else if (waveNum % 3 == 0) {
						int newHp = (int) (100 + Math.floor(((LevelMaze) screen).getEnemyWaveNum())*3);
						newHp *= 0.5;
						WaveSpawner ws = new CrossWaveSpawner(screen, ((LevelMaze) screen).getStart(), 
								((LevelMaze) screen).getEnemyWaveNum(), 6, newHp);
						screen.addEntity(ws);
					} else {
						int newHp = (int) (100 + Math.floor(((LevelMaze) screen).getEnemyWaveNum())*3);
						WaveSpawner ws = new SquareWaveSpawner(screen, ((LevelMaze) screen).getStart(), 
								((LevelMaze) screen).getEnemyWaveNum(), 3, newHp);
						screen.addEntity(ws);
					}
					
					Sound.t3.play();
				} else {
					Sound.t2.play();
				}
			}
		}
	}

}
