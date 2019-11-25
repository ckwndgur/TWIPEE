package com.nslb.twipee.board;

public class CommentDTO {
    private int imageUser;
    private String user_comment;
    private String time_comment;
    private String comment;

    public int getImageUser(){return imageUser; }
    public void setImageUser(int imageUser){this.imageUser=imageUser;}
    public String getUser_comment(){return user_comment;}
    public void setUser_comment(String user_name){this.user_comment=user_comment;}
    public String getTime(){return time_comment;}
    public void setTime(String day){this.time_comment=time_comment;}
    public String getComment(){return comment; }
    public void setComment(String content_board){this.comment=comment;}
}
