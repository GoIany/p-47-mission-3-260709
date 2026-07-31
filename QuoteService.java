package org.example;

import java.util.List;

public class QuoteService {

    QuoteRepository quoteRepository = new QuoteFileRepositoryImpl();

    public int save(Quote quote) {
        return quoteRepository.save(quote);
    }

    public boolean update(int idx, Quote quote) {
        return quoteRepository.update(idx, quote);
    }

    public boolean delete(int idx) {
        return quoteRepository.delete(idx);
    }

    public boolean check(int idx) {
        return quoteRepository.check(idx);
    }

    public Quote get(int idx) {
        return quoteRepository.get(idx);
    }

    public List<Quote> getAll() {
        return quoteRepository.getAll();
    }

    public boolean build() {
        return quoteRepository.build();
    }

}
