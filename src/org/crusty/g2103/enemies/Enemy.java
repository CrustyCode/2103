package org.crusty.g2103.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Random;

import org.crusty.engine.entity.RootEntity;
import org.crusty.engine.Screen;
import org.crusty.g2103.Cell;
import org.crusty.g2103.Game;
import org.crusty.g2103.Maze;
import org.crusty.g2103.Sound;
import org.crusty.g2103.levels.LevelMaze;
import org.crusty.g2103.levels.LevelNameInput;
import org.crusty.math.Vec2;
import org.crusty.math.Vec2int;

public abstract class Enemy extends RootEntity {

	int hp, maxhp;
	boolean move;
	Vec2int tar;
	Cell curCell;
	Cell prevCell = null;
	double speed;
	Maze maze;
	Screen screen;
	boolean dead = false;
	
	int gold;
	
	public Enemy(Screen screen, Maze maze, Cell pathStart, double speed, int hp) {
		Random r = new Random();
		gold = 7 + r.nextInt(3);
		this.screen = screen;
		this.hp = hp;
		this.maxhp = hp;
		move = true;
		this.speed = speed;
		curCell = pathStart;
//		System.out.println("curCellpos: " + curCell.pos.toString());
		this.maze = maze;
		tar = maze.gridCoordsToPos(curCell.pos); //new Vec2(curCell.pos.x, curCell.pos.y);
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public boolean doDamage(int damage) {
		if (!dead) {
			hp -= damage;
			if (screen instanceof LevelMaze) {
				((LevelMaze) screen).createHPFloater("" + damage, pos, 300 + (damage*100));
			}
			if (hp < 0) {
				hp = 0;
				if (screen instanceof LevelMaze) {
					((LevelMaze) screen).killEnemy(this, gold);
				}
				dead = true;
				return true;
			}
		}
		return false;
//		System.out.println("Damaged: " + damage + " HP: " + hp);
	}
	
	public void logic(long dt) {
		if (move) {
			Vec2 vec = new Vec2(tar.x - pos.x, tar.y - pos.y);
			Vec2 acc = vec.normalise();
			this.acc.x = acc.x * speed;
			this.acc.y = acc.y * speed;
//			System.out.println("vec length: " + vec.length());
			if (vec.length() < speed * 0.5) {
//				System.out.println("Less than 1 away");
				this.acc.x = 0; this.acc.y = 0;
				this.vel.x = 0; this.vel.y = 0;
				this.pos.x = tar.x;
				this.pos.y = tar.y;
//				System.out.println("curCell.next != null: " + (curCell.next != null));
				if (curCell.hasNextSinglePath(prevCell) == true) {
//					curCell = curCell.next;
					Cell prevTemp = curCell;
					curCell = curCell.getNextSinglePath(prevCell);
					prevCell = prevTemp;
					tar = maze.gridCoordsToPos(curCell.pos);
//					System.out.println("Next pos: " + curCell.pos);
				} else {
					System.out.println("Game over.");
//					LevelMaze levelMaze = ((LevelMaze) screen);
//					int score = levelMaze.getScore();
//					LevelHighScores highScoreScreen = (LevelHighScores) ((Game) screen.getEngine()).screenHighScores;
//					screen.setCurrentScreen(highScoreScreen);
//					highScoreScreen.submitHighScore("name", "" + score, "country");
//					highScoreScreen.refreshHighScores();
					LevelNameInput nameInput = (LevelNameInput)  ((Game) screen.getEngine()).screenNameInput;
					nameInput.reset();
					screen.setCurrentScreen(nameInput);
					move = false;
					Sound.endGame.play();
				}
			}
		}
		super.logic(dt);
//		System.out.println(pos.toString());
		
		// Update rect
		this.setRect((int) pos.x - 5, (int) pos.y - 5, 10, 10);
	}
	
	@Override
	/** Override and draw enemy, super.draw(g) for HP bar */
	public void draw(Graphics2D g) {
		// HP
		g.setColor(Color.RED);
		Vec2int hpBox = new Vec2int((int) pos.x - 5, (int) pos.y - 8);
		g.fillRect(hpBox.x, hpBox.y, 11, 2);
		g.setColor(Color.YELLOW);
		int hpPerc = (int) (((float) hp/maxhp) * 11);
		g.fillRect(hpBox.x, hpBox.y, hpPerc, 2);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
}
