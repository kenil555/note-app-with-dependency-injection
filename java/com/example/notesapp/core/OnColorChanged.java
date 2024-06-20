package com.example.notesapp.core;

import android.content.Context;

import com.example.notesapp.R;

public class OnColorChanged {
    public static int onColorChanged(Context context, String colorName){
        int color ;
        switch (colorName){
            case "RedOrange":
               color = context.getColor(R.color.RedOrange);
               break;
            case "RedPink":
                color = context.getColor(R.color.RedPink);
                break;
            case "BabyBlue":
                color =  context.getColor(R.color.BabyBlue);
                break;
            case "Violet":
                color = context.getColor(R.color.Violet);
                break;
            case "LightGreen":
                color = context.getColor(R.color.LightGreen);
                break;
            default:
                color = context.getColor(R.color.RedOrange);
        };

        return color;
    }
}
