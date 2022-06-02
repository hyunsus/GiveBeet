package kr.ac.hs.beet;

public class NewDiaryList {
    String sentence;
    String date;
    int image;

    public NewDiaryList(String sentence, String date, int image){
        this.sentence= sentence;
        this.date = date;
        this.image = image;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}