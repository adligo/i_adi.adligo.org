ADI (Adligo Dynamic Interface)

This is a cross between a Mix in, Service Locater, Proxy, Value Object and a Adapter the main goal being 
better swapability of software subsystems, with some performance enhancements.  Interfaces have 
historically (at least since java has been around) been the way for one part of a system to not 
know about the implementation details of a subsystem.  However Interfaces often end up being 
quite verbose (which is somewhat poor design) which makes them a lot of work to re implement, 
and so they are never reimplemented or reused.  There are of course a lot of exceptions to this 
comment like..

List of great interface usage!
Runnable   
Commons-Logging
List
Map
Set

This project is a attempt to come up with a more standard api for wrapping subsystems.  
It is intended to be used in j2ME environments, J2EE (Servlets, EJB) environments,
GWT environments and swing environments.


Cache is still experimental
light is for Single threaded (Javascript)
heavy is for appserver

Write once test everywhere :)

test commit
