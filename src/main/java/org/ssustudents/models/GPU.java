package org.ssustudents.models;

import lombok.Data;
import lombok.Getter;
import org.ssustudents.App;
import oshi.hardware.GraphicsCard;

import java.util.ArrayList;
import java.util.List;

@Data
public class GPU {
    private List<Card> cards;

    public GPU() {
//        updateGPUInfo();
        cards = new ArrayList<>();
    }

    public void updateGPUInfo() {
        List<GraphicsCard> graphicsCards = App.si.getHardware().getGraphicsCards();

        for (GraphicsCard card : graphicsCards) {
            cards.add(new Card(card.getName(), card.getVRam()));
        }
    }

    public static class Card {
        @Getter
        private String name;
        @Getter
        private long memory;

        public Card(String name, long memory) {
            this.name = name;
            this.memory = memory;
        }
    }
}
