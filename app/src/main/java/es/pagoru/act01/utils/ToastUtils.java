package es.pagoru.act01.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Pablo on 02/02/2017.
 */

public class ToastUtils {

    public static void make(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
