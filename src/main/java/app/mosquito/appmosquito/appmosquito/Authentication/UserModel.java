package app.mosquito.appmosquito.appmosquito.Authentication;

public class UserModel {

    String userName;
    String userCity;
    String userBirthDate;
    String userSchooling;
    String userFavoriteWord;
    String userGender;
    String userPhoto;
    String userCompany;
    String userEmail;

    public UserModel() {

    }

    public void userCompany(String userPhoto) { this.userCompany = userPhoto; }
    public String getUserBirthDate() { return userBirthDate; }
    public String getUserName() { return userName; }
    public String userCompany() { return userCompany; }
    public String getPhoto() { return userPhoto; }
    public String getUserCity() { return userCity; }
    public String getUserSchooling() { return userSchooling; }
    public String getUserFavoriteWord() { return userFavoriteWord; }
    public String getUserGender() { return userGender; }
    public String getUserEmail() { return userEmail; }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setUserCompany(String userEmail) { this.userCompany = userCompany; }
    public void setPhoto(String userPhoto) { this.userPhoto = userPhoto; }
    public void setUserName(String userFirstName) { this.userName = userFirstName; }
    public void setUserCity(String userCity) { this.userCity = userCity; }
    public void setUserBirthDate(String userBirthDate) { this.userBirthDate = userBirthDate; }
    public void setUserSchooling(String userSchooling) { this.userSchooling = userSchooling; }
    public void setUserFavoriteWord(String userFavoriteWord) { this.userFavoriteWord = userFavoriteWord; }
    public void setUserGender(String userGender) { this.userGender = userGender; }

}
