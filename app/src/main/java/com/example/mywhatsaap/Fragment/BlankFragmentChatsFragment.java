package com.example.mywhatsaap.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mywhatsaap.Addapters.UserAdapter;
import com.example.mywhatsaap.Modale.User;
import com.example.mywhatsaap.R;
import com.example.mywhatsaap.databinding.FragmentBlankChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;


public class BlankFragmentChatsFragment extends Fragment {
    FragmentBlankChatsBinding binding;
    public BlankFragmentChatsFragment()
    {

    }
    ArrayList<User>list=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentBlankChatsBinding.inflate(inflater,container,false);
        UserAdapter adapter=new UserAdapter(list ,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.chatRecyer.setLayoutManager(layoutManager);
        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        getUser();

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user=dataSnapshot.getValue(User.class);
                   user.getUserId(dataSnapshot.getKey());
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }

    private void getUser() {
        list.add(new User("11","suresh","ghuggg"));
    }
}