package cz.uxes.konqueror_game;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import cz.uxes.konqueror_game.network.Player;

/**
 * Created by havelada on 18/10/2017.
 */

public class CustomAdapter extends ArrayAdapter<Player> {
    Context context;
    List<Player> players;
    Integer layoutResourceId;

    public CustomAdapter(Context context, int layoutResourceId ,List<Player> players) {
        super(context, R.layout.user_row, players);
        this.context = context;
        this.players = players;
        this.layoutResourceId = layoutResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View viewRow = layoutInflater.inflate(R.layout.user_row, parent, false);

        Player player = players.get(position);
        TextView userName = (TextView) viewRow.findViewById(R.id.userName);
        userName.setText(player.getNick());

//        TextView userLevel = (TextView) viewRow.findViewById(R.id.userLevel);
//        userLevel.setText(player.getLevel());

        return viewRow;

    }
}
