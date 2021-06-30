# ingswAM2021-Carotti-Corti-Crugnola

# MAESTRI DEL RINASCIMENTO
#### *By: Andrea Carotti, Giacomo Corti, Matteo Crugnola*
## Final Examination: Ingegneria del Software

## **List of Requirements Implemented**
- single player mode (full rules)
- multyplayer mode (2-4)
- CLI (linux or macOS are needed because windows doesn't recognise colours and ANSI escape codes)
- GUI
- advanced feature of Multiple cuncurrent games
- extra feature we added of the live chat between players

## **How to Start The Application**
In order to start our application you should follow these few upcoming steps:

To run the program is necessary to use the two files jar:
- client.jar
- server.jar
- 
First run the server.jar with the port number where the server will receive connections

Then run the client.jar with the IP Address of the server, port number and type of graphics as arguments.

Example:

Client: .../java -jar client.jar 127.0.0.1 1234 gui

Server: .../java -jar server.jar 1234

## **CLI**
Every player has a list of actions that can be performed

Only some of the actions can be performed during your turn and some other actions can be performed anytime (for ex: seeing the state of the game and sendig chat messages)

Game event logs and instrutions are printed to the players so they can keep track of what is happening and they can decide what to do next

## **GUI**
#Components
3 Panels + 1 for each other player
- Your Punchboard: here the player can see its Faith Track, Activate the production, see the number of resources at his disposal, his development cards and his leader cards
- Market: here are placed the market marbles and the player can buy resources from here
- Development Cards: Where all the buyable developments cards are placed, from this panel the player can buy one of the twelve cards placed
- Other Player's Punchboard: here the player can observe other player's Faith Track, their resources, their active leader cards and their development cards
- Chat: Each panel is equipped with a chat component where the player can send messages and see updates about the match status and instructions.
- Some actions may create other temporary panels to ease the interactions

![image_alt](https://i.imgur.com/8wGUmOi.png)

![image_alt](https://i.imgur.com/vzKIZS2.png)


## **Server**
The server is implemented in the class MultiEchoServer.java in the package it/polimi/ingsw/server. Initially it puts itself on hold waiting for clients to connect.

Since the multiple game functionality is implemented, multiple games are allowed, and players can choose to connect to a random lobby or to a private match inserting the lobby password he/she decided with the other players beforehand.

If during the match a player is disconnected, the game in which the player is playing is interrupted, and an error message is shown to the other players.

In the eventuality that during the match the server crashes, all the matches are interrupted with an error message appearing.

The server starts the matches at regular intervals, so after a certain period of time, once the lobby is full the game of that lobby will start.

## **UML**
The UML diagrams can be found inside the folder Deliveries together with the protocol design of the application.

## **JAVADOC**
The Javadoc of the project can be found inside the folder Javadoc.
