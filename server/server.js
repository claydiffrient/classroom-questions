/**
 * Server for the Classroom-Questions application.
 */
var PORT = 6789;
var MCAST_ADDR = '239.239.239.1';
var HOST = '10.50.176.135';

var dgram = require('dgram');             // Provides UDP services
var sys = require('sys');                 // Access the system.
var server = dgram.createSocket('udp4');  // Make a UDP socket.
var readline = require('readline');

var rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});



server.bind();


//////////
/// Event Listeners
//////////

/**
 * Called after a bind event has occured
 */
server.on('listening', function () {
  server.setBroadcast(true);
  server.setMulticastTTL(128);
  server.addMembership(MCAST_ADDR);
  var address = server.address();
  console.log("server listening on " + address.address + ':' + address.port);

  rl.question("What question do you want to ask? ", function (answer) {
    /*var message = new Buffer(answer);
    server.send(message, 0, message.length, PORT, MCAST_ADDR, function (err, bytes) {
      server.close();
    }); */
  });

});

rl.on('line', function (input) {
  console.log("here we are..." + input);
  var input = new Buffer(input);
  server.send(input, 0, input.length, PORT, MCAST_ADDR);
});

/**
 * Called whenever an error condition occurs
 */
server.on('error', function (err) {
  console.log('Server Error:\n' + err.stack);
  server.close();
});
