package ru.myitschool.vkcase;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.myitschool.vkcase.databinding.FragmentFriendsBinding;

public class FriendsFragment extends Fragment {
    private final String TAG = getTag();
    private FragmentFriendsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        return binding.getRoot();
    }
    @SuppressLint("ResourceType")
    private void getContacts(){
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        ArrayList<LinearLayout> contacts = new ArrayList();
        while (cur.moveToNext()){
            String name = cur.getString(cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));
            String icon = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
            LinearLayout contact = (LinearLayout) getLayoutInflater().inflate(R.layout.contact, null, false);
            if (icon == null) {
                ((ImageView) contact.findViewById(R.id.contact_image)).setImageResource(R.drawable.friend_icon);
            }
            else {
                ((ImageView) contact.findViewById(R.id.contact_image)).setImageURI(Uri.parse(icon));
            }
            ((ImageView) contact.findViewById(R.id.contact_image)).getLayoutParams().width = 200;
            ((ImageView) contact.findViewById(R.id.contact_image)).getLayoutParams().height = 200;
            ((TextView) contact.findViewById(R.id.contact_name)).setText(name);
            contacts.add(contact);
        }
        MyAdapter arrayAdapter = new MyAdapter(getActivity(), R.layout.contact, contacts);
        binding.contacts.setAdapter(arrayAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull @NonNull String[] permissions, @android.support.annotation.NonNull @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, Arrays.toString(grantResults));
        if (grantResults.length > 0 && grantResults[0] >= 0) {
            getContacts();
        }
    }
    class MyAdapter extends ArrayAdapter{
        List<LinearLayout> contacts;

        public MyAdapter(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);
            contacts = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return contacts.get(position);
        }
    }
}