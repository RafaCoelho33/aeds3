package externalStructures.invertedList;

import java.io.*;
import java.util.*;

import models.NbaPlayer;
import reader.CRUD;

public class InvertedList {
    private Map<String, List<Long>> index;
    private RandomAccessFile raf;

    public InvertedList() {
        index = new HashMap<>();
        try {
            raf = new RandomAccessFile("././Database/player_db.db", "r");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createList() throws Exception {
        raf.seek(4);
        while (raf.getFilePointer() < raf.length()) {
            long address = raf.getFilePointer();
            NbaPlayer player = CRUD.readPlayerFromRaf(raf);
            String team = player.getTeam_abbreviation();
            insertPlayer(team, address);
        }
    }

    public void insertPlayer(NbaPlayer player) throws Exception {
        CRUD crud = new CRUD(false);
        crud.create(player);
        long address = CRUD.getPosInFile(player.getId());
        String team = player.getTeam_abbreviation();
        insertPlayer(team, address);
    }

    public void insertPlayer(String type, long address) {
        if (!index.containsKey(type)) {
            index.put(type, new ArrayList<>());
        }
        index.get(type).add(address);
    }

    public boolean remove(NbaPlayer player) throws Exception {
        boolean resp = false;
        String team = player.getTeam_abbreviation();

        if (index.containsKey(team)) {
            long pos = CRUD.getPosInFile(player.getId());
            index.get(team).remove(pos);

            if (index.get(team).isEmpty()) {
                index.remove(team);
            }
            resp = true;
        }
        return resp;
    }

    public boolean searchValue(NbaPlayer player) throws Exception {
        long pos = CRUD.getPosInFile(player.getId());
        if (pos == 0) {
            return false;
        }
        if (index.containsKey(player.getTeam_abbreviation())) {
            return searchValue(player.getTeam_abbreviation(), pos);
        } else {
            return false;
        }
    }

    public boolean searchValue(String type, long pos) {
        return index.get(type).contains(pos);
    }

    public void printFullList() throws Exception {
        for (List<Long> longList : index.values()) {
            for (Long value : longList) {
                System.out.println(readPlayerFromPos(value));
            }
        }

    }

    private NbaPlayer readPlayerFromPos(long pos) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("././Database/player_db.db", "r");
        if (pos > raf.length()) {
            raf.close();
            return null;
        }
        raf.seek(pos);
        NbaPlayer player = new NbaPlayer();
        int size = raf.readInt();
        byte[] array = new byte[size];
        raf.read(array);
        player.fromByteArray(array);
        raf.close();
        return player;
    }
}