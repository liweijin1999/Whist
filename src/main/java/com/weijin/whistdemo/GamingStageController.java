package com.weijin.whistdemo;

import static com.weijin.whistdemo.utils.animation.*;

import com.weijin.whistdemo.component.Card;
import com.weijin.whistdemo.component.Dealer;
import com.weijin.whistdemo.component.Deck;
import com.weijin.whistdemo.component.Player;
import com.weijin.whistdemo.templates.Whist;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Popup;

import java.io.File;
import java.net.URL;
import java.util.*;

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
    private HashMap<ImageView, Card> handMap = new HashMap<>(13);
    Whist whist = new Whist();
    Deck deck = new Deck();

    public GamingStageController() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       <tt>null</tt> if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Player you = new Player();
        List<Player> players = whist.loadPlayers(you);
        List<Player> turnList = whist.initGame(players);
        whist.dealCards(turnList);

        Card[] sortedCards = sortCards(you.getCurrHand());
        handList.addAll(Arrays.asList(sortedCards));

        List<ImageView> p1handList = Arrays.asList(p1iv1, p1iv2, p1iv3, p1iv4, p1iv5, p1iv6, p1iv7, p1iv8, p1iv9, p1iv10, p1iv11, p1iv12, p1iv13);
        p1ivList.addAll(p1handList);
        for (int i = 0; i < p1ivList.size(); i++) {
            handMap.put(p1ivList.get(i), handList.get(i));
        }
        List<ImageView> p2handList = Arrays.asList(p2iv1, p2iv2, p2iv3, p2iv4, p2iv5, p2iv6, p2iv7, p2iv8, p2iv9, p2iv10, p2iv11, p2iv12, p2iv13);
        p2ivList.addAll(p2handList);

        List<ImageView> p3handList = Arrays.asList(p3iv1, p3iv2, p3iv3, p3iv4, p3iv5, p3iv6, p3iv7, p3iv8, p3iv9, p3iv10, p3iv11, p3iv12, p3iv13);
        p3ivList.addAll(p3handList);

        List<ImageView> p4handList = Arrays.asList(p4iv1, p4iv2, p4iv3, p4iv4, p4iv5, p4iv6, p4iv7, p4iv8, p4iv9, p4iv10, p4iv11, p4iv12, p4iv13);
        p4ivList.addAll(p4handList);

        Rotate rotate = new Rotate();
        for (int i = 0; i < p1ivList.size(); i++) {
            p1ivList.get(i).setImage(CardtoImage(handList.get(i)));
        }

        for (ImageView imageView : p2ivList) {
            imageView.setRotate(90);
            imageView.setImage(CardtoImage(null));
            imageView.setRotate(-90);
        }
        for (ImageView imageView : p3ivList) {
            imageView.setImage(CardtoImage(null));
        }
        for (ImageView imageView : p4ivList) {
            imageView.setRotate(90);
            imageView.setImage(CardtoImage(null));
            imageView.setRotate(90);
        }

    }

    public void click(ActionEvent event) throws Exception {
        System.out.println("Button Clicked!");
        for (ImageView imageView : p1ivList) {
            if (imageView.getY() < 0) {
                for (ImageView iv : handMap.keySet()) {
                    if (imageView == iv) {
                        System.out.println(handMap.get(iv).getId());
                        if (whist.isAllowed(whist.selectAcard(handMap.get(iv)), deck)) {
                            cardDrop(iv);
                            //出牌界面显示出的牌
                            p1played.setImage(CardtoImage(handMap.get(iv)));
                            //重新排序
                            rearrange(iv, handList, p1ivList, handMap);
                        } else {
                            Label label = new Label("This card is not allowed! Change one please!");

                            Popup popup = new Popup();

                            label.setStyle("-fx-background-color: grey;");
                            popup.getContent().add(label);

                            label.setMinWidth(100);
                            label.setMinHeight(80);
                            popup.setX(900);

                            popup.setY(1280);
                            popup.setAutoFix(true);
                            popup.setAutoHide(true);
                            popup.setHideOnEscape(true);
                            popup.show(p1iv1.getScene().getWindow());
                        }
                    }
                }
            }
        }
        Deck deck = new Deck();
//        if (deck.getCardAtDeck()==0){
//            System.out.println("没有牌了");
//            //todo 算分
//            SettleStage settleStage =new SettleStage();
//            settleStage.showWindow();}
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
}