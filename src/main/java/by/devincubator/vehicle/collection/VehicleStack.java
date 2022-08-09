package by.devincubator.vehicle.collection;

import by.devincubator.vehicle.Vehicle;

import java.util.Arrays;

public class VehicleStack<T> {
    private static final String EMPTY_STACK = "Stack is empty";
    private static final int MIN_INITIAL_CAPACITY = 8;
    private Vehicle[] arrayElements;
    private int head;
    private int tail;
    private int size;

    public VehicleStack() {
        arrayElements = new Vehicle[MIN_INITIAL_CAPACITY];
        head = 0;
        tail = -1;
        size = 0;
    }

    public Vehicle pop() {
        Vehicle vehicle = peek();
        size--;
        tail--;
        arrayElements = Arrays.copyOfRange(arrayElements, head, size);
        return vehicle;
    }

    public void push(Vehicle obj) {
        if (tail == arrayElements.length - 1) {
            arrayElements = Arrays.copyOf(arrayElements, size() + 1);
        }
        arrayElements[++tail] = obj;
        size++;
    }

    public Vehicle peek() {
        if (isEmpty()) {
            throw new IllegalStateException(EMPTY_STACK);
        }
        return arrayElements[tail];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head > tail || arrayElements[head] == null;
    }
}
