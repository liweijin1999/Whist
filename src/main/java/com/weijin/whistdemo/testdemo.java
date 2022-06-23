package com.weijin.whistdemo;

import java.util.Random;

//int playedIndex = handList.indexOf(handMap.get(iv));
//                        int playedIVindex = p1ivList.indexOf(iv);
//                        handList.remove(handMap.get(iv));
//                        if (playedIVindex<6){
//                            for(int i=playedIVindex-1;i>=playedIVindex-playedIndex;i--){
//                                p1ivList.get(i+1).setImage(URLtoImage(handMap.get(p1ivList.get(i))));
//                                handMap.put(p1ivList.get(i+1), handMap.get(p1ivList.get(i)));
//                            }
//                            p1ivList.get(playedIVindex-playedIndex).setImage(null);
//                            p1ivList.get(playedIVindex-playedIndex).setFitHeight(0);
//                            p1ivList.get(playedIVindex-playedIndex).setFitWidth(0);
//                            handMap.put(p1ivList.get(playedIVindex-playedIndex), null);
//
//                            if (handList.size()%2==1&&(p1ivList.size()-handList.size())/2!=playedIVindex-playedIndex) {
//                                for (int j = playedIVindex-playedIndex; j < p1ivList.size()-1; j++) {
//                                        p1ivList.get(j).setImage(p1ivList.get(j+1).getImage());
//                                        p1ivList.get(j).setFitHeight(p1ivList.get(j+1).getFitHeight());
//                                        p1ivList.get(j).setFitWidth(p1ivList.get(j+1).getFitWidth());
//                                        handMap.put(p1ivList.get(j), handMap.get(p1ivList.get(j+1)));
//                                        p1ivList.get(j+1).setImage(null);
//                                        p1ivList.get(j+1).setFitHeight(0);
//                                        p1ivList.get(j+1).setFitWidth(0);
//                                        handMap.put(p1ivList.get(j+1), null);
//                                }
//                            }
//                        }else {
//                            for(int i=playedIVindex;i<=p1ivList.size()-(handList.size()-playedIndex)-1;i++){
//                                p1ivList.get(i).setImage(p1ivList.get(i+1).getImage());
//                                handMap.put(p1ivList.get(i), handMap.get(p1ivList.get(i+1)));
//                                }
//                            p1ivList.get(p1ivList.size()-(handList.size()-playedIndex)-1).setImage(null);
//                            p1ivList.get(p1ivList.size()-(handList.size()-playedIndex)-1).setFitHeight(0);
//                            p1ivList.get(p1ivList.size()-(handList.size()-playedIndex)-1).setFitWidth(0);
//                            handMap.put(p1ivList.get(playedIVindex-playedIndex), null);
//                            if (handList.size()%2==1&&(p1ivList.size()-handList.size())/2!=playedIVindex-playedIndex) {
//                                for (int j = p1ivList.size()-(handList.size()-playedIndex); j >0; j--) {
//                                    p1ivList.get(j).setImage(p1ivList.get(j-1).getImage());
//                                    p1ivList.get(j).setFitHeight(p1ivList.get(j-1).getFitHeight());
//                                    p1ivList.get(j).setFitWidth(p1ivList.get(j-1).getFitWidth());
//                                    handMap.put(p1ivList.get(j), handMap.get(p1ivList.get(j-1)));
//                                    p1ivList.get(j-1).setImage(null);
//                                    p1ivList.get(j-1).setFitHeight(0);
//                                    p1ivList.get(j-1).setFitWidth(0);
//                                    handMap.put(p1ivList.get(j-1), null);
//                                }
//                            }
//                        }

public class testdemo {
    public static void main(String[] args) {
        int[] arraytest = new int[13];
        int[] arraytest2 = new int[13];
        for (int i = 0; i < arraytest.length; i++) {
            arraytest[i] = i + 1;
        }
        for (int j : arraytest) {
            System.out.println(j);
        }
        int seleted1 = 8, seleted2 = 12;

    }
}
