package com.weijin.whistdemo;

import static com.weijin.whistdemo.utils.animation.*;

import com.weijin.whistdemo.AIstrategy.EasyStrategy;
import com.weijin.whistdemo.AIstrategy.Strategy;
import com.weijin.whistdemo.component.*;
import com.weijin.whistdemo.component.WhistImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAttribute;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import static com.weijin.whistdemo.utils.animation.cardPop;
import static com.weijin.whistdemo.utils.helper.*;
import static java.lang.Thread.sleep;

public class GamingStageController implements Initializable {

    public ImageView p1iv1, p1iv2, p1iv3, p1iv4, p1iv5, p1iv6, p1iv7, p1iv8, p1iv9, p1iv10, p1iv11, p1iv12, p1iv13;
    public List<ImageView> p1ivList = new ArrayList<>(13);
    public ImageView p1played, p2played, p3played, p4played;
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
    public TableColumn<ScoreBoard, String> youNpartner;
    @FXML
    public TableColumn<ScoreBoard, String> opponents;
    final ObservableList<ScoreBoard> scoreBoardData = FXCollections.observableArrayList();
    public Button playButton;

    private HashMap<ImageView, Card> handMap = new HashMap<>(13);
    WhistImpl whist;
    Deck deck;
    Player you;
    List<Player> playerList;
    List<Player> turnList;
    Boolean flag;

    void initController(WhistImpl whistConcrete) {
        /**
         * 游戏本身应当维护的参数：玩家playerList（0号为自己，也是显示的顺序），玩家的总分
         */
        this.whist = whistConcrete;
        this.playerList = whist.getPlayerList();
        this.you = playerList.get(0);
        /**
         * 每一个牌桌应当维护的变量：玩家名单playerList，当前轮次出牌顺序turnList
         * 牌桌功能：发牌，决定当前王牌花色，检测玩家出牌是否符合规则，计算最大牌，决定下一轮轮次，结束游戏并记分
         */
        deck = new Deck(playerList);
        initScoreBoardTable();
        deck.initNewDeck();
        turnList = deck.getTurnList();
        deck.dealCards(turnList);

        initCardViews();
        playButton.setVisible(false);
//        for (Player p : turnList) {
//            System.out.println(p.getId() + "'turn: " + p.isTurn());
//        }
//        for (Player p : playerList) {
//            System.out.println("playerlist: " + p.getId() + playerList.indexOf(p));
//        }
        // 监听轮次的变化，自己的出牌函数在click(),监听器改变按钮状态
        playerList.get(0).psc.addPropertyChangeListener("setTurn_pro", evt -> playButton.setVisible((Boolean) evt.getNewValue()));

        playerList.get(1).psc.addPropertyChangeListener("setTurn_pro", evt -> {
            if ((Boolean) evt.getNewValue()) {

                //给出牌的动画加个延迟

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            int turnIndex = turnList.indexOf(playerList.get(1));
                            Card AIcard = AIthrowCard(turnIndex, "easy");
                            System.out.println(playerList.get(1).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
                            p2played.setImage(CardtoImage(AIcard));
                            HandIvSetNullAfterThrowCard(playerList.get(1));
                            //todo 手牌图片减少一张
                            if (turnIndex == 3) {
                                refreshPlayedViews();
//                                endOneRound();


                            } else {
                                playerList.get(2).setTurn(true);
                            }

                        });
                    }
                }, 2000);


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
                                Card AIcard = AIthrowCard(turnIndex, "easy");
                                System.out.println(playerList.get(2).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
                                p3played.setImage(CardtoImage(AIcard));
                                HandIvSetNullAfterThrowCard(playerList.get(2));
                                if (turnIndex == 3) {
                                    refreshPlayedViews();
//                                    endOneRound();


                                } else {
                                    playerList.get(3).setTurn(true);
                                }
                            });
                        }
                    }, 2000);


                }
            }
        });
        playerList.get(3).psc.addPropertyChangeListener("setTurn_pro", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue()) {


                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {

//                            synchronized (flag){
                            Platform.runLater(() -> {
                                int turnIndex = turnList.indexOf(playerList.get(3));
                                Card AIcard = AIthrowCard(turnIndex, "easy");
                                System.out.println(playerList.get(3).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
                                p4played.setImage(CardtoImage(AIcard));
                                HandIvSetNullAfterThrowCard(playerList.get(3));
                                //todo 手牌图片减少一张
                                if (turnIndex == 3) {
                                    refreshPlayedViews();
//                                    endOneRound();

                                } else {
                                    playerList.get(0).setTurn(true);
                                }
                            });
                        }
//                        }
                    }, 2000);


                }

            }
        });
        turnList.get(0).setTurn(true);
        for (Player p : turnList) {
            System.out.println(p.getId() + " " + p.isTurn());
        }
    }

    public void click(ActionEvent event) throws Exception {
        int turnIndex = turnList.indexOf(you);

//        System.out.println("Button Clicked!");
        for (ImageView imageView : p1ivList) {
            if (imageView.getY() < 0) {
                for (ImageView iv : handMap.keySet()) {
                    if (imageView == iv) {
                        System.out.println(handMap.get(iv).getId());
                        if (deck.isAllowed(you, handMap.get(iv), deck)) {
                            Card thrownCard = handMap.get(iv);
                            System.out.println(you.getId() + " played: " + thrownCard.getSuit() + " " + thrownCard.getRank());
                            cardDrop(iv);
                            //出牌界面显示出的牌
                            p1played.setImage(CardtoImage(thrownCard));
                            if (turnIndex == 0) {
                                deck.setCurrentLeadSuit(thrownCard.getSuit());
                            }
                            you.throwCard(thrownCard);
                            deck.addThisRoundCard(you, thrownCard);
                            deck.cardsLeftOnDeck--;
                            //重新排序
                            rearrange(iv, handList, p1ivList, handMap);
                            you.setTurn(false);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Platform.runLater(() -> {

                                        if (turnIndex == 3) {
                                            refreshPlayedViews();
//                                Platform.runLater(this::endOneRound);
//                                endOneRound();

                                        } else {
                                            playerList.get(1).setTurn(true);
                                        }
                                    });
                                }
                            }, 100);


                        } else {
                            Label label = new Label("This card is not allowed! Change one please!");

                            Popup popup = new Popup();

                            label.setStyle("-fx-background-color: white;");
                            popup.getContent().add(label);

                            label.setMinWidth(100);
                            label.setMinHeight(80);
                            popup.setX(730);

                            popup.setY(700);
                            popup.setAutoFix(true);
                            popup.setAutoHide(true);
                            popup.setHideOnEscape(true);
//                            popup.show(p1iv1.getScene().getWindow());
//                            System.out.println(.getX()+""+ p1played.getY());
                            popup.show(p1played, p1played.getX(), p1played.getY());

                        }
                    }
                }
            }
        }

    }

    public void initScoreBoardTable() {
        youNpartner.setCellValueFactory(cellData -> cellData.getValue().uAndTeammateScoreProperty());
        opponents.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        //绑定数据到TableView
        scoreBoardTableView.setItems(scoreBoardData);
        ScoreBoard scoreBoard = new ScoreBoard("0", "0");
        //添加数据到scoreBoard，TableView会自动更新
        scoreBoardData.add(scoreBoard);
//        removeScrollBar(scoreBoardTableView);
    }

    public void initCardViews() {
        //对当前手牌进行排序，顺序：Spade，Heart，Diamond，Club; A，k，Q，J，10，9，8，7，6，5，4，3，2
        List<Card> sortedCards = sortCards(you.getCurrHand());
        handList.addAll(sortedCards);

        //手牌图层-玩家手牌
        for (int i = 0; i < p1ivList.size(); i++) {
            handMap.put(p1ivList.get(i), handList.get(i));
        }
        //设置imageView的图片
        for (int i = 0; i < 13; i++) {
            p1ivList.get(i).setImage(CardtoImage(handList.get(i)));
            p3ivList.get(i).setImage(CardtoImage(null));
            p2ivList.get(i).setRotate(90);
            p2ivList.get(i).setImage(CardtoImage(null));
            p2ivList.get(i).setRotate(-90);
            p4ivList.get(i).setRotate(90);
            p4ivList.get(i).setImage(CardtoImage(null));
            p4ivList.get(i).setRotate(90);
        }
    }

    public void refreshPlayedViews() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                            p1played.imageProperty().set(null);
                            p2played.imageProperty().set(null);
                            p3played.imageProperty().set(null);
                            p4played.imageProperty().set(null);
                            endOneRound();
                        }
                );
            }
        }, 4000);


