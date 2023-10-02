package order;

import java.io.*;

import models.NbaPlayer;

public class Order {
    private int m; // number of registers
    private int n; // number of paths
    private File[] files1 = new File[n];
    private File[] files2 = new File[n];
    private static final String database_path = "./Database/player_db.db";

    public Order() {
        this.m = 4200;
        this.n = 3;
    }

    public Order(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public void sort() throws Exception
    {
        distribution();
        intercalation();
    }

    private void distribution() throws Exception {

        RandomAccessFile rafInput = new RandomAccessFile(database_path, "r");
        NbaPlayer[] players = new NbaPlayer[m];

        for (int i = 0; i < n; i++) {
            files1[i] = new File("./Database/tmpFiles/tmpFile" + (i + 1) + ".db");
            files2[i] = new File("./Database/tmpFiles/tmpFile" + (i + 4) + ".db");
        }
        int circularCounter = 0;

        rafInput.seek(4);
        while (rafInput.getFilePointer() < rafInput.length()) {

            for (int i = 0; i < m;) {
                char tombstone = rafInput.readChar();
                if (tombstone == '&') {
                    int size = rafInput.readInt();
                    byte[] array = new byte[size];
                    rafInput.read(array);
                    NbaPlayer player = new NbaPlayer();
                    player.fromByteArray(array);
                    i++;

                    players[i] = player;
                } else if (tombstone == '*') {
                    int size = rafInput.readInt();
                    rafInput.seek(rafInput.getFilePointer() + size);
                }

            }
            quickSort(players, 0, players.length);
            writeArray(players, files1[circularCounter]);

            circularCounter = (++circularCounter) % n;
        }

        rafInput.close();
        intercalation();
    }

    private static void quickSort(NbaPlayer[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(NbaPlayer[] array, int low, int high) {
        NbaPlayer pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j].getPlayer_name().compareTo(pivot.getPlayer_name()) < 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private static void swap(NbaPlayer[] array, int i, int j) {
        NbaPlayer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void writeArray(NbaPlayer[] players, File file) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            for (int i = 0; i < players.length; i++) {
                byte[] array = players[i].toByteArray();
                int registerSize = array.length;

                raf.seek(raf.length());

                raf.writeInt(registerSize);
                raf.write(array);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    private void intercalation() throws Exception
    {
        int circularCounter = 0;


        RandomAccessFile rafInput;
        RandomAccessFile rafOutput;
        NbaPlayer menor;
        

        for(int i = 0; i < n; i++)
        {
            rafInput = new RandomAccessFile(files1[0], "r");
            int size = rafInput.readInt();
            byte[] array = new byte[size];
            rafInput.read(array);


        }


    }

}