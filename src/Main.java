import task1.Task1;
import task2.Task2;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && "1".equals(args[0])) {
            Task1.run();
        } else if (args.length > 0 && "2".equals(args[0])) {
            Task2.run();
        } else {
            Task1.run();
            System.out.println();
            Task2.run();
        }
    }
}
