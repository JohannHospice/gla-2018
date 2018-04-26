const $ = require('jquery');

const { onLogin, showLogin, showRegister, hideOverlay } = require('../overlay.js');

const showMaps = (user) => {
  $.ajax({
    url: '/ws/map/public',
  }).always(console.log);
};

const main = () => {
  onLogin(showMaps, showRegister);
}

module.exports = {
  main: main
};
