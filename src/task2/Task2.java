package task2;

public class Task2 {
    public static void run() {
        System.out.println("--- Task 2: Producer-Consumer (Circular Buffer) ---");

        CircularBuffer<String> buffer1 = new CircularBuffer<>(10);
        CircularBuffer<String> buffer2 = new CircularBuffer<>(10);

        for (int i = 1; i <= 5; i++) {
            final int producerId = i;
            Thread producer = new Thread(() -> {
                int messageNumber = 1;
                while (true) {
                    try {
                        String msg = "Потік № " + producerId + " згенерував повідомлення " + messageNumber++;
                        buffer1.put(msg);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            producer.setDaemon(true);
            producer.start();
        }

        for (int i = 1; i <= 2; i++) {
            final int translatorId = i;
            Thread translator = new Thread(() -> {
                while (true) {
                    try {
                        String originalMsg = buffer1.take();
                        String translatedMsg = "Потік № " + translatorId + " переклав повідомлення: [" + originalMsg + "]";
                        buffer2.put(translatedMsg);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            translator.setDaemon(true);
            translator.start();
        }

        int count = 100;
        System.out.println("Вичитування " + count + " повідомлень із другого буфера:");
        for (int i = 1; i <= count; i++) {
            try {
                String result = buffer2.take();
                System.out.println(i + ": " + result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Вичитування 100 повідомлень завершено.");
    }
}
