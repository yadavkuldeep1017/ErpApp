package com.example.collegeerp;

public class NewsModel {
    String Heading;
    String AddNews;
    String image;
    public NewsModel()
    {

    }

    public NewsModel(String heading, String addNews, String image) {
        Heading = heading;
        AddNews = addNews;
        this.image = image;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getAddNews() {
        return AddNews;
    }

    public void setAddNews(String addNews) {
        AddNews = addNews;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
