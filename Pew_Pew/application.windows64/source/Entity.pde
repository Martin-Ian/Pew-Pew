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
  color entity_color;

  Entity()
  {
    position = new PVector(0, 0);
    velocity = new PVector(0, 0);
    acceleration = new PVector(0, 0);
    axis = new PVector(10, 10);
    slowDown = 1.0;
    max_speed = 5;
    shape = "Circle";
    entity_color = color(random(255), random(255), random(255));
  }

  void update()
  {
    //Physics engine
    velocity.add(acceleration);
    velocity.limit(max_speed);

    position.add(velocity);

    velocity.mult(slowDown);
    acceleration.setMag(0);
  }

  void apply_force(PVector force)
  {
    //Physics engine
    acceleration.add(force);
  }

  void display()
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