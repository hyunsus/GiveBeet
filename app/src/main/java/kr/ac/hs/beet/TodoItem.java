package kr.ac.hs.beet;

public class TodoItem {

    private int id;             // 게시글의 고유 ID
    private String content;     //할 일 내용
    private String writeDate;   //작성날짜
    private boolean checkOK;
    public TodoItem(){

    }

    public boolean isCheckOK() {
        return checkOK;
    }

    public void setCheckOK(boolean checkOK) {
        this.checkOK = checkOK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

}
