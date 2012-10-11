package com.example;

import com.example.framework.*;

import javax.microedition.khronos.opengles.GL10;

public class AnimationScreen extends Screen {


    static final float WORLD_WIDTH = 4.8f;
    static final float WORLD_HEIGHT = 3.2f;

    static final int NUM_CAVEMEN = 1;
    GLGraphics glGraphics;
    SuperMario[] superMarios;
    SpriteBatcher batcher;
    Camera2D camera;
    Texture texture;
    Animation walkAnim;


    static class SuperMario extends DynamicGameObject {
        public float walkingTime = 0;

        public SuperMario(float x, float y, float width, float height) {
            super(x, y, width, height);
            this.position.set((float)Math.random() * WORLD_WIDTH,
                    (float)Math.random() * WORLD_HEIGHT);
            this.velocity.set(Math.random() > 0.5f?-0.5f:0.5f, 0);
            this.walkingTime = (float)Math.random() * 10;
        }

        public void update(float deltaTime) {
            position.add(velocity.x * deltaTime, velocity.y * deltaTime);
            if(position.x < 0) position.x = WORLD_WIDTH;
            if(position.x > WORLD_WIDTH) position.x = 0;
            walkingTime += deltaTime;
        }
    }

    public AnimationScreen(Game game) {
        super(game);
        glGraphics = ((AnimationExampleActivity) game).getGLGraphics();
        superMarios = new SuperMario[NUM_CAVEMEN];
        for (int i = 0; i < NUM_CAVEMEN; i++) {
            superMarios[i] = new SuperMario((float) Math.random(), (float) Math.random(), 1, 1);
        }
        batcher = new SpriteBatcher(glGraphics, NUM_CAVEMEN);
        camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
    }

    @Override
    public void resume() {
        texture = new Texture(((AnimationExampleActivity) game), "Mario-walk.png");
        walkAnim = new Animation(0.2f,
                new TextureRegion(texture, 0, 0, 17, 40),
                new TextureRegion(texture, 17, 0, 17, 40),
                new TextureRegion(texture, 34, 0, 17, 40),
                new TextureRegion(texture, 51, 0, 17, 40),
                new TextureRegion(texture, 68, 0, 17, 40),
                new TextureRegion(texture, 85, 0, 17, 40),
                new TextureRegion(texture, 102, 0, 17, 40),
                new TextureRegion(texture, 119, 0, 17, 40));
//                new TextureRegion(texture, 0, 0, 64, 64),
//                new TextureRegion(texture, 64, 0, 64, 64),
//                new TextureRegion(texture, 128, 0, 64, 64),
//                new TextureRegion(texture, 192, 0, 64, 64));
    }

    @Override
    public void update(float deltaTime) {
        int len = superMarios.length;
        for (int i = 0; i < len; i++) {
            superMarios[i].update(deltaTime);
        }
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
        int len = superMarios.length;
        for (int i = 0; i < len; i++) {
            SuperMario superMario = superMarios[i];
            TextureRegion keyFrame = walkAnim.getKeyFrame(superMario.walkingTime, Animation.ANIMATION_LOOPING);
            batcher.drawSprite(superMario.position.x, superMario.position.y, superMario.velocity.x < 0 ? 1 : -1, 1, keyFrame);
        }
        batcher.endBatch();
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }

}
