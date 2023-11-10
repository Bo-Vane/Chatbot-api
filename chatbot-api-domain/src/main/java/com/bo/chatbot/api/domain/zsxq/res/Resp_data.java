package com.bo.chatbot.api.domain.zsxq.res;

import com.bo.chatbot.api.domain.zsxq.vo.Favorites;

import java.util.ArrayList;
import java.util.List;
public class Resp_data
{
    private List<Favorites> favorites;

    public void setFavorites(List<Favorites> favorites){
        this.favorites = favorites;
    }
    public List<Favorites> getFavorites(){
        return this.favorites;
    }
}