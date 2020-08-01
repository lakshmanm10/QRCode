package com.example.student;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.student.database.DB_Handler;
import com.example.student.model.Exam;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    public static String STR;
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 700;
    public final static int HEIGHT = 700;

    private SecretKeySpec keyObj;
    private DB_Handler db;
    ImageView imgGen;
    TextView value;
    ImageButton sync;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning);
        final EditText qrgen = (EditText) findViewById(R.id.qrtext);
        final EditText qrfname = (EditText) findViewById(R.id.qrfname);
        final EditText qrlname =  (EditText) findViewById(R.id.qrlname);
        final EditText qrdob =  (EditText) findViewById(R.id.qrdob);
        final EditText qrfather =  (EditText) findViewById(R.id.qrfather);
        Button qrgenerate = (Button) findViewById(R.id.btn_scan);
        Button qrscan = (Button) findViewById(R.id.btn_scan);
        value = (TextView) findViewById(R.id.qrtxt);
        imgGen = (ImageView) findViewById(R.id.imgGen);
        sync = (ImageButton) findViewById(R.id.sync);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    DB_Handler db = new DB_Handler(MainActivity.this);
                    ArrayList<Exam> examarray = new ArrayList<>();
                    examarray = db.getExamDetails();
                    JSONObject json = new JSONObject();
                    JSONArray jsonArr = new JSONArray();

                    for (Exam examarrays : examarray) {
                        Log.i("QR datasss: ", examarrays.getService_data());
                        jsonArr.put(examarrays.getService_data());
                    }
                    json.put("data", jsonArr);

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            // Create URL
                            try {
                           //Create Connection
                            }
                            catch (Exception e)
                            {

                            }
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


        db = new DB_Handler(this);
        qrscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DecoderActivity.class);
                startActivity(i);

            }
        });
        /* QR Generation Code 
        qrgenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            STR = qrgen.getText().toString();
            try{
             //   JSONArray jArray = new JSONArray();
               // JSONObject jObjectType = new JSONObject();

                // put elements into the object as a key-value pair




                // 2nd array for user information
                JSONObject jObjectData = new JSONObject();

//                int resID = getResources().getIdentifier("image", "drawable",  getPackageName());
//                Drawable drawable = getResources().getDrawable(resID);



                Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                        R.drawable.qr);

                // Set the Image in ImageView after decoding the String
//                image.setImageBitmap(BitmapFactory
//                        .decodeFile(img_Decodable_Str));


                try {
                    // Download Image from URL
                    // Decode Bitmap


//here

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    Log.e("encoded",encoded);
                    int width = bmp.getWidth(), height = bmp.getHeight();
                    int[] pixels = new int[width * height];
                    bmp.getPixels(pixels, 0, width, 0, 0, width, height);
                    bmp.recycle();
                    bmp = null;
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    MultiFormatReader reader = new MultiFormatReader();

                    Result result = null;
                    try {
                        result = reader.decode(bBitmap);
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String text = result.getText();
                    Log.e("texttttt",text);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }










                    Bitmap b = BitmapFactory.decodeResource(getResources(),
                        R.drawable.image);
              //  Bitmap  bmp = BitmapFactory.decodeFile(resID);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] byteArray1 = bos .toByteArray();
                String encoded1 = Base64.encodeToString(byteArray1, Base64.DEFAULT);
                Log.d("length 100" , "len"+encoded1.length());
                ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 20, bos1);
                byte[] byteArrays = bos1 .toByteArray();
                String encodeds = Base64.encodeToString(byteArrays, Base64.DEFAULT);
                Log.d("length 50" , "len"+encodeds.length());

                byte[] decodedString = Base64.decode(encodeds, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgGen.setImageBitmap(decodedByte);
                ImageView imageview = (ImageView) findViewById(R.id.imgGen);
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                imageview.setColorFilter(filter);

                imageview.buildDrawingCache();
                Bitmap bmps = imageview.getDrawingCache();
                ByteArrayOutputStream boss = new ByteArrayOutputStream();
                bmps.compress(Bitmap.CompressFormat.JPEG, 100, boss);
                byte[] byteArray1s = bos .toByteArray();
                String encoded1s = Base64.encodeToString(byteArray1s, Base64.DEFAULT);
                Log.d("encoded1s 50" , "len"+encoded1s.length());

                InputStream in = new ByteArrayInputStream(bos.toByteArray());
               // ContentBody foto = new InputStreamBody(in, "image/jpeg", "filename");

                // Create Json Object using Facebook Data
                jObjectData.put("photo","V/3ogfXp1rWlMc6awJjiAAd2fm4ogXjz56aypOoIde4OE5u/F9x199dlXnnGiHZWEYbGpsAEA3QXYnHwEFliKAgswgJ8LPeiUXGwedCAKABACCN+EA1pYIIYaFlcDhytd51sGAJbo3onOpajiihlO92KHGaUXGwWjUBChjSPiWJuOO/LYIm4v1tXfE6J4gCSJEZ7YgRYUNrkji9P55sF/ogxw5ZkSqIDaZBV6aSGYq/lGZplndkckZ98xoICbTcIJGQAZcNmdmUc210hs35nCyJ58fgmIKX5RQGOZowxaZwYA+JaoKQwswGijBV4C6SiTUmpphMspJx9unX4KaimjDv9aaXOEBteBqmuuxgEHoLX6Kqx+yXqqBANsgCtit4FWQAEkrNbpq7HSOmtwag5w57GrmlJBASEU18ADjUYb3ADTinIttsgSB1oJFfA63bduimuqKB1keqwUhoCSK374wbujvOSu4QG6UvxBRydcpKsav++Ca6G8A6Pr1x2kVMyHwsVxUALDq/krnrhPSOzXG1lUTIoffqGR7Goi2MAxbv6O2kEG56I7CSlRsEFKFVyovDJoIRTg7sugNRDGqCJzJgcKE0ywc0ELm6KBCCJo8DIPFeCWNGcyqNFE06ToAfV0HBRgxsvLThHn1oddQMrXj5DyAQgjEHSAJMWZwS3HPxT/QMbabI/iBCliMLEJKX2EEkomBAUCxRi42VDADxyTYDVogV+wSChqmKxEKCDAYFDFj4OmwbY7bDGdBhtrnTQYOigeChUmc1K3QTnAUfEgGFgAWt88hKA6aCRIXhxnQ1yg3BCayK44EWdkUQcBByEQChFXfCB776aQsG0BIlQgQgE8qO26X1h8cEUep8ngRBnOy74E9QgRgEAC8SvOfQkh7FDBDmS43PmGoIiKUUEGkMEC/PJHgxw0xH74yx/3XnaYRJgMB8obxQW6kL9QYEJ0FIFgByfIL7/IQAlvQwEpnAC7DtLNJCKUoO/w45c44GwCXiAFB/OXAATQryUxdN4LfFiwgjCNYg+kYMIEFkCKDs6PKAIJouyGWMS1FSKJOMRB/BoIxYJIUXFUxNwoIkEKPAgCBZSQHQ1A2EWDfDEUVLyADj5AChSIQW6gu10bE/JG2VnCZGfo4R4d0sdQoBAHhPjhIB94v/wRoRKQWGRHgrhGSQJxCS+0pCZbEhAAOw==");
                jObjectData.put("RollNo", qrgen.getText().toString());
                jObjectData.put("first_name", qrfname.getText().toString());
                jObjectData.put("last_name", qrlname.getText().toString());
                jObjectData.put("dob", qrdob.getText().toString());
                jObjectData.put("father", qrfather.getText().toString());
                jObjectData.put("rollhash", "");
               // jArray.put(jObjectData);
                STR = jObjectData.toString();
               // value.setText(STR);
                byte[] str = encryptText(STR);
                String encryptedValue = Base64.encodeToString(str,Base64.DEFAULT);

               Bitmap bitmap = encodeAsBitmap(encryptedValue);


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

//                    byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                    imgGen.setImageBitmap(decodedByte);



                    String content = encoded;
                    File f;
                    File file = null;
                    FileOutputStream outputStream;
                    BufferedReader input = null;


                    try {
                        String filepath = getFilesDir().getAbsolutePath() + File.separator + "qrimages";
                        File dir = new File(filepath);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File[] files = dir.listFiles();




                            f = new File(getFilesDir().getAbsolutePath() + File.separator + "qrimages", "file");

                            outputStream = new FileOutputStream(f);
                            outputStream.write(content.getBytes());
                            outputStream.close();



                    } catch (IOException e) {
                        e.printStackTrace();
                    }



//                    bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                    File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");
//
//                    if (!direct.exists()) {
//                        File wallpaperDirectory = new File("/sdcard/DirName/");
//                        wallpaperDirectory.mkdirs();
//                    }
//                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                    File file = new File(new File("/sdcard/DirName/"), "image"+currentDateTimeString+".jpg");
//                    if (file.exists()) {
//                        file.delete();
//                    }
//
//                    try {
//                        FileOutputStream out = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
//                        out.flush();
//                        out.close();
//
//                    } catch (Exception e) {
//                        System.out.println("1st exception reached"+e);
//                        e.printStackTrace();
//                    }


                    //   MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "oa" , "image");
                } catch (WriterException e) {

                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        */
    }
    Bitmap encodeAsBitmap(String str) throws WriterException {
        Map<EncodeHintType, Object> hints = null;
        //  String encoding = guessAppropriateEncoding(contentsToEncode);
        hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        //hints.put(EncodeHintType.CHARACTER_SET, encoding);
        hints.put(EncodeHintType.MARGIN, 0); /* default = 4 */

        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {

        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

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



    public static byte[] encryptText(String plainText) throws Exception {
        String proposedKey="PUBLICEXAMBOARD$";

        Cipher aesCipher = Cipher.getInstance("AES");

        SecretKey secretKey = new SecretKeySpec(proposedKey.getBytes(), "AES");
        aesCipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());

        return byteCipherText;
    }

    public void Extract(String s)
    {
        byte[] finalbyte;

            try {
                JSONObject json = new JSONObject(s);
                byte[] dta= (byte[]) json.get("Photograph");
                System.out.println("dta    "+dta);
                finalbyte=decompress(dta);
                Bitmap bmp= BitmapFactory.decodeByteArray(finalbyte,0,finalbyte.length);

                imgGen.setImageBitmap(bmp);
            } catch (DataFormatException e) {
                // TODO Auto-generated catch block
                System.out.println("DataFormatException "+e.getMessage());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


    }
    public byte[] decompress(byte[] data) throws IOException, DataFormatException {
        ByteArrayOutputStream baos = null;
        Inflater iflr = new Inflater();
        iflr.setInput(data);
        baos = new ByteArrayOutputStream();
        byte[] tmp = new byte[4*1024];
        try{
            while(!iflr.finished()){
                int size = iflr.inflate(tmp);
                baos.write(tmp, 0, size);
            }
        } catch (Exception ex){

        } finally {
            try{
                if(baos != null) baos.close();
            } catch(Exception ex){}
        }

        return baos.toByteArray();



    }


    public static Bitmap test(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);
                // use 128 as threshold, above -> white, below -> black
                if (gray > 128) {
                    gray = 255;
                }
                else{
                    gray = 0;
                }
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return bmOut;
    }



}
