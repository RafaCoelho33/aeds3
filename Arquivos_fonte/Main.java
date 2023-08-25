import java.io.*;
import models.NbaPlayer;
import reader.PlayerReader;

class Main {
    public static void main(String[] args) throws Exception {

        String linha = "0,Dennis Rodman,CHI,36.0,198.12,99.79024,Southeastern Oklahoma State,USA,1986,2,27,55,5.7,16.1,3.1,16.1,0.18600000000000003,0.32299999999999995,0.1,0.479,0.113,1996-97";
        String path = "";

        PlayerReader reader = new PlayerReader(path);

        NbaPlayer player = new NbaPlayer(linha);
        byte[] array = player.toByteArray();
        reader.writeRegister(array);

        NbaPlayer player2 = new NbaPlayer();
        player2.fromByteArray(array);
        System.out.println(player2.toString());
        
    }
}
