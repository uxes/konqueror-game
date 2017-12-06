package cz.uxes.konqueror_game.map;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.qozix.tileview.TileView;

import cz.uxes.konqueror_game.network.Realm;

/**
 * Created by uxes on 6.12.17.
 */

public class KonquerMap extends Activity {

    Realm[] realms = {new Realm("Pískovna", 0.25, 0.25), new Realm("Hliuxov", 0.76, 0.26), new Realm("Zelená", 0.96, 0.92)};
    private TileView tileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate( savedInstanceState );
        tileView = new TileView( this );
        tileView.setSize( 800, 800 );

        tileView.setScaleLimits( 0, 2 );

        tileView.setShouldRenderWhilePanning( true );

        tileView.addDetailLevel( 1f, "tiles/1/%d-%d.png");

        //setRealms();


        TextView textView = new TextView(this);
        textView.setText(realms[0].name);
        tileView.addMarker(textView, 0.25, 0.75, null, null);


        tileView.defineBounds( 0, 0, 1, 1 );



        setContentView( tileView );

    }

    private void setRealms(){

        for (Realm realm : realms) {

            TextView textView = new TextView(this);
            textView.setText(realm.name);
            tileView.addMarker(textView, 0.25, 0.75, null, null);

        }

    }


}
