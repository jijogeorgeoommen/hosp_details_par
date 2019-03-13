package com.example.hosp_details_par;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;
    RequestParams params;

    ArrayList<String> hosp;
    ArrayList<String>hospnamelist;
    ArrayList<String>hospnumberlist;
    ArrayList<String>maillist;
    ArrayList<String>addlist;
    ArrayList<String>citylist;

    ListView listview;
    LayoutInflater inflate;

    String url="http://srishti-systems.info/projects/smartambulance/viewhosp.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview=findViewById(R.id.lviewhosp);

        client=new AsyncHttpClient();
        params=new RequestParams();

        hosp=new ArrayList<>();
        hospnamelist=new ArrayList<>();
        hospnumberlist=new ArrayList<>();
        maillist=new ArrayList<>();
        addlist=new ArrayList<>();
        citylist=new ArrayList<>();

        Log.e("In","Out");

        client.get(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try{
                    Log.e("inn","outt");
                    JSONObject jobjmain=new JSONObject(content);
                    if (jobjmain.getString("status").equals("success")){
                        JSONArray jarray=jobjmain.getJSONArray("Hospital_details");
                        for(int i=0;i<jarray.length();i++){
                            JSONObject jobj=jarray.getJSONObject(i);
                            String nam=jobj.getString("name");
                            hospnamelist.add("Name :"+nam);
                            String num=jobj.getString("contact");
                            hospnumberlist.add("Number :" +num);
                            String mail=jobj.getString("mail");
                            maillist.add("Email :"+mail);
                            String add=jobj.getString("address");
                            addlist.add("Address :"+add);
                            String city=jobj.getString("city");
                            citylist.add("City :"+city);




                        }

                    }
                    adapter adp=new adapter();
                    listview.setAdapter(adp);


                }catch (Exception e ){

                }
            }
        });
    }

    class adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return hospnamelist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflate=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflate.inflate(R.layout.lviewhospxml,null);
            TextView name=convertView.findViewById(R.id.hospnamexml);
            name.setText(hospnamelist.get(position));
            TextView number=convertView.findViewById(R.id.hospnumberxml);
            number.setText(hospnumberlist.get(position));
            TextView maill=convertView.findViewById(R.id.hospmailxml);
            maill.setText(maillist.get(position));
            TextView address=convertView.findViewById(R.id.hospaddxml);
            address.setText(addlist.get(position));
            TextView cityy=convertView.findViewById(R.id.hospcityxml);
            cityy.setText(citylist.get(position));

            return convertView;
        }
    }
}
