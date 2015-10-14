import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] jQueue;
    private int numberOfElements = 0;
    private Random jRand = new Random();

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        jQueue = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    public int size() {
        return numberOfElements;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newQueueSize) {
        Item[] copyQueue = (Item[]) new Object[newQueueSize];
        for (int i = 0; i < numberOfElements; i++) {
            copyQueue[Math.floorMod(i, newQueueSize)] = jQueue[Math.floorMod(i, jQueue.length)];
        }
        jQueue = copyQueue;
        copyQueue = null;

    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (numberOfElements == jQueue.length)
            resize(2 * jQueue.length);
        jQueue[numberOfElements++] = item;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Array is Empty");
        int randomElementIndex = jRand.nextInt(numberOfElements--);
        Item item = jQueue[randomElementIndex];
        jQueue[randomElementIndex] = jQueue[numberOfElements];
        jQueue[numberOfElements] = null;

        if (numberOfElements > 0 && numberOfElements <= jQueue.length / 4)
            resize(jQueue.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Array is Empty");
        return jQueue[jRand.nextInt(numberOfElements)];
    }

    public Iterator<Item> iterator() {
        return new JIterator();
    }

    private class JIterator implements Iterator<Item> {
        int currentNumberOfElements = 0;
        int[] indexArray;

        public JIterator() {
            indexArray = new int[numberOfElements];
            for (int i = 0; i < indexArray.length; i++) {
                indexArray[i] = i;
            }
            StdRandom.shuffle(indexArray);
        }

        public boolean hasNext() {
            return numberOfElements > currentNumberOfElements;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                return jQueue[indexArray[currentNumberOfElements++]];
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}