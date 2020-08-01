package com.example.student;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.adapter.StudentListAdapter;
import com.example.student.database.DB_Handler;
import com.example.student.interfaces.ItemClickListener;
import com.example.student.model.Exam;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.github.ivbaranov.mli.MaterialLetterIcon.Shape;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StudentList extends AppCompatActivity {
  private static String[] desuNoto;
  private static final String[] countries = {
      "Albania", "Australia", "Belgium", "Canada", "China", "Dominica", "Egypt", "Estonia",
      "Finland", "France", "Germany", "Honduras", "Italy", "Japan", "Madagascar", "Netherlands",
      "Norway", "Panama", "Portugal", "Romania", "Russia", "Slovakia", "Vatican", "Zimbabwe"
  };
  private static final int CONTACTS = 0;
  private static final int COUNTRIES = 1;
  private static final Random RANDOM = new Random();

  private RecyclerView recyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.student_main);

    DB_Handler db = new DB_Handler(StudentList.this);
    ArrayList<Exam> examarray = new ArrayList<>();
    examarray = db.getExamDetails();

    desuNoto = new String[examarray.size()];
    int i = 0;
    for (Exam examarrays : examarray){

      Log.i("QR datasss: ", examarrays.getQr_data());
      String examvalue = examarrays.getQr_data();
      try {
        JSONObject json = new JSONObject(examvalue);
        desuNoto[i] = json.getString("FirstName") + " " + json.getString("Lastname");

      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      i = i+1;
    }
    recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    setupRecyclerView();
  }



  @Override public boolean onCreateOptionsMenu(Menu menu) {
    final MenuInflater inflater = getMenuInflater();


    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();
    if (id == android.R.id.home) {
      finish();
    }

    return super.onOptionsItemSelected(item);
  }

  private void setupRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    setContactsAdapter(desuNoto);
  }

  private void setContactsAdapter(String[] array) {
    recyclerView.setAdapter(
        new StudentListAdapter(this,array));
  }

  private void setCountriesAdapter(String[] array) {
    recyclerView.setAdapter(
        new SimpleStringRecyclerViewAdapter(this, Arrays.asList(array), COUNTRIES));
  }

  public static class SimpleStringRecyclerViewAdapter
      extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<String> mValues;
    private int[] mMaterialColors;
    private int mType;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
      public String mBoundString;

      public final View mView;
      public final MaterialLetterIcon mIcon;
      public final TextView mTextView;

      private ItemClickListener clickListener;
      public ViewHolder(View view) {
        super(view);
        mView = view;
        mIcon = (MaterialLetterIcon) view.findViewById(R.id.icon);
        mTextView = (TextView) view.findViewById(R.id.text1);
      }

      @Override public String toString() {
        return super.toString() + " '" + mTextView.getText();
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

    public String getValueAt(int position) {
      return mValues.get(position);
    }

    public SimpleStringRecyclerViewAdapter(Context context, List<String> items, int type) {

      this.mContext = context;
      context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
      mMaterialColors = context.getResources().getIntArray(R.array.colors);
      mBackground = mTypedValue.resourceId;
      mValues = items;
      mType = type;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view =
          LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
      view.setBackgroundResource(mBackground);


      return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(final ViewHolder holder, int position) {
      switch (mType) {
        case CONTACTS:
          holder.mIcon.setInitials(true);
          holder.mIcon.setInitialsNumber(2);
          holder.mIcon.setLetterSize(18);
          break;
        case COUNTRIES:
          holder.mIcon.setLettersNumber(3);
          holder.mIcon.setLetterSize(16);
          holder.mIcon.setShapeType(Shape.RECT);
          break;
      }
      holder.mBoundString = mValues.get(position);
      holder.mIcon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
      holder.mTextView.setText(mValues.get(position));
      holder.mIcon.setLetter(mValues.get(position));

      holder.setClickListener(new ItemClickListener() {
        @Override
        public void onClick(View view, int position, boolean isLongClick) {
          if (isLongClick) {

            Toast.makeText(mContext, "#" + position + " - " + desuNoto[position] + " (Long click)", Toast.LENGTH_SHORT).show();
          } else {

            Toast.makeText(mContext, "#" + position + " - " + desuNoto[position], Toast.LENGTH_SHORT).show();
          }
        }
      });
    }

    @Override public int getItemCount() {
      return mValues.size();
    }
  }
}
