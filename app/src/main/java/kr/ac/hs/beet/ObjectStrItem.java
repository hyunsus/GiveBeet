package kr.ac.hs.beet;

public class ObjectStrItem {
    String name;
    String imgName;

     public ObjectStrItem(String name, String imgName){
         this.name = name;
         this.imgName = imgName;
     }

    public String getName() {
        return name;
    }

    public String getImgName() {
        return imgName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
