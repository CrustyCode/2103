package org.crusty.g2103;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.crusty.engine.entity.RootEntity;
import org.crusty.engine.Screen;
import org.crusty.g2103.enemies.Enemy;
import org.crusty.g2103.levels.LevelMaze;
import org.crusty.g2103.towers.CannonTower;
import org.crusty.g2103.towers.Tower;
import org.crusty.math.Vec2;

public class Slug extends RootEntity {

	Enemy e;
	Screen screen;
	int damage;
	float speed;
	Tower tower;
	
	public Slug(Tower tower, Screen screen, Vec2 pos, Enemy e, int damage, float speed) {
		this.damage = damage;
		this.screen = screen;
		this.pos.x = pos.x;
		this.pos.y = pos.y;
		this.e = e;
		this.speed = speed;
		this.tower = tower;
	}
	
	@Override
	public void draw(Graphics2D g) {
		Vec2 len = new Vec2(vel.x, vel.y);
		if (len.x == 0 && len.y == 0)
			len.x = 1;
		len = len.normalise();
		len.x *= 5;
		len.y *= 5;
		g.drawLine((int) pos.x, (int) pos.y, (int) (pos.x - len.x), (int) (pos.y - len.y));
	}
	
	public void logic(long dt) {
		if (screen instanceof LevelMaze) {
			if (e == null)
				((LevelMaze) screen).notifyRemoval(this);
			
			Vec2 vec = new Vec2(e.pos.x - pos.x, e.pos.y - pos.y);
			Vec2 newVel = vec.normalise();
			vel.x = newVel.x * speed;
			vel.y = newVel.y * speed;
			
			super.logic(dt);
			
			if (vec.length() < 1/(speed*2)) {
				((LevelMaze) screen).notifyRemoval(this);
				if (e.doDamage(damage)) {
					tower.incKilled();
				}
				tower.incDamageDone(damage);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

}
