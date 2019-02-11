import java.util.List;
import java.util.ArrayList;

public class Week {

	private ArrayList<Match> matches = new ArrayList<Match>();

	public Week() {
	}

	public Week( Match[] matches ) {
		for ( Match match : matches ) {
			this.matches.add( match );
		}
	}
	public Week(ArrayList<Match> matches) {
		this.matches = matches;
	}

	public ArrayList<Match> getAllMatches() {
		return matches;
	}

	public void add(Match match) {
		this.matches.add( match );
	}

	public void listAllTeamNames() {
		for (Match match : this.matches ) {
			System.out.println("Home Team: " + match.getHomeTeamName() + " @ " + match.getAwayTeamName());
		}
	}

	public void playGames() {
		for( Match match : this.matches ) {
			match.game();
		}
	}	
	public double totalErrorForOneWeekForMarginOfVictory() {
		//lower number = better;
		double totalError = 0;
		for (Match match : this.matches ) {
			totalError = totalError + match.differenceInActualAndPredictedForMarginOfVictory();
		}
		return totalError;
	}
	public double totalWinRateError() {
		//higher number = better
		double totalError = 0;
		for (Match match: this.matches ) {
			totalError = totalError + match.winRateDifference();
		}
		return totalError;
	}

	public int numberOfGamesInTheWeek() {
		return matches.size();
	}

	public void actualGameResults(double kValue) {
		for( Match match : this.matches ) {
			match.actualResults(kValue); 
		}
	}
}
