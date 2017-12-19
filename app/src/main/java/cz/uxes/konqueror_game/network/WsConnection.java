package cz.uxes.konqueror_game.network;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.uxes.konqueror_game.UsersListActivity;
import cz.uxes.konqueror_game.WellcomeActivity;
import cz.uxes.konqueror_game.db.Storage;
import cz.uxes.konqueror_game.game.AnswerActivity;
import cz.uxes.konqueror_game.map.KonquerMap;

/**
 * Created by uxes on 18.10.17.
 */

public class WsConnection extends AsyncTask<String, Void, String> {

    public WebSocket webSocket;
    private String serverIP;
    private Context context;
    private String opponentNick;
    private final String nick;

    public WsConnection(String serverIP, Context context) {
        this.serverIP = serverIP;
        this.context = context;
        nick = (new Storage(context)).playerInfo().getNick();
    }

    public void fetchUsers() {

        try{

            webSocket.sendText("{\"onlinePlayers\": true, \"nick\": \"" + this.nick + "\"}");

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

                        String nick = (new Storage(context)).playerInfo().getNick();

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
                                opponentNick = jObject.getString("opponent");

                                //open some modal if you wants to accept game

                                WellcomeActivity.instance.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(context)
                                                .setTitle("Game offer")
                                                .setMessage("User " + opponentNick + " wants to play with u")
                                                .setCancelable(false)
                                                .setPositiveButton("dobre no", new DialogInterface.OnClickListener() {


                                                    String nick = (new Storage(context)).playerInfo().getNick();

                                                    public void onClick(DialogInterface dialog, int whichButton) {

                                                        Log.d("tampere", "poslat potvrtzeni");
                                                        //moustachify(null, url);

                                                        webSocket.sendText("{\"acceptOpponent\": true, \"nick\": \"" + this.nick + "\", \"opponent\": \"" + opponentNick + "\"}");
                                                    }
                                                })
                                                .setNegativeButton("nasrat", new DialogInterface.OnClickListener() {
                                                    String nick = (new Storage(context)).playerInfo().getNick();

                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        Log.d("tampere", "poslat smula");
                                                        webSocket.sendText("{\"refuseOpponent\": true, \"nick\": \"" + this.nick + "\", \"opponent\": \"" + opponentNick + "\"}");
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
                                opponentNick = jObject.getString("opponent");

                                Intent intent = new Intent(context.getApplicationContext(), KonquerMap.class);
                                intent.putExtra("opponentNick", opponentNick);
                                context.startActivity(intent);

                                //webSocket.sendText("{\"getQuestion\": true, \"nick\": \"" + this.nick + "\", \"opponent\": \"" + opponentNick + "\"}");

                            }catch (Exception e){
                                //e.printStackTrace();
                            }

                            //listen when other side refuses game
                            try{

                                Boolean cosik  = jObject.getBoolean("gameRefused");
                                opponentNick = jObject.getString("opponent");

                                //open map

                                WellcomeActivity.instance.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(context)
                                                .setTitle("Game offer")
                                                .setMessage("User " + opponentNick + " refused to play with u")
                                                .setCancelable(false)
                                                .setPositiveButton("couž", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {

                                                        Log.d("tampere", "poslat potvrtzeni");
                                                    }
                                                })
                                                .setNegativeButton("close", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {

                                                    }
                                                })
                                                .show();
                                    }
                                });


                            }catch (Exception e){
                                //e.printStackTrace();
                            }

                            //listen when server gives a question
                            try{

                                Boolean cosik  = jObject.getBoolean("randomQuestion");
                                opponentNick = jObject.getString("opponent");
                                final Integer realm = jObject.getInt("realm");

                                JSONObject questionObj = jObject.getJSONObject("question");
                                final JSONArray players = questionObj.getJSONArray("answers");

                                final String question = questionObj.getString("question");


                                //open map

                                WellcomeActivity.instance.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        //open hrací pole..
                                        Intent intent = new Intent(context, AnswerActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("question", question);
                                        intent.putExtra("realm", realm);
                                        intent.putExtra("opponent", opponentNick);
                                        try {
                                            intent.putExtra("answer0", players.getString(0));
                                            intent.putExtra("answer1", players.getString(1));
                                            intent.putExtra("answer2", players.getString(2));
                                            intent.putExtra("answer3", players.getString(3));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d("chyba", "nelze parsovat");
                                        }


                                        context.getApplicationContext().startActivity(intent);
                                    }
                                });


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

    public void getQuestion(Integer realm) {
        webSocket.sendText("{\"getQuestion\": true, \"nick\": \"" + this.nick + "\", \"opponent\": \"" + this.opponentNick + "\", \"realm\": " + realm + "}");
    }

    public void incrementUserLevel() {
        webSocket.sendText("{\"incrementUserLevel\": true, \"nick\": \"" + this.nick + "\"}");
    }
}
