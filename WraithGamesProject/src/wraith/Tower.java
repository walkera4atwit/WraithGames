package wraith;

public class Tower {
	public static int atkSpeed;
	public static int level;
	public static int range;
	public static int damage;
	public int aoe;
	public String type;

	public static void attack(Enemy e) {
		e.hp = e.hp - damage;
	}

	//TODO: better upgrading system
	public static void upgrade() {
		level = level + 1; //set the level to add one
		atkSpeed = atkSpeed * 2; //attack speed should be set to run every X seconds and the upgrade will be 3/4 X
		range = range * 2; //have the range be increased by 50% (this.range *= 1.5)
		damage = damage * 2; //I would say damage does not need to be increased because the attack speed will increase DPS

	}

//do we want this to be tied to the player
//to allow for the "Sale" of the tower
	public static void destroy() {

	}

	public int getAttackSpeed() {
		return atkSpeed;
	}

	public int getLevel() {
		return level;
	}

	public int getRange() {
		return range;
	}

	public int getDamage() {
		return damage;
	}

	public int getAOE() {
		return aoe;
	}

	public String getType() {
		return type;
	}

}
