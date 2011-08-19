package org.crusty.g2103.enemies;

import java.awt.Color;
import java.awt.Graphics2D;

import org.crusty.engine.Screen;
import org.crusty.g2103.Cell;
import org.crusty.g2103.Maze;

public class CrossEnemy extends Enemy {

	public CrossEnemy(Screen screen, Maze maze, Cell pathStart, double speed,
			int hp) {
		super(screen, maze, pathStart, speed, hp);
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.drawLine((int) pos.x - 5, (int) pos.y - 5, (int) pos.x + 5, (int) pos.y + 5);
		g.drawLine((int) pos.x + 5, (int) pos.y - 5, (int) pos.x - 5, (int) pos.y + 5);
		super.draw(g);
	}
}
