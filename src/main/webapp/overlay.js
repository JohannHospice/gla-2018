const $ = require('jquery');
const _ = require('underscore');

const showPage = (page) => {
  let tpl = require('./pages/tpl/' + page + '.html');
  $('div.overlay').html(tpl);
  $('div.app').css('display', 'none');
  $('div.overlay').css('display', 'block');
};

const showLogin = () => {
  showPage('login');
  $('div.overlay a.register-link').on('click', () => {
    showRegister();
    return false;
  });
}

const showRegister = () => {
  showPage('register');
  $('div.overlay a.login-link').on('click', () => {
    showLogin();
    return false;
  });
}

const hideOverlay = () => {
  $('div.overlay').css('display', 'none');
  $('div.app').css('display', 'block');
};

module.exports = {
  showLogin: showLogin,
  showRegister: showRegister,
  hideOverlay: hideOverlay
};
