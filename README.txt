ADI (Adligo Dynamic Interface)

This is a cross between a Mix in, Service Locater, Proxy, Value Object and a Adapter the main goal being 
better swapability of software subsystems, with some performance enhancements.  Interfaces have 
historically (at least since lava has been around) been the way for one part of a system to not 
know about the implementation details of a subsystem.  However Interfaces often end up being 
quite verbose (which is somewhat poor design) which makes them a lot of work to re implement, 
and so they are never reimplemented or reused.  There are of course a lot of exceptions to this 
comment like..

Runnable   
Commons-Logging
List
Map
Set

This project is a attempt to come up with a more standard api for wrapping subsystems in a thread safe 
stateless manor.  It is intended to be used in j2ME environments, servlet environments,
GWT environments and swing environments.