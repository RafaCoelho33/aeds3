import java.io.*;

public class NbaPlayer {
    protected int id;
    protected String player_name;
    protected String team_abbreviation;
    protected String stats;
    protected long insertion_date;
    protected float player_height;
    protected int size;
    

    public NbaPlayer(int id, String player_name, String team_abbreviation, String stats, long insertion_date,
            float player_height, int size) {
        this.id = id;
        this.player_name = player_name;
        this.team_abbreviation = team_abbreviation;
        this.stats = stats;
        this.insertion_date = insertion_date;
        this.player_height = player_height;
        this.size = size;
    }

    public NbaPlayer(String line) {
        String[] info = line.split(",");

        this.id = Integer.parseInt(info[0]);
        this.player_name = info[1];
        this.team_abbreviation = info[2];
        this.player_height = Float.parseFloat(info[4]);
        this.stats = this.setStats(info[12], info[13], info[14]);   // 12,13,14
        this.size = this.setSize();                                  

    }

    public byte[] toByteArray() throws Exception { // cria o array de bytes com os dados
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeUTF(this.player_name);
        dos.writeUTF(this.team_abbreviation);
        dos.writeUTF(this.stats);
        dos.writeLong(this.insertion_date);
        dos.writeFloat(this.player_height);

        return baos.toByteArray();

    }

    private String setStats(String pts, String reb, String ast) {
        String stats = pts + "," + reb + "," + ast;

        return stats;
    }

    private int setSize(){
        int size = 0;

        

        return size;
    }   

    public void fromByteArray(byte[] array) throws Exception { // recebe o array de dados e instancia o objeto

        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.player_name = dis.readUTF();
        this.team_abbreviation = dis.readUTF();
        this.stats = dis.readUTF();
        this.insertion_date = dis.readLong();
        this.player_height = dis.readFloat();

    }

}