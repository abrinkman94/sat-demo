package com.brinkman.satdemo;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;

public class SATDemo extends Game implements InputProcessor, Screen
{
	private Body body;
	private Body otherBody;
	private Body otherBody2;
	private HUD hud;
	private OrthographicCamera camera = new OrthographicCamera();

    private static final float WORLD_UNITS = 1/64.0f;
    private static final float APP_WIDTH = 1080 * WORLD_UNITS;
    private static final float APP_HEIGHT = 720 * WORLD_UNITS;
    private static final float MOVE_SPEED = 5.0f * WORLD_UNITS;

	@Override
	public void create () {
		body = new Body(3, 1, 1, 2);
		otherBody = new Body(300 * WORLD_UNITS, 0, 400 * WORLD_UNITS, 400 * WORLD_UNITS, 3);
		otherBody2 = new Body(6, 6, 1, 5);

		hud = new HUD(new MinimumTranslationVector());

		camera = new OrthographicCamera(APP_WIDTH, APP_HEIGHT);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

		Gdx.input.setInputProcessor(this);
	}

    @Override
    public void render() {
	    this.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hud.render(Gdx.graphics.getDeltaTime());

        body.getPosition().add(body.getVelocity());

        checkCollisions(body, otherBody);
        checkCollisions(body, otherBody2);

        keepInScreen();

        renderShape(body, Color.WHITE, null);
        renderShape(otherBody, Color.GREEN, otherBody.getFloatVertices());
        renderShape(otherBody2, Color.BLUE, null);

        camera.update();
    }

    private void renderShape(Body body, Color color, float[] vertices) {
        if (vertices == null) {
            ShapeRenderer renderer = new ShapeRenderer();
            renderer.setProjectionMatrix(camera.combined);
            renderer.begin(ShapeType.Line);
            renderer.setColor(color);
            renderer.rect(body.getPosition().x, body.getPosition().y, body.getWidth(), body.getHeight());
            renderer.end();
        } else {
            ShapeRenderer renderer3 = new ShapeRenderer();
            renderer3.setProjectionMatrix(camera.combined);
            renderer3.begin(ShapeType.Line);
            renderer3.setColor(color);
            renderer3.polygon(body.getFloatVertices());
            renderer3.end();
        }
    }

    private void checkCollisions(Body body, Body other) {
        MinimumTranslationVector mtv = new MinimumTranslationVector();
        float x = 0;
        float y = 0;

        if (BodyCollision.intersects(body, other, mtv)) {
            System.out.println("COLLISION");
            hud.setMtv(mtv);

            float mtvX = mtv.normal.x * mtv.depth;
            float mtvY = mtv.normal.y * mtv.depth;

            x = body.getVelocity().x - mtvX;
            y = body.getVelocity().y - mtvY;
        }

        body.getPosition().x += x;
        body.getPosition().y += y;
    }

    private void keepInScreen() {
	    float x = body.getPosition().x;
	    float y = body.getPosition().y;
	    float width = body.getWidth();
	    float height = body.getHeight();

	    if (x < 0) {
	        body.getPosition().x = 0;
        } else if ((x + width) > APP_WIDTH) {
	        body.getPosition().x = APP_WIDTH - width;
        }

        if (y < 0) {
	        body.getPosition().y = 0;
        } else if ((y + height) > APP_HEIGHT) {
	        body.getPosition().y = APP_HEIGHT - height;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            body.getVelocity().x = -MOVE_SPEED;
        }
        if (keycode == Input.Keys.RIGHT) {
            body.getVelocity().x = MOVE_SPEED;
        }
        if (keycode == Input.Keys.UP) {
            body.getVelocity().y = MOVE_SPEED;
        }
        if (keycode == Input.Keys.DOWN) {
            body.getVelocity().y = -MOVE_SPEED;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
	    body.getVelocity().x = 0;
	    body.getVelocity().y = 0;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}
}
