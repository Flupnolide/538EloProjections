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

public class Match {

	private TeamFootball homeTeam;
	private TeamFootball awayTeam;
	private TeamFootball realWinner;
	private int marginOfVictory;
	public TeamFootball resetHomeTeam;
	public TeamFootball resetAwayTeam;
	public TeamFootball superBowlWinner;
	private int homeTeamEloAdvantage = 70;

	public Match() {
	}
	public Match(TeamFootball homeTeam, TeamFootball awayTeam) {
		this( homeTeam, awayTeam, null, 0 );
	}

	public Match(TeamFootball homeTeam, TeamFootball awayTeam, TeamFootball realWinner, int marginOfVictory) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.realWinner = realWinner;
		this.marginOfVictory = marginOfVictory;
	}
	
	public Match(TeamFootball homeTeam, TeamFootball awayTeam, TeamFootball realWinner, int marginOfVictory, TeamFootball resetHomeTeam, TeamFootball resetAwayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.realWinner = realWinner;
		this.marginOfVictory = marginOfVictory;
		this.resetHomeTeam = resetHomeTeam;
		this.resetAwayTeam = resetAwayTeam;
		this.superBowlWinner = null;
	}

	public Match(TeamFootball homeTeam, TeamFootball awayTeam, TeamFootball realWinner, int marginOfVictory, TeamFootball resetHomeTeam, TeamFootball resetAwayTeam, TeamFootball superBowlWinner) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.realWinner = realWinner;
		this.marginOfVictory = marginOfVictory;
		this.resetHomeTeam = resetHomeTeam;
		this.resetAwayTeam = resetAwayTeam;
		this.superBowlWinner = superBowlWinner;
	}

	public double homeTeamsChanceOfWinning() {
	//home team is first team 100% of time
		double homeTeamElo = homeTeam.getElo() + homeTeamEloAdvantage;
		double awayTeamElo = awayTeam.getElo();
		double differenceInElo = Math.abs(homeTeamElo - awayTeamElo);
		double oddsHolder = 0;
		double oddsNumber = 1 / ( (Math.pow(10 , differenceInElo / 400)) + 1);

		if (homeTeamElo > awayTeamElo) {

			oddsHolder =  1 - oddsNumber;				
		}
		else {
			oddsHolder = oddsNumber;
		}

		oddsHolder = oddsHolder * 100;
		return oddsHolder;

	}

	public String getHomeTeamName() {
		return homeTeam.getName();
	}
	public String getAwayTeamName() {
		return awayTeam.getName();
	}
	public TeamFootball getSuperBowlWinner() {
		return superBowlWinner;
	}

	public double superBowlTeamsChanceOfWinning() {
	//home team is first team 100% of time
		double homeTeamElo = homeTeam.getElo();
		double awayTeamElo = awayTeam.getElo();
		double differenceInElo = Math.abs(homeTeamElo - awayTeamElo);
		double oddsHolder = 0;
		double oddsNumber = 1 / ( (Math.pow(10 , differenceInElo / 400)) + 1);

		if (homeTeamElo > awayTeamElo) {

			oddsHolder =  1 - oddsNumber;				
		}
		else {
			oddsHolder = oddsNumber;
		}

		oddsHolder = oddsHolder * 100;
		return oddsHolder;
	}

	public double awayTeamsChanceOfWinning() {
		//away team is 2nd team 100% of time
		double homeTeamOdds = this.homeTeamsChanceOfWinning();
		return 100 - homeTeamOdds;
	}

	public double marginOfVictoryPredictorForHomeTeam() {
		return (homeTeam.getElo() + homeTeamEloAdvantage - awayTeam.getElo()) / 30;
	}
	public double marginOfVictoryPredictorForAwayTeam() {
		return (awayTeam.getElo() - homeTeam.getElo() - homeTeamEloAdvantage) / 30;
	}

	public double differenceInActualAndPredictedForMarginOfVictory() {
		if (homeTeam == realWinner) {
			return Math.abs(marginOfVictory - marginOfVictoryPredictorForHomeTeam());
			
		}
		if (awayTeam == realWinner) {
			return Math.abs(marginOfVictory - marginOfVictoryPredictorForAwayTeam());
		}
		return 0;
	}

	public double winRateDifference() {
		//max Score per week is number of games played, so 16 usually
		if(homeTeam == realWinner) {
			return homeTeamsChanceOfWinning()/100;
		}
		if (awayTeam == realWinner) {
			return awayTeamsChanceOfWinning()/100;
		}

		return 0;

	}

	public double eloBoostForKnownResults(double chrisValue) {

		double kValue = chrisValue;
		double eloValues = 0;
		double chanceOfWinning = 0;

		if (awayTeam == realWinner) {
			eloValues = awayTeam.getElo()  - ( homeTeam.getElo() + homeTeamEloAdvantage );
			chanceOfWinning = homeTeamsChanceOfWinning();
		}
		if (homeTeam == realWinner && superBowlWinner == null) {
			eloValues = homeTeam.getElo() + homeTeamEloAdvantage - awayTeam.getElo();
			chanceOfWinning = awayTeamsChanceOfWinning();
		}
		if (superBowlWinner != null) {
			eloValues = awayTeam.getElo()  -  homeTeam.getElo();
			chanceOfWinning = superBowlTeamsChanceOfWinning();
		}

		double valueHolder =  Math.log(marginOfVictory + 1) * (2.2 / ((eloValues) * .001 + 2.2));
		valueHolder = valueHolder * chanceOfWinning * kValue / 100;
		return valueHolder;

	}

	public int homeTeamEloBoost(TeamFootball homeTeam, TeamFootball awayTeam) {
		//placeholding for now
		return 0;
	}

	public int awayTeamEloBoost(TeamFootball homeTeam, TeamFootball awayTeam) {
		// placeholding for now
		return 0;
	}

	public ArrayList<TeamFootball> game() {
		ArrayList<TeamFootball> endGameResults = new ArrayList<TeamFootball>();
		Random game = new Random();
		double percentageRoll = game.nextDouble() * 100.0;

		if (homeTeamsChanceOfWinning() > percentageRoll) {
			homeTeam.setWins(homeTeam.getWins() + 1);
			homeTeam.setElo(homeTeam.getElo() + homeTeamEloBoost(homeTeam, awayTeam));
		}
		else {
			awayTeam.setWins(awayTeam.getWins() + 1);
			awayTeam.setElo(awayTeam.getElo() + awayTeamEloBoost(homeTeam, awayTeam));
		}

		endGameResults.add(homeTeam);
		endGameResults.add(awayTeam);

		return endGameResults;

	}

	public ArrayList<TeamFootball> actualResults(double kValue) {
		ArrayList<TeamFootball> actualGameResults = new ArrayList<TeamFootball>();
		if (marginOfVictory == 0) {
			awayTeam.setTies(homeTeam.getTies() + 1);
			homeTeam.setTies(homeTeam.getTies() + 1);
			actualGameResults.add(homeTeam);
			actualGameResults.add(awayTeam);

			return actualGameResults;
		}
		if (realWinner == homeTeam) {
			homeTeam.setWins(homeTeam.getWins() + 1);
			awayTeam.setLoses(awayTeam.getLoses() + 1);
			double eloChanger = eloBoostForKnownResults(kValue);
			homeTeam.setElo(homeTeam.getElo() + eloChanger);
			awayTeam.setElo(awayTeam.getElo() - eloChanger); 
		}

		if (realWinner == awayTeam) {
			awayTeam.setWins(awayTeam.getWins() + 1);
			homeTeam.setLoses(homeTeam.getLoses() + 1);
			double eloChanger = eloBoostForKnownResults(kValue);
			awayTeam.setElo(awayTeam.getElo() + eloChanger);
			homeTeam.setElo(homeTeam.getElo() - eloChanger);
		}

		actualGameResults.add(homeTeam);
		actualGameResults.add(awayTeam);
		return actualGameResults;
	} 
}