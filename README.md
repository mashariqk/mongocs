# MongoDB Changestream demo

This poc project can be used to demonstrate the change stream concept
 and how it can be used to create an audit trail if needed. 
The application is designed to only receive updates of customers with the birth month specified in the
property ``` watch.cursor.month ```. It will default to January if no month is provided

### Tech

* Java 1.8
* Mongo DB 4.2.3
* Gradle 5.6.4 
 
### Installation 

From the root of the project run the following commands:
```shell script
mkdir -pv data/db
mongod --dbpath ./data/db --replSet "rs"
```

if you get address already in use error then run the following commands in a new shell
```shell script
mongo
use admin
db.shutdownServer()
```

Then run the previous commands before running the following.

Then in a separate shell tab, run:
```shell script
mongo
```

After the rs:PRIMARY> prompt (or just > in the first run) appears, run: 
```shell script
rs.initiate()
```
For the first run the output will be something like
```json
{
	"info2" : "no configuration specified. Using a default configuration for the set",
	"me" : "localhost:27017",
	"ok" : 1,
	"$clusterTime" : {
		"clusterTime" : Timestamp(1586447207, 1),
		"signature" : {
			"hash" : BinData(0,"AAAAAAAAAAAAAAAAAAAAAAAAAAA="),
			"keyId" : NumberLong(0)
		}
	},
	"operationTime" : Timestamp(1586447207, 1)
}
```

For subsequent runs it may give an error like below which can be ignored
```json
{
	"operationTime" : Timestamp(1586447350, 1),
	"ok" : 0,
	"errmsg" : "already initialized",
	"code" : 23,
	"codeName" : "AlreadyInitialized",
	"$clusterTime" : {
		"clusterTime" : Timestamp(1586447350, 1),
		"signature" : {
			"hash" : BinData(0,"AAAAAAAAAAAAAAAAAAAAAAAAAAA="),
			"keyId" : NumberLong(0)
		}
	}
}
```

Open a new terminal window and navigate to the root of the project. Then run
```shell script
mongo src/main/resources/createCustomers.js
```

Completing these steps will start the mongo db and a steady stream of data getting inserted
in the database test and in collection customers with random first and last names and a random birth month. 
Once this setup is done start the spring boot app with the following command
```shell script
gradle bootRun
```

The app will now start receiving the updates of users with the birthday month as specified.
The createCustomers.js file is only inserting records, to see an update a query like below can be executed in mongo shell

```sql
db.customers.update({"firstName":"fname3195"},{$set: {"lastName":"uofname2376"}});
```

