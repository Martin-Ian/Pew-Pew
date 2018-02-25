class Player
{
  PVector position;
  PVector velocity;
  PVector acceleration;

  float diameter = 100;
  float max_speed = 5;
  float slowDown = 0.95;

  Player()
  {
    position = new PVector(width/2, height/2);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
  }

  void update()
  {
    velocity.add(acceleration);
    velocity.limit(max_speed);
    position.add(velocity);
    velocity.mult(slowDown);
    acceleration.setMag(0);
  }

  void apply_force(PVector force)
  {
    acceleration.add(force);
  }

  void move()
  {
    PVector movement = new PVector(0, 0);
    if (move_up)
    {
      movement.y -= 0.5;
    }
    if (move_down)
    {
      movement.y += 0.5;
    }
    if (move_left)
    {
      movement.x -= 0.5;
    }
    if (move_right)
    {
      movement.x += 0.5;
    }
      apply_force(movement);
  }

  void display()
  {
    noStroke();
    fill(150, 50, 150);
    ellipse(position.x, position.y, diameter/2, diameter/2);
  }
}