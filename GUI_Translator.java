/*
Last Update: 23 April 2021

This class serves to interact between the articles and the GuiMain class.

Contributing authors: Austin Matias
 */
import java.util.ArrayList;

public class GUI_Translator {

    protected ArrayList<String> titleList = new ArrayList<String>();
    protected ArrayList<String> authorList = new ArrayList<String>();
    protected ArrayList<String> descriptionList = new ArrayList<String>();
    protected ArrayList<String> UrlList = new ArrayList<String>();
    protected ArrayList<String> ImageUrlList = new ArrayList<String>();


    public GUI_Translator(){}

    protected ArrayList<String> setTitleList(ArrayList<Article> _articleList){
        ArrayList<String> titleListTest = new ArrayList<>();
        for (int i = 0; i < _articleList.toArray().length; i++){
            titleListTest.add(_articleList.get(i).getTitle());
        }
        return titleListTest;
    }

    protected ArrayList<String> setAuthorList(ArrayList<Article> _articleList){
        for (int i = 0; i < _articleList.toArray().length; i++){
            authorList.add(_articleList.get(i).getAuthor());
        }
        return authorList;
    }

    protected ArrayList<String> setDescriptionList(ArrayList<Article> _articleList){
        for (int i = 0; i < _articleList.toArray().length; i++){
            descriptionList.add(_articleList.get(i).getDescription());
        }
        return descriptionList;
    }

    protected ArrayList<String> setUrlList(ArrayList<Article> _articleList){
        for (int i = 0; i < _articleList.toArray().length; i++){
            UrlList.add(_articleList.get(i).getUrl());
        }
        return UrlList;
    }

    protected ArrayList<String> setImageUrlList(ArrayList<Article> _articleList){
        for (int i = 0; i < _articleList.toArray().length; i++){
            ImageUrlList.add(_articleList.get(i).getUrlToImage());
        }
        return ImageUrlList;
    }

    public ArrayList<String> getTitleList() {
        return titleList;
    }

    public ArrayList<String> getAuthorList() {
        return authorList;
    }

    public ArrayList<String> getDescriptionList() {
        return descriptionList;
    }

    public ArrayList<String> getUrlList() {
        return UrlList;
    }

    public ArrayList<String> getImageUrlList() {
        return ImageUrlList;
    }
}

