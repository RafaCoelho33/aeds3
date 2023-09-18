package models;

import java.io.*;
import java.util.Date;

public class NbaPlayer {
    protected short id;
    protected String player_name;
    protected String team_abbreviation;
    protected String stats;
    protected long insertion_date;
    protected float player_height;

    public NbaPlayer(short id, String player_name, String team_abbreviation, String stats, long insertion_date,
            float player_height, int size) {
        this.id = id;
        this.player_name = player_name;
        this.team_abbreviation = team_abbreviation;
        this.stats = stats;
        this.insertion_date = insertion_date;
        this.player_height = player_height;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
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

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
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

    @Override
    public String toString() {
        return "NbaPlayer [id=" + id + ", player_name=" + player_name + ", team_abbreviation=" + team_abbreviation
                + ", stats=" + stats + ", insertion_date=" + insertion_date + ", player_height=" + player_height + "]";
    }

    public NbaPlayer() {
    }

    public NbaPlayer(String line) {
        String[] info = line.split(",");

        Date today = new Date();

        this.id = Short.parseShort(info[0]);
        this.player_name = info[1];
        this.team_abbreviation = info[2];
        this.player_height = Float.parseFloat(info[4]);
        this.insertion_date = today.getTime();
        this.stats = this.setStats(info[12], info[13], info[14]); // 12,13,14

    }

    //method that creates the byte array with the object
    public byte[] toByteArray() throws Exception { 

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);

            dos.writeInt(this.id);
            dos.writeUTF(this.player_name);
            dos.writeUTF(this.team_abbreviation);
            dos.writeUTF(this.stats);
            dos.writeLong(this.insertion_date);
            dos.writeFloat(this.player_height);

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    //method that recieves the byte array and creates the object
    public void fromByteArray(byte[] array) throws Exception { 

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(array);
            DataInputStream dis = new DataInputStream(bais);

            this.id = dis.readShort();  
            this.player_name = dis.readUTF();
            this.team_abbreviation = dis.readUTF();
            this.stats = dis.readUTF();
            this.insertion_date = dis.readLong();
            this.player_height = dis.readFloat();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

   //method that creates the field "stats" 
    private String setStats(String pts, String reb, String ast) {
        String stats = pts + "," + reb + "," + ast;

        return stats;
    }

}