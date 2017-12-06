package cz.uxes.konqueror_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


        ws =  new WsConnection("10.0.0.139");
        ws.execute();
        //poprve spadne, jeste neni spojeni
        ws.fetchUsers();

        final PlayerSingleton ps = PlayerSingleton.INSTANCE;


        ListView listView = (ListView) findViewById(R.id.userList);

        Log.d("instance", ps.getPlayerList().toString());

        CustomAdapter ca = new CustomAdapter(this, R.layout.user_row, ps.getPlayerList());
        listView.setAdapter(ca);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "klikls na cosik " + position, Toast.LENGTH_LONG);
                Log.d("cosik", "kliks.. " + ps.getPlayerList().get(position).getNick());
            }

        });

    }

    public void loadPlayers(View view){

        Log.wtf("invalidace", "load players");

        ws.fetchUsers();

        PlayerSingleton ps = PlayerSingleton.INSTANCE;

        TextView counter = (TextView) findViewById(R.id.onlinePlayerCounter);
        Integer count = ps.getPlayerList().size();
        counter.setText(count.toString());


        ListView listView = (ListView) findViewById(R.id.userList);

        Log.d("instance", ps.getPlayerList().toString());

        CustomAdapter ca = new CustomAdapter(this, R.layout.user_row, ps.getPlayerList());
        listView.setAdapter(ca);

//        ListAdapter li = new ArrayAdapter<Player>(this, R.layout.user_row, R.id.userName, fruits);
        //listView.setAdapter(li);


    }






}
