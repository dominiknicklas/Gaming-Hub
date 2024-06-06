package com.dnicklas.gamehub.hangman.api;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class HangmanApiAccess {
    private static String getApiResponse() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://random-word-api.p.rapidapi.com/get_word"))
                .header("X-RapidAPI-Key", "18e7aa519fmsh21e5fbc7e65dfe0p18dcbfjsn2ab7d3ce4710")
                .header("X-RapidAPI-Host", "random-word-api.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    public static String getWord() {
        String apiResponse = getApiResponse();
        Pattern pattern = Pattern.compile("\\{\"word\":\"(\\w+)\"}");
        Matcher matcher = pattern.matcher(apiResponse);
        String word;
        if (matcher.find()) {
            word = matcher.group(1);
        } else {
            throw new RuntimeException("No word found");
        }
        return word;
    }
}
