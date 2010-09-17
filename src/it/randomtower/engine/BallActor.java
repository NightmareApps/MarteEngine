package it.randomtower.engine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class BallActor extends Entity {

    public static final String NAME = "ball";

    public static final String STAND_DOWN = "stand_down";
    public static final String STAND_UP = "stand_up";

    public Vector2f mySpeed = new Vector2f(5, 5);

    public boolean attacking = false;

    public BallActor(float x, float y, String ref) {
	super(x, y);

	// set id
	this.name = NAME;
	
	// load spriteSheet
	if (ref != null)
	    setupGraphic(ref);

	// player rendered above everything
	zLevel = ME.Z_LEVEL_TOP;

	// define collision box and type
	setHitBox(0, 0, currentImage.getWidth(), currentImage.getHeight());
	addType(NAME,ME.SOLID);
    }

    public void setupGraphic(String ref) {
	try {
	    setGraphic(new Image(ref));
	} catch (SlickException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void update(GameContainer container, int delta)
	    throws SlickException {
	super.update(container, delta);

	// movements
	updateMovements();
	
	// check ball ends
	if (x < 0 ){
	    int score = (Integer) ME.attributes.get("score2");
	    ME.attributes.put("score2",++score);
	    ME.remove(this);
	} else if (x > ME.container.getWidth()){
	    int score = (Integer) ME.attributes.get("score1");
	    ME.attributes.put("score1",++score);
	    ME.remove(this);
	}
    }

    private void updateMovements() {
	if (!collide(ME.SOLID, x+mySpeed.x, y)){
	    x+=mySpeed.x;
	}
	if (!collide(ME.SOLID, x, y+mySpeed.y)){
	    y+=mySpeed.y;
	}
    }

    @Override
    public void render(GameContainer container, Graphics g)
	    throws SlickException {
	super.render(container, g);
    }

    @Override
    public void collisionResponse(Entity entity) {
	if (entity.name.equalsIgnoreCase("player2")||entity.name.equalsIgnoreCase("player1")){
	    mySpeed.x = -1 *mySpeed.x;
	}
	if (entity.name.equalsIgnoreCase(StaticActor.NAME)){
	    mySpeed.y = -1 *mySpeed.y;
	}	
    }
    
}
