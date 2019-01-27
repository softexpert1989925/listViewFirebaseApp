package com.mycomponents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.listviewapp.R;
import com.model.CellModel;

import java.util.List;

public class UserAdapter extends ArrayAdapter<CellModel> {

    public UserAdapter(Context context, List<CellModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        CellModel user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listcell, parent, false);
        }
        // Lookup view for data population

        TextView name = (TextView) convertView.findViewById(R.id.cell_action_name);
        TextView date=(TextView)convertView.findViewById(R.id.cell_action_date);

        // Populate the data into the template view using the data object
        name.setText(user.getActionName());
        date.setText(user.getActiondate());
        // Return the completed view to render on screen
        return convertView;

    }
}
