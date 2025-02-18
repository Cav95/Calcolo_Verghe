package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

import controller.scene.SceneControllerImpl;
import model.*;
import view.View;

public class MainMenuController extends SceneControllerImpl {
    public MainMenuController(View mainView) {
        super(mainView);
    }

    MultiQueue<Integer, String> tubolarList = new MultiQueueImpl<>();



    public void newTubolarList(String nameTubolar, int lengthTubolar, int quantity) {
        try {
            tubolarList.openNewQueue(nameTubolar);
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        tubolarList.addTubolar(Integer.valueOf(lengthTubolar), nameTubolar, quantity);
    }

    public void removeTubolarList(String nameTubolar, int lengthTubolar, int quantity) {
        tubolarList.removeTubolar(nameTubolar, Integer.valueOf(lengthTubolar));
    }

    public String partialCalco() {
        return CalcolatorTubolar

                .printCuttedTubolarSmoll(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue()));
    }

    public String totalCalco() {
        return CalcolatorTubolar
                .printCuttedTubolar(CalcolatorTubolar.calcoloTotal(tubolarList.getMultiQueue()));
    }

    public void restart() {
        this.tubolarList = new MultiQueueImpl<>();
    }

}
