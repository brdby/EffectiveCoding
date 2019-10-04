package com.brdby;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            String text = new String(Files.readAllBytes(Paths.get("input.txt")));
            Counter counter = new Counter(text);

            System.out.println("\nОбщее количество символов: " + counter.getSymNum());

            System.out.println("\nСимволы и их количество:");
            System.out.println(counter.getSym());

            System.out.println("\nОтсортированные вероятности:");
            System.out.println(counter.getSymProb());

            System.out.println("\nКодировка символов:");
            System.out.println(counter.getSymCodes());

            System.out.println("\nДлина кодировки:");
            System.out.println(counter.getSymCodesLenght());

            System.out.println("\nP*Q:");
            System.out.println(counter.getSymPQ());

            System.out.println("\nP*logP:");
            System.out.println(counter.getSymPlogP());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
