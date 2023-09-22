package TP1;

//TODO criar nova pasta raiz para a ordenação
import java.io.*;

import models.NbaPlayer;
import reader.CRUD;

public class order {
    private int m; // number of registros
    private int n; // number of paths
    private static final String file_path = "../Database/player_db.db";

    public order() {

    }

    public order(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public void interlacaçãoComum() throws Exception{

        RandomAccessFile rafInput = new RandomAccessFile(file_path, "r");
        RandomAccessFile rafOutput;

        File[] files = new File[n];
        long[] filesPointers = new long[n];     //this array stores the position of the last register (is it necessary?)
        for(int i = 0; i < n; i++){files[i] = new File("../Database/inputFile"+ i);}

        int numRegister = 0;
        rafInput.seek(0);


        for(int j = 0; j < files.length; j++){
            rafOutput = new RandomAccessFile(files[j], "rw");

            for(int i = 0; rafInput.getFilePointer() < rafInput.length() && numRegister <= m; i++){            
                //realizar a leitura do arquivo database e jogar pro arquivo temporario

            }
    }

    rafInput.close();
}

}
