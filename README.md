# HyperScanning[OpenBCITrigger] - TCP/IP Trigger for Open BCI

[![N|Solid](https://github.com/prasanthsasikumar/localMultiplayer/blob/master/powerdByLogo.png)](http://empathiccomputing.org/)

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://github.com/prasanthsasikumar/localMultiplayer)

This is part of the hyperscanning project that explores how Virtual Reality(VR) can be used to help people understand each other better in face-to-face and remote collaboration, with the ultimate goal of designing an empathic computing interface.  Simultaneous and classification of data from multiple physiological sensors will be carried out to study inter-brain synchrony cognitive load and emotional responses of two or more collaborators in real and virtual environments.


This section explains the implementation of Trigger used to mark the start and end of sessions in BCI designer. This simple TCP/IP client sends markers to the OpenBCI server.  


# REQUIREMENTS
- Any platform that supports Java
- Any platform running Open BCI server

# TECHNICAL SPECIFICATIONS

### Building
- Written in Java, can be build according to user needs. We export from eclipse as executable jar.  

### Structure
- MainClass - Makes use of javax to create UI. All variables are initialized in the begining. There is a function corresponding to each button that is evoked when pressed
- StimulusSender - Manages opening, sending and closure of the TCP/IP connections. Accepts address and port number.
- ToneGenerator - Was used at first for generating different tones, each of which corresponds to a particular action. For eg- a low frequency tone when connection established, a high pitch tone when start marker is send and so on. Later sending the start and end markers were replaced by audio cues but establishing a connection and disconneting still makes use of this class.
- Audio files are to be kept in the same directory as the MainClass. 

### Downloads(Source code)
- Please find the source code here - https://github.com/prasanthsasikumar/OpenBCITrigger
- Issues can be reported here - https://github.com/prasanthsasikumar/OpenBCITrigger/issues/new



### Todos

 - Organize the code

License
----

MIT


**Free Software, Hell Yeah!**

