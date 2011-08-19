package org.crusty.g2103.levels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import org.crusty.engine.CrustyEngine;
import org.crusty.engine.FontStore;
import org.crusty.engine.Screen;
import org.crusty.g2103.MainMenu;
import org.crusty.mandelbrot.Mandelbrot;
import org.crusty.math.Vec2int;

public class LevelMenu extends Screen {

	Image background;
//	double XZ, YZ, XA, YA;
//	int maxIteration;
	
	public LevelMenu(CrustyEngine engine) {
		super(engine);
		
		addEntity(new MainMenu(this, new Vec2int(100, 200), new String[] { "Play", "High Scores", "Quit" }));
		
//		Random r = new Random();
		
//		XZ = YZ = r.nextDouble()*2;
//		XA = r.nextDouble() - 0.5;
//		YA = r.nextDouble() - 0.5;
//		maxIteration = 200;
		
//		System.out.println("Mandelbrot arguments");
//		System.out.println("{ " + XZ + ", " + YZ + ", " + XA + ", " + YA + " },");
		
		background = Mandelbrot.renderRandom(800, 600);
	}
	
	public void logic(long dt) {
		super.logic(dt);
		
		// TEMP TODO
		
//		if (CrustyEngine.keys[KeyEvent.VK_L]) {
//			XZ /= 2;
//			YZ /= 2;
//			render();
//		}
//		if (CrustyEngine.keys[KeyEvent.VK_K]) {
//			XZ *= 2;
//			YZ *= 2;
//			render();
//		}
//		
//		if (CrustyEngine.keys[KeyEvent.VK_N]) {
//			maxIteration += 1000;
//			render();
//		}
//		if (CrustyEngine.keys[KeyEvent.VK_M]) {
//			maxIteration -= 1000;
//			render();
//		}
//		
//		if (CrustyEngine.keys[KeyEvent.VK_W]) {
//			YA -= 0.1 * YZ;
//			render();
//		}
//		getEngine();
//		if (CrustyEngine.keys[KeyEvent.VK_A]) {
//			XA -= 0.1 * XZ;
//			render();
//		}
//		getEngine();
//		if (CrustyEngine.keys[KeyEvent.VK_S]) {
//			YA += 0.1 * YZ;
//			render();
//		}
//		getEngine();
//		if (CrustyEngine.keys[KeyEvent.VK_D]) {
//			XA += 0.1 * XZ;
//			render();
//		}
//		
//		if (CrustyEngine.keys[KeyEvent.VK_SHIFT]) {
//			System.out.println("{ " + XZ + ", " + YZ + ", " + XA + ", " + YA + ", " + maxIteration + " },");
//		}
		
	}
	
//	public void render() {
//		background = Mandelbrot.render(800, 600, XZ, YZ, XA, YA, maxIteration);
//		
//	}

	public void draw(Graphics2D g) {
		
		// Background
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.drawImage(background, 0, 0, null);
		
		g.setFont(FontStore.woahFont);
		g.setColor(Color.WHITE);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g.drawString("2103", -50, 350);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		g.setFont(FontStore.massiveFont);
		g.setColor(Color.WHITE);
		g.drawString("2103", 320, 450);
		g.setFont(FontStore.smallFont);
		g.drawString("by Elliot Walmsley", 460, 480);
		g.setFont(FontStore.smallFont);
		g.setColor(Color.YELLOW);
		
		super.draw(g);
		
//		g.drawString("The year is 2103 AD.", 260, 190);
//		g.drawString("", 260, 190);
//		g.drawString("", 260, 210);
//		g.drawString("", 260, 230);
//		g.drawString("", 260, 250);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
