import java.util.ArrayList;
/*
 * Fall 2019
 * CP468 Group 4
 * A* Path Planning
 * 
 * AStar class in which the AStar algorithm is implemented. 
 * 
 */

public class AStar {
	Map map;
	
	public AStar(Map map) {
		this.map = map;
	}
	
	public ArrayList<Point> AStarImplementation(int robotNum) {
		int[][] mapArray = map.getMap();
		
		// Initalize lists to store open and visited points, and the solution
		ArrayList<Point> openPointsList = new ArrayList<Point>(); 
		ArrayList<Point> visitedPointsList = new ArrayList<Point>();
		ArrayList<Point> path = new ArrayList<Point>();
		
		// Array of all the possible directions that the algo can take from a point
		int[][] directions = { {0, -1}, {0,1}, {-1, 0}, {1,0}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
		
		// Assign the starting position and destination 
		int[] robotStartingPos = map.getStartingRobotPosition(robotNum);
		int[] tempStartingPos = {robotStartingPos[1], robotStartingPos[0]};
		
		int[] rendezPos = map.getRendezvousPosition();
		int[] tempDestinationPos = {rendezPos[1], rendezPos[0]};
		
		Point startingPos =  new Point(null, tempStartingPos);
		Point destinationPos = new Point(null, tempDestinationPos);
		
		// Add starting position to the open list 
		openPointsList.add(startingPos);
		
		// Loop through the openPointsList until we reach the end of possible points 
		while (openPointsList.size() > 0) {
			Point currentPoint;
			int i;
			int index = 0;
			
			// Get the current position 
			currentPoint = openPointsList.get(0);
			
			// Check if any other points in the list have a lower cost
			for (i = 0; i < openPointsList.size(); i++) {
				if (openPointsList.get(i).getF() < currentPoint.getF()) {
					currentPoint = openPointsList.get(i);
					index = i;
				}
			}
			
			// Remove the current out of open points and into visited
			openPointsList.remove(index);
			visitedPointsList.add(currentPoint);
			
			// Check if reached rendezvous point 
			int[] currentPos = currentPoint.getPosition();
			int[] goalPos = destinationPos.getPosition();
			
			if ((currentPos[0] == goalPos[0]) && (currentPos[1] == goalPos[1])) {
				Point current = currentPoint;
				
				while (current != null) {
					path.add(current);
					current = current.parent;
				}
				
				// total cost of the path (f = g + h)
				int cost = 0;
				
				// print path
				for (int k = path.size() - 1; k >= 0; k--) {
					int[] pointCoordinates = path.get(k).getPosition();
					int pointCost = path.get(k).getF();
					if (k == 0) {
						System.out.print("(" + pointCoordinates[1] + ", " + pointCoordinates[0] + ")");
					} else {
						System.out.print("(" + pointCoordinates[1] + ", " + pointCoordinates[0] + ")" + " -> ");
					}
					
					cost += pointCost;
				}
				
				System.out.println();
				System.out.println("Total Cost Of Path: " + cost);
				
				return path;
			}
			
			// If not at rendezvous point, go through and add possible routes
			ArrayList<Point> possiblePoints = new ArrayList<Point>();
			
			for (i = 0; i < directions.length; i++) {
				
				int[] currentPointCoordinates = currentPoint.getPosition();
				int x = currentPointCoordinates[0] + directions[i][0];
				int y = currentPointCoordinates[1] + directions[i][1];
				int[] pos = {x, y};
				Point possiblePoint =  new Point(currentPoint, pos);

				// check if new point is valid
				if ((x > (mapArray.length - 1)) || x < 0) {
					continue;
				}
				
				if (y > (mapArray[mapArray.length - 1].length - 1) || y < 0) {
					continue;
				}
				
				// check if there is an obstacle at the point
				if (mapArray[x][y] != 0) {
					continue;
				}
				
				// check if been visited before
				if (visitedPointsList.contains(possiblePoint)) {
					continue;
				}
								
				// if passes all of the above, make it a possible point
				possiblePoints.add(possiblePoint);
				
			}
			
			// Go through the possible routes and calculate heuristic costs
			for (Point possible : possiblePoints) {
				
				if (visitedPointsList.contains(possible)) {
					continue;
				}
				
				possible.setG(currentPoint.getG() + 1);
				possible.setH(Math.abs(possible.position[0] - destinationPos.position[0]) + Math.abs(possible.position[1] - destinationPos.position[1]));
				possible.setF(possible.getG() + possible.getH());
				
				
				if (openPointsList.contains(possible)) {
					int alreadyExists = openPointsList.indexOf(possible);
					
					if (possible.getG() > openPointsList.get(alreadyExists).getG()) {
						continue;
					}
				}
				
				openPointsList.add(possible);
			}
		}
		return path;
	}

}
