package com.weijin.whist.model;

import java.util.Arrays;
import java.util.List;

public class Dealer {
    public void deal(List<Player> players) {
        int[] rdm = randomCommon();
//        System.out.println(Arrays.toString(rdm));
        int[] hand = new int[13];
        Card[] handCards = new Card[13];
        for (int i = 0; i < players.size(); i++) {
            System.arraycopy(rdm, i * 13, hand, 0, hand.length);
//            System.out.println(Arrays.toString(hand));
            for (int j = 0; j < hand.length; j++) {
                handCards[j] = new Card(Suit.getSuit((hand[j] - 1) / 13), Rank.getRank(hand[j] % 13));
            }
            players.get(i).setInitHand(List.of(handCards));
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

    public static void main(String[] args) {
        int[] rdm = randomCommon();
        System.out.println(Arrays.toString(rdm));
        int[] a = new int[12];
        int[] b = new int[12];
        int[] c = new int[12];
        int[] d = new int[12];
        System.arraycopy(rdm, 0, a, 0, a.length);
        System.arraycopy(rdm, 13, b, 0, b.length);
        System.arraycopy(rdm, 26, c, 0, c.length);
        System.arraycopy(rdm, 39, d, 0, d.length);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(c));
        System.out.println(Arrays.toString(d));

        Player p1 = new Player();
        Player p2 = new Player();
        Player p3 = new Player();
        Player p4 = new Player();
        List<Player> players = Arrays.asList(p1, p2, p3, p4);
        Dealer dealer = new Dealer();
        dealer.deal(players);
        List<Card> hand = p1.getCurrHand();
        for (Card card : hand) {
            System.out.println(card.getRank() + " " + card.getSuit() + " " + card.getId());
        }
//        System.out.println(Arrays.toString(p1.getCurrHand()));

    }
}
