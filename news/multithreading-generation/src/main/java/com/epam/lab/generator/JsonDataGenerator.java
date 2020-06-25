package com.epam.lab.generator;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.model.Author;
import com.google.gson.Gson;
import net.andreinc.mockneat.MockNeat;

import java.util.Random;

public class JsonDataGenerator {

    private static final String TITLE = "title";
    private static final String SHORT_TEXT = "shortText";
    private static final String FULL_TEXT = "fullText";
    private static final String CREATION_DATE = "creationDate";
    private static final String MODIFICATION_DATE = "modificationDate";
    private static final String AUTHOR = "author";
    private static final String AUTHOR_NAME = "name";
    private static final String AUTHOR_SURNAME = "surname";
    private static final int DIVIDER = 2;
    private static final String WRONG_FIELD = "wrongField";
    private static final double RIGHT_WRONG_BORDER_PROBABILITY = 0.2;

    public static String generateRightJson() {
        MockNeat mock = MockNeat.threadLocal();
        Gson gson = new Gson();
        return mock.reflect(NewsDto.class)
                .field(TITLE, mock.strings())
                .field(SHORT_TEXT, mock.strings())
                .field(FULL_TEXT, mock.strings())
                .field(CREATION_DATE, mock.localDates())
                .field(MODIFICATION_DATE, mock.localDates())
                .field(AUTHOR, mock.reflect(AuthorDto.class)
                        .field(AUTHOR_NAME, mock.names())
                        .field(AUTHOR_SURNAME, mock.names()))
                .map(gson::toJson)
                .val();
    }

    public static String generateWrongFieldJson() {
        MockNeat mock = MockNeat.threadLocal();
        Gson gson = new Gson();
        return mock.reflect(NewsDto.class)
                .field(TITLE, mock.strings())
                .field(SHORT_TEXT, mock.strings())
                .field(FULL_TEXT, mock.strings())
                .field(CREATION_DATE, mock.localDates())
                .field(MODIFICATION_DATE, mock.localDates())
                .field(AUTHOR, mock.reflect(AuthorDto.class)
                        .field(AUTHOR_NAME, mock.names())
                        .field(AUTHOR_SURNAME, mock.names()))
                .map(gson::toJson)
                .val();
    }

    public static String generateNonValidBeanJson() {
        MockNeat mock = MockNeat.threadLocal();
        Gson gson = new Gson();
        return mock.reflect(AuthorDto.class)
                .field(AUTHOR_NAME, mock.names())
                .field(AUTHOR_SURNAME, mock.names())
                .map(gson::toJson)
                .val();
    }

    public static String generateWrongJson() {
        MockNeat mock = MockNeat.threadLocal();
        Gson gson = new Gson();
        String json = mock.reflect(AuthorDto.class)
                .field(AUTHOR_NAME, mock.names())
                .field(AUTHOR_SURNAME, mock.names())
                .map(gson::toJson)
                .val();
        return json.substring(json.length() / DIVIDER);
    }

    public static String generateJson() {
        Random random = new Random();
        double probability = random.nextDouble();
        if (probability > RIGHT_WRONG_BORDER_PROBABILITY) {
            return generateRightJson();
        } else {
            final double WRONG_JSON_RIGHT_BORDER_PROBABILITY = 0.33;
            final double WRONG_FIELD_RIGHT_BORDER_PROBABILITY = 0.66;
            double wrongProbability = random.nextDouble();
            if (wrongProbability <= WRONG_JSON_RIGHT_BORDER_PROBABILITY) {
                return generateWrongJson();
            }
            if (wrongProbability > WRONG_JSON_RIGHT_BORDER_PROBABILITY
                    && wrongProbability <= WRONG_FIELD_RIGHT_BORDER_PROBABILITY) {
                return generateWrongFieldJson();
            }
            return generateNonValidBeanJson();
        }
    }
}
