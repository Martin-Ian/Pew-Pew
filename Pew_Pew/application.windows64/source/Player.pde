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
    diameter = 100;
    slowDown = 0.95;
    entity_color = color(150, 20, 150);
    shape = "Circle";
    fire_delay = 20;
    cool_down = 0;
    max_speed = 3;
    speed = 1;
  }

  void move()
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

  void shoot()
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