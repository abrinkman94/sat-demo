package com.brinkman.satdemo;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Austin Brinkman.
 */
public class Body
{
    private final Vector2 position = new Vector2();
    private final Vector2 velocity = new Vector2();
    private float[] vertices;
    private float countVert;
    private float width = 0;
    private float height = 0;

    public Body(float x, float y, float width, float height) {
        position.set(x, y);
        this.width = width;
        this.height = height;
    }

    public Body(float x, float y, float width, float height, float countVert) {
        position.set(x, y);
        this.width = width;
        this.height = height;
        this.countVert = countVert;
        vertices = new float[(int) countVert * 2];
    }

    public Vector2 getPosition() { return position; }

    public Vector2 getVelocity() { return velocity; }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public List<Vector2> getVertices() {
        if (vertices != null) {
            List<Vector2> temp = new ArrayList<Vector2>();

            vertices = new float[(int) countVert * 2];

            vertices[0] = position.x;
            vertices[1] = position.y;
            vertices[2] = position.x + width;
            vertices[3] = position.y;
            vertices[4] = position.x + width;
            vertices[5] = position.y + height;

            for (int i = 0; i < (vertices.length - 1); i+=2) {
                temp.add(new Vector2(vertices[i], vertices[i + 1]));
            }

            return temp;
        }

        List<Vector2> temp = new ArrayList<Vector2>();
        temp.add(new Vector2(position.x, position.y));
        temp.add(new Vector2(position.x + width, position.y));
        temp.add(new Vector2(position.x + width, position.y + height));
        temp.add(new Vector2(position.x, position.y + height));

        return temp;
    }

    public float[] getFloatVertices() {
        return vertices;
    }
}
