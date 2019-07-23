package ru.epam.maven;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *Класс для получения цитаты на запрос на сайт bash.im
 * */
public class Requester {

    /**
     * Переменная цитата
     */
    private String statement;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    /**
     * Конструктор, которому на вход подаётся номер цитаты @param integer для её поиска
     */
    public Requester(Integer integer) {
        getPhrase(integer);
    }

    /**
     * Метод для передачи запроса на сайт и обработки полученного ответа для получения цитаты пол указанныи номером
     */
    private void getPhrase(Integer num) {
        try {
            URL url = new URL(" https://bash.im/quote/" + num);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("quote__body")) {
                        line = reader.readLine();
                        if(!line.contains("Утверждено")){
                            setStatement(line);
                        }else {
                            setStatement("Цитаты не найдено.");
                        }
                        modifyStr();
                        reader.close();
                        connection.disconnect();
                        return ;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Обработка полученной строки для исправления неотобразившихся символов и удалении лишних пробелов
     */
    private void modifyStr() {
        if (getStatement() == null) {
            setStatement("Not found");
        } else {
            setStatement(getStatement().trim());
            setStatement(getStatement().replaceAll("&quot;", "\""));
            setStatement(getStatement().replaceAll("<br>", "\n"));
            setStatement(getStatement().replaceAll("&gt;", ">"));
            setStatement(getStatement().replaceAll("&lt;", "<"));
        }
    }

}
