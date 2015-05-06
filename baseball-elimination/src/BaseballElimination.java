/**
 * Create a baseball division from given filename in format specified below.
 */
public class BaseballElimination {
    private int n;      // number of teams
    private int[] w;    // wins for each team
    private int[] l;    // losses for each team
    private int[] r;    // remaining games for each team
    private int[][] g;  // remaining games for each team against each other team
    private FordFulkerson[] results;
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
        this.results = new FordFulkerson[n];
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
        checkTeams(team);

        return w[teams.get(team)];
    }

    /**
     * Number of losses for the given team.
     */
    public int losses(String team) {
        checkTeams(team);

        return l[teams.get(team)];
    }

    /**
     * Number of remaining games for the given team.
     */
    public int remaining(String team) {
        checkTeams(team);

        return r[teams.get(team)];
    }

    /**
     * Number of remaining games between the two given teams.
     */
    public int against(String team1, String team2) {
        checkTeams(team1);
        checkTeams(team2);

        return g[teams.get(team1)][teams.get(team2)];
    }

    /**
     * Is the given team eliminated?
     */
    public boolean isEliminated(String team) {
        checkTeams(team);

        int x = teams.get(team);

        if (isTriviallyEliminated(x)) {
            return true;
        }

        int max = 0;
        for (Matchup matchup : matchups(x)) {
            int team1 = matchup.either();
            int team2 = matchup.other(team1);
            max += g[team1][team2];
        }

        return calculateMaxflow(team).value() < max;
    }

    /**
     * The subset <i>R</i> of teams that eliminates the given team;
     * <tt>null</tt> if not eliminated.
     */
    public Iterable<String> certificateOfElimination(String team) {
        if (!isEliminated(team)) {
            return null;
        }

        if (isTriviallyEliminated(teams.get(team))) {
            return trivialCert(team);
        }

        FordFulkerson ff = calculateMaxflow(team);

        Stack<String> cert = new Stack<String>();
        for (String other : teams.keys()) {
            if (ff.inCut(teams.get(other))) {
                cert.push(other);
            }
        }

        return cert;
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
                StdOut.print(team + " is eliminated by the subset of R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

    private FlowNetwork buildFlowNetwork(String teamName) {
        int team = teams.get(teamName);
        Queue<Matchup> matchups = matchups(team);
        int v = n + matchups.size() + 2;
        FlowNetwork net = new FlowNetwork(v);
        int t = v - 1;  // target node
        int s = t - 1;  // source node

        int c = w[team] + r[team];

        // Connect team nodes to sink
        for (int i = 0; i < n; ++i) {
            if (i != team) {
                net.addEdge(new FlowEdge(i, t, c - w[i]));
            }
        }

        int i = n;
        for (Matchup matchup : matchups) {
            int team1 = matchup.either();
            int team2 = matchup.other(team1);

            // Connect game node to team nodes
            net.addEdge(new FlowEdge(i, team1, Double.POSITIVE_INFINITY));
            net.addEdge(new FlowEdge(i, team2, Double.POSITIVE_INFINITY));

            // Connect source node to game nodes
            net.addEdge(new FlowEdge(s, i++, g[team1][team2]));
        }

        return net;
    }

    private Queue<Matchup> matchups(int team) {
        Queue<Matchup> matchups = new Queue<Matchup>();

        for (int i = 0; i < n; ++i) {
            if (i != team) {
                for (int j = i + 1; j < n; ++j) {
                    if (j != team && g[i][j] != 0) {
                        matchups.enqueue(new Matchup(i, j));
                    }
                }
            }
        }

        return matchups;
    }

    private FordFulkerson calculateMaxflow(String teamName) {
        int team = teams.get(teamName);

        if (results[team] == null) {
            FlowNetwork net = buildFlowNetwork(teamName);
            results[team] = new FordFulkerson(net, net.V()-2, net.V()-1);
        }

        return results[team];
    }

    private boolean isTriviallyEliminated(int x) {
        for (int i = 0; i < n; ++i) {
            if (i != x && w[x] + r[x] < w[i]) {
                return true;
            }
        }

        return false;
    }

    private Iterable<String> trivialCert(String team) {
        Stack<String> cert = new Stack<String>();

        int x = teams.get(team);
        for (String other : teams.keys()) {
            if (!other.equals(team) && w[x] + r[x] < w[teams.get(other)]) {
                cert.push(other);
                return cert;
            }
        }

        return null;
    }

    private void checkTeams(String team) {
        if (!teams.contains(team)) {
            throw new IllegalArgumentException(team + " is not a valid team.");
        }
    }

    private class Matchup {
        private int team1;
        private int team2;

        public Matchup(int team1, int team2) {
            if (team1 < 0 || team2 < 0 || team1 == team2) {
                throw new IllegalArgumentException();
            }

            this.team1 = team1;
            this.team2 = team2;
        }

        public int either() {
            return team1;
        }

        public int other(int one) {
            if (one == team1) {
                return team2;
            }

            return team1;
        }
    }
}

