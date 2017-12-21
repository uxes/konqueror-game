package cz.uxes.konqueror_game.game;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.uxes.konqueror_game.R;
import cz.uxes.konqueror_game.UsersListActivity;
import cz.uxes.konqueror_game.db.Storage;
import cz.uxes.konqueror_game.network.Player;
import cz.uxes.konqueror_game.network.WsConnection;

public class ConnectActivity extends AppCompatActivity {

    private Storage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        ButterKnife.bind(this);

        storage = new Storage(this);
        Player myself = storage.playerInfo();
        ((TextView) findViewById(R.id.connectNick)).setText( myself.getNick() );
        ((TextView) findViewById(R.id.connectHostname)).setText( myself.getHostname() );


    }

    @OnClick(R.id.connectButton)
    public void clickConnect(View view){
        //ziskat nove udaje, zapsat do db
        Player player = new Player();

        String hostname = ((TextView) findViewById(R.id.connectHostname) ).getText().toString();
        player.setNick( ( (TextView) findViewById(R.id.connectNick) ).getText().toString() );
        player.setId(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        player.setHostname( hostname );
        storage.updatePlayer(player);



        //udelat ws pozadavek



        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);

        WsConnection ws = new WsConnection(hostname, this);
        ws.fetchUsers();
    }

}
