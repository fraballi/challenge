# Challenge Game

## Release Notes

```yaml
    App_Version: 1.0
    JDK_Version: AdoptOpenJDK (build 11.0.3+7)
    Build_Tool_Version: maven-3.6.1
    SCM_Version: git-2.22.0
```

### Introduction

```bash
Hi there, Challengers!

- The program arranges fights between two characters.
- A character gains experience and other abilities during fights and exploration activities.
- A match can be saved and restored later
- 'q - Quit' is not an option !!!


                                   ...
                                  .....
    }                          [[.[].[].]]
====> Let's FIGHT! ==>          88888888
    }                        [-( 9)  9) )-]
                                8  (   8
                                 8 -= 8
                                   88

CHALLENGE

1 - Create a character
2 - Explore
3 - Fight
4 - Resume game
5 - Save game
q - Quit
```

### Instructions

---

```bash
# *nix like example
# e.g folder: /home/i-am/challenge

# Run:

  cd
  git clone https://github.com/fraballi/challenge.git

  cd ./challenge && \
  mvn package && \
  java -jar ./target/challenge-1.0-SNAPSHOT.jar
```

**Note**: *The application comes in 'uber-jar' flavor*

### *Create characters*

```bash
Create a character:
    Name: Crusader_A
    # ...

Select a weapon:    # single-selection
    1 - AX [Damage: 0.45]
    2 - SWORD [Damage: 0.4]
    3 - MAGIC [Damage: 0.35]
    4 - HAMMER [Damage: 0.3]
    5 - SPEAR [Damage: 0.25]
    6 - CHAIN [Damage: 0.2]
    7 - ROPE [Damage: 0.15]

    # ...   e.g Input: '1' (AX)

Select available actions (Press 's' = Save): # multiple-selection
    1 - MOVE
    2 - RUN
    3 - JUMP
    4 - FLIGHT
    5 - SWIM
    6 - ATTACK

    # ... e.g Input: '1', '2', '3', '5', '6' ('AX')
    # !!! Attention: No birds (Input: '4')

+------------------------------------------------------------+
|Name       |Weapons          |Actions                |Damage |
+------------------------------------------------------------+
|Crusader_A |AX [Damage: 0.45]|JUMP, RUN, ATTACK, MOVE|10000.0|
+------------------------------------------------------------+

+--------------------------------------------------------------------+
|Name       |Weapons            |Actions                      |Damage |
+--------------------------------------------------------------------+
|Crusader_B |SWORD [Damage: 0.4]|JUMP, RUN, ATTACK, MOVE, SWIM|10000.0|
+--------------------------------------------------------------------+

```

#### *Fight*

```bash
Select (2) characters (Press 's' = Save, 'r' = Reset):

+------------------------------------------------------+
| ID         | Name       | Weapons                    |
+------------------------------------------------------+
| 1          | Crusader_A | AX [Damage: 0.4]           |
| 2          | Crusader_B | SWORD  [Damage: 0.45]      |
+------------------------------------------------------+

Opponent: 1  # Column ID
    # ...
Opponent: 2
    # ...
Select (2) characters (Press 's' = Save, 'r' = Reset):
Match is full (2 characters). Press 's' / 'r':
    # ...

           .ooooo:
           :@@@@@@
           :@@@@@@
           o8***o@
           &&.  :@.
           8@@ :@@.
           #@@ :@@*
           @@@ ...
        .. @@: *************.
      .#@& .*..@@@@@@@@@@@@@&
      #: o&*. .@@@@@@@@@@@@@&
     .@ .@@@@*.@@@@@8 *@@@@@&
     .@:@@@@@*.@@@@@@ :@@@@@&
     .@@@@@@@*.@@@@@@ :@@@@@&
     :@@@@@@@*.@@@@@@ :@@@@@&
     8@@@@@@@*.@@:::: .:::&@&
    .@@@@@@@@*.@@.        o@&
    :@@@o#@@@*.@@&@@@ :@@#8@&
    &@@@.&@@@*.@@@@@@ :@@@@@&
    @@@# o@@@*.@@@@@@ :@@@@@o
   o@@@: :@@@: @@@@@@ :@@@@@o
   o@@@. .***. #@@@@@ :@@@@@*
   o8@@  ..... o@@@@@ :@@@@@.
   :o.@*.@@@@@..@@@@@ :@@@@&
   :o .@#@@@@@o o@@@@ :@@@@.
   *&  @@@@@@@@. @@@@ :@@@o
   .# .@@@@@@@@# .@@8*:@@&
   .@ .@@@@@@@@@& *@@@@@&
   .@:o@@@@@@@@@@o .#@@o
    oooooooooooooo:  :*
```

#### *Explore*

```bash
Select a character to go explore (Press 's' = Save, 'r' = Reset):

+------------------------------------------------------+
| ID         | Name       | Weapons                    |
+------------------------------------------------------+
| 1          | Crusader_B | SWORD [Damage: 0.4]        |
| 2          | Crusader_A | AX [Damage: 0.45]          |
+------------------------------------------------------+

Adventurer: 1


@@@@@@        @@@@  @@          @@    @@                              @@       @@@@                             @@  @@  
  @@          @@                      @@                              @@       @@ @@                            @@  @@  
  @@   @@@@@ @@@@@ @@@   @@@@@ @@@   @@@@@  @@@         @@@  @@@@@  @@@@       @@ @@  @@@  @@ @@  @@@  @@@@@  @@@@  @@  
  @@   @@ @@  @@    @@   @@ @@  @@    @@   @@ @@          @@ @@ @@ @@ @@       @@@   @@ @@  @ @  @@ @@ @@ @@ @@ @@  @@  
  @@   @@ @@  @@    @@   @@ @@  @@    @@   @@@@@       @@@@@ @@ @@ @@ @@       @@ @@ @@@@@  @ @  @@ @@ @@ @@ @@ @@  @@  
  @@   @@ @@  @@    @@   @@ @@  @@    @@   @@          @@ @@ @@ @@ @@ @@       @@ @@ @@     @@@  @@ @@ @@ @@ @@ @@
 @@@@  @@ @@  @@  @@@@@@ @@ @@@@@@@@  @@@@  @@@@       @@@@@ @@ @@  @@@@       @@@@   @@@@   @    @@@  @@ @@  @@@@  @@  
                                                                                            @@
                                                                                            @@
```
