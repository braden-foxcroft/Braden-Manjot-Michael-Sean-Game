package com.tutorial.main;


// Creates a Bullet. This Bullet that can collide with stuff.
// Exclusively original code.

public class Bullet extends GameObject {
	
	protected int lifeSpan;
	
/**	creates a bullet of radius 5. The handler should be the original handler you use.
 * 
 * @param x = initial X coordinate on canvas
 * @param y = initial Y coordinate on canvas
 * @param id = identification flag for handler
 * @param handler = the list that this object will be kept in for rendering and ticks
 */
	public Bullet(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.radius = 5;
		this.lifeSpan = (60) * 5; // 5 second lifespan, invisible for the first second
		this.canTouch = false;
	}
	
/**	A routine that acts once a tick.
 * All of the calls that must be called per tick of the game.
 */
	public void tick() {
		displace();
		this.constrain();
		this.lifeSpan--;
		if (this.lifeSpan < (60) * 4.5)
		{
			this.canTouch = true;
		}
		if (check_Death()) {
			Handler.time_To_Die();
		}
	}
	
/**	Overwrite the default behavior
 * checks for death of the object
 */
	public boolean check_Death() {
		return this.lifeSpan < 0;
	}
	
	
/**	display the object
 * passes the instance variable references to the display class
 */
	public void render(Display d) {
		if (this.canTouch) {
			d.displayObject(DisplayID.Bullet, x, y, radius);
		} else {
			d.displayObject(DisplayID.BulletUntouchable, x, y, radius);
		}
	}
	
/**	Gives the Ball a randomized direction and a constrained randomized velocity. 
 * 
 * @param dir = direction of the vector being passed in
 */
	public void launchAround(Vector dir) {
		this.setVelocity(dir);
	}
	
/**	move the object according to its velocity
 * 
 */
	protected void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}
	
/**	slow the object proportional to its velocity
 * 
 */
	protected void drag() {
		if (!this.anchored) {
			float drag = 0.99f;
			this.setVelX(this.getVelX() * drag);
			this.setVelY(this.getVelY() * drag);
		}
	}
/**	adds the objects to the appropriate lists in the handler
 * 
 */
	public void addTo(Handler h) {
		h.object.add(this);
		h.movingStuff.add(this);
	}
/**	removes objects from the necessary lists in the handler	
 * 
 */
	public void removeFrom(Handler h) {
		h.object.remove(this);
		h.movingStuff.remove(this);
	}
	
	public void onCollision(GameObject other) {
		
	}
	
/**	does nothing, there because it's required.
 * 
 */
	public void hitWall() {
		// do nothing
	}
	
}
