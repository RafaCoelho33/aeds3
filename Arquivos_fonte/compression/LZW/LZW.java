package compression.LZW;

import java.io.*;
import java.util.*;

public class LZW {
    private Map<Integer, List<Byte>> dictionary;
    private byte[] data;
    private static final String compressedLZWFile = "././compression/compressedFiles/LZWcompressed.db";
    private static final String decodedLZWFile = "././compression/decompressedFiles/LZWdecompressed.db";

    public LZW(String dbPath) throws Exception {
        RandomAccessFile raf = new RandomAccessFile(dbPath, "rw");
        byte[] data = new byte[(int) raf.length()];
        raf.read(data);

        createDictionaty(data);
        this.data = data;

        raf.close();
    }

    private void createDictionaty(byte[] input) {
        this.dictionary = new LinkedHashMap<>();
        int dictSize = 256;

        for (int i = 1; i < dictSize; i++) {
            List<Byte> list = new ArrayList<>();
            list.add((byte) i);
            this.dictionary.put(i, list);
        }
    }

    public void compress() throws IOException {
        List<Integer> result = new ArrayList<>();
        List<Byte> current = new ArrayList<>();

        int dictSize = 255;

        for (Byte b : data) {
            List<Byte> next = new ArrayList<>(current);
            next.add(b);
            if (contains(next)) {
                current = new ArrayList<>(next);

            } else {
                result.add(search(current));
                this.dictionary.put(++dictSize, next);
                current.clear();
                current.add(b);
            }
        }
        if (!(current.isEmpty())) {
            result.add(search(current));
        }
        createBinaryFile(result, compressedLZWFile);
        System.out.println("Compression completed");
    }

    public void decompress() throws IOException {
        List<Integer> integerList = readFile();
        System.out.println();
        List<Byte> byteArray = new ArrayList<>();

        for (Integer integer : integerList) {
            System.out.print(integer + " ");
            List<Byte> dictionaryEntry = this.dictionary.get(integer);

            // Check if the dictionary contains an entry for the given integer
            if (dictionaryEntry != null) {
                for (byte b : dictionaryEntry) {
                    byteArray.add(b);
                }
            } else {
                System.out.println("Entry not found in dictionary for integer: " + integer);
            }
        }

        byte[] bytes = new byte[byteArray.size()];
        for (int i = 0; i < byteArray.size(); i++) {
            bytes[i] = byteArray.get(i);
        }

        RandomAccessFile raf = new RandomAccessFile(decodedLZWFile, "rw");
        raf.write(bytes);
        raf.close();
    }

    public static void createBinaryFile(List<Integer> compressedData, String filePath) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath))) {
            for (int value : compressedData) {
                write(dos, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> readFile() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(compressedLZWFile, "rw")) {
            List<Integer> result = new ArrayList<>();
            while (true) {
                try {
                    result.add(raf.readInt());
                } catch (EOFException e) {

                    break;
                }
            }
            return result;
        } catch (IOException e) {

            throw e;
        }
    }

    private int search(List<Byte> array) {
        for (Map.Entry<Integer, List<Byte>> mapElement : dictionary.entrySet()) {
            if (mapElement.getValue().equals(array)) {
                return mapElement.getKey();
            }
        }
        return -1;
    }

    private boolean contains(List<Byte> byteArray) {
        for (List<Byte> list : dictionary.values()) {
            if (list.equals(byteArray)) {
                return true;
            }
        }
        return false;
    }

    private static void write(DataOutputStream dos, int value) throws IOException {
        do {
            int byteValue = value & 0x7F;
            value >>>= 7;
            if (value != 0) {
                byteValue |= 0x80;
            }
            dos.writeByte(byteValue);
        } while (value != 0);
    }

}