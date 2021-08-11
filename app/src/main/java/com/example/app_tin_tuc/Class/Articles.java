package com.example.app_tin_tuc.Class;

import java.util.Date;

public class Articles {

    private int id;
    private String nameArticle;
    private String author;
    private String datetime;
    private String contentArticle;
    private String desciption;
    private String img;
    private String name_category;
    private int id_category;

    public Articles(int id, String nameArticle, String author, String datetime, String contentArticle, String desciption, String img, String name_category, int id_category) {
        this.id = id;
        this.nameArticle = nameArticle;
        this.author = author;
        this.datetime = datetime;
        this.contentArticle = contentArticle;
        this.desciption = desciption;
        this.img = img;
        this.name_category = name_category;
        this.id_category = id_category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameArticle() {
        return nameArticle;
    }

    public void setNameArticle(String nameArticle) {
        this.nameArticle = nameArticle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContentArticle() {
        return contentArticle;
    }

    public void setContentArticle(String contentArticle) {
        this.contentArticle = contentArticle;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
}
