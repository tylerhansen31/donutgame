package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class DemoDonut extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Sprite sprite;
	Texture img;
	World world;
	Body body;
	Body bodyEdgeScreen;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	OrthographicCamera camera;
	BitmapFont font;
	int jump_counter = 0;
	boolean movingRight = false;
	boolean movingLeft = false;

	float torque = 0.0f;
	boolean drawSprite = true;

	final float PIXELS_TO_METERS = 100f;

	@Override
	public void create() {

		batch = new SpriteBatch();
		img = new Texture("donut.png");
		sprite = new Sprite(img);

		sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2);

		world = new World(new Vector2(0, -15f),true);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
						PIXELS_TO_METERS,
				(sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);

		body = world.createBody(bodyDef);

		CircleShape shape = new CircleShape();
		shape.setRadius(sprite.getWidth()/ 2f / PIXELS_TO_METERS);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = 0f;

		body.createFixture(fixtureDef);
		shape.dispose();


		//bodyDef2 and edgeShape work together they are bottom border
		BodyDef bodyDef2 = new BodyDef();
		bodyDef2.type = BodyDef.BodyType.StaticBody;
		float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
		// Set the height to just 25 pixels above the bottom of the screen so we can see the edge in the
		// debug renderer will need the 25 changed to 50 and turned on
		float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS -1 /PIXELS_TO_METERS;
		//bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
		bodyDef2.position.set(0,0);
		FixtureDef fixtureDef2 = new FixtureDef();

		EdgeShape edgeShape = new EdgeShape();
		edgeShape.set(-w/2,-h/2,w/2,-h/2);
		fixtureDef2.shape = edgeShape;

		bodyEdgeScreen = world.createBody(bodyDef2);
		bodyEdgeScreen.createFixture(fixtureDef2);
		edgeShape.dispose();


		//bodyDef3 and edgeShape2 work together they are right border
		BodyDef bodyDef3 = new BodyDef();
		//1.5708 radians is exactly 90 degrees
		//angle is defined by radians
		bodyDef3.angle = 1.5708f;
		bodyDef3.type = BodyDef.BodyType.StaticBody;
		float w2 = Gdx.graphics.getWidth()/PIXELS_TO_METERS ;
		// Set the height to just 25 pixels above the bottom of the screen so we can see the edge in the
		// debug renderer will need the 25 changed to 50 and turned on
		// 192 is defining where the border is in location to the screen?
		float h2 = Gdx.graphics.getHeight()/PIXELS_TO_METERS +200 /PIXELS_TO_METERS;
		//bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
		bodyDef3.position.set(0,0);
		FixtureDef fixtureDef3 = new FixtureDef();

		EdgeShape edgeShape2 = new EdgeShape();
		edgeShape2.set(-w2/2,-h2/2,w2/2,-h2/2);
		fixtureDef3.shape = edgeShape2;

		bodyEdgeScreen = world.createBody(bodyDef3);
		bodyEdgeScreen.createFixture(fixtureDef3);
		edgeShape2.dispose();


		//bodyDef4 and edgeShape3 work together they are a platform
		BodyDef bodyDef4 = new BodyDef();
		bodyDef4.type = BodyDef.BodyType.StaticBody;
		//platform width make closer to 0 for bigger platform
		float w3 = Gdx.graphics.getWidth()/PIXELS_TO_METERS - 750/PIXELS_TO_METERS ;
		//platform height higher the number the lower on screen, height -1400 is top of screen
		float h3 = Gdx.graphics.getHeight()/PIXELS_TO_METERS -250 /PIXELS_TO_METERS;
		//bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
		bodyDef4.position.set(0,0);
		FixtureDef fixtureDef4 = new FixtureDef();

		EdgeShape edgeShape3 = new EdgeShape();
		edgeShape3.set(-w3/2,-h3/2,w3/2,-h3/2);
		fixtureDef4.shape = edgeShape3;

		bodyEdgeScreen = world.createBody(bodyDef4);
		bodyEdgeScreen.createFixture(fixtureDef4);
		edgeShape3.dispose();

		//bodyDef5 and edgeShape4 work together they are left border
		BodyDef bodyDef5 = new BodyDef();
		//1.5708 radians is exactly 90 degrees
		//angle is defined by radians
		bodyDef5.angle = 1.5708f;
		bodyDef5.type = BodyDef.BodyType.StaticBody;
		float w4 = Gdx.graphics.getWidth()/PIXELS_TO_METERS ;
		// Set the height to just 25 pixels above the bottom of the screen so we can see the edge in the
		// debug renderer will need the 25 changed to 50 and turned on
		// 1600 is defining where the border is in location to the screen?
		float h4 = Gdx.graphics.getHeight()/PIXELS_TO_METERS -1600 /PIXELS_TO_METERS;
		//bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
		bodyDef5.position.set(0,0);
		FixtureDef fixtureDef5 = new FixtureDef();

		EdgeShape edgeShape4 = new EdgeShape();
		edgeShape4.set(-w4/2,-h4/2,w4/2,-h4/2);
		fixtureDef5.shape = edgeShape4;

		bodyEdgeScreen = world.createBody(bodyDef5);
		bodyEdgeScreen.createFixture(fixtureDef5);
		edgeShape4.dispose();


		Gdx.input.setInputProcessor(this);

		debugRenderer = new Box2DDebugRenderer();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
				getHeight());
	}

	private float elapsed = 0;
	@Override
	public void render() {

		camera.update();
		// Step the physics simulation forward at a rate of 60hz
		world.step(1f/60f, 6, 2);

		body.applyTorque(torque,true);

		sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.
						getWidth()/2 ,
				(body.getPosition().y * PIXELS_TO_METERS) -sprite.getHeight()/2 )
		;
		sprite.setRotation((float)Math.toDegrees(body.getAngle()));

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
				PIXELS_TO_METERS, 0);
		batch.begin();

		if(drawSprite)
			batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
					sprite.getOriginY(),
					sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
							getScaleY(),sprite.getRotation());

		font.draw(batch,
				" Donut Demo: X_VELOCITY: " + body.getLinearVelocity().x + " Y_VELOCITY: " + body.getLinearVelocity().y,
				-Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		batch.end();

		//set jump_counter to 0 if at rest vertically
		if(body.getLinearVelocity().y == 0){
			jump_counter = 0;
		}
		if(movingRight)
			body.setLinearVelocity(2f, body.getLinearVelocity().y);

		if(movingLeft)
			body.setLinearVelocity(-2f, body.getLinearVelocity().y);

		debugRenderer.render(world, debugMatrix);
	}

	@Override
	public void dispose() {
		img.dispose();
		world.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.UP) {
			if(jump_counter == 2){

			}
			else if(jump_counter == 1){
				if(body.getLinearVelocity().y < 0){
					body.applyForceToCenter(0f, 20f, true);
					jump_counter++;
				}
				else {
					body.applyForceToCenter(0f, 10f, true);
					jump_counter++;
				}
			}
			else {
				body.applyForceToCenter(0f, 15f, true);
				jump_counter++;
			}
		}

		if(keycode == Input.Keys.DOWN) {

			body.setLinearVelocity(0f, body.getLinearVelocity().y);

		}

		/*
		if(keycode == Input.Keys.RIGHT)
			if(body.getLinearVelocity().x < 0) {
				body.setLinearVelocity((body.getLinearVelocity().x-body.getLinearVelocity().x), body.getLinearVelocity().y);
				body.setLinearVelocity(2f, body.getLinearVelocity().y);
			}
			else
				body.setLinearVelocity(1.5f, body.getLinearVelocity().y);
				*/
		if(keycode == Input.Keys.RIGHT)
			movingRight = true;
		if(keycode == Input.Keys.LEFT)
			movingLeft = true;
		/*
		if(keycode == Input.Keys.LEFT)-
			if(body.getLinearVelocity().x > 0) {
				body.setLinearVelocity((body.getLinearVelocity().x-body.getLinearVelocity().x), body.getLinearVelocity().y);
				body.setLinearVelocity(-2f, body.getLinearVelocity().y);
			}
			else
				body.setLinearVelocity(-1.5f, body.getLinearVelocity().y);
		*/
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {

		if(keycode == Input.Keys.RIGHT)
			movingRight = false;
		if(keycode == Input.Keys.LEFT)
			movingLeft = false;
		//if(keycode == Input.Keys.DOWN)
		//	body.applyForceToCenter(0f, -10f, true);

		// On brackets ( [ ] ) apply torque, either clock or counterclockwise
		if(keycode == Input.Keys.RIGHT_BRACKET)
			torque += 0.1f;
		if(keycode == Input.Keys.LEFT_BRACKET)
			torque -= 0.1f;

		// Remove the torque using backslash /
		if(keycode == Input.Keys.BACKSLASH)
			torque = 0.0f;

		// If user hits spacebar, reset everything back to normal
		if(keycode == Input.Keys.SPACE|| keycode == Input.Keys.NUM_2) {
			body.setLinearVelocity(0f, 0f);
			body.setAngularVelocity(0f);
			torque = 0f;
			sprite.setPosition(0f,0f);
			body.setTransform(0f,0f,0f);
		}

		if(keycode == Input.Keys.COMMA) {
			body.getFixtureList().first().setRestitution(body.getFixtureList().first().getRestitution()-0.1f);
		}
		if(keycode == Input.Keys.PERIOD) {
			body.getFixtureList().first().setRestitution(body.getFixtureList().first().getRestitution()+0.1f);
		}
		if(keycode == Input.Keys.ESCAPE || keycode == Input.Keys.NUM_1)
			drawSprite = !drawSprite;

		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}


	// On touch we apply force from the direction of the users touch.
	// This could result in the object "spinning"
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		body.applyForce(1f,1f,screenX,screenY,true);
		//body.applyTorque(0.4f,true);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
