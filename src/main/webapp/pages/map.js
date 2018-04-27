const $ = require('jquery');

const { onLogin, showLogin, showRegister, hideOverlay } = require('../overlay.js');

const showUserMaps = (user) => {
  $.ajax({
    url: '/ws/user/maps',
  }).always(console.log);
};

const main = () => {
  onLogin(showUserMaps, showLogin);
}

module.exports = {
  main: main
};
