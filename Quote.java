package org.example;

import lombok.Getter;

public class Quote{

    @Getter
    int idx = 0;
    String quote = "";
    String author = "";

    public Quote(){}

    public Quote(String quote, String author){
        this.quote = quote;
        this.author = author;
    }

    public Quote setIdx(int idx){
        this.idx = idx;
        return this;
    }

}