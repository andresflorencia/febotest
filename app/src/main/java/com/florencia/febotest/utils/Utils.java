package com.florencia.febotest.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Utils {
    public static void showMessage(Context context, String Message){
        try{
            Toast.makeText(context, Message, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(context, "MostrarError(): "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public static void showMessageShort(Context context, String Message){
        try{
            Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "MostrarError(): "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void GoToUrl(Context c, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(uri, "text/html");
            //i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            c.startActivity(i);
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());
        }
    }
}
