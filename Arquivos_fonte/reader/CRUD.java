package reader;

import java.io.*;
import models.NbaPlayer;

public class CRUD {
    private static final String file_path = "../Database/player_db.db";
    private static final char lapide = '&';
    private static final char lapideInvalida = '*';
    static private DataOutputStream dos;

    public CRUD() {
        try {
            FileOutputStream arq = new FileOutputStream(file_path);
            dos = new DataOutputStream(arq);
            dos.writeShort(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // --------------- CREATE ---------------

    public void create(NbaPlayer player) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(file_path, "rw");
        try {
            // id creation
            raf.seek(0);
            short lastId = raf.readShort();
            raf.seek(0);
            player.setId((short) (lastId + 1));
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

        raf.close();

    }

    // --------------- READS ---------------

    public NbaPlayer read(short searchId) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file_path, "r")) {
            try {
                raf.seek(4);
                long eof = raf.length();
                while (raf.getFilePointer() < eof) {
                    if (raf.readChar() == lapide) {
                        int size = raf.readInt();
                        byte[] array = new byte[size];
                        NbaPlayer player = new NbaPlayer();
                        player.fromByteArray(array);
                        if (player.getId() == searchId) {
                            return player;

                        } else {
                            raf.seek(raf.getFilePointer() + size);

                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

    }

    // --------------- UPDATE ---------------

    public boolean update(NbaPlayer newPlayer) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file_path, "rw")) {
            long pos = this.getFilePointer(newPlayer.getId());
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
                raf.seek(pos - 1);
                raf.writeChar(lapideInvalida);

                // getting the new id
                raf.seek(0);
                short lastId = raf.readShort();
                newPlayer.setId(lastId);

                // writing the new register at the end of the file
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
    public boolean delete(short deleteId) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file_path, "rw")) {
            long pos = getFilePointer(deleteId);
            raf.seek(pos - 1);
            raf.writeChar(lapideInvalida);
            return true;
        }
    }

    // -----------------SEARCH-----------------------
    public long getFilePointer(short searchId) throws Exception {
        long pos = 0;
        try (RandomAccessFile raf = new RandomAccessFile("../Database/", "r")) {
            try {
                raf.seek(4);
                long eof = raf.length();
                while (raf.getFilePointer() < eof) {
                    if (raf.readChar() == lapide) {
                        pos = raf.getFilePointer();
                        int size = raf.readInt();
                        byte[] array = new byte[size];
                        NbaPlayer player = new NbaPlayer();
                        player.fromByteArray(array);
                        if (player.getId() == searchId) {
                            return pos;

                        } else {
                            raf.seek(raf.getFilePointer() + size);

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        return pos;
    }
}
