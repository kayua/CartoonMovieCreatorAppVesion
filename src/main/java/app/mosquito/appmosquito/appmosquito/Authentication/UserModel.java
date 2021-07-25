package app.mosquito.appmosquito.appmosquito.Authentication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel {

    String idUser;
    String userFirstName;
    String userSecondName;
    String userCity;
    String userBirthDate;
    String userSchooling;
    String userFavoriteWord;
    String userGender;

    public UserModel(String idUser, String userFirstName, String userSecondName, String userCity, String userBirthDate, String userSchooling, String userFavoriteWord, String userGender) {

        this.idUser = idUser;
        this.userFirstName = userFirstName;
        this.userSecondName = userSecondName;
        this.userCity = userCity;
        this.userBirthDate = userBirthDate;
        this.userSchooling = userSchooling;
        this.userFavoriteWord = userFavoriteWord;
        this.userGender = userGender;
    }

    public String getIdUser() { return idUser; }

    public void setIdUser(String idUser) { this.idUser = idUser; }

    public String getUserFirstName() { return userFirstName; }

    public void setUserFirstName(String userFirstName) { this.userFirstName = userFirstName; }

    public String getUserSecondName() { return userSecondName; }

    public void setUserSecondName(String userSecondName) { this.userSecondName = userSecondName; }

    public String getUserCity() { return userCity; }

    public void setUserCity(String userCity) { this.userCity = userCity; }

    public String getUserBirthDate() { return userBirthDate; }

    public void setUserBirthDate(String userBirthDate) { this.userBirthDate = userBirthDate; }

    public String getUserSchooling() { return userSchooling; }

    public void setUserSchooling(String userSchooling) { this.userSchooling = userSchooling; }

    public String getUserFavoriteWord() { return userFavoriteWord; }

    public void setUserFavoriteWord(String userFavoriteWord) {
        this.userFavoriteWord = userFavoriteWord; }

    public String getUserGender() { return userGender; }

    public void setUserGender(String userGender) { this.userGender = userGender; }

    public void saveDataFirebase(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("user_info").child(getIdUser()).setValue(this);
    }

}
