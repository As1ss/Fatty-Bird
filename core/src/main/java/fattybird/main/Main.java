package fattybird.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;

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
    private Pipe reversedPipe;
    private Random rng;
    private float randomPipeHeight;
    private State state;


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
        reversedPipe = new Pipe();

        pipes = new Array<>();
        reversedPipes = new Array<>();
        pipes.add(pipe);
        reversedPipes.add(pipe);
        rng = new Random();
        randomPipeHeight = 0f;

        state = State.SCROLLING;


    }


    public void update(float delta) {
        backGroundScroll = (backGroundScroll + background_xSpeed * delta) % backGround_loop_point;
        groundScroll = (groundScroll + ground_xSpeed * delta) % 313;


        bird.update(delta);
        addPipes(delta);

        for (Pipe i : pipes) {
            if (isCollision(bird,i)) {
                state = State.PAUSE;
            }
        }
        for (Pipe j : reversedPipes) {
            if (isCollision(bird,j)) {
                state = State.PAUSE;
            }
        }



        if (Gdx.input.isTouched()) {
            bird.setYSpeed(-13);
        }

    }
    public boolean isCollision(Bird bird,Pipe pipe){
        if (bird.getX() < pipe.getX() + pipe.getWidth() &&
            bird.getX() + bird.getWidth() > pipe.getX() &&
            bird.getY() < pipe.getY() + pipe.getHeight() &&
            bird.getY() + bird.getHeight() > pipe.getY() ){
            return true;
        }
        return false;
    }

    public void pause() {


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
                reversedPipes.add(newPipe);
                if (j.getX() + j.getWidth() < 0) {
                    reversedPipes.removeValue(j, true);
                }
            }

        }
    }

    @Override
    public void render() {


        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        switch (state) {
            case SCROLLING:
                update(delta);
                break;
            case PAUSE:
                pause();
                break;
        }
        batch.begin();
        batch.draw(background, -backGroundScroll, 0, 8000, Gdx.graphics.getHeight());
        batch.draw(ground, -groundScroll, 0, ground.getWidth(), 220);
        batch.draw(bird.getTexture(), bird.getX(), bird.getY(), bird.getWidth()+20f, bird.getHeight()+20f);
        for (Pipe i : pipes) {
            batch.draw(i.getTexture(), i.getX(), 220, i.getWidth(), i.getHeight());
            //batch.draw(i.getReversedTexture(), i.getX(), Gdx.graphics.getHeight() - i.getHeight(), i.getWidth(), i.getHeight());
        }
        for (Pipe j : reversedPipes) {
            batch.draw(j.getTexture(), j.getX(), j.getY(), j.getWidth(), j.getHeight());
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
