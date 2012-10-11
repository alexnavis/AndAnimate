package com.example;

import com.example.framework.GameObject;
import com.example.framework.math.Vector2;

import static com.example.AnimationScreen.WORLD_HEIGHT;
import static com.example.AnimationScreen.WORLD_WIDTH;

class SuperMario extends GameObject {
    public final Vector2 velocity;
    public final Vector2 accel;

    public float walkingTime = 0;

    public SuperMario(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();

        this.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2);
        this.velocity.set(Math.random() > 0.5f ? -0.5f : 0.5f, 0);
        this.walkingTime = (float) Math.random() * 10;
    }

    public void update(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        if (position.x < 0) position.x = WORLD_WIDTH;
        if (position.x > WORLD_WIDTH) position.x = 0;
        walkingTime += deltaTime;
    }
}
