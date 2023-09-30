package fattybird.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;


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
    private Bird bird;




    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        ground = new Texture("ground.png");
        backGroundScroll = 0;
        groundScroll = 0;
        background_xSpeed = 60;
        ground_xSpeed = 200;
        backGround_loop_point = 2850;
        bird = new Bird();



    }



    public void update(float delta) {
        backGroundScroll = (backGroundScroll + background_xSpeed * delta) % backGround_loop_point;
        groundScroll = (groundScroll + ground_xSpeed * delta) % 313;

        bird.update(delta);

        if (Gdx.input.isTouched()){
            bird.setYSpeed(-10);

        }

    }


    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        update(delta);
        batch.begin();
        batch.draw(background, -backGroundScroll, 0, 8000, Gdx.graphics.getHeight());
        batch.draw(ground, -groundScroll, 0, ground.getWidth(), 219);
        batch.draw(bird.getTexture(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();

    }
}
