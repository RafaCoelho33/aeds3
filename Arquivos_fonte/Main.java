import reader.PlayerReader;
import externalStructures.invertedList.InvertedList;

class Main {
    public static void main(String[] args) throws Exception {
        
        PlayerReader.createDatabase();
        InvertedList list = new InvertedList();

        list.createList();

    }
}
