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

    public static NbaPlayer readFile(int search) throws IOException {
        try {
            RandomAccessFile raf = new RandomAccessFile("./Database/player_db.db", "rw");
            NbaPlayer player = new NbaPlayer();
            raf.seek(4);
            long currentPosition = raf.getFilePointer();
            long endPosition = raf.length();
            int len;
            byte ba[];
            boolean flag = false;
            while (raf.getFilePointer() < raf.length()) {
                if (raf.readByte() == 0) { // Se a lápide não existe (0000)
                    len = raf.readInt(); // Leitura do tamanho (lenght) do registro
                    ba = new byte[len]; // Cria um vetor de bytes de acordo com o tamanho
                    raf.read(ba); // Lê o vetor de bytes
                    player.fromByteArray(ba); // Converte o array em bytes no objeto
                    if (player.getId() == search) { // Força a terminada da leitura do arquivo
                        currentPosition = endPosition; // Força a terminada da leitura do arquivo
                        return player;
                    } else { // Senão, continua a ler
                        currentPosition = raf.getFilePointer();
                    }
                } else { // Se a lapide existir, ele salta para o registro
                    len = raf.readInt();
                    long temp = raf.getFilePointer();
                    raf.seek(temp + len);
                    currentPosition = raf.getFilePointer();
                }
            }
            raf.close();
            if (flag) { // Se contem o registro, retorna o player
                return player;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(" -> Erro na leitura do registro!");
            return null;
        }
    }

    // --------------- UPDATE ---------------
}
