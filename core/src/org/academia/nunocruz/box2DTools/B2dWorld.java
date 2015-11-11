package org.academia.nunocruz.box2DTools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Enemies.Enemy;
import org.academia.nunocruz.gameObjects.Enemies.Jawa;
import org.academia.nunocruz.gameObjects.Enemies.Tusken;
import org.academia.nunocruz.gameObjects.TileObjects.EnergyGlobe;
import org.academia.nunocruz.gameObjects.TileObjects.SpaceShip;
import org.academia.nunocruz.gameObjects.TileObjects.Yoda;
import org.academia.nunocruz.screens.PlayScreen;

public class B2dWorld {

    private Array<Tusken> tuskens;
    private Array<Jawa> jawas;

    public B2dWorld(PlayScreen screen) {

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // GROUND // LAYER #3
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            fdef.filter.categoryBits = StarWars.GROUND_BIT;

            bdef.position.set((r.getX() + r.getWidth() / 2) / StarWars.PPM, (r.getY() + r.getHeight() / 2) / StarWars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(r.getWidth() / 2 / StarWars.PPM, r.getHeight() / 2 / StarWars.PPM);

            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // PLATFORM // LAYER #4
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            fdef.filter.categoryBits = StarWars.PLATFORM_BIT;

            bdef.position.set((r.getX() + r.getWidth() / 2) / StarWars.PPM, (r.getY() + r.getHeight() / 2) / StarWars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(r.getWidth() / 2 / StarWars.PPM, r.getHeight() / 2 / StarWars.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // ENERGY // LAYER #5
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();

            fdef.filter.categoryBits = StarWars.ENERGYGLOBE_BIT;
            new EnergyGlobe(screen, r);
        }

        // JAWA // LAYER #6
        jawas = new Array<Jawa>();

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();
            jawas.add(new Jawa(screen, r.getX() / StarWars.PPM, r.getY() / StarWars.PPM));
            fdef.filter.categoryBits = StarWars.ENEMY_BIT;
        }

        // TUSKEN // LAYER #7
        tuskens = new Array<Tusken>();

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();
            tuskens.add(new Tusken(screen, r.getX() / StarWars.PPM, r.getY() / StarWars.PPM));
            fdef.filter.categoryBits = StarWars.ENEMY_BIT;
        }

        // SPACESHIP // LAYER #8
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();
            fdef.filter.categoryBits = StarWars.SPACESHIP_BIT;
            new SpaceShip(screen, r);
        }

        // YODA // LAYER #9
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();
            fdef.filter.categoryBits = StarWars.YODA_BIT;
            new Yoda(screen, r);
        }
    }

    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(tuskens);
        enemies.addAll(jawas);
        return enemies;
    }
}
