package com.example.assignment_3.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.assignment_3.R;

public class EnterCode extends DialogFragment {
    private EditText editCode;
    private EnterVertifyCode listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_entercode,null);
        builder.setView(view).setTitle("Enter the Verification Code From your Email").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String code = editCode.getText().toString();
                listener.applyCode(code);
            }
        });
        editCode = view.findViewById(R.id.txt_entercode_code);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (EnterVertifyCode) context;
        }catch (ClassCastException ex){
            throw new ClassCastException(context.toString() + "Must implement code enter listener");
        }
    }

    public interface EnterVertifyCode{
        void applyCode(String code);
    }
}
