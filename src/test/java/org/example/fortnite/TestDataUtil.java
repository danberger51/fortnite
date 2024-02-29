package org.example.fortnite;

import org.example.fortnite.models.Skin;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static List<Skin> getTestSkins(){
        List<Skin> skins = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Skin skin = new Skin();
            skin.setId(i);
            skin.setName("Skin"+i);
            skin.setRarity("Episch");
            skins.add(skin);
        }

        return skins;
    }
}
