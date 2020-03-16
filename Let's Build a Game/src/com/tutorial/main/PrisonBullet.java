package com.tutorial.main;

public class PrisonBullet extends Bullet {
	
	private int maxLife = (60) * 8;
	
	public PrisonBullet(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.lifeSpan = maxLife;
	}
	
//	A routine that acts once a tick.
	public void tick() {
//		this.drag();
		displace();
		this.constrain();
		this.lifeSpan--;
		if (this.lifeSpan < maxLife - 30)
		{
			this.canTouch = true;
			this.anchored = true;
			this.damaging = true;
			this.setVelX(0);
			this.setVelY(0);
		}
		if (check_Death()) {
			Handler.time_To_Die();
		}
	}
	
	public void render(Display d) {
		if (this.canTouch) {
			d.displayObject(DisplayID.PrisonBullet, x, y, radius);
		} else {
			d.displayObject(DisplayID.PrisonBulletUntouchable, x, y, radius);
		}
	}
}
