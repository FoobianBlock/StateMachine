# Very simple Java based deterministic finite state machine 
Little thing I threw together for school

## Example Input file
*This automaton reaches an accepting state for decimal numbers (definition may vary from reader to reader)*
```
// $            = initial state
// %            = accept state
// a1:          = state header
// x;y>state    = transitions to 'state'
// x;y>$start   = (the % or $ musn't be left out when denoting targets)
// ABC#a;b;c    = Replace any occurence of 'ABC' in transitions
//                with 'a;b;c' (must be defined before the first state)

NONZERONUMBER#1;2;3;4;5;6;7;8;9

$z0:
->z1
0>z3
NONZERONUMBER>z2

z1:
0>z3
NONZERONUMBER>z2

z2:
0;NONZERONUMBER>z2
.>z4

z3:
.>z4

z4:
0;NONZERONUMBER>%z6

%z6:
0;NONZERONUMBER>%z6
```

## Shortcomings
- The alphabet is always the full US-ASCII character set
- The start state can currently not also be a accept state