package com.weijin.whistdemo.test;

import com.weijin.whistdemo.AIstrategy.*;
import com.weijin.whistdemo.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.weijin.whistdemo.utils.helper.*;

import static com.weijin.whistdemo.utils.helper.rankToSymbol;
import static com.weijin.whistdemo.utils.helper.suitToSymbol;

public class blackBoxTest1 {
    //对比不同的策略胜率，看是否更好的策略总体胜率更高
    WhistImpl whist;
    Deck deck;
    Player you;
    List<Player> playerList;
    List<Player> turnList;
    Strategy strategy1;
    Strategy strategy2;
    int winCount = 0;
    int loseCount = 0;
    int points = 0;

    public void init() {
        whist = new WhistImpl();
        Player you = new Player();
        you.setId("player1 (you)");
        whist.loadPlayers(you);
    }

    public void deal() {
        Card card1 = new Card(Suit.HEARTS, Rank.TEN);
        Card card2 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card3 = new Card(Suit.SPADES, Rank.ACE);
        Card card4 = new Card(Suit.SPADES, Rank.KING);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card card6 = new Card(Suit.SPADES, Rank.NINE);
        Card card7 = new Card(Suit.SPADES, Rank.FOUR);
        Card card8 = new Card(Suit.SPADES, Rank.THREE);
        Card card9 = new Card(Suit.DIAMONDS, Rank.FIVE);
        Card card10 = new Card(Suit.DIAMONDS, Rank.FOUR);
        Card card11 = new Card(Suit.CLUBS, Rank.KING);
        Card card12 = new Card(Suit.CLUBS, Rank.EIGHT);
        Card card13 = new Card(Suit.CLUBS, Rank.SIX);
        List<Card> handList1 = new ArrayList<>(List.of(card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13));
        playerList.get(0).setInitHand(handList1);
        Card card14 = new Card(Suit.HEARTS, Rank.ACE);
        Card card15 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card16 = new Card(Suit.HEARTS, Rank.JACK);
        Card card17 = new Card(Suit.SPADES, Rank.EIGHT);
        Card card18 = new Card(Suit.SPADES, Rank.SEVEN);
        Card card19 = new Card(Suit.SPADES, Rank.FIVE);
        Card card20 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card21 = new Card(Suit.DIAMONDS, Rank.TEN);
        Card card22 = new Card(Suit.CLUBS, Rank.QUEEN);
        Card card23 = new Card(Suit.CLUBS, Rank.JACK);
        Card card24 = new Card(Suit.CLUBS, Rank.TEN);
        Card card25 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card26 = new Card(Suit.CLUBS, Rank.THREE);
        List<Card> handList2 = new ArrayList<>(List.of(card14, card15, card16, card17, card18, card19, card20, card21, card22, card23, card24, card25, card26));
        playerList.get(1).setInitHand(handList2);
        Card card27 = new Card(Suit.HEARTS, Rank.KING);
        Card card28 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card29 = new Card(Suit.HEARTS, Rank.SIX);
        Card card30 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card31 = new Card(Suit.HEARTS, Rank.TWO);
        Card card32 = new Card(Suit.SPADES, Rank.SIX);
        Card card33 = new Card(Suit.SPADES, Rank.TWO);
        Card card34 = new Card(Suit.DIAMONDS, Rank.NINE);
        Card card35 = new Card(Suit.DIAMONDS, Rank.SIX);
        Card card36 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card37 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card38 = new Card(Suit.CLUBS, Rank.ACE);
        Card card39 = new Card(Suit.CLUBS, Rank.SEVEN);
        List<Card> handList3 = new ArrayList<>(List.of(card27, card28, card29, card30, card31, card32, card33, card34, card35, card36, card37, card38, card39));
        playerList.get(2).setInitHand(handList3);
        Card card40 = new Card(Suit.HEARTS, Rank.NINE);
        Card card41 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card42 = new Card(Suit.HEARTS, Rank.THREE);
        Card card43 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card44 = new Card(Suit.SPADES, Rank.JACK);
        Card card45 = new Card(Suit.DIAMONDS, Rank.KING);
        Card card46 = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card47 = new Card(Suit.DIAMONDS, Rank.JACK);
        Card card48 = new Card(Suit.DIAMONDS, Rank.EIGHT);
        Card card49 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card card50 = new Card(Suit.CLUBS, Rank.NINE);
        Card card51 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card52 = new Card(Suit.CLUBS, Rank.TWO);
        List<Card> handList4 = new ArrayList<>(List.of(card40, card41, card42, card43, card44, card45, card46, card47, card48, card49, card50, card51, card52));
        playerList.get(3).setInitHand(handList4);
    }

