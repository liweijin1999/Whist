package com.weijin.whistdemo.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String id;
    private int score = 0;
    private String avatar;
    private List<Card> hand = new ArrayList<>();
    private List<Card> tricks = new ArrayList<>();
    private boolean turn = false;
    public PropertyChangeSupport psc = new PropertyChangeSupport(this);
    private Suit trumpSuit;
    private boolean askingForTrump = false;

    private Suit strongestSuit;
    private Suit weakestSuit = null;
    private Card lastCard = null;
    //    private void
    public AIThinking TrumpSignal = AIThinking.INITIAL;
    public boolean hasNoSpadeSignal = false;
    public boolean hasNoHeartSignal = false;
    public boolean hasNoDiamondSignal = false;
    public boolean hasNoClubSignal = false;
    public Suit strongSuitSignal = null;
    public Suit weakSuitSignal = null;
    public Suit discardSuitSignal = null;
    private boolean playInTenace = false;
    private Card lastCardInTenace;

    public Card firstLead = null;

    //    Player(String id,String avatar,boolean isAI){
//        this.id=id;
//        this.avatar=avatar;
//        this.isAI=isAI;
//    }
    public String getId() {
        return id;
    }

    public void setId(String name) {
        this.id = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
        psc.firePropertyChange("hand", null, hand);
    }

    public List<Card> getCurrHand() {
        return hand;
    }

    public void setInitHand(List<Card> hand) {
        this.hand = hand;

        int spades = 0, hearts = 0, diamonds = 0, clubs = 0;
        int spadesLarge = 0, heartsLarge = 0, diamondsLarge = 0, clubsLarge = 0;
        for (Card card : hand) {
            if (card.getSuit() == Suit.SPADES) {
                spades++;
                spadesLarge += transVal(card);
            } else if (card.getSuit() == Suit.HEARTS) {
                hearts++;
                heartsLarge += transVal(card);
            } else if (card.getSuit() == Suit.DIAMONDS) {
                diamonds++;
                diamondsLarge += transVal(card);
            } else if (card.getSuit() == Suit.CLUBS) {
                clubs++;
                clubsLarge += transVal(card);
            }
        }

        if (spades > hearts && spades > diamonds && spades > clubs) {
            if (spades == 5 && (hearts == 4 || clubs == 4 || diamonds == 4) & spadesLarge == 0) {
                if (hearts == 4 && heartsLarge >= 2) {
                    strongestSuit = Suit.HEARTS;
                } else if (clubs == 4 && clubsLarge >= 2) {
                    strongestSuit = Suit.CLUBS;
                } else if (diamonds == 4 && diamondsLarge >= 2) {
                    strongestSuit = Suit.DIAMONDS;
                } else {
                    strongestSuit = Suit.SPADES;
                }
            } else {
                strongestSuit = Suit.SPADES;
            }
        } else if (hearts > spades && hearts > diamonds && hearts > clubs) {
            if (hearts == 5 && (spades == 4 || clubs == 4 || diamonds == 4) & heartsLarge == 0) {
                if (spades == 4 && spadesLarge >= 2) {
                    strongestSuit = Suit.SPADES;
                } else if (clubs == 4 && clubsLarge >= 2) {
                    strongestSuit = Suit.CLUBS;
                } else if (diamonds == 4 && diamondsLarge >= 2) {
                    strongestSuit = Suit.DIAMONDS;
                } else {
                    strongestSuit = Suit.HEARTS;
                }
            } else {
                strongestSuit = Suit.HEARTS;
            }
        } else if (diamonds > spades && diamonds > hearts && diamonds > clubs) {
            if (diamonds == 5 && (spades == 4 || hearts == 4 || clubs == 4) & diamondsLarge == 0) {
                if (spades == 4 && spadesLarge >= 2) {
                    strongestSuit = Suit.SPADES;
                } else if (hearts == 4 && heartsLarge >= 2) {
                    strongestSuit = Suit.HEARTS;
                } else if (clubs == 4 && clubsLarge >= 2) {
                    strongestSuit = Suit.CLUBS;
                } else {
                    strongestSuit = Suit.DIAMONDS;
                }
            } else {
                strongestSuit = Suit.DIAMONDS;
            }
        } else if (clubs > spades && clubs > hearts && clubs > diamonds) {
            if (clubs == 5 && (spades == 4 || hearts == 4 || diamonds == 4) & clubsLarge == 0) {
                if (spades == 4 && spadesLarge >= 2) {
                    strongestSuit = Suit.SPADES;
                } else if (hearts == 4 && heartsLarge >= 2) {
                    strongestSuit = Suit.HEARTS;
                } else if (diamonds == 4 && diamondsLarge >= 2) {
                    strongestSuit = Suit.DIAMONDS;
                } else {
                    strongestSuit = Suit.CLUBS;
                }
            } else {
                strongestSuit = Suit.CLUBS;
            }
        } else if (spades == hearts && spades > diamonds && spades > clubs) {
            if (spadesLarge > heartsLarge) {
                strongestSuit = Suit.SPADES;
            } else {
                strongestSuit = Suit.HEARTS;
            }
        } else if (spades == diamonds && spades > hearts && spades > clubs) {
            if (spadesLarge > diamondsLarge) {
                strongestSuit = Suit.SPADES;
            } else {
                strongestSuit = Suit.DIAMONDS;
            }
        } else if (spades == clubs && spades > hearts && spades > diamonds) {
            if (spadesLarge > clubsLarge) {
                strongestSuit = Suit.SPADES;
            } else {
                strongestSuit = Suit.CLUBS;
            }
        } else if (hearts == diamonds && hearts > spades && hearts > clubs) {
            if (heartsLarge > diamondsLarge) {
                strongestSuit = Suit.HEARTS;
            } else {
                strongestSuit = Suit.DIAMONDS;
            }
        } else if (hearts == clubs && hearts > spades && hearts > diamonds) {
            if (heartsLarge > clubsLarge) {
                strongestSuit = Suit.HEARTS;
            } else {
                strongestSuit = Suit.CLUBS;
            }
        } else if (diamonds == clubs && diamonds > spades && diamonds > hearts) {
            if (diamondsLarge > clubsLarge) {
                strongestSuit = Suit.DIAMONDS;
            } else {
                strongestSuit = Suit.CLUBS;
            }
        } else if (spades == hearts && spades == diamonds && clubs == 1) { //4:4:4:1
            if (spadesLarge > heartsLarge && spadesLarge > diamondsLarge) {
                strongestSuit = Suit.SPADES;
            } else if (heartsLarge > spadesLarge && heartsLarge > diamondsLarge) {
                strongestSuit = Suit.HEARTS;
            } else {
                strongestSuit = Suit.DIAMONDS;
            }
            weakestSuit = Suit.CLUBS;
        } else if (spades == hearts && spades == clubs && diamonds == 1) {
            if (spadesLarge > heartsLarge && spadesLarge > clubsLarge) {
                strongestSuit = Suit.SPADES;
            } else if (heartsLarge > spadesLarge && heartsLarge > clubsLarge) {
                strongestSuit = Suit.HEARTS;
            } else {
                strongestSuit = Suit.CLUBS;
            }
            weakestSuit = Suit.DIAMONDS;
        } else if (spades == diamonds && spades == clubs && hearts == 1) {
            if (spadesLarge > diamondsLarge && spadesLarge > clubsLarge) {
                strongestSuit = Suit.SPADES;
            } else if (diamondsLarge > spadesLarge && diamondsLarge > clubsLarge) {
                strongestSuit = Suit.DIAMONDS;
            } else {
                strongestSuit = Suit.CLUBS;
            }
            weakestSuit = Suit.HEARTS;
        } else if (hearts == diamonds && hearts == clubs && spades == 1) {
            if (heartsLarge > diamondsLarge && heartsLarge > clubsLarge) {
                strongestSuit = Suit.HEARTS;
            } else if (diamondsLarge > heartsLarge && diamondsLarge > clubsLarge) {
                strongestSuit = Suit.DIAMONDS;
            } else {
                strongestSuit = Suit.CLUBS;
            }
            weakestSuit = Suit.SPADES;
        }
        askingForTrump = strongestSuit == trumpSuit;
        System.out.println(trumpSuit + " " + askingForTrump + "Strongest suit: " + strongestSuit);
    }

    public void throwCard(Card card) {
        List<Card> newHand = new ArrayList<>();
        int index = 0;
        for (Card value : hand) {
            if (value != card) {
                newHand.add(index, value);
                index++;
            }
        }
        this.hand = newHand;

        lastCard = card;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Card> getTricks() {
        return tricks;
    }

    public void addTrick(Card trick) {
        this.tricks.add(trick);
    }

    public void setTricks(List<Card> tricks) {
        this.tricks = tricks;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        boolean previousTurn = this.turn;
        this.turn = turn;
        psc.firePropertyChange("setTurn_pro", previousTurn, this.turn);
    }


    public void setFirstLead(Card firstLead) {
        this.firstLead = firstLead;
    }

    public boolean isPlayInTenace() {
        return playInTenace;
    }

    public void setPlayInTenace(boolean playInTenace) {
        this.playInTenace = playInTenace;
    }

    public Card getLastCardInTenace() {
        return lastCardInTenace;
    }

    public void setLastCardInTenace(Card lastCardInTenace) {
        this.lastCardInTenace = lastCardInTenace;
    }

    public Card getLastCard() {
        return lastCard;
    }

    public void setLastCard(Card lastCard) {
        this.lastCard = lastCard;
    }

    public void setTrumpSuit(Suit trump) {
        this.trumpSuit = trump;
    }

    public Suit getStrongestSuit() {
        return strongestSuit;
    }

    private int transVal(Card card) {
        if (card.getValue() >= 10 && card.getValue() <= 14) return 1;
        return 0;
    }

    public boolean isAskingForTrump() {
        return askingForTrump;
//        return true;
    }


}
