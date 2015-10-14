
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] jArray;

    //init cycle array index
    private int left = -1;
    private int right = 0;

    @SuppressWarnings("unchecked")
    public Deque() {
        int capacity = 2;
        jArray = (Item[]) new Object[capacity];
        // construct an empty deque
    }

    public boolean isEmpty() {
        // is the deque empty?
        return (right - left - 1 == 0);
    }

    public int size() {
        return right - left - 1;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newArraySize = 0;
        // make twice bigger size
        if (right - left - 1 >= jArray.length) {
            newArraySize = 2 * jArray.length;
        } // or twice smaller
        else if ((right - left - 1) > 0 && (right - left - 1) <= jArray.length / 4) {
            newArraySize = jArray.length / 2;
        } else {
            return;
        }
        // pass items to new array
        Item[] copyArray = (Item[]) new Object[newArraySize];

        for (int i = left + 1; i < right; i++) {
            copyArray[Math.floorMod(i, newArraySize)] = jArray[Math.floorMod(i, jArray.length)];
        }
        jArray = copyArray;
        copyArray = null;
    }

    public void addLast(Item item) {
        // check
        if (item == null) {
            throw new NullPointerException();
        }
        // add to the left and increment pointer
        jArray[Math.floorMod(left--, jArray.length)] = item;

        // check and resize
        resize();
    }

    public void addFirst(Item item) {
        // check
        if (item == null) {
            throw new NullPointerException();
        }
        // add to the right and increment pointer
        jArray[Math.floorMod(right++, jArray.length)] = item;
        resize();
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = jArray[Math.floorMod(++left, jArray.length)];
        jArray[Math.floorMod(left, jArray.length)] = null;
        resize();
        return item;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = jArray[Math.floorMod(--right, jArray.length)];
        jArray[Math.floorMod(right, jArray.length)] = null;
        resize();
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new JIterator();
    }

    private class JIterator implements Iterator<Item> {

         private Item[] testItemArray = jArray;
         private int numberOfElementsLeft = right - left - 1;
         private int currentRightIndex = right-1;


        @Override
        public boolean hasNext() {

            return numberOfElementsLeft > 0 ? true : false;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                numberOfElementsLeft--;
                return testItemArray[Math.floorMod(currentRightIndex--, testItemArray.length)];  
            } else
                throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}