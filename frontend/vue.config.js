// vue.config.js
const fs = require('fs')
var path = require('path');

module.exports = {
  // proxy all webpack dev-server requests starting with /api
  // to our Spring Boot backend (localhost:8088) using http-proxy-middleware
  // see https://cli.vuejs.org/config/#devserver-proxy
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8098', // this configuration needs to correspond to the Spring Boot backends' application.properties server.port
        ws: true,
        changeOrigin: true
      }
    },
      https: {
        key: fs.readFileSync('/Users/milutinzeljkovic/localhost+3-key.pem'),
        cert: fs.readFileSync('/Users/milutinzeljkovic/localhost+3.pem'),
      },
      public: 'https://localhost:8080/'

  },
  // Change build paths to make them Maven compatible
  // see https://cli.vuejs.org/config/
  outputDir: 'target/dist',
  assetsDir: 'static'
};
