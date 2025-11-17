package ua.lab.parallel;

public class RingBuffer<T> {

    private final Object lock = new Object();
    private final Object[] buffer;

    private int head = 0;
    private int tail = 0;
    private int size = 0;

    public RingBuffer(int capacity) {
        buffer = new Object[capacity];
    }

    public void put(T value) throws InterruptedException {
        synchronized (lock) {
            while (size == buffer.length) {
                lock.wait();
            }
            buffer[tail] = value;
            tail = (tail + 1) % buffer.length;
            size++;
            lock.notifyAll();
        }
    }

    @SuppressWarnings("unchecked")
    public T get() throws InterruptedException {
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
            }
            T value = (T) buffer[head];
            head = (head + 1) % buffer.length;
            size--;
            lock.notifyAll();
            return value;
        }
    }
}
