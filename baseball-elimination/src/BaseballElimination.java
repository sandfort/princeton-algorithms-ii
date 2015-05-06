/**
 * Create a baseball division from given filename in format specified below.
 */
public class BaseballElimination {
    private int n;      // number of teams
    private int[] w;    // wins for each team
    private int[] l;    // losses for each team
    private int[] r;    // remaining games for each team
    private int[][] g;  // remaining games for each team against each other team
    private SeparateChainingHashST<String, Integer> teams;

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
        In in = new In(filename);

        this.n = in.readInt();
        this.w = new int[n];
        this.l = new int[n];
        this.r = new int[n];
        this.g = new int[n][n];
        this.teams = new SeparateChainingHashST<String, Integer>(n);

        for (int i = 0; i < n; ++i) {
            String team = in.readString();
            teams.put(team, i);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < n; ++j) {
                g[i][j] = in.readInt();
            }
        }
    }

    /**
     * The number of teams in the division.
     */
    public int numberOfTeams() {
        return n;
    }

    /**
     * All teams in the division.
     */
    public Iterable<String> teams() {
        return teams.keys();
    }

    /**
     * Number of wins for the given team.
     */
    public int wins(String team) {
        return w[teams.get(team)];
    }

    /**
     * Number of losses for the given team.
     */
    public int losses(String team) {
        return l[teams.get(team)];
    }

    /**
     * Number of remaining games for the given team.
     */
    public int remaining(String team) {
        return r[teams.get(team)];
    }

    /**
     * Number of remaining games between the two given teams.
     */
    public int against(String team1, String team2) {
        return g[teams.get(team1)][teams.get(team2)];
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
        StdOut.printf("Number of teams = %d%n", division.numberOfTeams());
        for (String team : division.teams()) {
            StdOut.printf("%s: %d wins, %d losses, %d remaining%n",
                    team,
                    division.wins(team),
                    division.losses(team),
                    division.remaining(team));
        }
        /*
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
        */
    }

    private FlowNetwork buildFlowNetwork(String teamName) {
        int team = teams.get(teamName);
        int v = numberOfTeams() + numberOfMatchups(team) + 1;
        FlowNetwork net = new FlowNetwork(v);
        int t = v - 1; // target node
        int s = t - 1;        // source node

        int c = w[team] + r[team];

        // Connect team nodes to sink
        for (int i = 0; i < n; ++i) {
            if (i != team) {
                net.addEdge(new FlowEdge(i, t, c - w[i]));
            }
        }

        // TODO implement
        return null;
    }

    private int numberOfMatchups(int team) {
        int m = 0;
        for (int i = 0; i < n; ++i) {
            if (i != team) {
                for (int j = i + 1; j < n; ++j) {
                    if (j != team && g[i][j] != 0) {
                        ++m;
                    }
                }
            }
        }

        return m;
    }
}

