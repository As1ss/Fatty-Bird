package fattybird.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Texture ground;
    private float backGroundScroll;
    private int backGround_loop_point;
    private float groundScroll;
    private int background_xSpeed;
    private int ground_xSpeed;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg_night.png");
        ground = new Texture("ground.png");
        backGroundScroll = 0;
        groundScroll = 0;
        background_xSpeed = 30;
        ground_xSpeed = 180;
        backGround_loop_point = 313;

    }

    public void update(float delta) {
        backGroundScroll = (backGroundScroll + background_xSpeed * delta) % 313;
        groundScroll = (groundScroll + ground_xSpeed * delta) % backGround_loop_point;
    }


    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        batch.begin();
        batch.draw(background, -backGroundScroll, 0, ground.getWidth(), Gdx.graphics.getHeight());
        batch.draw(ground, -groundScroll, 0, ground.getWidth(), ground.getHeight());
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}
