package reader;

import java.io.*;
import models.NbaPlayer;

public class PlayerReader {

    protected String path;

    public PlayerReader() {

    }

    public PlayerReader(String file_path) {
        this.path = file_path;

    }

    public void readAndWrite() throws Exception { // metodo que le o arquivo csv e cria

        //metodo similar ao create

        File file = new File(this.path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String linha = "";

        while ((linha = br.readLine()) != null) {
            NbaPlayer player = new NbaPlayer(linha);
            byte[] array = player.toByteArray();
            writeRegister(array);

        }

        br.close();

    }

    public void writeRegister(byte[] array) throws Exception {
        String path = "./Database/player_db.db";

        FileOutputStream arq;
        DataOutputStream dos;

        try {

            arq = new FileOutputStream(path);
            dos = new DataOutputStream(arq);

            dos.write(array);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public NbaPlayer readRegister(){
        NbaPlayer player = new NbaPlayer();
        


        return player;
    }

    public int getSize(NbaPlayer player) throws Exception {

        byte[] array = player.toByteArray();
        int size = array.length;

        return size;
    }

}
