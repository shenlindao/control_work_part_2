package Interface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.io.FileWriter;

import Classes.Toy;

public class Prize {
    public static void main(ArrayList<Toy> toyList, Scanner scanner, Queue<Toy> prizeQueue,
            String prizeQueueFilePath)
            throws IOException, InterruptedException {
        prizeDrawing(toyList, scanner, prizeQueue, prizeQueueFilePath);
    }

    public static void prizeDrawing(ArrayList<Toy> toyList, Scanner scanner, Queue<Toy> prizeQueue,
            String prizeQueueFilePath)
            throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("PRIZE DRAWING");
        System.out.println();
        System.out.println("1 - Start");
        System.out.println("2 - Get a prize toy");
        System.out.println("3 - Return to main menu");
        System.out.println("else key - exit program");
        System.out.println();
        System.out.println("Select the number: ");
        try {
            int menuChoise = Integer.parseInt(scanner.nextLine());
            switch (menuChoise) {
                case 1:
                    startDrawing(toyList, scanner, prizeQueue, prizeQueueFilePath);
                    break;
                case 2:
                    getPrizeToy(toyList, scanner, prizeQueue, prizeQueueFilePath);
                    break;
                case 3:
                    Interface.MainInterface.mainMenu(toyList, scanner, prizeQueue, prizeQueueFilePath);
                    break;
                default:
                    System.out.println("\nHave a good day!\n");
                    System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("\nHave a good day!\n");
            System.exit(0);
        }
        scanner.close();
    }

    public static void startDrawing(ArrayList<Toy> toyList, Scanner scanner, Queue<Toy> prizeQueue,
            String prizeQueueFilePath)
            throws IOException, InterruptedException {
        Toy prizeToy = anyToy(toyList);
        if (prizeToy == null) {
            System.out.println("\nSorry you lose :( try again later");
            PartOfMenu.showPart(toyList, scanner, prizeQueue, prizeQueueFilePath);
        }
        Toy queueToy = new Toy(prizeToy.getId(), prizeToy.getName(), 1, prizeToy.getFrequency());
        if (prizeToy.getQuantity() == 1) {
            toyList.remove(prizeToy);
        } else {
            Toy toy = toyList.get(prizeToy.getId() - 1);
            int quantity = toy.getQuantity();
            toy.setQuantity(quantity - 1);
        }
        prizeQueue.add(queueToy);
        System.out.println("\nCongratulations! You have won the " + queueToy.getName());
        PartOfMenu.showPart(toyList, scanner, prizeQueue, prizeQueueFilePath);
    }

    public static void getPrizeToy(ArrayList<Toy> toyList, Scanner scanner, Queue<Toy> prizeQueue,
            String prizeQueueFilePath)
            throws IOException, InterruptedException {
        Toy firstToy = prizeQueue.element();
        try {
            FileWriter writer = new FileWriter(prizeQueueFilePath, true);
            writer.write(firstToy.getName() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
        System.out.println(firstToy);
        prizeQueue.remove(firstToy);
        PartOfMenu.showPart(toyList, scanner, prizeQueue, prizeQueueFilePath);

    }

    public static Toy anyToy(ArrayList<Toy> toyList) {
        int random = (int) (Math.random() * 100);
        System.out.println("You random number: " + random);
        int frequencyLimit = 0;
        Toy prizeToy = null;
        for (int i = 0; i < toyList.size(); i++) {
            if (random >= frequencyLimit && random <= (int) toyList.get(i).getFrequency()) {
                prizeToy = toyList.get(i);
                return prizeToy;
            } else {
                frequencyLimit += (int) toyList.get(i).getFrequency();
            }
        }
        return prizeToy;
    }

    public static void showPrizeList(ArrayList<Toy> toyList, Scanner scanner, Queue<Toy> prizeQueue,
            String prizeQueueFilePath)
            throws IOException, InterruptedException {
        System.out.println(prizeQueue);
        PartOfMenu.showPart(toyList, scanner, prizeQueue, prizeQueueFilePath);
    }
}
