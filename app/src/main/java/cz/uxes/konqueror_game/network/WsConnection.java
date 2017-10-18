package cz.uxes.konqueror_game.network;

import android.util.Log;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uxes on 18.10.17.
 */

public class WsConnection extends Thread {

    public WebSocket webSocket;

    public void run(String server){

        try{

            webSocket = new WebSocketFactory()
                    .setConnectionTimeout(5000)
                    .createSocket("ws://" + server + ":8124")
                    .addProtocol("connection")
                    .addListener(new WebSocketAdapter() {
                        // A text message arrived from the server.
                        public void onTextMessage(WebSocket websocket, String message) {
                            System.out.println(message);
                            Log.d("cosiks", message);
                        }
                    })
                    .connect();

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public List<User> fetchUsers() {
        webSocket.sendText("{\"users\": true}");

        List<User> returner = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);



        webSocket.addListener(new WebSocketAdapter(){
            public void onTextMessage(WebSocket ws, String msg) throws Exception{

                Log.d("ws cosik", msg);
            }

        });




        return returner;
    }
}
