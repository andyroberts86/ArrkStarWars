package com.appt.arrkstarwars.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appt.arrkstarwars.R;
import com.appt.arrkstarwars.models.Character;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewModel> {
    private final List<Character> items;

    CharactersAdapter(List<Character> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character_name, parent, false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        holder.name.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewModel extends RecyclerView.ViewHolder {
        private final TextView name;

        private ViewModel(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
