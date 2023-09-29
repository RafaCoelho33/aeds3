package externalStructures.Hash;

import java.util.*;
import java.io.*;

import models.NbaPlayer;

public class Hash<K, V> {
    private List<Bucket<Integer, Long>> directory;
    private int directoryDepth;
    private int bucketSize;

    public Hash(int bucketSize) {
        this.directoryDepth = 0;
        this.bucketSize = bucketSize;
        this.directory = new ArrayList<>();
        for (int i = 0; i < bucketSize; i++) {
            this.directory.add(new Bucket<>()); // Inicializa os buckets como listas vazias
        }
    }

    public void startHash() throws Exception {
        RandomAccessFile raf = new RandomAccessFile("././Database/player_db.db", "r");
        raf.seek(4);
        while (raf.getFilePointer() < raf.length()) {
            char tombstone = raf.readChar();
            if (tombstone == '&') {
                long address = raf.getFilePointer();
                int size = raf.readInt();
                byte[] array = new byte[size];
                raf.read(array);
                NbaPlayer player = new NbaPlayer();
                player.fromByteArray(array);
                int key = player.getId();

                set(key, address);
            } else if (tombstone == '*') {
                int size = raf.readInt();
                raf.seek(raf.getFilePointer() + size);
            }
        }
        raf.close();
    }

    public void set(int key, Long value) {
        int bucketIndex = calculateBucketIndex(key);
        Bucket<Integer, Long> bucket = directory.get(bucketIndex);
        bucket.put(key, value);
        if (bucket.size() > bucketSize) { // checking if the memory has run out
            extendDirectory(bucketIndex); // expand the director increasing the depth and rehashing the bucket that blew
                                          // out the memory
        }
    }

    private void extendDirectory(int bucketIndex) {
        Bucket<Integer, Long> oldBucket = directory.get(bucketIndex);
        directory.get(bucketIndex).entries.clear();
        this.directoryDepth++;
        for (int i = directory.size(); i < (int) Math.pow(2, directoryDepth); i++) {
            this.directory.add(new Bucket<>());
        }
        for (Integer key : oldBucket.entries.keySet()) {
            Long value = oldBucket.get(key);
            set(key, value);
        }
    }

    public NbaPlayer get(int key) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("././Database/player_db.db", "r");

        int bucketIndex = calculateBucketIndex(key);
        Bucket<Integer, Long> bucket = directory.get(bucketIndex);

        for (Map.Entry<Integer, Long> entry : bucket.entries.entrySet()) {
            Integer tempKey = entry.getKey();
            if (tempKey.equals(key)) {
                raf.seek(entry.getValue());
                NbaPlayer player = createPlayer(raf);
                raf.close(); // Close the file here
                return player;
            }
        }

        raf.close(); // Close the file in case the film is not found
        return null; // Returns null if the film is not found
    }

    private int calculateBucketIndex(int key) {
        int bucketIndex = key * (int) Math.pow(2, directoryDepth - 1);
        return bucketIndex % directory.size();
    }

    private NbaPlayer createPlayer(RandomAccessFile raf) throws Exception
    {
        raf.seek(raf.getFilePointer() + 1);
        int size = raf.readInt();

        byte[] array = new byte[size];
        raf.read(array);
        NbaPlayer player = new NbaPlayer();
        player.fromByteArray(array);
        return player;
    }

}