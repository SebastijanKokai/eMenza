package com.example.eMenza.classes;

import org.jsoup.nodes.Element;

public class Post {

    private Element postId;
    private Element notification;
    private Element title;
    private Element paragraph;

    public Post(Element postId, Element notification, Element title, Element paragraph) {
        this.postId = postId;
        this.notification = notification;
        this.title = title;
        this.paragraph = paragraph;
    }

    public Element getPostId() {
        return postId;
    }

    public void setPostId(Element postId) {
        this.postId = postId;
    }

    public Element getNotification() {
        return notification;
    }

    public void setNotification(Element notification) {
        this.notification = notification;
    }

    public Element getTitle() {
        return title;
    }

    public void setTitle(Element title) {
        this.title = title;
    }

    public Element getParagraph() {
        return paragraph;
    }

    public void setParagraph(Element paragraph) {
        this.paragraph = paragraph;
    }

}
