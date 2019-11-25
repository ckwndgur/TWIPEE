package com.nslb.twipee.board;

import java.util.ArrayList;
import java.util.List;

public class BoardDTO {
    private int resld_user_image;
    private String user_name;
    private String day;
    private String content_board;
    private String content_image;
    private int BoardKind;

    private List<BoardCommentText> Board_Comment = new ArrayList();

    public int getResld_user_image(){return resld_user_image;}
    public void setResld_user_image(int resld_board){this.resld_user_image=resld_board; }

    public String getUser_name(){return user_name;}
    public void setUser_name(String user_name){this.user_name=user_name;}

    public String getDay(){return day;}
    public void setDay(String day){this.day=day;}

    public String getContent_board(){return content_board; }
    public void setContent_board(String content_board){this.content_board=content_board;}

    public String getResld_content_image(){return content_image;}
    public void setResld_content_image(String content_image){this.content_image=content_image; }

    public int getResld_BoardKind(){return BoardKind;}
    public void setResld_BoardKind(int BoardKind){this.BoardKind=BoardKind; }

}
