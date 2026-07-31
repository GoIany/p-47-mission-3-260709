package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QuoteFileRepositoryImpl implements QuoteRepository {

    private static final String DB_PATH = "db/wiseSaying/";

    public QuoteFileRepositoryImpl() {
        new File("db/wiseSaying").mkdirs();
    }

    private int getLastIdx(){
        File lastIdxFile = new File("db/wiseSaying/lastId.txt");

        if (lastIdxFile.exists()) {
            try {
                return Integer.parseInt(Files.readString(lastIdxFile.toPath()));
            } catch (IOException e) {
                return 0;
            }
        } else {
            return 0;
        }

    }

    private String toJson(Quote quote){
        return """
                            {
                              "id": %d,
                              "content": "%s",
                              "author": "%s"
                            }
                            """.formatted(quote.idx, quote.quote, quote.author);
    }

    private Quote fromJson(String json){
        Pattern idPattern = Pattern.compile("\"id\"\\s*:\\s*(\\d+)");
        Pattern contentPattern = Pattern.compile("\"content\"\\s*:\\s*\"([^\"]+)\"");
        Pattern authorPattern = Pattern.compile("\"author\"\\s*:\\s*\"([^\"]+)\"");

        int id = 0;
        String content = "";
        String author = "";

        Matcher idMatcher = idPattern.matcher(json);
        if (idMatcher.find()) id = Integer.parseInt(idMatcher.group(1));

        Matcher contentMatcher = contentPattern.matcher(json);
        if (contentMatcher.find()) content = contentMatcher.group(1);

        Matcher authorMatcher = authorPattern.matcher(json);
        if (authorMatcher.find()) author = authorMatcher.group(1);

        return new Quote(content, author).setIdx(id);
    }

    @Override
    public int save(Quote quote) {
        try {
            quote.idx = getLastIdx() + 1;
            File jsonFile = new File(DB_PATH + quote.idx + ".json");

            FileWriter jsonWriter = new FileWriter(jsonFile);
            jsonWriter.write(toJson(quote));
            jsonWriter.close();

            FileWriter writer = new FileWriter("db/wiseSaying/lastId.txt");
            writer.write(String.valueOf(quote.idx));
            writer.close();
        } catch (IOException e) {
            return 0;
        }
        return quote.idx;
    }

    @Override
    public boolean update(int idx, Quote quote) {
        try {
            File jsonFile = new File(DB_PATH + idx + ".json");
            quote.idx = idx;
            FileWriter jsonWriter = new FileWriter(jsonFile);
            jsonWriter.write(toJson(quote));
            jsonWriter.close();
        }catch (IOException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int idx) {
        File jsonFile = new File(DB_PATH + idx + ".json");
        return jsonFile.delete();
    }

    @Override
    public boolean check(int idx) {
        File jsonFile = new File(DB_PATH + idx + ".json");
        return jsonFile.exists();
    }

    @Override
    public Quote get(int idx) {
        File jsonFile = new File(DB_PATH + idx + ".json");
        String json = "";
        try {
            json = Files.readString(jsonFile.toPath());
        } catch (IOException _) {}
        return fromJson(json);
    }

    @Override
    public List<Quote> getAll() {
        File fathFile = new File(DB_PATH);
        File[] jsonFiles = fathFile.listFiles((file, name) -> name.matches("\\d+\\.json"));

        assert jsonFiles != null;
        return Arrays.stream(jsonFiles).map(file -> {
            String json = "";
            try {
                json = Files.readString(file.toPath());
            } catch (IOException _) {}
            return fromJson(json);
        })
                .sorted(Comparator.comparingInt(Quote::getIdx).reversed())
                .toList();
    }

    @Override
    public boolean build() {

        try{
            File jsonFile = new File(DB_PATH + "data.json");

            FileWriter jsonWriter = new FileWriter(jsonFile);

            String jsons = getAll().reversed().stream()
                    .map(this::toJson)
                    .map(json -> {
                        String cleaned = json.trim();
                        return "  " + cleaned.replace("\n", "\n  ");
                    })
                    .collect(Collectors.joining(",\n", "[\n", "\n]"));

            jsonWriter.write(jsons);
            jsonWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
