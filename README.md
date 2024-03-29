# Fite-Nite Of Legends: Global Offensive XV (FNOLGO XV)
## Authors
Shaylan Dias, Ben Gu, Jason Zhu \
5/2/18

![Image of FNOLGO](https://github.com/ShaylanDias/FNOLGO-XV/blob/master/FNOLGO.png)

## Description:
In a world, where nothing seems to make sense anymore, a dark, dangerous, deadly, death match is taking place. The leaders of the splintered factions have placed heavy bets on their champions to succeed. They hold an annual contest to determine the strongest faction of them all to lead their people and rule over all the land. Whose faction are YOU a part of? Take control of a champion and fight to take control of this mysterious new world.

## Instructions:
- WASD - Movement
- Mouse - Aim
- Left Mouse Button - Basic Attack 1 (Fireball)
- Space - Dash in direction of mouse
- Right Mouse Button - Basic Attack 2
- E - Ability 1
- R - Ability 2
- F - Ability 3
- Q - Ultimate Ability

## Feature List:
Must Haves
- Server Networking
- Menu
- Single map, GUI with basic character models that move
- Basic movement and combat abilities w/ effects
- Block and roll defensive mechanics
- Win/Loss mechanic (battle royale)
- 3 classes/characters: Brute, Mage, Ranger 
- Cooldown System

## Want to Haves
- Powerups: speed-booster, heal (minor, major), no-depletion stamina, shield. 
- Spectator Mode
- Weapons pickups: Crossbow, Bomb, Mine
- Arena obstacles/Effects
- Multiple classes/types of characters 
- Some Overwatch style ult build up. Not something like a Smash Ball 
- The server would be able to handle more than 2 people (Free-for-all)
- 6 classes/characters: fighter, soldier, ranger, ninja, sumo, mage

## Stretches 
- Multiple Maps
- Team games/Multiple game modes (Deathmatch, Duel, 
- Progression from wins -> Customizable abilities by class (upgrades/perk tree)

## Project Structure:
	•	CLIENTSIDE
		◦	GUI PACKAGE
	▪	Main - Main method for the code 
	▪	GamePanel - The interface that draws everything 
		◦	ControlType - enum constructor
		◦	ImageWrapper - a wrapper for the images
		◦	Player - creates an individual player
		◦	Resources - Stores all the images
	•	GAMEPLAY
		◦	ATTACK
	▪	Attack - A damaging or status-causing effect that an Avatar can create
	▪	Fireball - the projectile attack
	▪	MovingSprite - superclass for avatar
	▪	Projectile - creates the projectiles that a character will fire
	▪	StatusEffect - stuns, stuff the inhibits avatar movement
	•	AVATAR
		◦	Avatar  - abstract class that defines what a player’s character is
		◦	Brute - A class in the game. Melee strongman type of deal
		◦	Ranger - A class in the game. Bow user
		◦	Mage - Another type of character in the game. Magic caster
	•	MAPS
		◦	Map - abstract class that declares map 
		◦	StandardMap - the actuall map tile
	•	GameManger - Runs the game mechanics from the server side, Listens to the network from the server
	•	GameState - Stores the gamedata to send from the server to the GamePanel to be drawn
	•	Package: Networking.frontend
		◦	NetworkDataObject - A piece of data sent over the network between 	clients and servers.
		◦	NetworkListener - Called when a successful connection with a server 	is made.
		◦	NetworkManagementPanel - Window for creating and connecting to servers
		◦	NetworkMessenger - Sends data to other connected clients.
	•	Package: Networking.backend
			ClientReader
			ClientWriter
			GameServer - Takes in information from connected GamePanels to pass on to GameManager and sends back GameState to be drawn
		◦	PeerDiscovery - Performs broadcast and multicast peer detection. How well this works depends on your network configuration

## Responsibilities: 
- Shaylan Dias: Networking, Projectile Game Mechanics, Game Manager
- Ben Gu: Character movement
- Jason Zhu: MAP, random stuff, character selection menu.  
