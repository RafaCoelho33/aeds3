package reader;

import java.io.*;
import models.NbaPlayer;

public class CRUD {
    private static final String file_path = "";
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

    public boolean create(NbaPlayer Nba) throws FileNotFoundException {
        RandomAccessFile raf = new RandomAccessFile("./Database/player_db.db", "rw");
        try {
            // processo de escrita do novo id
            raf.seek(0);
            int ultimoID = raf.readInt();
            int novoID = ultimoID + 1;
            Nba.setId(novoID);
            raf.seek(0);
            raf.writeInt(novoID);

            // criação de um novo registro
            raf.seek(raf.length());
            raf.writeUTF(";"); // ! trocar a lapide pra ;
            raf.writeInt(Nba.toByteArray().length); // escreve o tamanho do registro

            // uma ideia que eu nao fiz, ao inves de inserir 1 por 1 cria um que ja insere
            // todos os registros de uma vez (não sei fazer kkk)

            raf.writeInt(Nba.getId()); // escreve o id no array de bytes
            raf.writeUTF(Nba.getPlayer_name()); // escreve o nome no array de bytes
            raf.writeLong(Nba.getInsertion_date()); // escreve a date no array de bytes
            raf.writeUTF(Nba.getTeam_abbreviation()); // escreve o abbreviation no array de bytes
            raf.writeUTF(Nba.getStats());
            raf.writeFloat(Nba.getPlayer_height()); // escreve a height no array de bytes
            System.out.println(Nba);
            raf.close();
            return true;
        } catch (Exception e) {
            System.out.println("-> Erro ao criar o registro! -> " + e);
            return false;
        }
    }

    // --------------- READS ---------------

}
