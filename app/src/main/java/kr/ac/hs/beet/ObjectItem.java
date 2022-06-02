package kr.ac.hs.beet;

public class ObjectItem {
    String price;
    String name;
    String imgName;
    String category = "object";
    int resId;
    //파일 명 추가
     public ObjectItem(String price, String name, String imgName, int resId){
         this.price = price;
         this.name = name;
         this.imgName = imgName;
         this.resId = resId;
     }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getImgName() { return imgName; }

    public String getCategory() {
        return category;
    }
}
