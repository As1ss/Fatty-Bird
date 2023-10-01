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
import com.badlogic.gdx.utils.Array;

import java.util.Random;


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
    //private Pipe pipe;
    private Array<Pipe> pipes;
    private Array<Pipe> reversedPipes;
    private Pipe pipe;
    private Random rng;
    private float randomPipeHeight;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        ground = new Texture("ground.png");
        backGroundScroll = 0;
        groundScroll = 0;
        background_xSpeed = 110;
        ground_xSpeed = 750;
        backGround_loop_point = 2850;
        bird = new Bird();
        pipe = new Pipe();

        pipes = new Array<>();
        reversedPipes = new Array<>();
        pipes.add(pipe);

        rng = new Random();

        randomPipeHeight = 0f;


    }


    public void update(float delta) {
        backGroundScroll = (backGroundScroll + background_xSpeed * delta) % backGround_loop_point;
        groundScroll = (groundScroll + ground_xSpeed * delta) % 313;


        bird.update(delta);
        addPipes(delta);

        if (Gdx.input.isTouched()) {
            bird.setYSpeed(-13);

        }

    }


    private void addPipes(float delta) {
        for (Pipe i : pipes) {
            i.update(delta);
            randomPipeHeight = rng.nextFloat() * (pipe.getHeight() + 350f - pipe.getHeight()) + pipe.getHeight();
            Pipe lastPipe = pipes.peek(); // Obtener la última tubería en la lista
            if (lastPipe != null && lastPipe.getX() + lastPipe.getWidth() < Gdx.graphics.getWidth()) {
                Pipe newPipe = new Pipe();
                newPipe.setX(Gdx.graphics.getWidth() + 300f);
                newPipe.setHeight(randomPipeHeight);
                pipes.add(newPipe);
                if (i.getX() + i.getWidth() < 0) {
                    pipes.removeValue(i, true);
                }
            }

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
        batch.draw(ground, -groundScroll, 0, ground.getWidth(), 220);
        batch.draw(bird.getTexture(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());
        for (Pipe i : pipes) {
            batch.draw(i.getTexture(), i.getX(), 220, i.getWidth(), i.getHeight());
            batch.draw(i.getReversedTexture(), i.getX(), Gdx.graphics.getHeight() - i.getHeight(), i.getWidth(), i.getHeight());

        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ground.dispose();
        bird.getTexture().dispose();
        pipe.getTexture().dispose();

    }
}
