package com.weijin.whistdemo.controllers;

import static com.weijin.whistdemo.utils.animation.*;

import com.weijin.whistdemo.AIstrategy.*;
import com.weijin.whistdemo.AIstrategy.Strategy;
import com.weijin.whistdemo.AboutStage;
import com.weijin.whistdemo.MainStage;
import com.weijin.whistdemo.RuleStage;
import com.weijin.whistdemo.model.*;
import com.weijin.whistdemo.SettleStage;
import com.weijin.whistdemo.javafxComponents.MyImageView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.weijin.whistdemo.utils.helper.*;

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
        initScoreBoardTable();
        whist.addDeckRound();
        deck.initNewDeck(whist.deckRound);
//        deck.initNewDeck(5);
        if (deck.getCurrentTrump() == null) {
            trumpLabel.setText("No Trump This Round");
        } else {
            trumpIv.setImage(SuitToImage(deck.getCurrentTrump()));
        }
        turnList = deck.getTurnList();
        deck.dealCards(turnList);

        initCardViews();
        playButton.setVisible(false);
        updatePlayersInfo();
        updateLogger();
        // 监听轮次的变化，自己的出牌函数在click(),监听器改变按钮状态
//        System.out.println(playerList.get(0).psc.getPropertyChangeListeners().length);
        for (Player player : playerList) {
            if (player.psc.getPropertyChangeListeners().length > 0) {
                player.psc.removePropertyChangeListener(player.psc.getPropertyChangeListeners()[0]);
            }
            player.setTricks(null);
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
                            Card AIcard = AIthrowCard(turnIndex, whist.getDifficulty());
                            logger.add(playerList.get(1).getId() + " played:   " + suitToSymbol(AIcard.getSuit()) + " " + rankToSymbol(AIcard.getRank()));
                            System.out.println(playerList.get(1).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
                            p2played.setImage(CardToImage(AIcard));
                            HandIvSetNullAfterThrowCard(playerList.get(1));
                            updatePlayersInfo();
                            if (turnIndex == 3) {
                                refreshPlayedViews();
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
                                Card AIcard = AIthrowCard(turnIndex, whist.getDifficulty());
                                logger.add(playerList.get(2).getId() + " played:   " + suitToSymbol(AIcard.getSuit()) + " " + rankToSymbol(AIcard.getRank()));
                                System.out.println(playerList.get(2).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
                                p3played.setImage(CardToImage(AIcard));
                                HandIvSetNullAfterThrowCard(playerList.get(2));
                                updatePlayersInfo();
                                if (turnIndex == 3) {
                                    refreshPlayedViews();
                                } else {
                                    playerList.get(3).setTurn(true);
                                }
                            });
                        }
                    }, 2000);
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
                            Card AIcard = AIthrowCard(turnIndex, whist.getDifficulty());
                            logger.add(playerList.get(3).getId() + " played:   " + suitToSymbol(AIcard.getSuit()) + " " + rankToSymbol(AIcard.getRank()));
                            System.out.println(playerList.get(3).getId() + " played: " + AIcard.getSuit() + " " + AIcard.getRank());
                            p4played.setImage(CardToImage(AIcard));
                            HandIvSetNullAfterThrowCard(playerList.get(3));
                            updatePlayersInfo();
                            if (turnIndex == 3) {
                                refreshPlayedViews();
                            } else {
                                playerList.get(0).setTurn(true);
                            }
                        });
                    }
                }, 2000);
            }
        });
        logger.add("-------------------------   Round " + round++ + "   -------------------------");
        turnList.get(0).setTurn(true);
        for (Player p : turnList) {
            System.out.println(p.getId() + "start");
            break;
        }

    }

    public void click(ActionEvent event) {
        int turnIndex = turnList.indexOf(you);
        for (ImageView imageView : p1ivList) {
            if (imageView.getY() < 0) {
                for (ImageView iv : handMap.keySet()) {
                    if (imageView == iv) {
                        System.out.println(handMap.get(iv).getId());
                        if (deck.isAllowed(you, handMap.get(iv), deck)) {
                            Card thrownCard = handMap.get(iv);
                            logger.add(you.getId() + " played:   " + suitToSymbol(thrownCard.getSuit()) + " " + rankToSymbol(thrownCard.getRank()));
                            System.out.println(you.getId() + " played: " + thrownCard.getSuit() + " " + thrownCard.getRank());
                            cardDrop(iv);
                            //出牌界面显示出的牌
                            p1played.setImage(CardToImage(thrownCard));

                            if (turnIndex == 0) {
                                deck.setCurrentLeadSuit(thrownCard.getSuit());
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
                                        }
                                    });
                                }
                            }, 100);
                        } else {
                            Label label = new Label("The lead suit is " + deck.getCurrentLeadSuit() + ", you have a " + deck.getCurrentLeadSuit() + ", so you must play it.");
                            Popup popup = new Popup();
                            label.setStyle("-fx-background-color: white;");
                            popup.getContent().add(label);
                            popup.setAutoFix(true);
                            popup.setAutoHide(true);
                            popup.setHideOnEscape(true);
                            popup.setAnchorX(p1played.getLayoutX() + p1played.getX() + 140);
                            popup.setAnchorY(p1played.getLayoutY() + p1played.getY() + 200);
                            popup.show(gamingGridPane.getScene().getWindow());
                        }
                    }
                }
            }
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
                            //todo 添加tricks的动画以及图层
                            p1played.imageProperty().set(null);
                            p2played.imageProperty().set(null);
                            p3played.imageProperty().set(null);
                            p4played.imageProperty().set(null);
                            updatePlayersInfo();
                        }
                );
            }
        }, 2000);
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

    public Card AIthrowCard(int turnIndex, Integer difficulty) {
        Strategy strategy = null;
        Player playerThisTurn = turnList.get(turnIndex);
        switch (difficulty) {
            case 1: {
                strategy = new EasyStrategy();
            }
            case 2: {
//                strategy = new MediumSrtategy();
                strategy = new EasyStrategy();
            }
            case 3: {
//                strategy = new HardStrategy();
                strategy = new EasyStrategy();
            }

        }

        Card AIcard = strategy.AIStrategy(playerThisTurn, deck, whist);
        if (turnIndex == 0) {
            deck.setCurrentLeadSuit(AIcard.getSuit());
        }
        playerThisTurn.throwCard(AIcard);
        deck.addThisRoundCard(playerThisTurn, AIcard);
        deck.cardsLeftOnDeck--;
        playerList.get(playerList.indexOf(playerThisTurn)).setTurn(false);
        return AIcard;
    }

    public void endOneRound() throws CloneNotSupportedException, IOException {
        HashMap<Player, Card> biggestCard = deck.getBiggestThisRound();
        for (Player winner : biggestCard.keySet()) {
            logger.add(winner.getId() + " wins this round with   " + suitToSymbol(biggestCard.get(winner).getSuit()) + " " + rankToSymbol(biggestCard.get(winner).getRank()));
            logger.add("-------------------------   Round " + round++ + "   -------------------------");
            System.out.println(winner.getId() + " biggest: " + biggestCard.get(winner).getSuit() + " " + biggestCard.get(winner).getRank());
            winner.addTrick(biggestCard.get(winner));
            if (playerList.get(0).equals(winner)) {
                MyImageView trickIv = (MyImageView) p1played.clone();
                trickIv.setFitWidth(10.29 * 5);
                trickIv.setFitHeight(14.22 * 5);
                System.out.println(trickIv.translateXProperty());
                p1TricksPane.setLeftAnchor(trickIv, 0.0 + (10.29 * 3 * winner.getTricks().size() - 1));
                p1TricksPane.setBottomAnchor(trickIv, 0.0);
                p1TricksPane.getChildren().add(trickIv);
            } else if (playerList.get(1).equals(winner)) {
                MyImageView trickIv = (MyImageView) p2played.clone();
                trickIv.setFitWidth(10.29 * 5);
                trickIv.setFitHeight(14.22 * 5);
                System.out.println(trickIv.translateXProperty());
                p2TricksPane.setLeftAnchor(trickIv, 0.0 + (10.29 * 3 * winner.getTricks().size() - 1));
                p2TricksPane.setTopAnchor(trickIv, 0.0);
                p2TricksPane.getChildren().add(trickIv);
            } else if (playerList.get(2).equals(winner)) {
                MyImageView trickIv = (MyImageView) p3played.clone();
                trickIv.setFitWidth(10.29 * 5);
                trickIv.setFitHeight(14.22 * 5);
                System.out.println(trickIv.translateXProperty());
                p3TricksPane.setLeftAnchor(trickIv, 0.0 + (10.29 * 3 * winner.getTricks().size() - 1));
                p3TricksPane.setBottomAnchor(trickIv, 0.0);
                p3TricksPane.getChildren().add(trickIv);
            } else if (playerList.get(3).equals(winner)) {
                MyImageView trickIv = (MyImageView) p4played.clone();
                trickIv.setFitWidth(10.29 * 5);
                trickIv.setFitHeight(14.22 * 5);
                System.out.println(trickIv.translateXProperty());
                p4TricksPane.setLeftAnchor(trickIv, 0.0 + (10.29 * 3 * winner.getTricks().size() - 1));
                p4TricksPane.setTopAnchor(trickIv, 0.0);
                p4TricksPane.getChildren().add(trickIv);
            }

            if (!deck.isDeckEmpty()) {
//            if (deck.isDeckEmpty()) {
                System.out.println("deck not empty" + deck.cardsLeftOnDeck);
                System.out.println("winner: " + winner.getId() + " " + playerList.indexOf(winner));
                deck.setTurnListByStarter(playerList.indexOf(winner), true);
                turnList = deck.getTurnList();
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
                    scoreMap.put("deckRound", String.valueOf(whist.deckRound) + "  " + suitToSymbol(deck.getCurrentTrump()));
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
            logPane.setDisable(false);
            logPane.setVisible(true);
            logListView.setItems(logger);
        } else {
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