package com.example.mdc.news;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.likhitha.news.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by likhitha on 4/14/2017.
 */

public class VerticlePagerAdapter extends PagerAdapter {
  //  private DataBaseHelper db;


    String mimage[] = new String[100]; //= {"http://telugu.oneindia.com/img/2016/10/17-1445093418-supremecourt-03-1475483824.jpg","http://telugu.oneindia.com/img/2016/10/ambati-rambabu-new-687-03-1475484392.jpg","http://telugu.oneindia.com/img/2016/10/03-1475464620-jayalalitha7543-03-1475485869.jpg","http://telugu.oneindia.com/img/2016/10/amaravati-855-03-1475486462.jpg"};
    String[] mtitle = new String[100]; //= {"కావేరి: తీర్పు పరిశీలించాలని సుప్రీంలో కేంద్రం అర్జీ","ప్రభుత్వాన్ని ప్రశ్నించే నేతలపై చంద్రబాబు అస్త్రం: మండిపడ్డ అంబటి","జయలలిత ఆరోగ్యంపై క్లారిటీ ఇచ్చిన జర్నలిస్ట్!","అమరావతి తాత్కాలిక సచివాలయంలో మళ్లీ కూల్చివేతలు"};
    String mResources[] = new String[100]; //= {"న్యూఢిల్లీ: కావేరీ జలాల పంపిణి వివాదానికి సంబంధించి కావేరీ మేనేజ్ మెంట్ బోర్డును ఏర్పాటు చెయ...", "హైదరాబాద్: ముఖ్యమంత్రి చంద్రబాబు నాయుడు నిరంకుశంగా వ్య&zwnj;వ&zwnj;హ&zwnj;రిస్తున్నార&zwnj;ని, మ&zwnj;నం ప్ర&zwnj;...", "చెన్నై: తమిళనాడు ముఖ్యమంత్రి, అన్నాడీఎంకే అధినేత్రి జయలలితకు ఆరోగ్యం పైన సస్పెన్స్ కొనసాగుతో...", "అమరావతి : వచ్చే డిసెంబర్ నాటికి తాత్కాలిక సచివాలయ నిర్మాణాలు పూర్తి చేసి రాబోయే అసెంబ్లీ శీతా..."};

    ArrayList<String> atitle = new ArrayList<String>();
    ArrayList<String> aimage = new ArrayList<String>();
    //ArrayList<Bitmap> aimage = new ArrayList<Bitmap>();
    ArrayList<String> adetails = new ArrayList<String>();
    ArrayList<String> adatetime = new ArrayList<String>();
    ArrayList<String> aurl = new ArrayList<String>();
    private DatabaseReference childRef;
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<News> newsws = new ArrayList<News>();
    ImageView imageView;
    Cursor cursor;
    Bitmap bitmap = null;
    ViewGroup container;
    DatabaseReference db;
    int position;
    public VerticlePagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //db = new DataBaseHelper(context);
        Log.e("hi","vew");
        db = FirebaseDatabase.getInstance().getReference();
        childRef = db.child("News");
    //    Firebase ref = new Firebase(Config.FIREBASE_URL);
       // Firebase cities = ref.child("News");

        Log.e("hi","vew");

        childRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                List<String> lst = new ArrayList<String>();
                for (com.google.firebase.database.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                  //  for (int i = 1; i < postSnapshot.getChildrenCount(); i++) {
                        //Getting the data from snapshot

                        News news = postSnapshot.getValue(News.class);
                        atitle.add(news.getTitle());
                        adetails.add(news.getDescription());
                        adatetime.add(news.getDate());
                        aimage.add(news.getImage());
                        aurl.add(news.getUrl());
                       // newsws.add(news);
                 //   }
                    //lst.add(postSnapshot.getValue().toString());
                    // db.inserData(title, imageView, label, datetimeview, url);
                    // String value = "title: " + news.getTitle() + "\ndate: " + news.getDate() + "\ndescription: " + news.getDescription() + "\n id :" + news.getId() + "\n image:" + news.getImage() + "\n url :" + news.getUrl();
                }

                Log.e("title", String.valueOf(atitle));
                Log.e("title", String.valueOf(adetails));
                Log.e("title", String.valueOf(adatetime));
                Log.e("title", String.valueOf(aimage));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







      /*  cursor = db.getAllNews();


        while (cursor.moveToNext()) {
            atitle.add(cursor.getString(cursor.getColumnIndexOrThrow(db.KEY_TITLE)));
            adetails.add(cursor.getString(cursor.getColumnIndexOrThrow(db.KEY_DESCRIPTION)));
            adatetime.add(cursor.getString(cursor.getColumnIndexOrThrow(db.KEY_DATETIME)));
            aimage.add(cursor.getString(cursor.getColumnIndexOrThrow(db.KEY_IMAGE)));
            url.add(cursor.getString(cursor.getColumnIndexOrThrow(db.KEY_LINK)));
        }*/


    }

    @Override
    public int getCount() {
        Log.e("hi","hi");
        return 7;
         }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((LinearLayout) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Log.e("hi","test");
        final View itemView = mLayoutInflater.inflate(R.layout.content_news, container, false);
        Log.e("hi","test");
        final TextView title = (TextView) itemView.findViewById(R.id.textTitle);

        final TextView label = (TextView) itemView.findViewById(R.id.textView);
        final TextView datetimeview = (TextView) itemView.findViewById(R.id.datetimeview);
        final TextView url = (TextView) itemView.findViewById(R.id.textlink);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Log.e("hi","test");

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(aurl.get(position)));
                itemView.getContext().startActivity(intent);
            }
        });

        try {
            new DownloadImageFromInternet((ImageView) itemView.findViewById(R.id.imageView))

                    .execute(aimage.get(position));


            label.setText(adetails.get(position));
            Log.e("label", String.valueOf(label));
            title.setText(atitle.get(position));


         //   url.setText(aurl.get(position));

            //imageView.setImageBitmap(aimage.get(position));
            datetimeview.setText((adatetime.get(position).replace("&zwnj;", "")));
        } catch (Exception e) {
            /*String url = Config.DATA_URL;
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    showJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //.makeText(getApplicationContext(),error.getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);



        }*/
            //Firebase.setAndroidContext(VerticlePagerAdapter.this);

            Log.w("myApp", "error");
        }
        container.addView(itemView);

        return itemView;
    }


   /* private void showJSON(String response){
        DataBaseHelper db = new DataBaseHelper(mContext);
        String title="";
        String image="";
        String details="";
        String datetime="";
        Bitmap img=null;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            // BackTask bt = new BackTask();

            for(int i=0; i<result.length(); i++) {
                JSONObject collegeData = result.getJSONObject(i);
                title = collegeData.getString(Config.KEY_TITLE);
                image = collegeData.getString(Config.KEY_IMAGE);
                details = collegeData.getString(Config.KEY_DETAILS);
                datetime = collegeData.getString(Config.KEY_DATETIME);
                // bt.execute(image, title, details, datetime);
                db.inserData(title, image, details, datetime);

            }
            db.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }*/


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(mContext, "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }


    }

}
