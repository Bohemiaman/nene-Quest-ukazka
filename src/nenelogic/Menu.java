package nenelogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Martin Joukl
 */
public final class Menu {

    //menu bude jednoduché, stačí String
    private final List<String> selectionItems;
    private int selectedIndex;

    public Menu() {
        this.selectionItems = new ArrayList<>();
    }

    public Menu(String... itemText) {
        this.selectionItems = new ArrayList<>();
        this.addItems(itemText);
    }

    public Menu(List<String> itemText) {
        this.selectionItems = new ArrayList<>(itemText);
    }

    public void addItem(String itemText) {
        selectionItems.add(itemText);
    }

    public void addItems(String... itemText) {
        //stream místo for
        Arrays.stream(itemText).forEachOrdered((t) -> {
            addItem(t);
        });
    }

    public void addItems(List<String> itemText) {
        selectionItems.addAll(itemText);
    }

    public List<String> getSelectionItems() {
        return selectionItems;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int newSelection) {
        if (selectedIndex > 0 && selectedIndex > selectionItems.size()) {
            this.selectedIndex = newSelection;
        }
    }

    //metoda posune index o 1 dolu (-1), v případě, že se jedná o první prvek,
    //index se neposune
    public void downIndex() {
        if (selectedIndex > 0) {
            this.selectedIndex--;
        }
    }

    //metoda posune index o 1 nahoru (+1), v případě, že se jedná o poslední prvek,
    //index se neposune
    public void upIndex() {
        if (selectedIndex < selectionItems.size()-1) {
            this.selectedIndex++;
        }
    }

}
