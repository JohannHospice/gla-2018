const $ = require('jquery');
const _ = require('underscore');

const { showLogin, showRegister, hideOverlay } = require('../overlay.js');

let get = (url, callback) => {
  $.ajax({
    url: url,
    dataType: 'json',
  }).done(callback).fail((err) => {
    console.log(err);
  });
}

let main = () => {
  showRegister();
  /*get('ws/user/all', (data, _status, xhr) => {
    console.log(data, xhr);
    $('div.app').html(tpl(data));
  });*/
}

module.exports = {
  main: main
};
