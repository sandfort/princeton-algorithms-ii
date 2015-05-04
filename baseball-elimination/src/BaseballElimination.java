/**
 * Create a baseball division from given filename in format specified below.
 */
public class BaseballElimination {
    /**
     * Create a baseball division from given filename in the format specified
     * below.
     *
     * The input format is the number of teams in the division <i>N</i> followed
     * by one line for each team. Each line contains the team name (with no
     * internal whitespace characters), the number of twins, the number of
     * losses, the number of remaining games, and the number of remaining games
     * against each team in the division.
     */
    public BaseballElimination(String filename) {
        // TODO implement
    }

    /**
     * The number of teams in the division.
     */
    public int numberOfTeams() {
        // TODO implement
        return 0;
    }

    /**
     * All teams in the division.
     */
    public Iterable<String> teams() {
        // TODO implement
        return null;
    }

    /**
     * Number of wins for the given team.
     */
    public int wins(String team) {
        // TODO implement
        return 0;
    }

    /**
     * Number of losses for the given team.
     */
    public int losses(String team) {
        // TODO implement
        return 0;
    }

    /**
     * Number of remaining games for the given team.
     */
    public int remaining(String team) {
        // TODO implement
        return 0;
    }

    /**
     * Number of remaining games between the two given teams.
     */
    public int against(String team1, String team2) {
        // TODO implement
        return 0;
    }

    /**
     * Is the given team eliminated?
     */
    public boolean isEliminated(String team) {
        // TODO implement
        return false;
    }

    /**
     * The subset <i>R</i> of teams that eliminates the given team;
     * <tt>null</tt> if not eliminated.
     */
    public Iterable<String> certificateOfElimination(String team) {
        // TODO implement
        return null;
    }

    /**
     * Read in a sports division from an input file and print out whether each
     * team is mathematically eliminated and a certificate of elimination for
     * each team that is eliminated.
     */
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the supset of R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}

