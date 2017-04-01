package com.brinkman.satdemo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Austin Brinkman.
 */
public class HUD
{
    private final Stage stage;
    private MinimumTranslationVector mtv;
    private Label label;

    public HUD(MinimumTranslationVector mtv) {
        this.mtv = mtv;
        stage = new Stage();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.CYAN;

        label = new Label("MTV.x: " + mtv.normal.x * mtv.depth + "        "
              + "MTV.y: " + mtv.normal.y * mtv.depth, labelStyle);

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.add(label);

        stage.addActor(table);
    }

    public void setMtv(MinimumTranslationVector mtv) { this.mtv = mtv; }

    public void render(float delta) {
        label.setText("MTV.x: " + (mtv.normal.x * mtv.depth) + "        "
              + "MTV.y: " + (mtv.normal.y * mtv.depth) + "        "
        + "MTV.normal: " + mtv.normal);
        stage.draw();
        stage.act(delta);
    }
}
