package hw04;

import java.util.*;
import java.util.stream.Collectors;

public class CustomHashMap<K, V> implements Map<K, V> {
    private int size;
    private int capacity;
    private final float loadFactor = 0.75f;
    private final int initSize = 16;
    private Node<K, V>[] table;

    static class Node<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V val) {
            V previousValue = value;
            value = val;
            return previousValue;
        }
    }

    public CustomHashMap() {
        clear();
    }

    private int getIndexByKey(K key) {
        int hash = key.hashCode();
        return hash % capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < capacity; i++) {
            Node<K, V> aNode = table[i];
            while (aNode != null) {
                if (Objects.equals(aNode.value, value)) {
                    return true;
                }
                aNode = aNode.next;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = getIndexByKey((K) key);
        Node<K, V> foundNode = table[index];
        for (int i = 0; ; i++) {
            if (foundNode == null) {
                return null;
            }
            if (Objects.equals(foundNode.key, key)) {
                return foundNode.value;
            }
            if (foundNode.next == null) {
                return null;
            }
            foundNode = foundNode.next;
        }
    }

    private void putIntoTable(K key, V value, Node<K, V>[] table) {
        int index = getIndexByKey(key);
        Node<K, V> newNode = new Node<>(key, value, null);
        Node<K, V> collisionNode = table[index];
        if (collisionNode == null) {
            table[index] = newNode;
        } else {
            for (int i = 0; ; i++) {
                if (Objects.equals(collisionNode.key, key)) {
                    collisionNode.value = value;
                    break;
                }
                if (collisionNode.next == null) {
                    collisionNode.next = newNode;
                    break;
                }
                collisionNode = collisionNode.next;
            }
        }
    }

    private void resize() {
        Node<K, V>[] oldTab = table;
        capacity *= 2;
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[capacity];
        for (Node<K, V> kvNode : oldTab) {
            Node<K, V> e = kvNode;
            while (e != null) {
                putIntoTable(e.key, e.value, newTab);
                e = e.next;
            }
        }
        table = newTab;
    }

    @Override
    public V put(K key, V value) {
        V previous = get(key);
        if (previous == null) {
            if (size + 1 > capacity * loadFactor) resize();
            size++;
        }
        putIntoTable(key, value, table);
        return previous;
    }

    @Override
    public V remove(Object key) {
        int index = getIndexByKey((K) key);
        Node<K, V> foundNode = table[index];
        for (int i = 0; ; i++) {
            if (foundNode == null) {
                return null;
            }
            if (Objects.equals(foundNode.key, key)) {
                table[index] = foundNode.next; //TODO: fix remove from middle of chain
                size--;
                return foundNode.value;
            }
            if (foundNode.next == null) {
                return null;
            }
            foundNode = foundNode.next;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m == null) {
            throw new NullPointerException("Cannot add null map");
        }
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        table = (Node<K, V>[]) new Node[initSize];
        capacity = initSize;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return entrySet().stream().map(Entry::getKey).collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return entrySet().stream().map(Entry::getValue).collect(Collectors.toSet());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<>() {
                    private int index = 0;
                    private Node<K, V> current;

                    @Override
                    public boolean hasNext() {
                        while (current == null && index < capacity) {
                            current = table[index];
                            index++;
                        }
                        return current != null;
                    }

                    @Override
                    public Entry<K, V> next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        Entry<K, V> element = current;
                        current = current.next;
                        return element;
                    }
                };
            }

            @Override
            public int size() {
                return size;
            }
        };
    }
}
