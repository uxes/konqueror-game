package cz.uxes.konqueror_game;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cz.uxes.konqueror_game.network.Player;

/**
 * Created by uxes on 18/10/2017.
 */

public class CustomAdapter extends ArrayAdapter<Player> {
    Context context;
    List<Player> players;
    Integer layoutResourceId;

    public CustomAdapter(Context context, int layoutResourceId ,List<Player> players) {
        super(context, layoutResourceId, players);
        this.context = context;
        this.players = players;
        this.layoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View viewRow = layoutInflater.inflate(R.layout.user_row, parent, false);
Log.wtf("kurwa", "co to kurva..");

        if(this.layoutResourceId == R.layout.user_row) {


            Player player = players.get(position);
            TextView userName = (TextView) viewRow.findViewById(R.id.userName);
            userName.setText(player.getNick());

            TextView userLevel = (TextView) viewRow.findViewById(R.id.userLevel);
            userLevel.setText(player.getLevel().toString());
        }
        else if(this.layoutResourceId == R.layout.activity_score){


            Player player = players.get(position);
            TextView userName = (TextView) viewRow.findViewById(R.id.userName);
            userName.setText(player.getNick());


            TextView userScore = (TextView) viewRow.findViewById(R.id.userLevel);
            userScore.setText(player.getScore());

        }

        return viewRow;

    }

}
