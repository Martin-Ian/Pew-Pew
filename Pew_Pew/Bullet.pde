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