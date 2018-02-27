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