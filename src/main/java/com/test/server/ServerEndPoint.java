package com.test.server;

import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;

@Component
@ServerEndpoint(value = "/hello")
public class ServerEndPoint {

    private Session session=null;

    @OnOpen
    public void createSession(Session session) {
        System.out.println("Player 2 joined the game");
        this.session = session;
    }

    @OnMessage
    public void onMessage(String msg) throws IOException, InterruptedException {
        System.out.println("Player2 sent:" + msg);
        Thread.sleep(2000);

        if (!msg.contains("wins")) {

            JsonObject jsonObject = Json.createReader(new StringReader(msg)).readObject();
            String number = jsonObject.getString("Resulting Number");


            int n = Integer.parseInt(number);

            int result = n % 3 == 0 ? n : ((n - 1) % 3 == 0 ? (n - 1) : n + 1);

            int added = result - n;

            int output = result / 3;

            if (output == 1) {
                System.out.println("You won");
                this.session.getBasicRemote().sendText("You Lost. Player1 wins");
                System.exit(0);

            } else {
                this.session.getBasicRemote().sendText(getMessage(String.valueOf(added), String.valueOf(output)));
            }
        }
    }



    @OnClose
    public void onClose(Session session, CloseReason reason) throws IOException {
        System.out.println("in close");
        //closeSessionAndExit();
    }

    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        System.out.println("in err");
    }

    private void closeSessionAndExit() throws IOException {
        this.session.close();
        System.exit(0);
    }

    private static String getMessage(String added, String Number) {
        return Json.createObjectBuilder()
                .add("Added", added)
                .add("Resulting Number", Number)
                .build()
                .toString();
    }

}
