package com.famousquotes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
DataClass[] quotes;
Context context;
    public RecyclerViewAdapter(Context context, DataClass[] dataClasses){
        this.context = context;
        quotes = dataClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View recItem = layoutInflater.inflate(R.layout.rec_items, parent,false);
        return new ViewHolder(recItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.tvQuote.setText(quotes[position].getQuote());
        holder.tvAuthor.setText(quotes[position].getAuthor());

        //Button to add quote into favorite list
        /*holder.imgBtnFavorite.setOnClickListener(v -> {
               if(holder.imgBtnFavorite.getContentDescription().equals(context.getString(R.string.favorite_border_desc))){
                   //set filled icon on btn
                   holder.imgBtnFavorite.setBackgroundResource(R.drawable.favorite_filled_24);
                   holder.imgBtnFavorite.setContentDescription("favorite filled");
               }
               else{
                   //set border icon on btn
                   holder.imgBtnFavorite.setBackgroundResource(R.drawable.favorite_border_24);
                   holder.imgBtnFavorite.setContentDescription("favorite border");
               }
            }
        ); */
        holder.imgBtnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Quote", quotes[position].getQuote()+" ~"+quotes[position].getAuthor());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return quotes.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvQuote, tvAuthor;
        public LinearLayout linearLayout;
        public ImageButton imgBtnCopy;

        //ImageButton imgBtnFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgBtnCopy = itemView.findViewById(R.id.imgBtn_recItems_copy);
            this.tvQuote = itemView.findViewById(R.id.tv_recItems_quote);
            this.tvAuthor = itemView.findViewById(R.id.tv_recItems_authorName);
            this.linearLayout = itemView.findViewById(R.id.ll_main_quoteContainer);

            //Button to add quote into favorite list
            //this.imgBtnFavorite = itemView.findViewById(R.id.imgBtn_recItems_favorite);
        }
    }
}
