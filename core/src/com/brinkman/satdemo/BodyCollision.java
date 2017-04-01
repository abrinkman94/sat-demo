package com.brinkman.satdemo;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Austin Brinkman.
 */
public class BodyCollision
{
    private static List<Vector2> getAxes(Body body) {
        List<Vector2> axes = new ArrayList<Vector2>();

        // loop over the vertices
        for (int i = 0; i < body.getVertices().size(); i++) {
            // get the current vertex
            Vector2 p1 = body.getVertices().get(i);
            // get the next vertex
            Vector2 p2 = body.getVertices().get(((i + 1) == body.getVertices().size()) ? 0 : (i + 1));
            // subtract the two to get the edge vector
            Vector2 edge = p1.sub(p2);
            // get either perpendicular vector
            Vector2 normal = perp(edge);
            // the perp method is just (x, y) => (-y, x) or (y, -x)
            normal.nor();

            axes.add(normal);
        }

        return axes;
    }

    private static Projection project(Body body, Vector2 axis) {
        float min = body.getVertices().get(0).dot(axis);
        float max = min;
        for (int i = 1; i < body.getVertices().size(); i++) {
            // NOTE: the axis must be normalized to get accurate projections
            float p = body.getVertices().get(i).dot(axis);
            min = Math.min(p, min);
            max = Math.max(p, max);
        }
        return new Projection(min, max);
    }

    private static Vector2 perp(Vector2 vector) {
        return new Vector2(-vector.y, vector.x);
    }

    public static boolean intersects(Body body, Body other, MinimumTranslationVector mtv) {
        List<Vector2> axes = getAxes(body);
        List<Vector2> otherAxes = getAxes(other);

        float magnitude = 1000000;

        for (Vector2 axis : axes) {
            Projection projection = project(body, axis);
            Projection otherProjection = project(other, axis);

            if (!projection.overlaps(otherProjection)) {
                return false;
            }

            float velocityProjection = axis.dot(body.getVelocity());

            if (velocityProjection < 0) {
                projection.min += velocityProjection;
            } else {
                projection.max += velocityProjection;
            }

            float overlap = Math.abs(projection.getOverlap(otherProjection));

            if (overlap < magnitude) {
                magnitude = overlap;
                mtv.normal = axis;

                Vector2 vd = new Vector2(body.getPosition().x - other.getPosition().x,
                      body.getPosition().y - other.getPosition().y);

                if (vd.dot(axis) > 0) {
                    mtv.normal = axis.scl(-1);
                }
            }
        }

        for (Vector2 axis : otherAxes) {
            Projection projection = project(body, axis);
            Projection otherProjection = project(other, axis);

            if (!projection.overlaps(otherProjection)) {
                return false;
            }

            float velocityProjection = axis.dot(body.getVelocity());

            if (velocityProjection < 0) {
                projection.min += velocityProjection;
            } else {
                projection.max += velocityProjection;
            }

            float overlap = Math.abs(projection.getOverlap(otherProjection));

            if (overlap < magnitude) {
                magnitude = overlap;
                mtv.normal = axis;

                Vector2 vd = new Vector2(body.getPosition().x - other.getPosition().x,
                      body.getPosition().y - other.getPosition().y);

                if (vd.dot(axis) > 0) {
                    mtv.normal = axis.scl(-1);
                }
            }
        }

        mtv.depth = magnitude;

        return mtv.depth != 0;
    }
}
