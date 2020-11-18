package com.ruslangrigoriev.weather.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.ruslangrigoriev.weather.R;

public class EnterCityDialog extends DialogFragment {

    private Button dialogOkBtn;
    private EditText dialogCityNameET;
    private ConstraintLayout dialogCL;

    private DialogListener dialogListener;

    private boolean isDay = true;

    public EnterCityDialog(boolean isDay) {
        this.isDay = isDay;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setHorizontalGravity(Gravity.CENTER);
        View view = inflater.inflate(R.layout.dialog_enter_city, linearLayout);
        dialogCL = view.findViewById(R.id.dialog_CL);
        dialogCityNameET = view.findViewById(R.id.dialogCityNameET);
        dialogOkBtn = view.findViewById(R.id.okBtn);
        if (!isDay) {
            dialogCL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.dialog_night_background));
            dialogCityNameET.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.dialog_night_enter_text));
            dialogOkBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.dialog_night_button));
        } else {
            dialogCL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.dialog_day_background));
            dialogCityNameET.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.dialog_day_enter_text));
            dialogOkBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.dialog_day_button));
        }
        dialogOkBtn.setOnClickListener(v -> dialogListener.onDialogOkBtnClick(dialogCityNameET.getText().toString(),getDialog()));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        return alertDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface DialogListener {
        void onDialogOkBtnClick(String cityName, Dialog dialog);
    }
}
