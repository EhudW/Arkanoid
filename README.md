# Arkanoid
arcade game using the Bar-Illan University biuoop jar file (**I don't know about license**).

based on assignment 6 of https://github.com/kleinay/biuoop2020/wiki/Assignment-2#part-2-gui-and-abstract-art


![Level four in game gif](/gifmaker_me.gif "Level 4 gif")
![Tower level in game](/tower.png "Tower level")

# run --h
```
Arkanoid game
    made by Ehud Wasserman as part of oop course, Bar-Ilan University
    with adjustments later
    'biuoop-1.4.jar', 'build.xml' is not mine and might has copyrights to Bar-Ilan
    which therefore also affect 'Arkanoid.jar'
How to play
    hit the blocks with the balls until the level is clear
    move the paddle with ARROWS or 'WASD' to avoid the balls from falling down
    hit 'P' to pause, and SPACE to continue
    each level start with only enabling left/right but you can enable up/down with 'U'
Using ant with given 'build.xml'
    ant clean
    ant compile
    ant run [-Dargs="[--help] [--levels=(1|2|3|4)*] [--lives=7] [--3d]"]
Running command:
    java -jar Arkanoid.jar [--help] [--levels=(1|2|3|4)*] [--lives=7] [--3d]
${args}:
    --help  --h  --?              show this msg
    --levels=12  --lvl=334        sequence of levels to be played [there are 1-4 lvls]
    --lives=10   --live=7         lives for the game, positive integer
    --3d                          3D view
```
where ${args} are the command lind arguments

so there are 2 ways to compile and run the **new** compiled version
```
With ant
  ant clean
  ant compile
  ant run -Dargs="${args}"

or without
Windows ;
  javac -d bin src/*.java -cp biuoop-1.4.jar;src
  java -cp biuoop-1.4.jar;bin Arkanoid ${args}
Linux :
  javac -d bin src/*.java -cp biuoop-1.4.jar:src
  java -cp biuoop-1.4.jar:bin Arkanoid ${args}
```

the **precompiled** jar can be run with 
```
Windows cmd
  run.cmd ${args}
Linux bash
  ./run.sh ${args}
both will run the following
  java -jar Arkanoid.jar ${args}
```
however, it **will not be updated** when compiling classes outside
