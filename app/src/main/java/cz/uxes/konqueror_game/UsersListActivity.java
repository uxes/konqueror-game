package cz.uxes.konqueror_game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import cz.uxes.konqueror_game.network.WsConnection;
import cz.uxes.konqueror_game.network.WsConnectionSingleton;

public class UsersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        ListView listView = (ListView) findViewById(R.id.userList);

        String[] fruits = new String[] { "Uxes", "Cosik", "Jakysik hrač", "Jakysik jiny hrač" };

        //todo: should not use singleton, thread seems to be threaten
        WsConnectionSingleton ws = WsConnectionSingleton.INSTANCE;


        ws.connection.start();
        //ws.connection.run("10.0.0.25");

        //ws.webSocket.sendText("text");

        //ws.fetchUsers();

        ListAdapter li = new ArrayAdapter<String>(this, R.layout.user_row, R.id.userName, fruits);
        listView.setAdapter(li);

    }

}
