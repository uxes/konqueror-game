package cz.uxes.konqueror_game.map;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerLayout;

import cz.uxes.konqueror_game.UsersListActivity;
import cz.uxes.konqueror_game.WellcomeActivity;
import cz.uxes.konqueror_game.db.Storage;
import cz.uxes.konqueror_game.network.Realm;
import cz.uxes.konqueror_game.network.WsConnection;

/**
 * Created by uxes on 6.12.17.
 */

public class KonquerMap extends Activity {

    public static Realm[] realms = {new Realm(0, "Pískovna", 0.25, 0.25), new Realm(2, "Hliuxov", 0.76, 0.26), new Realm(3, "Zelená", 0.96, 0.92)};
    private TileView tileView;
    private String opponentNick;
    private String nick;

    public static KonquerMap instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate( savedInstanceState );
        TileView tileView = new TileView( this );
        tileView.setSize( 420, 480 );

        instance = this;

        //tileView.setScaleLimits( 0, 2 );

        tileView.setShouldRenderWhilePanning( true );

        tileView.addDetailLevel( 1f, "tiles/1/%d-%d.png");

        tileView.defineBounds( 0, 0, 1, 1 );

        addCosik(tileView);

        opponentNick = getIntent().getStringExtra("opponentNick");
        Storage storage = new Storage(getApplicationContext());
        nick = storage.playerInfo().getNick();


        // add a marker listener
        tileView.setMarkerTapListener( mMarkerTapListener );


        setContentView( tileView );


    }

    void addCosik(TileView viev){


        for (Realm realm : realms) {

            TextView textView = new TextView(this);
            textView.setText(realm.name);
            textView.setTextColor(Color.WHITE);
            textView.setTag(realm);
            viev.addMarker(textView, realm.x, realm.y, null, null);

        }
    }



    private MarkerLayout.MarkerTapListener mMarkerTapListener = new MarkerLayout.MarkerTapListener() {
        @Override
        public void onMarkerTap(View v, int x, int y ) {
            Toast.makeText( getApplicationContext(), "You tapped a pin " + x + " " + y, Toast.LENGTH_LONG ).show();

            Realm realm = (Realm) v.getTag();

            Log.d("name", realm.name);
            Log.d("name x", "" +  realm.x);
            Log.d("name y", "" +  realm.y);

            Log.d("tap", v.toString());
            Log.d("tap", "You tapped a pin " + x + " " + y);

            UsersListActivity.ws.webSocket.sendText("{\"getQuestion\": true, \"nick\": \"" + nick + "\", \"opponent\": \"" + opponentNick + "\", \"realm\": \"" + realm.id + "\"}");
        }
    };


    private void setRealms(){

        for (Realm realm : realms) {

            TextView textView = new TextView(this);
            textView.setText(realm.name);
            tileView.addMarker(textView, 0.25, 0.75, null, null);

        }

    }

}
