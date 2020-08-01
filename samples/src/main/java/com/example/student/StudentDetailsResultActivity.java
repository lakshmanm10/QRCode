package com.example.student;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.student.database.DB_Handler;
import com.example.student.model.Exam;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class StudentDetailsResultActivity extends AppCompatActivity {

    TextView firstname;
    TextView lastname;
    TextView fathername;
    TextView rollno;
    TextView mobileno;
    TextView dob;
    TextView bodymark;
    TextView city;
    RelativeLayout moredetailsview;
    LinearLayout acceptmorebutton;
    LinearLayout acceptdecline;
    Button accept;
    Button acceptmore;
    Button decline;
    Button moredetails;
    String examevent;
    String testid;
    de.hdodenhof.circleimageview.CircleImageView profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_details_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstname = (TextView) findViewById(R.id.firstname_view);
        lastname = (TextView) findViewById(R.id.lastname_view);
        fathername = (TextView) findViewById(R.id.fathername_view);
        rollno = (TextView) findViewById(R.id.rollno_view);
        mobileno = (TextView) findViewById(R.id.mobileno_view);
        dob = (TextView) findViewById(R.id.dob_view);
        bodymark = (TextView) findViewById(R.id.permanentmark_view);
        city = (TextView) findViewById(R.id.city_view);
        profilepic = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profilepic);
        accept = (Button) findViewById(R.id.acceptfirst);
        acceptmore = (Button) findViewById(R.id.accept);
        moredetails = (Button) findViewById(R.id.moredetails);
        decline = (Button) findViewById(R.id.decline);
        acceptmorebutton = (LinearLayout) findViewById(R.id.acceptmore);
        acceptdecline = (LinearLayout) findViewById(R.id.acceptdecline);
        moredetailsview = (RelativeLayout) findViewById(R.id.moreconstraint);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptdecline("y");
            }
        });

        acceptmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptdecline("y");
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptdecline("n");
            }
        });
        moredetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moredetailsview.setVisibility(View.VISIBLE);
                acceptmorebutton.setVisibility(View.GONE);
                acceptdecline.setVisibility(View.VISIBLE);
            }
        });
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });
        setData();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void acceptdecline(String status)
    {
        try{
            JSONObject json = new JSONObject();
            json.put("FirstName",firstname.getText().toString());
            json.put("LastName",lastname.getText().toString());
            json.put("FatherName",fathername.getText().toString());
            json.put("RollNo",rollno.getText().toString());
            json.put("Mobile No",mobileno.getText().toString());
            json.put("DOB",dob.getText().toString());
            json.put("PermanentBody Mark",bodymark.getText().toString());
            json.put("Test City",city.getText().toString());
            json.put("ExamEvent",examevent);
            json.put("TestId",testid);
            if(status.equals("y")) {
                json.put("Status", "true");
            }
            else {
                json.put("Status","false");
            }
            JSONObject j = new JSONObject();
            j.put("RollNo",rollno.getText().toString());
            j.put("ExamEvent",examevent);
            j.put("FirstName",firstname.getText().toString());
            j.put("Test City",city.getText().toString());
            j.put("TestId",testid);
            if(status.equals("y")) {
                j.put("Status", "true");
            }
            else {
                j.put("Status","false");
            }
            Log.e("jsonvalue",json.toString());
            Exam exam = new Exam();
            exam.setQr_data(json.toString());
            exam.setService_data(j.toString());

            exam.setStatus(status);
            DB_Handler db = new DB_Handler(StudentDetailsResultActivity.this);
            db.insertqr(exam);
            ArrayList<Exam> examarray = new ArrayList<>();
            examarray = db.getExamDetails();
            for (Exam examarrays : examarray){
                Log.i("QR datasss: ", examarrays.getService_data());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finish();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Bitmap image = (Bitmap) data.getExtras().get("data");

            profilepic.setImageBitmap(image);
        }
    }
    public void setData()
    {
        String qrvalue = getIntent().getStringExtra("qrvalue");
        String flagvalue = getIntent().getStringExtra("FLAGVALUE");
        if(flagvalue.equals("0")){
            moredetailsview.setVisibility(View.VISIBLE);
            acceptmorebutton.setVisibility(View.GONE);
            acceptdecline.setVisibility(View.GONE);
        }

        Exam exam  = new Exam();

        try {


            byte[] ss = Base64.decode(qrvalue, Base64.DEFAULT);
            qrvalue= decryptText(ss);
           Log.d("decrypted", qrvalue);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            JSONObject j = new JSONObject(qrvalue);
            firstname.setText(j.getString("FirstName"));
            lastname.setText(j.getString("LastName"));
            fathername.setText(j.getString("FatherName"));
            rollno.setText(j.getString("RollNo"));
            mobileno.setText(j.getString("Mobile No"));
            dob.setText(j.getString("DOB"));
            bodymark.setText(j.getString("PermanentBody Mark"));
            city.setText(j.getString("Test City"));
            examevent = j.getString("ExamEvent");
            testid = j.getString("TestId");

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String decryptText(byte[] byteCipherText) throws Exception {

            Cipher aesCipher = Cipher.getInstance("AES");
            String proposedKey="PUBLICEXAMBOARD$";
            byte[] b = proposedKey.getBytes();
            SecretKey secretKey = new SecretKeySpec(b, "AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
            return new String(bytePlainText);
        }


    }
