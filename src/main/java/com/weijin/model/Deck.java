package com.weijin.model;

import com.weijin.singleton.FileLogger;

import java.util.*;

import static com.weijin.utils.helper.rankToSymbol;
import static com.weijin.utils.helper.suitToSymbol;

public class Deck {
    //每轮游戏牌桌上的基本信息，公开
    //例如：牌桌上的牌数量，牌桌上剩余的牌（已经出过的牌）
    //游戏开始52张牌，游戏结束时没有牌
    //各个玩家的出牌情况，剩余手牌数量
    public static final int DECK_SIZE = 13 * 4;
    private Suit currentTrump;
    private Suit currentLeadSuit = null;
    private List<Suit> trumpList = List.of(Suit.SPADES, Suit.HEARTS, Suit.CLUBS, Suit.DIAMONDS);
    private List<Player> playerList;
    private List<Player> turnList;
    private HashMap<Player, Card> thisRoundCards = new HashMap<Player, Card>();
    public int cardsLeftOnDeck = DECK_SIZE;
    private List<List<Card>> AllCardLists;
    private List<Card> spadeList = new ArrayList<>(13);
    private List<Card> heartList = new ArrayList<>(13);
    private List<Card> clubList = new ArrayList<>(13);
    private List<Card> diamondList = new ArrayList<>(13);
    private int round = 0;
    private List<Card> leadHistory = new ArrayList<>(13);

