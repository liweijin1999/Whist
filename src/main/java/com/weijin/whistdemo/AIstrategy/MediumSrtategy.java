package com.weijin.whistdemo.AIstrategy;

import com.weijin.whistdemo.model.*;

import java.io.IOException;
import java.util.*;

public class MediumSrtategy implements Strategy {
    List<Card> spadeList = new ArrayList<>();
    List<Card> heartList = new ArrayList<>();
    List<Card> clubList = new ArrayList<>();
    List<Card> diamondList = new ArrayList<>();

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
            if (card.getSuit().equals(Suit.SPADES)) {
                spadeList.add(card);
            } else if (card.getSuit().equals(Suit.HEARTS)) {
                heartList.add(card);
            } else if (card.getSuit().equals(Suit.CLUBS)) {
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
            if (suitList.size() == 0) {
//                System.out.println("empty suitList");
                continue;
            }
            if (suitList.size() < shortestSuitListSize) {
                shortestSuitLists.clear();
                shortestSuitLists.add(suitList);
                shortestSuitListSize = suitList.size();
            } else if (suitList.size() == shortestSuitListSize) {
                shortestSuitLists.add(suitList);
            }
        }
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

    private List<List<Card>> haveDoubleton() {
        List<List<Card>> doubletonList = new ArrayList<>();
        for (List<Card> suitList : getShortestSuitLists()) {
            if (suitList.size() == 2) {
                doubletonList.add(suitList);
            }
        }
        if (doubletonList.size() > 0) {
            return doubletonList;
        } else {
            return null;
        }
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
        if (leadSuit == Suit.SPADES && spadeList.size() > 0) {
//            hasLeadSuit=true;
            leadSuitList = spadeList;
        } else if (leadSuit == Suit.HEARTS && heartList.size() > 0) {
//            hasLeadSuit=true;
            leadSuitList = heartList;
        } else if (leadSuit == Suit.CLUBS && clubList.size() > 0) {
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

    public Card at1stHand(Deck deck) {
        Player partner = deck.getTurnList().get(2);
        Player you = deck.getTurnList().get(0);
        setHandList(you);
        Suit trumpSuit = deck.getCurrentTrump();
        boolean NoTrumpThisRound = trumpSuit == null;
        boolean firstRoundOfSuit = true;
        HashMap<Card, Integer> cardWeightMap = new HashMap<>();
        List<List<Card>> weakestSuitLists = getWeakestSuitLists();
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
//        boolean hasLeadSuit=false;
        List<List<Card>> suitListsByLength = sortSuitListByLength();
        //reverse suitListsByLength
        List<List<Card>> suitListsByLengthReversed = new ArrayList<>();
        for (int i = suitListsByLength.size() - 1; i >= 0; i--) {

            suitListsByLengthReversed.add(suitListsByLength.get(i));
        }
        if (!NoTrumpThisRound) {
            // there is a trump
            List<Card> singleton = haveSingleton();
            if (singleton != null) {
                if (singleton.get(0).getSuit() != trumpSuit) {
                    return singleton.get(0);
                }
            }
            List<List<Card>> doubletonList = haveDoubleton();
            if (doubletonList != null) {
                for (List<Card> doubleton : doubletonList) {
                    if (doubleton.get(0).getSuit() != trumpSuit) {
                        if (containsRank(doubleton, Rank.ACE) && containsRank(doubleton, Rank.KING)) {
                            return getCardByRank(doubleton, Rank.ACE);
                        } else if (containsRank(doubleton, Rank.ACE)) {
                            return getCardByRank(doubleton, Rank.ACE);
                        } else if (containsRank(doubleton, Rank.KING)) {
                            return getCardByRank(doubleton, Rank.KING);
                        } else {
                            int suitIndex = switch (doubleton.get(0).getSuit()) {
                                case SPADES -> 0;
                                case HEARTS -> 1;
                                case CLUBS -> 2;
                                case DIAMONDS -> 3;
                            };
                            List<Card> otherCardCollection = getSubtractList(deck.getAllCardLists().get(suitIndex), doubleton);
                            for (Card card : doubleton) {
                                if (otherCardCollection.size() == 0) break;
                                if (card.getValue() > otherCardCollection.get(otherCardCollection.size() - 1).getValue()) {
                                    System.out.println("here最大" + otherCardCollection.size() + " " + otherCardCollection.get(otherCardCollection.size() - 1).getSuit() + " " + otherCardCollection.get(otherCardCollection.size() - 1).getRank());
                                    System.out.println("更大 " + card.getSuit() + " " + card.getRank());
                                    return card;
                                }
                            }
                        }
                    }
                }
            }
            for (List<Card> suitList : suitListsByLengthReversed) {
                int suitIndex;
                if (suitList.equals(spadeList)) {
                    suitIndex = 0;
                } else if (suitList.equals(heartList)) {
                    suitIndex = 1;
                } else if (suitList.equals(clubList)) {
                    suitIndex = 2;
                } else {
                    suitIndex = 3;
                }
//                if (suitIndex == trumpSuit.getCode()) continue;
//            System.out.println("here" + deck.getAllCardLists().get(suitIndex).size());
                if (deck.getAllCardLists().get(suitIndex).size() < 12) {
                    firstRoundOfSuit = false;
                }
//                if (firstRoundOfSuit) {
                return suitList.get(suitList.size() - 1);
//                }
            }
            System.out.println(suitListsByLength.size());
//            for (List<Card> suitList:suitListsByLength){
//                if (suitList.size()==0) continue;
//                Card recard=suitList.get(suitList.size()-1);
//                System.out.println(recard.getSuit()+" "+recard.getRank());
//                return recard;
//            }
//            return suitListsByLength.get(0).get(0);
            return null;
        } else {
            for (List<Card> suitList : suitListsByLengthReversed) {
                if (containsRank(suitList, Rank.KING)) {
                    // king leads
                    if (containsRank(suitList, Rank.ACE)) {
                        return getCardByRank(suitList, Rank.KING);
                    } else if (containsRank(suitList, Rank.QUEEN) && containsRank(suitList, Rank.JACK) && suitList.size() >= 5) {
                        //lead jack
                        return getCardByRank(suitList, Rank.JACK);
                    } else if (containsRank(suitList, Rank.QUEEN)) {
                        return getCardByRank(suitList, Rank.KING);
                    }
                } else if (containsRank(suitList, Rank.ACE) && suitList.size() >= 3) {
                    // ace leads
                    if (containsRank(suitList, Rank.QUEEN) && containsRank(suitList, Rank.JACK)) {
                        return getCardByRank(suitList, Rank.ACE);
                    } else if (suitList.size() >= 5) {
                        return getCardByRank(suitList, Rank.ACE);
                    }
                } else if (containsRank(suitList, Rank.QUEEN)) {
                    //queen leads
                    if (containsRank(suitList, Rank.JACK) && containsRank(suitList, Rank.TEN)) {
                        return getCardByRank(suitList, Rank.QUEEN);
                    }
                } else if (containsRank(suitList, Rank.TEN)) {
                    //ten leads
                    if (containsRank(suitList, Rank.KING) && containsRank(suitList, Rank.JACK) && suitList.size() >= 5) {
                        return getCardByRank(suitList, Rank.TEN);
                    }
                }
            }
            return suitListsByLengthReversed.get(0).get(0);
        }
    }

    public Card at2ndHand(Deck deck) {
        HashMap<Player, Card> biggestInfo = deck.getBiggestThisRound();
        Player partner = deck.getTurnList().get(3);
        Player you = deck.getTurnList().get(1);
        setHandList(you);
        HashMap<Player, Card> thisRoundCards = deck.getThisRoundCards();
        boolean NoTrumpThisRound = deck.getCurrentTrump() == null;
        boolean firstRoundOfSuit = true;
        HashMap<Card, Integer> cardWeightMap = new HashMap<>();
        List<List<Card>> weakestSuitLists = getWeakestSuitLists();
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
        Suit leadSuit = deck.getCurrentLeadSuit();
        List<Card> leadSuitList = hasLeadSuit(leadSuit);

        Card biggestCard = null;

        for (Player player : biggestInfo.keySet()) {
            biggestCard = biggestInfo.get(player);
        }
        if (leadSuitList != null) {
            //has lead suit
            int suitIndex = switch (leadSuit) {
                case SPADES -> 0;
                case HEARTS -> 1;
                case CLUBS -> 2;
                case DIAMONDS -> 3;
            };
//            System.out.println("here" + deck.getAllCardLists().get(suitIndex).size());
            if (deck.getAllCardLists().get(suitIndex).size() < 12) {
                firstRoundOfSuit = false;
            }
            if (!firstRoundOfSuit) {
//                System.out.println("here");
                List<Card> otherCardCollection = getSubtractList(deck.getAllCardLists().get(suitIndex), leadSuitList);
//                System.out.println("------");
//                for (Card card : otherCardCollection) {
//                    System.out.println("otherCardCollection " + card.getSuit() + " " + card.getRank());
//                }
//                for (Card card : leadSuitList) {
//                    System.out.println("leadSuitList " + card.getSuit() + " " + card.getRank());
//                }
//                System.out.println("here" + otherCardCollection.size() + " " + leadSuitList.get(leadSuitList.size() - 1).getRank());
//                System.out.println("------");
//                for (Card card : otherCardCollection) {
//                    System.out.println("otherCardCollection " + card.getSuit() + " " + card.getRank());
//                }
//                System.out.println("------");
                for (Card card : leadSuitList) {
                    if (otherCardCollection.size() == 0) {
                        return leadSuitList.get(leadSuitList.size() - 1);
                    }
                    if (card.getValue() > otherCardCollection.get(otherCardCollection.size() - 1).getValue()) {
                        return card;
                    }
                }
            }
            assert biggestCard != null;
            if (biggestCard.getValue() >= 11 && biggestCard.getValue() <= 14) {
                for (Card card : leadSuitList) {
                    if (card.getValue() > biggestCard.getValue()) {
                        return card;
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

    public Card at3rdHand(Deck deck) throws IOException, ClassNotFoundException {
        HashMap<Player, Card> biggestInfo = deck.getBiggestThisRound();
        Player partner = deck.getTurnList().get(0);
        Player you = deck.getTurnList().get(2);
        setHandList(you);
        HashMap<Player, Card> thisRoundCards = deck.getThisRoundCards();
        boolean partnerWon = false;
        boolean NoTrumpThisRound = deck.getCurrentTrump() == null;
        HashMap<Card, Integer> cardWeightMap = new HashMap<>();
        List<List<Card>> weakestSuitLists = getWeakestSuitLists();
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
//        boolean hasLeadSuit=false;
        Suit leadSuit = deck.getCurrentLeadSuit();
        List<Card> leadSuitList = hasLeadSuit(leadSuit);
        Card biggestCard = null;

        for (Player player : biggestInfo.keySet()) {
            biggestCard = biggestInfo.get(player);
        }
        if (leadSuitList != null) {
            // have lead suit
            // if have larger card, play the biggest, else play the smallest
            boolean haveLargerCard = false;
            for (Card card : leadSuitList) {
                assert biggestCard != null;
                if (card.getValue() > biggestCard.getValue()) {
                    haveLargerCard = true;
                }
            }
            int suitIndex = switch (leadSuit) {
                case SPADES -> 0;
                case HEARTS -> 1;
                case CLUBS -> 2;
                case DIAMONDS -> 3;
            };
            List<Card> otherCardCollection = getSubtractList(deck.getAllCardLists().get(suitIndex), leadSuitList);
            assert biggestCard != null;
            if (thisRoundCards.get(partner).getValue() == biggestCard.getValue()) {
                if (otherCardCollection.size() == 0) {
                    partnerWon = true;
                } else if (thisRoundCards.get(partner).getValue() > otherCardCollection.get(otherCardCollection.size() - 1).getValue()) {
                    partnerWon = true;
                }
            }
            if (haveLargerCard && !partnerWon) {
                //todo: finesse
                Random random = new Random();
                int finesse = random.nextInt(2);
                if (containsRank(leadSuitList, Rank.ACE) && containsRank(leadSuitList, Rank.QUEEN)) {
                    if (finesse == 0) {
                        return getCardByRank(leadSuitList, Rank.ACE);
                    } else {
                        return getCardByRank(leadSuitList, Rank.QUEEN);
                    }
                }

                return leadSuitList.get(leadSuitList.size() - 1);
            } else {
                return leadSuitList.get(0);
            }
        } else {
            // no lead suit
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

    public Card at4thHand(Deck deck) {
        HashMap<Player, Card> biggestInfo = deck.getBiggestThisRound();
        Player partner = deck.getTurnList().get(1);
        Player you = deck.getTurnList().get(3);
        setHandList(you);
        HashMap<Player, Card> thisRoundCards = deck.getThisRoundCards();
        boolean partnerWon = false;
        boolean NoTrumpThisRound = deck.getCurrentTrump() == null;
        HashMap<Card, Integer> cardWeightMap = new HashMap<>();
        List<List<Card>> weakestSuitLists = getWeakestSuitLists();
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
//        boolean hasLeadSuit=false;
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
        System.out.println("biggg " + biggestCard.getSuit() + " " + biggestCard.getRank());
        if (partnerWon) {
            // partner won this round
            if (!NoTrumpThisRound) {
                // trump ある this round
                if (leadSuitList != null) {
                    // has lead suit
                    //play the lowest card of lead suit
                    return leadSuitList.get(0);
                } else {
                    // has no lead suit
                    for (Card card : you.getCurrHand()) {
                        int weight = 0;
                        // lower card gets more weight
                        weight += (14 - card.getValue());
                        if (!card.getSuit().equals(deck.getCurrentTrump())) {
                            // non-trump card gets more weight
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
                }
            } else {
                // no trump this round
                if (leadSuitList != null) {
                    // has lead suit
                    //play the lowest card of lead suit
                    return leadSuitList.get(0);
                } else {
                    // has no lead suit
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
                    return leadSuitList.get(0);
                } else {
                    // has no lead suit
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
    }

    private Card discard() {
        return null;
    }

    @Override
    public Card AIStrategy(Player player, Deck deck, AbstractWhist cardGame) throws IOException, ClassNotFoundException {
        int turnIndex = deck.getTurnList().indexOf(player);
        Card cardSelected = null;
        switch (turnIndex) {
            case 0 -> cardSelected = at1stHand(deck);
            case 1 -> cardSelected = at2ndHand(deck);
            case 2 -> cardSelected = at3rdHand(deck);
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
            return null;
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
        Card card1 = new Card(Suit.SPADES, Rank.FIVE);
        Card card2 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card3 = new Card(Suit.SPADES, Rank.SIX);
        Card card4 = new Card(Suit.SPADES, Rank.ACE);
        Card card5 = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card6 = new Card(Suit.CLUBS, Rank.EIGHT);
        Card card7 = new Card(Suit.HEARTS, Rank.SIX);
        Card card8 = new Card(Suit.SPADES, Rank.JACK);
        Card card9 = new Card(Suit.HEARTS, Rank.ACE);
        Card card10 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card11 = new Card(Suit.CLUBS, Rank.NINE);
        Card card12 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card13 = new Card(Suit.CLUBS, Rank.JACK);
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
