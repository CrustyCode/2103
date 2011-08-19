package org.crusty.g2103;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Random;

import org.crusty.engine.entity.RootEntity;
import org.crusty.g2103.Cell;
import org.crusty.math.Vec2int;

public class Maze extends RootEntity {

	public int startx;
	public int starty;
	public int boxWidth;
	public int boxHeight;
	
//	Cell[][] path;

	protected int width, height;
	Random r;
	protected Cell mazeStart;
	protected Cell[][] cells;
//	protected Cell pathStart;
	
	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
		r = new Random();
		
		long seed = r.nextLong();
		System.out.println("Seed: " + seed);
		r.setSeed(seed);
		
		startx = 50;
		starty = 50;
		boxWidth = 20; // 20
		boxHeight = 20;
		
		mazeStart = generateMaze();
		
//		path = cells; //.clone();
		genPath(cells);
	}
	
	/** Dead end filling */
	public void genPath(Cell[][] cells) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if ((x == 0 && y == 0) ||
						(x == width - 1 && y == height - 1))
					continue;
				Cell[] linked = cells[x][y].linkedNeighbours;
				int num = 0;
				for (int i = 0; i < 4; i++)
					if (linked[i] == null)
						num++;
				if (num == 3) {
					// Fill
//					System.out.println("> " + x + ", " + y);
					recursiveFill(cells[x][y]);
				}
			}
		}
	}
	
	public void recursiveFill(Cell c) {
		if ((c.pos.x == 0 && c.pos.y == 0) ||
				(c.pos.x == width - 1 && c.pos.y == height - 1))
			return;
		Cell[] linked = c.linkedNeighbours;
		int num = 0;
		for (int i = 0; i < 4; i++)
			if (linked[i] == null)
				num++;
		if (num == 4)
			System.out.println("ZERO");
		if (num == 3) {
//			System.out.println("> " + c.pos.x + ", " + c.pos.y);
			Cell next = linked[0];
			int dir = 0;
			if (next == null) {
				next = linked[1];
				dir = 1;
			}
			if (next == null) {
				next = linked[2];
				dir = 2;
			}
			if (next == null) {
				next = linked[3];
				dir = 3;
			}
			int opp = getOpposite(dir);
			c.linkedNeighbours[dir] = null;
			next.linkedNeighbours[opp] = null;
			recursiveFill(next);
		}
	}
	
	public static int getOpposite(int s) {
		if (s > 3) {
			System.out.println("getOpposite too large");
		}
		switch(s) {
			case 0 : return 1;
			case 1 : return 0;
			case 2 : return 3;
			case 3 : return 2;
		}
		return -1; // Shouldn't get here
	}
	
	public int[] randomOrdering(int size) {
		/* Shuffle list of numbers */
		int[] nums = new int[size];
		// Fill with numbers
		for (int i = 0; i < size; i++) {
			nums[i] = i;
		}
		// Shuffle
		for (int i = 0; i < 4; i++) {
			int firstNum = r.nextInt(size);
			int secondNum = r.nextInt(size);
			// Swap
			int temp = nums[firstNum];
			nums[firstNum] = nums[secondNum];
			nums[secondNum] = temp;
		}
//		for (int i = 0; i < size; i++) {
//			System.out.println(">" + nums[i]);
//		}
		return nums;
	}
	
	public void recursiveMazeGeneration(Cell curCell) {
		curCell.setVisited();
		Cell[] curNeighbours = curCell.neighbours;
		int[] randomNums = randomOrdering(4);
		for (int i = 0; i < 4; i++) {
//			System.out.print(".");
			Cell c = curNeighbours[randomNums[i]];
			if (c == null) { // Edge
//				System.out.println("-");
				continue;
			}
			if (!c.getVisited()) {
				// Remove wall
				curCell.setLinkedNeighbour(randomNums[i], c);
				// Recursive call
				recursiveMazeGeneration(c);
			}
		}
	}
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public Cell generateMaze() {
		Cell exit = setupBlankMaze();
		recursiveMazeGeneration(exit);
		return exit;
	}
	
	/** Returns exit Cell */
	public Cell setupBlankMaze() {
		cells = new Cell[width][height];
		// Make all cells
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				cells[x][y] = new Cell(x, y);
			}
		}
		// Horizontal x axis join
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width - 1; x++) {
				cells[x][y].setCellNeighbour(3, cells[x + 1][y]);
			}
		}
		
		// Vertical y axis join
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height - 1; y++) {
				cells[x][y].setCellNeighbour(1, cells[x][y + 1]);
			}
		}
		return cells[0][0];
	}
	
	/** returns new Vec2int */
	public Vec2int gridCoordsToPos(Vec2int coords) {
//		Current box center
//		int xBox = startx + x*boxWidth + (boxWidth/2);
//		int yBox = starty + y*boxHeight + (boxHeight/2);
		return new Vec2int(	startx + coords.x*boxWidth + (boxWidth/2), 
							starty + coords.y*boxHeight + (boxHeight/2));
	}
	
	/** returns new Vec2int */
	public Vec2int posToGridCoords(Vec2int coords) {
//		Current box center              adjustment below
		int x = (-startx + (coords.x + (((boxWidth/2)))) - (boxWidth/2))/boxWidth;
		int y = (-starty + (coords.y + (((boxWidth/2)))) - (boxHeight/2))/boxHeight;
		x = Math.min(x, width - 1);
		x = Math.max(x, 0);
		y = Math.min(y, height - 1);
		y = Math.max(y, 0);
		return new Vec2int(x, y);
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.YELLOW);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Cell curCell = cells[x][y];
				
				// Current box center
				int xBox = startx + x*boxWidth + (boxWidth/2);
				int yBox = starty + y*boxHeight + (boxHeight/2);
				
//				g.drawRect(xBox - 5, yBox - 5, 10, 10);
				
				Cell[] linked = curCell.linkedNeighbours;
				
				/** 0 - up<br>
				 * 	1 - down<br>
				 *  2 - left<br>
				 *  3 - right<br> */
				
				if (linked[0] == null &&
						linked[1] == null &&
						linked[2] == null &&
						linked[3] == null) {
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
				} else {
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
				}
						
				
				if (linked[0] == null) { // UP
					g.drawLine(xBox - (boxWidth/2), yBox - (boxHeight/2), 
							xBox + (boxWidth/2), yBox - (boxHeight/2));
				}
				if (linked[1] == null) { // DOWN
					g.drawLine(xBox - (boxWidth/2), yBox + (boxHeight/2), 
							xBox + (boxWidth/2), yBox + (boxHeight/2));
				}
				if (linked[2] == null) { // LEFT
					g.drawLine(xBox - (boxWidth/2), yBox - (boxHeight/2), 
							xBox - (boxWidth/2), yBox + (boxHeight/2));
				}
				if (linked[3] == null) { // RIGHT
					g.drawLine(xBox + (boxWidth/2), yBox - (boxHeight/2), 
							xBox + (boxWidth/2), yBox + (boxHeight/2));
				}
			}
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
}
