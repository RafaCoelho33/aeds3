package reader;

import java.io.*;
import models.NbaPlayer;

public class CRUD {
    private static final String file_path = "./Database/player_db.db";
    private static final char lapide = '&';
    static private DataOutputStream dos;

    public CRUD() {
        try {
            FileOutputStream arq = new FileOutputStream(file_path);
            dos = new DataOutputStream(arq);
            dos.writeInt(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // --------------- CREATE ---------------

    public void create(NbaPlayer player) throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile("./Database/player_db.db", "rw");
        try {
            // id creation
            raf.seek(0);
            int lastId = raf.readInt();
            raf.seek(0);
            player.setId(lastId + 1);
            raf.seek(0);
            raf.writeInt(player.getId());

            // writing the new player
            byte[] array = player.toByteArray();
            raf.seek(raf.length());
            raf.writeChar(lapide);
            raf.writeInt(array.length);
            raf.write(array);

        } catch (Exception e) {
            System.out.println("-> Erro ao criar o registro! -> " + e);
        }

    }

    // --------------- READS ---------------

    
}
