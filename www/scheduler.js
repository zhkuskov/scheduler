/*var exec = require('cordova/exec');

var scheduler = {
    start:function() {
        exec(null, null, "SchedulerPlugin", "start", []);
    },
    stop:function() {
        exec(null, null, "SchedulerPlugin", "stop", []);
    }
};

module.exports = scheduler;*/

window.scheduler = function(scallback, ecallback) {

 cordova.exec(
scallback, 
ecallback, 
"SchedulerPlugin", 
"start", 
[]
);

 };