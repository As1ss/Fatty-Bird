package fattybird.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bird {

    private Texture texture;
    private float x;
    private float y;
    private float height;
    private float width;
    private float GRAVITY;
    private float ySpeed;

    public Bird() {
        this.texture = new Texture("bird.png");
        this.height = 108;
        this.width = 168;
        this.x = Gdx.graphics.getWidth() / 2 - (this.width / 2);
        this.y = Gdx.graphics.getHeight() / 2 - (this.height / 2);
        this.GRAVITY = 30;
        this.ySpeed = 0;
    }


    public void update(float delta) {
        this.ySpeed += GRAVITY * delta;
        this.y -= this.ySpeed;

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

    public float getYSpeed() {
        return this.ySpeed;
    }

    public void setYSpeed(float newYSpeed) {
        this.ySpeed = newYSpeed;
    }

    public void setGRAVITY(float newGravity) {
        this.GRAVITY = newGravity;
    }
}
