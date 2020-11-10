package Bomberman.gameObjects.creatures;

import Bomberman.Game;
import Bomberman.map.Map;
import Bomberman.sounds.Playlist;
import Bomberman.gameObjects.Bomb;
import Bomberman.graphics.gallery.Luigi;
import Bomberman.graphics.gallery.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Creature {
    private final ArrayList<Bomb> bombs = new ArrayList<>();
    private int bagCapacity;
    private int cntBomb;
    private int bombLength;
    private final String name;

    private static int cnt = 0;
    private final int stable;

    public Player(Game game, Map gameMap, int x, int y, Resources asset) {
        super(game, gameMap, x, y, asset);
        current = asset.Down[0];
        cnt++;
        stable = cnt;
        hitBox.x = 10;
        hitBox.y = 18;
        hitBox.width = 30;
        hitBox.height = 32;
        cntBomb = 0;
        bagCapacity = 1;
        bombLength = 1;
        if (asset instanceof Luigi) {
            name = "Luigi";
        } else {
            name = "Minotaur";
        }
    }

    @Override
    public void update() {
        if (alive) {
            yMove = 0;
            xMove = 0;
            checkAlive();
            aDown.update();
            aUp.update();
            aLeft.update();
            aRight.update();
            powerUpdate();
            if (stable == 1 || stable == 3) {
                if (!game.getKeyMN().keys[KeyEvent.VK_SPACE]) {
                    if (game.getKeyMN().keys[KeyEvent.VK_W]) {
                        current = asset.Up[0];
                        yMove -= speed;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_S]) {
                        current = asset.Down[0];
                        yMove += speed;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_A]) {
                        current = asset.Left[0];
                        xMove -= speed;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_D]) {
                        current = asset.Right[0];
                        xMove += speed;
                    }
                } else if (game.getKeyMN().keys[KeyEvent.VK_SPACE]) {
                    if (cntBomb >= 0) {
                        int xBomb = 0;
                        int yBomb = 0;

                        if ((x + hitBox.x + hitBox.width) % Resources.tWidth > 32) {
                            yBomb = (x + hitBox.x + hitBox.width) / Resources.tWidth;
                        } else if ((x + hitBox.x + hitBox.width) % Resources.tWidth <= 32) {
                            yBomb = (x + hitBox.x) / Resources.tWidth;
                        }

                        if ((y + hitBox.y + hitBox.height) % Resources.tHeight > 32) {
                            xBomb = (y + hitBox.y + hitBox.height) / Resources.tHeight;
                        } else if ((y + hitBox.y + hitBox.height) % Resources.tHeight <= 32) {
                            xBomb = (y + hitBox.y) / Resources.tHeight;
                        }

                        char gameCoor = gameMap.getGameCoor(xBomb, yBomb);

                        if (cntBomb < bagCapacity && gameCoor == '0' &&
                           (xBomb != game.getGameScene1().getPortal().getY() / Resources.tWidth ||
                            yBomb != game.getGameScene1().getPortal().getX() / Resources.tWidth)) {
                            Playlist.setBomb.play();
                            Bomb bomb = new Bomb(game, gameMap, yBomb * Resources.tWidth,
                                    xBomb * Resources.tHeight, bombLength, this);
                            bombs.add(bomb);
                            bomb.setBombed(true);
                        }
                    }
                }
            } else if (stable == 2) {
                if (!game.getKeyMN().keys[KeyEvent.VK_P]) {
                    if (game.getKeyMN().keys[KeyEvent.VK_UP]) {
                        current = asset.Up[0];
                        yMove -= speed;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_DOWN]) {
                        current = asset.Down[0];
                        yMove += speed;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_LEFT]) {
                        current = asset.Left[0];
                        xMove -= speed;
                    }
                    if (game.getKeyMN().keys[KeyEvent.VK_RIGHT]) {
                        current = asset.Right[0];
                        xMove += speed;
                    }
                } else if (game.getKeyMN().keys[KeyEvent.VK_P]) {
                    if (cntBomb >= 0) {
                        int xBomb = 0;
                        int yBomb = 0;

                        if ((x + hitBox.x + hitBox.width) % Resources.tWidth > 32) {
                            yBomb = (x + hitBox.x + hitBox.width) / Resources.tWidth;
                        } else if ((x + hitBox.x + hitBox.width) % Resources.tWidth <= 32) {
                            yBomb = (x + hitBox.x) / Resources.tWidth;
                        }

                        if ((y + hitBox.y + hitBox.height) % Resources.tHeight > 32) {
                            xBomb = (y + hitBox.y + hitBox.height) / Resources.tHeight;
                        } else if ((y + hitBox.y + hitBox.height) % Resources.tHeight <= 32) {
                            xBomb = (y + hitBox.y) / Resources.tHeight;
                        }

                        char gameCoor = gameMap.getGameCoor(xBomb, yBomb);
                        if (cntBomb < bagCapacity && gameCoor == '0') {
                            Playlist.setBomb.play();
                            Bomb bomb = new Bomb(game, gameMap, yBomb * Resources.tWidth,
                                    xBomb * Resources.tHeight, bombLength, this);
                            bombs.add(bomb);
                            bomb.setBombed(true);
                        }
                    }
                }
            }
        }

        for (Bomb bomb : bombs) {
            bomb.update();
        }
        move();
    }

    public void powerUpdate() {
        char[][] tmp = gameMap.getPowerMap();

        if (tmp[(y + Resources.tWidth / 2) / 64][(x + Resources.pHeight / 2) / 64] == '1') {
            Playlist.eatPower.play();
            bombLength++;
            tmp[(y + Resources.tWidth / 2) / 64][(x + Resources.pHeight / 2) / 64] = '0';
        } else if (tmp[(y + Resources.tWidth / 2) / 64][(x + Resources.pHeight / 2) / 64] == '2') {
            Playlist.eatPower.play();
            speed += 0.25f;
            tmp[(y + Resources.tWidth / 2) / 64][(x + Resources.pHeight / 2) / 64] = '0';
        } else if (tmp[(y + Resources.tWidth / 2) / 64][(x + Resources.pHeight / 2) / 64] == '3') {
            Playlist.eatPower.play();
            bagCapacity++;
            tmp[(y + Resources.tWidth / 2) / 64][(x + Resources.pHeight / 2) / 64] = '0';
        }
    }

    public void plusCntBomb() {
        this.cntBomb += 1;
    }

    public void subCntBomb() {
        this.cntBomb -= 1;
    }

    public String getName() {
        return name;
    }

    public int getBagCapacity() {
        return bagCapacity;
    }

    public int getBombLength() {
        return bombLength;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void render(Graphics g) {
        if (alive) {
            g.drawImage(getFrame(), x, y, Resources.pWidth, Resources.pHeight, null);
            //g.setColor(Color.blue); // debugging purpose
            //g.fillRect(x + hitBox.x, y + hitBox.y, hitBox.width, hitBox.height); // debugging purpose
            for (Bomb bomb : bombs) {
                bomb.render(g);
            }
        } else {
            xMove = 0;
            yMove = 0;
            deadTimer = System.nanoTime();
            deadNow += System.nanoTime() - deadTimer;
            if (deadNow < 20000) {
                g.drawImage(Resources.dead, x, y, null);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        bombs.clear();
        cntBomb = 0;
        bombLength = 1;
        bagCapacity = 1;
        speed = DEFAULT_SPEED;
    }
}
