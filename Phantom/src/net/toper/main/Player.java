package net.toper.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player {

	Image carImg;

	float carX;
	float carY;
	float carDeltaX = 50;
	float carDeltaY = 300;

	float rotAccel = 0.16f;
	float rotChangeL = 0.0f;
	float rotChangeR = 0.0f;
	float maxRotSpeed = 2.00f;
	float rot = 180;
	float oldRot;

	float accel = 10.75f;
	float moveSpeed = 0;
	float stopSpeed = 750f;
	float maxSpeed = 1000f;

	float scale = 0.5f;

	float delta;

	public Player() {
		try {
			carImg = new Image("res/car.png");
			carImg = carImg.getScaledCopy(scale);
			carImg.setCenterOfRotation(carImg.getWidth() * .5f, carImg.getHeight() * .3f);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void update(GameContainer gc, float delta2) {
		Input input = gc.getInput();
		this.delta = delta2;
		if (!(input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_S)
				|| input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_UP))) {
			decelerate();
		}
		if (input.isKeyDown(Input.KEY_SPACE)) {
			decelerate();
			decelerate();
			decelerate();

		} else {
			if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
				if (moveSpeed < maxSpeed) {
					moveSpeed += accel;
				}
			}
			if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
				if (moveSpeed > -maxSpeed) {
					moveSpeed -= accel;
				}
			}
		}
		if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
			rotChangeL += rotAccel * (Math.abs(moveSpeed) / 1.25f);
		} else {
			if (rotChangeL > 0.0f)
				rotChangeL -= rotAccel / 1.5f;
		}
		if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
			rotChangeR += rotAccel * (Math.abs(moveSpeed) / 1.25f);
		} else {
			if (rotChangeR > 0.0f)
				rotChangeR -= rotAccel / 1.5f;
		}
		if (rotChangeR > maxRotSpeed) {
			rotChangeR = maxRotSpeed;
		}
		if (rotChangeL > maxRotSpeed) {
			rotChangeL = maxRotSpeed;
		}
		float rotChange = rotChangeR - rotChangeL;
		if (rotChange > maxRotSpeed) {
			rotChange = maxRotSpeed;
		}
		if (rotChange < -maxRotSpeed) {
			rotChange = -maxRotSpeed;
		}
		rot -= rotChange;
		carDeltaY = (float) ((moveSpeed * Math.cos(Math.toRadians(rot))) - (rotChange / 5f)) * delta2;
		carDeltaX = (float) ((moveSpeed * Math.sin(Math.toRadians(rot))) - (rotChange / 5f)) * delta2;
	}

	private void decelerate() {
		if (moveSpeed > 0.0) {
			moveSpeed -= stopSpeed * delta;
		}
		if (moveSpeed < 0.0) {
			moveSpeed += stopSpeed * delta;
		}
	}

	public void render(Render r, GameContainer gc) {
		if (oldRot != rot) {
			carImg.setRotation(-rot);
			oldRot = rot;
		}
		r.drawImage(carImg, gc.getWidth() / 2 - carImg.getWidth() / 2, gc.getHeight() / 2 - carImg.getHeight() / 2, 1f);
	}

	public float getAccelX() {
		return -carDeltaX;
	}

	public float getAccelY() {
		return -carDeltaY;
	}

	public int getWidth() {
		return carImg.getWidth();
	}

	public int getHeight() {
		return carImg.getHeight();
	}
}
