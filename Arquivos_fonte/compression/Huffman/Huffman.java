package compression.Huffman;

import java.io.RandomAccessFile;
import java.util.*;

public class Huffman {
    private Map<Byte, String> huffmanCodes;
    private Map<Byte, Integer> frequencyMap;
    private Node root;
    private byte[] data;
    private byte[] decompressedBytes;
    private byte[] compressedBytes;

    public Huffman(String db_path) throws Exception {
        
        RandomAccessFile raf = new RandomAccessFile(db_path, "r");
        byte [] inputData = new byte[(int) raf.length()];
        raf.read(inputData);

        this.data = inputData;
        this.frequencyMap = new HashMap<>();
        for (byte b : this.data) {
            frequencyMap.put(b, frequencyMap.getOrDefault(b, 0) + 1);
        }
        this.root = createHuffmanTree(frequencyMap);
        this.huffmanCodes = createHuffmanCodes(root);

        raf.close();

    }

    public void compress() {
        StringBuilder compressedData = new StringBuilder();
        for (byte b : this.data) {
            compressedData.append(huffmanCodes.get(b));
        }
        this.compressedBytes = new byte[(compressedData.length() + 7) / 8];
        int currentIndex = 0;
        for (int i = 0; i < compressedData.length(); i += 8) {
            String byteString = compressedData.substring(i, Math.min(i + 8, compressedData.length()));
            this.compressedBytes[currentIndex++] = (byte) Integer.parseInt(byteString, 2);
        }
    }

    public Node createHuffmanTree(Map<Byte, Integer> frequencyMap) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Byte, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (priorityQueue.size() > 1) {
            Node leftNode = priorityQueue.poll();
            Node rightNode = priorityQueue.poll();
            Node parent = new Node((byte) 0, leftNode.frequency + rightNode.frequency);
            parent.left = leftNode;
            parent.right = rightNode;
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }

    public Map<Byte, String> createHuffmanCodes(Node root) {
        huffmanCodes = new HashMap<>();
        createHuffmanCodes(root, "", huffmanCodes);
        return huffmanCodes;

    }

    public void createHuffmanCodes(Node node, String code, Map<Byte, String> huffmanCodes) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.information, code);
        }
        createHuffmanCodes(node.left, code.concat("0"), huffmanCodes);
        createHuffmanCodes(node.right, code.concat("1"), huffmanCodes);

    }

    public void decompress(byte[] compressedData) {
        StringBuilder stringCompressedData = new StringBuilder();
        this.decompressedBytes = new byte[this.data.length + 5];

        for (byte b : compressedData) {
            String byteString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            stringCompressedData.append(byteString);
        }

        String stringCompressed = stringCompressedData.toString();
        Node current = root;

        int counter = 0;
        for (int i = 0; i < stringCompressedData.length(); i++) {
            if (stringCompressed.charAt(i) == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
            if (current.left == null && current.right == null) {
                this.decompressedBytes[counter] = current.information;
                counter += 1;
                current = root;
            }

        }
        System.out.println("Successfully Unzip file.");
    }

    public byte[] getCompressedBytes(){
        return this.compressedBytes;
    }
    public byte[] getDecompressedBytes(){
        return this.decompressedBytes;
    }
    public byte[] getInputData(){
        return this.data;
    }
}