public class NbaPlayer {
    protected int id;
    protected String player_name;
    protected String team_abbreviation;
    protected String stats;
    protected long insertion_date;
    protected float player_height;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getTeam_abbreviation() {
        return team_abbreviation;
    }

    public void setTeam_abbreviation(String team_abbreviation) {
        this.team_abbreviation = team_abbreviation;
    }

    public String getstats() {
        return stats;
    }

    public void setstats(String stats) {
        this.stats = stats;
    }

    public long getInsertion_date() {
        return insertion_date;
    }

    public void setInsertion_date(long insertion_date) {
        this.insertion_date = insertion_date;
    }

    public float getPlayer_height() {
        return player_height;
    }

    public void setPlayer_height(float player_height) {
        this.player_height = player_height;
    }

    public NbaPlayer(int id, String player_name, String team_abbreviation, String stats, long insertion_date,
            float player_height) {
        this.id = id;
        this.player_name = player_name;
        this.team_abbreviation = team_abbreviation;
        this.stats = stats;
        this.insertion_date = insertion_date;
        this.player_height = player_height;
    }

    public String createRegister(String line) {
        String[] info = line.split(",");

        this.id = Integer.parseInt(info[0]);
        this.player_name = info[1];
        this.team_abbreviation = info[2];
        this.player_height = Float.parseFloat(info[4]);
        this.stats = this.setStats(info[12], info[13], info[14]); // 12,13,14

        return null;
    }

    public String setStats(String pts, String reb, String ast) {
        String stats = pts + "," + reb + "," + ast;;

        return stats;
    }

    public void fromByteArray(byte[] array) { // recebe o array de dados e instancia o objeto conforme esses dados

    }

}