    public Deck(List<Player> playerList) {
        this.playerList = playerList;
        for (Player player : playerList) {
            player.setLastCard(null);
            player.TrumpSignal = AIThinking.INITIAL;
            player.strongSuitSignal = null;
            player.weakSuitSignal = null;
            player.hasNoSpadeSignal = false;
            player.hasNoHeartSignal = false;
            player.hasNoClubSignal = false;
            player.hasNoDiamondSignal = false;
            player.discardSuitSignal = null;
        }
        AllCardLists = Arrays.asList(spadeList, heartList, clubList, diamondList);

        for (int i = 1; i <= DECK_SIZE; i++) {
            Card card = new Card(Suit.getSuit((i - 1) / 13), Rank.getRank(i % 13));
            if (card.getRank() != Rank.ACE) {
                AllCardLists.get((i - 1) / 13).add(card);
            }
        }
        for (int i = 0; i < AllCardLists.size(); i++) {
            Card card = new Card(Suit.getSuit(i % 4), Rank.ACE);
            AllCardLists.get(i).add(card);
        }
//        for (Card card : AllCardLists.get(0)) {
//            System.out.println(card.getSuit() + " " + card.getRank());
//        }
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<List<Card>> getAllCardLists() {
        return AllCardLists;
    }

    public List<Player> getTurnList() {
        return turnList;
    }

    public int getCardAtDeck() {
        return this.cardsLeftOnDeck;
    }

    public void cardThrowed() {
        this.cardsLeftOnDeck--;
    }

    public boolean isDeckEmpty() {
        return this.cardsLeftOnDeck <= 0;
    }

    public Suit getCurrentTrump() {
        return currentTrump;
    }

    public void setCurrentTrump(Suit currentTrump) {
        this.currentTrump = currentTrump;
    }

    public void setCurrentTrumpByRound(int deckRound) {
        if (deckRound % 5 == 0) this.currentTrump = null;
        else this.currentTrump = trumpList.get(deckRound % 5 - 1);
    }

    public Suit getCurrentLeadSuit() {
        return currentLeadSuit;
    }

    public void setCurrentLeadSuit(Suit currentLeadSuit) {
        this.currentLeadSuit = currentLeadSuit;
    }

    public HashMap<Player, Card> getThisRoundCards() {
        return thisRoundCards;
    }

    void hasNoSignalChange(Player player) {
        switch (currentLeadSuit) {
            case SPADES -> {
                player.hasNoSpadeSignal = true;
            }
            case HEARTS -> {
                player.hasNoHeartSignal = true;
            }
            case CLUBS -> {
                player.hasNoClubSignal = true;
            }
            case DIAMONDS -> {
                player.hasNoDiamondSignal = true;
            }
        }
    }

    public List<Card> getLeadHistory() {
        return leadHistory;
    }

    public void addThisRoundCard(Player player, Card thisRoundCard) {
        player.setLastCard(thisRoundCard);
        if (turnList.indexOf(player) == 0) {
            leadHistory.add(thisRoundCard);
            if (player.firstLead == null && thisRoundCard.getSuit() != turnList.get(2).strongSuitSignal && thisRoundCard.getSuit() == player.getStrongestSuit()) {
                player.setFirstLead(thisRoundCard);
                if (thisRoundCard.getSuit() == currentTrump && player.isAskingForTrump()) {
                    player.TrumpSignal = AIThinking.CONFIRMED;
                }
            }
        }
        this.thisRoundCards.put(player, thisRoundCard);
        switch (thisRoundCard.getSuit()) {
            case SPADES -> {
                if (currentLeadSuit != Suit.SPADES) {
                    player.discardSuitSignal = thisRoundCard.getSuit();
                    player.hasNoSpadeSignal = true;
                    player.weakSuitSignal = currentLeadSuit;
                }
                for (int i = 0; i < spadeList.size(); i++) {
                    if (spadeList.get(i).getRank() == thisRoundCard.getRank()) {
                        spadeList.remove(i);
                        break;
                    }
                }
//                this.spadeList.remove(thisRoundCard); 经典错误写法，虽然是同样的卡，但是不是同一个对象
            }
            case HEARTS -> {
                if (currentLeadSuit != Suit.HEARTS) {
                    player.discardSuitSignal = thisRoundCard.getSuit();
                    player.hasNoHeartSignal = true;
                    player.weakSuitSignal = currentLeadSuit;
                }
                for (int i = 0; i < heartList.size(); i++) {
                    if (heartList.get(i).getRank() == thisRoundCard.getRank()) {
                        heartList.remove(i);
                        break;
                    }
                }
            }
            case CLUBS -> {
                if (currentLeadSuit != Suit.CLUBS) {
                    player.discardSuitSignal = thisRoundCard.getSuit();
                    player.hasNoClubSignal = true;
                    player.weakSuitSignal = currentLeadSuit;
                }
                for (int i = 0; i < clubList.size(); i++) {
                    if (clubList.get(i).getRank() == thisRoundCard.getRank()) {
                        clubList.remove(i);
                        break;
                    }
                }
            }
            case DIAMONDS -> {
                if (currentLeadSuit != Suit.DIAMONDS) {
                    player.discardSuitSignal = thisRoundCard.getSuit();
                    player.hasNoDiamondSignal = true;
                    player.weakSuitSignal = currentLeadSuit;
                }
                for (int i = 0; i < diamondList.size(); i++) {
                    if (diamondList.get(i).getRank() == thisRoundCard.getRank()) {
                        diamondList.remove(i);
                        break;
                    }
                }
            }
        }
        FileLogger obj = FileLogger.getFileLogger();
        obj.write("\t\t" + player.getId() + " :" + suitToSymbol(thisRoundCard.getSuit()) + " " + rankToSymbol(thisRoundCard.getRank()));
        obj.close();
    }

    public HashMap<Player, Card> getBiggestThisRound() {
        HashMap<Player, Card> biggestThisRound = new HashMap<Player, Card>();
        int max = 0;
        boolean hasTrump = false;
        for (Player player : thisRoundCards.keySet()) {
            if (thisRoundCards.get(player).getSuit() == this.currentTrump) {
                hasTrump = true;
            }
        }
        String msg = null;
        for (Player player : thisRoundCards.keySet()) {
            int value = 0;
            Card card = thisRoundCards.get(player);
            if (hasTrump) {
                if (card.getSuit() == this.currentTrump) {
                    value = card.getRank().getCardValue() * 100;
                } else {
                    value = card.getRank().getCardValue();
                }
            } else {
                if (card.getSuit() == this.currentLeadSuit) {
                    value = card.getRank().getCardValue() * 100;
                } else {
                    value = card.getRank().getCardValue();
                }
            }
            if (value > max) {
                max = value;
                biggestThisRound.clear();
                biggestThisRound.put(player, card);
                msg = "\thighest this round: " + player.getId() + " : " + suitToSymbol(card.getSuit()) + " " + rankToSymbol(card.getRank());
            }
        }
        FileLogger obj = FileLogger.getFileLogger();
        obj.write(msg);
        if (cardsLeftOnDeck != 0) {
            obj.write("\tRound " + (14 - cardsLeftOnDeck / 4));
        }
        obj.close();
        return biggestThisRound;
    }

    public void resetThisRoundCards() {
        this.thisRoundCards.clear();
    }

    public void dealCards(List<Player> players) {
        Dealer dealer = new Dealer();
        dealer.deal(players);
        FileLogger obj = FileLogger.getFileLogger();
        obj.write("\tround " + 1);
        obj.close();
    }

    public void initNewDeckTurn() {
        Random random = new Random();
        int starter = random.nextInt(4);
        setTurnListByStarter(starter, false);
    }

    public void setTurnListByStarter(int starter, boolean toSetTurn) {
        switch (starter) {
            case 0 ->
                    turnList = Arrays.asList(playerList.get(0), playerList.get(1), playerList.get(2), playerList.get(3));
            case 1 ->
                    turnList = Arrays.asList(playerList.get(1), playerList.get(2), playerList.get(3), playerList.get(0));
            case 2 ->
                    turnList = Arrays.asList(playerList.get(2), playerList.get(3), playerList.get(0), playerList.get(1));
            case 3 ->
                    turnList = Arrays.asList(playerList.get(3), playerList.get(0), playerList.get(1), playerList.get(2));
            default -> turnList = playerList;
        }
        if (toSetTurn) {
            turnList.get(1).setTurn(false);
            turnList.get(2).setTurn(false);
            turnList.get(3).setTurn(false);
            turnList.get(0).setTurn(true);

            currentLeadSuit = null;
        }
    }


    public void initNewDeck(int deckRound) {
        initNewDeckTurn();
        setCurrentTrumpByRound(deckRound);
    }

    public boolean declare(Player player, Card card, Deck deck) {
        if (currentLeadSuit == null) {
            return true;
        }
        boolean hasCurrLeadSuit = false;
        for (Card c : player.getCurrHand()) {
            if (c.getSuit() == deck.getCurrentLeadSuit()) {
                hasCurrLeadSuit = true;
                break;
            }
        }
        if (hasCurrLeadSuit && deck.getCurrentLeadSuit() == card.getSuit()) {
            return true;
        } else return !hasCurrLeadSuit;
    }
}
