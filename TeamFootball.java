
public class TeamFootball {

	private String name;
	private double elo;
	private int wins;
	private int loses;
	private int ties;
	private int conferenceWins;
	private int divisionWins;
	private String divisionName;
	private String conferenceName;

	public TeamFootball(String name, double elo, int wins, int loses, int ties) {
		this.name = name;
		this.elo = elo;
		this.wins = wins;
		this.loses = loses;
		this.ties = ties;
	}
	public TeamFootball(String name, double elo, int wins, int loses, int ties, String divisionName, String conferenceName, int divisionWins, int conferenceWins) {
		this.name = name;
		this.elo = elo;
		this.wins = wins;
		this.loses = loses;
		this.ties = ties;
		this.divisionName = divisionName;
		this.divisionWins = divisionWins;
		this.conferenceWins = conferenceWins;
	}

	public TeamFootball copy() {
		return new TeamFootball( this.name, this.elo, this.wins, this.loses, this.ties );
	}

	public String getName() {
		return this.name;
	}

	public double getElo() {
		return this.elo;
	}
	public int getWins() {
		return this.wins;
	}
	public int getLoses() {
		return this.loses;
	}

	public int getTies() {
		return this.ties;
	}

	public void setWins( int wins ) {
		this.wins = wins;
	}
	public void setElo(double elo) {
		this.elo = elo;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLoses(int loses) {
		this.loses = loses;
	}
	public void setTies(int ties) {
		this.ties = ties;
	}
}