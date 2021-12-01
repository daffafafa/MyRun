package com.daffa.myrun;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RunViewHolder> {

    Context context;
    ArrayList _id, name, date;
    int position;

    CustomAdapter(Context context, ArrayList _id, ArrayList name, ArrayList date){
        this.context = context;
        this._id = _id;
        this.name = name;
        this.date = date;
    }

    @NonNull
    @Override
    public RunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.run_row, parent, false);

        return new RunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RunViewHolder holder, final int position) {
        this.position = position;
        holder._idView.setText(String.valueOf(_id.get(position)));
        holder.nameView.setText(String.valueOf(name.get(position)));
        holder.dateView.setText(String.valueOf(date.get(position)));
        holder.run_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewRecipeIntent = new Intent(context, RunDetails.class);
                viewRecipeIntent.putExtra("id", (Integer) _id.get(position));

                context.startActivity(viewRecipeIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _id.size();
    }

    public class RunViewHolder extends RecyclerView.ViewHolder{
        TextView _idView, nameView, dateView;
        LinearLayout run_list_item;

        public RunViewHolder(@NonNull View itemView) {
            super(itemView);
            _idView = itemView.findViewById(R.id.textViewID);
            nameView = itemView.findViewById(R.id.textViewName);
            dateView = itemView.findViewById(R.id.textViewDateValue);
            run_list_item = itemView.findViewById(R.id.RunsListItem);
        }
    }
}
