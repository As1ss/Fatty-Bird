package fattybird.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bird {

    private Texture texture;
    private float x;
    private float y;
    private float height;
    private float width;

    public Bird() {
        this.texture = new Texture("bird.png");
        this.height = 128;
        this.width = 128;
        this.x = Gdx.graphics.getWidth() / 2 - (this.width / 2);
        this.y = Gdx.graphics.getHeight() / 2 - (this.height / 2);
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
