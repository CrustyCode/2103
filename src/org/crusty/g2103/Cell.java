package org.crusty.g2103;

import org.crusty.g2103.towers.Tower;
import org.crusty.math.Vec2int;

public class Cell {

	Tower tower = null;
	
	protected boolean visited = false;
	public Vec2int pos;
	protected Cell[] neighbours;
	public Cell[] linkedNeighbours;
//	public Cell prev, next = null; // For path use
	
	public Cell(int x, int y) {
		neighbours = new Cell[4];
		linkedNeighbours = new Cell[4];
//		path = new Cell[4];
		for (int i = 0; i < 4; i++) {
			neighbours[i] = null;
			linkedNeighbours[i] = null;
		}
		pos = new Vec2int(x, y);
	}
	
	public void setVisited() {
		visited = true;
	}
	
	public boolean getVisited() {
		return visited;
	}
	
	/** is there space for a turret? */
	private boolean canPlaceTower() {
		if (tower != null)
			return false;
		int num = 0;
		for (int i = 0; i < 4; i++) {
			if (linkedNeighbours[i] == null)
				num++;
		}
		if (num == 4)
			return true;
		return false;
	}
	
	/** Returns false if cant place tower, otherwise true */
	public boolean placeTower(Tower t) {
		if (canPlaceTower()) {
			tower = t;
			tower.setCellPos(this);
			return true;
		}
		return false;
	}
	
//	/** 0 - up<br>
//	 * 	1 - down<br>
//	 *  2 - left<br>
//	 *  3 - right<br> */
//	public Cell getCellNeighbour(int num) {
//		return neighbours[num];
//	}
	
	/** 0 - up<br>
	 * 	1 - down<br>
	 *  2 - left<br>
	 *  3 - right<br> 
	 *  Sets both  	CELL -> num -> c
	 *  		 	c    -> opp -> CELL */
	public void setCellNeighbour(int num, Cell c) {
		neighbours[num] = c;
		c.neighbours[Maze.getOpposite(num)] = this;
//		neighbours[num] = c;
//		c.neighbours[getOpposite(num)] = this;
	}
	
	/** 0 - up<br>
	 * 	1 - down<br>
	 *  2 - left<br>
	 *  3 - right<br> 
	 *  Sets both  	CELL -> num -> c
	 *  		 	c    -> opp -> CELL */
	public void setLinkedNeighbour(int num, Cell c) {
//		System.out.println("Link");
		linkedNeighbours[num] = c;
//		path[num] = c;
		c.linkedNeighbours[Maze.getOpposite(num)] = this;
//		c.path[getOpposite(num)] = this;
	}

	public boolean hasNextSinglePath(Cell prevCell) {
		int walls = 0;
		for (int i = 0; i < 4; i++) {
			if (linkedNeighbours[i] == null || linkedNeighbours[i] == prevCell) {
//				System.out.println("wall: " + i);
				walls++;
			}
		}
//		System.out.println("Walls: " + walls);
		if (walls == 3)
			return true;
		return false;
	}

	public Cell getNextSinglePath(Cell prevCell) {
		int i = 0;
		Cell c = null;
		while (c == null || c == prevCell) {
			c = linkedNeighbours[i];
			i++;
		}
		return c;
	}
	
//	public void removeLinkedNeighbour(int num, Cell c) {
//		path[num] = null;
//		c.path[getOpposite(num)] = null;
//	}
	
//	public Vec2int getAdjCoords(Vec2int pos, int dir) {
//		Vec2int newPos = new Vec2int(0, 0);
//		
//		return newPos;
//	}

}
