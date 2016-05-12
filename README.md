# Google IO 2016 Extended Sri Lanka
## participants selection algorithm

This is the selection algorithm for the registered users for Google IO 2016 extended Sri Lanka.

All registrants are given weights for the following attributes

General weight = 7

For each previous IO participation = 1

For having an app in ideamart = 7

For having an app in the playstore = 7


In addition to this, a score from RSVP.lk is added to this but normalized by dividing by 2.
The RSVP.lk score reflects your previous GDG event participation. This score can be negative. 
Negative marks are added for participants who registered, RSVP'ed `Yes` and did not participate for events.

The weights are accumulated (say W) and then the algo is run W times.

Algorithm:
- Get the MD5 of the user's name, and XOR it with the MD5 of the current time from epoch.(ms) (Say X)
- Then count the number of 1's in that X's binary representation. (say K)
- Do a MOD operation to the value of X with K (X mod K) and get the value (say M)
- check if M is prime. If prime, a point is awarded. 

This point awarding is done W times. Then if number of points is above a selected percentage of W, (say P) 
the user is selected for the event.
P is selected by GDG to limit the number of final selected participants to 1000. At the time of this writing P is 98%.

## Please note:
## This is for transparency purposes only. The algo returns a different list of names every time it is run
## Therefore, this repo does not serve the purpose of you cloning it and running it at the local end and disputing the results.
