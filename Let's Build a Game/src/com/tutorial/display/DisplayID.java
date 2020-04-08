 package com.tutorial.display;

 // TODO Comments by Manjot
/**
 * Enumerations to be used in display class which represent different objects that will displayed in the game
 */
public enum DisplayID {
	/**
	 * Player that the user controls
	 */
	Player(),
	
	/**
	 * When the player takes damage, they enter an invincible state for on tick
	 */
	PlayerInvincible(),
	
	/**
	 * the enemy AI who you have to beat
	 */
	Enemy(),
	
	/**
	 * When the enemy takes damage, they enter an invincible state for on tick
	 */
	EnemyInvincible(),
	
	/**
	 * Any allies for the user
	 */
	Ally(),
	
	/**
	 * When an ally takes damage, they enter an invincible state for on tick
	 */
	AllyInvincible(),
	
	/**
	 * Basic obstacles on the map that damage the player, enemy, and allies
	 */
	Obstacle(),
	
	/**
	 * Traps that come into play when a character hits specific obstacles
	 */
	Trap(),
	
	/**
	 * Other different traps that can be set off by any character
	 */
	DasherTrap(),
	Bullet(),
	BulletUntouchable(),
	PrisonBullet(),
	PrisonBulletUntouchable(),
	
	/**
	 * A ball trap that just has a ball bouncing around on the map for a short period of time
	 */
	Ball();
}
