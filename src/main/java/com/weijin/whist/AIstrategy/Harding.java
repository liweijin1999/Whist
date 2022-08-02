package com.weijin.whist.AIstrategy;


import com.weijin.whist.model.*;

import java.util.*;
import java.util.List;

import static com.weijin.whist.model.Suit.*;

public class Harding implements Strategy {
    List<Card> spadeList = new ArrayList<>();
    List<Card> heartList = new ArrayList<>();
    List<Card> clubList = new ArrayList<>();
    List<Card> diamondList = new ArrayList<>();
    Player you;
    Player partner;
    Deck CurrentDeck;

    public void printAllCards() {
        for (Card card : spadeList) {
            System.out.println(card.getSuit() + " " + card.getRank());
        }
        for (Card card : heartList) {
            System.out.println(card.getSuit() + " " + card.getRank());
        }
        for (Card card : clubList) {
            System.out.println(card.getSuit() + " " + card.getRank());
        }
        for (Card card : diamondList) {
            System.out.println(card.getSuit() + " " + card.getRank());
        }
    }

    public void setHandList(Player player) {
        for (Card card : player.getCurrHand()) {
            if (card.getSuit().equals(SPADES)) {
                spadeList.add(card);
            } else if (card.getSuit().equals(HEARTS)) {
                heartList.add(card);
            } else if (card.getSuit().equals(CLUBS)) {
                clubList.add(card);
            } else if (card.getSuit().equals(Suit.DIAMONDS)) {
                diamondList.add(card);
            }
        }
        spadeList.sort(Comparator.comparing(Card::getValue));
        heartList.sort(Comparator.comparing(Card::getValue));
        clubList.sort(Comparator.comparing(Card::getValue));
        diamondList.sort(Comparator.comparing(Card::getValue));
    }

    private boolean containsRank(List<Card> list, Rank rank) {
        for (Card c : list) {
            if (c.getRank().equals(rank)) {
                return true;
            }
        }
        return false;
    }

