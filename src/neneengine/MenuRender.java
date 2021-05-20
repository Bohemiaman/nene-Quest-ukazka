package neneengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nenelogic.Menu;

/**
 *
 * @author Martin Joukl
 */
public class MenuRender {

    private Menu menu;
    private Color backgroundColor;
    private Color textColor = Color.rgb(105, 64, 93);
    //zatím nevybrána
    private Color selectionColor = Color.PINK;
    private final Font font = Font.font("Arial", 50);

    double totalTextHeight;
    double maxTextWidth;
    double maxTextHeight;

    double spacing = 20;
    double logoHeight;

    Image logo;
    
    

    public MenuRender() throws FileNotFoundException {
        this.menu = new Menu();
        this.logo = new Image(new FileInputStream("resources/neneQuestLogo.png"));
        initializeSize();

    }

    public MenuRender(Menu menu) throws FileNotFoundException {
        this();
        this.menu = menu;
        initializeSize();
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    private void initializeSize() {
        List<String> menuList = menu.getSelectionItems();

        maxTextWidth = calculateMaxTextWidth(menuList);
        maxTextHeight = calculateMaxTextHeight(menuList);
        totalTextHeight = calculateTotalHeight(menuList, maxTextHeight);
    }

    public void render(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(font);

        List<String> menuList = menu.getSelectionItems();

        gc.setFill(textColor);

        for (int i = 0; i < menuList.size(); i++) {

            double textStartX = canvas.getWidth() / 2 - maxTextWidth / 2;
            // /2 není, tady startuji od zhora, posunuto o spacing
            double textStartY = canvas.getHeight() - (totalTextHeight - ((0.5 + i) * maxTextHeight)) - spacing;

            logoHeight = canvas.getHeight() - (totalTextHeight + 2 * spacing + maxTextHeight + 5 * spacing);

            gc.drawImage(logo, 5 * spacing, 5 * spacing, canvas.getWidth() - 10 * spacing, logoHeight);

            if (i == menu.getSelectedIndex()) {
                gc.setStroke(textColor);
                gc.setLineWidth(4);
                //čtvereček volby
                double[] pointsX = {textStartX - 2 * spacing, textStartX - 2 * spacing, textStartX - (spacing - 5)};
                double[] pointsY = {textStartY, textStartY - 0.5 * maxTextHeight, textStartY - 0.25 * maxTextHeight};

                gc.strokePolygon(pointsX, pointsY, 3);
            }

            gc.fillText(menuList.get(i), textStartX, textStartY);
        }
    }

    private double calculateFontWidth(String inputText, Font font) {
        Text text = new Text(inputText);
        text.setFont(font);
        text.getBoundsInLocal();
        return text.getBoundsInLocal().getWidth();
    }

    private double calculateFontHeight(String inputText, Font font) {
        Text text = new Text(inputText);
        text.setFont(font);
        text.getBoundsInLocal();
        return text.getBoundsInLocal().getHeight();
    }
    //tato metoda spočítá maximální šířku textu ze seznamu

    private double calculateMaxTextWidth(List<String> texts) {
        double maxWidth = 0;
        for (int i = 0; i < texts.size(); i++) {
            if (maxWidth < calculateFontWidth(texts.get(i), font)) {
                maxWidth = calculateFontWidth(texts.get(i), font);
            }
        }
        return maxWidth;
    }

    //tato metoda spočítá maximální výšku textu ze seznamu
    private double calculateMaxTextHeight(List<String> texts) {
        double maxHeight = 0;
        for (int i = 0; i < texts.size(); i++) {
            if (maxHeight < calculateFontHeight(texts.get(i), font)) {
                maxHeight = calculateFontHeight(texts.get(i), font);
            }
        }
        return maxHeight;

    }

    //tato metoda spočítá celkovou výšku textů za pomocí výšky, zadané v parametru
    private double calculateTotalHeight(List<String> texts, double height) {
        double totalHeight = 0;
        for (int i = 0; i < texts.size(); i++) {
            totalHeight += height;
        }
        return totalHeight;

    }
}
