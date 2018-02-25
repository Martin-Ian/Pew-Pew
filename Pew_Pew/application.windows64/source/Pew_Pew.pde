boolean move_up, move_down, move_left, move_right;
boolean shoot_up, shoot_down, shoot_left, shoot_right;
float cameraX, cameraY;

Player player;

void setup()
{
  //size(600, 600);
  fullScreen();
  cameraX = width/2;
  cameraY = height/2;
  player = new Player();
}

void draw()
{
  background(100);

  player.move();
  player.update();
  
  cameraX = lerp(cameraX, player.position.x, 0.075);
  cameraY = lerp(cameraY, player.position.y, 0.075);
  translate(width/2 - cameraX, height/2 - cameraY);
  
  strokeWeight(5);
  stroke(0);
  noFill();
  rect(0, 0, width, height);
  
  player.display();
}

void keyPressed()
{
  set_move(key, true);
}

void keyReleased()
{
  set_move(key, false);
}

void set_move(int key, boolean set)
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