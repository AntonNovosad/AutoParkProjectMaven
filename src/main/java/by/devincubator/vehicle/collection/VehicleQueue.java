package by.devincubator.vehicle.collection;

import by.devincubator.vehicle.Vehicle;

import java.util.Arrays;

public class VehicleQueue<T> {
    private static final String EMPTY_QUEUE = "Queue is empty";
    private static final int MIN_INITIAL_CAPACITY = 8;
    private Vehicle[] arrayElements;
    private int head;
    private int tail;
    private int size;

    public VehicleQueue() {
        arrayElements = new Vehicle[MIN_INITIAL_CAPACITY];
        head = 0;
        tail = -1;
        size = 0;
    }

    public Vehicle dequeue() {
        Vehicle vehicle = peek();
        size--;
        arrayElements = Arrays.copyOfRange(arrayElements, 1, arrayElements.length);
        return vehicle;
    }

    public void enqueue(Vehicle obj) {
        if (tail == arrayElements.length - 1) {
            arrayElements = Arrays.copyOf(arrayElements, size() + 1);
        }
        arrayElements[++tail] = obj;
        size++;
    }

    public Vehicle peek() {
        if (isEmpty()) {
            throw new IllegalStateException(EMPTY_QUEUE);
        }
        return arrayElements[head];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head > tail || arrayElements[head] == null;
    }
}
