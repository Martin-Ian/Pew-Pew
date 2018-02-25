/**********************************
 * MADE BY: IAN MARTIN
 * PEW_PEW
 **********************************/

//These are for movement and shooting
boolean move_up, move_down, move_left, move_right;
boolean shoot_up, shoot_down, shoot_left, shoot_right;

//This is so the "camera" follows the player
float cameraX, cameraY;

//Object declarations
Player player;
ArrayList<Bullet> bullets = new ArrayList<Bullet>();

void setup()
{
  size(600, 600);
  //fullScreen();
  cameraX = width/2;
  cameraY = height/2;
  player = new Player();
}

void draw()
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

  //This is how the camera follows the player
  cameraX = lerp(cameraX, player.position.x, 0.075);
  cameraY = lerp(cameraY, player.position.y, 0.075);
  translate(width/2 - cameraX, height/2 - cameraY);

  //This is temparary... Show the boundry box
  strokeWeight(5);
  stroke(0);
  noFill();
  rect(0, 0, width, height);

  for (Bullet B : bullets)
  {
    B.display();
  }

  player.display();
}

//Returns true if the given PVector is out of the arena
boolean out_of_bounds(PVector location)
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
void keyPressed()
{
  set_move(keyCode, true);
}

//sets the key to false
void keyReleased()
{
  set_move(keyCode, false);
}

//sets the key to the given boolean
boolean set_move(int key, boolean set)
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