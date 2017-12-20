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

/**
 * Created by uxes on 6.12.17.
 */

public class KonquerMap extends Activity {

    public static Realm[] realms = {new Realm(0, "Pískovna", 0.25, 0.25), new Realm(1, "Hliuxov", 0.76, 0.26), new Realm(2, "Zelená", 0.86, 0.92)};
    public static TileView tileView;
    private String opponentNick;
    private String nick;

    public static KonquerMap instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate( savedInstanceState );
        tileView = new TileView( this );
        tileView.setSize( 420, 480 );

        instance = this;

        tileView.setScaleLimits( 0, 4 );

        tileView.setShouldRenderWhilePanning( true );

        tileView.addDetailLevel( 1f, "tiles/1/%d-%d.png");

        tileView.defineBounds( 0, 0, 1, 1 );

        cosikRealms();

        opponentNick = getIntent().getStringExtra("opponentNick");
        Storage storage = new Storage(getApplicationContext());
        nick = storage.playerInfo().getNick();


        // add a marker listener
        tileView.setMarkerTapListener( mMarkerTapListener );


        setContentView( tileView );


    }

    public static void cosikRealms(){

        //tileView.removeMarker();

        for (Realm realm : realms) {

            TextView textView = new TextView(instance);
            textView.setText(realm.name);
            if(realm.isKonquered()){
                textView.setTextColor(Color.BLACK);
            }
            else{
                textView.setTextColor(Color.WHITE);
            }
            textView.setTag(realm);
            tileView.addMarker(textView, realm.x, realm.y, null, null);

        }
    }



    private MarkerLayout.MarkerTapListener mMarkerTapListener = new MarkerLayout.MarkerTapListener() {
        @Override
        public void onMarkerTap(View v, int x, int y ) {
            Realm realm = (Realm) v.getTag();
            if(realm.isKonquered()){
                Toast.makeText( getApplicationContext(), "This realm is already konquered", Toast.LENGTH_LONG ).show();
            }
            else{
                UsersListActivity.ws.webSocket.sendText("{\"getQuestion\": true, \"nick\": \"" + nick + "\", \"opponent\": \"" + opponentNick + "\", \"realm\": \"" + realm.id + "\"}");
            }

        }
    };


}
