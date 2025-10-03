public class Language {
    private String english;
    private String alpha2;

    public Language(){}

    public Language(String english, String alpha2) {
        this.english = english;
        this.alpha2 = alpha2;
    }

    public String getEnglish() {
        return english;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }
}
