public class User implements User_Data_Base {
    private String Country;
    private String Interest;












    //---------------------Getters And Setters---------------------
    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getInterest() {
        return Interest;
    }

    public void setInterest(String interest) {
        Interest = interest;
    }

    public User(String country, String interest) {
        Country = country;
        Interest = interest;


    }
}
