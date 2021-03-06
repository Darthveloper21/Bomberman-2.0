package Bomberman.scenes;

import Bomberman.Game;
import Bomberman.graphics.SceneButton;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;

public class OptionScene extends MyScene {
    private final SceneButton offline;
    private final SceneButton online;
    private final SceneButton back;

    public OptionScene(Game game) {
        super(game);
        offline = new SceneButton(game, Resources.offline1, Resources.offline2, 400, 140);
        online = new SceneButton(game, Resources.online1, Resources.online2, 400, 240);
        back = new SceneButton(game, Resources.back1, Resources.back2, 440, 340);
    }

    @Override
    public void update() {
        offline.update(game.getGameScene2());
        online.update(game.getGameScene3());
        back.update(game.getMenuScene());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Resources.menuBG, 0, 0, null);
        offline.render(g);
        online.render(g);
        back.render(g);
    }
}
