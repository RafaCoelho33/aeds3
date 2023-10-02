package externalStructures.invertedList;

import java.io.*;
import java.util.*;

import models.NbaPlayer;
import reader.CRUD;

public class InvertedList {
    private Map<String, Map<Integer, Long>> index;
    private String database_path = "././Database/player_db.db";

    public InvertedList() {
        index = new HashMap<>();
        
    }

    public void createList() throws Exception {
        RandomAccessFile raf = new RandomAccessFile(database_path, "r");
        raf.seek(4);
        while (raf.getFilePointer() < raf.length()) {
            char tombstone = raf.readChar();
            if (tombstone == '&') {
                int size = raf.readInt();
                byte[] array = new byte[size];
                raf.read(array);
                NbaPlayer player = new NbaPlayer();
                player.fromByteArray(array);
                insertPlayer(player);

            }

            else if (tombstone == '*') {
                int size = raf.readInt();
                raf.seek(raf.getFilePointer() + size);

            }

        }
        raf.close();
    }

    // call this method to insert a new player
    public void insertPlayer(NbaPlayer player) throws Exception {   

        insertPlayer(index, player.getTeam_abbreviation(), player.getId(), CRUD.getFilePointer(player.getId()));

    }

    private static void insertPlayer(Map<String, Map<Integer, Long>> map, String outerKey, Integer innerKey,
            Long value) {

        // checks if the team name exists in the map
        if (!map.containsKey(outerKey)) {
            map.put(outerKey, new HashMap<>()); // if not, creates a new key associated with a new hash map
        }

        // the id and address is always insert, because there will always exist a outer
        // key for it
        map.get(outerKey).put(innerKey, value);

    }

    // call this method to search for the value
    public long searchValue(NbaPlayer player) {
        return (searchValue(index, player.getTeam_abbreviation(), player.getId()));
    }

    private static long searchValue(Map<String, Map<Integer, Long>> nestedMap, String outerKey, int innerKey) {
        if (nestedMap.containsKey(outerKey)) {
            Map<Integer, Long> innerMap = nestedMap.get(outerKey);

            if (innerMap.containsKey(innerKey)) {
                return innerMap.get(innerKey);
            }
        }
        return -1;
    }

}
