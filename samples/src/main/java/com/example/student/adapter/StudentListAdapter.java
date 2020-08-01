package com.example.student.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.R;
import com.example.student.StudentDetailsResultActivity;
import com.example.student.database.DB_Handler;
import com.example.student.interfaces.ItemClickListener;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.List;
import java.util.Random;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {
    private Context mContext;
    private String[] mList;
    private int mBackground;
    private static final Random RANDOM = new Random();
    private int[] mMaterialColors;
    private final TypedValue mTypedValue = new TypedValue();
    public StudentListAdapter(Context contexts, String[] list) {
        this.mContext = contexts;
        this.mList = list;

        contexts.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mMaterialColors = contexts.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {





        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(2);
        holder.mIcon.setLetterSize(18);

        holder.mBoundString = mList[position];
        holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        holder.titleTextView.setText(mList[position]);
        holder.mIcon.setLetter(mList[position]);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(mContext, "#" + position + " - " + mList[position] + " (Long click)", Toast.LENGTH_SHORT).show();
                } else {
                    DB_Handler db = new DB_Handler(mContext);
                    int pos = position + 1;
                    String studentdetails = db.getStudentDetails(pos);
                    Log.e("studentdetails",studentdetails);

                    Toast.makeText(mContext, "#" + position + " - " + mList[position], Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(mContext,StudentDetailsResultActivity.class);
                    in.putExtra("qrvalue",studentdetails);
                    in.putExtra("FLAGVALUE","0");
                    mContext.startActivity(in);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mList.length;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        public String mBoundString;
        private TextView titleTextView;;
        private MaterialLetterIcon mIcon;
        private ItemClickListener clickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.text1);
            mIcon = (MaterialLetterIcon) itemView.findViewById(R.id.icon);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}