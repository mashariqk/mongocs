conn = new Mongo("mongodb://localhost:27017/test?replicaSet=rs");
db = conn.getDB("test");
collection = db.customers;

const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

var docToInsert = {
    firstName: "",
    lastName: "",
    birthDayMonth:""
};

function sleepFor(sleepDuration) {
    var now = new Date().getTime();
    while (new Date().getTime() < now + sleepDuration) {
        /* do nothing */
    }
}

function create() {
    sleepFor(1000);
    print("inserting doc...");
    docToInsert.firstName = "fname" + Math.floor(Math.random() * 10000);
    docToInsert.lastName = "lname" + Math.floor(Math.random() * 90000);
    docToInsert.birthDayMonth = months[Math.floor(Math.random() * months.length)];
    res = collection.insert(docToInsert);
    print(res)
}

while (true) {
    create();
}