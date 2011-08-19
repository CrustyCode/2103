package org.crusty.g2103.levels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.crusty.engine.CrustyEngine;
import org.crusty.engine.FontStore;
import org.crusty.engine.entity.RootEntity;
import org.crusty.engine.Screen;
import org.crusty.engine.GUI.Button;
import org.crusty.engine.GUI.MouseOverPane;
import org.crusty.g2103.Cell;
import org.crusty.g2103.Game;
import org.crusty.g2103.HPFloater;
import org.crusty.g2103.Maze;
import org.crusty.g2103.Slug;
import org.crusty.g2103.Sound;
import org.crusty.g2103.enemies.Enemy;
import org.crusty.g2103.enemies.spawners.WaveSpawner;
import org.crusty.g2103.gui.FastForwardButton;
import org.crusty.g2103.gui.NextWaveButton;
import org.crusty.g2103.gui.TowerButton;
import org.crusty.g2103.towers.CannonTower;
import org.crusty.g2103.towers.FireTower;
import org.crusty.g2103.towers.LaserTower;
import org.crusty.g2103.towers.SlugTower;
import org.crusty.g2103.towers.Tower;
import org.crusty.mandelbrot.Mandelbrot;
import org.crusty.math.Vec2;

public class LevelMaze extends Screen {

	Color mainColor = Color.YELLOW;
	Maze maze; // 25
	int gold;
	int score;
	Font mainFont = FontStore.smallFont;
	
	int sx = 50 + (20 * 25) + 20;
	int sy = 50;
	
	Cell[][] cells;
	private Cell start;
	
	Button b;
	Button laserTowerButton, fireTowerButton, slugTowerButton, cannonTowerButton;
	
	private int enemyWaveNum;
	private int waveNum;
	
	Image background;
	
	/** Call initMaze() to generate maze */
	public LevelMaze(CrustyEngine engine) {
		super(engine);
		
		initMaze();
		Game.setTimeMultiplier(1);
		
		String[] text = {  	"Click to spawn the next wave.",
							"Each successive wave will spawn",
							"more enemies than before." };
		
		b = new NextWaveButton(this, sx + 20, sy + 460, 80, 22, new MouseOverPane(this, text));
		b.setText("Next Wave");
		
		laserTowerButton = new TowerButton(this, sx + 20, sy + 110, 60, 60, "LaserTower", 50,
				new MouseOverPane(this, new String[] { 	"- Laser Tower -",
													"Fires a long laser stream",
													"that damages many enemies",
													"in a single shot."}));
		fireTowerButton = new TowerButton(this, sx + 100, sy + 110, 60, 60, "FireTower", 50,
				new MouseOverPane(this, new String[] { 	"- Fire Tower -",
													"Blasts enemies surrounding",
													"the tower with heavy fire",
													"damage."}));
		slugTowerButton = new TowerButton(this, sx + 20, sy + 190, 60, 60, "SlugTower", 50,
				new MouseOverPane(this, new String[] { 	"- Slug Tower -",
													"Fires extremely fast low-damage",
													"slugs at the enemy."}));
		cannonTowerButton = new TowerButton(this, sx + 100, sy + 190, 60, 60, "CannonTower", 50,
				new MouseOverPane(this, new String[] { 	"- Cannon Tower -",
													"Fires a single high-powered",
													"projectile inflicting high",
													"damage." }));

		FastForwardButton fastForwardButton = new FastForwardButton(this, sx + 120, sy + 460, 40, 22,
				new MouseOverPane(this, new String[] { "Speed up time!" }));
		
		addEntity(fastForwardButton);
		addEntity(cannonTowerButton);
		addEntity(fireTowerButton);
		addEntity(slugTowerButton);
		addEntity(laserTowerButton);
		addEntity(maze);
		addEntity(b);
		
		background = Mandelbrot.renderRandom(800, 600);
	}
	
	public void initMaze() {
		maze = new Maze(25, 25);
		gold = 300;
		score = 0;
		cells = maze.getCells();
		start = cells[0][0];
		enemyWaveNum = 1;
		waveNum = 1;
	}
	
	public Maze getMaze() {
		return maze;
	}
	
	public void setStart(Cell start) {
		this.start = start;
	}

	public Cell getStart() {
		return start;
	}

	public void setEnemyWaveNum(int currentWave) {
		this.enemyWaveNum = currentWave;
	}

	public int getEnemyWaveNum() {
		return enemyWaveNum;
	}
	
	public int getWaveNum() {
		return waveNum;
	}

	public Enemy getClosestEnemy(Vec2 pos, double range) {
		Enemy en = null;
		double dist = range;
		for (RootEntity e : entities) {
			if (e instanceof Enemy) {
				// Get distance
				double d = new Vec2(pos.x - e.pos.x, pos.y - e.pos.y).length();
				if (d < dist) {
					dist = d;
					en = (Enemy) e;
				}
			}
		}
		return en;
	}
	
