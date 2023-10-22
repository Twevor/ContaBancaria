package SistemaContaBancaria.Class;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {
    static Scanner scanner = new Scanner(System.in);

    public void limpar() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public Integer getUserInteger(Scanner scanner, String texto) {
        Integer numero = 0;
        Boolean verificacao = false;

        while (!verificacao) {
            try {
                System.out.print(texto);
                numero = scanner.nextInt();
                verificacao = true;
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida");
                scanner.nextLine();
            }
        }
        return numero;
    }

    public Double getUserDouble(Scanner scanner, String texto) {
        Double numero = 0.0;
        Boolean verificacao = false;

        while (!verificacao) {
            try {
                System.out.print(texto);
                numero = scanner.nextDouble();
                verificacao = true;
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida");
                scanner.nextLine();
            }
        }
        return numero;
    }

    public void pressToContinue() {
        System.out.println("Pressione a tecla ENTER para continuar.");
        scanner.nextLine();
    }

}