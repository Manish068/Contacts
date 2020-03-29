package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.contacts.Adapters.MyAdapter;
import com.example.contacts.Models.MyContacts;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =(RecyclerView)findViewById(R.id.recycler_view_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadContacts();
    }

    private void loadContacts() {
        Cursor cursor=getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.NUMBER);
        ArrayList<MyContacts> arrayList=new ArrayList<>();
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                String id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (number.length()>0){
                    Cursor phonecursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                    +"=?",new  String[]{id},null);

                    if(phonecursor.getCount()>0){
                        while(phonecursor.moveToNext()){
                            String phonenumberValue=phonecursor.getString(phonecursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            MyContacts myContacts=new MyContacts(name,phonenumberValue);
                            arrayList.add(myContacts);
                        }
                    }
                    phonecursor.close();
                }
            }
            //initialize the adapter and set it to recyclerview
            MyAdapter myAdapter=new MyAdapter(this,arrayList);
            recyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

        }

        else{
            Toast.makeText(getApplicationContext(),"No Contacts Found",Toast.LENGTH_LONG).show();
        }

    }
}
