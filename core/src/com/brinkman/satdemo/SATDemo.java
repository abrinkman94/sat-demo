package com.brinkman.satdemo;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;

public class SATDemo extends Game implements InputProcessor, Screen
{
	private Body body;
	private Body otherBody;
	private Body otherBody2;
	private HUD hud;
	private OrthographicCamera camera = new OrthographicCamera();

    private final float WORLD_UNITS = 1/64f;

    private final float moveSpeed = 5f * WORLD_UNITS;

	@Override
	public void create () {
		body = new Body(100 * WORLD_UNITS, 100 * WORLD_UNITS, 128 * WORLD_UNITS, 128 * WORLD_UNITS);
		otherBody = new Body(300 * WORLD_UNITS, 0, 400 * WORLD_UNITS, 400 * WORLD_UNITS, 3);
		otherBody2 = new Body(0, 0, 1080 * WORLD_UNITS, 100 * WORLD_UNITS);

		hud = new HUD(new MinimumTranslationVector());

		camera = new OrthographicCamera(1080 * WORLD_UNITS, 720 * WORLD_UNITS);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

		Gdx.input.setInputProcessor(this);
	}



	@Override
	public void render () {
		this.render(Gdx.graphics.getDeltaTime());
	}

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            body.getVelocity().x = -moveSpeed;
        }
        if (keycode == Input.Keys.RIGHT) {
            body.getVelocity().x = moveSpeed;
        }
        if (keycode == Input.Keys.UP) {
            body.getVelocity().y = moveSpeed;
        }
        if (keycode == Input.Keys.DOWN) {
            body.getVelocity().y = -moveSpeed;
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
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hud.render(Gdx.graphics.getDeltaTime());

        body.getPosition().add(body.getVelocity());

        MinimumTranslationVector mtv = new MinimumTranslationVector();
        float x = 0;
        float y = 0;

        if (BodyCollision.intersects(body, otherBody, mtv)) {
            System.out.println("COLLISION");
            hud.setMtv(mtv);

            float mtvX = mtv.normal.x * mtv.depth;
            float mtvY = mtv.normal.y * mtv.depth;

            x = body.getVelocity().x - mtvX;
            y = body.getVelocity().y - mtvY;
        }

        body.getPosition().x += x;
        body.getPosition().y += y;

        MinimumTranslationVector mtv2 = new MinimumTranslationVector();

        double x2 = 0;
        double y2 = 0;

        if (BodyCollision.intersects(body, otherBody2, mtv2)) {
            hud.setMtv(mtv2);

            double mtvX = mtv2.normal.x * mtv2.depth;
            double mtvY = mtv2.normal.y * mtv2.depth;

            x2 = body.getVelocity().x - mtvX;
            y2 = body.getVelocity().y - mtvY;
        }

        body.getPosition().x += x2;
        body.getPosition().y += y2;

        ShapeRenderer renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.rect(body.getPosition().x, body.getPosition().y, body.getWidth(), body.getHeight());
        renderer.end();

        ShapeRenderer renderer3 = new ShapeRenderer();
        renderer3.setProjectionMatrix(camera.combined);
        renderer3.begin(ShapeRenderer.ShapeType.Line);
        renderer3.setColor(Color.GREEN);
        renderer3.polygon(otherBody.getFloatVertices());
        renderer3.end();

        ShapeRenderer renderer1 = new ShapeRenderer();
        renderer1.begin(ShapeRenderer.ShapeType.Line);
        renderer1.setProjectionMatrix(camera.combined);
        renderer1.setColor(Color.GREEN);
        renderer1.rect(otherBody2.getPosition().x, otherBody2.getPosition().y, otherBody2.getWidth(), otherBody2.getHeight());
        renderer1.end();

        camera.update();

    }

    @Override
    public void hide() {

    }

    private void handleMovement() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			body.getVelocity().x = -5;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			body.getVelocity().x = 5;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			body.getVelocity().y = 5;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			body.getVelocity().y = -5;
		}
	}
}
