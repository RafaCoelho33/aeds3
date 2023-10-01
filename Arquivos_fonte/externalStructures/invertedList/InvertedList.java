package externalStructures.invertedList;

import java.io.*;
import java.util.*;

import models.NbaPlayer;

public class invertedList {
    private Map<String, Map<Integer, Long>> index;
    private String database_path = "././Database/player_db.db";

    public invertedList() {
        index = new HashMap<>();
    }

    public void createList() throws Exception {
        RandomAccessFile raf = new RandomAccessFile(database_path, "r");
        raf.seek(4);
        while (raf.getFilePointer() < raf.length()) {
            char tombstone = raf.readChar();
            if (tombstone == '&') {
                long pos = raf.getFilePointer();
                int size = raf.readInt();
                byte[] array = new byte[size];
                raf.read(array);
                NbaPlayer player = new NbaPlayer();
                player.fromByteArray(array);
                insertPlayer(player.getTeam_abbreviation(), player.getId(), pos);

            }

            else if (tombstone == '*') {
                int size = raf.readInt();
                raf.seek(raf.getFilePointer() + size);

            }

        }
        raf.close();
    }

    // call this method to insert a new player
    public void insertPlayer(String outerKey, Integer innerKey, Long value) {
        insertPlayer(index, outerKey, innerKey, value);

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
    public long searchValue(String outerKey, int innerKey) {
        return (searchValue(index, outerKey, innerKey));
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
