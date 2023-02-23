
## Name
Human Benchmark Games using JavaFX.

## Description
This is the implementation of all the games that are available in humanbenchmark.com website using JavaFX. In this 
project, you will be able to play 8 games from the website, which are connected to the main class called GameManager, 
which has the main menu of the game and all the games linked to the main screen. You will be able to jump to any game 
you desire using the main menu, and then play the game from there. You will be prompted to enter your name whenever you
click on any game, but if you already put your name by clicking on the "Get Started" button, you will not have to enter
your name again and it will take you straight to the game. The games use injectButton function which injects the back
button to the games which then returns the user to the main screen when clicked on by setting the root of the 
GameManager class.

## Visuals
Depending on what you are making, it can be a good idea to include screenshots or even a video (you'll frequently see GIFs rather than actual videos). Tools like ttygif can help, but check out Asciinema for a more sophisticated method.

## Games

### Aim Trainer
This game is nearly complete but still has some fixing to do. The game is able to create circles at random places for the user to click on it.

### Reaction Timer
The game is able to get the average time after 5 attempts, and then user can restart the game.

### Visual Memory Test
Incomplete

### Verbal Memory Test
This game checks the input with the given word from dictionary and randomly shows words from the dictionary file. If 
the user is not able to identify the word, or fails to be correct when choosing if it is a new word or a seen word for
3 times, then the game ends and the user gets a prompt to try again. User can try again as many times as they want. They
can quit the game by clicking on the back button.

### Number Memory
Incomplete

### Typing Game
Incomplete

### Chimp Test
Incomplete

### Sequence Memory
Incomplete

### Own game (Hit Me!)
It is a simple implementation where the dot appears and disappears.The user has to click on it before
it disappears and reappears. If user doesn't click on it before it disappears, the miss count goes up, similarly,
if the user clicks on it before it disappears, hit count goes up. The user gets 3 tries to miss, and when it is reached, the game ends.
 
## Installation
There is no need of any extra installation than the JavaFX JDK installed on your system. The game can be executed by 
using the .jar file of the game. It includes all the necessary files to run, except for the JDK itself.

## Authors and acknowledgment
Thanks to the author of the website humanbenchmark.com to create such games which helped us take the challenge of 
creating the game on our own using JavaFX.
## License
This project is part of the CS351 project assignment. The code is written by me but is not to be open to avoid it being 
used in the future by any student taking the class incase the professor reuses the project assignment.
## Project status
This project is still incomplete, which a lot to fix. The back button is still not working as expected. Games are also 
to be improved further to make them function properly and make them look more like that in the website.