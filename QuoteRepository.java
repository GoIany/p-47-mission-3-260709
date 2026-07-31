package org.example;

import java.util.List;

public interface QuoteRepository {

    int save(Quote quote);

    boolean update(int idx, Quote quote);

    boolean delete(int idx);

    boolean check(int idx);

    Quote get(int idx);

    List<Quote> getAll();

    boolean build();

}
