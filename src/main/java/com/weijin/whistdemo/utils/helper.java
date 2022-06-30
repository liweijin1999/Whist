package com.weijin.whistdemo.utils;

import com.weijin.whistdemo.component.Card;
import com.weijin.whistdemo.component.Deck;
import com.weijin.whistdemo.component.Rank;
import com.weijin.whistdemo.component.Suit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class helper {
    static final String FIXED_URL = "src/main/resources/com/weijin/whistdemo/static/pictures/poker_imgs/";
    static final String POSTFIX = ".gif";

    public static Image CardtoImage(Card card) {
        File file;
        if (card == null) {
            file = new File(FIXED_URL + "back" + POSTFIX);
        } else {
            file = new File(FIXED_URL + card.getId() + POSTFIX);
        }
        String path = file.toURI().toString();
        Image image = new Image(path);
        return image;
    }

    public static List<Card> sortCards(List<Card> cards) {
        List<Card> sortedCards = new ArrayList<>(cards);
        int impactFactorI = 10000000, impactFactorJ = 10000000;
//        System.arraycopy(cards, 0, sortedCards, 0, sortedCards.length);
//        System.out.println(sortedCards.length);
        for (int i = 0; i < sortedCards.size(); i++) {
            for (int j = 0; j < sortedCards.size(); j++) {
                switch (sortedCards.get(i).getSuit()) {
                    case SPADES -> {
                        impactFactorI = 1000;
                    }
                    case HEARTS -> {
                        impactFactorI = 100;
                    }
                    case CLUBS -> {
                        impactFactorI = 10;
                    }
                    case DIAMONDS -> {
                        impactFactorI = 1;
                    }

                }
                switch (sortedCards.get(j).getSuit()) {
                    case SPADES -> {
                        impactFactorJ = 1000;
                    }
                    case HEARTS -> {
                        impactFactorJ = 100;
                    }
                    case CLUBS -> {
                        impactFactorJ = 10;
                    }
                    case DIAMONDS -> {
                        impactFactorJ = 1;
                    }

                }
                if (sortedCards.get(i).getRank().getCardValue() * impactFactorI > sortedCards.get(j).getRank().getCardValue() * impactFactorJ) {
//                        System.out.println(sortedCards[i].getRank() + " " + sortedCards[i].getSuit()+"//"+sortedCards[j].getRank() + " " + sortedCards[j].getSuit());
//                        System.out.println(sortedCards[i].getRank().getCardValue()*impactFactorI + " " + sortedCards[j].getRank().getCardValue()*impactFactorJ);
                    Card tempCard = sortedCards.get(i);
                    sortedCards.set(i, sortedCards.get(j));
                    sortedCards.set(j, tempCard);
                }
            }
        }
        return sortedCards;
    }

    public static void rearrange(ImageView seletedIV, List<Card> handList, List<ImageView> ivList, HashMap<ImageView, Card> handMap) {
        //preliminary:每次循环的时候都是规律的
        int playedHandIndex = handList.indexOf(handMap.get(seletedIV));// 出的牌在手牌当中的索引
        int playedIVindex = ivList.indexOf(seletedIV);//出的牌在13张图层的索引
        int leftBound = playedIVindex - playedHandIndex; //图层中不为空的第一张牌的索引（部分移动前）,移动后要+1
        handList.remove(handMap.get(seletedIV));
        int cardsPlayed = ivList.size() - handList.size();//已经出的牌数
        int rightBound = (ivList.size() - 1) - (cardsPlayed - leftBound) + 1; //图层中不为空的最后一张牌的索引（部分移动前）,移动后要-1

        if (playedIVindex < 6) {
            for (int i = playedIVindex - 1; i >= leftBound; i--) {
                ivList.get(i + 1).setImage(CardtoImage(handMap.get(ivList.get(i))));
                handMap.put(ivList.get(i + 1), handMap.get(ivList.get(i)));
            }
            ivList.get(leftBound).setImage(null);
            ivList.get(leftBound).setFitHeight(0);
            ivList.get(leftBound).setFitWidth(0);
            handMap.put(ivList.get(leftBound), null);
            leftBound += 1;
            rightBound = (ivList.size() - 1) - (cardsPlayed - leftBound);
            if (handList.size() % 2 == 1 && (cardsPlayed) / 2 != leftBound) {
                for (int j = leftBound - 1; j < rightBound; j++) {
                    if (j == leftBound - 1) {
                        ivList.get(j).setFitHeight(ivList.get(j + 1).getFitHeight());
                        ivList.get(j).setFitWidth(ivList.get(j + 1).getFitWidth());
                    }
                    ivList.get(j).setImage(ivList.get(j + 1).getImage());
                    handMap.put(ivList.get(j), handMap.get(ivList.get(j + 1)));
                }
                ivList.get(rightBound).setImage(null);
                ivList.get(rightBound).setFitHeight(0);
                ivList.get(rightBound).setFitWidth(0);
                handMap.put(ivList.get(rightBound), null);
                leftBound -= 1;
                rightBound -= 1;
            }
        } else {
            for (int i = playedIVindex; i < rightBound; i++) {
                ivList.get(i).setImage(ivList.get(i + 1).getImage());
                handMap.put(ivList.get(i), handMap.get(ivList.get(i + 1)));
            }
            ivList.get(rightBound).setImage(null);
            ivList.get(rightBound).setFitHeight(0);
            ivList.get(rightBound).setFitWidth(0);
            handMap.put(ivList.get(rightBound), null);
            rightBound -= 1;
            if (handList.size() % 2 == 1 && leftBound != rightBound) {
                for (int j = rightBound; j >= leftBound; j--) {
                    if (j == rightBound) {
                        ivList.get(j + 1).setFitHeight(ivList.get(j).getFitHeight());
                        ivList.get(j + 1).setFitWidth(ivList.get(j).getFitWidth());
                    }
                    ivList.get(j + 1).setImage(ivList.get(j).getImage());
                    handMap.put(ivList.get(j + 1), handMap.get(ivList.get(j)));
                }
                ivList.get(leftBound).setImage(null);
                ivList.get(leftBound).setFitHeight(0);
                ivList.get(leftBound).setFitWidth(0);
                handMap.put(ivList.get(leftBound), null);
                leftBound += 1;
                rightBound += 1;
            }
        }
    }

    public static void main(String[] args) {
        List<Card> cards = new ArrayList<>(52);
        for (int i = 0; i < 52; i++) {
            cards.set(i, new Card(Suit.getSuit(i / 13), Rank.getRank(i % 13)));
        }
//            for (int i = 0; i < cards.length; i++) {
//                System.out.println(cards[i].getRank() + " " + cards[i].getSuit());
//            }
        List<Card> sortedCards = sortCards(cards);
        for (int i = 0; i < sortedCards.size(); i++) {
            System.out.println(sortedCards.get(i).getRank() + " " + sortedCards.get(i).getSuit());
        }
    }

    public static int[] randomCommon() {
        int min = 1;
        int n = Deck.DECK_SIZE;

        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * 52) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }


    public class SerialCloneUtils {
        /**
         * 使用ObjectStream序列化实现深克隆
         *
         * @return Object obj
         */
        public static <T extends Serializable> T deepClone(T t) throws CloneNotSupportedException {
            // 保存对象为字节数组
            try {
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                try (ObjectOutputStream out = new ObjectOutputStream(bout)) {
                    out.writeObject(t);
                }

                // 从字节数组中读取克隆对象
                try (InputStream bin = new ByteArrayInputStream(bout.toByteArray())) {
                    ObjectInputStream in = new ObjectInputStream(bin);
                    return (T) (in.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
                CloneNotSupportedException cloneNotSupportedException = new CloneNotSupportedException();
                e.initCause(cloneNotSupportedException);
                throw cloneNotSupportedException;
            }
        }
    }
}
