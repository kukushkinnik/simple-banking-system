package com.nik.banking;

import java.util.Scanner;

public class Main {
    public static String fileName;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        fileName =  args[1];
        UI ui = new UI(scanner);
        ui.start();
    }
}
