package com.example.korzhik.testproject.rating;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.korzhik.testproject.R;

import java.util.List;


public class RatingLineAdapter
        extends RecyclerView.Adapter<RatingLineAdapter.ViewHolder> {

    private List<RatingLine> data;

    public RatingLineAdapter(final List<RatingLine> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return
                new ViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_ratingline,
                                parent,
                                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RatingLine ratingLine = data.get(position);
        holder.name.setText(ratingLine.name + " " + ratingLine.surname);
        holder.username.setText(ratingLine.username);
        holder.number.setText(ratingLine.number);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<RatingLine> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View view;

        private TextView name;

        private TextView username;

        private TextView number;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = view.findViewById(R.id.tvname);
            username = view.findViewById(R.id.tvusername);
            number = view.findViewById(R.id.tvnumber);
        }
    }
}
