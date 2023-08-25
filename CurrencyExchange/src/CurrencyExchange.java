import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyExchange {
    private Map<String, Double> exchangeRates;

    public CurrencyExchange() {
        exchangeRates = new HashMap<>();
        // Заполните курсы обмена валют в соответствии с вашими требованиями
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("GBP", 0.75);
    }

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        if (exchangeRates.containsKey(fromCurrency) && exchangeRates.containsKey(toCurrency)) {
            double rateFrom = exchangeRates.get(fromCurrency);
            double rateTo = exchangeRates.get(toCurrency);
            return (amount / rateFrom) * rateTo;
        }
        return -1.0; // Некорректные валюты
    }

    public void startExchange() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите сумму: ");
        double amount = scanner.nextDouble();

        System.out.print("Введите валюту (USD, EUR, GBP): ");
        String fromCurrency = scanner.next().toUpperCase();

        System.out.print("Введите желаемую валюту (USD, EUR, GBP): ");
        String toCurrency = scanner.next().toUpperCase();

        double convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);
        if (convertedAmount >= 0) {
            System.out.println("Сумма " + amount + " " + fromCurrency + " эквивалентна " +
                    convertedAmount + " " + toCurrency);
        } else {
            System.out.println("Некорректные валюты.");
        }

        scanner.close();
    }

    public static void main(String[] args) {
        CurrencyExchange exchange = new CurrencyExchange();
        exchange.startExchange();
    }
}
