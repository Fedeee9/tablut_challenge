# TablutCompetition
Software for the Tablut Students Competition

## How to start player
Clone the repository
```
git clone https://github.com/luca-ant/tablutchallenge.git
```

Run JAR file. The first argument must be your color (BLACK or WHITE)

`
java -jar teampedro.jar BLACK
`

or

`
java -jar teampedro.jar WHITE
`

## FOR UNIBO CHALLENGE
Run JAR file with **-p 2** option

`
java -jar teampedro.jar BLACK -p 2
`

or

`
java -jar teampedro.jar WHITE -p 2
`


### Optionally you can add some arguments to change the execution

**-t [timeout]** -> to change timeout for each move (*default 55 sec*)

**-p [core]** -> to set threading option (*default 8*)

**-d [max_depth]** -> to change max depth which min-max alghoritm try to reach (*default 8*)

**Example of used option parameters:**

`
java -jar teampedro.jar BLACK -t 30
`