    public void deal2() {
        Card card1 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card2 = new Card(Suit.HEARTS, Rank.THREE);
        Card card3 = new Card(Suit.SPADES, Rank.ACE);
        Card card4 = new Card(Suit.SPADES, Rank.KING);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card card6 = new Card(Suit.SPADES, Rank.NINE);
        Card card7 = new Card(Suit.SPADES, Rank.FOUR);
        Card card8 = new Card(Suit.SPADES, Rank.THREE);
        Card card9 = new Card(Suit.DIAMONDS, Rank.FIVE);
        Card card10 = new Card(Suit.DIAMONDS, Rank.FOUR);
        Card card11 = new Card(Suit.CLUBS, Rank.KING);
        Card card12 = new Card(Suit.CLUBS, Rank.EIGHT);
        Card card13 = new Card(Suit.CLUBS, Rank.SIX);
        List<Card> handList1 = new ArrayList<>(List.of(card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13));
        playerList.get(0).setInitHand(handList1);
        Card card14 = new Card(Suit.HEARTS, Rank.ACE);
        Card card15 = new Card(Suit.HEARTS, Rank.NINE);
        Card card16 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card17 = new Card(Suit.SPADES, Rank.EIGHT);
        Card card18 = new Card(Suit.SPADES, Rank.SEVEN);
        Card card19 = new Card(Suit.SPADES, Rank.FIVE);
        Card card20 = new Card(Suit.SPADES, Rank.TWO);
        Card card21 = new Card(Suit.DIAMONDS, Rank.NINE);
        Card card22 = new Card(Suit.DIAMONDS, Rank.SIX);
        Card card23 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card24 = new Card(Suit.CLUBS, Rank.TEN);
        Card card25 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card26 = new Card(Suit.CLUBS, Rank.THREE);
        List<Card> handList2 = new ArrayList<>(List.of(card14, card15, card16, card17, card18, card19, card20, card21, card22, card23, card24, card25, card26));
        playerList.get(1).setInitHand(handList2);
        Card card27 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card28 = new Card(Suit.HEARTS, Rank.JACK);
        Card card29 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card30 = new Card(Suit.SPADES, Rank.SIX);
        Card card31 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card32 = new Card(Suit.DIAMONDS, Rank.EIGHT);
        Card card33 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card card34 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card35 = new Card(Suit.CLUBS, Rank.ACE);
        Card card36 = new Card(Suit.CLUBS, Rank.QUEEN);
        Card card37 = new Card(Suit.CLUBS, Rank.JACK);
        Card card38 = new Card(Suit.CLUBS, Rank.SEVEN);
        Card card39 = new Card(Suit.CLUBS, Rank.TWO);
        List<Card> handList3 = new ArrayList<>(List.of(card27, card28, card29, card30, card31, card32, card33, card34, card35, card36, card37, card38, card39));
        playerList.get(2).setInitHand(handList3);
        Card card40 = new Card(Suit.HEARTS, Rank.KING);
        Card card41 = new Card(Suit.HEARTS, Rank.TEN);
        Card card42 = new Card(Suit.HEARTS, Rank.SIX);
        Card card43 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card44 = new Card(Suit.HEARTS, Rank.TWO);
        Card card45 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card46 = new Card(Suit.SPADES, Rank.JACK);
        Card card47 = new Card(Suit.DIAMONDS, Rank.KING);
        Card card48 = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card49 = new Card(Suit.DIAMONDS, Rank.JACK);
        Card card50 = new Card(Suit.DIAMONDS, Rank.TEN);
        Card card51 = new Card(Suit.CLUBS, Rank.NINE);
        Card card52 = new Card(Suit.CLUBS, Rank.FIVE);
        List<Card> handList4 = new ArrayList<>(List.of(card40, card41, card42, card43, card44, card45, card46, card47, card48, card49, card50, card51, card52));
        playerList.get(3).setInitHand(handList4);
    }

