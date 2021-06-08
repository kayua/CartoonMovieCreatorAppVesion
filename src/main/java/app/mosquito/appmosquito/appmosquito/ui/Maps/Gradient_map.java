package app.mosquito.appmosquito.appmosquito.ui.Maps;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Gradient_map {

    public void access_account(){

        ParseUser.logInInBackground("Admin", "admin", (user, e) -> {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Detections");

            List<String> latitude = new ArrayList<>();
            List<String> longitude = new ArrayList<>();
            List<String> datetime = new ArrayList<>();

            try {
                List<ParseObject> results = query.find();
                for (ParseObject result : results) {
                    ParseObject a = query.get(result.getObjectId());
                    latitude.add((String) a.get("latitude"));
                    longitude.add((String) a.get("longitude"));
                    longitude.add((String) a.get("DataTime"));


                }
            } catch (ParseException ei) {
                e.printStackTrace();
            }


        });
    }

}