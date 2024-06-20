package com.example.notesapp.feature_notes.presentation.savenotes;

import static com.example.notesapp.feature_notes.domain.model.Note.NOTE_COLORS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.notesapp.R;
import com.example.notesapp.core.OnColorChanged;
import com.example.notesapp.core.PreferenceWrapper;
import com.example.notesapp.databinding.FragmentAddEditNoteBinding;
import com.example.notesapp.feature_notes.domain.model.Note;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddEditNoteFragment extends Fragment {

    private FragmentAddEditNoteBinding binding;
    private AddEditViewModel viewModel;
    private ColorRVAdapter adapter ;
    private int currentNoteId = -1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AddEditViewModel.class);
        initalizeColorListRv();

        Bundle recievedBundle = getArguments();
        int noteId = -1;
        if(recievedBundle != null){
            noteId = recievedBundle.getInt("Note_Id");
        }

        observeChanges();

        if(noteId == -1){
            int randomColor = new Random().nextInt(NOTE_COLORS.length);
            String colorName = NOTE_COLORS[randomColor];

            viewModel.onEvent(new AddEditNoteEventClass.ColorChanged(colorName));

        }
        else {
            currentNoteId = noteId;
            viewModel.getNoteById(noteId);
        }

        saveNoteBtnClickListener();
    }
    private void saveNoteBtnClickListener() {
        binding.saveBtnAddEditFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = String.valueOf(binding.etTitle.getText());
                String content = String.valueOf(binding.etContent.getText());



                if (title.length() > 0 && content.length() > 0
                ) {
                    viewModel.onEvent(new AddEditNoteEventClass.EnteredTitle(title));
                    viewModel.onEvent(new AddEditNoteEventClass.EnteredContent(content));

                    PreferenceWrapper preference = new PreferenceWrapper(requireContext());
                    String email = preference.getStringFromPreference("userEmail", "");
                    viewModel.onEvent(new AddEditNoteEventClass.EmailChanged(email));

                    viewModel.onEvent(new AddEditNoteEventClass.SaveNote(createNote()));

                    navigateToNotesListFragment();
                } else {
                    showSnackBar("Empty Note cannot be saved!!!");
                }
            }
        });
    }
    private void navigateToNotesListFragment(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container);
        navController.navigate(R.id.action_addEditNote_to_notesFragment);
        navController.popBackStack(R.id.notesFragment, true);
    }
    private void observeChanges(){
        viewModel.note.observe(getViewLifecycleOwner(), note ->{
            changeColorOfBackground(note.Color);
            binding.etTitle.setText(note.Title);
            binding.etContent.setText(note.Content);
        });

        viewModel.colorName.observe(getViewLifecycleOwner(), colorName -> {
            binding.constraintLayoutAddEditFragment.setBackgroundColor(
                    OnColorChanged.onColorChanged(requireContext(), colorName)
            );
        });
    }
    private void initalizeColorListRv() {
        adapter = new ColorRVAdapter(this::changeColorOfBackground);
        binding.colorRvAddEditFragment.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
        ));
        binding.colorRvAddEditFragment.setAdapter(adapter);
        adapter.submitList(Arrays.asList(NOTE_COLORS));
    }
    private void showSnackBar(String msg){
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }
    private void changeColorOfBackground(String colorName){
        binding.constraintLayoutAddEditFragment.setBackgroundColor(
                OnColorChanged.onColorChanged(requireContext(), colorName)
        );
        viewModel.onEvent(new AddEditNoteEventClass.ColorChanged(colorName));
    }
    private Note createNote(){
        String title = viewModel.title.getValue();
        String content = viewModel.content.getValue();
        String emailId = viewModel.emailID.getValue();
        String colorName = viewModel.colorName.getValue();
        long timeStamp = System.currentTimeMillis();

        if(currentNoteId != -1){
            return new Note(
                    currentNoteId, title, content, emailId, timeStamp, colorName
            );
        }
        else {
            return new Note(
                    title, content, emailId, timeStamp, colorName
            );
        }
    }
}