package com.example.dairyapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Diary_RecycleViewAdapter extends RecyclerView.Adapter<Diary_RecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<diariesModel>diaryModels;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();



    public Diary_RecycleViewAdapter(Context context, ArrayList<diariesModel> diaryModels){
        this.context=context;
        this.diaryModels=diaryModels;
    }
    @NonNull
    @Override
    public Diary_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recyclerview_diaries,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Diary_RecycleViewAdapter.MyViewHolder holder, int position) {
        holder.date.setText(diaryModels.get(position).getDates());
        holder.diary.setText(diaryModels.get(position).getDiaries());
        String path = diaryModels.get(position).getImagePath();
        if (path != null && !path.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            if (bitmap != null) {
                holder.diaryImage.setImageBitmap(bitmap);
                holder.diaryImage.setVisibility(View.VISIBLE);
            }
        } else {
            holder.diaryImage.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, dairy_details.class);
                intent.putExtra("diary", diaryModels.get(position).getDiaries());
                intent.putExtra("date", diaryModels.get(position).getDates());
                intent.putExtra("imagePath", diaryModels.get(position).getImagePath());
                intent.putExtra("mood", diaryModels.get(position).getMood());  // 👈 Add this line

                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(v -> {
            String userId = auth.getCurrentUser().getUid();
            String docId = diaryModels.get(position).getDocumentId();

            db.collection("users").document(userId).collection("diaries")
                    .document(docId)
                    .delete()
                    .addOnSuccessListener(unused -> {
                        diaryModels.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Diary deleted", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show());
        });
        holder.supportText.setText(diaryModels.get(position).getSupportMessage());



    }

    @Override
    public int getItemCount() {
        Log.d("RecyclerViewDebug", "Number of items: " + diaryModels.size());
        return diaryModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView supportText;
        ImageButton btnDelete;

        ImageView diaryImage;

        TextView diary;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.tvdate);
            supportText = itemView.findViewById(R.id.tvSupport);
            btnDelete = itemView.findViewById(R.id.btnDelete);


            diary=itemView.findViewById(R.id.tvdiary);
            diaryImage = itemView.findViewById(R.id.diaryImage);


        }

    }
}
