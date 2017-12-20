package cz.uxes.konqueror_game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.uxes.konqueror_game.db.Storage;
import cz.uxes.konqueror_game.game.ConnectActivity;
import cz.uxes.konqueror_game.network.Player;
import cz.uxes.konqueror_game.network.Realm;

public class WellcomeActivity extends AppCompatActivity {

    public Storage storage;
    public static WellcomeActivity instance;
    public Integer triesLeft = 4;
    public Player myself;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        this.instance = this;

        Storage storage = new Storage(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        ButterKnife.bind(this);
        this.storage = new Storage(this);

        myself = storage.playerInfo();


        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        mp.start();


    }


    @Override
    protected void onDestroy(){
        super.onDestroy();

        UsersListActivity.ws.quitGame();
    }



    public void showJoinGame(View view){
        Intent intent = new Intent(this, ConnectActivity.class);
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

        UsersListActivity.ws.quitGame();
        storage.updatePlayer(myself);

        finishActivity(0);
        System.exit(0);

    }
}
