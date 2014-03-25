var PORT = 6789;
var MCAST_ADDR = '224.100.0.1';//'239.239.239.1';
var dgram = require('dgram');
var client = dgram.createSocket('udp4');

client.on('listening', function () {
    var address = client.address();
    console.log('UDP Client listening on ' + address.address + ":" + address.port);
    client.setBroadcast(true)
    client.setMulticastTTL(128);
    client.addMembership(MCAST_ADDR);
});

client.on('message', function (message, remote) {
    console.log('B: From: ' + remote.address + ':' + remote.port +' - ' + message);
});

client.bind(PORT);
