const path = require('path');
const _ = require('underscore');
const fs = require('fs');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

let pages = require('./pages.json');

let plugins = _.map(pages.list, (page) => {
  return new HtmlWebpackPlugin({
    filename: page.id + '.html',
    template: pages.template,
    page: page.id,
    title: page.title
  });
});

plugins.push(new MiniCssExtractPlugin({
  filename: '[name].css'
}));

module.exports = {
  mode: 'production',
  entry: [
    './src/main/webapp/app.js',
    './src/main/webapp/app.scss'
  ],
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist')
  },
  plugins: plugins,
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /(node_modules|bower_components)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env']
          }
        }
      },
      {
        test: /main\/webapp\/pages\/tpl\/[^.]+\.html$/, // bundled templates
        use: {
          loader: 'html-loader',
          options: {
            attrs: [':data-src'],
            minimize: true
          }
        }
      },
      {
        test: /\.scss$/,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader',
          'sass-loader'
        ],
      }
    ]
  }
};

