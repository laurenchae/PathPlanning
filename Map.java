/*
 * Fall 2019
 * CP468 Group 4
 * A* Path Planning
 * 
 * Map class to hold the room of the map, as well as its properties
 * (number of robots, starting positions, rendezvous point)
 * 
 */
public class Map {
	private Integer rows;
	private Integer cols;
	private int numRobots;
	int[][] robotPositions;
	int[] rendezvous;
	int[][] mapArray;
	

	public Map(Integer rows, Integer cols, Integer numRobots, int[][] robotPositions, int[] rendezvous, int[][] mapArray) {
		this.rows = rows;
		this.cols = cols;
		this.robotPositions = robotPositions;
		this.rendezvous = rendezvous;
		this.mapArray = mapArray;
	}
	
	public int getRowDimension() {
		return rows;
	}
	
	public int getColumnsDimension() {
		return cols;
	}
	
	public int getNumberofRobots() {
		return numRobots;
	}
	
	public int[] getStartingRobotPosition(int numRobot) {
		int[] robotPosition = {robotPositions[numRobot-1][0], robotPositions[numRobot-1][1]};
		return robotPosition;
	}
	
	public int[] getRendezvousPosition() {
		return rendezvous;
	}
	
	public int[][] getMap(){
		return mapArray;
	}
		
}
