# --------- Quake Trace ----------

The team 'Blaze' consisting of zaid bin junaid , vagish dilawari and vinay vyas is indulged in making the 'Quake Trace' app for the competition Microsoft Code Fun Do.

### objective:

In case of earthquakes , it has been a common observation that in cases when building collapses , often the victims (alive) get buried in the debris. they are left buried there for quite a lot of time. this is so because the rescue team do not have enough information as to where the victims are buried. due to this , the alive victims are left buried within the debris for a couple of days and they end up dying due to starvation, suffocation or other reasons. moreover , the rescue team will waste a lot of time in searching those places where there are no victims.
### if somehow the rescue team gets to know the location of the victims , they would first search those places and thereby quicly save the victims.

### idea:

To design an android app which automatically picks the location of the victim and sends it to the rescue team (the user need not do anything for his/her location to be sent) on the occurence of a fatal earthquake

### app description:

the rescue team needs to have a server (azure database for our app demonstration) in order to store victim's location and name. the app checks for seismic activity at regular intervals of time through an Earthquake API. if the checked seismic value is above 5 , the app picks user's current location and sends it to the rescue server. thus , the rescue team can take necesarry actions. it is possible that the user is safe. so, as soon as the location of the user is sent to the rescue server, an alert message is launched by the app asking whether he/she is safe. if the user agrees to it ,then it will be reflected in the database by changing the 'complete' parameter from false to true(saving the time of rescue team from carrying out the search operation in that location). the app keeps on operating in the background by the use of job scheduler (it keeps re-launching itself at regular intervals)

### role of azure database:

azure database serves as the database of the rescue team who receive the location (longitude and latitude), name of the victim and phone number of an acquaintance of the victim (phone number and name had been entered after app installation) when an earthquake of magnitude 5 or above hits the region . if the person has not been victimised by the earthquake he or she hits the ok button in the alert box which in turn changes the complete parameter from false to true showing that the victim is safe.

### features used: 
earthquake API
job schedular
azure database
       
       ----------------------------------------
