package com.brinkman.satdemo;

/**
 * @author Austin Brinkman.
 */
public class Projection
{
    public float min;
    public float max;
    private float overlap;

    public Projection() {}

    public Projection(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public boolean overlaps(Projection projection) {
        if(!((this.min > projection.max) || (projection.min > this.max)))
        {
            overlap = Math.min(this.max, projection.max) - Math.max(this.min, projection.min);
            return true;
        }
        return false;
    }

    public float getOverlap(Projection projection) {
        return Math.min(this.max, projection.max) - Math.max(this.min, projection.min);
    }
}
