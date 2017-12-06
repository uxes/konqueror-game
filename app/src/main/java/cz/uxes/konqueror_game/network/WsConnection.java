package cz.uxes.konqueror_game.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.uxes.konqueror_game.R;
import cz.uxes.konqueror_game.UsersListActivity;

/**
 * Created by uxes on 18.10.17.
 */

public class WsConnection extends AsyncTask<String, Void, String> {

    public WebSocket webSocket;
    private String serverIP;

    public WsConnection(String serverIP) {
        this.serverIP = serverIP;
    }

    public void fetchUsers() {

        try{

            webSocket.sendText("{\"onlinePlayers\": true}");

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected String doInBackground(String... server) {

        final PlayerSingleton ps = PlayerSingleton.INSTANCE;

        try{

            webSocket = new WebSocketFactory()
                    .setConnectionTimeout(5000)
                    .createSocket("ws://" + serverIP + ":8124")
                    .addProtocol("connection")
                    .addListener(new WebSocketAdapter() {
                        // A text message arrived from the server.
                        public void onTextMessage(WebSocket websocket, String message) throws JSONException, IOException {
                            System.out.println(message);
                            Log.d("cosiks", message);

                            JSONObject jObject = new JSONObject(message);
                            JSONArray players = jObject.getJSONArray("players");

                            List<Player> newPlayerList = new ArrayList<Player>();

                            for(int i = 0; i < players.length(); i++){
                                Player player = new Player(
                                        players.getJSONObject(i).getString("nick"),
                                        players.getJSONObject(i).getString("score"),
                                        players.getJSONObject(i).getInt("level")
                                );

                                newPlayerList.add(player);
                                Log.wtf("player - sc", player.getScore());
                                Log.wtf("player - nick", player.getNick());
                                Log.wtf("player - lvl", player.getLevel().toString());

                            }

                            ps.setPlayerList(newPlayerList);


                            UsersListActivity.listView.invalidate();

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
