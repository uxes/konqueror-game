package cz.uxes.konqueror_game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.uxes.konqueror_game.network.PlayerSingleton;
import cz.uxes.konqueror_game.network.WsConnection;
import cz.uxes.konqueror_game.network.Player;

public class UsersListActivity extends AppCompatActivity {

    private WsConnection ws;
    public static ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Log.wtf("invalidace", "on crate");

        listView = (ListView) findViewById(R.id.userList);


        ws =  new WsConnection("10.0.0.6");
        ws.execute();
        //poprve spadne, jeste neni spojeni
        ws.fetchUsers();

        PlayerSingleton ps = PlayerSingleton.INSTANCE;


        ListView listView = (ListView) findViewById(R.id.userList);

        Log.d("instance", ps.getPlayerList().toString());

        CustomAdapter ca = new CustomAdapter(this, R.layout.user_row, ps.getPlayerList());
        listView.setAdapter(ca);

    }

    public void loadPlayers(View view){

        Log.wtf("invalidace", "load players");

        ws.fetchUsers();

        PlayerSingleton ps = PlayerSingleton.INSTANCE;


        ListView listView = (ListView) findViewById(R.id.userList);

        Log.d("instance", ps.getPlayerList().toString());

        CustomAdapter ca = new CustomAdapter(this, R.layout.user_row, ps.getPlayerList());
        listView.setAdapter(ca);

//        ListAdapter li = new ArrayAdapter<Player>(this, R.layout.user_row, R.id.userName, fruits);
        //listView.setAdapter(li);


    }






}
