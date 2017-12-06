package cz.uxes.konqueror_game;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerLayout;



import cz.uxes.konqueror_game.network.Realm;

public class WellcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.addLogAdapter(new AndroidLogAdapter());

        /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        */

        super.onCreate( savedInstanceState );
        TileView tileView = new TileView( this );
        tileView.setSize( 420, 480 );

        //tileView.setScaleLimits( 0, 2 );

        tileView.setShouldRenderWhilePanning( true );

        tileView.addDetailLevel( 1f, "tiles/1/%d-%d.png");

        tileView.defineBounds( 0, 0, 1, 1 );

        addCosik(tileView);


        // add a marker listener
        tileView.setMarkerTapListener( mMarkerTapListener );


        setContentView( tileView );
    }

    void addCosik(TileView viev){

        Realm[] realms = {new Realm("Pískovna", 0.15, 0.5), new Realm("Zelená", 0.76, 0.26), new Realm("Hliuxov", 0.86, 0.82)};

        for (Realm realm : realms) {

            TextView textView = new TextView(this);
            textView.setText(realm.name);
            textView.setTextColor(Color.WHITE);
            textView.setTag(realm);
            viev.addMarker(textView, realm.x, realm.y, null, null);

        }
    }

    public void showJoinGame(View view){
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);

    }


    private MarkerLayout.MarkerTapListener mMarkerTapListener = new MarkerLayout.MarkerTapListener() {
        @Override
        public void onMarkerTap( View v, int x, int y ) {
            Toast.makeText( getApplicationContext(), "You tapped a pin " + x + " " + y, Toast.LENGTH_LONG ).show();

            Realm realm = (Realm) v.getTag();

            Log.d("name", realm.name);
            Log.d("name x", "" +  realm.x);
            Log.d("name y", "" +  realm.y);

            Log.d("tap", v.toString());
            Log.d("tap", "You tapped a pin " + x + " " + y);
        }
    };
}
