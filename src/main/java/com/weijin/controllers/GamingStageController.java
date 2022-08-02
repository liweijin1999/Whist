package com.weijin.controllers;

import com.weijin.AIstrategy.*;
import com.weijin.AIstrategy.Strategy;
import com.weijin.AboutStage;
import com.weijin.MainStage;
import com.weijin.RuleStage;
import com.weijin.SettleStage;
import com.weijin.javafxComponents.MyImageView;
import com.weijin.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.weijin.utils.animation.*;
import static com.weijin.utils.helper.*;

public class GamingStageController implements Initializable {

    public ImageView p1iv1, p1iv2, p1iv3, p1iv4, p1iv5, p1iv6, p1iv7, p1iv8, p1iv9, p1iv10, p1iv11, p1iv12, p1iv13;
    public List<ImageView> p1ivList = new ArrayList<>(13);
    public MyImageView p1played, p2played, p3played, p4played;
    public ImageView p2iv1, p2iv2, p2iv3, p2iv4, p2iv5, p2iv6, p2iv7, p2iv8, p2iv9, p2iv10, p2iv11, p2iv12, p2iv13;
    public List<ImageView> p2ivList = new ArrayList<>(13);
    public ImageView p3iv1, p3iv2, p3iv3, p3iv4, p3iv5, p3iv6, p3iv7, p3iv8, p3iv9, p3iv10, p3iv11, p3iv12, p3iv13;
    public List<ImageView> p3ivList = new ArrayList<>(13);
    public ImageView p4iv1, p4iv2, p4iv3, p4iv4, p4iv5, p4iv6, p4iv7, p4iv8, p4iv9, p4iv10, p4iv11, p4iv12, p4iv13;
    public List<ImageView> p4ivList = new ArrayList<>(13);
    public List<Card> handList = new ArrayList<>(13);
    @FXML
    public TableView<ScoreBoard> scoreBoardTableView;
    @FXML
    public TableColumn<ScoreBoard, String> team;
    @FXML
    public TableColumn<ScoreBoard, String> score;
    final ObservableList<ScoreBoard> scoreBoardData = FXCollections.observableArrayList();
    public Button playButton;
    public ToggleButton logButton;
    public ListView<String> logListView;
    public ScrollPane logPane;
    public GridPane gamingGridPane;
    public Label trumpLabel;
    public AnchorPane p1TricksPane, p2TricksPane, p3TricksPane, p4TricksPane;
    public ImageView trumpIv;
    public Tooltip p1Info, p2Info, p3Info, p4Info;
    public Label trumpSuitIcon;
    public AnchorPane p1handPane;
    public ImageView avatar2, avatar3, avatar4;
    public Label tipLabel;
    private HashMap<ImageView, Card> reviewHandMap = new HashMap<>(13);

    private HashMap<ImageView, Card> handMap = new HashMap<>(13);
    WhistImpl whist;
    Deck deck;
    Player you;
    List<Player> playerList;
    List<Player> turnList;
    ObservableList<String> logger = FXCollections.observableArrayList();
    int round;
    final double FIT_WIDTH = 102.9;
    final double FIT_HEIGHT = 142.2;

    boolean reviewMode = false;

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

