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


public class NFLSimulator {

	private ArrayList <TeamFootball> totalTeamsListed;
	private HashMap<String,TeamFootball> teamHashMap;

	public NFLSimulator() {
		totalTeamsListed = new ArrayList<TeamFootball>();
		teamHashMap = new HashMap<String,TeamFootball>();
	}

	public void addTeam( TeamFootball team) {
		this.totalTeamsListed.add(team);
		this.teamHashMap.put( team.getName(), team );
	}

	public TeamFootball getTeam( String name ) {
		return this.teamHashMap.get( name.toUpperCase() );
	}

	public List<TeamFootball> getTotalTeamsListed() {
		return this.totalTeamsListed;
	}

		public void resetTeams(TeamFootball team) {
			team.setWins(0);

			team.setLoses(0);

			team.setElo( ( ( team.getElo() - 1505) / 2 ) + ( 1505 ) );

			team.setTies(0);

		}



	public static void main( String[] args) throws Exception {

		NFLSimulator nfl = new NFLSimulator();

		nfl.addTeam(new TeamFootball("DenverBroncos", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("SeattleSeahawks", 1300, 0, 0, 0));
		nfl.addTeam(new TeamFootball("CarolinaPanthers", 1300, 0, 0, 0));
		nfl.addTeam(new TeamFootball("KansasCityChiefs", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("ArizonaCardinals", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("NewEnglandPatriots", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("PittsburghSteelers", 1500, 0, 0, 0));//start
		nfl.addTeam(new TeamFootball("GreenBayPackers", 1500, 0, 0, 0));//start
		nfl.addTeam(new TeamFootball("CincinnatiBengals", 1300, 0, 0, 0));
		nfl.addTeam(new TeamFootball("MinnesotaVikings", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("NewYorkJets", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("BuffaloBills", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("HoustonTexans", 1300, 0, 0, 0));
		nfl.addTeam(new TeamFootball("DetroitLions", 1500, 0, 0, 0)); // start
		nfl.addTeam(new TeamFootball("WashingtonRedskins", 1500, 0, 0, 0));//start
		nfl.addTeam(new TeamFootball("PhiladelphiaEagles", 1500, 0, 0, 0));//start
		nfl.addTeam(new TeamFootball("AtlantaFalcons", 1500, 0, 0, 0));//start
		nfl.addTeam(new TeamFootball("IndianapolisColts", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("BaltimoreRavens", 1300, 0, 0, 0));
		nfl.addTeam(new TeamFootball("NewYorkGiants", 1500, 0, 0, 0));//start
		nfl.addTeam(new TeamFootball("NewOrleansSaints", 1300, 0, 0, 0));
		nfl.addTeam(new TeamFootball("DallasCowboys", 1500, 0, 0, 0)); // Start
		nfl.addTeam(new TeamFootball("ChicagoBears", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("MiamiDolphins", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("SanDiegoChargers", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("SanFrancisco49ers", 1500, 0, 0, 0)); // Start
		nfl.addTeam(new TeamFootball("TampaBayBuccaneers", 1300, 0, 0, 0));
		nfl.addTeam(new TeamFootball("ClevelandBrowns", 1500, 0, 0, 0)); //Start
		nfl.addTeam(new TeamFootball("JacksonvilleJaguars", 1300, 0, 0, 0));
		nfl.addTeam(new TeamFootball("TennesseeTitans", 1500, 0, 0, 0)); //start
		nfl.addTeam(new TeamFootball("LosAngelesRams", 1500, 0, 0, 0)); // start

		//----name changes
		nfl.addTeam(new TeamFootball("OaklandRaiders", 1500, 0, 0, 0)); //start	
		nfl.addTeam(new TeamFootball("TennesseeOilers", 1500, 0, 0, 0)); //tenesseetitans+
		nfl.addTeam(new TeamFootball("St.LouisCardinals", 1500, 0, 0, 0)); // phoneix cardinals+ start
		nfl.addTeam(new TeamFootball("St.LouisRams", 1300, 0, 0, 0)); // losangelesrams
		nfl.addTeam(new TeamFootball("BaltimoreColts", 1500, 0, 0, 0)); // indianapolis colts+ start
		nfl.addTeam(new TeamFootball("HoustonOilers", 1500, 0, 0, 0)); //tenesseoilers+ start
		nfl.addTeam(new TeamFootball("BostonPatriots", 1500, 0, 0, 0)); // newenglandpatriots+ start
		nfl.addTeam(new TeamFootball("LosAngelesRaiders", 1300, 0, 0, 0)); // -->Oaklandraiders+
		nfl.addTeam(new TeamFootball("PhoenixCardinals", 1300, 0, 0, 0)); //arizona cardinals+



		ArrayList<TeamFootball> originalTeamValuesForSorting = new ArrayList<TeamFootball>();


		ArrayList<TeamFootball> sortTeamsByElo = new ArrayList<TeamFootball>();
		TeamFootball currentBestTeam = null;

		double errorHolder = 0;
		double totalError = 0;
		int numberOfGames = 0;
		Year yearHolder = new Year();
		Week weekHolder = new Week();
		ArrayList<Week> listOfAllWeeksInAYear = new ArrayList<Week>();
		ArrayList<Match> listOfAllMatchesInTheSuperbowlWeek = new ArrayList<Match>();
		Match singleYearMatch = new Match();
		ArrayList<Year> testYears = Parser.loadRealTeamScores( "completenflhistory.csv", nfl.totalTeamsListed, nfl.teamHashMap );

	for(int i = 0; i < testYears.size(); i++) {

			if ( i >= testYears.size() - 1) {
				errorHolder = testYears.get(i).runRealResultsOfYearWithErrorCalculatorMOV();
				totalError = totalError + errorHolder;
				numberOfGames = testYears.get(i).numberOfGamesInYearFromStartWeek();

			}
			else {
				testYears.get(i).runRealResultsOfYear();
			}

			sortTeamsByElo = new ArrayList<TeamFootball>();
			originalTeamValuesForSorting.clear();
			originalTeamValuesForSorting.addAll( nfl.totalTeamsListed );

			while ( originalTeamValuesForSorting.size() != 0 ) {
				currentBestTeam = originalTeamValuesForSorting.get(0);
				for (int j = 0; j < originalTeamValuesForSorting.size(); j++) {
					if( currentBestTeam.getElo() < originalTeamValuesForSorting.get(j).getElo() ) {
						currentBestTeam = originalTeamValuesForSorting.get(j);
					}
				}
				sortTeamsByElo.add(currentBestTeam);
				originalTeamValuesForSorting.remove(currentBestTeam);
			}

			for ( int z = sortTeamsByElo.size() - 1; z >= 0; z-- ) {
				if ( ( sortTeamsByElo.get(z).getWins() + sortTeamsByElo.get(z).getLoses() ) < 1 ) {
					sortTeamsByElo.remove(sortTeamsByElo.get(z));
				}
			} 
			yearHolder = testYears.get(i);
			listOfAllWeeksInAYear = yearHolder.getAllWeeks();
			weekHolder = listOfAllWeeksInAYear.get(listOfAllWeeksInAYear.size() - 1);
			listOfAllMatchesInTheSuperbowlWeek = weekHolder.getAllMatches();
			singleYearMatch = listOfAllMatchesInTheSuperbowlWeek.get(listOfAllMatchesInTheSuperbowlWeek.size() - 1); 
			TeamFootball superBowlWinner = singleYearMatch.getSuperBowlWinner(); 

			if (superBowlWinner != null) {
				System.out.println("The Super Bowl Winner is : " + superBowlWinner.getName());
			}
			System.out.println("Super Bowl " + (i + 1)); 
			for (int k = 0; k < sortTeamsByElo.size(); k++) { 
				String ties = "";
				if (sortTeamsByElo.get(k).getTies() != 0) {
					ties = "-" + Integer.toString(sortTeamsByElo.get(k).getTies());
				}
				System.out.println(sortTeamsByElo.get(k).getName() + " : " + sortTeamsByElo.get(k).getWins() + "-" + sortTeamsByElo.get(k).getLoses() + ties + " : " + Math.round(sortTeamsByElo.get(k).getElo()) + " Elo ");

			}

			System.out.println("=============================  NEXT YEAR ======================================");

			for (TeamFootball team : nfl.totalTeamsListed ) {
				nfl.resetTeams(team);
			}
		}


	}

}





