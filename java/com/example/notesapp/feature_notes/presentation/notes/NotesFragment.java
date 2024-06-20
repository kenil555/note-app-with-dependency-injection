package com.example.notesapp.feature_notes.presentation.notes;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.notesapp.R;
import com.example.notesapp.core.PreferenceWrapper;
import com.example.notesapp.databinding.FragmentNotesBinding;
import com.example.notesapp.feature_notes.domain.model.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotesFragment extends Fragment {
    private FragmentNotesBinding binding;
    private NotesViewModel notesViewModel;
    private NotesListRVAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        initalizeNotesListRv();

//        // retrieving email from preferences so that notes related to only that email can be shown
//        String email = getEmailFromPreferences();
//        if(email != null && email.length() > 0){
//            notesViewModel.getNotesList(email);
//        }
//        else{
//            Toast.makeText(requireContext(), "User logged out!!!", Toast.LENGTH_SHORT).show();
//            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container);
//            navController.navigate(R.id.action_notesFragment_to_signInFragment2);
//        }
        observeNotesList();

        binding.addNoteNotesFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass any random value as id coz it's not needed
                navigateTOAddEditNotes(-1);
            }
        });

        binding.signOutBtnNotesFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutUser();
            }
        });

    }
    private void observeNotesList(){
        notesViewModel.noteList.observe(getViewLifecycleOwner(), notes->{
            if(notes != null){
                Log.e("NotesApp" , "List Size -> " + notes.size());
                adapter.submitList(notes);
            }
        });
    }
    private String getEmailFromPreferences(){
        PreferenceWrapper preference = new PreferenceWrapper(requireContext());
        return preference.getStringFromPreference("userEmail" , "");
    }
    private void navigateTOAddEditNotes(int id){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container);
        Bundle bundle = new Bundle();
        bundle.putInt("Note_Id" , id);
        navController.navigate(R.id.action_notesFragment_to_addEditNote, bundle);
    }
    private void initalizeNotesListRv(){
        adapter = new NotesListRVAdapter(this::navigateTOAddEditNotes, this::deleteNoteItem);
        binding.rvNotesFragment.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        );
        binding.rvNotesFragment.setAdapter(adapter);
    }

    private void signOutUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user !=  null){
            FirebaseAuth.getInstance().signOut();
        }

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container);
        navController.navigate(R.id.action_notesFragment_to_signInFragment2);
    }
    private void deleteNoteItem(Note note){
        notesViewModel.onEvent(new NotesEvent.Delete(note));
    }
}