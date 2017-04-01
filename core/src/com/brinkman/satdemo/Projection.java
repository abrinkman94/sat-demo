package com.brinkman.satdemo;

/**
 * @author Austin Brinkman.
 */
public class Projection
{
    public float min;
    public float max;

    public Projection(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public boolean overlaps(Projection projection) {
        return !((this.min > projection.max) || (projection.min > this.max));
    }

    public float getOverlap(Projection projection) {
        return Math.min(this.max, projection.max) - Math.max(this.min, projection.min);
    }
}
