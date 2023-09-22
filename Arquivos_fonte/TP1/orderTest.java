package TP1;

//TODO criar nova pasta raiz para a ordenação
import java.io.*;

public class orderTest {
    private int m; // number of registers
    private int n; // number of paths
    private static final String file_path = "../Database/player_db.db";

    public orderTest() {
        this.m = 4200;
        this.n = 3;
    }

    public orderTest(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public void distribution() throws Exception {

        RandomAccessFile rafInput = new RandomAccessFile(file_path, "r");
        RandomAccessFile rafOutput;

        File[] files = new File[n];
        for (int i = 0; i < n; i++) {files[i] = new File("./Database/tmpFile" + (i + 1) + ".db");}

        int circularCounter = 0;
        rafInput.seek(0);

        while (rafInput.getFilePointer() < rafInput.length()) {

            rafOutput = new RandomAccessFile(files[circularCounter], "rw");

            for (int i = 0; i <= m; i++) {
                char tombstone = rafInput.readChar();

                if (tombstone == '&') {
                    int size = rafInput.readInt();
                    byte[] array = new byte[size];
                    rafInput.read(array);

                    rafOutput.seek(rafOutput.length());
                    rafOutput.writeInt(size);
                    rafOutput.write(array);

                } else if(tombstone == '*'){
                    int size = rafInput.readInt();
                    rafInput.seek(rafInput.getFilePointer() + size);
                }

            }

            circularCounter = (++circularCounter) % n;
        }

        // TODO deletar os arquivos txt temporarios, deixando apenas o arquivo ordenado
        // final

        rafInput.close();
    }

}
