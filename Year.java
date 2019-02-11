import java.util.List;
import java.util.ArrayList;

public class Year {

	private ArrayList<Week> completeYear = new ArrayList<Week>();

	public Year(ArrayList<Week> completeYear ) {
		this.completeYear = completeYear;
	}
	public Year() {
	}

	public ArrayList<Week> getAllWeeks() {
		return completeYear;
	}

	public void printAllWeekMatches() {
		for (Week weeks : this.completeYear) {
			weeks.listAllTeamNames();
			System.out.println("--------NEW WEEK--------");
		}
	}
	public void addWeek(Week weeks) {
			this.completeYear.add( weeks );
	}

	public void simulateCompleteYear() {
		for (Week weeks : this.completeYear) {
			weeks.playGames();
		}
	}

	public int getNumberOfPlayOffGames() {
		int playOffCounter = 0;
		for (Week weeks : this.completeYear) {
			if (weeks.numberOfGamesInTheWeek() <= 4) {
				playOffCounter++;
			}
		}
		return playOffCounter;
	}

	public double getFindKValue(int currentWeek) {
		double kHelper = 0; //just for iterating thru the forloop changes = no affect
		double kStartValue = 50;
		int numberOfGamesForStartValue = 4;
		int lastYearStretchBoost = 0;
		int middleingGames = completeYear.size() - getNumberOfPlayOffGames() - lastYearStretchBoost;
		double kTesterForPlayOffs = 10;
		double kTesterForLastYearStretch = 0;
		int numberOfGamesTillPlayoffsAfterStartBoost =  middleingGames - numberOfGamesForStartValue;
		if (currentWeek < numberOfGamesForStartValue) {
			return kStartValue;
		}
		if (currentWeek >= numberOfGamesForStartValue &&  currentWeek < middleingGames) {
			kHelper = (kStartValue - kTesterForPlayOffs) / numberOfGamesTillPlayoffsAfterStartBoost;
			return kStartValue - (kHelper * (currentWeek - numberOfGamesForStartValue + 1));
		}
		if ( currentWeek >= completeYear.size() - getNumberOfPlayOffGames() - lastYearStretchBoost && currentWeek < completeYear.size() - getNumberOfPlayOffGames() ) {
			kHelper = (kTesterForLastYearStretch - kTesterForPlayOffs) / lastYearStretchBoost;
			return (kHelper * (currentWeek - middleingGames + 1)) + kTesterForPlayOffs; 
		} 
		if (currentWeek >=  completeYear.size() - getNumberOfPlayOffGames()) {
			return kTesterForPlayOffs;
		}
		return 0;
	}
	public void runRealResultsOfYear() {
		for (int i = 0; i < completeYear.size(); i++) {
			completeYear.get(i).actualGameResults(getFindKValue(i));
		}
	}
	public double runRealResultsOfYearWithErrorCalculatorMOV() {
		int weekToStartErrorCalculationsAt = 4;
		double totalError = 0;
		for (int i = 0; i < weekToStartErrorCalculationsAt; i++) {
			completeYear.get(i).actualGameResults(getFindKValue(i));
		}
		for (int i = weekToStartErrorCalculationsAt; i < completeYear.size(); i++) {
			totalError = totalError + completeYear.get(i).totalErrorForOneWeekForMarginOfVictory();
			completeYear.get(i).actualGameResults(getFindKValue(i));
		}
		return totalError;
	}
	public double runRealResultsOfYearWithErrorCalculatorWR() {
		int weekToStartErrorCalculationsAt = 4;
		double totalError = 0;
		for (int i = 0; i < weekToStartErrorCalculationsAt; i++) {
			completeYear.get(i).actualGameResults(getFindKValue(i));
		}
		for (int i = weekToStartErrorCalculationsAt; i < completeYear.size(); i++) {
			totalError = totalError + completeYear.get(i).totalWinRateError();
			completeYear.get(i).actualGameResults(getFindKValue(i));
		}
		return totalError;
	}
	public int numberOfGamesInYearFromStartWeek() {
		int startWeek = 4;
		int totalGames = 0;
		for (int i = startWeek; i < completeYear.size(); i++) {
			totalGames = totalGames + completeYear.get(i).numberOfGamesInTheWeek();
		}

		return totalGames;
	}

}