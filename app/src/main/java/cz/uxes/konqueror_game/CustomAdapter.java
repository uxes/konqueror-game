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

/**
 * Created by havelada on 18/10/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    public CustomAdapter(@NonNull Context context, String[] resource) {
        super(context, R.layout.user_row, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View viewRow = layoutInflater.inflate(R.layout.user_row, parent, false);


        String str = (String) getItem(position);
        TextView textView = (TextView) viewRow.findViewById(R.id.userName);
        textView.setText(str);

        return viewRow;

    }
}
