package ua.lab.parallel;

public class Producer extends Thread {

    private final RingBuffer<String> out;

    public Producer(int id, RingBuffer<String> out) {
        this.out = out;
        setDaemon(true);
        setName("Producer-" + id);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = "Потік " + getName() + " згенерував повідомлення " + System.nanoTime();
                out.put(msg);
                Thread.sleep(10);
            }
        } catch (Exception ignored) {}
    }
}
