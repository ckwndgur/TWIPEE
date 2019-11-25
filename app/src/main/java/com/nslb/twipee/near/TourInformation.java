package com.nslb.twipee.near;

public class TourInformation {
    public final int INFOSIZE = 7;
    public final int ADDR1 = 0, DIST = 1, FIRSTIMAGE = 2, MAPX = 3, MAPY = 4, TEL = 5, TITLE = 6;
    public final boolean[] checkItems = {false, false, false, false, false, false, false};
    public final String[] elements = {"addr1", "dist", "firstimage", "mapx", "mapy", "tel", "title"};
    public String[] informations = new String[INFOSIZE];

    public TourInformation()
    {
        informations = elements;
    }



    /*private String Addr1, dist, firstimage, mapx, mapy, tel, title;

    public void setAddr1(String addr1){ this.Addr1 = addr1;}
    public String getAddr1(){return this.Addr1;}

    public void setDist(String dist){ this.dist = dist;}
    public String getDist(){return this.dist;}

    public void setFirstimage(String firstimage){ this.firstimage = firstimage;}
    public String getFirstimage(){return this.firstimage;}

    public void setMapx(String mapx){ this.mapx = mapx;}
    public String getMapx(){return this.mapx;}

    public void setMapy(String mapy){ this.mapy = mapy;}
    public String getMapy(){return this.mapy;}

    public void setTEL(String tel){ this.tel = tel;}
    public String getTel(){return this.tel;}

    public void setTitle(String title){ this.title = title;}
    public String getTitle(){return this.title;}*/
}
