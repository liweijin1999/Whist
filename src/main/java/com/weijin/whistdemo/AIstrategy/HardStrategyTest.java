package com.weijin.whistdemo.AIstrategy;

import com.weijin.whistdemo.model.*;

import java.util.*;

public class HardStrategyTest implements Strategy {
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
        List<Suit> suitList = new ArrayList<>(List.of(Suit.SPADES, Suit.HEARTS, Suit.CLUBS, Suit.DIAMONDS));
        suitList.remove(suit1);
        suitList.remove(suit2);
        suitList.remove(suit3);
        System.out.println(suitList.get(0));
        return suitList.get(0);
    }

    public Card at1stHand(Deck deck) {
        partner = deck.getTurnList().get(2);
        you = deck.getTurnList().get(0);
        Player opponent1 = deck.getTurnList().get(1);
        Player opponent2 = deck.getTurnList().get(3);
        setHandList(you);
        Suit trumpSuit = deck.getCurrentTrump();
        boolean NoTrumpThisRound = trumpSuit == null;
        boolean firstRoundOfSuit = true;
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
        Suit strongestSuit = you.getStrongestSuit();
        List<Card> strongestSuitList = switch (strongestSuit) {
            case SPADES -> spadeList;
            case HEARTS -> heartList;
            case CLUBS -> clubList;
            case DIAMONDS -> diamondList;
        };
        int suitIndex = switch (strongestSuit) {
            case SPADES -> 0;
            case HEARTS -> 1;
            case CLUBS -> 2;
            case DIAMONDS -> 3;
        };
        if (deck.getAllCardLists().get(suitIndex).size() < 11) {
            firstRoundOfSuit = false;
        }
        List<List<Card>> suitListsByLength = sortSuitListByLength();
        //reverse suitListsByLength
        List<List<Card>> suitListsByLengthReversed = new ArrayList<>();
        for (int i = suitListsByLength.size() - 1; i >= 0; i--) {
            suitListsByLengthReversed.add(suitListsByLength.get(i));
        }
        if (!NoTrumpThisRound) {
            // there is a trump
            List<Card> trumpSuitList = switch (trumpSuit) {
                case SPADES -> spadeList;
                case HEARTS -> heartList;
                case CLUBS -> clubList;
                case DIAMONDS -> diamondList;
            };
            boolean bringIn = hasBringIn(deck, strongestSuitList, trumpSuitList);
            System.out.println("bring in: " + bringIn);
            if (bringIn) {
                return strongestSuitList.get(strongestSuitList.size() - 1);
            }

            if (you.isAskingForTrump()) {
                if (you.isPlayInTenace() && you.getLastCardInTenace().getSuit() == strongestSuit) {
                    if (you.getLastCardInTenace().getRank() == Rank.KING) {
                        if (containsRank(strongestSuitList, Rank.QUEEN)) {
                            return getCardByRank(strongestSuitList, Rank.QUEEN);
                        }
                    }
                    if (you.getLastCardInTenace().getRank() == Rank.QUEEN) {
                        if (containsRank(strongestSuitList, Rank.JACK)) {
                            return getCardByRank(strongestSuitList, Rank.JACK);
                        }
                        if (containsRank(strongestSuitList, Rank.ACE)) {
                            return getCardByRank(strongestSuitList, Rank.ACE);
                        }
                    }
                }
                if (containsRank(strongestSuitList, Rank.ACE) && containsRank(strongestSuitList, Rank.KING)) {
                    you.setPlayInTenace(true);
                    you.setLastCardInTenace(getCardByRank(strongestSuitList, Rank.KING));
                    you.strongSuitSignal = strongestSuit;
                    you.TrumpSignal = AIThinking.CONFIRMED;
                    return getCardByRank(strongestSuitList, Rank.KING);
                } else if (containsRank(strongestSuitList, Rank.KING) && containsRank(strongestSuitList, Rank.QUEEN)) {
                    you.setPlayInTenace(true);
                    you.setLastCardInTenace(getCardByRank(strongestSuitList, Rank.KING));
                    you.strongSuitSignal = strongestSuit;
                    you.TrumpSignal = AIThinking.CONFIRMED;
                    return getCardByRank(strongestSuitList, Rank.KING);
                } else if (containsRank(strongestSuitList, Rank.KING) && containsSequence(strongestSuitList, Rank.KING) > 1) {
                    you.strongSuitSignal = strongestSuit;
                    you.TrumpSignal = AIThinking.CONFIRMED;
                    return strongestSuitList.get(strongestSuitList.indexOf(getCardByRank(strongestSuitList, Rank.KING)) - (containsSequence(strongestSuitList, Rank.KING) - 1));
                } else if (containsRank(strongestSuitList, Rank.ACE) && containsRank(strongestSuitList, Rank.QUEEN) && containsRank(strongestSuitList, Rank.JACK)) {
                    you.TrumpSignal = AIThinking.CONFIRMED;
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.ACE);
                } else if (containsRank(strongestSuitList, Rank.ACE) && !containsRank(strongestSuitList, Rank.KING) && !containsRank(strongestSuitList, Rank.QUEEN) && !containsRank(strongestSuitList, Rank.JACK) && strongestSuitList.size() >= 5) {
                    you.setPlayInTenace(true);
                    you.setLastCardInTenace(getCardByRank(strongestSuitList, Rank.ACE));
                    you.strongSuitSignal = strongestSuit;
                    you.TrumpSignal = AIThinking.CONFIRMED;
                    return getCardByRank(strongestSuitList, Rank.ACE);
                } else if (containsRank(strongestSuitList, Rank.QUEEN) && containsRank(strongestSuitList, Rank.JACK) && containsRank(strongestSuitList, Rank.TEN)) {
                    you.setPlayInTenace(true);
                    you.setLastCardInTenace(getCardByRank(strongestSuitList, Rank.QUEEN));
                    you.strongSuitSignal = strongestSuit;
                    you.TrumpSignal = AIThinking.CONFIRMED;
                    return getCardByRank(strongestSuitList, Rank.QUEEN);
                } else if (containsRank(strongestSuitList, Rank.JACK) && containsRank(strongestSuitList, Rank.TEN) && containsRank(strongestSuitList, Rank.NINE)) {
                    you.TrumpSignal = AIThinking.CONFIRMED;
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.JACK);
                } else if (containsRank(strongestSuitList, Rank.KING) && containsRank(strongestSuitList, Rank.JACK) && containsRank(strongestSuitList, Rank.TEN) && containsRank(strongestSuitList, Rank.NINE) && strongestSuitList.size() == 5) {
                    you.TrumpSignal = AIThinking.CONFIRMED;
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.NINE);
                } else if (containsRank(strongestSuitList, Rank.KING) && containsRank(strongestSuitList, Rank.JACK) && containsRank(strongestSuitList, Rank.TEN) && strongestSuitList.size() >= 4) {
                    you.TrumpSignal = AIThinking.CONFIRMED;
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.TEN);
                } else if (strongestSuitList.size() > 0) {
                    you.strongSuitSignal = strongestSuit;
                    return strongestSuitList.get(0);
                } else if (strongestSuitList.size() == 0) {
                    return longestSuitLists.get(0).get(0);
                }
            } else if (partner.TrumpSignal == AIThinking.CONFIRMED) {
                if (trumpSuitList.size() > 0) {
                    return trumpSuitList.get(trumpSuitList.size() - 1);
                } else {
                    return shortestSuitLists.get(0).get(0);
                }
            } else {
                if (you.isPlayInTenace() && you.getLastCardInTenace().getSuit() == strongestSuit) {
                    if (you.getLastCardInTenace().getRank() == Rank.KING) {
                        if (containsRank(strongestSuitList, Rank.QUEEN)) {
                            return getCardByRank(strongestSuitList, Rank.QUEEN);
                        }
                    }
                    if (you.getLastCardInTenace().getRank() == Rank.QUEEN) {
                        if (containsRank(strongestSuitList, Rank.JACK)) {
                            return getCardByRank(strongestSuitList, Rank.JACK);
                        }
                        if (containsRank(strongestSuitList, Rank.ACE)) {
                            return getCardByRank(strongestSuitList, Rank.ACE);
                        }
                    }
                }
                //todo: tenance
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
                if (containsRank(strongestSuitList, Rank.ACE) && containsRank(strongestSuitList, Rank.KING)) {
                    you.setPlayInTenace(true);
                    you.setLastCardInTenace(getCardByRank(strongestSuitList, Rank.KING));
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.KING);
                } else if (containsRank(strongestSuitList, Rank.KING) && containsRank(strongestSuitList, Rank.QUEEN)) {
                    you.setPlayInTenace(true);
                    you.setLastCardInTenace(getCardByRank(strongestSuitList, Rank.KING));
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.KING);
                } else if (containsRank(strongestSuitList, Rank.KING) && containsSequence(strongestSuitList, Rank.KING) > 1) {
                    you.strongSuitSignal = strongestSuit;
                    return strongestSuitList.get(strongestSuitList.indexOf(getCardByRank(strongestSuitList, Rank.KING)) - (containsSequence(strongestSuitList, Rank.KING) - 1));
                } else if (containsRank(strongestSuitList, Rank.ACE) && containsRank(strongestSuitList, Rank.QUEEN) && containsRank(strongestSuitList, Rank.JACK)) {
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.ACE);
                } else if (containsRank(strongestSuitList, Rank.ACE) && !containsRank(strongestSuitList, Rank.KING) && !containsRank(strongestSuitList, Rank.QUEEN) && !containsRank(strongestSuitList, Rank.JACK) && strongestSuitList.size() >= 5) {
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.ACE);
                } else if (containsRank(strongestSuitList, Rank.QUEEN) && containsRank(strongestSuitList, Rank.JACK) && containsRank(strongestSuitList, Rank.TEN)) {
                    you.setPlayInTenace(true);
                    you.setLastCardInTenace(getCardByRank(strongestSuitList, Rank.QUEEN));
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.QUEEN);
                } else if (containsRank(strongestSuitList, Rank.JACK) && containsRank(strongestSuitList, Rank.TEN) && containsRank(strongestSuitList, Rank.NINE)) {
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.JACK);
                } else if (containsRank(strongestSuitList, Rank.KING) && containsRank(strongestSuitList, Rank.JACK) && containsRank(strongestSuitList, Rank.TEN) && containsRank(strongestSuitList, Rank.NINE) && strongestSuitList.size() == 5) {
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.NINE);
                } else if (containsRank(strongestSuitList, Rank.KING) && containsRank(strongestSuitList, Rank.JACK) && containsRank(strongestSuitList, Rank.TEN) && strongestSuitList.size() >= 4) {
                    you.strongSuitSignal = strongestSuit;
                    return getCardByRank(strongestSuitList, Rank.TEN);
                } else if (strongestSuitList.size() > 0) {
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

    public Card at2ndHand(Deck deck) {
        HashMap<Player, Card> biggestInfo = deck.getBiggestThisRound();
        partner = deck.getTurnList().get(3);
        you = deck.getTurnList().get(1);
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
            assert biggestCard != null;
            if (biggestCard.getValue() >= 11 && biggestCard.getValue() <= 14) {
                for (Card card : leadSuitList) {
                    if (card.getValue() > biggestCard.getValue()) {
                        return card;
                    }
                }
            }
//            boolean firstRoundOfSuit = true;
            if (deck.getAllCardLists().get(suitIndex).size() < 11) {
                firstRoundOfSuit = false;
            }
            if (!firstRoundOfSuit) {
                List<Card> otherCardCollection = getSubtractList(deck.getAllCardLists().get(suitIndex), leadSuitList);
                for (Card card : leadSuitList) {
                    if (otherCardCollection.size() == 0) {
                        return leadSuitList.get(leadSuitList.size() - 1);
                    }
                    if (card.getValue() > otherCardCollection.get(otherCardCollection.size() - 1).getValue()) {
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

    public Card at3rdHand(Deck deck) {
        HashMap<Player, Card> biggestInfo = deck.getBiggestThisRound();
        partner = deck.getTurnList().get(0);
        you = deck.getTurnList().get(2);
        setHandList(you);
        HashMap<Player, Card> thisRoundCards = deck.getThisRoundCards();
        boolean partnerWon = false;
        boolean NoTrumpThisRound = deck.getCurrentTrump() == null;
        List<List<Card>> weakestSuitLists = getWeakestSuitLists();
        List<List<Card>> shortestSuitLists = getShortestSuitLists();
        List<List<Card>> longestSuitLists = getLongestSuitLists();
//        boolean hasLeadSuit=false;
        Suit leadSuit = deck.getCurrentLeadSuit();
        List<Card> leadSuitList = hasLeadSuit(leadSuit);
        Card biggestCard = null;
        boolean firstRoundOfSuit = true;
        if (leadSuitList != null) {
            //has lead suit
            int suitIndex = switch (leadSuit) {
                case SPADES -> 0;
                case HEARTS -> 1;
                case CLUBS -> 2;
                case DIAMONDS -> 3;
            };
            if (deck.getAllCardLists().get(suitIndex).size() < 11) {
                firstRoundOfSuit = false;
            }
            if (firstRoundOfSuit) {
                if (partner.strongSuitSignal == null && partner.getStrongestSuit() == leadSuit) {
                    partner.strongSuitSignal = leadSuit;
                }
                if (leadSuit == deck.getCurrentTrump() && partner.TrumpSignal != AIThinking.CONFIRMED && partner.isAskingForTrump()) {
                    partner.TrumpSignal = AIThinking.CONFIRMED;
                }
            }
        }
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
                boolean finesse = false;
                if (you.getStrongestSuit() == deck.getCurrentTrump() || you.getStrongestSuit() == leadSuit || partner.weakSuitSignal == leadSuit || deck.getAllCardLists().get(suitIndex).size() >= 11) {
                    finesse = true;
                } else if (partner.strongSuitSignal == leadSuit || deck.getAllCardLists().get(suitIndex).size() < 11) {
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
                    if (you.getLastCard().getSuit() == leadSuitList.get(0).getSuit()) {
                        if (you.getLastCard().getValue() > leadSuitList.get(0).getValue()) {
                            you.TrumpSignal = AIThinking.CONFIRMED;
                            System.out.println(you.getId() + " asking for trump CONFIRMED");
                        }
                    }
                    you.setLastCard(leadSuitList.get(0));
                    return leadSuitList.get(0);
                } else if (you.TrumpSignal == AIThinking.INITIAL) {
                    if (leadSuitList.get(0).getRank() == Rank.TWO) {
                        you.TrumpSignal = AIThinking.DENIED;
                    }
                    you.setLastCard(leadSuitList.get(0));
                    return leadSuitList.get(0);
                } else if (you.TrumpSignal == AIThinking.GUESSING) {
                    if (you.getLastCard().getSuit() == leadSuitList.get(0).getSuit()) {
                        if (you.getLastCard().getValue() < leadSuitList.get(0).getValue()) {
                            you.TrumpSignal = AIThinking.DENIED;
                            System.out.println(you.getId() + " asking for trump DENIED");
                        }
                    }
                    you.setLastCard(leadSuitList.get(0));
                    return leadSuitList.get(0);
                } else {
                    if (otherCardCollection.size() == 0) {
                        return leadSuitList.get(leadSuitList.size() - 1);
                    }
                    if (!firstRoundOfSuit && leadSuit == partner.strongSuitSignal) {
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
                return discardHT(deck, shortestSuitLists, weakestSuitLists);
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
//        System.out.println("biggg " + biggestCard.getSuit() + " " + biggestCard.getRank());
        if (partnerWon) {
            // partner won this round
            if (!NoTrumpThisRound) {
                // trump ある this round
                if (leadSuitList != null) {
                    // has lead suit
                    // play the lowest card of lead suit
                    System.out.println("hereh" + you.isAskingForTrump() + you.TrumpSignal);
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
                        you.setLastCard(leadSuitList.get(0));
                        return leadSuitList.get(0);
                    } else {
                        if (you.getLastCard().getSuit() == leadSuitList.get(0).getSuit()) {
                            if (you.getLastCard().getValue() < leadSuitList.get(0).getValue()) {
                                you.TrumpSignal = AIThinking.DENIED;
                                System.out.println(you.getId() + " asking for trump DENIED");
                            }
                        }
                        you.setLastCard(leadSuitList.get(0));
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
                        you.setLastCard(leadSuitList.get(0));
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
        for (Card card : you.getCurrHand()) {
            int weight = 0;
            // lower card gets more weight
            weight += (14 - card.getValue());
            if (card.getSuit().equals(deck.getCurrentTrump())) {
                // trump card gets more weight
                if (deck.getCurrentLeadSuit().equals(partner.strongSuitSignal)) {
                    weight -= 14;
                } else {
                    weight += 14;
                }
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
            if (card.getRank() == Rank.TWO && card.getSuit() == Suit.CLUBS)
                System.out.println("two of clubs" + weight);
            if (card.getRank() == Rank.THREE && card.getSuit() == Suit.DIAMONDS)
                System.out.println("three of diamond" + weight);
            cardWeightMap.put(card, weight);
        }
        return getCardOfMostWeight(cardWeightMap);
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
