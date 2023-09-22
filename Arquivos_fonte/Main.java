import models.NbaPlayer;
import reader.PlayerReader;

class Main {
    public static void main(String[] args) throws Exception {
        //String linha = "0,Dennis Rodman,CHI,36.0,198.12,99.79024,Southeastern Oklahoma State,USA,1986,2,27,55,5.7,16.1,3.1,16.1,0.18600000000000003,0.32299999999999995,0.1,0.479,0.113,1996-97";

        //String[] info = linha.split(",");

        //for(int i = 0; i < info.length; i++){System.out.print(info[i] + "//");}

        //NbaPlayer player = new NbaPlayer(linha);
        //System.out.println(player.toString());

        PlayerReader.createDatabase();
    }
}
