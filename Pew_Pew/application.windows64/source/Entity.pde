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

  //These differ for each class
  float diameter;
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
    diameter = 10;
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
    if (shape == "Circle")
    {
      fill(entity_color);
      noStroke();
      ellipse(position.x, position.y, diameter/2, diameter/2);
    }
  }
}