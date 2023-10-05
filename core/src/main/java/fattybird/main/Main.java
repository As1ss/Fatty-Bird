package fattybird.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


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
    private Array<Pipe> pipes;
    private Array<Pipe> reversedPipes;
    private Pipe pipe;
    private Pipe reversedPipe;
    private Random rng;
    private float randomPipeHeight;
    private State state;
    private int score;
    private BitmapFont bigFont;
    private BitmapFont middleFont;
    private BitmapFont littleFont;
    private Boolean scoreUP;


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
        pipe.setY(220);
        pipe.setX(Gdx.graphics.getWidth());

        reversedPipe = new Pipe();
        reversedPipe.setY(Gdx.graphics.getHeight() - reversedPipe.getHeight());
        reversedPipe.setX(Gdx.graphics.getWidth());
        reversedPipe.setTexture(new Texture("reversedPipe.png"));

        pipes = new Array<>();
        reversedPipes = new Array<>();
        pipes.add(pipe);
        reversedPipes.add(reversedPipe);
        rng = new Random();
        randomPipeHeight = 0f;

        state = State.INITIAL;

        score = 0;

        bigFont = new BitmapFont(Gdx.files.internal("flappy-bird.fnt"));
        bigFont.setColor(Color.WHITE);
        bigFont.getData().scale(3);

        middleFont = new BitmapFont(Gdx.files.internal("flappy-bird.fnt"));
        middleFont.setColor(Color.WHITE);
        middleFont.getData().scale(2);

        littleFont = new BitmapFont(Gdx.files.internal("flappy-bird.fnt"));
        littleFont.setColor(Color.WHITE);
        littleFont.getData().scale(1);


        scoreUP = false;


    }


    public void update(float delta) {


        backGroundScroll = (backGroundScroll + background_xSpeed * delta) % backGround_loop_point;
        groundScroll = (groundScroll + ground_xSpeed * delta) % 313;


        bird.update(delta);


        for (Pipe i : pipes) {
            if (isCollision(bird, i)) {
                state = State.GAMEOVER;
            }
        }
        for (Pipe j : reversedPipes) {
            if (isCollision(bird, j)) {
                state = State.GAMEOVER;
            }
        }

        for (Pipe l : pipes) {
            if (bird.getX() >= l.getX() + l.getWidth() && !scoreUP) {
                score++;
                scoreUP = true;
            }
        }

        if (Gdx.input.isTouched()) {
            bird.setYSpeed(-10);
        }


    }

    public boolean isCollision(Bird bird, Pipe pipe) {
        if (bird.getX() < pipe.getX() + pipe.getWidth() &&
            bird.getX() + bird.getWidth() > pipe.getX() &&
            bird.getY() < pipe.getY() + pipe.getHeight() &&
            bird.getY() + bird.getHeight() > pipe.getY()) {
            return true;
        }
        return false;
    }

    public void pause(SpriteBatch batch) {

        batch.draw(background, -backGroundScroll, 0, 8000, Gdx.graphics.getHeight());
        batch.draw(ground, -groundScroll, 0, ground.getWidth(), 220);
        batch.draw(bird.getTexture(), bird.getX(), bird.getY(), bird.getWidth() + 20f, bird.getHeight() + 20f);
        bigFont.draw(batch, "Oops! you failed!", 20f, Gdx.graphics.getHeight() - 400f);
        middleFont.draw(batch, "Score: " + String.valueOf(score), Gdx.graphics.getWidth() / 2 - 200f, Gdx.graphics.getHeight() - 700f);
        middleFont.draw(batch, "Tap to play again!", 130f, Gdx.graphics.getHeight() / 2);
        bird.setY(-3000);
        if (Gdx.input.isTouched()) {
            resetPositions();
            state = State.SCROLLING;
        }
    }

    public void resetPositions() {
        score = 0;
        bird.setX(Gdx.graphics.getWidth() / 2 - (bird.getWidth() / 2));
        bird.setY(Gdx.graphics.getHeight() / 2 - (bird.getHeight() / 2));

        pipes.clear(); // Vaciar la lista de pipes
        reversedPipes.clear(); // Vaciar la lista de reversedPipes

        // Agregar un nuevo objeto Pipe a cada lista
        Pipe newPipe = new Pipe();
        newPipe.setX(Gdx.graphics.getWidth() + 300f);
        newPipe.setHeight(randomPipeHeight);
        newPipe.setY(220f);
        pipes.add(newPipe);

        Pipe newReversedPipe = new Pipe();
        newReversedPipe.setX(Gdx.graphics.getWidth() + 300f);
        newReversedPipe.setHeight(randomPipeHeight);
        newReversedPipe.setY(Gdx.graphics.getHeight() - randomPipeHeight);
        newReversedPipe.setTexture(new Texture("reversedPipe.png"));
        reversedPipes.add(newReversedPipe);
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
                newPipe.setY(220f);
                scoreUP = false;
                pipes.add(newPipe);
                if (i.getX() + i.getWidth() < 0) {
                    pipes.removeValue(i, true);
                }
            }


        }

        for (Pipe j : reversedPipes) {
            j.update(delta);
            randomPipeHeight = rng.nextFloat() * (reversedPipe.getHeight() + 350f - reversedPipe.getHeight()) + reversedPipe.getHeight();
            Pipe lastPipe = reversedPipes.peek(); // Obtener la última tubería en la lista
            if (lastPipe != null && lastPipe.getX() + lastPipe.getWidth() < Gdx.graphics.getWidth()) {
                Pipe newPipe = new Pipe();
                newPipe.setX(Gdx.graphics.getWidth() + 300f);
                newPipe.setHeight(randomPipeHeight);
                newPipe.setY(Gdx.graphics.getHeight() - randomPipeHeight);
                newPipe.setTexture(new Texture("reversedPipe.png"));
                scoreUP = false;
                reversedPipes.add(newPipe);
                if (j.getX() + j.getWidth() < 0) {
                    reversedPipes.removeValue(j, true);
                }
            }

        }
    }

    @Override
    public void render() {


        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        switch (state) {
            case INITIAL:
                initialEvent(batch);
                break;
            case SCROLLING:
                scrolling(batch);
                break;
            case GAMEOVER:
                pause(batch);

                break;
        }

        batch.end();
    }

    private void initialEvent(SpriteBatch batch) {
        bird.setY(-3000);
        update(Gdx.graphics.getDeltaTime());
        batch.draw(background, -backGroundScroll, 0, 8000, Gdx.graphics.getHeight());
        batch.draw(ground, -groundScroll, 0, ground.getWidth(), 220);
        batch.draw(bird.getTexture(), bird.getX(), bird.getY(), bird.getWidth() + 20f, bird.getHeight() + 20f);
        bigFont.draw(batch, "Fatty Bird", 200f, Gdx.graphics.getHeight() - 600f);
        littleFont.draw(batch, "Tap to begin!", 350f, Gdx.graphics.getHeight() - 800f);
        if (Gdx.input.isTouched()) {
            resetPositions();
            state = State.SCROLLING;
        }

    }

    private void scrolling(SpriteBatch batch) {

        update(Gdx.graphics.getDeltaTime());
        addPipes(Gdx.graphics.getDeltaTime());
        batch.draw(background, -backGroundScroll, 0, 8000, Gdx.graphics.getHeight());
        batch.draw(ground, -groundScroll, 0, ground.getWidth(), 220);
        batch.draw(bird.getTexture(), bird.getX(), bird.getY(), bird.getWidth() + 20f, bird.getHeight() + 20f);
        for (Pipe i : pipes) {
            batch.draw(i.getTexture(), i.getX(), 220, i.getWidth(), i.getHeight());

        }
        for (Pipe j : reversedPipes) {
            batch.draw(j.getTexture(), j.getX(), j.getY(), j.getWidth(), j.getHeight());
        }
        bigFont.draw(batch, "SCORE: " + String.valueOf(score), 30f, Gdx.graphics.getHeight() - 30f);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        ground.dispose();
        bird.getTexture().dispose();
        pipe.getTexture().dispose();
        reversedPipe.getTexture().dispose();

    }
}
