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

public class Parser {

    private static ArrayList<String> listOfAllTeamNameChanges = new ArrayList<String>();
    private static HashMap<String,String> hashMapForNameChanges = new HashMap();

    public static ArrayList<Year> loadRealTeamScores( String filename, ArrayList<TeamFootball> totalTeamsListed, HashMap<String,TeamFootball> hashMapOfAllTeams ) throws Exception {

        listOfAllTeamNameChanges.add("HoustonOilers");
        listOfAllTeamNameChanges.add("TennesseeOilers");
        listOfAllTeamNameChanges.add("St.LouisCardinals");
        listOfAllTeamNameChanges.add("PhoenixCardinals");
        listOfAllTeamNameChanges.add("BostonPatriots");
        listOfAllTeamNameChanges.add("St.LouisRams");
        listOfAllTeamNameChanges.add("LosAngelesRaiders");
        listOfAllTeamNameChanges.add("BaltimoreColts");

        hashMapForNameChanges.put("HoustonOilers", "TennesseeTitans");
        hashMapForNameChanges.put("TennesseeOilers", "TennesseeTitans");
        hashMapForNameChanges.put("St.LouisCardinals", "ArizonaCardinals");
        hashMapForNameChanges.put("PhoenixCardinals", "ArizonaCardinals");
        hashMapForNameChanges.put("BostonPatriots", "NewEnglandPatriots");
        hashMapForNameChanges.put("St.LouisRams", "LosAngelesRams");
        hashMapForNameChanges.put("LosAngelesRaiders", "OaklandRaiders");
        hashMapForNameChanges.put("BaltimoreColts", "IndianapolisColts");

		String fileName = filename;

		String line = null;

		FileReader fileReader = new FileReader(fileName);

       	BufferedReader bufferedReader = new BufferedReader(fileReader);

       	ArrayList<Match> weekBuilding = new ArrayList<Match>();

       	ArrayList<Week> regularSeasonWeekList = new ArrayList<Week>();

       	ArrayList<Week> postSeasonList = new ArrayList<Week>();

       	ArrayList<Week> currentYear = new ArrayList<Week>();

       	ArrayList<Year> completeHistoryOfAllYears = new ArrayList<Year>();

		int weekTracker = 1;

        String currentPlayOffWeek = null;

        boolean isThereANewPlayOffWeek = true;

        boolean firstPlayOffGame = true;

        

        while ( (line = bufferedReader.readLine()) != null ) {
            
        		if ( line.equalsIgnoreCase("newyear") ) {
                    if (weekBuilding.size() != 0 ) {
                        postSeasonList.add(singleWeek(weekBuilding));
                        if (regularSeasonWeekList.size() != 0) {
                            currentYear.addAll(regularSeasonWeekList);
                            regularSeasonWeekList = new ArrayList<Week>();
                        }
                        if (postSeasonList.size() != 0) {
                            currentYear.addAll(postSeasonList);
                            postSeasonList = new ArrayList<Week>();
                        }
                        weekBuilding = new ArrayList<Match>();
                    }

                    currentYear.addAll(postSeasonList);
                    postSeasonList = new ArrayList<Week>();
        			Year yearBuilder = new Year(currentYear);
        			completeHistoryOfAllYears.add(yearBuilder);
        			currentYear = new ArrayList<Week>();
                    firstPlayOffGame = true;

        		}

        		else {

        			String[] parts = line.split(",");

        			boolean isInt = true;

        			try {
						Integer.parseInt(parts[0]);
        			}
        			catch ( Exception exception ) {
        				isInt = false;
        			}

        			if ( isInt == true ) {
        				if (Integer.parseInt(parts[0]) <= weekTracker) {
        					weekBuilding.add( individualRegularMatch(parts, totalTeamsListed, hashMapOfAllTeams) );
                            
        				}

        				if (Integer.parseInt(parts[0]) >  weekTracker) {
        					regularSeasonWeekList.add(singleWeek(weekBuilding));
        					weekBuilding = new ArrayList<Match>();
        					weekBuilding.add ( individualRegularMatch(parts, totalTeamsListed, hashMapOfAllTeams) );
        					weekTracker++;
        				}
        			}

                    if ( isInt == false ) {

                        if (firstPlayOffGame == true) {
                            currentPlayOffWeek = parts[0];
                            firstPlayOffGame = false;
                        }

                        isThereANewPlayOffWeek = parts[0].equalsIgnoreCase(currentPlayOffWeek);

                    if( isThereANewPlayOffWeek == true ) {

                        if ( regularSeasonWeekList.size() != 0 ) {
                            weekTracker = 1;
                            regularSeasonWeekList.add(singleWeek(weekBuilding));
                            weekBuilding = new ArrayList<Match>();

                            currentYear.addAll(regularSeasonWeekList);
                            regularSeasonWeekList = new ArrayList<Week>();

                            weekBuilding.add( individualRegularMatch(parts, totalTeamsListed, hashMapOfAllTeams) );

                        }
                        else {
                            weekBuilding.add( individualRegularMatch(parts, totalTeamsListed, hashMapOfAllTeams) );
                        }

                    }

                    if (isThereANewPlayOffWeek == false ) {

                        currentPlayOffWeek = parts[0];

                        if ( parts[0].equalsIgnoreCase("SuperBowl") != true) {
                            postSeasonList.add(singleWeek(weekBuilding));
                            weekBuilding = new ArrayList<Match>();
                            weekBuilding.add( individualRegularMatch(parts, totalTeamsListed, hashMapOfAllTeams) );
                        }
                        if ( parts[0].equalsIgnoreCase("SuperBowl") == true ) {
                            postSeasonList.add(singleWeek(weekBuilding));
                            weekBuilding = new ArrayList<Match>();
                            weekBuilding.add( superBowlMatch(parts, totalTeamsListed, hashMapOfAllTeams) );
                            postSeasonList.add(singleWeek(weekBuilding));
                            weekBuilding = new ArrayList<Match>();


                        }
                        
                    }

                    }
        			
        		}
        	}

        return completeHistoryOfAllYears;
		
    }

