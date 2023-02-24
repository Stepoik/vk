package ru.myitschool.vkcase;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import ru.myitschool.vkcase.databinding.ActivityMainBinding;
import ru.myitschool.vkcase.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    private FragmentMainBinding binding;
    private boolean mic = false;
    private boolean video = false;
    private Context context;
    private boolean swapped = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        context = getActivity();
        binding.friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FriendsFragment()).addToBackStack(null).commit();
            }
        });
        binding.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messageIntent = new Intent(Intent.ACTION_VIEW);
                messageIntent.setData(Uri.parse("sms:"));
                startActivity(messageIntent);
            }
        });
        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapViews();
            }
        });
        binding.microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mic) {
                    binding.microphone.setImageResource(R.drawable.microphone_icon);
                    binding.fUserName.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }
                else{
                    binding.microphone.setImageResource(R.drawable.microphone_slash_icon);
                    binding.fUserName.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icons, 0);
                }
                mic = !mic;
            }
        });
        binding.video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!video){
                    binding.video.setImageResource(R.drawable.video_icon);
                }
                else{
                    binding.video.setImageResource(R.drawable.video_slash_icon);
                }
                video = !video;
            }
        });
        binding.hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setMessage("привет").show();
            }
        });
        binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return binding.getRoot();
    }
    private void swapViews(){
        ConstraintLayout fUser = binding.fId;
        ConstraintLayout sUser = binding.sId;
        if (!swapped) {
            binding.users.removeAllViews();
            binding.users.addView(sUser);
            binding.users.addView(fUser);
        }
        else{
            binding.users.removeAllViews();
            binding.users.addView(fUser);
            binding.users.addView(sUser);
        }
        swapped = !swapped;
    }
}