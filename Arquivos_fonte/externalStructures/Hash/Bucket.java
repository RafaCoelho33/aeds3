package externalStructures.Hash;

import java.util.*;

public class Bucket<Key, Value> {
    public Map<Key, Value> entries;

    public Bucket() {
        entries = new HashMap<>();
    }

    public Value get(Key key) {
        return entries.get(key);
    }

    public void put(Key key, Value value) {
        entries.put(key, value);
    }

    public int size(){
        return entries.size();
    }

    // Other methods like removing entries can be added here.
}