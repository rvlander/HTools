HTools
======

HTools, tools for studying handwriting.

PRESENTATION
------------

This suite aims at providing several tools to record, edit, 
model and do more things on handwritten traces. By now, it is 
composed of 4 piece of software. Both 4 of them are compatible 
with Windows & Linux and are based on Java:

  1. ExpDO is a handwriting trace recorder, allowing to record 
traces both with mouse and using a Wacom tablet. Recordings 
are organized by an experimentation file to ease the process of 
making an experiments.
  2. HandwritingEditor allow to modify a trace interactively working 
on the signal or it derivatives. Several tools are provided such as 
resize, move, smooth and hand-free edition of the signals.
  3. HollerSynth is a tiny program based on the Hollerbach's 1986 paper 
in which handwriting is modeled as the result of two oscillators with piece-wise 
constant parameters changing at vertical zero velocity moments of handwriting. 
This programallows to edit interactively these parameters and displays 
the resulting synthesized trace.
  4. HollerMap is a piece of software allowing to apply several algorithm
 to traces and display the result and export them in a PDF file. The 
main algorithm is the one explained in André and al. [submitted].

REQUIREMENTS
------------

HTools run on both Linux and Windows.

Htools require Java>1.6 to run. HandwritingEditor needs a valid 
installation of Matlab.

INSTALLATION
------------

HTools can be built using Apache ant or graphically using Netbeans.  

Using Apache ant : 
cd to the base root of the project and type :
>$ ant -f HTools/ jar

Using Netbeans :
 Open the HTools project in Netbeans and build it.

RUNNING 
-------

You have 2 way of running the tools :
  1. Use Netbeans' run (F5) command on each project (one project = one tool).
  1. In a terminal, cd in HTools_build  and run :
>$ java -Djava.library.path=. -jar <The program>.jar

AUTHOR
------

Gaëtan André <gaetan.andre@gmail.com>