    public void deal3() {
        Card card1 = new Card(Suit.HEARTS, Rank.KING);
        Card card2 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.SPADES, Rank.ACE);
        Card card5 = new Card(Suit.SPADES, Rank.KING);
        Card card6 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card7 = new Card(Suit.SPADES, Rank.FIVE);
        Card card8 = new Card(Suit.SPADES, Rank.THREE);
        Card card9 = new Card(Suit.DIAMONDS, Rank.KING);
        Card card10 = new Card(Suit.DIAMONDS, Rank.FIVE);
        Card card11 = new Card(Suit.CLUBS, Rank.KING);
        Card card12 = new Card(Suit.CLUBS, Rank.SIX);
        Card card13 = new Card(Suit.CLUBS, Rank.FIVE);
        List<Card> handList1 = new ArrayList<>(List.of(card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13));
        playerList.get(0).setInitHand(handList1);
        Card card14 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card15 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card16 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card17 = new Card(Suit.SPADES, Rank.JACK);
        Card card18 = new Card(Suit.SPADES, Rank.TEN);
        Card card19 = new Card(Suit.SPADES, Rank.FOUR);
        Card card20 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card21 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card22 = new Card(Suit.CLUBS, Rank.QUEEN);
        Card card23 = new Card(Suit.CLUBS, Rank.ACE);
        Card card24 = new Card(Suit.CLUBS, Rank.NINE);
        Card card25 = new Card(Suit.CLUBS, Rank.SEVEN);
        Card card26 = new Card(Suit.CLUBS, Rank.TWO);
        List<Card> handList2 = new ArrayList<>(List.of(card14, card15, card16, card17, card18, card19, card20, card21, card22, card23, card24, card25, card26));
        playerList.get(1).setInitHand(handList2);
        Card card27 = new Card(Suit.HEARTS, Rank.ACE);
        Card card28 = new Card(Suit.HEARTS, Rank.NINE);
        Card card29 = new Card(Suit.HEARTS, Rank.SIX);
        Card card30 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card31 = new Card(Suit.SPADES, Rank.TWO);
        Card card32 = new Card(Suit.SPADES, Rank.SIX);
        Card card33 = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card34 = new Card(Suit.DIAMONDS, Rank.NINE);
        Card card35 = new Card(Suit.DIAMONDS, Rank.JACK);
        Card card36 = new Card(Suit.DIAMONDS, Rank.TEN);
        Card card37 = new Card(Suit.DIAMONDS, Rank.FOUR);
        Card card38 = new Card(Suit.CLUBS, Rank.EIGHT);
        Card card39 = new Card(Suit.CLUBS, Rank.THREE);
        List<Card> handList3 = new ArrayList<>(List.of(card27, card28, card29, card30, card31, card32, card33, card34, card35, card36, card37, card38, card39));
        playerList.get(2).setInitHand(handList3);
        Card card40 = new Card(Suit.HEARTS, Rank.JACK);
        Card card41 = new Card(Suit.HEARTS, Rank.TEN);
        Card card42 = new Card(Suit.HEARTS, Rank.THREE);
        Card card43 = new Card(Suit.SPADES, Rank.NINE);
        Card card44 = new Card(Suit.SPADES, Rank.EIGHT);
        Card card45 = new Card(Suit.SPADES, Rank.SEVEN);
        Card card46 = new Card(Suit.DIAMONDS, Rank.EIGHT);
        Card card47 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card card48 = new Card(Suit.DIAMONDS, Rank.SIX);
        Card card49 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card50 = new Card(Suit.CLUBS, Rank.JACK);
        Card card51 = new Card(Suit.CLUBS, Rank.TEN);
        Card card52 = new Card(Suit.CLUBS, Rank.FOUR);
        List<Card> handList4 = new ArrayList<>(List.of(card40, card41, card42, card43, card44, card45, card46, card47, card48, card49, card50, card51, card52));
        playerList.get(3).setInitHand(handList4);
    }

    public void deal4() {
        Card card1 = new Card(Suit.HEARTS, Rank.KING);
        Card card2 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card3 = new Card(Suit.HEARTS, Rank.TEN);
        Card card4 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card5 = new Card(Suit.SPADES, Rank.KING);
        Card card6 = new Card(Suit.SPADES, Rank.FOUR);
        Card card7 = new Card(Suit.SPADES, Rank.THREE);
        Card card8 = new Card(Suit.DIAMONDS, Rank.TEN);
        Card card9 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card card10 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card11 = new Card(Suit.CLUBS, Rank.JACK);
        Card card12 = new Card(Suit.CLUBS, Rank.EIGHT);
        Card card13 = new Card(Suit.CLUBS, Rank.SEVEN);
        List<Card> handList1 = new ArrayList<>(List.of(card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13));
        playerList.get(0).setInitHand(handList1);
        Card card14 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card15 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card16 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card17 = new Card(Suit.SPADES, Rank.JACK);
        Card card18 = new Card(Suit.SPADES, Rank.FIVE);
        Card card19 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card20 = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card21 = new Card(Suit.DIAMONDS, Rank.JACK);
        Card card22 = new Card(Suit.DIAMONDS, Rank.EIGHT);
        Card card23 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card24 = new Card(Suit.CLUBS, Rank.ACE);
        Card card25 = new Card(Suit.CLUBS, Rank.KING);
        Card card26 = new Card(Suit.CLUBS, Rank.FOUR);
        List<Card> handList2 = new ArrayList<>(List.of(card14, card15, card16, card17, card18, card19, card20, card21, card22, card23, card24, card25, card26));
        playerList.get(1).setInitHand(handList2);
        Card card27 = new Card(Suit.HEARTS, Rank.ACE);
        Card card28 = new Card(Suit.HEARTS, Rank.NINE);
        Card card29 = new Card(Suit.HEARTS, Rank.THREE);
        Card card30 = new Card(Suit.HEARTS, Rank.TWO);
        Card card31 = new Card(Suit.SPADES, Rank.ACE);
        Card card32 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card33 = new Card(Suit.SPADES, Rank.SIX);
        Card card34 = new Card(Suit.SPADES, Rank.TWO);
        Card card35 = new Card(Suit.DIAMONDS, Rank.KING);
        Card card36 = new Card(Suit.DIAMONDS, Rank.FIVE);
        Card card37 = new Card(Suit.DIAMONDS, Rank.FOUR);
        Card card38 = new Card(Suit.CLUBS, Rank.SIX);
        Card card39 = new Card(Suit.CLUBS, Rank.THREE);
        List<Card> handList3 = new ArrayList<>(List.of(card27, card28, card29, card30, card31, card32, card33, card34, card35, card36, card37, card38, card39));
        playerList.get(2).setInitHand(handList3);
        Card card40 = new Card(Suit.HEARTS, Rank.JACK);
        Card card41 = new Card(Suit.HEARTS, Rank.SIX);
        Card card42 = new Card(Suit.HEARTS, Rank.TEN);
        Card card43 = new Card(Suit.SPADES, Rank.NINE);
        Card card44 = new Card(Suit.SPADES, Rank.EIGHT);
        Card card45 = new Card(Suit.SPADES, Rank.SEVEN);
        Card card46 = new Card(Suit.DIAMONDS, Rank.NINE);
        Card card47 = new Card(Suit.DIAMONDS, Rank.SIX);
        Card card48 = new Card(Suit.CLUBS, Rank.QUEEN);
        Card card49 = new Card(Suit.CLUBS, Rank.TEN);
        Card card50 = new Card(Suit.CLUBS, Rank.NINE);
        Card card51 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card52 = new Card(Suit.CLUBS, Rank.TWO);
        List<Card> handList4 = new ArrayList<>(List.of(card40, card41, card42, card43, card44, card45, card46, card47, card48, card49, card50, card51, card52));
        playerList.get(3).setInitHand(handList4);
    }

    private void testStrategy() throws IOException, ClassNotFoundException {
        this.playerList = whist.getPlayerList();
        this.you = playerList.get(0);
        Player partner = playerList.get(2);
        deck = new Deck(playerList);
        whist.addDeckRound();
        whist.deckRound = 2;
        deck.initNewDeck(whist.deckRound);
        deck.setTurnListByStarter(0, false);
        for (Player player : playerList) {
            player.setTrumpSuit(deck.getCurrentTrump());
        }
        turnList = deck.getTurnList();
        deal();
        for (Player player : playerList) {
            player.setTricks(new ArrayList<>());
//            for (Card card : player.getCurrHand()) {
//                System.out.println(card.getSuit() + " " + card.getRank());
//            }
        }
//        System.out.println(turnList.get(0).getId());
//        System.out.println(deck.getCurrentTrump());
        int round = 1;
        deck.setRound(round);
        while (!deck.isDeckEmpty()) {
            deck.resetThisRoundCards();
            System.out.println("Round " + round++);
            deck.setRound(round);
            turnList = deck.getTurnList();
            for (int i = 0; i < turnList.size(); i++) {
                Card AIcard;
                if (turnList.get(i) == this.you || turnList.get(i) == playerList.get(2)) {
                    strategy1 = new HardStrategyTest();
                    AIcard = strategy1.AIStrategy(turnList.get(i), deck, whist);
                } else {
                    strategy2 = new HardStrategyTest();
                    AIcard = strategy2.AIStrategy(turnList.get(i), deck, whist);
                }
                if (i == 0) {
                    deck.setCurrentLeadSuit(AIcard.getSuit());
                }
                turnList.get(i).throwCard(AIcard);
                deck.addThisRoundCard(turnList.get(i), AIcard);
                deck.cardsLeftOnDeck--;
                System.out.println(turnList.get(i).getId() + " played: " + suitToSymbol(AIcard.getSuit()) + " " + rankToSymbol(AIcard.getRank()));
            }

            HashMap<Player, Card> biggestCard = deck.getBiggestThisRound();
            for (Player winner : biggestCard.keySet()) {
                System.out.println(winner.getId() + " biggest: " + suitToSymbol(biggestCard.get(winner).getSuit()) + " " + rankToSymbol(biggestCard.get(winner).getRank()));
                winner.addTrick(biggestCard.get(winner));
                if (!deck.isDeckEmpty()) {
//                    System.out.println("winner: " + winner.getId() + " " + playerList.indexOf(winner));
                    deck.setTurnListByStarter(playerList.indexOf(winner), true);
//                    System.out.println("new turnList: " + turnList.get(0).getId() + " " + turnList.get(1).getId() + " " + turnList.get(2).getId() + " " + turnList.get(3).getId());
                }
            }
        }
        System.out.println("---end of game---");
        int uAndTeammateTricks = playerList.get(0).getTricks().size() + playerList.get(2).getTricks().size();
        int opponentTricks = playerList.get(1).getTricks().size() + playerList.get(3).getTricks().size();
        int uAndTeammateScore, opponentScore;

        if (uAndTeammateTricks > opponentTricks) {
            uAndTeammateScore = uAndTeammateTricks - 6;
            System.out.println("you and your teammate win with " + uAndTeammateScore + " points");
            winCount++;
            points += uAndTeammateScore;
        } else if (uAndTeammateTricks < opponentTricks) {
            opponentScore = opponentTricks - 6;
            System.out.println("you lose by " + opponentScore + " points");
            loseCount++;
            points -= opponentScore;
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        blackBoxTest1 strategyTest = new blackBoxTest1();
        strategyTest.init();
        strategyTest.testStrategy();
    }
}
