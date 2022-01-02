# Hyper Minecraft Auth
![preview](https://i.imgur.com/xkqL7Mx.png)

## About Hyper Minecraft Auth
- Hyper Minecraft Auth is the #1 and only plugin for using Hyper keys on your Minecraft server!
- To learn more about what Hyper is, and how it can help you, check out https://hyper.co/!

## Extra
Checkout our Spigot Page https://www.spigotmc.org/resources/hyper-minecraft-auth.89725/!

## Requirements
- Hyper Minecraft Auth currently only supports Spigot. Other server implementations may work, but we don't recommend them as they may cause compatibility issues.

## Setup
To setup Hyper Minecraft Auth on your server follow the steps below!
1. Download the jar from this page.
2. Place the jar into the "Plugins" folder.
3. Start your server.
4. Once the server finished starting up shut down the server.
5. Open the "HyperMinecraftAuth" folder inside of the plugin folder.
6. Open "config.yml"
7. Add your Hyper Public Authorization key into there.

An example of the config.yml can be found below:
![preview](https://i.imgur.com/X2tXPD3.png)

Raw:
```
lists:
  authorizedplayers: []
  usedkeys: []
API_KEY: Bearer pk_########################
maps: {}
```
## Commands
Players:
- Auth <key> // Authorize <key> - Using either players can authorize your key!
- Unauthorize // Unauth - Using either players can unauthorize their key.

Ops:
- Unauthorize <user> // Unauth <user> - Using either ops can unauthorize players keys.
- Bypassauth <player> - Using this command ops can make it so the user doesn't need a key to play!

## Support
For support with the Hyper Minecraft Auth plugin please DM Folded Lettuce#0001 on Discord or on my twitter https://twitter.com/FoldedLettuce
