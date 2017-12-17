package cz.uxes.konqueror_game.network;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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
import cz.uxes.konqueror_game.WellcomeActivity;

/**
 * Created by uxes on 18.10.17.
 */

public class WsConnection extends AsyncTask<String, Void, String> {

    public WebSocket webSocket;
    private String serverIP;
    private Context context;

    public WsConnection(String serverIP, Context context) {
        this.serverIP = serverIP;
        this.context = context;
    }

    public void fetchUsers() {

        try{

            webSocket.sendText("{\"onlinePlayers\": true, \"nick\": \"ferda\"}");

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
                            //System.out.println(message);
                            Log.d("cosiks", message);

                            JSONObject jObject = new JSONObject(message);

                            try{

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

                            }catch (Exception e){
                                //e.printStackTrace();
                            }


                            //listen when someone offers a game
                            try{

                                Boolean cosik  = jObject.getBoolean("offerGame");
                                Log.d("nasrani", cosik.toString());
                                final String opponentNick = jObject.getString("opponent");

                                //open some modal if you wants to accept game

                                WellcomeActivity.instance.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(context)
                                                .setTitle("Game offer")
                                                .setMessage("User " + opponentNick + " wants to play with u")
                                                .setCancelable(false)
                                                .setPositiveButton("dobre no", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {

                                                        Log.d("tampere", "poslat potvrtzeni");
                                                        //moustachify(null, url);
                                                    }
                                                })
                                                .setNegativeButton("nasrat", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        Log.d("tampere", "poslat smula");
                                                    }
                                                })
                                                .show();
                                    }
                                });




                                //from modal, call:  {nick: this.nick, acceptOpponent: true, opponent: "Dummy user 1"}

                            }catch (Exception e){
                                //e.printStackTrace();
                            }

                            //listen when other side accepts game
                            try{

                                Boolean cosik  = jObject.getBoolean("gameAccepted");
                                String opponentNick = jObject.getString("opponent");

                                //open map

                                //from modal, call:  {nick: this.nick, acceptOpponent: true, opponent: "Dummy user 1"}

                            }catch (Exception e){
                                //e.printStackTrace();
                            }




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