    private static int homeTeamPoints( String[] parts) {
        return Integer.parseInt(parts[8]);
    }
    private static int awayTeamPoints( String[] parts) {
        return Integer.parseInt(parts[9]);
    }	

    private static Match superBowlMatch(String[] parts, ArrayList<TeamFootball> totalTeamsListed, HashMap<String,TeamFootball> hashMapOfAllTeams) {
    	String winningTeamString = new String(parts[4]);
    	String homeTeamString = new String(parts[4]);
    	String awayTeamString = new String(parts[6]);
    	TeamFootball homeTeam = null;
    	TeamFootball awayTeam = null;
    	TeamFootball winningTeam = null;
    	int marginOfVictory = (Integer.parseInt(parts[8]) - Integer.parseInt(parts[9])  );

    	for (TeamFootball teamFootball : totalTeamsListed) {
    		if ( winningTeamString.equalsIgnoreCase( teamFootball.getName() ) ) {
    			winningTeam = doesValidationNeedToHappen(teamFootball, hashMapOfAllTeams);
    		}
    		if ( homeTeamString.equalsIgnoreCase( teamFootball.getName() ) ) {
    			homeTeam = doesValidationNeedToHappen(teamFootball, hashMapOfAllTeams);
    		}
    		if ( awayTeamString.equalsIgnoreCase( teamFootball.getName() ) ) {
    			awayTeam = doesValidationNeedToHappen(teamFootball, hashMapOfAllTeams);
    		}

    	}

    	Match match = new Match(homeTeam, awayTeam, winningTeam, marginOfVictory, homeTeam.copy(), awayTeam.copy(), winningTeam); 
    	return match;

    }

    private static TeamFootball doesValidationNeedToHappen(TeamFootball team, HashMap<String,TeamFootball> hashMapOfAllTeams ) {
        for (int i = 0; i < listOfAllTeamNameChanges.size(); i++) {
            if ( listOfAllTeamNameChanges.get(i).equalsIgnoreCase(team.getName()) ) {
                return hashMapOfAllTeams.get( (hashMapForNameChanges.get( ( listOfAllTeamNameChanges.get(i) ) ) ) );
            }
        }
        return team;

    }
    private static Match individualRegularMatch(String[] parts, ArrayList<TeamFootball> totalTeamsListed, HashMap<String,TeamFootball> hashMapOfAllTeams) {
    	String winningTeamString = new String(parts[4]);
    	String homeTeamString = null;
    	String awayTeamString = null;
    	TeamFootball homeTeam = null;
    	TeamFootball awayTeam = null;
    	TeamFootball winningTeam = null;
    	int marginOfVictory = (Integer.parseInt(parts[8]) - Integer.parseInt(parts[9])  );

    	if ( parts[5].equalsIgnoreCase("@") ) {
    		homeTeamString = parts[6];
    		awayTeamString = parts[4];
    	}
    	else {
    		homeTeamString = parts[4];
    		awayTeamString = parts[6];
    	}

    	for (TeamFootball teamFootball : totalTeamsListed) {
    		if ( homeTeamString.equalsIgnoreCase( teamFootball.getName() ) ) {
    			homeTeam = doesValidationNeedToHappen(teamFootball, hashMapOfAllTeams);
    		}
    		if ( awayTeamString.equalsIgnoreCase( teamFootball.getName() ) ) {
    			awayTeam = doesValidationNeedToHappen(teamFootball, hashMapOfAllTeams);
    		}
    		if ( winningTeamString.equalsIgnoreCase( teamFootball.getName() ) ) {
    			winningTeam = doesValidationNeedToHappen(teamFootball, hashMapOfAllTeams);
    		}
    	}
    	Match match = new Match(homeTeam, awayTeam, winningTeam, marginOfVictory, homeTeam.copy(), awayTeam.copy());

    	return match;
    }
    private static Week singleWeek(ArrayList<Match> matches) {
    	Week weeks = new Week(matches);
    	return weeks;
    }

}