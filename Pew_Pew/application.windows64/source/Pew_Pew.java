import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Pew_Pew extends PApplet {

/**********************************
 * MADE BY: IAN MARTIN
 * PEW_PEW
 **********************************/

//These are for movement and shooting
boolean move_up, move_down, move_left, move_right;
boolean shoot_up, shoot_down, shoot_left, shoot_right;

//This is so the "camera" follows the player
float cameraX, cameraY;

//This is the level
PImage current_level;

//Object declarations
Player player;
ArrayList<Bullet> bullets = new ArrayList<Bullet>();
ArrayList<Wall> walls = new ArrayList<Wall>();

public void setup()
{
  
  //fullScreen();
  rectMode(CENTER);
  cameraX = width/2;
  cameraY = height/2;
  current_level = loadImage("Levels/Level_01.png");
  process_level(current_level);
}

public void draw()
{
  background(100);

  player.move();
  player.shoot();
  player.update();

  for (int i = bullets.size() - 1; i >= 0; i--)
  {
    Bullet B = bullets.get(i);
    B.update();
    if (out_of_bounds(B.position))
    {
      bullets.remove(B);
    }
  }

  for (Wall W : walls)
  {
    W.update();
  }

  //This is how the camera follows the player
  cameraX = lerp(cameraX, player.position.x, 0.075f);
  cameraY = lerp(cameraY, player.position.y, 0.075f);
  translate(width/2 - cameraX, height/2 - cameraY);

  for (Bullet B : bullets)
  {
    B.display();
  }

  for (Wall W : walls)
  {
    W.display();
  }

  player.display();
}

//Returns true if the given PVector is out of the arena
public boolean out_of_bounds(PVector location)
{
  if (location.x < -200 || location.y < -200 || location.x > width + 200 || location.y > height + 200)
  {
    return true;
  } else
  {
    return false;
  }
}

//Sets the key to true
public void keyPressed()
{
  set_move(keyCode, true);
}

//sets the key to false
public void keyReleased()
{
  set_move(keyCode, false);
}

//sets the key to the given boolean
public boolean set_move(int key, boolean set)
{
  switch (key)
  {
  case 'W':
  case 'w':
    return move_up = set;
  case 'A':
  case 'a':
    return move_left = set;
  case 'D':
  case 'd':
    return move_right = set;
  case 'S':
  case 's':
    return move_down = set;

  case UP:
    return shoot_up = set;
  case LEFT:
    return shoot_left = set;
  case RIGHT:
    return shoot_right = set;
  case DOWN:
    return shoot_down = set;
  default:
    return set;
  }
}
/**************************
 * WRITTEN BY: Ian Martin
 * Bullet class
 *************************/

//This is a child of Entity
class Bullet extends Entity
{

  Bullet()
  {
    super();
    position = new PVector(0, 0);
    velocity = new PVector(1, 1);
    acceleration = new PVector(0, 0);
    entity_color = color(50);
    shape = "Circle";
    max_speed = 5;
  }

  Bullet(float locationX, float locationY, PVector heading)
  {
    super();
    position = new PVector(locationX, locationY);
    velocity = heading;
    acceleration = new PVector(0, 0);
    entity_color = color(150, 0, 0);
    shape = "Circle";
    max_speed = 7;
    axis = new PVector(15, 15);
  }
}
/**************************
 * WRITTEN BY: Ian Martin
 * Entity class
 *************************/

//This is the foundation for all classes
class Entity
{
  //PVectors for physics
  PVector position;
  PVector velocity;
  PVector acceleration;
  PVector axis;

  //These differ for each class
  float slowDown;
  float max_speed;

  //"Circle" or "Rectangle"
  String shape;

  //This hols the color of the entity
  int entity_color;

  Entity()
  {
    position = new PVector(0, 0);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
    axis = new PVector(10, 10);
    slowDown = 1.0f;
    max_speed = 5;
    shape = "Circle";
    entity_color = color(random(255), random(255), random(255));
  }

  public void update()
  {
    //Physics engine
    velocity.add(acceleration);
    velocity.limit(max_speed);

    position.add(velocity);

    velocity.mult(slowDown);
    acceleration.setMag(0);
  }

  public void apply_force(PVector force)
  {
    //Physics engine
    acceleration.add(force);
  }

  public void display()
  {
    fill(entity_color);
    noStroke();
    if (shape == "Circle")
    {
      ellipse(position.x, position.y, axis.x + 1, axis.y + 1);
    } else if (shape == "Rectangle")
    {
      rect(position.x, position.y, axis.x + 1, axis.y + 1);
    }
  }
}
public void process_level(PImage level)
{
  level.loadPixels();
  for (int w = 0; w < level.width; w++)
  {
    for (int h = 0; h < level.height; h++)
    {
      if(level.get(w, h) == color(0))
      {
        walls.add(new Wall(w * 50, h * 50));
      } else if(level.get(w, h) == color(0, 0, 255))
      {
        player = new Player(w * 50, h * 50);
      }
    }
  }
}
/**************************
 * WRITTEN BY: Ian Martin
 * Player class
 *************************/

//This is a child of Entity
class Player extends Entity
{

  //This is for the player shoot mechanic
  int cool_down;
  int fire_delay;

  //This is how fast the player accelerates
  float speed;

  Player()
  {
    super();
    position = new PVector(width/2, height/2);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
    axis = new PVector(50, 50);
    slowDown = 0.95f;
    entity_color = color(150, 20, 150);
    shape = "Rectangle";
    fire_delay = 20;
    cool_down = 0;
    max_speed = 3;
    speed = 1;
  }

  Player(float posx, float posy)
  {
    super(); 
    position = new PVector(posx, posy);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
    axis = new PVector(50, 50);
    slowDown = 0.95f;
    entity_color = color(150, 20, 150);
    shape = "Rectangle";
    fire_delay = 20;
    cool_down = 0;
    max_speed = 3;
    speed = 1;
  }

  public void move()
  {
    //Movement

    PVector movement = new PVector(0, 0);
    if (move_up)
    {
      movement.y -= speed;
    }
    if (move_down)
    {
      movement.y += speed;
    }
    if (move_left)
    {
      movement.x -= speed;
    }
    if (move_right)
    {
      movement.x += speed;
    }
    apply_force(movement);
  }

  public void shoot()
  {
    //Shooting mechanic

    cool_down--;
    if (cool_down <= 0 && (shoot_up || shoot_down || shoot_left || shoot_right))
    {
      cool_down = fire_delay;
      PVector bullet_velocity = new PVector(0, 0);
      if (shoot_up)
      {
        bullet_velocity.y -= 5;
      } else if (shoot_down)
      {
        bullet_velocity.y += 5;
      }
      if (shoot_left)
      {
        bullet_velocity.x -= 5;
      } else if (shoot_right)
      {
        bullet_velocity.x += 5;
      }
      bullets.add(new Bullet(position.x, position.y, bullet_velocity));
    }
  }
}
/**************************
 * WRITTEN BY: Ian Martin
 * Wall class
 *************************/

//This is a child of Entity
class Wall extends Entity
{
  Wall(float posx, float posy)
  {
    super();
    position = new PVector(posx, posy);
    axis = new PVector(50, 50);
    entity_color = color(20);
    max_speed = 0;
    shape = "Rectangle";
  }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Pew_Pew" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
