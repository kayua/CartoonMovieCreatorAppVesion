package app.mosquito.appmosquito.appmosquito.Authentication;

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

}
