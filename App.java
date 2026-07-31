package org.example;

import java.util.Scanner;

import static java.lang.IO.print;
import static java.lang.IO.println;

public class App {

    public void run(Scanner scanner){

        QuoterController quoterController = new QuoterController();

        println("== 명언 앱 ==");

        boolean isRunning = true;
        while (isRunning){

            print("명령) ");
            String[] command = scanner.next().split("\\?id=");

            switch (command[0]){
                case "종료" :
                    isRunning = false;
                    break;
                case "등록" :
                    quoterController.registration();
                    break;
                case "목록" :
                    quoterController.printList();
                    break;
                case "삭제" :
                    quoterController.delete(command);
                    break;
                case "수정" :
                    quoterController.update(command);
                    break;
                case "빌드" :
                    quoterController.build();
                    break;

            }

        }

    }

}
