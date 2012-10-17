package com.example;

import com.example.framework.*;

import javax.microedition.khronos.opengles.GL10;

public class AnimationScreen extends Screen {

    GLGraphics glGraphics;
    SuperMario superMario;
    SpriteBatcher batcher;
    Camera2D camera;
    Texture texture;
    Animation walkAnim;

    public AnimationScreen(Game game) {
        super(game);
        glGraphics = ((MarioActivity) game).getGLGraphics();
        superMario = new SuperMario(MarioActivity.WORLD_HEIGHT / 2, MarioActivity.WORLD_WIDTH / 2, 1, 1);
        batcher = new SpriteBatcher(glGraphics);
        camera = new Camera2D(glGraphics, 6, 4);
    }

    @Override
    public void resume() {
        texture = new Texture(((MarioActivity) game), "Mario-walk.png");
        walkAnim = new Animation(0.19f,
                new TextureRegion(texture, 0, 0, 19.75f, 35),
                new TextureRegion(texture, 19.75f, 0, 19.75f, 35),
                new TextureRegion(texture, 39.5f, 0, 19.75f, 35),
                new TextureRegion(texture, 59.25f, 0, 19.75f, 35),
                new TextureRegion(texture, 79, 0, 19.75f, 35),
                new TextureRegion(texture, 98.75f, 0, 19.75f, 35),
                new TextureRegion(texture, 118.5f, 0, 19.75f, 35),
                new TextureRegion(texture, 138.25f, 0, 19.75f, 35));
    }

    @Override
    public void update(float deltaTime) {
        superMario.update(deltaTime);
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.setViewportAndMatrices();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(texture);
        TextureRegion keyFrame = walkAnim.getKeyFrame(superMario.walkingTime, Animation.ANIMATION_LOOPING);
        batcher.drawSprite(superMario.position.x, superMario.position.y, superMario.velocity.x < 0 ? -1 : 1, 1, keyFrame);
        batcher.endBatch();
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }
}
