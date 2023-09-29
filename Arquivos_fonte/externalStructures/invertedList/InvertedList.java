package externalStructures.invertedList;

import java.io.*;
import java.util.*;

import models.NbaPlayer;

public class InvertedList {
    private Map<String, List<Long>> index;
    private String database_path = "././Database/player_db.db";


    public InvertedList(){
        index = new HashMap<>();
    }

    public void createList()
    {
        RandomAccessFile raf = new RandomAccessFile(database_path, "r");
        

    }


    public void insertFilm(NbaPlayer player, Long address)
    {   



    }









}
