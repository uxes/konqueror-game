package cz.uxes.konqueror_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.uxes.konqueror_game.db.Storage;
import cz.uxes.konqueror_game.map.KonquerMap;
import cz.uxes.konqueror_game.network.Player;
import cz.uxes.konqueror_game.network.PlayerSingleton;
import cz.uxes.konqueror_game.network.WsConnection;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        ListView listView = (ListView) findViewById(R.id.historyList);


        Storage storage = new Storage(this);

        CustomAdapter ca = new CustomAdapter(this, R.layout.activity_score, storage.getScores());
        listView.setAdapter(ca);

    }
}
