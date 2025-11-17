package ua.lab.parallel;

import java.util.*;
import java.util.concurrent.*;

public class BankTest {

    public static void main(String[] args) throws Exception {

        Bank bank = new Bank();
        Random random = new Random();

        int accountsNumber = 200;
        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < accountsNumber; i++) {
            accounts.add(new Account(i, random.nextInt(1000) + 500));
        }

        int initial = accounts.stream().mapToInt(Account::getBalance).sum();
        System.out.println("Initial total = " + initial);

        ExecutorService pool = Executors.newFixedThreadPool(200);

        for (int i = 0; i < 5000; i++) {
            pool.submit(() -> {
                Account a1 = accounts.get(random.nextInt(accountsNumber));
                Account a2 = accounts.get(random.nextInt(accountsNumber));
                int amount = random.nextInt(50);

                if (a1 != a2) bank.transfer(a1, a2, amount);
            });
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        int after = accounts.stream().mapToInt(Account::getBalance).sum();
        System.out.println("Final total = " + after);

        if (initial == after)
            System.out.println("Баланс збігається. Тест успішний!");
        else
            System.out.println("Баланс різний! Помилка!");
    }
}
