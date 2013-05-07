#Play Framework Bootstrap

##Overview
The goal is to let developers go from 0 to tackling real problems in one line of code.  By removing all the cruft of setting up your DB layer, and addding authentication, a developer can go straight to tackling all the hard problems of their application. 

##Packages
Bundles with:

[Salat MongoDB Driver](https://github.com/novus/salat)

[Secure Social Authentication](http://securesocial.ws/)

##Usage
We use the [giter8](http://github.com/n8han/giter8) to build our projects.

To create a new project:

```g8 mbseid/play-mongo-securesocial```

It will prompt you for some information.  After you will have you persoanlized project.
Please update conf/securesocial.conf and conf/mongo.conf to finish your instalation.

##Play Form Mappings
There is now a mapping for ObjectIds.  This will significitaly simply your code, allowing for ObjectId to be parsed and valided on form binding.  

```
import import utils.formaters.ObjectIdFormatter._

val form = Form(
	Mapping(
	"id" -> objectId,
	"name" -> text,
	"list" -> list(objectId)
	)
)

```
