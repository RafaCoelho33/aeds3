package externalStructures.BTree;
import java.io.*;

public class BTree {

    private Node root;
    private int order;

    public BTree(int order) { // setar a ordem da arvore
        this.root = new Node(order, true);
        this.order = order;
    }

    public void insert(Key key) {
        Node current = root;

        // Se a raiz estiver cheia, particionar a raiz
        if (current.isFull()) {
            Node newRoot = new Node(order, false);
            newRoot.setChild(0, current);
            newRoot.splitChild(0, current);
            current = (key.compareTo(current.getKey(0)) < 0) ? current.getChild(0) : current.getChild(1);
        }

        current.insertNotFull(root, key);
    }

    public class Node {
        private Key keys[];
        private Node children[];
        private int length;
        private boolean isLeaf;
        private int order;
        private long position;

        public Node(int order, boolean isLeaf) {
            this.length = 0;
            this.isLeaf = isLeaf;
            this.keys = new Key[order];
            this.children = new Node[order];
            this.length = 0;

        }

        public long getChildPosition(int i) {
            return children[i].getPosition();
        }

        public long getPosition() {
            return position;
        }

        public void setPosition(long position) {
            this.position = position;
        }

        public boolean isFull() {
            return length == (2 * order - 1);
        }

        public Key getKey(int index) {
            return this.keys[index];
        }

        public void setKey(int index, Key key) {
            this.keys[index] = key;
        }

        public Node getChild(int index) {
            return this.children[index];
        }

        public void setChild(int index, Node child) {
            this.children[index] = child;
        }

        public int getOrder() {
            return this.order;
        }

        public int getLength() {
            return this.length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public boolean isLeaf() {
            return this.isLeaf;
        }

        private void insertNotFull(Node node, Key key) {
            int i = node.getLength() - 1;
            if (node.getLength() == 2 * order - 1) {
                // Divide o nó antes de continuar com a inserção
                splitChild(i, node);
                // Decide em qual dos novos nós a chave deve ser inserida
                if (key.compareTo(node.getKey(i)) > 0) {
                    i++;
                }
            }

            if (node.isLeaf()) {
                // Move todas as chaves maiores para frente
                while (i >= 0 && key.compareTo(node.getKey(i)) < 0) {
                    node.setKey(i + 1, node.getKey(i));
                    i--;
                }
                node.setKey(i + 1, key);
                node.setLength(node.getLength() + 1);
            } else {
                if (node.getLength() == 2 * order - 1) {
                    // Divide o nó antes de continuar com a inserção
                    splitChild(i, node);
                    // Decide em qual dos novos nós o filho deve ser inserido
                    if (key.compareTo(node.getKey(i)) > 0) {
                        i++;
                    }
                }
                // Encontra a posição para inserir a chave
                while (i >= 0 && key.compareTo(node.getKey(i)) < 0) {
                    i--;
                }
                int index = i + 1;
                Node child = node.getChild(index);
                // Divide o filho, se estiver cheio
                if (child.getLength() == 2 * order - 1) {
                    splitChild(index, child);
                    if (key.compareTo(node.getKey(i)) > 0) {
                        index++;
                        child = node.getChild(index);
                    }
                }
                insertNotFull(child, key);
            }
        }

        public void splitChild(int index, Node child) { // Divide o filho
            Node newChild = new Node(child.getOrder(), child.isLeaf());

            // Copia as chaves e filhos do nó a ser dividido para o novo nó
            for (int i = 0; i < this.getOrder() - 1; i++) {
                newChild.setKey(i, child.getKey(i + this.getOrder()));
            }

            if (!child.isLeaf()) {
                for (int i = 0; i < this.getOrder(); i++) {
                    newChild.setChild(i, child.getChild(i + this.getOrder()));
                }
            }
            // Atualiza o número de chaves do nó a ser dividido
            child.setLength(this.getOrder() - 1);
            // Desloca os filhos do nó atual para a direita para abrir espaço para o novo
            // filho
            for (int i = this.getLength(); i >= index + 1; i--) {
                this.setChild(i + 2, this.getChild(i));
            }
            // Insere o novo filho no lugar vago
            this.setChild(index + 1, newChild);
            // Desloca as chaves do nó atual para a direita para abrir espaço para a nova chave
            for (int i = this.getLength() - 1; i >= index; i--) {
                this.setKey(i + 1, this.getKey(i));
            }

            // Insere a nova chave no lugar vago
            this.setKey(index, child.getKey(this.getOrder() - 1));

            // Atualiza o número de chaves do nó atual
            this.setLength(this.getLength() + 1);
        }
    }

    public static boolean construirArvore(RandomAccessFile raf) throws IOException {
        BTree btree = new BTree(8);
        RandomAccessFile file = new RandomAccessFile("btree.bin", "rw");
        raf.seek(4);
        long currentPosition = raf.getFilePointer();
        long endPosition = raf.length();
        int len;
        while (currentPosition != endPosition) {
            if (raf.readByte() == 0) { // Se a lápide não existe (0000)
                len = raf.readInt(); // Leitura do length (length) do registro
                int id = raf.readInt();
                double pointer = raf.getFilePointer();
                raf.readUTF(); // Nome inteiro do Player
                raf.readLong(); // Date
                raf.readUTF(); // Stats
                raf.readFloat(); // Height
                raf.readUTF(); // Team Abbreviation
                btree.insert(new Key(id, pointer));
                file.writeInt(id);
                file.writeDouble(pointer);
                currentPosition = raf.getFilePointer();
            } else { // Se a lapide existir, ele pula o registro
                len = raf.readInt();
                long temp = raf.getFilePointer();
                raf.seek(temp + len);
                currentPosition = raf.getFilePointer();
            }
        }
        file.close();
        return true;
    }

}

class Key {
    private int value;
    double address;

    // Constructor
    public Key(int value, double address) {
        this.value = value;
        this.address = address;
    }

    public int getValue() {
        return this.value;
    }

    public int compareTo(Key other) {
        return this.value - other.getValue();
    }

}
