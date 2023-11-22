import java.io.*;
import java.util.*;

import externalStructures.ExtendableHash.ExtendableHash;
import externalStructures.invertedList.InvertedList;
import compression.Huffman.Huffman;
import compression.LZW.LZW;
import patternSearch.Boyer.Boyer;
import patternSearch.KMP.KMP;
import reader.*;
import models.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // Start parsing data from CSV
        CRUD crud = new CRUD();

        String dbPath = "./Database/player_db.db";

        File file = new File("./Database/player_db.db");
        if (!(file.exists() && file.length() > 100))
            PlayerReader.createDatabase();

        int option = 0; // Initialize the option variable

        try (Scanner sc = new Scanner(System.in)) {
            while (true) { // Start an infinite loop for the main menu
                // Display the main menu options
                System.out.println("Select an option:\n" +
                        "1- CRUD\n" +
                        "2- External Sorting\n" +
                        "3- Hash\n" +
                        "4- Inverted List\n" +
                        "5- B-Tree\n" +
                        "6- Compression\n" +
                        "7- Pattern Search\n" +
                        "8- Exit");

                option = Integer.parseInt(sc.nextLine()); // Read user input as an integer

                switch (option) {
                    case 1:
                        while (true) { // Start an inner loop for CRUD operations
                            // Display CRUD operation options
                            System.out.println("What would you like to do?:\n" +
                                    "1- Create\n" +
                                    "2- Read\n" +
                                    "3- Update\n" +
                                    "4- Delete\n" +
                                    "5- Exit");

                            option = Integer.parseInt(sc.nextLine()); // Read user input for CRUD operation

                            switch (option) {
                                case 1:
                                    // Create a player object and add it to the database
                                    NbaPlayer player = new NbaPlayer();
                                    System.out.println("Digite o nome do jogador");
                                    player.setPlayer_name(sc.nextLine());
                                    System.out.println("Digite o total de pontos do jogador, rebotes e assistencias");
                                    player.setStats(sc.nextLine());
                                    System.out.println("Digite a altura do jogador");
                                    player.setPlayer_height(Float.parseFloat(sc.nextLine()));
                                    System.out.println("Digite o time do jogador");
                                    player.setTeam_abbreviation(sc.nextLine());
                                    crud.create(player);
                                    break;
                                case 2:
                                    // Read a player from the database
                                    System.out.println("Digite um id de jogador");
                                    int id = Integer.parseInt(sc.nextLine());
                                    player = crud.read(id);
                                    System.out.println(player);

                                    break;
                                case 3:
                                    // Update a film in the database
                                    player = new NbaPlayer(1, "teste_player123", "PHX", "7.0,2.2,9.0", 198);
                                    crud.update(player);
                                    break;
                                case 4:
                                    // Delete a film from the database
                                    System.out.println("Digite o id a ser deletado:");
                                    int deleteId = Integer.parseInt(sc.nextLine());
                                    crud.delete(deleteId);
                                    break;
                                case 5:
                                    System.out.println("Exiting CRUD.");
                                    break;
                                default:
                                    System.out.println("Invalid option. Please select a valid option.");
                                    break;
                            }

                            if (option == 5) {
                                break; // Exit the inner CRUD loop
                            }
                        }
                        break;

                    case 2:
                        // Perform external merge sorting on the data
                        System.out.println("Not implemented yet");
                        break;

                    case 3:
                        // Initialize and perform operations on the Extendible Hash Table
                        ExtendableHash extendableHash = new ExtendableHash<>(2);
                        extendableHash.startHash();

                        System.out.println("Select an ID to search: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.println(extendableHash.get(id));
                        break;

                    case 4:
                        option = 1;
                        while (true) {
                            System.out.println("1- List 1\n2- List 2\n3- Exit");
                            option = Integer.parseInt(sc.nextLine());
                            if (option == 1) {
                                // Initialize and perform operations on Inverted List 1
                                InvertedList invertedList1 = new InvertedList();
                                NbaPlayer player = new NbaPlayer(0, "teste_player", "CHI", "7.0,2.2,9.0", 198);
                                invertedList1.createList();
                                System.out.println(invertedList1.searchValue(player));
                                invertedList1.insertPlayer(player);
                                System.out.println(invertedList1.searchValue(player));
                            } else if (option == 2) {
                                // Initialize and perform operations on Inverted List 2
                                InvertedList invertedList1 = new InvertedList();
                                NbaPlayer player = new NbaPlayer(0, "teste_player", "CHI", "7.0,2.2,9.0", 198);
                                invertedList1.createList();
                                invertedList1.insertPlayer(player);
                                System.out.println(invertedList1.searchValue(player));
                            } else {
                                break;
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Not implemented yet");
                        break;

                    case 6:
                        System.out.println(
                                "1- Huffman\n" +
                                        "2-LZW\n" +
                                        "3- Exit");
                        option = Integer.parseInt(sc.nextLine());

                        switch (option) {
                            case 1:
                                Huffman huffman = new Huffman(dbPath);
                                huffman.compress();

                                System.out.println(
                                        "1- Decompress" +
                                                "2- Exit");

                                option = Integer.parseInt(sc.nextLine());
                                switch (option) {
                                    case 1:
                                        huffman.decompress();
                                        break;

                                    default:
                                        break;
                                }

                                break;

                            case 2:
                                LZW lzw = new LZW(dbPath);
                                lzw.compress();

                                System.out.println(
                                        "1- Decode" +
                                                "2- Exit");
                                option = Integer.parseInt(sc.nextLine());
                                switch (option) {
                                    case 1:
                                        lzw.decompress();
                                        break;

                                    default:
                                        break;
                                }

                            default:
                                break;
                        }

                    case 7:
                        System.out.println(
                            "1- KMP" +
                            "2- BoyerMoore"+
                            "3- Exit"
                            );
                        option = Integer.parseInt(sc.nextLine());

                        switch (option) {
                            case 1:
                                KMP kmp = new KMP(null, null)
                                break;

                            case 2:
                                
                                break;
                        
                            default:
                                break;
                        }

                    case 8:
                        System.out.println("Exiting");
                        System.exit(0); // Exit the program
                        break;

                    default:
                        System.out.println("Invalid option. Please select a valid option.");
                        break;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}