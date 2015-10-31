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
    private Array<SpaceShip> spaceShips;
    private Array<Yoda> yodas;

    public B2dWorld(PlayScreen screen) {

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //create body and fixture variables

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / StarWars.PPM, (rect.getY() + rect.getHeight() / 2) / StarWars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / StarWars.PPM, rect.getHeight() / 2 / StarWars.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create plataform bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / StarWars.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / StarWars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / StarWars.PPM, rectangle.getHeight() / 2 / StarWars.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = StarWars.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create energy bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new EnergyGlobe(screen, rectangle);
        }

        //create all jawas;
        jawas = new Array<Jawa>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            jawas.add(new Jawa(screen, rect.getX() / StarWars.PPM, rect.getY() / StarWars.PPM));
            fdef.filter.categoryBits = StarWars.ENEMY_BIT;
        }

        //create all tuskens;
        tuskens = new Array<Tusken>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            tuskens.add(new Tusken(screen, rect.getX() / StarWars.PPM, rect.getY() / StarWars.PPM));
            fdef.filter.categoryBits = StarWars.ENEMY_BIT;
        }

        //create SpaceShips;
        spaceShips = new Array<SpaceShip>();
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new SpaceShip(screen, rect);
        }

        //create yodas;
        yodas = new Array<Yoda>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Yoda(screen, rect);
        }
    }

    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(tuskens);
        enemies.addAll(jawas);
        return enemies;
    }
}
