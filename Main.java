package org.example;

import java.util.Scanner;

public class Main {

    static QuoteRepository quoteFileRepository = new QuoteFileRepositoryImpl();

    static void main() {
        App app = new App();
        Scanner scanner = new Scanner(System.in);
        app.run(scanner);
    }

}

