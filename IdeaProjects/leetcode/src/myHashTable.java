/**
 * Created by yiyangtan on 10/4/15.
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class myHashTable<K, V> {

    class Entry<K, V> {
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        K key;
        V value;
    }

    private int size = 1000;
    private ArrayList<LinkedList<Entry<K, V>>> ary = new ArrayList<>(size);

    public myHashTable(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            ary.add(i, null);
        }
    }
    /*
    public void put(K key, V value) {
        if (key == null)
            throw new NullPointerException("null of key is not allowed");
        if (ary.get(key.hashCode() % size) == null)
            ary.set(key.hashCode() % size, new LinkedList<Entry<K, V>>());
        ary.get(key.hashCode() % size).addFirst(new Entry<K, V>(key, value));
    }
    */
    public void put(K key, V value) {
        if (key == null)
            throw new NullPointerException("null of key is not allowed");
        int index = key.hashCode() % size;
        if (ary.get(index) == null){
            ary.set(index, new LinkedList<Entry<K, V>>());
            ary.get(index).add(new Entry<K, V>(key, value));
        }else {
            LinkedList<Entry<K, V>> list = ary.get(key.hashCode() % size);
            Iterator<Entry<K, V>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Entry<K, V> e = iterator.next();
                if (e.key.equals(key)) {
                    e.value = value;
                    return;
                }
            }
            list.add(new Entry<K, V>(key, value));
        }
    }

    public V get(K key) {
        if (key == null)
            throw new NullPointerException("null of key is not allowed");
        if (ary.get(key.hashCode() % size) == null)
            return null;
        else {
            LinkedList<Entry<K, V>> res = ary.get(key.hashCode() % size);
            Iterator<Entry<K, V>> iterator = res.iterator();
            while (iterator.hasNext()) {
                Entry<K, V> e = iterator.next();
                if (e.key.equals(key))
                    return e.value;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        myHashTable<String, String> m = new myHashTable<>(1000);
        m.put("abc", "eee");
        System.out.println("abc".hashCode());
        m.put("abc", "bbb");
        System.out.println("eee".hashCode());
        m.put("eee","ddd");
        System.out.println(m.get("eee"));
        System.out.println(m.get("abc"));
    }
}





