import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RealTimeCurrency {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Real-Time Currency Converter!");

        String apiKey = "2d17d3b697a384cbf899c5c8e7522b12";
        String baseCurrency = "USD";
        String favoriteCurrencies = "";

        while (true) {
            System.out.println("\nEnter the number of the operation you want to perform:");
            System.out.println("1. Add favorite currency");
            System.out.println("2. View favorite currencies");
            System.out.println("3. Update favorite currency");
            System.out.println("4. Show favorite currency");
            System.out.println("5. Exit");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Enter the currency code you want to add to your favorite currencies:");
                    String currencyCode = scanner.nextLine();
                    favoriteCurrencies += currencyCode + ",";
                    System.out.println("Added " + currencyCode + " to your favorite currencies.");
                    break;
                case 2:
                    System.out.println("Your favorite currencies are: " + favoriteCurrencies);
                    break;
                case 3:
                    System.out.println("Enter the currency code you want to update in your favorite currencies:");
                    String oldCurrencyCode = scanner.nextLine();
                    System.out.println("Enter the new currency code:");
                    String newCurrencyCode = scanner.nextLine();
                    favoriteCurrencies = favoriteCurrencies.replace(oldCurrencyCode + ",", newCurrencyCode + ",");
                    System.out.println("Updated " + oldCurrencyCode + " to " + newCurrencyCode + " in your favorite currencies.");
                    break;
                case 4:
                    try {
                        URL url = new URL("https://api.exchangerate.host/live" + baseCurrency + "&symbols=" + favoriteCurrencies);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Authorization", apiKey);

                        if (connection.getResponseCode() != 200) {
                            System.out.println("Failed to fetch the data. Server responded with: " + connection.getResponseCode());
                        } else {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String response = reader.readLine();
                            System.out.println("The data is: " + response);
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}