package com.weijin.whist.test;

import com.weijin.whist.AIstrategy.*;
import com.weijin.whist.model.*;

import java.io.IOException;
import java.util.*;

public class StrategyTest {
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

    public void setStrategys(Strategy strategy1, Strategy strategy2) {
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }

    public void init() {
        whist = new WhistImpl();
        Player you = new Player();
        you.setId("player1 (you)");
        whist.loadPlayers(you);
    }

    private void testStrategy() throws IOException, ClassNotFoundException {
        this.playerList = whist.getPlayerList();
        this.you = playerList.get(0);
        Player partner = playerList.get(2);
        deck = new Deck(playerList);
        whist.addDeckRound();
//        whist.deckRound = 2;
        deck.initNewDeck(whist.deckRound);
        for (Player player : playerList) {
            player.setTrumpSuit(deck.getCurrentTrump());
        }
        turnList = deck.getTurnList();
        deck.dealCards(turnList);
        for (Player player : playerList) {
            player.setTricks(new ArrayList<>());
        }
        System.out.println(deck.getCurrentTrump());
        int k = 0;
        int j = 0;
        for (Card card : you.getCurrHand()) {
            if (card.getSuit() == deck.getCurrentTrump()) {
                k++;
            }
        }
        for (Card card : playerList.get(2).getCurrHand()) {
            if (card.getSuit() == deck.getCurrentTrump()) {
                j++;
            }
        }
        int round = 0;
        deck.setRound(round);
        while (!deck.isDeckEmpty()) {
            round++;
            deck.setRound(round);
            turnList = deck.getTurnList();
            for (int i = 0; i < turnList.size(); i++) {
                Card AIcard;
                if (turnList.get(i) == this.you || turnList.get(i) == playerList.get(2)) {
                    strategy1 = new HardStrategy();
//                    if (k >= 5||j>=5) {
                    AIcard = strategy1.AIStrategy(turnList.get(i), deck, whist);
//                    } else {
//                        AIcard = strategy2.AIStrategy(turnList.get(i), deck, whist);
//                    }
                } else {
                    strategy2 = new EasyStrategy();
                    AIcard = strategy2.AIStrategy(turnList.get(i), deck, whist);
                }
                if (i == 0) {
                    deck.setCurrentLeadSuit(AIcard.getSuit());
                }
                turnList.get(i).throwCard(AIcard);
                deck.addThisRoundCard(turnList.get(i), AIcard);
                deck.cardsLeftOnDeck--;
//                System.out.println(turnList.get(i).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
            }

            HashMap<Player, Card> biggestCard = deck.getBiggestThisRound();
            for (Player winner : biggestCard.keySet()) {
//                System.out.println(winner.getId() + " biggest: " + biggestCard.get(winner).getSuit() + " " + biggestCard.get(winner).getRank());
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
        StrategyTest strategyTest = new StrategyTest();
        strategyTest.init();
        for (int i = 1; i <= 50000; i++) {
            System.out.println("game " + i);
            strategyTest.testStrategy();
            System.out.println("winning rate: " + (double) strategyTest.winCount / (strategyTest.winCount + strategyTest.loseCount));
            System.out.println("points: " + (double) strategyTest.points / i);
        }

    }
}
