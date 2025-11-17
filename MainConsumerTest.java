package ua.lab.parallel;

public class MainConsumerTest {

    public static void main(String[] args) throws Exception {

        RingBuffer<String> buf1 = new RingBuffer<>(50);
        RingBuffer<String> buf2 = new RingBuffer<>(50);

        for (int i = 1; i <= 5; i++) {
            new Producer(i, buf1).start();
        }

        for (int i = 1; i <= 2; i++) {
            new Translator(i, buf1, buf2).start();
        }

        for (int i = 0; i < 100; i++) {
            String msg = buf2.get();
            System.out.println(msg);
        }

        System.out.println("Готово - 100 повідомлень прочитано.");
    }
}
