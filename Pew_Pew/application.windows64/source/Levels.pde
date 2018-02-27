void process_level(PImage level)
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