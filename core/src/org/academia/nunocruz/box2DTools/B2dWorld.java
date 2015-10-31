package org.academia.nunocruz.box2DTools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Enemies.Enemy;
import org.academia.nunocruz.gameObjects.Enemies.Tusken;
import org.academia.nunocruz.gameObjects.TileObjects.Brick;
import org.academia.nunocruz.gameObjects.TileObjects.EnergyGlobe;
import org.academia.nunocruz.screens.PlayScreen;

public class B2dWorld {

    private Array<Tusken> tuskens;

    public B2dWorld(PlayScreen screen){

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //create body and fixture variables

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / StarWars.PPM, (rect.getY() + rect.getHeight() / 2) / StarWars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / StarWars.PPM, rect.getHeight() / 2 / StarWars.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create plataform bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(PolygonMapObject.class)){
            Polygon polygon = ((PolygonMapObject) object).getPolygon();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((polygon.getX() + polygon.getBoundingRectangle().getWidth() / 2) / StarWars.PPM, (polygon.getY() + polygon.getBoundingRectangle().getHeight() / 2) / StarWars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(polygon.getBoundingRectangle().getWidth() / 2 / StarWars.PPM, polygon.getBoundingRectangle().getHeight() / 2 / StarWars.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = StarWars.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create jawas bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            new Brick(screen, object);
        }

        //create energy bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            new EnergyGlobe(screen, object);
        }

        //create all tuskens;
        tuskens = new Array<Tusken>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            tuskens.add(new Tusken(screen, rect.getX() / StarWars.PPM, rect.getY() / StarWars.PPM));
        }
    }

    public Array<Tusken> getTuskens() {
        return tuskens;
    }

    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(tuskens);
        return enemies;
    }
}
