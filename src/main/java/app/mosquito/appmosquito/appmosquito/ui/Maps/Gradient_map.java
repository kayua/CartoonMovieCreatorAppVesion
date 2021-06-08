package app.mosquito.appmosquito.appmosquito.ui.Maps;

import android.util.Log;

import androidx.collection.CircularArray;
import androidx.collection.SimpleArrayMap;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Gradient_map {

    public void access_account(){

        ParseUser.logInInBackground("Admin", "admin", (user, e) -> {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Detections");

            List<String>
            ParseObject a = null;
            try {
                a = query.getFirst();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

            String b = (String) a.get("latitude");
                Log.i("Latitude********************************************", b);


        });
    }

}