    public void initController(WhistImpl whistConcrete) {
        /*
         * 游戏本身应当维护的参数：玩家playerList（0号为自己，也是显示的顺序），玩家的总分
         */
        this.whist = whistConcrete;
        this.playerList = whist.getPlayerList();
        this.you = playerList.get(0);
        /*
         * 每一个牌桌应当维护的变量：玩家名单playerList，当前轮次出牌顺序turnList
         * 牌桌功能：发牌，决定当前王牌花色，检测玩家出牌是否符合规则，计算最大牌，决定下一轮轮次，结束游戏并记分
         */
        deck = new Deck(playerList);
        round = 1;
        deck.setRound(round);
        initScoreBoardTable();
        whist.addDeckRound();
        deck.initNewDeck(whist.deckRound);
        //测试第n局
//        deck.initNewDeck(2);
//        deck.setTurnListByStarter(0, false);//test
        for (Player player : playerList) {
            player.setTrumpSuit(deck.getCurrentTrump());
        }
        turnList = deck.getTurnList();
        deck.dealCards(turnList);
//        deal();//test
        if (deck.getCurrentTrump() == null) {
            trumpLabel.setText("No Trump This Round");
            trumpSuitIcon.setText("");
        } else {
            trumpLabel.setText("Trump This Round:  ");
            trumpSuitIcon.setText(suitToSymbol(deck.getCurrentTrump()));
            switch (deck.getCurrentTrump()) {
                case SPADES -> trumpSuitIcon.setStyle("-fx-text-fill: black;-fx-font-size: 16;");
                case HEARTS -> trumpSuitIcon.setStyle("-fx-text-fill: red;-fx-font-size: 16;");
                case DIAMONDS -> trumpSuitIcon.setStyle("-fx-text-fill: red;-fx-font-size: 16;");
                case CLUBS -> trumpSuitIcon.setStyle("-fx-text-fill: black;-fx-font-size: 16;");
            }
            trumpIv.setImage(SuitToImage(deck.getCurrentTrump()));
        }
        initCardViews();
        tipLabel.setVisible(false);
        playButton.setVisible(false);
        updatePlayersInfo();
        updateLogger();
        // 监听轮次的变化，自己的出牌函数在click(),监听器改变按钮状态
//        System.out.println(playerList.get(0).psc.getPropertyChangeListeners().length);
        for (Player player : playerList) {
            if (player.psc.getPropertyChangeListeners().length > 0) {
                player.psc.removePropertyChangeListener(player.psc.getPropertyChangeListeners()[0]);
            }
            player.setTricks(new ArrayList<>());
        }
        playerList.get(0).psc.addPropertyChangeListener("setTurn_pro", evt -> playButton.setVisible((Boolean) evt.getNewValue()));
//        System.out.println(playerList.get(0).psc.getPropertyChangeListeners().length);
        playerList.get(1).psc.addPropertyChangeListener("setTurn_pro", evt -> {
            if ((Boolean) evt.getNewValue()) {
                //给出牌的动画加个延迟
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            int turnIndex = turnList.indexOf(playerList.get(1));
                            Card AIcard = null;
                            try {
                                AIcard = AIthrowCard(turnIndex, whist.getDifficulty());
                            } catch (IOException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            logger.add(playerList.get(1).getId() + " played:   " + suitToSymbol(AIcard.getSuit()) + " " + rankToSymbol(AIcard.getRank()));
                            System.out.println(playerList.get(1).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
                            p2played.setImage(CardToImage(AIcard));
                            if (reviewMode) {
                                //重新排序
                                for (ImageView iv : reviewHandMap.keySet()) {
                                    if (reviewHandMap.get(iv).getSuit() == AIcard.getSuit() && reviewHandMap.get(iv).getRank() == AIcard.getRank()) {
                                        iv.imageProperty().set(null);
                                        break;
                                    }
                                }
                            } else {
                                HandIvSetNullAfterThrowCard(playerList.get(1));
                            }
                            updatePlayersInfo();
                            if (turnIndex == 3) {
                                refreshPlayedViews();
                            } else {
                                playerList.get(2).setTurn(true);
                                setEffectByTurn(2);
                            }
                        });
                    }
                }, 1500);
            }
        });
        playerList.get(2).psc.addPropertyChangeListener("setTurn_pro", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue()) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                int turnIndex = turnList.indexOf(playerList.get(2));
                                Card AIcard = null;
                                try {
                                    AIcard = AIthrowCard(turnIndex, whist.getDifficulty());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                logger.add(playerList.get(2).getId() + " played:   " + suitToSymbol(AIcard.getSuit()) + " " + rankToSymbol(AIcard.getRank()));
                                System.out.println(playerList.get(2).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
                                p3played.setImage(CardToImage(AIcard));
                                if (reviewMode) {
                                    //重新排序
                                    for (ImageView iv : reviewHandMap.keySet()) {
                                        if (reviewHandMap.get(iv).getSuit() == AIcard.getSuit() && reviewHandMap.get(iv).getRank() == AIcard.getRank()) {
                                            iv.imageProperty().set(null);
                                            break;
                                        }
                                    }
                                } else {
                                    HandIvSetNullAfterThrowCard(playerList.get(2));
                                }
                                updatePlayersInfo();
                                if (turnIndex == 3) {
                                    refreshPlayedViews();
                                } else {
                                    playerList.get(3).setTurn(true);
                                    setEffectByTurn(3);
                                }
                            });
                        }
                    }, 1500);
                }
            }
        });
        playerList.get(3).psc.addPropertyChangeListener("setTurn_pro", evt -> {
            if ((Boolean) evt.getNewValue()) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            int turnIndex = turnList.indexOf(playerList.get(3));
                            Card AIcard = null;
                            try {
                                AIcard = AIthrowCard(turnIndex, whist.getDifficulty());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            logger.add(playerList.get(3).getId() + " played:   " + suitToSymbol(AIcard.getSuit()) + " " + rankToSymbol(AIcard.getRank()));
                            System.out.println(playerList.get(3).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
                            p4played.setImage(CardToImage(AIcard));
                            if (reviewMode) {
                                //重新排序
                                for (ImageView iv : reviewHandMap.keySet()) {
                                    if (reviewHandMap.get(iv).getSuit() == AIcard.getSuit() && reviewHandMap.get(iv).getRank() == AIcard.getRank()) {
                                        iv.imageProperty().set(null);
                                        break;
                                    }
                                }
                            } else {
                                HandIvSetNullAfterThrowCard(playerList.get(3));
                            }
                            updatePlayersInfo();
                            if (turnIndex == 3) {
                                refreshPlayedViews();
                            } else {
                                playerList.get(0).setTurn(true);
                                setEffectByTurn(0);
                            }
                        });
                    }
                }, 1500);
            }
        });

        logger.add("-------------------------   Round " + round++ + "   -------------------------");
        turnList.get(0).setTurn(true);
        setEffectByTurn(playerList.indexOf(turnList.get(0)));
        for (Player p : turnList) {
            System.out.println(p.getId() + "start");
            break;
        }

    }

    public void click(ActionEvent event) {
        tipLabel.setVisible(false);
        int turnIndex = turnList.indexOf(you);
        int isSelect = 0;
        for (ImageView imageView : p1ivList) {
            if (imageView.getY() < 0) {
                isSelect = 1;
                for (ImageView iv : handMap.keySet()) {
                    if (imageView == iv) {
                        System.out.println(handMap.get(iv).getId());
                        if (deck.declare(you, handMap.get(iv), deck)) {
                            Card thrownCard = handMap.get(iv);
                            logger.add(you.getId() + " played:   " + suitToSymbol(thrownCard.getSuit()) + " " + rankToSymbol(thrownCard.getRank()));
                            System.out.println(you.getId() + " played: " + thrownCard.getSuit() + " " + thrownCard.getRank());
                            cardDrop(iv);
                            //出牌界面显示出的牌
                            p1played.setImage(CardToImage(thrownCard));

                            if (turnIndex == 0) {
                                deck.setCurrentLeadSuit(thrownCard.getSuit());
                                if (whist.deckRound % 5 == 0) {
                                    trumpIv.setImage(SuitToImage(deck.getCurrentLeadSuit()));
                                }
                            }
                            you.throwCard(thrownCard);
                            deck.addThisRoundCard(you, thrownCard);
                            deck.cardsLeftOnDeck--;
                            updatePlayersInfo();
                            //重新排序
                            rearrange(iv, handList, p1ivList, handMap);
                            you.setTurn(false);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Platform.runLater(() -> {
                                        if (turnIndex == 3) {
                                            refreshPlayedViews();
                                        } else {
                                            playerList.get(1).setTurn(true);
                                            setEffectByTurn(1);
                                        }
                                    });
                                }
                            }, 100);
                        } else {
                            tipLabel.setText("The lead suit is " + suitToSymbol(deck.getCurrentLeadSuit()) + ", you have a " + suitToSymbol(deck.getCurrentLeadSuit()) + ", so you must play it.");
                            tipLabel.setVisible(true);
                        }
                    }
                }
            }
        }
        if (isSelect == 0) {
            tipLabel.setText("Please select a card ! ");
            tipLabel.setVisible(true);
        }
    }

    public void initScoreBoardTable() {
        team.setCellValueFactory(cellData -> cellData.getValue().teamProperty());
        score.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());

        //绑定数据到TableView
        scoreBoardTableView.setItems(scoreBoardData);
        if (scoreBoardTableView.getItems().size() == 0) {
            ScoreBoard scoreBoard = new ScoreBoard("You & " + playerList.get(2).getId(), "0");
            ScoreBoard sc2 = new ScoreBoard(playerList.get(1).getId() + " & " + playerList.get(3).getId(), "0");
            //添加数据到scoreBoard，TableView会自动更新
            scoreBoardData.add(scoreBoard);
            scoreBoardData.add(sc2);
        }
    }

    public void updateLogger() {
        logger.clear();
    }

    public void initCardViews() {
        if (reviewMode) {
            List<Card> sortedCards = sortCards(you.getCurrHand());
            handList.addAll(sortedCards);

            //手牌图层-玩家手牌
            for (int i = 0; i < p1ivList.size(); i++) {
                handMap.put(p1ivList.get(i), handList.get(i));
            }
            List<Card> p1hand = sortCards(playerList.get(0).getCurrHand());
            List<Card> p2hand = sortCards(playerList.get(1).getCurrHand());
            List<Card> p3hand = sortCards(playerList.get(2).getCurrHand());
            List<Card> p4hand = sortCards(playerList.get(3).getCurrHand());
            for (int i = 0; i < 13; i++) {
                reviewHandMap.put(p1ivList.get(i), p1hand.get(i));
                reviewHandMap.put(p2ivList.get(i), p2hand.get(i));
                reviewHandMap.put(p3ivList.get(i), p3hand.get(i));
                reviewHandMap.put(p4ivList.get(i), p4hand.get(i));
            }
            //重置手牌图层宽高
            for (ImageView iv : p1ivList) {
                iv.setFitWidth(FIT_WIDTH);
                iv.setFitHeight(FIT_HEIGHT);
            }
            for (int i = 0; i < 13; i++) {
                p1ivList.get(i).setImage(CardToImage(p1hand.get(i)));
                p3ivList.get(i).setImage(CardToImage(p3hand.get(i)));
                p2ivList.get(i).setRotate(90);
                p2ivList.get(i).setImage(CardToImage(p2hand.get(i)));
                p2ivList.get(i).setRotate(-90);
                p4ivList.get(i).setRotate(90);
                p4ivList.get(i).setImage(CardToImage(p4hand.get(i)));
                p4ivList.get(i).setRotate(90);
            }
        } else {
            //对当前手牌进行排序，顺序：Spade，Heart，Diamond，Club; A，k，Q，J，10，9，8，7，6，5，4，3，2
            List<Card> sortedCards = sortCards(you.getCurrHand());
            handList.addAll(sortedCards);

            //手牌图层-玩家手牌
            for (int i = 0; i < p1ivList.size(); i++) {
                handMap.put(p1ivList.get(i), handList.get(i));
            }
            //重置手牌图层宽高
            for (ImageView iv : p1ivList) {
                iv.setFitWidth(FIT_WIDTH);
                iv.setFitHeight(FIT_HEIGHT);
            }
            //设置imageView的图片
            for (int i = 0; i < 13; i++) {
                p1ivList.get(i).setImage(CardToImage(handList.get(i)));
                p3ivList.get(i).setImage(CardToImage(null));
                p2ivList.get(i).setRotate(90);
                p2ivList.get(i).setImage(CardToImage(null));
                p2ivList.get(i).setRotate(-90);
                p4ivList.get(i).setRotate(90);
                p4ivList.get(i).setImage(CardToImage(null));
                p4ivList.get(i).setRotate(90);
            }
        }
        //清空tricks
        p1TricksPane.getChildren().clear();
        p2TricksPane.getChildren().clear();
        p3TricksPane.getChildren().clear();
        p4TricksPane.getChildren().clear();
    }

    public void refreshPlayedViews() {

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                            try {
                                endOneRound();
                            } catch (CloneNotSupportedException | IOException e) {
                                throw new RuntimeException(e);
                            }
                            p1played.imageProperty().set(null);
                            p2played.imageProperty().set(null);
                            p3played.imageProperty().set(null);
                            p4played.imageProperty().set(null);
                            if (whist.deckRound % 5 == 0) {
                                trumpIv.imageProperty().set(null);
                            }
                            updatePlayersInfo();
                        }
                );
            }
        }, 1500);
    }

    public void removeEffectForTrick(ImageView iv1) {
        if (iv1 == null) return;
        iv1.effectProperty().set(null);
    }

    public void setEffectForTrick(ImageView iv1) {
        DropShadow shadow = new DropShadow(10.0, Color.PURPLE);
        iv1.setEffect(shadow);

    }

    public void updatePlayersInfo() {
        p1Info.setText("Hand: " + playerList.get(0).getCurrHand().size() + "\n" + "Tricks: " + playerList.get(0).getTricks().size());
        p2Info.setText("Hand: " + playerList.get(1).getCurrHand().size() + "\n" + "Tricks: " + playerList.get(1).getTricks().size());
        p3Info.setText("Hand: " + playerList.get(2).getCurrHand().size() + "\n" + "Tricks: " + playerList.get(2).getTricks().size());
        p4Info.setText("Hand: " + playerList.get(3).getCurrHand().size() + "\n" + "Tricks: " + playerList.get(3).getTricks().size());

    }

    public void HandIvSetNullAfterThrowCard(Player player) {
        int playerIndex = playerList.indexOf(player);
        int currentHandSize = player.getCurrHand().size();
        switch (playerIndex) {
            case 1 -> {
                if (currentHandSize % 2 == 0) {
                    p2ivList.get(12 - (13 - currentHandSize) / 2).imageProperty().set(null);
                } else if (currentHandSize % 2 == 1) {
                    p2ivList.get((13 - currentHandSize) / 2 - 1).imageProperty().set(null);
                }
            }
            case 2 -> {
                if (currentHandSize % 2 == 0) {
                    p3ivList.get(12 - (13 - currentHandSize) / 2).imageProperty().set(null);
                } else if (currentHandSize % 2 == 1) {
                    p3ivList.get((13 - currentHandSize) / 2 - 1).imageProperty().set(null);
                }
            }
            case 3 -> {
                if (currentHandSize % 2 == 0) {
                    p4ivList.get(12 - (13 - currentHandSize) / 2).imageProperty().set(null);
                } else if (currentHandSize % 2 == 1) {
                    p4ivList.get((13 - currentHandSize) / 2 - 1).imageProperty().set(null);
                }
            }
        }
    }

    public void cardImg1Click(MouseEvent mouseEvent) {
        cardPop(p1iv1, p1ivList);

    }

    public void cardImg2Click(MouseEvent mouseEvent) {
        cardPop(p1iv2, p1ivList);
    }

    public void cardImg3Click(MouseEvent mouseEvent) {
        cardPop(p1iv3, p1ivList);

    }

    public void cardImg4Click(MouseEvent mouseEvent) {
        cardPop(p1iv4, p1ivList);
    }

    public void cardImg5Click(MouseEvent mouseEvent) {
        cardPop(p1iv5, p1ivList);

    }

    public void cardImg6Click(MouseEvent mouseEvent) {
        cardPop(p1iv6, p1ivList);
    }

    public void cardImg7Click(MouseEvent mouseEvent) {
        cardPop(p1iv7, p1ivList);

    }

    public void cardImg8Click(MouseEvent mouseEvent) {
        cardPop(p1iv8, p1ivList);
    }

    public void cardImg9Click(MouseEvent mouseEvent) {
        cardPop(p1iv9, p1ivList);

    }

    public void cardImg10Click(MouseEvent mouseEvent) {
        cardPop(p1iv10, p1ivList);
    }

    public void cardImg11Click(MouseEvent mouseEvent) {
        cardPop(p1iv11, p1ivList);

    }

    public void cardImg12Click(MouseEvent mouseEvent) {
        cardPop(p1iv12, p1ivList);
    }

    public void cardImg13Click(MouseEvent mouseEvent) {
        cardPop(p1iv13, p1ivList);

    }

    public Card AIthrowCard(int turnIndex, Integer difficulty) throws IOException, ClassNotFoundException {
        Strategy strategy = null;
        Player playerThisTurn = turnList.get(turnIndex);
        switch (difficulty) {
            case 1 -> {
                strategy = new EasyStrategy();
            }
            case 2 -> {
                System.out.println("Medium");
//                strategy = new MediumSrtategy();
                strategy = new MediumSrtategy();
            }
            case 3 -> {
//                strategy = new HardStrategy();
                System.out.println("hard");
                strategy = new HardStrategy();
            }

        }

        assert strategy != null;
        Card AIcard = strategy.AIStrategy(playerThisTurn, deck, whist);
        if (turnIndex == 0) {
            deck.setCurrentLeadSuit(AIcard.getSuit());
            if (whist.deckRound % 5 == 0) {
                trumpIv.setImage(SuitToImage(deck.getCurrentLeadSuit()));
            }
        }
        playerThisTurn.throwCard(AIcard);
        deck.addThisRoundCard(playerThisTurn, AIcard);
        deck.cardsLeftOnDeck--;
        playerList.get(playerList.indexOf(playerThisTurn)).setTurn(false);
        return AIcard;
    }

    private ImageView temp_trick;
    public void endOneRound() throws CloneNotSupportedException, IOException {
        HashMap<Player, Card> biggestCard = deck.getBiggestThisRound();

        for (Player winner : biggestCard.keySet()) {
            logger.add(winner.getId() + " wins this round with   " + suitToSymbol(biggestCard.get(winner).getSuit()) + " " + rankToSymbol(biggestCard.get(winner).getRank()));
            deck.setRound(round);
            System.out.println("round: " + round);
            logger.add("-------------------------   Round " + round++ + "   -------------------------");
            System.out.println(winner.getId() + " biggest: " + biggestCard.get(winner).getSuit() + " " + biggestCard.get(winner).getRank());
            winner.addTrick(biggestCard.get(winner));
            if (playerList.get(0).equals(winner)) {

                MyImageView trickIv = (MyImageView) p1played.clone();
                if (temp_trick == null) {
                    temp_trick = trickIv;
                    setEffectForTrick(trickIv);
                } else {
//                    assert temp_trick != null;
                    removeEffectForTrick(temp_trick);
                    temp_trick = trickIv;
                    setEffectForTrick(trickIv);
                }
//                ImageView targetIv=new ImageView();
//                System.out.println(trickIv.localToScene(trickIv.getLayoutBounds()).getMinX());
//                targetIv.setFitWidth(10.29 * 5);
//                targetIv.setFitHeight(14.22 * 5);
//                p1TricksPane.setLeftAnchor(targetIv, 0.0 + (10.29 * 3 * winner.getTricks().size() - 1));
//                p1TricksPane.setBottomAnchor(targetIv, 0.0);
//                p1TricksPane.getChildren().add(trickIv);
//                System.out.println(targetIv.localToScene(targetIv.getLayoutBounds()).getMinX());
//                System.out.println(targetIv.localToScene(targetIv.getLayoutBounds()).getMinY());
//
//                KeyValue kv1x = new KeyValue(trickIv.translateXProperty(), 0);
//                KeyValue kv1y = new KeyValue(trickIv.translateYProperty(), 0);
//                KeyValue kv1sx = new KeyValue(trickIv.scaleXProperty(), 1);
//                KeyValue kv1sy = new KeyValue(trickIv.scaleYProperty(), 1);
//                KeyFrame kf1 = new KeyFrame(Duration.seconds(0), kv1x, kv1y, kv1sx, kv1sy);
//
//                KeyValue kv2x = new KeyValue(trickIv.translateXProperty(), targetIv.localToScene(targetIv.getLayoutBounds()).getMinX() );
//                KeyValue kv2y = new KeyValue(trickIv.translateYProperty(), targetIv.localToScene(targetIv.getLayoutBounds()).getMinY() );
//                KeyValue kv2sx = new KeyValue(trickIv.scaleXProperty(), 0.5);
//                KeyValue kv2sy = new KeyValue(trickIv.scaleYProperty(), 0.5);
//                KeyFrame kf2 = new KeyFrame(Duration.seconds(2), kv2x, kv2y, kv2sx, kv2sy);
//
//                Timeline timeline = new Timeline();
//                timeline.getKeyFrames().addAll(kf1, kf2);
//
//                timeline.play();
//                System.out.println(trickIv.localToScene(trickIv.getLayoutBounds()).getMinX());
                trickIv.setFitWidth(10.29 * 5);
                trickIv.setFitHeight(14.22 * 5);
                p1TricksPane.setLeftAnchor(trickIv, 0.0 + (10.29 * 3 * winner.getTricks().size() - 1));
                p1TricksPane.setBottomAnchor(trickIv, 0.0);
                p1TricksPane.getChildren().add(trickIv);
//                System.out.println(trickIv.localToScene(trickIv.getLayoutBounds()).getMinX());

            } else if (playerList.get(1).equals(winner)) {
                MyImageView trickIv = (MyImageView) p2played.clone();
                if (temp_trick == null) {
                    temp_trick = trickIv;
                    setEffectForTrick(trickIv);
                } else {
//                    assert temp_trick != null;
                    removeEffectForTrick(temp_trick);
                    temp_trick = trickIv;
                    setEffectForTrick(trickIv);
                }
                trickIv.setFitWidth(10.29 * 5);
                trickIv.setFitHeight(14.22 * 5);
                System.out.println(trickIv.translateXProperty());
                p2TricksPane.setLeftAnchor(trickIv, 0.0 + (10.29 * 3 * winner.getTricks().size() - 1));
                p2TricksPane.setTopAnchor(trickIv, 0.0);
                p2TricksPane.getChildren().add(trickIv);
            } else if (playerList.get(2).equals(winner)) {
                MyImageView trickIv = (MyImageView) p3played.clone();
                if (temp_trick == null) {
                    temp_trick = trickIv;
                    setEffectForTrick(trickIv);
                } else {
//                    assert temp_trick != null;
                    removeEffectForTrick(temp_trick);
                    temp_trick = trickIv;
                    setEffectForTrick(trickIv);
                }
                trickIv.setFitWidth(10.29 * 5);
                trickIv.setFitHeight(14.22 * 5);
                System.out.println(trickIv.translateXProperty());
                p3TricksPane.setLeftAnchor(trickIv, 0.0 + (10.29 * 3 * winner.getTricks().size() - 1));
                p3TricksPane.setBottomAnchor(trickIv, 0.0);
                p3TricksPane.getChildren().add(trickIv);
            } else if (playerList.get(3).equals(winner)) {
                MyImageView trickIv = (MyImageView) p4played.clone();
                if (temp_trick == null) {
                    temp_trick = trickIv;
                    setEffectForTrick(trickIv);
                } else {
//                    assert temp_trick != null;
                    removeEffectForTrick(temp_trick);
                    temp_trick = trickIv;
                    setEffectForTrick(trickIv);
                }
                trickIv.setFitWidth(10.29 * 5);
                trickIv.setFitHeight(14.22 * 5);
                System.out.println(trickIv.translateXProperty());
                p4TricksPane.setLeftAnchor(trickIv, 0.0 + (10.29 * 3 * winner.getTricks().size() - 1));
                p4TricksPane.setTopAnchor(trickIv, 0.0);
                p4TricksPane.getChildren().add(trickIv);
            }
            deck.resetThisRoundCards();
            if (!deck.isDeckEmpty()) {
                //测试跳转结算界面
//            if (deck.isDeckEmpty()) {
                System.out.println("deck not empty" + deck.cardsLeftOnDeck);
                System.out.println("winner: " + winner.getId() + " " + playerList.indexOf(winner));
                deck.setTurnListByStarter(playerList.indexOf(winner), true);
                turnList = deck.getTurnList();
                setEffectByTurn(playerList.indexOf(winner));
                System.out.println("new turnList: " + turnList.get(0).getId() + " " + turnList.get(1).getId() + " " + turnList.get(2).getId() + " " + turnList.get(3).getId());
                System.out.println("new order:" + turnList.get(0).isTurn() + " " + turnList.get(1).isTurn() + " " + turnList.get(2).isTurn() + " " + turnList.get(3).isTurn());

            } else {
                int uAndTeammateTricks = playerList.get(0).getTricks().size() + playerList.get(2).getTricks().size();
                int opponentTricks = playerList.get(1).getTricks().size() + playerList.get(3).getTricks().size();
                int uAndTeammateScore = 0;
                int opponentScore = 0;

                if (uAndTeammateTricks > opponentTricks) {
                    uAndTeammateScore = uAndTeammateTricks - 6;
                } else if (uAndTeammateTricks < opponentTricks) {
                    opponentScore = opponentTricks - 6;
                }

                int uAndTeammateScoreTotal = Integer.parseInt(scoreBoardData.get(0).getScore()) + uAndTeammateScore;
                int opponentScoreTotal = Integer.parseInt(scoreBoardData.get(1).getScore()) + opponentScore;
                scoreBoardData.get(0).setScore(String.valueOf(uAndTeammateScoreTotal));
                scoreBoardData.get(1).setScore(String.valueOf(opponentScoreTotal));
                SettleStage settleStage = new SettleStage();
                try {
                    HashMap<String, String> scoreMap = new HashMap<>();
                    scoreMap.put("uAndTeammateScore", String.valueOf(uAndTeammateScore));
                    scoreMap.put("uAndTeammateScoreTotal", String.valueOf(uAndTeammateScoreTotal));
                    scoreMap.put("opponentScore", String.valueOf(opponentScore));
                    scoreMap.put("opponentScoreTotal", String.valueOf(opponentScoreTotal));
                    String deckRoundString;
                    if (whist.deckRound % 5 != 0) {
                        deckRoundString = whist.deckRound + "  " + suitToSymbol(deck.getCurrentTrump());
                    } else {
                        deckRoundString = whist.deckRound + "  " + "No Trump";
                    }
                    scoreMap.put("deckRound", deckRoundString);
                    for (String key : scoreMap.keySet()) {
                        System.out.println(key + ": " + scoreMap.get(key));
                    }
                    settleStage.showScoreBoardWindow(whist, scoreMap, this);

                    //方案1：直接关闭当前界面，然后打开结算界面，结算界面中选择新游戏，然后新建一个controller(easy)
//                    Stage stage = (Stage) p1TricksPane.getScene().getWindow();
//                    stage.close();
                    //(已实现)方案二：不关闭当前界面，结算界面选择新游戏后重新在当前界面操作，不需要新建controller

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setEffectByTurn(int turn) {
        DropShadow dropShadow = new DropShadow(10.0, Color.RED);
        playButton.effectProperty().set(null);
        avatar2.effectProperty().set(null);
        avatar3.effectProperty().set(null);
        avatar4.effectProperty().set(null);
        switch (turn) {
            case 0 -> playButton.setEffect(dropShadow);
            case 1 -> avatar2.setEffect(dropShadow);
            case 2 -> avatar3.setEffect(dropShadow);
            case 3 -> avatar4.setEffect(dropShadow);
        }
    }

    public GamingStageController() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        p1ivList.addAll(Arrays.asList(p1iv1, p1iv2, p1iv3, p1iv4, p1iv5, p1iv6, p1iv7, p1iv8, p1iv9, p1iv10, p1iv11, p1iv12, p1iv13));
        p2ivList.addAll(Arrays.asList(p2iv1, p2iv2, p2iv3, p2iv4, p2iv5, p2iv6, p2iv7, p2iv8, p2iv9, p2iv10, p2iv11, p2iv12, p2iv13));
        p3ivList.addAll(Arrays.asList(p3iv1, p3iv2, p3iv3, p3iv4, p3iv5, p3iv6, p3iv7, p3iv8, p3iv9, p3iv10, p3iv11, p3iv12, p3iv13));
        p4ivList.addAll(Arrays.asList(p4iv1, p4iv2, p4iv3, p4iv4, p4iv5, p4iv6, p4iv7, p4iv8, p4iv9, p4iv10, p4iv11, p4iv12, p4iv13));

    }

    public void showLog(ActionEvent actionEvent) {
        if (logButton.isSelected()) {
            logButton.setText("hide log");
            logPane.setDisable(false);
            logPane.setVisible(true);
            logListView.setItems(logger);
        } else {
            logButton.setText("show log");
            logPane.setDisable(true);
            logPane.setVisible(false);
        }
    }

    public void newGameClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Restart a game?");
        alert.setContentText("Are you sure you want to start a new game?\n\nWarning: All of your progress will be lost.");
        Button ok = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        ok.setText("Confirm");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancel");
        ok.setOnAction(event -> {
            MainStage mainStage = new MainStage();
            try {
                mainStage.showWindow();
                Stage stage = (Stage) playButton.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        alert.show();
    }

    public void exit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exit?");
        alert.setContentText("Are you sure to exit this application?\n\nWarning: All of your progress will be lost.");
        Button ok = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        ok.setText("Confirm");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancel");
        ok.setOnAction(event -> {
            Platform.exit();
        });
        alert.show();

    }

    public void showRules(ActionEvent actionEvent) throws Exception {
        RuleStage ruleStage = new RuleStage();
        ruleStage.showWindow();
    }

    public void showAbout(ActionEvent actionEvent) throws Exception {
        AboutStage aboutStage = new AboutStage();
        aboutStage.showWindow();
    }
}