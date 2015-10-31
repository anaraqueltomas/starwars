package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

public abstract class TileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public TileObject(PlayScreen screen, Rectangle bounds){

        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / StarWars.PPM, (bounds.getY() + bounds.getHeight() / 2) / StarWars.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / StarWars.PPM, bounds.getHeight() / 2 / StarWars.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = StarWars.OBJECT_BIT;
        fixture = body.createFixture(fdef);

    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public abstract void onHit();

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
        return layer.getCell((int)(body.getPosition().x * StarWars.PPM / 16),
                (int)(body.getPosition().y * StarWars.PPM / 16));
    }

}
