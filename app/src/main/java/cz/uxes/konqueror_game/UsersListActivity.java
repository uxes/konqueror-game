package cz.uxes.konqueror_game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import cz.uxes.konqueror_game.network.WsConnection;

public class UsersListActivity extends AppCompatActivity {

    private WsConnection ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        ListView listView = (ListView) findViewById(R.id.userList);

        String[] fruits = new String[] { "Uxes", "Cosik", "Jakysik hrač", "Jakysik jiny hrač" };

        //todo: should not use singleton, thread seems to be threaten


        ws =  new WsConnection();
        ws.execute("10.0.0.25");


        ListAdapter li = new ArrayAdapter<String>(this, R.layout.user_row, R.id.userName, fruits);
        listView.setAdapter(li);

    }

    public void loadPlayers(View view){

        ws.fetchUsers();


        ListView listView = (ListView) findViewById(R.id.userList);

        String[] fruits = new String[] { "Uxes" };


        ListAdapter li = new ArrayAdapter<String>(this, R.layout.user_row, R.id.userName, fruits);
        listView.setAdapter(li);


    }

}
