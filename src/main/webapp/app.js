const $ = require('jquery');
const L = require('leaflet');
const _ = require('underscore');

console.log('jquery: ' + $.fn.jquery);
console.log('leaflet: ' + L.version);
console.log('underscore: ' + _.VERSION);

let current_page = $('body').attr('data-page');
const { main } = require('./pages/' + current_page);
main();
