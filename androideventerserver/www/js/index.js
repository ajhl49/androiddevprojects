// index.js

var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var path = require('path');

var SERVER_PORT = 3000;

app.get('/events', function (req, res) {
    var obj = {
        'status': 'OK',
        'events': [
            {
                'name': 'NotificationAction',
                'uuid': '487aedb9-dc84-46d9-b9a6-dd30f5fb050f'
            }
        ]
    }
    res.send(JSON.stringify(obj));
});

app.listen(SERVER_PORT, function () {
    console.log('App listening on port ' + SERVER_PORT + "!");
})
