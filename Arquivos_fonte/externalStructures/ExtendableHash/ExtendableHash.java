package externalStructures.ExtendableHash;

import java.util.*;
import java.io.*;

import models.NbaPlayer;

public class ExtendableHash<K, V> {
    private List<HashMap<Integer, Long>> directory;
    private int globalDepth;
    private int bucketSize;
    private static final String database_path = "././Database/player_db.db";

    public ExtendableHash(int bucketSize) {
        this.globalDepth = 0;
        this.bucketSize = bucketSize;
        this.directory = new ArrayList<>();
        for (int i = 0; i < bucketSize; i++) {
            this.directory.add(new HashMap<>()); // Inicializa os buckets como listas vazias
        }
    }

    public void startHash() throws Exception {
        RandomAccessFile raf = new RandomAccessFile(database_path, "r");
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

                set(player.getId(), address);
            } else if (tombstone == '*') {
                int size = raf.readInt();
                raf.seek(raf.getFilePointer() + size);
            }
        }
        raf.close();
    }

    public void set(int key, Long address) {
        int bucketIndex = calculateBucketIndex(key);
        HashMap<Integer, Long> bucket = directory.get(bucketIndex);
        bucket.put(key, address);
        if (bucket.size() > bucketSize) {
            extendDirectory(bucketIndex);
        }
    }

    private int calculateBucketIndex(int key) {
        int bucketIndex = key * (int) Math.pow(2, globalDepth - 1); // Cálculo do índice do bucket
        return bucketIndex % directory.size(); // Retorna o índice ajustado para o tamanho da lista de buckets
    }

    private void extendDirectory(int bucketIndex) {
        HashMap<Integer, Long> oldBucket = directory.get(bucketIndex);
        HashMap<Integer, Long> newBucket = new HashMap<>();
        directory.add(newBucket);
        for (Map.Entry<Integer, Long> entry : oldBucket.entrySet()) {
            Integer key = entry.getKey();
            long value = entry.getValue();
            int newBucketIndex = calculateBucketIndex(key);
            if (bucketIndex != newBucketIndex) {
                newBucket.put(key, value);
                oldBucket.remove(key);
            }
        }
        if (globalDepth == Math.ceil(Math.log(directory.size()) / Math.log(2))) {
            resizeHashTable();
        }
    }

    private void resizeHashTable() {
        List<HashMap<Integer, Long>> oldDirectory = directory;
        globalDepth++;
        bucketSize *= 2;
        directory = new ArrayList<>();
        for (int i = 0; i < bucketSize; i++) {
            directory.add(new HashMap<Integer, Long>());
        }
        for (HashMap<Integer, Long> bucket : oldDirectory) {
            for (Map.Entry<Integer, Long> entry : bucket.entrySet()) {
                Integer key = entry.getKey();
                long value = entry.getValue();
                set(key, value);
            }
        }
    }

    public NbaPlayer get(int key) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(database_path, "r");

        int bucketIndex = calculateBucketIndex(key);
        HashMap<Integer, Long> bucket = directory.get(bucketIndex);

        for (Map.Entry<Integer, Long> entry : bucket.entrySet()) {
            Integer tempKey = entry.getKey();
            if (tempKey.equals(key)) { // Use equals for object comparison
                raf.seek(entry.getValue());
                NbaPlayer player = new NbaPlayer();
                int size = raf.readInt();
                byte[] array = new byte[size];
                raf.read(array);
                player.fromByteArray(array);

                raf.close(); // Close the file here
                return player;
            }
        }

        raf.close(); // Close the file in case the film is not found
        return null; // Returns null if the film is not found
    }

}