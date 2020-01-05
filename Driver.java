import java.io.*;
import java.util.*;

/*
 * Fall 2019
 * CP468 Group 4
 * A* Path Planning
 * 
 * Driver class to put together the program and format/print the output.
 * 
 */

public class Driver {
	Map map;
	int[][] tempMapArray;
	int[][] mapArray;
	int numRobots;
	int[][] robotPos = null;
	int[] rendezvous = new int[2];
	
	public Driver() {
		createStateSpace();
		
		System.out.println();
		System.out.println("========= A* Path Planning =========");
		System.out.println();
		System.out.println("Map of room:");
		
		// Print Map
		for (int i = 0; i < tempMapArray.length; i++) {
			for (int j = 0; j < tempMapArray[i].length; j++) {
				System.out.print(tempMapArray[i][j]);
			}
			System.out.println();
		}
		
//		System.out.println();
//		System.out.println();
//
//		for (int i = 0; i < mapArray.length; i++) {
//			for (int j = 0; j < mapArray[i].length; j++) {
//				System.out.print(mapArray[i][j]);
//			}
//			System.out.println();
//		}
		
		// Print details about the problem
		System.out.println();
		System.out.println("Number of robots: " + numRobots);
		for (int i = 0; i < numRobots; i++) {
			System.out.println("Starting position for robot #" + (i+1) + ": (" + robotPos[i][0] + ", " + robotPos[i][1] + ")");
		}		
		System.out.println("Rendezvous Point: (" + rendezvous[0] + ", " + rendezvous[1] + ")");
		
		System.out.println();
		System.out.println("Beginning the A* Algorithm .....");
		System.out.println();
		
		AStar aStar = new AStar(map);
				
		// Print the path for each robot
		for (int i = 1; i <= numRobots; i++) {
			System.out.println("Path for robot #" + i + ":");	
			aStar.AStarImplementation(i);
			System.out.println();
			System.out.println();
		}
	}

	@SuppressWarnings("resource")
	public void createStateSpace() {
		// Instantiate variables
		Integer rows = 0;
		Integer cols = 0;
		int robot = 0;
		
		// Get user input for which file to open
		System.out.println("Enter name of input file: ");
		Scanner keyboard = new Scanner(System.in);
		String filename = keyboard.nextLine();
		
		// Create a buffered reader
		BufferedReader reader;
		
		// Try to open the file
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			
			// Loop through file
			int i = 0;
			int arrayRow = 0;
			String[] temp;
			String[] tempArray;
			
			while (line != null) {
				
				// Get room dimensions
				if (i == 0) {
					temp = (line.split(" "));
					rows = Integer.valueOf(temp[0]);
					cols = Integer.valueOf(temp[1]);
				// Get number of robots
				} else if (i == 1) {
					temp = (line.split(" "));
					numRobots = Integer.valueOf(temp[0]);
					robotPos = new int[numRobots][2];
					
				// Get robot positions
				} else if ((i == 2) || (i < 2 + numRobots)) {
					temp = (line.split(" "));
					
					if(robotPos == null) {
						return;
					} else {
						robotPos[robot][0] = Integer.valueOf(temp[0]);
						robotPos[robot][1] = Integer.valueOf(temp[1]);
					}
					
					robot++;
				
				// Get rendezvous point
				} else if (i == 2 + numRobots) {
					temp = (line.split(" "));
					rendezvous[0] = Integer.valueOf(temp[0]);
					rendezvous[1] = Integer.valueOf(temp[1]);
		
				} 
				else { // put map into 2d array
					
					if (tempMapArray == null) {
						tempMapArray = new int[rows][cols];
					}
					
					tempArray = line.split("");
										
					for (int j = 0; j < tempArray.length; j++) {
						tempMapArray[arrayRow][j] = Integer.valueOf(tempArray[j]);
					}
					
					arrayRow++;
				}
				line = reader.readLine();
				i++;	
			}
					
			// take temparray and flip it to put into maparray (because the input file is formatted differently)
			mapArray = new int[rows][cols];
			int mapIndex = 0;
			
			for (int j = tempMapArray.length - 1; j >= 0 ; j--) {
				
				for (int k = 0; k < tempMapArray[j].length; k++) {
					mapArray[mapIndex][k] = tempMapArray[j][k];
				}
				mapIndex++;
			}
			
			map = new Map(rows, cols, numRobots, robotPos, rendezvous, mapArray);
			
		// Print error message if file doesn't open
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}