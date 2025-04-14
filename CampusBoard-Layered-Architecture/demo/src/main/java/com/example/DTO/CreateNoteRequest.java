package com.example.DTO;


public class CreateNoteRequest {
    private Long userId;
    private String title;
    private String content;
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
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
        return "CreateNoteRequest{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + (content != null ? content.substring(0, Math.min(content.length(), 20)) + "..." : "null") + '\'' +
                '}';
    }
}
