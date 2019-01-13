import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int size = 0;
    private Node first, last;

    private class Node {
        Item item;
        Node next, previous;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();

            Item item = current.item;
            current = current.next;

            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();

        Node node = new Node();
        node.item = item;

        if (size == 0) {
            first = node;
            last = node;
        } else {
            first.previous = node;
            node.next = first;
            first = node;
        }

        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();

        Node node = new Node();
        node.item = item;

        if (size == 0) {
            first = node;
            last = node;
        } else {
            last.next = node;
            node.previous = last;
            last = node;
        }

        size += 1;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Node node = first;
        size -= 1;

        if (size == 0) {
            first = null;
            last = null;
        } else {
            first = node.next;
            first.previous = null;
        }

        return node.item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Node node = last;
        size -= 1;

        if (size == 0) {
            first = null;
            last = null;
        } else {
            last = node.previous;
            last.next = null;
        }

        return node.item;
    }
}
