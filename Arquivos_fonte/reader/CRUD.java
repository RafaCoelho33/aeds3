package reader;

import java.io.*;
import models.NbaPlayer;

public class CRUD {
    private static final String file_path = "./Database/player_db.db";
    private static final char lapide = '&';
    private static final char lapideInvalida = '*';
    static private DataOutputStream dos;

    public CRUD() {
        try {
            FileOutputStream arq = new FileOutputStream(file_path);
            dos = new DataOutputStream(arq);
            dos.writeInt(0);
            dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public CRUD(boolean createDb){}

    // --------------- CREATE ---------------

    public boolean create(NbaPlayer player) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file_path, "rw");
        try {

            // id creation
            raf.seek(0);
            int lastId = raf.readInt();
            raf.seek(0);
            player.setId(lastId + 1);
            raf.writeInt(player.getId());

            // writing the new player
            byte[] array = player.toByteArray();
            raf.seek(raf.length());
            raf.writeChar(lapide);
            raf.writeInt(array.length);
            raf.write(array);

            raf.close();
            return true;

        } catch (Exception e) {
            System.out.println("-> Erro ao criar o registro! -> " + e);

            return false;
        }
    }

    // --------------- READ ---------------

    public NbaPlayer read(int searchId) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file_path, "rw")) {
            raf.seek(4);
            while (raf.getFilePointer() < raf.length()) {
                if (raf.readChar() == lapide) {
                    NbaPlayer player = new NbaPlayer();
                    int size = raf.readInt();
                    byte[] array = new byte[size];
                    raf.read(array);
                    player.fromByteArray(array);
                    if (player.getId() == searchId) {
                        return player;

                    }
                } else{
                    int size = raf.readInt();
                    raf.seek(raf.getFilePointer() + size);

                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        }

    }

    // --------------- UPDATE ---------------

    public boolean update(NbaPlayer newPlayer) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file_path, "rw")) {
            long pos = getPosInFile(newPlayer.getId());
            raf.seek(pos);
            int size = raf.readInt();
            raf.seek(pos);
            if ((newPlayer.toByteArray()).length <= size) {
                byte[] array = newPlayer.toByteArray();
                raf.writeInt(array.length);
                raf.write(array);
                return true;
            } else {
                // deleting the register from the database
                raf.seek(pos - 4);
                raf.writeChar(lapideInvalida);

                // getting the new id
                raf.seek(0);
                int lastId = raf.readInt();
                newPlayer.setId(lastId + 1);

                // writing the new register at the end of the file
                raf.seek(raf.length());
                byte[] array = newPlayer.toByteArray();
                raf.writeInt(array.length);
                raf.write(array);
                return true;

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    // ----------------DELETE-----------------------
    public boolean delete(int deleteId) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file_path, "rw")) {
            long pos = getPosInFile(deleteId);
            raf.seek(pos - 1);
            raf.writeChar(lapideInvalida);
            return true;
        }
    }

    // -----------------SEARCH-----------------------
    public static long getPosInFile(int i) throws Exception {
        long pos = 0;
        try (RandomAccessFile raf = new RandomAccessFile(file_path, "rw")) {
            raf.seek(4);
            while (raf.getFilePointer() < raf.length()) {
                if (raf.readChar() == lapide) {
                    pos = raf.getFilePointer();
                    NbaPlayer player = new NbaPlayer();
                    int size = raf.readInt();
                    byte[] array = new byte[size];
                    raf.read(array);
                    player.fromByteArray(array);
                    if (player.getId() == i) {
                        return pos;

                    }
                } else{
                    int size = raf.readInt();
                    raf.seek(raf.getFilePointer() + size);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        }

        return pos;
    }

    public static NbaPlayer readPlayerFromRaf(RandomAccessFile raf) throws Exception {
        if (raf.getFilePointer() < raf.length()) { 
            NbaPlayer player = new NbaPlayer();
            

            // Read and skip through records until a tombstone character is encountered
            while (raf.readChar() != lapide) {
                int size = raf.readInt();
                raf.skipBytes(size); 
            }

            int size = raf.readInt(); 
            byte[] array = new byte[size]; 

            raf.read(array); 
            player.fromByteArray(array); 

            return player; // Return the deserialized Film object
        }
        return null; // Return null if there is no more data to read
    }
}
