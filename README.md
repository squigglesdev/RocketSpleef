# ![Rocket Spleef](https://cdn.modrinth.com/data/cached_images/bd4ae5602fffc28590dbca7771ebf5a5f345abe5.png)
A configurable server-side mod that brings back the Tumble functionality of rockets from Legacy Console Edition.

<div align="center">
  <a href="https://modrinth.com/mod/rocket-spleef"><img src="https://cdn.modrinth.com/data/cached_images/8e05af937c2e2d97c155c0d9c8201edcc1fd1bf2.png" width="150" alt="Modrinth" /></a>
  &nbsp;  &nbsp;  &nbsp;
  <a href="https://modrinth.com/mod/fabric-api"><img src="https://cdn.modrinth.com/data/cached_images/4efd8ed27cfea30edc011e5e504187f23312f593.png" width="159" alt="Fabric API"/></a>
</div>


## Features

### Configurable block destroy list

Server admins can, using the `/rocket` command, specify a list of blocks that can be destroyed by rockets:
```mcfunction
rocket add stone
```
```
>> Added block stone to breakable list
```
<br />

```mcfunction
rocket remove stone
```
```
>> Removed block stone from breakable list
```
<br />

```mcfunction
rocket list
```
```
>> The current breakable blocks are: stone, grass_block, ice
```

### Ignite TNT

Server admins can also specify if rockets ignite TNT, similar to the Legacy Console Edition minigame:

```mcfunction
rocket ignitesTNT true
```
```
>> RocketsIgniteTNT set to true
```
<br />

```mcfunction
rocket ignitesTNT false
```
```
>> RocketsIgniteTNT set to false
```

### Hurt Players

Rockets now deal a small amount of damage and knockback to players, similar to the Legacy Console Edition minigame.

## Other Projects

Like RocketSpleef? Consider checking out my other Tumble mechanic port, [SnowballSpleef](https://github.com/squigglesdev/SnowballSpleef/)