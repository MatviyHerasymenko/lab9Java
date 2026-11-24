package task1;

import java.util.Random;

public class Task1 {
    public static void run() {
        int accountCount = 100;
        int threadCount = 3000;
        Random random = new Random();

        Account[] accounts = new Account[accountCount];
        long initialTotal = 0;

        for (int i = 0; i < accountCount; i++) {
            int balance = 1000 + random.nextInt(9001);
            accounts[i] = new Account(i, balance);
            initialTotal += balance;
        }

        System.out.println("--- Task 1: Bank Transfer ---");
        System.out.println("Кількість рахунків: " + accountCount);
        System.out.println("Початкова сума в банку: " + initialTotal);

        Bank bank = new Bank();
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                Random r = new Random();
                int fromIndex = r.nextInt(accountCount);
                int toIndex = r.nextInt(accountCount);
                int amount = 1 + r.nextInt(500);
                bank.transfer(accounts[fromIndex], accounts[toIndex], amount);
            });
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        long finalTotal = 0;
        boolean hasNegativeBalance = false;
        for (Account account : accounts) {
            int bal = account.getBalance();
            if (bal < 0) {
                hasNegativeBalance = true;
            }
            finalTotal += bal;
        }

        System.out.println("Кількість потоків переказу: " + threadCount);
        System.out.println("Кінцева сума в банку: " + finalTotal);
        System.out.println("Суми збігаються: " + (initialTotal == finalTotal));
        System.out.println("Негативні баланси відсутні: " + (!hasNegativeBalance));
    }
}
