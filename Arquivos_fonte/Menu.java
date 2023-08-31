import java.io.*;
import java.util.*;

import models.NbaPlayer;

class Menu extends Crud {

    public static void main(String[] args) throws IOException {
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


           }
            
        }
    }

}