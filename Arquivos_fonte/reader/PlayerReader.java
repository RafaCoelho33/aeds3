package reader;

import java.io.*;
import java.util.*;
import models.NbaPlayer;

public class PlayerReader {

    private static final String database_path = "./Database/player_db.db";
    // TODO verificar se o caminho esta correto
    private static CRUD crud = new CRUD();

    public PlayerReader() {}

    public static void createDatabase() throws Exception {
        String[] players = readFile();

        for (String line : players) {
            try {
                NbaPlayer player = new NbaPlayer(line);
                crud.create(player);

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    private static String[] readFile() throws Exception { // metodo que le o arquivo csv e cria

        // metodo similar ao create

        List<String> players = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(database_path));
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                players.add(line);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return players.toArray(new String[0]);
    }
}