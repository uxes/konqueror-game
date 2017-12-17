package cz.uxes.konqueror_game;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerLayout;


import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.uxes.konqueror_game.db.Storage;
import cz.uxes.konqueror_game.network.Realm;

public class WellcomeActivity extends AppCompatActivity {

    public Storage storage;
    public static WellcomeActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        this.instance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        ButterKnife.bind(this);
        this.storage = new Storage(this);


        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        mp.start();

    }



    public void showJoinGame(View view){
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.showScores)
    public void showHistory(View view){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
        Log.wtf("get", "older");
    }

    @OnClick(R.id.quit)
    public void quitGame(){

        finishActivity(0);
        System.exit(0);

    }
}
