import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("""
                    What would you like to do?
                    (1) Convert currency
                    (2) Get weather
                    (3) Convert audio file to text
                    (4) Quit?
                    Please enter your choice:""");
            int choice = getValidInt(sc);
            sc.nextLine(); // got to consume Scanner.nextInts missing '\n' before reading nextLine again.
            if (choice == 4) {
                System.out.println("Closing program..");
                break;
            }
            switch (choice) {
                case 1 -> convertCurrency(sc);
                case 2 -> weatherCheck(sc);
                case 3 -> convertAudio(sc);
            }
        }
    }

    private static int getValidInt(Scanner sc) {
        while (true) {
            try {
                int choice = sc.nextInt();
                if (choice <= 4 && choice >= 1) return choice;
                else System.out.print("Please, enter a number between 1 and 4\n" +
                        "Try again: ");
            } catch (Exception e) {
                System.out.println("I dont think that's a valid choice.");
                System.out.print("Try again: ");
                sc.nextLine();
            }
        }
    }

    private static void convertCurrency(Scanner sc) throws URISyntaxException, IOException, InterruptedException{
        int currencyChoice1, currencyChoice2;
        double currencyAmount;
        while (true) {
            System.out.print("""
                    (1) USD
                    (2) EUR
                    (3) SEK
                    Please, select currency you want to convert from:""");
            try {
                currencyChoice1 = sc.nextInt();
                System.out.print("""
                    (1) USD
                    (2) EUR
                    (3) SEK
                    Please, select currency you want to convert to:""");
                currencyChoice2 = sc.nextInt();
                System.out.println("Please enter the amount:");
                currencyAmount = sc.nextDouble();
                break;
            } catch (Exception e) {
                System.out.println("Invalid choice");
                sc.nextLine();
            }
        }
        String currencyFrom;
        String currencyTo;
        switch(currencyChoice1) {
            case 1 -> currencyFrom = "USD";
            case 2 -> currencyFrom = "EUR";
            case 3 -> currencyFrom = "SEK";
            default -> currencyFrom = "EUR";
        }
        switch(currencyChoice2) {
            case 1 -> currencyTo = "USD";
            case 2 -> currencyTo = "EUR";
            case 3 -> currencyTo = "SEK";
            default -> currencyTo = "USD";
        }
            Currency.getCurrencyRate(currencyFrom, currencyTo, currencyAmount);

    }
    private static void weatherCheck(Scanner sc) throws URISyntaxException, IOException, InterruptedException {
        System.out.print("Please enter the name of the city: ");
        String city = sc.nextLine();
        Weather.getWeather(city);
    }

    private static void convertAudio(Scanner sc) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("Please enter a http address to your audio file: ");
        String httpAddress = sc.nextLine();
        AudioToText.convertAudioToText(httpAddress);
    }
}
