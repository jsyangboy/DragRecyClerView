package itboy.dragrecyclerview;

import java.util.ArrayList;
import java.util.List;

import itboy.dragrecyclerview.bean.A_Item;
import itboy.dragrecyclerview.bean.Base_Item;

public class Const {



    /**
     * @return
     */
    public static List<Base_Item> getAItem() {
        List<Base_Item> showAItem = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            showAItem.add(new A_Item("A_Item" + i, "A_Item_" + i));
        }
        return showAItem;
    }


    /**
     * @return
     */
    public static List<Base_Item> getBItem() {
        List<Base_Item> showAItem = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            showAItem.add(new A_Item("B_Item" + i, "B_Item_" + i));
        }
        return showAItem;
    }

}
