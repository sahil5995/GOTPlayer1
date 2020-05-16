package com.got.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;

@Slf4j
@Component
@ServerEndpoint(value = "/start")
public class ServerEndPoint {

    private Session session = null;

    private String gameOver = "Game Over";
    private String youWon = "You won the game";
    private String youLost = "You lost the game..!!";

    /**
     * Method for Connection open events.
     *
     * @param session the session which is opened.
     */
    @OnOpen
    public void createSession(Session session) {
        log.info("Player 2 joined the game");
        this.session = session;
    }

    /**
     * This method will be invoked when other player sends a message.
     *
     * @param message The received text message
     * @throws IOException          IOException
     * @throws InterruptedException InterruptedException
     */
    @OnMessage
    public void onMessage(String message) throws IOException, InterruptedException {

        if (!message.contains(gameOver)) {
            log.info("Player2 sent:" + message);
            Thread.sleep(1000);

            JsonObject jsonObject = Json.createReader(new StringReader(message)).readObject();
            String number = jsonObject.getString("Resulting Number");

            int rawDigit = Integer.parseInt(number);
            int result = getDivisibleBy3(rawDigit);
            int addedValue = result - rawDigit;
            int output = result / 3;

            if (output == 1) {
                log.info(youWon);
                System.out.println(youWon);
                this.session.getBasicRemote().sendText(gameOver);
            } else {
                this.session.getBasicRemote().sendText(getMessage(String.valueOf(addedValue), String.valueOf(output)));
            }
        } else {
            log.info(youLost);
            System.out.println(youLost);
        }
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


    /**
     * Method for Connection close events.
     *
     * @param session the session which is getting closed.
     * @param reason  the reason for connection close
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) throws IOException {
        log.error("Connection closed with the other player.");
    }

    /**
     * Method for receiving any errors.
     *
     * @param session   the session which is getting closed.
     * @param throwable the error which is thrown
     */
    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        log.error(throwable.getMessage());
    }


    /**
     * This method converts the string into JSON message
     *
     * @param added  modified value
     * @param Number resulting number
     * @return json message
     */
    private static String getMessage(String added, String Number) {
        return Json.createObjectBuilder()
                .add("Added", added)
                .add("Resulting Number", Number)
                .build()
                .toString();
    }

}
