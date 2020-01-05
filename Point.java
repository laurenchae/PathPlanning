/*
 * Fall 2019
 * CP468 Group 4
 * A* Path Planning
 * 
 * Point class to set properties for each possible valid point 
 * (i.e. set heuristic costs, track the points predecessor point, etc)
 * 
 */

public class Point {
	// f = g + h
	public int f;
	public int g;
	public int h;
	Point parent;
	int[] position;
	
	Point(Point parent, int[] position){
		this.parent = parent;
		this.position = position;
		
		this.f = 0;
		this.g = 0;
		this.h = 0;
	}
	
	public void setF(int f) {
		this.f = f;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public void setH(int h) {
		this.h = h;
	}
	
	public int getF() {
		return f;
	}
	
	public int getG() {
		return g;
	}
	
	public int getH() {
		return h;
	}
	
	public int[] getPosition() {
		return position;
	}
	
	
}
