const $ = require('jquery');
const L = require('leaflet');
const _ = require('underscore');

console.log('jquery: ' + $.fn.jquery);
console.log('leaflet: ' + L.version);
console.log('underscore: ' + _.VERSION);

let current_page = $('body').attr('data-page');
let menu_current = $('li.menu-' + current_page);
if (menu_current.length)
  menu_current.addClass('active');
$('li.menu-plus').on('click', () => { return false; });
$('form.search-form').on('submit', () => { return false; });
const { main } = require('./pages/' + current_page);
main();
