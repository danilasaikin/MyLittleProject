import java.util.Scanner;

public class ATM {
    private Bank bank;
    private String atmId;

    public ATM(Bank bank, String atmId) {
        this.bank = bank;
        this.atmId = atmId;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите идентификатор: ");
        String accountId = scanner.nextLine();
        System.out.print("Введите пин-код: ");
        String pin = scanner.nextLine();

        Account account = bank.getAccountByIdAndPin(accountId, pin);
        if (account != null) {
            boolean exit = false;

            while (!exit) {
                System.out.println("1. Посмотреть баланс");
                System.out.println("2. Внести деньги");
                System.out.println("3. Снять деньги");
                System.out.println("4. Сделать перевод");
                System.out.println("5. Выйти");

                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Баланс: " + account.getBalance());
                        break;
                    case 2:
                        System.out.print("Введите сумму для внесения: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        System.out.println("Баланс обновлен. Новый баланс: " + account.getBalance());
                        break;
                    case 3:
                        System.out.print("Введите сумму для снятия: ");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                        System.out.println("Баланс обновлен. Новый баланс: " + account.getBalance());
                        break;
                    case 4:
                        System.out.print("Введите номер счета получателя: ");
                        String targetAccountNumber = scanner.next();
                        System.out.print("Введите сумму для перевода: ");
                        double transferAmount = scanner.nextDouble();

                        boolean transferResult = bank.makeTransfer(account, targetAccountNumber, transferAmount);
                        if (transferResult) {
                            System.out.println("Перевод выполнен успешно.");
                        } else {
                            System.out.println("Не удалось выполнить перевод.");
                        }
                        break;
                    case 5:
                        exit = true;
                        System.out.println("Выход из банкомата.");
                        break;
                    default:
                        System.out.println("Неверный выбор.");
                        break;
                }
            }
        } else {
            System.out.println("Неверные идентификатор или пин-код.");
        }

        scanner.close();
    }

    public static void main(String[] args) {
        // Создание банка и добавление счетов

        Bank bank = new Bank("MyBank");
        Owner owner1 = new Owner("John Doe", "123-456-789");
        Owner owner2 = new Owner("Jane Smith", "987-654-321");

        Account account1 = new Account("1001", "1234", owner1);
        Account account2 = new Account("1002", "5678", owner2);

        bank.addAccount(account1);
        bank.addAccount(account2);

        ATM atm = new ATM(bank, "ATM-001");
        atm.start();
    }
}
