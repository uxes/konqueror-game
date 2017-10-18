package cz.uxes.konqueror_game;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        ListView listView = (ListView) findViewById(R.id.userList);

        String[] fruits = new String[] { "Uxes", "Cosik", "Jakysik hrač", "Jakysik jiny hrač" };

        ListAdapter li = new ArrayAdapter<String>(this, R.layout.user_row, R.id.userName, fruits);
        listView.setAdapter(li);

    }

}
