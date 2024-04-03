package com.bb.coffee2.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.coffee2.Model.CoffeeModel;
import com.bb.coffee2.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeListHolder> {

    List<CoffeeModel> coffeeModelList;
    GetOneCoffee interfacegetcoffee;
    public CoffeeAdapter(GetOneCoffee interfacegetcoffee){
        this.interfacegetcoffee = interfacegetcoffee;
    }
    @Override
    public CoffeeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coffeeliststyle, parent,false);
        return new CoffeeListHolder(view);
    }

    @Override
    public void onBindViewHolder(CoffeeListHolder holder, int pos) {
        holder.coffeename.setText(coffeeModelList.get(pos).getCoffeeName());
        holder.description.setText(coffeeModelList.get(pos).getDescription());
        Log.d("IMG", "url: " + coffeeModelList.get(pos).getImageUrl());
        Glide.with(holder.itemView.getContext()).load(coffeeModelList.get(pos).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return coffeeModelList.size();
    }

    public void setCoffeeModelList(List<CoffeeModel> coffeeModelList) {
        this.coffeeModelList = coffeeModelList;
    }




    class CoffeeListHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView coffeename, description;
        ImageView imageView;
        public CoffeeListHolder(View itemView) {
            super(itemView);
            // component from coffeeliststyle.xml
            coffeename = itemView.findViewById(R.id.coffeeName);
            description= itemView.findViewById(R.id.coffeedetail);
            imageView= itemView.findViewById(R.id.coffeeImage);

            coffeename.setOnClickListener((View.OnClickListener) this);
            description.setOnClickListener((View.OnClickListener) this);
            imageView.setOnClickListener((View.OnClickListener) this);
        }
        @Override
        public void onClick(View view) {
            interfacegetcoffee.clickedCoffee(getAdapterPosition(),coffeeModelList);
        }
    }
    public interface GetOneCoffee {
        void clickedCoffee(int pos, List<CoffeeModel> coffeeModels);
    }
}
