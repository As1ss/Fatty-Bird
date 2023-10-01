package fattybird.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Pipe {
    private float height;
    private float width;
    private Texture texture;
    private Texture reversedTexture = new Texture("reversedPipe.png");

    private float x;
    private float y;
    private float xSpeed;

    public Pipe() {
        this.texture = new Texture("pipe.png");
        this.height = 500f;
        this.width = 250f;
        this.x = Gdx.graphics.getWidth();
        this.y = 0;
        this.xSpeed = 750f;


    }

    public void update(float Delta) {
        this.x -= this.xSpeed * Delta;


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

    public Texture getTexture() {
        return texture;
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

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public Texture getReversedTexture() {
        return reversedTexture;
    }

    public void setReversedTexture(Texture reversedTexture) {
        this.reversedTexture = reversedTexture;
    }
}