//        this.p2played.setImage(null);
//        this.p3played.setImage(null);
//        this.p4played.setImage(null);

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

    public Card AIthrowCard(int turnIndex, String strategy) {
        Strategy strategy1 = null;
        Player playerThisTurn = turnList.get(turnIndex);
        switch (strategy) {
            case "easy": {
                strategy1 = new EasyStrategy();
            }
        }

        Card AIcard = strategy1.AIStrategy(playerThisTurn, deck, whist);
        if (turnIndex == 0) {
            deck.setCurrentLeadSuit(AIcard.getSuit());
        }
        playerThisTurn.throwCard(AIcard);
        deck.addThisRoundCard(playerThisTurn, AIcard);
        deck.cardsLeftOnDeck--;
        playerList.get(playerList.indexOf(playerThisTurn)).setTurn(false);
        return AIcard;
    }

    public void endOneRound() {
        HashMap<Player, Card> biggestCard = deck.getBiggestThisRound();
        for (Player winner : biggestCard.keySet()) {
            System.out.println(winner.getId() + " biggest: " + biggestCard.get(winner).getSuit() + " " + biggestCard.get(winner).getRank());
            winner.addTrick(biggestCard.get(winner));
            if (!deck.isDeckEmpty()) {
                System.out.println("deck not empty" + deck.cardsLeftOnDeck);
                System.out.println("winner: " + winner.getId() + " " + playerList.indexOf(winner));
                deck.setTurnListByStarter(playerList.indexOf(winner), true);
                turnList = deck.getTurnList();
                System.out.println("new turnList: " + turnList.get(0).getId() + " " + turnList.get(1).getId() + " " + turnList.get(2).getId() + " " + turnList.get(3).getId());
                System.out.println("new order:" + turnList.get(0).isTurn() + " " + turnList.get(1).isTurn() + " " + turnList.get(2).isTurn() + " " + turnList.get(3).isTurn());
            } else {
                SettleStage settleStage = new SettleStage();
                try {
                    settleStage.showWindow();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
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

        // TODO Auto-generated constructor stub
    }
}