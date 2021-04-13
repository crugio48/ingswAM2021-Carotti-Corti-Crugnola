package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.resources.ResourceBox;

import java.util.Stack;

public class DevCardSpace {

    private Stack<DevCard> Lv1blue;
    private Stack<DevCard> Lv2blue;
    private Stack<DevCard> Lv3blue;
    private Stack<DevCard> Lv1green;
    private Stack<DevCard> Lv2green;
    private Stack<DevCard> Lv3green;
    private Stack<DevCard> Lv1yellow;
    private Stack<DevCard> Lv2yellow;
    private Stack<DevCard> Lv3yellow;
    private Stack<DevCard> Lv1purple;
    private Stack<DevCard> Lv2purple;
    private Stack<DevCard> Lv3purple;

    private Stack<DevCard> selectstack(int level, char colour){
        switch (colour){
            case 'b': {
                if(level==1)return Lv1blue; if(level==2)return Lv2blue; if(level==3) return Lv3blue;
            }
            case 'g':{
                if(level==1)return Lv1green; if(level==2)return Lv2green; if(level==3) return Lv3green;
            }
            case 'y':{
                if(level==1)return Lv1yellow; if(level==2)return Lv2yellow; if(level==3) return Lv3yellow;
            }
            case 'p':{
                if(level==1)return Lv1purple; if(level==2)return Lv2purple; if(level==3) return Lv3purple;
            }
        } ;
        return null;
    }
     public DevCard peekTopCard(int level , char colour){
        return selectstack(level,colour).peek();
     }
     public DevCard getnremoveTopCard(int level, char colour){
        return selectstack(level,colour).pop();
     }

    // public boolean isBuyable(int level, char colour, ResourceBox cost){ }







}