	/** Damages enemies in the vec at pos 
	 * @param tower */
	public int damageEnemies(Tower tower, double x1, double y1, double x2, double y2, int damage) {
		int count = 0;
		for (RootEntity e : entities) {
			if (e instanceof Enemy) {
				if (e.getRect().intersectsLine(x1, y1, x2, y2)) {
					tower.incDamageDone(damage);
					if (((Enemy) e).doDamage(damage))
						count++;
				}
			}
		}
		return count;
	}
	
	/** Damage enemies in a circle around pos 
	 * @param tower */
	public int damageEnemies(Tower tower, Vec2 pos, int rangeRadius, int damage) {
		int count = 0;
		for (RootEntity e : entities) {
			if (e instanceof Enemy) {
				Vec2 vec = new Vec2(e.pos.x - pos.x, e.pos.y - pos.y);
				if (vec.length() <= rangeRadius) {
					tower.incDamageDone(damage);
					if (((Enemy) e).doDamage(damage)) {
						count++;
					}
				}
			}
		}
		return count;
	}
	
//	public void createSlug(CannonTower cannonTower, Vec2 pos, Enemy e, int damage, float speed) {
//		
//		Slug s = new Slug(cannonTower, this, pos, e, damage, speed);
//		addEntity(s);
//	}
	
	public void createHPFloater(String text, Vec2 pos, long lifeTime) {
		addEntity(new HPFloater(this, text, pos, lifeTime));
	}
	
	public void pickUpTower(TowerButton towerButton, MouseEvent e) {
		Tower t1 = null;
		String towerType = towerButton.getTowerType();
		int cost = towerButton.getCost();
		if (towerType.equals("LaserTower")) {
			t1 = new LaserTower(this, maze, cost, towerButton);
		} else if (towerType.equals("FireTower")) {
			t1 = new FireTower(this, maze, cost, towerButton);
		} else if (towerType.equals("SlugTower")) {
			t1 = new SlugTower(this, maze, cost, towerButton);
		} else if (towerType.equals("CannonTower")) {
			t1 = new CannonTower(this, maze, cost, towerButton);
		} else {
			// Default
			t1 = new LaserTower(this, maze, cost, towerButton);
		}
		if (gold - cost >= 0) {
			gold -= cost;
			t1.setMouseHold(true);
			t1.setMousePos(e);
			addEntity(t1);
			System.out.println("New Tower: " + towerType);
//			SoundManager.playSound("toneset/5.wav");
			Sound.t5.play();
			towerButton.incCost();
		} else {
			System.out.println("Insufficient funds");
//			SoundManager.playSound("toneset/3.wav");
			Sound.t3.play();
		}
	}
	
	public void unselectEverything() {
		for (RootEntity t : entities) {
			if (t instanceof Tower) {
				((Tower) t).setSelected(false);
			}
		}
	}
	
//	public void spawnSquareEnemy(double speed, int hp) {
//		Enemy e = new SquareEnemy(this, maze, start, speed, hp);
//		addEntity(e);
//		System.out.println("Spawned Enemy: HP: " + hp);
//	}
	
	public void killEnemy(Enemy e, int gold) {
		entities.remove(e);
		this.gold += gold;
		this.score += gold;
		System.out.println("Enemy died.");
//		SoundManager.playSound("toneset/2.wav");
		Sound.t2.play();
	}
	
	public void removeTower(Tower tower, int cost) {
		entities.remove(tower);
		gold += cost;
	}
	
	public void removeSpawner(WaveSpawner waveSpawner) {
		entities.remove(waveSpawner);
		setEnemyWaveNum((int) (getEnemyWaveNum() + Math.ceil(getEnemyWaveNum()*0.4f)));
		waveNum++;
	}

	public void draw(Graphics2D g) {
		
		// Background
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g.drawImage(background, 0, 0, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		// GUI border
		g.setColor(mainColor);
		
		g.drawLine(sx, sy, sx + 5, sy);
		g.drawLine(Game.width - 55, sy, Game.width - 50, sy);
		
		g.drawLine(sx, Game.height - 50, sx + 5, Game.height - 50);
		g.drawLine(Game.width - 55, Game.height - 50, Game.width - 50, Game.height - 50);
		
		g.drawLine(sx, sy, sx, Game.height - 50);
		g.drawLine(Game.width - 50, sy, Game.width - 50, Game.height - 50);
		
		g.setColor(Color.WHITE);
		g.setFont(mainFont);
		g.drawString("Gold:  " + gold, sx + 20, sy + 20);
		g.drawString("Score: " + score, sx + 20, sy + 40);
		g.drawString("Wave:  " + getWaveNum(), sx + 20, sy + 60);
		
		super.draw(g);
	}
	
	public void logic(long dt) {
		super.logic(dt);
		
	}

	public int getScore() {
		return score;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
