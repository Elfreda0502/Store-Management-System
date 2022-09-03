
module.exports = {
  publicPath: './',
  devServer: {
    open: true,
    proxy: {
      '/api': {
        target: 'http://localhost:3000/', 
        changeOrigin: true, 
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