    private int containsSequence(List<Card> list, Rank rank) {
        int length = 1;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getRank().equals(rank)) {
                for (int j = i; j > 0; j--) {
                    if (list.get(j).getValue() - list.get(j - 1).getValue() == 1) {
                        length++;
                    } else {
                        break;
                    }
                }
            }
        }
        return length;
    }

    private Card getCardByRank(List<Card> list, Rank rank) {
        for (Card c : list) {
            if (c.getRank().equals(rank)) {
                return c;
            }
        }
        return null;
    }

    public List<List<Card>> getShortestSuitLists() {
        List<List<Card>> shortestSuitLists = new ArrayList<>();
        List<List<Card>> AllSuitLists = new ArrayList<>(List.of(spadeList, heartList, clubList, diamondList));
        int shortestSuitListSize = 13;
        for (List<Card> suitList : AllSuitLists) {
            if (suitList.size() == 0) continue;
            if (suitList.get(0).getSuit() == CurrentDeck.getCurrentTrump()) continue;
            if (suitList.size() < shortestSuitListSize) {
                shortestSuitLists.clear();
                shortestSuitLists.add(suitList);
                shortestSuitListSize = suitList.size();
            } else if (suitList.size() == shortestSuitListSize) {
                shortestSuitLists.add(suitList);
            }
        }
//        System.out.println("shortestSuitLists size: " + shortestSuitLists.size()+shortestSuitLists.get(0).get(0).getSuit());
        return shortestSuitLists;
    }

    private List<Card> haveSingleton() {
        for (List<Card> suitList : getShortestSuitLists()) {
            if (suitList.size() == 1) {
                return suitList;
            }
        }
        return null;
    }

    public List<List<Card>> getLongestSuitLists() {
        List<List<Card>> longestSuitLists = new ArrayList<>();
        List<List<Card>> AllSuitLists = new ArrayList<>(List.of(spadeList, heartList, clubList, diamondList));
        int longestSuitListSize = 0;
        for (List<Card> suitList : AllSuitLists) {
            if (suitList.size() > longestSuitListSize) {
                longestSuitLists.clear();
                longestSuitLists.add(suitList);
                longestSuitListSize = suitList.size();
            } else if (suitList.size() == longestSuitListSize) {
                longestSuitLists.add(suitList);
            }
        }
        return longestSuitLists;
    }

    public List<List<Card>> sortSuitListByLength() {
        List<List<Card>> AllSuitLists = new ArrayList<>(List.of(spadeList, heartList, clubList, diamondList));
        AllSuitLists.sort(Comparator.comparing(List::size));
        return AllSuitLists;
    }

    public List<List<Card>> getWeakestSuitLists() {
        List<List<Card>> weakestSuitLists = new ArrayList<>();
        List<List<Card>> AllSuitLists = new ArrayList<>(List.of(spadeList, heartList, clubList, diamondList));
        double weakestAvgForce = 2.0f;
        for (List<Card> suitList : AllSuitLists) {
            double cardForce = 0.0f;
            for (Card card : suitList) {
                if (card.getValue() >= 2 && card.getValue() <= 5) {
                    //pass
                } else if (card.getValue() >= 6 && card.getValue() <= 9) {
                    cardForce += 1;
                } else if (card.getValue() >= 10 && card.getValue() <= 14) {
                    cardForce += 2;
                }
            }
            if (cardForce / suitList.size() < weakestAvgForce) {
                weakestSuitLists.clear();
                weakestSuitLists.add(suitList);
                weakestAvgForce = cardForce / suitList.size();
            } else if (cardForce / suitList.size() == weakestAvgForce) {
                weakestSuitLists.add(suitList);
            }
        }
        return weakestSuitLists;
    }

    public Card getCardOfMostWeight(HashMap<Card, Integer> cardWeightMap) {
        Card cardOfMostWeight = null;
        int maxWeight = 0;
        for (Card card : cardWeightMap.keySet()) {
            if (cardWeightMap.get(card) > maxWeight) {
                maxWeight = cardWeightMap.get(card);
                cardOfMostWeight = card;
            }
        }
        return cardOfMostWeight;
    }

    private List<Card> hasLeadSuit(Suit leadSuit) {
        List<Card> leadSuitList;
        if (leadSuit == SPADES && spadeList.size() > 0) {
//            hasLeadSuit=true;
            leadSuitList = spadeList;
        } else if (leadSuit == HEARTS && heartList.size() > 0) {
//            hasLeadSuit=true;
            leadSuitList = heartList;
        } else if (leadSuit == CLUBS && clubList.size() > 0) {
//            hasLeadSuit=true;
            leadSuitList = clubList;
        } else if (leadSuit == Suit.DIAMONDS && diamondList.size() > 0) {
//            hasLeadSuit=true;
            leadSuitList = diamondList;
        } else {
            leadSuitList = null;
        }
        return leadSuitList;
    }

    private List<Card> getSubtractList(List<Card> list1, List<Card> list2) {
        // list1: long list, list2: short list
        List<Card> subtractList = new ArrayList<>(list1);
        for (Card card : list2) {
            if (containsRank(subtractList, card.getRank())) {
                subtractList.remove(getCardByRank(subtractList, card.getRank()));
            }
        }
        return subtractList;
    }

    private boolean hasBringIn(Deck deck, List<Card> strongSuitList, List<Card> trumpSuitList) {
        if (strongSuitList.size() == 0) {
            return false;
        }
        int strongSuitIndex = switch (strongSuitList.get(0).getSuit()) {
            case SPADES -> 0;
            case HEARTS -> 1;
            case CLUBS -> 2;
            case DIAMONDS -> 3;
        };
        int trumpSuitIndex = switch (deck.getCurrentTrump()) {
            case SPADES -> 0;
            case HEARTS -> 1;
            case CLUBS -> 2;
            case DIAMONDS -> 3;
        };
        List<Card> otherCardCollection = getSubtractList(deck.getAllCardLists().get(strongSuitIndex), strongSuitList);
        List<Card> trumpCardCollection = getSubtractList(deck.getAllCardLists().get(trumpSuitIndex), trumpSuitList);
        int otherCardCollectionSize = otherCardCollection.size();
        int trumpCardCollectionSize = trumpCardCollection.size();
        if (partner.TrumpSignal == AIThinking.CONFIRMED) {
            trumpCardCollectionSize -= 5;
        }
        if (trumpCardCollectionSize <= 0) {
            if (otherCardCollectionSize == 0) {
                return true;
            } else if (otherCardCollectionSize == 1 && strongSuitList.get(strongSuitList.size() - 1).getValue() > otherCardCollection.get(0).getValue()) {
                return true;
            } else if (otherCardCollectionSize == 2 && strongSuitList.size() >= 2) {
                if (strongSuitList.get(strongSuitList.size() - 2).getValue() > otherCardCollection.get(1).getValue()) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    private Suit getOneLastSuit(Suit suit1, Suit suit2, Suit suit3) {
        List<Suit> suitList = new ArrayList<>(List.of(SPADES, HEARTS, CLUBS, DIAMONDS));
        suitList.remove(suit1);
        suitList.remove(suit2);
        suitList.remove(suit3);
        System.out.println(suitList.get(0));
        return suitList.get(0);
    }

    private int getSuitIndex(Suit suit) {
        return switch (suit) {
            case SPADES -> 0;
            case HEARTS -> 1;
            case CLUBS -> 2;
            case DIAMONDS -> 3;
        };
    }

    private int sameValAsBestCardPos(List<Card> cardList) {
        Card bestCard = cardList.get(cardList.size() - 1);
        Card sameValCard = bestCard;
        int sameValCardPos = cardList.size() - 1;
        if (containsRank(cardList, bestCard.getRank())) {
            if (cardList.size() > 1) {
                if (containsSequence(cardList, bestCard.getRank()) > 1) {
                    sameValCardPos = cardList.size() - containsSequence(cardList, bestCard.getRank());
                    sameValCard = cardList.get(sameValCardPos);
                }
                if (sameValCardPos == 0) {
                    System.out.println("test000000");
                    return sameValCardPos;
                }
                int bestVal = sameValCard.getValue();
                int secVal = cardList.get(sameValCardPos - 1).getValue();
                for (int i = bestVal - 1; i > secVal; i--) {
                    if (containsRank(CurrentDeck.getAllCardLists().get(getSuitIndex(bestCard.getSuit())), Rank.getRank(i))) {
                        System.out.println("test111111");
                        return sameValCardPos;
                    }
                }
                sameValCardPos--;
                sameValCard = cardList.get(sameValCardPos);
                if (containsSequence(cardList, sameValCard.getRank()) > 1) {
                    sameValCardPos = sameValCardPos + 1 - containsSequence(cardList, sameValCard.getRank());
                    System.out.println("test222222");
                    return sameValCardPos;
                }
            }
        }
        System.out.println("test333333");
        return sameValCardPos;
    }

    public Card at1stHand(Deck deck) {
        partner = getPartner(deck);
        you = deck.getTurnList().get(0);
        Player opponent1 = deck.getTurnList().get(1);
        Player opponent2 = deck.getTurnList().get(3);
        setHandList(you);
        Suit trumpSuit = deck.getCurrentTrump();
        boolean NoTrumpThisRound = trumpSuit == null;
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
        Suit strongestSuit = you.getStrongestSuit();
        List<Card> strongestSuitList = getListBySuit(strongestSuit);

        int suitLedTimes = 0;
        for (Card card : CurrentDeck.getLeadHistory()) {
            if (card.getSuit() == strongestSuit) {
                suitLedTimes++;
            }
        }
        boolean originalLead = suitLedTimes == 0;
        boolean suitLedForOnce = suitLedTimes == 1;

        List<List<Card>> suitListsByLength = sortSuitListByLength();
        //reverse suitListsByLength
        List<List<Card>> suitListsByLengthReversed = new ArrayList<>();
        for (int i = suitListsByLength.size() - 1; i >= 0; i--) {
            suitListsByLengthReversed.add(suitListsByLength.get(i));
        }
        if (!NoTrumpThisRound) {
            // there is a trump
            List<Card> trumpSuitList = getListBySuit(trumpSuit);
            boolean bringIn = hasBringIn(deck, strongestSuitList, trumpSuitList);
            System.out.println("bring in: " + bringIn);
            if (bringIn && strongestSuit != trumpSuit) {
                return strongestSuitList.get(strongestSuitList.size() - 1);
            }
            if (partner.strongSuitSignal != null &&
                    partner.strongSuitSignal != deck.getPlayerList().get(3).strongSuitSignal &&
                    partner.strongSuitSignal != deck.getPlayerList().get(1).strongSuitSignal) {
                // if you hold the best card of partner's suit, lead it before opening you own
                int patnerSuitLedTimes = 0;
                for (Card card : CurrentDeck.getLeadHistory()) {
                    if (card.getSuit() == partner.strongSuitSignal) {
                        patnerSuitLedTimes++;
                    }
                }
                if (patnerSuitLedTimes < 2) {
                    List<Card> partnerStrongSuitList = getListBySuit(partner.strongSuitSignal);
                    List<Card> otherCardCollection = getSubtractList(deck.getAllCardLists().get(getSuitIndex(partner.strongSuitSignal)), partnerStrongSuitList);
                    for (Card card : partnerStrongSuitList) {
                        if (otherCardCollection.size() == 0) {
                            return partnerStrongSuitList.get(partnerStrongSuitList.size() - 1);
                        }
                        if (card.getValue() > otherCardCollection.get(otherCardCollection.size() - 1).getValue()) {
                            return partnerStrongSuitList.get(partnerStrongSuitList.size() - 1);
                        }
                    }
                    if (partnerStrongSuitList.size() == 2) {
                        return partnerStrongSuitList.get(1);
                    } else if (partnerStrongSuitList.size() > 0) {
                        return partnerStrongSuitList.get(0);
                    }
                }
            }
            if (you.isAskingForTrump()) {
                if (originalLead) {
                    if (you.firstLead == null && trumpSuitList.size() >= 4) {
                        you.TrumpSignal = AIThinking.CONFIRMED;
                        you.strongSuitSignal = strongestSuit;
                        return trumpSuitList.get(trumpSuitList.size() - 4);
                    }
                }
                if (trumpSuitList.size() > 0) {
                    return trumpSuitList.get(0);
                } else {
                    return longestSuitLists.get(0).get(0);
                }
            } else if (partner.TrumpSignal == AIThinking.CONFIRMED) {
                if (trumpSuitList.size() > 0) {
                    return trumpSuitList.get(trumpSuitList.size() - 1);
                } else {
                    return shortestSuitLists.get(0).get(0);
                }
            } else {
                System.out.println(partner.TrumpSignal + " " + partner.weakSuitSignal + " " + strongestSuit);
                if (partner.TrumpSignal == AIThinking.DENIED && partner.weakSuitSignal != null && partner.discardSuitSignal != null) {
                    switch (getOneLastSuit(trumpSuit, partner.discardSuitSignal, partner.weakSuitSignal)) {
                        case SPADES -> {
                            if (spadeList.size() > 0) {
                                return spadeList.get(spadeList.size() - 1);
                            }
                        }
                        case HEARTS -> {
                            if (heartList.size() > 0) {
                                return heartList.get(heartList.size() - 1);
                            }
                        }
                        case CLUBS -> {
                            if (clubList.size() > 0) {
                                return clubList.get(clubList.size() - 1);
                            }
                        }
                        case DIAMONDS -> {
                            if (diamondList.size() > 0) {
                                return diamondList.get(diamondList.size() - 1);
                            }
                        }
                    }
                }
                if (suitLedForOnce) {
                    if (you.firstLead != null) {
                        if (you.firstLead.getSuit() == strongestSuit && you.firstLead.getValue() < 10 && strongestSuitList.size() > 0) {
                            if (strongestSuitList.get(strongestSuitList.size() - 1).getValue() > 10) {
                                return strongestSuitList.get(strongestSuitList.size() - 1);
                            }
                        }
                    }
                    //in the second round, lead best if you have
                    List<Card> otherCardCollection = getSubtractList(deck.getAllCardLists().get(getSuitIndex(strongestSuit)), strongestSuitList);
                    for (Card card : strongestSuitList) {
                        if (otherCardCollection.size() == 0) {
                            if (strongestSuitList.size() == 3 && strongestSuitList.size() - sameValAsBestCardPos(strongestSuitList) >= 2) {
                                return strongestSuitList.get(strongestSuitList.size() - 2);
                            } else if (strongestSuitList.size() >= 4) {
                                return strongestSuitList.get(sameValAsBestCardPos(strongestSuitList));
                            } else {
                                return strongestSuitList.get(sameValAsBestCardPos(strongestSuitList));
                            }
                        }
                        if (card.getValue() > otherCardCollection.get(otherCardCollection.size() - 1).getValue()) {
                            if (strongestSuitList.size() == 3 && strongestSuitList.size() - sameValAsBestCardPos(strongestSuitList) >= 2) {
                                return strongestSuitList.get(strongestSuitList.size() - 2);
                            } else if (strongestSuitList.size() >= 4) {
                                return strongestSuitList.get(sameValAsBestCardPos(strongestSuitList));
                            } else {
                                return strongestSuitList.get(sameValAsBestCardPos(strongestSuitList));
                            }
                        }
                    }
                }
                if (containsRank(strongestSuitList, Rank.KING)) {
                    // king leads
                    if (containsRank(strongestSuitList, Rank.ACE)) {
                        if (you.firstLead == null) {
                            you.strongSuitSignal = strongestSuit;
                        }
                        return getCardByRank(strongestSuitList, Rank.KING);
                    } else if (containsRank(strongestSuitList, Rank.QUEEN) && containsRank(strongestSuitList, Rank.JACK) && strongestSuitList.size() >= 5) {
                        //lead jack
                        if (you.firstLead == null) {
                            you.strongSuitSignal = strongestSuit;
                        }
                        return getCardByRank(strongestSuitList, Rank.JACK);
                    } else if (containsRank(strongestSuitList, Rank.QUEEN)) {
                        if (you.firstLead == null) {
                            you.strongSuitSignal = strongestSuit;
                        }
                        return getCardByRank(strongestSuitList, Rank.KING);
                    }
                } else if (containsRank(strongestSuitList, Rank.ACE) && strongestSuitList.size() >= 3) {
                    // ace leads
                    if (containsRank(strongestSuitList, Rank.QUEEN) && containsRank(strongestSuitList, Rank.JACK)) {
                        if (you.firstLead == null) {
                            you.strongSuitSignal = strongestSuit;
                        }
                        return getCardByRank(strongestSuitList, Rank.ACE);
                    } else if (strongestSuitList.size() >= 5) {
                        if (you.firstLead == null) {
                            you.strongSuitSignal = strongestSuit;
                        }
                        return getCardByRank(strongestSuitList, Rank.ACE);
                    }
                } else if (containsRank(strongestSuitList, Rank.QUEEN)) {
                    //queen leads
                    if (containsRank(strongestSuitList, Rank.JACK) && containsRank(strongestSuitList, Rank.TEN)) {
                        if (you.firstLead == null) {
                            you.strongSuitSignal = strongestSuit;
                        }
                        return getCardByRank(strongestSuitList, Rank.QUEEN);
                    }
                } else if (containsRank(strongestSuitList, Rank.TEN)) {
                    //ten leads
                    if (containsRank(strongestSuitList, Rank.KING) && containsRank(strongestSuitList, Rank.JACK) && strongestSuitList.size() >= 5) {
                        if (you.firstLead == null) {
                            you.strongSuitSignal = strongestSuit;
                        }
                        return getCardByRank(strongestSuitList, Rank.TEN);
                    }
                } else {
                    if (strongestSuitList.size() >= 4) {
                        if (you.firstLead == null) {
                            you.strongSuitSignal = strongestSuit;
                        }
                        return strongestSuitList.get((strongestSuitList.size() - 4));
                    }
                }


                if (strongestSuitList.size() > 0) {
                    switch (strongestSuit) {
                        case SPADES -> {
                            if (opponent1.hasNoSpadeSignal || opponent2.hasNoSpadeSignal) {
                                return strongestSuitList.get(strongestSuitList.size() - 1);
                            }
                        }
                        case HEARTS -> {
                            if (opponent1.hasNoHeartSignal || opponent2.hasNoHeartSignal) {
                                return strongestSuitList.get(strongestSuitList.size() - 1);
                            }
                        }
                        case CLUBS -> {
                            if (opponent1.hasNoClubSignal || opponent2.hasNoClubSignal) {
                                return strongestSuitList.get(strongestSuitList.size() - 1);
                            }
                        }
                        case DIAMONDS -> {
                            if (opponent1.hasNoDiamondSignal || opponent2.hasNoDiamondSignal) {
                                return strongestSuitList.get(strongestSuitList.size() - 1);
                            }
                        }
                    }
                    if (strongestSuitList.get(strongestSuitList.size() - 1).getValue() >= 10) {
                        return strongestSuitList.get(strongestSuitList.size() - 1);
                    }
                    return strongestSuitList.get(0);
                }

            }
            return null;
        } else {
            for (List<Card> suitList : suitListsByLengthReversed) {
                if (containsRank(suitList, Rank.ACE) && containsRank(suitList, Rank.KING)) {
                    return getCardByRank(suitList, Rank.KING);
                } else if (containsRank(suitList, Rank.KING) && containsRank(suitList, Rank.QUEEN)) {
                    return getCardByRank(suitList, Rank.KING);
                } else if (containsRank(suitList, Rank.KING) && containsSequence(suitList, Rank.KING) > 1) {
                    return suitList.get(suitList.indexOf(getCardByRank(suitList, Rank.KING)) - (containsSequence(suitList, Rank.KING) - 1));
                } else if (containsRank(suitList, Rank.ACE) && containsRank(suitList, Rank.QUEEN) && containsRank(suitList, Rank.JACK)) {
                    return getCardByRank(suitList, Rank.ACE);
                } else if (containsRank(suitList, Rank.ACE) && !containsRank(suitList, Rank.KING) && !containsRank(suitList, Rank.QUEEN) && !containsRank(suitList, Rank.JACK) && suitList.size() >= 5) {
                    return getCardByRank(suitList, Rank.ACE);
                } else if (containsRank(suitList, Rank.QUEEN) && containsRank(suitList, Rank.JACK) && containsRank(suitList, Rank.TEN)) {
                    return getCardByRank(suitList, Rank.QUEEN);
                } else if (containsRank(suitList, Rank.JACK) && containsRank(suitList, Rank.TEN) && containsRank(suitList, Rank.NINE)) {
                    return getCardByRank(suitList, Rank.JACK);
                } else if (containsRank(suitList, Rank.KING) && containsRank(suitList, Rank.JACK) && containsRank(suitList, Rank.TEN) && containsRank(suitList, Rank.NINE) && suitList.size() == 5) {
                    return getCardByRank(suitList, Rank.NINE);
                } else if (containsRank(suitList, Rank.KING) && containsRank(suitList, Rank.JACK) && containsRank(suitList, Rank.TEN) && suitList.size() >= 4) {
                    return getCardByRank(suitList, Rank.TEN);
                }
            }
            return suitListsByLengthReversed.get(0).get(0);
        }
    }

    private Player getPartner(Deck deck) {
        return deck.getTurnList().get(2);
    }

    private List<Card> getListBySuit(Suit trumpSuit) {
        return switch (trumpSuit) {
            case SPADES -> spadeList;
            case HEARTS -> heartList;
            case CLUBS -> clubList;
            case DIAMONDS -> diamondList;
        };
    }

    public Card at2ndHand(Deck deck) {
        HashMap<Player, Card> biggestInfo = deck.getBiggestThisRound();
        partner = deck.getTurnList().get(3);
        you = deck.getTurnList().get(1);
        setHandList(you);
        boolean NoTrumpThisRound = deck.getCurrentTrump() == null;

        HashMap<Card, Integer> cardWeightMap = new HashMap<>();
        List<List<Card>> weakestSuitLists = getWeakestSuitLists();
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
        Suit leadSuit = deck.getCurrentLeadSuit();
        List<Card> leadSuitList = hasLeadSuit(leadSuit);

        Card leadCard = null;

        for (Player player : biggestInfo.keySet()) {
            leadCard = biggestInfo.get(player);
        }
        if (leadSuitList != null) {
            //has lead suit
            int suitLedTimes = 0;
            for (Card card : CurrentDeck.getLeadHistory()) {
                if (card.getSuit() == leadSuit) {
                    suitLedTimes++;
                }
            }
            boolean firstRoundOfSuitLed = suitLedTimes == 1;
            boolean secondRoundOfSuitLed = suitLedTimes == 2;
            if (leadSuit == CurrentDeck.getCurrentTrump()) {
                if (firstRoundOfSuitLed) {
                    return leadSuitList.get(0);
                }
            } else {
                assert leadCard != null;
                int largeCard = 0;
                for (Card card : leadSuitList) {
                    if (card.getValue() >= 10) {
                        largeCard += 1;
                    }
                }
                if (leadCard.getValue() >= 11 && leadCard.getValue() < 14) {
                    // honour led
                    if (containsRank(leadSuitList, Rank.ACE)) {
                        return getCardByRank(leadSuitList, Rank.ACE);
                    } else {
                        return leadSuitList.get(0);
                    }
                }
                if (leadCard.getValue() == 10) {
                    // 10 led
                    if (leadSuitList.size() == 2 && containsRank(leadSuitList, Rank.QUEEN) && containsRank(leadSuitList, Rank.TWO)) {
                        //very exception case
                        return getCardByRank(leadSuitList, Rank.QUEEN);
                    } else {
                        return leadSuitList.get(0);
                    }
                }
                if (leadSuitList.size() >= 4 && largeCard >= 2) {
                    //special combinations to follow large
                    if (largeCard >= 3) {
                        //win the card as cheaply as possible
                        for (Card card : leadSuitList) {
                            if (card.getValue() > leadCard.getValue() && card.getValue() >= 10) {
                                return card;
                            }
                        }
                    }
                    if (largeCard == 2) {
                        //win the card as cheaply as possible
                        if (containsRank(leadSuitList, Rank.ACE) && containsRank(leadSuitList, Rank.KING)) {
                            return getCardByRank(leadSuitList, Rank.ACE);
                        } else if (containsRank(leadSuitList, Rank.KING) && containsRank(leadSuitList, Rank.QUEEN)) {
                            return getCardByRank(leadSuitList, Rank.KING);
                        } else {
                            return leadSuitList.get(0);
                        }
                    }
                }
                if (leadCard.getValue() < 10) {
                    int remainder = numberOfCardsAgainstLead(leadCard, leadSuitList);
                    // small card led
                    if (remainder == 0) {
                        //win the card as cheaply as possible
                        for (Card card : leadSuitList) {
                            if (card.getValue() > leadCard.getValue()) {
                                return card;
                            }
                        }
                    }
                }
            }
            if (secondRoundOfSuitLed) {
                // play the best card if you have
                if (leadSuitList.get(leadSuitList.size() - 1).getValue() < leadCard.getValue()) {
                    return leadSuitList.get(0);
                }
                List<Card> otherCardCollection = getSubtractList(deck.getAllCardLists().get(getSuitIndex(leadSuit)), leadSuitList);
                for (Card card : leadSuitList) {
                    if (otherCardCollection.size() == 0) {
                        if (leadSuitList.size() == 3 && leadSuitList.size() - sameValAsBestCardPos(leadSuitList) >= 2) {
                            return leadSuitList.get(leadSuitList.size() - 2);
                        } else if (leadSuitList.size() >= 4) {
                            return leadSuitList.get(sameValAsBestCardPos(leadSuitList));
                        } else {
                            return leadSuitList.get(sameValAsBestCardPos(leadSuitList));
                        }
                    }
                    if (card.getValue() > otherCardCollection.get(otherCardCollection.size() - 1).getValue()) {
                        if (leadSuitList.size() == 3 && leadSuitList.size() - sameValAsBestCardPos(leadSuitList) >= 2) {
                            return leadSuitList.get(leadSuitList.size() - 2);
                        } else if (leadSuitList.size() >= 4) {
                            return leadSuitList.get(sameValAsBestCardPos(leadSuitList));
                        } else {
                            return leadSuitList.get(sameValAsBestCardPos(leadSuitList));
                        }
                    }
                }
            }
            return leadSuitList.get(0);
        } else {
            //have no lead suit
            if (!NoTrumpThisRound) {
                // have trump this round
                for (Card card : you.getCurrHand()) {
                    int weight = 0;
                    // lower card gets more weight
                    weight += (14 - card.getValue());
                    if (card.getSuit().equals(deck.getCurrentTrump())) {
                        // trump card gets more weight
                        weight += 14;
                    }
                    for (List<Card> suitList : shortestSuitLists) {
                        if (suitList.contains(card)) {
                            // shortest suit card gets more weight
                            weight += 1;
                        }
                    }
                    for (List<Card> suitList : weakestSuitLists) {
                        if (suitList.contains(card)) {
                            // card in the weakest suit gets more weight
                            weight += 1;
                        }
                    }
                    cardWeightMap.put(card, weight);
                }
                return getCardOfMostWeight(cardWeightMap);
            } else {
                // no trump this round
                for (Card card : you.getCurrHand()) {
                    int weight = 0;
                    // lower card gets more weight
                    weight += (14 - card.getValue()) * 2;
                    for (List<Card> suitList : longestSuitLists) {
                        if (suitList.contains(card)) {
                            // card in the longest suit gets more weight
                            weight += 1;
                        }
                    }
                    for (List<Card> suitList : weakestSuitLists) {
                        if (suitList.contains(card)) {
                            // card in the weakest suit gets more weight
                            weight += 1;
                        }
                    }
                    cardWeightMap.put(card, weight);
                }
                return getCardOfMostWeight(cardWeightMap);
            }
        }
    }

    public Card at3rdHand() {
        HashMap<Player, Card> biggestInfo = CurrentDeck.getBiggestThisRound();
        partner = CurrentDeck.getTurnList().get(0);
        you = CurrentDeck.getTurnList().get(2);
        setHandList(you);
        HashMap<Player, Card> thisRoundCards = CurrentDeck.getThisRoundCards();
        boolean partnerWon = false;
        boolean NoTrumpThisRound = CurrentDeck.getCurrentTrump() == null;
        List<List<Card>> weakestSuitLists = getWeakestSuitLists();
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
        Suit leadSuit = CurrentDeck.getCurrentLeadSuit();
        List<Card> leadSuitList = hasLeadSuit(leadSuit);
        Card biggestCard = null;
        for (Player player : biggestInfo.keySet()) {
            biggestCard = biggestInfo.get(player);
        }
        if (leadSuitList != null) {
            // have lead suit
            int suitIndex = getSuitIndex(leadSuit);
            int suitLedTimes = 0;
            for (Card card : this.CurrentDeck.getLeadHistory()) {
                if (card.getSuit() == leadSuit) {
                    suitLedTimes++;
                }
            }
            boolean firstRoundOfSuitLed = suitLedTimes == 1;
            boolean secondRoundOfSuitLed = suitLedTimes == 2;

            if (firstRoundOfSuitLed) {
                if (partner.strongSuitSignal == null && partner.getStrongestSuit() == leadSuit) {
                    partner.strongSuitSignal = leadSuit;
                }
                if (leadSuit == CurrentDeck.getCurrentTrump() && partner.TrumpSignal != AIThinking.CONFIRMED && partner.isAskingForTrump()) {
                    partner.TrumpSignal = AIThinking.CONFIRMED;
                }
            }
            // if have larger card, play the biggest, else play the smallest
            boolean haveLargerCard = false;
            for (Card card : leadSuitList) {
                assert biggestCard != null;
                if (card.getValue() > biggestCard.getValue()) {
                    haveLargerCard = true;
                }
            }
            List<Card> otherCardCollection = getSubtractList(CurrentDeck.getAllCardLists().get(suitIndex), leadSuitList);
            assert biggestCard != null;
            if (thisRoundCards.get(partner).getValue() == biggestCard.getValue()) {
                if (otherCardCollection.size() == 0) {
                    partnerWon = true;
                } else if (thisRoundCards.get(partner).getValue() > otherCardCollection.get(otherCardCollection.size() - 1).getValue()) {
                    partnerWon = true;
                }
            }
            if (haveLargerCard && !partnerWon) {
                boolean finesse = false;
                if (firstRoundOfSuitLed || you.getStrongestSuit() == CurrentDeck.getCurrentTrump() || you.getStrongestSuit() == leadSuit ||
                        partner.weakSuitSignal == leadSuit || CurrentDeck.getAllCardLists().get(suitIndex).size() >= 11) {
                    finesse = true;
                } else if (partner.strongSuitSignal == leadSuit || CurrentDeck.getAllCardLists().get(suitIndex).size() < 11) {
                    finesse = false;
                }
                if (containsRank(leadSuitList, Rank.ACE) && containsRank(leadSuitList, Rank.QUEEN)) {
                    if (!finesse) {
                        return getCardByRank(leadSuitList, Rank.ACE);
                    } else {
                        return getCardByRank(leadSuitList, Rank.QUEEN);
                    }
                }
                return leadSuitList.get(leadSuitList.size() - 1);
            } else {
                if (you.isAskingForTrump() && leadSuitList.size() >= 2 && you.TrumpSignal == AIThinking.INITIAL) {
                    System.out.println(you.getId() + " is trying to ask for trump");
                    you.TrumpSignal = AIThinking.GUESSING;
                    return leadSuitList.get(1);
                } else if (you.isAskingForTrump() && you.TrumpSignal == AIThinking.GUESSING) {
                    if (you.getLastCard() == null) {
                        return leadSuitList.get(0);
                    }
                    if (you.getLastCard().getSuit() == leadSuitList.get(0).getSuit()) {
                        if (you.getLastCard().getValue() > leadSuitList.get(0).getValue()) {
                            you.TrumpSignal = AIThinking.CONFIRMED;
                            System.out.println(you.getId() + " asking for trump CONFIRMED");
                        }
                    }
                    return leadSuitList.get(0);
                } else if (you.TrumpSignal == AIThinking.INITIAL) {
                    if (leadSuitList.get(0).getRank() == Rank.TWO) {
                        you.TrumpSignal = AIThinking.DENIED;
                    }
                    return leadSuitList.get(0);
                } else if (you.TrumpSignal == AIThinking.GUESSING) {
                    if (you.getLastCard().getSuit() == leadSuitList.get(0).getSuit()) {
                        if (you.getLastCard().getValue() < leadSuitList.get(0).getValue()) {
                            you.TrumpSignal = AIThinking.DENIED;
                            System.out.println(you.getId() + " asking for trump DENIED");
                        }
                    }
                    return leadSuitList.get(0);
                } else {
                    if (otherCardCollection.size() == 0) {
                        return leadSuitList.get(leadSuitList.size() - 1);
                    }
                    if (!firstRoundOfSuitLed && leadSuit == partner.strongSuitSignal) {
                        if (leadSuitList.get(leadSuitList.size() - 1).getValue() >= 10 &&
                                leadSuitList.get(leadSuitList.size() - 1).getValue() > otherCardCollection.get(otherCardCollection.size() - 1).getValue()) {
                            return leadSuitList.get(leadSuitList.size() - 1);
                        }
                    }
                    return leadSuitList.get(0);
                }
            }
        } else {
            // no lead suit
            if (!NoTrumpThisRound) {
                // have trump this round
                you.weakSuitSignal = leadSuit;
                return discardHT(CurrentDeck, shortestSuitLists, weakestSuitLists);
            } else {
                // no trump this round
                return discardNT(longestSuitLists, weakestSuitLists);
            }
        }
    }

    public Card at4thHand(Deck deck) {
        // 第四手出牌，尽可能用最小的牌赢下这一墩
        // 如果没有更大的牌，则按照弃牌顺序弃牌
        HashMap<Player, Card> biggestInfo = deck.getBiggestThisRound();
        partner = deck.getTurnList().get(1);
        you = deck.getTurnList().get(3);
        setHandList(you);
//        HashMap<Player, Card> thisRoundCards = deck.getThisRoundCards();
        boolean partnerWon = false;
        boolean NoTrumpThisRound = deck.getCurrentTrump() == null;
        List<List<Card>> weakestSuitLists = getWeakestSuitLists();
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
        Suit leadSuit = deck.getCurrentLeadSuit();
        List<Card> leadSuitList = hasLeadSuit(leadSuit);
        Card biggestCard = null;

        for (Player player : biggestInfo.keySet()) {
            biggestCard = biggestInfo.get(player);
            if (player.equals(partner)) {
                partnerWon = true;
                break;
            }
        }
        if (partnerWon) {
            // partner won this round
            if (!NoTrumpThisRound) {
                // trump ある this round
                if (leadSuitList != null) {
                    // has lead suit
                    // play the lowest card of lead suit
                    System.out.println("here" + you.isAskingForTrump() + you.TrumpSignal);
                    if (you.isAskingForTrump() && leadSuitList.size() >= 2 && you.TrumpSignal == AIThinking.INITIAL) {
                        System.out.println(you.getId() + " is trying to ask for trump");
                        you.TrumpSignal = AIThinking.GUESSING;
                        return leadSuitList.get(1);
                    } else if (you.isAskingForTrump() && you.TrumpSignal == AIThinking.GUESSING) {
                        if (you.getLastCard().getSuit() == leadSuit) {
                            if (you.getLastCard().getValue() > leadSuitList.get(0).getValue()) {
                                you.TrumpSignal = AIThinking.CONFIRMED;
                                System.out.println(you.getId() + " asking for trump CONFIRMED");
                            }
                        }
                        return leadSuitList.get(0);
                    } else {
                        if (you.getLastCard() == null) {
                            return leadSuitList.get(0);
                        }
                        if (you.getLastCard().getSuit() == leadSuitList.get(0).getSuit()) {
                            if (you.getLastCard().getValue() < leadSuitList.get(0).getValue()) {
                                you.TrumpSignal = AIThinking.DENIED;
                                System.out.println(you.getId() + " asking for trump DENIED");
                            }
                        }
                        if (leadSuitList.get(0).getRank() == Rank.TWO) {
                            you.TrumpSignal = AIThinking.DENIED;
                        }
                        return leadSuitList.get(0);
                    }
                } else {
                    // has no lead suit
                    you.weakSuitSignal = leadSuit;
                    return discardHT(deck, shortestSuitLists, weakestSuitLists);
                }
            } else {
                // no trump this round
                if (leadSuitList != null) {
                    // has lead suit
                    //play the lowest card of lead suit
                    return leadSuitList.get(0);
                } else {
                    // has no lead suit
                    return discardNT(longestSuitLists, weakestSuitLists);
                }
            }
        } else {
            // your partner didn't win this round
            if (!NoTrumpThisRound) {
                // trump ある this round
                if (leadSuitList != null) {
                    // has lead suit
                    // play the minimax card of lead suit
                    for (Card card : leadSuitList) {
                        assert biggestCard != null;
                        if (card.getValue() > biggestCard.getValue()) {
                            return card;
                        }
                    }
                    if (you.isAskingForTrump() && leadSuitList.size() >= 2 && you.TrumpSignal == AIThinking.INITIAL) {
                        System.out.println(you.getId() + " is trying to ask for trump");
                        you.TrumpSignal = AIThinking.GUESSING;
                        return leadSuitList.get(1);
                    } else if (you.isAskingForTrump() && you.TrumpSignal == AIThinking.GUESSING) {
                        if (you.getLastCard().getSuit() == leadSuitList.get(0).getSuit()) {
                            if (you.getLastCard().getValue() > leadSuitList.get(0).getValue()) {
                                you.TrumpSignal = AIThinking.CONFIRMED;
                                System.out.println(you.getId() + " asking for trump CONFIRMED");
                            }
                        }
                        return leadSuitList.get(0);
                    } else {
                        return leadSuitList.get(0);
                    }
                } else {
                    // has no lead suit
                    you.weakSuitSignal = leadSuit;
                    return discardHT(deck, shortestSuitLists, weakestSuitLists);
                }
            } else {
                // no trump this round
                if (leadSuitList != null) {
                    // has lead suit
                    // play the minimax card of lead suit
                    for (Card card : leadSuitList) {
                        assert biggestCard != null;
                        if (card.getValue() > biggestCard.getValue()) {
                            return card;
                        }
                    }
                    return leadSuitList.get(0);
                } else {
                    // has no lead suit
                    you.weakSuitSignal = leadSuit;
                    return discardNT(longestSuitLists, weakestSuitLists);
                }
            }
        }
    }

    private Card discardHT(Deck deck, List<List<Card>> shortestSuitLists, List<List<Card>> weakestSuitLists) {
        HashMap<Card, Integer> cardWeightMap = new HashMap<>();
        List<Card> trumpSuitList = getListBySuit(deck.getCurrentTrump());
        List<Card> strongestSuitList = hasLeadSuit(you.getStrongestSuit());
        int large = 0;
        for (Card card : trumpSuitList) {
            if (card.getValue() > 10) {
                large++;
            }
        }
        for (Card card : you.getCurrHand()) {
            int weight = 0;
            // lower card gets more weight
            weight += (14 - card.getValue());
            if (card.getSuit().equals(deck.getCurrentTrump())) {
                // trump card gets more weight
                if (deck.getCurrentLeadSuit().equals(partner.strongSuitSignal)) {
                    weight -= 14;
                } else if (trumpSuitList.size() == 4 && large >= 2) {
                    weight -= 14;
                } else if (trumpSuitList.size() == 3 && large == 3) {
                    weight -= 14;
                } else {
                    weight += 14;
                }
            }
            if (strongestSuitList != null && strongestSuitList.contains(card) && strongestSuitList.size() >= 2) {
                // shortest suit card gets more weight
                weight -= 20;
            }
            for (List<Card> suitList : shortestSuitLists) {
                if (suitList.contains(card)) {
                    // shortest suit card gets more weight
                    if (card.getValue() < 11) {
                        weight += 10;
                    }
                }
            }
            for (List<Card> suitList : weakestSuitLists) {
                if (suitList.contains(card)) {
                    // card in the weakest suit gets more weight
                    weight += 3;
                }
            }
            if (card.getRank() == Rank.TWO && card.getSuit() == CLUBS)
                System.out.println("two of clubs" + weight);
            if (card.getRank() == Rank.THREE && card.getSuit() == Suit.DIAMONDS)
                System.out.println("three of diamond" + weight);
            cardWeightMap.put(card, weight);
        }
        return getCardOfMostWeight(cardWeightMap);
    }

    private int numberOfAgainstPartnerLead(Card lead) {
        //The eleven rule no.1
        int val = lead.getValue();
        if (val < 10) {
            return 11 - val;
        }
        return -1;
    }

    private int numberOfCardsAgainstLead(Card lead, List<Card> hand) {
        //The eleven rule no.2
        int count = 0;
        for (Card card : hand) {
            if (card.getValue() > lead.getValue()) {
                count++;
            }
        }
        List<Card> otherCardCollection = getSubtractList(CurrentDeck.getAllCardLists().get(getSuitIndex(lead.getSuit())), hand);
        for (int i = lead.getValue() + 1; i <= 14; i++) {
            if (!containsRank(otherCardCollection, Rank.getRank(i))) {
                count++;
            }
        }
        if (numberOfAgainstPartnerLead(lead) > 0) {
            return numberOfAgainstPartnerLead(lead) - count;
        }
        return -1;
    }

    private Card discardNT(List<List<Card>> longestSuitLists, List<List<Card>> weakestSuitLists) {
        HashMap<Card, Integer> cardWeightMap = new HashMap<>();
        for (Card card : you.getCurrHand()) {
            int weight = 0;
            // lower card gets more weight
            weight += (14 - card.getValue()) * 2;
            for (List<Card> suitList : longestSuitLists) {
                if (suitList.contains(card)) {
                    // card in the longest suit gets more weight
                    weight += 1;
                }
            }
            for (List<Card> suitList : weakestSuitLists) {
                if (suitList.contains(card)) {
                    // card in the weakest suit gets more weight
                    weight += 1;
                }
            }
            cardWeightMap.put(card, weight);
        }
        return getCardOfMostWeight(cardWeightMap);
    }

    @Override
    public Card AIStrategy(Player player, Deck deck, AbstractWhist cardGame) {
        this.CurrentDeck = deck;
        int turnIndex = deck.getTurnList().indexOf(player);
        Card cardSelected = null;
        switch (turnIndex) {
            case 0 -> cardSelected = at1stHand(deck);
            case 1 -> cardSelected = at2ndHand(deck);
            case 2 -> cardSelected = at3rdHand();
            case 3 -> {
                cardSelected = at4thHand(deck);
//                System.out.println("4th hand");
//                System.out.println(cardSelected.getSuit() + " " + cardSelected.getRank());
            }
        }
        if (cardSelected == null) {
            for (Card card : player.getCurrHand()) {
                if (deck.declare(player, card, deck)) {
                    System.out.println((1 + turnIndex) + " hand is selected randomly when player has has " + player.getCurrHand().size() + " cards");
                    return card;
                }
            }
        } else {
            if (deck.declare(player, cardSelected, deck)) {
//                System.out.println(turnIndex + "th hand");
//                System.out.println("AIStrategy: " + cardSelected.getSuit() + cardSelected.getRank());
                return cardSelected;
            }
        }
        return cardSelected;
    }

    public static void main(String[] args) {
        Card card1 = new Card(SPADES, Rank.FIVE);
        Card card2 = new Card(HEARTS, Rank.FIVE);
        Card card3 = new Card(SPADES, Rank.SIX);
        Card card4 = new Card(SPADES, Rank.ACE);
        Card card5 = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card6 = new Card(CLUBS, Rank.EIGHT);
        Card card7 = new Card(HEARTS, Rank.SIX);
        Card card8 = new Card(SPADES, Rank.JACK);
        Card card9 = new Card(HEARTS, Rank.ACE);
        Card card10 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card11 = new Card(CLUBS, Rank.NINE);
        Card card12 = new Card(CLUBS, Rank.FIVE);
        Card card13 = new Card(CLUBS, Rank.JACK);
        List<Card> handList = new ArrayList<>(List.of(card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13));
        Player testPlayer = new Player();
        testPlayer.setInitHand(handList);
        MediumSrtategy mediumSrtategy = new MediumSrtategy();
        mediumSrtategy.setHandList(testPlayer);
        mediumSrtategy.printAllCards();
        List<Card> cardList = new ArrayList<>(List.of(card1, card2, card3, card4, card5));
        handList.removeAll(cardList);
        for (Card card : handList) {
            System.out.println("subs: " + card.getSuit() + " " + card.getRank());
        }
    }
}
