package org.crusty.g2103.towers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.crusty.engine.FontStore;
import org.crusty.engine.entity.RootEntity;
import org.crusty.engine.Screen;
import org.crusty.g2103.Cell;
import org.crusty.g2103.Maze;
import org.crusty.g2103.Sound;
import org.crusty.g2103.gui.TowerButton;
import org.crusty.g2103.levels.LevelMaze;
import org.crusty.math.Vec2int;

public abstract class Tower extends RootEntity {

	protected boolean mouseHold = false;
	private boolean selected = false;
	
	int stage = 0;
	int damage; // cost
	int costOfThisTower;
	
	Maze maze;
	int rangeRadius;
	
	Screen screen;
	
	protected int totalDamage = 0;
	protected int timesShot = 0;
	protected int enemiesKilled = 0;
	
	TowerButton tb;
	
	Font font = FontStore.tinyFont;
	
	public Tower(Screen screen, Maze maze, int costOfThisTower, TowerButton tb) {
		this.maze = maze;
		this.screen = screen;
		this.costOfThisTower = costOfThisTower;
		this.tb = tb;
		rangeRadius = 50;
	}
	
	public int getStage() {
		return stage;
	}
	
//	public int getCost(int stage) {
//		return cost;
//	}
	
//	public void upgrade() {
//		if (stage < costs.length - 1) {
//			stage++;
//			System.out.println("Upgraded Tower");
//		}
//	}
	
	public void setMousePos(MouseEvent e) {
		pos.x = e.getX();
		pos.y = e.getY();
		this.setRect(e.getX() - maze.boxWidth/2, e.getY() - maze.boxHeight/2, 0, 0);
	}
	
	public void setSelected(boolean b) {
		selected = b;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setMouseHold(boolean b) {
		mouseHold = b;
	}
	
	public boolean isMouseHold() {
		return mouseHold;
	}

	@Override
	/** Override and call super.draw(g) */
	public void draw(Graphics2D g) {
		
		double x = getRect().getX();
		double y = getRect().getY();
		double w = getRect().getWidth();
		double h = getRect().getHeight();
		
		if (selected || mouseHold) {
			// Draw fire radius
			g.setColor(Color.RED);
			g.drawOval((int) x + maze.boxWidth/2 - rangeRadius, (int) y + maze.boxHeight/2 - rangeRadius, rangeRadius*2, rangeRadius*2);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			g.fillOval((int) x + maze.boxWidth/2 - rangeRadius, (int) y + maze.boxHeight/2 - rangeRadius, rangeRadius*2, rangeRadius*2);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
		
		if (selected) {
			g.setColor(Color.WHITE);
			g.drawRect((int) x - 3, (int) y - 3, (int) w + 6, (int) h + 6);
			
			// Info Box
			int ox = 590, oy = 320;
			g.setColor(Color.YELLOW);
			g.drawRect(ox, oy, 140, 170);
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString("Damage: " + totalDamage, ox + 15, oy + 20);
			g.drawString("Shot:   " + timesShot, ox + 15, oy + 20*2);
			g.drawString("Killed: " + enemiesKilled, ox + 15, oy + 20*3);
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!mouseHold && this.getRect().contains(e.getX(), e.getY())) {
			// Unselected everything
			boolean toState = !selected;
			if (screen instanceof LevelMaze) {
				((LevelMaze) screen).unselectEverything();
			}
			selected = toState;
			Sound.t4.play();
		}
		
		if (mouseHold) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				// Place selected tower
				Vec2int mazeCoords = maze.posToGridCoords(new Vec2int(e.getX(), e.getY()));
				
				maze.getCells()[mazeCoords.x][mazeCoords.y].placeTower(this);
				
//				System.out.println("Attempting to place tower at: " + mazeCoords.toString());
				Sound.t4.play();
			} else {
				// Get rid of tower
				if (screen instanceof LevelMaze) {
					((LevelMaze) screen).removeTower(this, costOfThisTower);
					tb.decCost();
				}
				Sound.t3.play();
			}
		}
	}
	
//	public void logic(long dt) {
//		// Not calling super.logic()
//
//	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (mouseHold) {
			pos.x = e.getX();
			pos.y = e.getY();
			this.setRect(e.getX()- maze.boxWidth/2, e.getY() - maze.boxHeight/2, maze.boxWidth, maze.boxHeight);
		}
	}

	public void setCellPos(Cell cell) {
		mouseHold = false;
		Vec2int newPos = maze.gridCoordsToPos(cell.pos);
		pos.x = newPos.x;
		pos.y = newPos.y;
		this.setRect(newPos.x - maze.boxWidth/2, newPos.y - maze.boxHeight/2, maze.boxWidth, maze.boxHeight);
	}

	public void incDamageDone(int damage) {
		this.totalDamage += damage;
	}

	public void incKilled() {
		this.enemiesKilled++;
	}

}
