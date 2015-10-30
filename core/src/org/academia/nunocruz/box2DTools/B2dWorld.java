package org.academia.nunocruz.box2DTools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Enemies.Jawa;
import org.academia.nunocruz.gameObjects.TileObjects.Brick;
import org.academia.nunocruz.gameObjects.TileObjects.EnergyGlobe;
import org.academia.nunocruz.gameObjects.Enemies.Enemy;
import org.academia.nunocruz.gameObjects.Enemies.Tusken;
import org.academia.nunocruz.screens.PlayScreen;

public class B2dWorld {

    private Array<Tusken> tusken;
    private Array<Jawa> jawa;

    public B2dWorld(PlayScreen screen){

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //create body and fixture variables

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / StarWars.PPM, (rect.getY() + rect.getHeight() / 2) / StarWars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / StarWars.PPM, rect.getHeight() / 2 / StarWars.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / StarWars.PPM, (rect.getY() + rect.getHeight() / 2) / StarWars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / StarWars.PPM, rect.getHeight() / 2 / StarWars.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = StarWars.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create energy bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Brick(screen, object);
        }

        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){

            new EnergyGlobe(screen, object);
        }
/**
        //create all Tusken enemies;
        tusken = new Array<Tusken>();

        // layer 7 of the map tiled
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            tusken.add(new Tusken(screen, rect.getX() / StarWars.PPM, rect.getY() / StarWars.PPM));
        }

        //create all Tusken enemies;
        jawa = new Array<Jawa>();

        // layer 6 of the map tiled
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            jawa.add(new Jawa(screen, rect.getX() / StarWars.PPM, rect.getY() / StarWars.PPM));
        }
 */
    }

    public Array<Tusken> getTusken() {
        return tusken;
    }

    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
/**     enemies.addAll(tusken);
        enemies.addAll(jawa);  */
        return enemies;
    }
}
