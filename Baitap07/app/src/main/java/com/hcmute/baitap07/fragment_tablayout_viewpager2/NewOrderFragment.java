package com.hcmute.baitap07.fragment_tablayout_viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcmute.baitap07.R;
import com.hcmute.baitap07.databinding.FragmentNeworderBinding;

public class NewOrderFragment extends Fragment {

    FragmentNeworderBinding binding;
    public NewOrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNeworderBinding.inflate(inflater,container,false);

        //recycleView
        return binding.getRoot();
    }
}