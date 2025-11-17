package ua.lab.parallel;

public class Translator extends Thread {

    private final RingBuffer<String> in;
    private final RingBuffer<String> out;

    public Translator(int id, RingBuffer<String> in, RingBuffer<String> out) {
        this.in = in;
        this.out = out;
        setDaemon(true);
        setName("Translator-" + id);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = in.get();
                String newMsg = "Потік " + getName() + " переклав повідомлення: " + msg;
                out.put(newMsg);
            }
        } catch (Exception ignored) {}
    }
}
