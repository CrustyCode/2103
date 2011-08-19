package org.crusty.g2103;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.crusty.engine.FontStore;
import org.crusty.engine.entity.RootEntity;
import org.crusty.engine.Screen;
import org.crusty.math.Vec2;

public class HPFloater extends RootEntity {

	String text = "";
	Screen s;
	long lifeTime;
	long startTime;
	boolean show = true;
	
	public HPFloater(Screen s, String text, Vec2 pos, long lifeTime) {
		startTime = Game.currentTimeMillis();
		this.lifeTime = lifeTime;
		this.s = s;
		this.text = text;
		this.pos = new Vec2(pos.x, pos.y);
		this.acc.y = -0.3f;
		alpha = 0.7f;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.setFont(FontStore.tinyFont);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawString(text, (int) pos.x, (int) pos.y);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
	
	public void logic(long dt) {
		if (show) {
			if (Game.currentTimeMillis() - startTime > lifeTime) {
				show = false;
				s.notifyRemoval(this);
			}
		}
		super.logic(dt);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

}
