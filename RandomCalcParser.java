import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.*;
import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

public class RandomCalcParser {


	public static ArrayList<Double> calculations( String filename) throws Exception {

		ArrayList<Double> results = new ArrayList<Double>();

		String fileName = filename;

		String line = null;

		FileReader fileReader = new FileReader(fileName);

       	BufferedReader bufferedReader = new BufferedReader(fileReader);

       	double homeTeamPoints = 0;
       	double awayTeamPoints = 0;
       	double marginOfVictory = 0;
       	double averageMOV = 141550/11862;
       	double variance = 0;
       	double standardDeviation = 0;
       	double totalGames = 0;

       	while ( (line = bufferedReader.readLine()) != null ) {
       		String[] parts = line.split(",");

       		if ( line.equalsIgnoreCase("newyear") != true && parts[5].equalsIgnoreCase("n") != true ) { 
       			if (parts[5].equalsIgnoreCase("@")) {
       				homeTeamPoints = homeTeamPoints + Double.parseDouble(parts[9]);
       				awayTeamPoints = awayTeamPoints + Double.parseDouble(parts[8]);
       				totalGames++;
       			}
       			else {
       				homeTeamPoints = homeTeamPoints + Double.parseDouble(parts[8]);
       				awayTeamPoints = awayTeamPoints + Double.parseDouble(parts[9]);
       				totalGames++;

       			} 
       			marginOfVictory = marginOfVictory + Double.parseDouble(parts[8]) - Double.parseDouble(parts[9]);
       			variance = variance + Math.pow( (Double.parseDouble(parts[8]) - Double.parseDouble(parts[9]) - averageMOV), 2);

       		}


       	}
       	variance = variance/11862;
       	standardDeviation = Math.sqrt(variance);
       	//the mean excluding SB's is 11.933 (MARGIN OF VICTORY)
       	results.add(homeTeamPoints);
       	results.add(awayTeamPoints);
       	results.add(marginOfVictory);
       	results.add(variance);
       	results.add(standardDeviation);
       	results.add(totalGames);

       	return results;

	}
}