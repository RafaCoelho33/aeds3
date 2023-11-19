package compression.Huffman;

public class Node implements Comparable<Node> {
    protected Node right;
    protected Node left;
    protected int frequency;
    protected byte information;

    public Node(byte information, int frequency) {
        this.information = information;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Node other) {
        return this.frequency - other.frequency;
    }

}
