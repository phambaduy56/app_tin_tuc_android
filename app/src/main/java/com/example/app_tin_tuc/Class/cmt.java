package com.example.app_tin_tuc.Class;

public class cmt {
    private  int id_comment;
    private  String  comment_content;
    private String name;


    public cmt(int id_comment, String comment_content, String name) {
        this.id_comment = id_comment;
        this.comment_content = comment_content;
        this.name = name;
    }

    public int getId_comment() {
        return id_comment;
    }

    public void setId_comment(int id_comment) {
        this.id_comment = id_comment;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
