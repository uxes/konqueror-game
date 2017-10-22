package cz.uxes.konqueror_game.network;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by uxes on 18.10.17.
 */

public class WsConnection extends AsyncTask<String, Void, String> {

    public WebSocket webSocket;

    public List<User> fetchUsers() {

        try{

            webSocket.sendText("{\"onlinePlayers\": true}");




            return new ArrayList<>();
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();

        }

    }

    @Override
    protected String doInBackground(String... server) {

        try{

            webSocket = new WebSocketFactory()
                    .setConnectionTimeout(5000)
                    .createSocket("ws://" + server[0] + ":8124")
                    .addProtocol("connection")
                    .addListener(new WebSocketAdapter() {
                        // A text message arrived from the server.
                        public void onTextMessage(WebSocket websocket, String message) throws JSONException, IOException {
                            System.out.println(message);
                            Log.d("cosiks", message);



                            JSONObject jObject = new JSONObject(message);
                            String aJsonString = jObject.getString("players");
                            Log.d("co tam je?", aJsonString);

                            ObjectMapper objectMapper = new ObjectMapper();
                            objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

                            TypeReference<List<User>> mapType = new TypeReference<List<User>>() {};

                            //todo: nějak se mu nechce parsovat..
                            List<User> returner = objectMapper.readValue(aJsonString, mapType);

                            for (User u : returner) {
                                Log.d("cosik", u.getNick());

                            }



                            Log.d("bloody", objectMapper.writeValueAsString(returner));


                            Log.d("cosiksy", message);
                        }
                    })
                    .connect();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
