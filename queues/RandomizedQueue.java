import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items = (Item[]) new Object[1];
    private int n = 0;

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = n;
        private final Item[] a = items.clone();

        public RandomizedQueueIterator() {
            StdRandom.shuffle(a, 0, i);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return a[--i];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();

        if (n == items.length) resize(items.length * 2);
        items[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item randomItem = sample();
        items[--n] = null;
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return randomItem;
    }

    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int rand = StdRandom.uniform(n);
        Item last = items[n - 1];
        Item randomItem = items[rand];

        items[n - 1] = randomItem;
        items[rand] = last;

        return randomItem;
    }

    private void resize(int max) {
        Item[] newItems = (Item[]) new Object[max];

        for (int i = 0; i < n; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }
}
