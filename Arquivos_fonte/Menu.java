import java.io.*;
import java.util.*;

import externalStructures.BTree.BTree;
import models.NbaPlayer;
import reader.CRUD;

class Menu extends CRUD {

    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("./Database/player_db.db", "rw");
        Scanner sc = new Scanner(System.in);
        NbaPlayer NbaPlayer = new NbaPlayer();
        boolean flag = true;
        int opcao = 0;
        boolean loop = true;

        if (raf.length() == 0)
            raf.writeInt(0);

        raf.seek(0); // Volta para o inicio do arquivo

        while (loop) {
            System.out.println("|_________MENU_________|");
            System.out.println("|                      |");
            System.out.println("|0 - Sair              |");
            System.out.println("|1 - Create            |");
            System.out.println("|2 - Read              |");
            System.out.println("|3 - Update            |");
            System.out.println("|4 - Delete            |");
            System.out.println("|5 - Intercalar        |");
            System.out.println("|6 - Print All         |");
            System.out.println("|7 - Lista Invertida   |");
            System.out.println("|______________________|\n");
            System.out.print("-> ");
            do {
                try {
                    opcao = sc.nextInt();
                    if (opcao < 0 || opcao > 8)
                        System.out.println("Operação Inválida");
                } catch (Exception e) {
                    System.out.println("-> Digite um número! ");
                    sc.nextLine();
                    break;
                }

            } while (opcao < 0 || opcao > 8);

            switch (opcao) {
                // --------- CREATE ---------
                case 1:
                    NbaPlayer = new NbaPlayer();
                    System.out.println("\n____________CRIAR PLAYER____________");

                    System.out.print("-> Name: ");
                    String test;
                    sc.nextLine();
                    test = sc.nextLine();
                    NbaPlayer.setPlayer_name(test);

                    while (flag) { // para inserir a data

                    }

                    System.out.print("-> TeamAbbreviation: ");
                    String team_abbreviation;
                    sc.nextLine();
                    team_abbreviation = sc.nextLine();
                    NbaPlayer.setTeam_abbreviation(team_abbreviation);

                    System.out.print("-> Stats: ");
                    String stats;
                    stats = sc.nextLine();
                    NbaPlayer.setStats(stats);

                    System.out.print("-> Player Height: ");
                    Float player_height;
                    player_height = sc.nextFloat();
                    NbaPlayer.setPlayer_height(player_height);

                    if (create(NbaPlayer))
                        System.out.println("\n-> Player criado com sucesso!");
                    else
                        System.out.println("\n-> Erro ao criar o Player!");
                    break;

                // --------- READ ---------
                case 2:
                    System.out.println("\n____________BUSQUE ____________");
                    System.out.print("-> Escreva o id do Player a ser buscado: ");
                    int search = sc.nextInt();
                    sc.nextLine();
                    NbaPlayer = read(search);
                    if (NbaPlayer == null)
                        System.out.println("-> Filme não encontrado!");
                    else
                        System.out.println(NbaPlayer);
                    break;

                // --------- UPDATE ---------
                case 3:
                    System.out.println("\n____________ATUALIZAR Player____________");
                    System.out.println("-> Escreva o id do player a ser atualizado: ");
                    int searchId = sc.nextInt();
                    sc.nextLine();
                    System.out.println();
                    NbaPlayer = read(searchId);
                    if (NbaPlayer != null) {
                        NbaPlayer newPlayer = new NbaPlayer();
                        newPlayer.setId(searchId);
                        System.out.print("-> New Name: ");
                        newPlayer.setPlayer_name(sc.nextLine());
                        System.out.print("-> New Insertion Date: ");
                        newPlayer.setInsertion_date(sc.nextLong());
                        System.out.print("-> New Height: ");
                        newPlayer.setPlayer_height(sc.nextFloat());
                        System.out.print("-> New Stats: ");
                        newPlayer.setStats(sc.nextLine());
                        System.out.print("-> New Team Abbreviation: ");
                        newPlayer.setTeam_abbreviation(sc.nextLine());
                        update(newPlayer);
                        System.out.println("Player atualizado com sucesso");
                    } else {
                        System.out.println("Id não encontrado");
                    }

                    break;

                // --------- DELETE ---------
                case 4:
                    System.out.print("\n____________DELETAR PLAYER____________");
                    System.out.println("-> Escreva o id do player a ser deletado: ");
                    int deleteId = sc.nextInt();
                    if (delete(deleteId))
                        System.out.println("\n-> Player deletado com sucesso!");
                    else
                        System.out.println("\n-> Erro ao deletar player!");
                    break;

                // ------- INTERCALAÇÂO -------
                case 5:

                case 6:

                    // -------- LISTA INVERTIDA -------
                case 7:

                case 9:
                    System.out.println("____________ÁRVORE B+____________");
                    if (BTree.construirArvore(raf)) {
                        System.out.println("\n-> Árvore criada com sucesso!");
                    } else {
                        System.out.println("\n-> Erro ao criar árvore!");
                    }
                    break;

                // --------- SAIR ---------
                case 0:
                    System.out.println("-> Saindo...");
                    loop = false;
                    break;

            }

        }
        raf.close();
        sc.close();
    }

}