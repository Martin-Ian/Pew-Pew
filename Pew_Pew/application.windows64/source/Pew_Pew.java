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

boolean move_up, move_down, move_left, move_right;
boolean shoot_up, shoot_down, shoot_left, shoot_right;
float cameraX, cameraY;

Player player;

public void setup()
{
  //size(600, 600);
  
  cameraX = width/2;
  cameraY = height/2;
  player = new Player();
}

public void draw()
{
  background(100);

  player.move();
  player.update();
  
  cameraX = lerp(cameraX, player.position.x, 0.075f);
  cameraY = lerp(cameraY, player.position.y, 0.075f);
  translate(width/2 - cameraX, height/2 - cameraY);
  
  strokeWeight(5);
  stroke(0);
  noFill();
  rect(0, 0, width, height);
  
  player.display();
}

public void keyPressed()
{
  set_move(key, true);
}

public void keyReleased()
{
  set_move(key, false);
}

public void set_move(int key, boolean set)
{
  switch (key)
  {
  case 'W':
  case 'w':
    move_up = set;
    break;
  case 'A':
  case 'a':
    move_left = set;
    break;
  case 'D':
  case 'd':
    move_right = set;
    break;
  case 'S':
  case 's':
    move_down = set;
    break;
  case UP:
    shoot_up = set;
    break;
  case LEFT:
    shoot_left = set;
    break;
  case RIGHT:
    shoot_right = set;
    break;
  case DOWN:
    shoot_down = set;
    break;
  }
}

class Player
{
  PVector position;
  PVector velocity;
  PVector acceleration;

  float diameter = 100;
  float max_speed = 5;
  float slowDown = 0.95f;

  Player()
  {
    position = new PVector(width/2, height/2);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
  }

  public void update()
  {
    velocity.add(acceleration);
    velocity.limit(max_speed);
    position.add(velocity);
    velocity.mult(slowDown);
    acceleration.setMag(0);
  }

  public void apply_force(PVector force)
  {
    acceleration.add(force);
  }

  public void move()
  {
    PVector movement = new PVector(0, 0);
    if (move_up)
    {
      movement.y -= 0.5f;
    }
    if (move_down)
    {
      movement.y += 0.5f;
    }
    if (move_left)
    {
      movement.x -= 0.5f;
    }
    if (move_right)
    {
      movement.x += 0.5f;
    }
      apply_force(movement);
  }

  public void display()
  {
    noStroke();
    fill(150, 50, 150);
    ellipse(position.x, position.y, diameter/2, diameter/2);
  }
}

  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Pew_Pew" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
