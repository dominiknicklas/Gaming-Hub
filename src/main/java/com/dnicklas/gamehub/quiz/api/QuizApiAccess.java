package com.dnicklas.gamehub.quiz.api;

import com.dnicklas.gamehub.quiz.model.Question;
import com.dnicklas.gamehub.quiz.model.Topics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class QuizApiAccess {
    private static org.json.simple.JSONArray getQuestionJSONFromApi(String urlString) {
        try {
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline = inline.concat(scanner.nextLine());
                }
                scanner.close();

                JSONParser parse = new JSONParser();

                return (org.json.simple.JSONArray) parse.parse(inline);
            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Question> getQuestions(Topics topic, String amount) {
        String topicString = topic.toString();
        String url = String.format("https://the-trivia-api.com/api/questions?categories=%s&limit=%s", topicString, amount);
        List<Question> questions = new ArrayList<>();
        org.json.simple.JSONArray jsonQuestions = getQuestionJSONFromApi(url);

        for (Object obj : jsonQuestions) {
            Question question = new Question();
            JSONObject jsonObj = (JSONObject) obj;
            question.setQuestion((String) jsonObj.get("question"));
            String correctAnswer = (String) jsonObj.get("correctAnswer");
            question.setCorrectAnswer(correctAnswer);
            question.setAnswersOne(correctAnswer);
            org.json.simple.JSONArray incorrectAnswers = (JSONArray) jsonObj.get("incorrectAnswers");
            question.setAnswerTwo(incorrectAnswers.get(0).toString());
            question.setAnswerThree(incorrectAnswers.get(1).toString());
            question.setAnswerFour(incorrectAnswers.get(2).toString());
            questions.add(question);
        }

        return questions;
    }
}
