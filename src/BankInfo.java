import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class BankInfo {
    public static void main(String[] args) {
        String urlSpec = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";

        try (BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the first three digits of the bank account:");
            String accountNumber = userInputReader.readLine();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlSpec).openStream()))) {
                boolean found = br.lines()
                        .map(line -> line.split("\\t+"))
                        .filter(parts -> parts.length >= 2)
                        .filter(parts -> parts[0].trim().equals(accountNumber))
                        .peek(parts -> System.out.println("Bank number: " + parts[0].trim() + " | Bank name: " + parts[1].trim()))
                        .findFirst()
                        .isPresent();

                if (!found) {
                    System.out.println("No bank found for the given number.");
                }
            } catch (IOException e) {
                System.out.println("Error while reading data from the URL: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error reading input from user: " + e.getMessage());
        }
    }
}