package com.example.atelierul_digital.week_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atelierul_digital.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView itemRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        itemRecyclerView = findViewById(R.id.myRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        itemRecyclerView.setLayoutManager(linearLayoutManager);

        ItemAdapter itemAdapter = new ItemAdapter();
        itemAdapter.setItemList(DataSource.getItemData(12));
        itemRecyclerView.setAdapter(itemAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        itemRecyclerView.addItemDecoration(dividerItemDecoration);

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView lastNameTextView, firstNameTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            lastNameTextView = itemView.findViewById(R.id.textView_lastName);
            firstNameTextView = itemView.findViewById(R.id.textView_firstName);

        }

        private void bindData(@NonNull final Item currentItem) {
            lastNameTextView.setText(currentItem.getLastName());
            firstNameTextView.setText(currentItem.getFirstName());
            updateBackground(getAdapterPosition());
        }
        private void updateBackground(int position){
            if(position%2==1){
                itemView.setBackgroundColor(Color.rgb(240,240,250));
            }else {
                itemView.setBackgroundColor(Color.rgb(255,255,250));
            }
        }
    }

    static class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        @NonNull
        private List<Item>itemList= new ArrayList<>();

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.bindData(itemList.get(position));
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public void setItemList(List<Item> itemList) {
            this.itemList = itemList;
            notifyDataSetChanged();
        }
    }
}
