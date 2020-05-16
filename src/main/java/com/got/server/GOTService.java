package com.got.server;

import com.got.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Session;
import java.io.IOException;
import java.io.StringReader;

@Slf4j
@Component
public class GOTService {


    /**
     * This method receives the input from other player
     * and then answer with a new number
     *
     * @param message input message
     * @param session current session
     * @throws IOException IOException
     */
    public void answerWithNewNumber(String message, Session session) throws IOException {
        String number = getNumberFromJSON(message);
        int rawDigit = Integer.parseInt(number);
        int result = getDivisibleBy3(rawDigit);
        int addedValue = result - rawDigit;
        int output = result / 3;
        if (output == 1) {
            log.info(Utils.YOU_WON);
            System.out.println(Utils.YOU_WON);
            session.getBasicRemote().sendText(Utils.GAME_OVER);
        } else {
            session.getBasicRemote().sendText(Utils.getMessage(String.valueOf(addedValue), String.valueOf(output)));
        }
    }

    /**
     * This method is extracting number value from JSON
     *
     * @param message input
     * @return String
     */
    private String getNumberFromJSON(String message) {
        JsonObject jsonObject = Json.createReader(new StringReader(message)).readObject();
        return jsonObject.getString(Utils.resultingNumber);
    }

    /**
     * This method modifies the input which is divisible by 3
     *
     * @param rawDigit whole number
     * @return int
     */
    private int getDivisibleBy3(int rawDigit) {
        return rawDigit % 3 == 0 ? rawDigit : ((rawDigit - 1) % 3 == 0 ? (rawDigit - 1) : rawDigit + 1);
    }

}
