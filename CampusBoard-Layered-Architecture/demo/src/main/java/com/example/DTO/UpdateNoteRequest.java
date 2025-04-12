package com.example.DTO;

import java.util.Date;

public class UpdateNoteRequest {
    private String title;
    private String content;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "UpdateNoteRequest{" +
                "title='" + title + '\'' +
                ", content='" + (content != null ? content.substring(0, Math.min(content.length(), 20)) + "..." : "null") + '\'' +
                '}';
    }
}