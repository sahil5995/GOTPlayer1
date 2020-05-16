package com.got.server;

import com.got.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Slf4j
@Component
@ServerEndpoint(value = "/start")
public class ServerEndPoint {

    private Session session = null;
    private GOTService gotService;

    /**
     * Method for Connection open events.
     *
     * @param session the session which is opened.
     */
    @OnOpen
    public void createSession(Session session) {
        log.info("Player 2 joined the game");
        this.session = session;
        gotService = new GOTService();
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

        if (!message.contains(Utils.GAME_OVER)) {
            log.info("Player2 sent:" + message);
            Thread.sleep(1000);
            gotService.answerWithNewNumber(message, this.session);
        } else {
            log.info(Utils.YOU_LOST);
            System.out.println(Utils.YOU_LOST);
        }
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

}
