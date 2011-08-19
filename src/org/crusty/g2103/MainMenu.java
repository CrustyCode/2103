package org.crusty.g2103;

import org.crusty.engine.GUI.Menu;
import org.crusty.engine.Screen;
import org.crusty.g2103.levels.LevelMaze;
import org.crusty.math.Vec2int;

public class MainMenu extends Menu {

	Screen screen;
	
	public MainMenu(Screen screen, Vec2int pos, String[] options) {
		super(pos, options);
		this.screen = screen;
	}

	@Override
	public void activateMenuItem(int index) {
		Sound.t4.play();
		switch (index) {
			case 0 : {
				((Game) screen.getEngine()).screenMaze = new LevelMaze(screen.getEngine());
//				((LevelMaze) ((Game) screen.getEngine()).screenMaze).initMaze();
				screen.setCurrentScreen(((Game) screen.getEngine()).screenMaze);
				System.out.println("Going to levelMaze");
				break;
			}
			case 1 : {
				screen.setCurrentScreen(((Game) screen.getEngine()).screenHighScores);
				System.out.println("Going to highScores");
				break;
			}
			case 2 : {
				System.exit(1);
				break;
			}
		}
	}

	@Override
	public void movedMenuItem() {
		Sound.t6.play();
	}


	
}
