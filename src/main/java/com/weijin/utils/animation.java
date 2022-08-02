package com.weijin.utils;

import javafx.scene.image.ImageView;

import java.util.List;

public class animation {
    static final double CARDPOP = 20.0f;

    public static void cardPop(ImageView controlledIv, List<ImageView> ivList) {

        for (ImageView iv : ivList) {
            //点击这张牌的时候，其他的牌都应该是Y=0
            if (iv != controlledIv && iv.getY() < 0) {
                iv.setY(iv.getY() + CARDPOP);
            } else if (iv == controlledIv && controlledIv.getY() < 0) {
                controlledIv.setY(controlledIv.getY() + CARDPOP);
            } else if (iv == controlledIv && controlledIv.getY() == 0) {
                controlledIv.setY(controlledIv.getY() - CARDPOP);
            }
        }
    }

    public static void cardDrop(ImageView controlledIV) {
        if (controlledIV.getY() < 0) {
            controlledIV.setY(controlledIV.getY() + CARDPOP);
        }
    }
}
