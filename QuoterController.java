package org.example;

import java.util.Scanner;

import static java.lang.IO.print;
import static java.lang.IO.println;

public class QuoterController {

    QuoteService quoteService = new QuoteService();

    void registration() {
        Scanner scanner = new Scanner(System.in);

        print("명언 : ");
        String quote = scanner.next();
        print("작가 : ");
        String author = scanner.next();

        int idx = quoteService.save(new Quote(quote,author));

        println(idx + "번 명언이 등록되었습니다.");
    }

    void printList() {
        println("번호 / 작가 / 명언");

        for(Quote quote : quoteService.getAll()){
            println(quote.idx + " / " + quote.author + " / " + quote.quote);
        }
    }

    void delete(String[] command){

        try{
            int key = Integer.parseInt(command[1]);

            if(quoteService.check(key)){
                quoteService.delete(key);
                println(key + "번 명언이 삭제되었습니다.");
            }else{
                println(key + "번 명언은 존재하지 않습니다.");
            }

        } catch (Exception e) {
            println("명언 번호 타입 오류");
        }

    }

    void update(String[] command){
        Scanner scanner = new Scanner(System.in);

        try{
            int key = Integer.parseInt(command[1]);

            if(quoteService.check(key)){
                Quote quote = quoteService.get(key);
                println("명언(기존) : " + quote.quote);
                print("명언 : ");
                quote.quote = scanner.next();
                println("작가(기존) : " + quote.author);
                print("작가 : ");
                quote.author = scanner.next();

                quoteService.update(key,quote);
            }else {
                println(key + "번 명언은 존재하지 않습니다.");
            }

        } catch (Exception e) {
            println("명언 번호 타입 오류");
        }

    }

    void build(){
        quoteService.build();
        println("data.json 파일의 내용이 갱신되었습니다.");
    }

}
